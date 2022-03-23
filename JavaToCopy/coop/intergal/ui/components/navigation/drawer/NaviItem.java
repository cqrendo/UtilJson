package coop.intergal.ui.components.navigation.drawer;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.dependency.CssImport;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.Image;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.router.AfterNavigationEvent;
import com.vaadin.flow.router.HighlightCondition;
import com.vaadin.flow.router.HighlightConditions;
import com.vaadin.flow.router.QueryParameters;
import com.vaadin.flow.router.RouterLink;

import coop.intergal.ui.util.UIUtils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@CssImport("styles/components/navi-item.css")
public class NaviItem extends Div {

    private String CLASS_NAME = "navi-item";

    private int level = 0;

    private Component link;
    private Class<? extends Component> navigationTarget;
    private String text;

    protected Button expandCollapse;

    private List<NaviItem> subItems;
    private boolean subItemsVisible;

	private Map<String, List<String>> parameters;
    

   

    public NaviItem(VaadinIcon icon, String text, Class<? extends Component> navigationTarget) {
        this(text, navigationTarget, null);
        link.getElement().insertChild(0, new Icon(icon).getElement());
    }

    public NaviItem(Image image, String text, Class<? extends Component> navigationTarget) {
        this(text, navigationTarget, null);
        link.getElement().insertChild(0, image.getElement());
    }
    public NaviItem(VaadinIcon icon, String text, Class<? extends Component> navigationTarget, String parameter) {
        this(text, navigationTarget, null);
        link.getElement().insertChild(0, new Icon(icon).getElement());

	}
//	public NaviItem(String text, Class<? extends Component> navigationTarget, Map<String,List<String>> parameters) {
//		this(text, navigationTarget);
//		this.parameters = parameters;
//		
//	}

	public NaviItem(VaadinIcon icon, String text, Class<? extends Component> navigationTarget, Map<String,List<String>> parameters) {
	       this(text, navigationTarget, parameters);
	       link.getElement().insertChild(0, new Icon(icon).getElement());
	}


    public NaviItem(String text, Class<? extends Component> navigationTarget,  Map<String,List<String>> parameters) {
        setClassName(CLASS_NAME);
        setLevel(0);

        this.text = text;
        this.navigationTarget = navigationTarget;

        if (navigationTarget != null && parameters != null) { // normal when you go to a view you must send parameters, if not this code must be adapt
            QueryParameters queryParameters = new QueryParameters(parameters);
            RouterLink routerLink = new RouterLink(null, navigationTarget);
            routerLink.add(new Span(text));
            routerLink.setClassName(CLASS_NAME + "__link");
	
            routerLink.setQueryParameters(queryParameters);
            routerLink.setHighlightCondition(HighlightConditions.sameLocation());
 //           routerLink.setHighlightCondition(sameLocation());
            this.link = routerLink;

        } else {
            Div div = new Div(new Span(text));
            div.addClickListener(e -> expandCollapse.click());
            div.setClassName(CLASS_NAME + "__link");
            this.link = div;
        }

        expandCollapse = UIUtils.createButton(VaadinIcon.CARET_UP, ButtonVariant.LUMO_SMALL, ButtonVariant.LUMO_TERTIARY);
        expandCollapse.addClickListener(event -> setSubItemsVisible(!subItemsVisible));
        expandCollapse.setVisible(false);

        subItems = new ArrayList<>();
        subItemsVisible = false;
        updateAriaLabel();

        add(link, expandCollapse);
    }

    public static HighlightCondition<RouterLink> sameLocation() {
        return (link, event) -> event.getLocation().getPathWithQueryParameters()
                .equals(link.getHref());
    }
//    public static HighlightCondition<RouterLink> locationPrefix() {
//        return (link, event) -> event.getLocation().getPathWithQueryParameters()
//                .startsWith(link.getHref());
//    }



	private void updateAriaLabel() {
        String action = (subItemsVisible ? "Collapse " : "Expand ") + text;
        UIUtils.setAriaLabel(action, expandCollapse);
    }

    public boolean isHighlighted(AfterNavigationEvent e) {
        return link instanceof RouterLink && ((RouterLink) link)
                .getHighlightCondition().shouldHighlight((RouterLink) link, e);
    }

    public void setLevel(int level) {
        this.level = level;
        if (level > 0) {
            getElement().setAttribute("level", Integer.toString(level));
        }
    }

    public int getLevel() {
        return level;
    }

    public Class<? extends Component> getNavigationTarget() {
        return navigationTarget;
    }

    public void addSubItem(NaviItem item) {
        if (!expandCollapse.isVisible()) {
            expandCollapse.setVisible(true);
        }
        item.setLevel(getLevel() + 1);
        subItems.add(item);
    }

    public void setSubItemsVisible(boolean visible) {
        if (level == 0) {
            expandCollapse.setIcon(new Icon(visible ? VaadinIcon.CARET_UP : VaadinIcon.CARET_DOWN));
        }
        subItems.forEach(item -> item.setVisible(visible));
        subItemsVisible = visible;
        updateAriaLabel();
    }

    public String getText() {
        return text;
    }

    @Override
    public void setVisible(boolean visible) {
        super.setVisible(visible);

        // If true, we only update the icon. If false, we hide all the sub items
        if (visible) {
            if (level == 0) {
                expandCollapse.setIcon(new Icon(VaadinIcon.CARET_DOWN));
            }
        } else {
            setSubItemsVisible(visible);
        }
    }
}
