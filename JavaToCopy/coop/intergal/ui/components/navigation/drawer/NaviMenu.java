package coop.intergal.ui.components.navigation.drawer;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.dependency.CssImport;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.Image;
import com.vaadin.flow.component.icon.VaadinIcon;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@CssImport("styles/components/navi-menu.css")
public class NaviMenu extends Div {

    private String CLASS_NAME = "navi-menu";

    public NaviMenu() {
        setClassName(CLASS_NAME);
    }

    protected void addNaviItem(NaviItem item) {
        add(item);
    }

    protected void addNaviItem(NaviItem parent, NaviItem item) {
        parent.addSubItem(item);
        addNaviItem(item);
    }

    public void filter(String filter) {
        getNaviItems().forEach(naviItem -> {
            boolean matches = ((NaviItem) naviItem).getText().toLowerCase()
                    .contains(filter.toLowerCase());
            naviItem.setVisible(matches);
        });
    }

    public NaviItem addNaviItem(String text,
                                Class<? extends Component> navigationTarget) {
        NaviItem item = new NaviItem(text, navigationTarget, null);
        addNaviItem(item);
        return item;
    }

    public NaviItem addNaviItem(VaadinIcon icon, String text,
            Class<? extends Component> navigationTarget) {
        NaviItem item = new NaviItem(icon, text, navigationTarget);
        addNaviItem(item);
        return item;
    }

    public NaviItem addNaviItem(Image image, String text,
            Class<? extends Component> navigationTarget) {
        NaviItem item = new NaviItem(image, text, navigationTarget);
        addNaviItem(item);
        return item;
    }

    public NaviItem addNaviItem(NaviItem parent, String text,
            Class<? extends Component> navigationTarget) {
        NaviItem item = new NaviItem(text, navigationTarget,null);
        addNaviItem(parent, item);
        return item;
    }
    public NaviItem addNaviItem(NaviItem parent, String text,
            Class<? extends Component> navigationTarget, Map<String,List<String>> parameters) {
        NaviItem item = new NaviItem(text, navigationTarget, parameters);
        addNaviItem(parent, item);
        return item;
    }
    public NaviItem addNaviItem(VaadinIcon icon, String text,
            Class<? extends Component> navigationTarget, Map<String,List<String>> parameters) {
        NaviItem item = new NaviItem(icon, text, navigationTarget, parameters);
        addNaviItem(item);
        return item;
    }
    
    public NaviItem addNaviItem(VaadinIcon icon, String text,
            Class<? extends Component> navigationTarget, String parameter) {
        NaviItem item = new NaviItem(icon, text, navigationTarget, parameter);
        addNaviItem(item);
        return item;
    }

    public List<NaviItem> getNaviItems() {
        List<NaviItem> items = (List) getChildren()
                .collect(Collectors.toList());
        return items;
    }

}
