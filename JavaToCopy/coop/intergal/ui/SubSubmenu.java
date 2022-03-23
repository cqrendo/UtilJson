package coop.intergal.ui;

import com.vaadin.flow.templatemodel.TemplateModel;

import coop.intergal.AppConst;
import coop.intergal.ui.components.FlexBoxLayout;
import coop.intergal.ui.util.UtilSessionData;
import coop.intergal.ui.utils.converters.CurrencyFormatter;
import coop.intergal.vaadin.rest.utils.DataService;
import coop.intergal.vaadin.rest.utils.DdbDataBackEndProvider;
import coop.intergal.vaadin.rest.utils.DynamicDBean;
import coop.intergal.vaadin.rest.utils.RestData;

import static coop.intergal.AppConst.PAGE_PRODUCTS;

import java.io.UnsupportedEncodingException;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import com.fasterxml.jackson.databind.JsonNode;
import com.vaadin.flow.component.ClickEvent;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.Tag;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.dependency.JsModule;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.Paragraph;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.polymertemplate.Id;
import com.vaadin.flow.component.polymertemplate.PolymerTemplate;
import com.vaadin.flow.component.polymertemplate.TemplateParser;
import com.vaadin.flow.component.tabs.Tab;
import com.vaadin.flow.component.tabs.Tabs;
import com.vaadin.flow.data.binder.BeanValidationBinder;
import com.vaadin.flow.data.provider.DataProvider;
import com.vaadin.flow.router.AfterNavigationEvent;
import com.vaadin.flow.router.AfterNavigationObserver;
import com.vaadin.flow.router.BeforeEnterEvent;
import com.vaadin.flow.router.BeforeEnterObserver;
import com.vaadin.flow.router.HasDynamicTitle;
import com.vaadin.flow.router.QueryParameters;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.VaadinService;

/**
 * A Designer generated component for the sub-submenu template.
 *
 * Designer will add and remove fields with @Id mappings but
 * does not overwrite or otherwise change this file.
 */
@Tag("sub-submenu")
@JsModule("./src/sub-submenu.js")
@Route(value = "ssubmenu")//, layout = MainLayout.class)
public class SubSubmenu extends PolymerTemplate<TemplateModel> implements BeforeEnterObserver, HasDynamicTitle {//AfterNavigationObserver, HasDynamicTitle {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Id("butomGroup1")
	private VerticalLayout vlButomsGroup1;

	private String losThemes;
	private String filter = "";
	private final BeanValidationBinder<DynamicDBean> binder = new BeanValidationBinder<>(DynamicDBean.class);
	
	private ArrayList<String[]> rowsColList; 
	public ArrayList<String[]> getRowsColList() {return rowsColList;}
	public void setRowsColList(ArrayList<String[]> rowsColList) {this.rowsColList = rowsColList;}	

	private String title = AppConst.TITLE_MENU_PRINCIPAL;

	private int esTab = 0;

	public SubSubmenu() {
		super();
	}

	public SubSubmenu(String filter2) {
		filter = filter2;
		esTab  = 1;
		setupButtons();
	}
	private void setupButtons() {
		System.out.println("SubSubmenu.setupButtons() cambio " + filter);
		vlButomsGroup1.removeAll();
	    if (UtilSessionData.getCompanyYear() == null || UtilSessionData.getCompanyYear().isEmpty() == true) // not menu until company is choose
	    {
	    	DataService.get().showError("Primero debe seleccionar la Empresa, pulse en el logo.");
	    	return;
	    }
		DdbDataBackEndProvider dataProvider = new DdbDataBackEndProvider();
		dataProvider.setPreConfParam(UtilSessionData.getCompanyYear()+AppConst.PRE_CONF_PARAM);
		dataProvider.setResourceName("CR-menu");
		rowsColList = dataProvider.getRowsColList();
		
		Collection<DynamicDBean> menuList = RestData.getResourceData(0,0,"CR-menu", AppConst.PRE_CONF_PARAM_METADATA, rowsColList, filter, UtilSessionData.getCache(), false, null);
		Iterator<DynamicDBean> itMenuList = menuList.iterator();
		while (itMenuList.hasNext())
		{	
			Div div= new Div();
			div.getStyle().set("display", "table");
			div.getStyle().set("width", "100%");
			DynamicDBean rowMenu = itMenuList.next();
			String optionName = rowMenu.getCol0().toString();
			String resourceName = rowMenu.getCol4();
			if (resourceName.equals("null") || resourceName.length() == 0)
			{
				Button  titulo = new Button(optionName);
				losThemes = rowMenu.getRowJSon().get("theme").asText();
				titulo.addThemeNames(losThemes);
				div.add(titulo);
				JsonNode rowNode = rowMenu.getRowJSon();
				Iterator<JsonNode> subMenus = rowNode.get("List-menu").elements();
				while (subMenus.hasNext())
				{
					Paragraph div2 = new Paragraph();
					div2.getStyle().set("margin", "0");
					JsonNode rowSubMenu = subMenus.next();
					String optionNameSubmenu = rowSubMenu.get("optionName").asText();
					Button  button1 = new Button(optionNameSubmenu, evt -> processButon(evt,rowSubMenu ));
					losThemes = rowSubMenu.get("theme").asText();
					button1.addThemeNames(losThemes);
					String microHelp = rowSubMenu.get("microHelp").asText();
					if (microHelp != null && microHelp.equals("null") == false)
						button1.getElement().setAttribute("title",microHelp); 
					div2.add(button1);
					div.add(div2);
				}
			}
			else
			{
				Button  button1 = new Button(optionName, evt -> processButon(evt,rowMenu.getRowJSon() ));
				String losThemes = rowMenu.getRowJSon().get("theme").asText();
				String microHelp = rowMenu.getRowJSon().get("microHelp").asText();
				button1.addThemeNames(losThemes);
				button1.getElement().setAttribute("title",microHelp); 
				div.add(button1);
			}
			vlButomsGroup1.add(div);	
		}
	}
	
	private Object processButon(ClickEvent<Button> evt, JsonNode rowSubMenu) {
		String titleOption = rowSubMenu.get("optionName").asText();
		String layoutPage = rowSubMenu.get("layoutPage").asText();
		if (layoutPage == null || layoutPage.isEmpty() || layoutPage.equals("null") )
			layoutPage = AppConst.PAGE_DYNAMIC_QGD;
		else if (layoutPage.equals("PAGE_DYNAMIC_QGD"))
			layoutPage = AppConst.PAGE_DYNAMIC_QGD;
		else if (layoutPage.equals("PAGE_DYNAMIC_QG"))
			layoutPage = AppConst.PAGE_DYNAMIC_QG;
		else if (layoutPage.equals("PAGE_DYNAMIC_TREE"))
			layoutPage = AppConst.PAGE_DYNAMIC_TREE;
		else
			DataService.get().showError("valor invalido para layoutPage, debe de ser (PAGE_DYNAMIC_QG o PAGE_DYNAMIC_QGD o PAGE_DYNAMIC_TREE");
		try {
			String urlBase = "../"+layoutPage;
			String hostName = InetAddress.getLocalHost().getHostName() ;
			if (hostName.indexOf(".local") == -1 && hostName.indexOf("FC-NB-MLOPEZ") == -1) // to diferent when is running in local (Maven) or in remote (tys.war -> tomcat)
				urlBase= "../tys"+AppConst.CURRENT_YEAR+"/"+layoutPage;
		String resource = rowSubMenu.get("resource").asText();
		String queryFormClassName = rowSubMenu.get("queryFormClassName").asText();
		String displayFormClassName = rowSubMenu.get("displayFormClassName").asText();
		String addFormClassName = rowSubMenu.get("addFormClassName").asText();
		String idMenu = rowSubMenu.get("idMenu").asText();
		
//		titleOption = titleOption.replace(" ", "%20");
		UI.getCurrent().getPage().executeJs("window.open('"+urlBase+"?resourceName="+resource+"&queryFormClassName="+queryFormClassName+"&displayFormClassName="+displayFormClassName+"&addFormClassName="+addFormClassName+"&title="+titleOption+"&idMenu="+idMenu+"', '_blank');") ;
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
//    private String componTitle(String optionName) {
//		String title = optionName+" ("+UtilSessionData.getCompanyYear()+")";
//		try {
//			title = java.net.URLDecoder.decode(title, StandardCharsets.UTF_8.name());
//		} catch (UnsupportedEncodingException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//		return  title;
//	}

//	@Override
//	public void afterNavigation(AfterNavigationEvent event) {
	@Override
	public void beforeEnter(BeforeEnterEvent event) {
	
		if (esTab == 0) {
			QueryParameters queryParameters = event.getLocation().getQueryParameters();
			filter = queryParameters.getParameters().get("filter").get(0);
			filter=filter.replace("EEQQ", "=");
			title=queryParameters.getParameters().get("title").get(0);
			setupButtons();
		}
	}

	@Override
	public String getPageTitle() {
		try {
			title = java.net.URLDecoder.decode(title, StandardCharsets.UTF_8.name());
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        FlexBoxLayout p1 = (FlexBoxLayout) this.getParent().get();
        Component p2 = p1.getParent().get();
        Component p3 = p2.getParent().get();
//        MainLayout mL = (MainLayout) p3.getParent().get();
//        mL.getAppBar().setTitle(title);
		return UtilSessionData.addCompanyToTitle(title);
	}
 
}
