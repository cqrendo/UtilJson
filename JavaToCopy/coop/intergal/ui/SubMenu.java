package coop.intergal.ui;

import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.Tag;
import com.vaadin.flow.component.dependency.JsModule;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.polymertemplate.Id;
import com.vaadin.flow.component.polymertemplate.PolymerTemplate;
import com.vaadin.flow.component.tabs.Tab;
import com.vaadin.flow.component.tabs.Tabs;
import com.vaadin.flow.router.BeforeEnterEvent;
import com.vaadin.flow.router.BeforeEnterObserver;
import com.vaadin.flow.router.HasDynamicTitle;
import com.vaadin.flow.router.QueryParameters;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.templatemodel.TemplateModel;

import coop.intergal.AppConst;
import coop.intergal.ui.components.FlexBoxLayout;
import coop.intergal.ui.util.UtilSessionData;
import coop.intergal.vaadin.rest.utils.DdbDataBackEndProvider;
import coop.intergal.vaadin.rest.utils.DynamicDBean;
import coop.intergal.vaadin.rest.utils.RestData;

@Tag("sub-menu")
@JsModule("./src/sub-menu.js")
@Route(value = AppConst.PAGE_SUBMENU)//, layout = MainLayout.class)
//@PageTitle(AppConst.TITLE_MENU_PRINCIPAL)
//@Secured(Role.ADMIN)
public class SubMenu extends PolymerTemplate<TemplateModel> implements BeforeEnterObserver, HasDynamicTitle {
/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Id("dvTabs")
	private Div dvTabs;
	@Id("dvPages")
	private Div dvPages;
	private String filter;
	private Tabs tabs = new Tabs(false);
	private Tab tab1;
	private Div page1;
	private Div pages = new Div();
	private String title = AppConst.TITLE_MENU_PRINCIPAL +" ("+UtilSessionData.getCompanyYear()+")";
	private ArrayList<String[]> rowsColList; //= getRowsCnew String[] { "code_customer", "name_customer", "cif", "amountUnDisbursedPayments" };
	private String filtertab;
	public ArrayList<String[]> getRowsColList() {return rowsColList;}
	public void setRowsColList(ArrayList<String[]> rowsColList) {this.rowsColList = rowsColList;}	

	public SubMenu() {
		super();
	}
	
	@Override
	public void beforeEnter(BeforeEnterEvent event) {
		QueryParameters queryParameters = event.getLocation().getQueryParameters();
		filter = queryParameters.getParameters().get("filter").get(0);
		filter=filter.replace("EEQQ", "=");
		title=queryParameters.getParameters().get("title").get(0);
		setupButtons();
	}

	private void setupButtons() {
		DdbDataBackEndProvider dataProvider = new DdbDataBackEndProvider();
		System.out.println("SubMenu.setupButtons() " + filter);
		dataProvider.setPreConfParam(UtilSessionData.getCompanyYear()+AppConst.PRE_CONF_PARAM);
		dataProvider.setResourceName("CR-menu");
		rowsColList = dataProvider.getRowsColList();
		Collection<DynamicDBean> menuList = RestData.getResourceData(0,0,"CR-menu", AppConst.PRE_CONF_PARAM_METADATA, rowsColList, filter, false, false, null);
		Iterator<DynamicDBean> itMenuList = menuList.iterator();
		tabs.removeAll();
		Map<Tab, Component> tabsToPages = new HashMap<>();
		while (itMenuList.hasNext())
		{	
			Div div= new Div();
			div.getStyle().set("display", "table");
			div.getStyle().set("width", "100%");
			DynamicDBean rowMenu = itMenuList.next();
			String optionName = rowMenu.getCol0().toString();
			filtertab = "isFKidMenu="+rowMenu.getRowJSon().get("idMenu").asText();
			dvTabs.getStyle().set("display", "inherit");
			tab1 = new Tab(optionName);
			tabs.add(tab1);
			page1 = new Div();
			page1.getStyle().set("display", "table");
			page1.getStyle().set("width", "100%");
			pages.getStyle().set("width", "100%");
			SubSubmenu laPage = new SubSubmenu(filtertab);
			page1.add(laPage);
			page1.setVisible(false);
			tabsToPages.put(tab1, page1);
			tabs.add(tab1);
			pages.add(page1);
			tabs.addSelectedChangeListener(event -> {
			    tabsToPages.values().forEach(page -> page.setVisible(false));
			    Component selectedPage = tabsToPages.get(tabs.getSelectedTab());
			    if (selectedPage != null) selectedPage.setVisible(true);
			});
			dvTabs.add(tabs);
			dvPages.add(pages);
		}		
	}

	@Override
	public String getPageTitle() {
		System.out.println("SubMenu.getPageTitle() " +  title);
		try {
			title = java.net.URLDecoder.decode(title, StandardCharsets.UTF_8.name());
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	    FlexBoxLayout p1 = (FlexBoxLayout) this.getParent().get();
	    Component p2 = p1.getParent().get();
	    Component p3 = p2.getParent().get();
//	    MainLayout mL = (MainLayout) p3.getParent().get();
//	    mL.getAppBar().setTitle(title);

		return UtilSessionData.addCompanyToTitle(title);
	}
}
