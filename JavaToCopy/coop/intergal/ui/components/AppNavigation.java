package coop.intergal.ui.components;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;

import com.vaadin.flow.component.Tag;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.dependency.JsModule;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.icon.IronIcon;
import com.vaadin.flow.component.polymertemplate.Id;
import com.vaadin.flow.component.polymertemplate.PolymerTemplate;
import com.vaadin.flow.component.tabs.Tab;
import com.vaadin.flow.component.tabs.Tabs;
import com.vaadin.flow.router.AfterNavigationEvent;
import com.vaadin.flow.router.AfterNavigationObserver;
import com.vaadin.flow.router.QueryParameters;
import com.vaadin.flow.templatemodel.TemplateModel;
import coop.intergal.ui.entities.PageInfo;

@Tag("app-navigation")
@JsModule("./src/components/app-navigation.js")
public class AppNavigation extends PolymerTemplate<TemplateModel> implements AfterNavigationObserver {

	@Id("tabs")
	private Tabs tabs;

	private List<String> hrefs = new ArrayList<>();
	private List<QueryParameters> listQParams = new ArrayList<>();
	private String logoutHref;
	private String defaultHref;
	private String currentHref;

	public void init(List<PageInfo> pages, String defaultHref, String logoutHref) {
		this.logoutHref = logoutHref;
		this.defaultHref = defaultHref;

		for (PageInfo page : pages) {
			Tab tab = new Tab(new IronIcon("vaadin", page.getIcon()), new Span(page.getTitle()));
			tab.getElement().setAttribute("theme", "icon-on-top");
			String paramsList = page.getParamsList();
			QueryParameters queryParameters = null;
			if (paramsList != null)
			{
				queryParameters = fillParams(page.getParamsList());				
			}	
			listQParams.add(queryParameters);
			hrefs.add(page.getLink());
			
			tabs.add(tab);
		}

		tabs.addSelectedChangeListener(e -> navigate());
	}
	private QueryParameters fillParams(String paramsList) {
		Map<String, List<String>> parameters = new HashMap();
		StringTokenizer tokens=new StringTokenizer(paramsList, "&");
		while(tokens.hasMoreTokens()){
			List<String> values = new ArrayList<>();
			String param = tokens.nextToken();
			String [] paramAndValue = param.split("="); 
			values.add(paramAndValue[1]);
			parameters.put(paramAndValue[0],values);
	           
	            
	        }
		QueryParameters queryParameters = new QueryParameters(parameters);
		return queryParameters;
//		return queryParameters.simple(new Map()["",""]);
	}
//	Button button = new Button("Navigate to my view");
//	button.addClickListener( e-> {
//	Map<String, List<String>> parameters = new HashMap();
//	parameters.put("param", "value");
//	QueryParameters queryParameters = new QueryParameters(parameters);
//	button.getUI().ifPresent(ui -> ui.navigate("myView", queryParameters));
//	});
	
	private void navigate() {
		int idx = tabs.getSelectedIndex();
		if (idx >= 0 && idx < hrefs.size()) {
			String href = hrefs.get(idx);
			QueryParameters qParams = listQParams.get(idx);
			if (href.equals(logoutHref)) {
				// The logout button is a 'normal' URL, not Flow-managed but
				// handled by Spring Security.
				UI.getCurrent().getPage().executeJavaScript("location.assign('logout')");
			} else if (!href.equals(currentHref)) {
//				UI.getCurrent().getPage().executeJavaScript("window.open('https://www.google.com', '_blank');") ;
				if (qParams != null)
					UI.getCurrent().navigate(href, qParams);
				else
					UI.getCurrent().navigate(href);
			}
		}
	}

	@Override
	public void afterNavigation(AfterNavigationEvent event) {
		String href = event.getLocation().getFirstSegment().isEmpty() ? defaultHref
				: event.getLocation().getFirstSegment();
		currentHref = href;
		if (hrefs.indexOf(href) > 0 )
			tabs.setSelectedIndex(hrefs.indexOf(href));
	}
}
