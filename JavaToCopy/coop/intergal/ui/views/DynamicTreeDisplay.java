package coop.intergal.ui.views;
import static coop.intergal.AppConst.PACKAGE_VIEWS;
import static coop.intergal.AppConst.PAGE_DYNAMIC;
import static coop.intergal.AppConst.PAGE_DYNAMIC_TREE;
import static coop.intergal.AppConst.PAGE_PRODUCTS;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.databind.JsonNode;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.Tag;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.dependency.JsModule;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.polymertemplate.Id;
import com.vaadin.flow.component.polymertemplate.PolymerTemplate;
import com.vaadin.flow.component.polymertemplate.TemplateParser;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.router.BeforeEnterEvent;
import com.vaadin.flow.router.BeforeEnterObserver;
import com.vaadin.flow.router.HasDynamicTitle;
import com.vaadin.flow.router.QueryParameters;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.VaadinService;
import com.vaadin.flow.templatemodel.TemplateModel;

import coop.intergal.AppConst;
import coop.intergal.espresso.presutec.utils.JSonClient;
import coop.intergal.metadata.ui.components.FlexBoxLayout;
import coop.intergal.metadata.ui.components.navigation.drawer.NaviDrawer;
import coop.intergal.metadata.ui.components.navigation.drawer.NaviItem;
import coop.intergal.metadata.ui.components.navigation.drawer.NaviMenu;
import coop.intergal.metadata.ui.util.css.FlexDirection;
import coop.intergal.metadata.ui.util.css.Overflow;
import coop.intergal.ui.components.FormButtonsBar;
import coop.intergal.ui.utils.converters.CurrencyFormatter;
import coop.intergal.vaadin.rest.utils.DynamicDBean;

//@Tag("dynamic-view-grid")
@Tag("dynamic-tree-display")
@JsModule("./src/views/generic/layout/dynamic-tree-display.js")
@Route(value = PAGE_DYNAMIC_TREE)//, layout = MainView.class)
//@PageTitle(AppConst.TITLE_PRODUCTS)
//@Secured(Role.ADMIN)
public class DynamicTreeDisplay extends PolymerTemplate<TemplateModel> implements BeforeEnterObserver, HasDynamicTitle  {
	private ArrayList <String> rowsColList; //= getRowsCnew String[] { "code_customer", "name_customer", "cif", "amountUnDisbursedPayments" };
	private String preConfParam;
	public ArrayList<String> getRowsColList() {
		return rowsColList;
	}

	public void setRowsColList(ArrayList<String> rowsColList) {
		this.rowsColList = rowsColList;
	}	

	public DynamicTreeDisplay() {
		super();
//		setupGrid();
		
	}

	public DynamicTreeDisplay(TemplateParser parser, VaadinService service) {
		super(parser, service);
		// TODO Auto-generated constructor stub
	}

	public DynamicTreeDisplay(TemplateParser parser) {
		super(parser);
		// TODO Auto-generated constructor stub
	}

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

//	@Id("search")
//	private SearchBar search;
	
	@Id("divTree")
	private Div divTree;
//	private DynamicGridRowCard grid;
//	DynamicViewGrid grid = new DynamicViewGrid();
	
//	@Id("divGrid")
//	private Div divGrid;
	
	@Id("divDisplay")
	private Div divDisplay;
	@Id("divSubGrid")  
	private Div divSubGrid;
	
//	@Id("querySplitListSplitDisplay")
//	private SplitLayout gridSplitDisplay;

//	private CrudEntityPresenter<DynamicDBean> presenter;

	private final Binder<DynamicDBean> binder = new Binder<>(DynamicDBean.class);
	

	private CurrencyFormatter currencyFormatter = new CurrencyFormatter();

	private String resourceName;
	private String title;
	private String filter;
	@Id("buttons")
	private FormButtonsBar buttons;
	private String apiname;
	private NaviDrawer naviDrawer;
//	@Id("splitQryAndResult")
//	private SplitLayout splitQryAndResult;
	private FlexBoxLayout viewContainer;
	private FlexBoxLayout column;
	private FlexBoxLayout row;
	private FlexBoxLayout flexBoxLayout;
	private static final String CLASS_NAME = "root";

//	@Autowired()
//	public DynamicViewGrid(CrudEntityPresenter<DynamicDBean> presenter, CrudForm<DynamicDBean> form) {
//		super(EntityUtil.getName(DynamicDBean.class), form);
//		this.presenter = presenter;
//		form.setBinder(binder);
//
//		setupEventListeners();
//		setupGrid();
////		presenter.setView(this);
//	}

//	private void setupGrid() {
//	
//		DdbDataProvider dataProvider = new DdbDataProvider();
//		dataProvider.setPreConfParam("GferPrueba");
//		dataProvider.setResourceName(resourceName);
//		grid.setDataProvider(dataProvider);
////		grid.addColumn(DynamicDBean::getCol1).setHeader("Product Name").setFlexGrow(10);
//		rowsColList = dataProvider.getRowsColList();
////		grid.removeAllColumns();
//		int numberOFCols =rowsColList.size();//length;
//	//       addColumn(Customer::getId, new NumberRenderer()).setCaption("Id");
//    if (numberOFCols > 0) 
//    {
//    	grid.addColumn(DynamicDBean::getCol0).setHeader(TranslateResource.getFieldLocale(rowsColList.get(0), preConfParam));
//    }	
//    if (numberOFCols > 1) 
//    {
//    	grid.addColumn(DynamicDBean::getCol1).setHeader(TranslateResource.getFieldLocale(rowsColList.get(1), preConfParam));
//    }
//    if (numberOFCols > 2) 
//    {
//    	grid.addColumn(DynamicDBean::getCol2).setHeader(TranslateResource.getFieldLocale(rowsColList.get(2), preConfParam));
//    }
//    if (numberOFCols > 3) 
//    {
//    	grid.addColumn(DynamicDBean::getCol3).setHeader(TranslateResource.getFieldLocale(rowsColList.get(3), preConfParam));
//    }
//    if (numberOFCols > 4) 
//    {
//    	grid.addColumn(DynamicDBean::getCol4).setHeader(TranslateResource.getFieldLocale(rowsColList.get(4), preConfParam));
//    }
//    if (numberOFCols > 5) 
//    {
//    	grid.addColumn(DynamicDBean::getCol5).setHeader(TranslateResource.getFieldLocale(rowsColList.get(5), preConfParam));
//    }
//    if (numberOFCols > 6) 
//    {
//    	grid.addColumn(DynamicDBean::getCol6).setHeader(TranslateResource.getFieldLocale(rowsColList.get(6), preConfParam));
//    }
////		grid.addColumn(p -> currencyFormatter.encode(p.getPrice())).setHeader("Unit Price");
//	}

////	@Override
//	public Grid<DynamicDBean> getGrid() {
//		return grid;
//	}

//	@Override
//	protected CrudEntityPresenterREST<DynamicDBean> getPresenter() {
//		return presenter;
//	}

//	@Override
	protected String getBasePage() {
		return PAGE_PRODUCTS;
	}

//	@Override
	protected Binder<DynamicDBean> getBinder() {
		return binder;
	}

////	@Override
//	protected SearchBar getSearchBar() {
//		return search;
//	}

//	@Override
//	protected CrudEntityPresenter<DynamicDBean> getPresenter() {
//		// TODO Auto-generated method stub
//		return null;
//	}

//	@Override
//	public void afterNavigation(AfterNavigationEvent event) {
//		QueryParameters queryParameters = event.getLocation().getQueryParameters();
//		if (queryParameters != null && !queryParameters.getParameters().isEmpty())
//			resourceName = queryParameters.getParameters().get("resourceName").get(0);
////		grid.setQueryParameters(queryParameters);
////		setupGrid();
//			
//		
//	}
	public Button showButtonClickedMessage()
	{
		return null;
		
	}

	@Override
	public void beforeEnter(BeforeEnterEvent event) {  // when is call from a navigation
		QueryParameters queryParameters = event.getLocation().getQueryParameters();
		filter = null; 
		List<String> parFIlter = queryParameters.getParameters().get("filter");
		if (parFIlter != null)
			{
			filter = parFIlter.get(0);
			filter=filter.replace("EEQQ", "=");
			}
//		List<String> parinlinelimit = queryParameters.getParameters().get("inlinelimit");
//		if (parinlinelimit != null)
//			{
//			if (filter != null)
//				filter = filter + "&inlinelimit="+parinlinelimit.get(0);
//			else
//				filter = "inlinelimit="+parinlinelimit.get(0);
//			}
		title="..";
		String queryFormClassName = null;
		String displayFormClassName  = null;
//		String resourceSubGrid = null;
		if (queryParameters != null && !queryParameters.getParameters().isEmpty())
		{
			title=queryParameters.getParameters().get("title").get(0);
			resourceName = queryParameters.getParameters().get("resourceName").get(0);
			apiname = queryParameters.getParameters().get("apiname").get(0);
			queryFormClassName = PACKAGE_VIEWS+queryParameters.getParameters().get("queryFormClassName").get(0);
			displayFormClassName = PACKAGE_VIEWS+queryParameters.getParameters().get("displayFormClassName").get(0);
			
//			resourceSubGrid =  queryParameters.getParameters().get("resourceSubGrid").get(0);
		}
		
		grid.setDisplayFormClassName(displayFormClassName);
		grid.setDisplay(divDisplay);
		grid.setDivSubGrid(divSubGrid);
		grid.setButtonsForm(buttons);
		grid.setLayout(this);
//		grid.setGridSplitDisplay(gridSplitDisplay);
		grid.setResourceName(resourceName);
		if (filter != null  && filter.length() > 0)
		{
			filter = filter + "%20%AND%20APIname='"+apiname+"'";
		}
		else
		{
			filter = "APIname='"+apiname+"'";
		}
		
		grid.setFilter(filter);
		grid.setupGrid(false);
//		divGrid.add(grid );
		buttons.setVisible(false);
		buttons.addSaveListener(e -> grid.saveSelectedRow(apiname));
		buttons.addCancelListener(e -> grid.undoSelectedRow());
		buttons.addAddListener(e -> grid.insertANewRow());
		buttons.addDeleteListener(e -> grid.DeleteARow());
	
//		queryButtonsBar.addClearSearchListener(e -> cleanQryForm());//System.out.println("PedidoProveedorQuery.beforeEnter() BUSCAR>>>>"));

		
	}

	@Override
	public String getPageTitle() {
		return title;
	}
	private void initStructure() {
	        naviDrawer = new NaviDrawer();

	        viewContainer = new FlexBoxLayout();
	        viewContainer.addClassName(CLASS_NAME + "__view-container");
	        viewContainer.setOverflow(Overflow.HIDDEN);

	        column = new FlexBoxLayout(viewContainer);
	        column.addClassName(CLASS_NAME + "__column");
	        column.setFlexDirection(FlexDirection.COLUMN);
	        column.setFlexGrow(1, viewContainer);
	        column.setOverflow(Overflow.HIDDEN);

	        row = new FlexBoxLayout(naviDrawer, column);
	        row.addClassName(CLASS_NAME + "__row");
	        row.setFlexGrow(1, column);
	        row.setOverflow(Overflow.HIDDEN);
	        flexBoxLayout.add(row);
	        flexBoxLayout.setFlexGrow(1, row);
	    }
	   private void initNaviItems() {
	        NaviMenu menu = naviDrawer.getMenu();
	        
			try {
				JsonNode rowsList = JSonClient.get("menu","APIname='monbus'%20AND%20application='TGT'%20AND%20isFKidMenu%20is%20null",false,"metadata",500+"");
				for (JsonNode eachRow : rowsList)  {
					String optionName = eachRow.get("optionName").asText();
					String resource = eachRow.get("resource").asText();
					String params = eachRow.get("params").asText();
					int countSubMenus= eachRow.get("countSubmenus").asInt();
					if (countSubMenus > 0)
					{
						 NaviItem submenu = menu.addNaviItem(VaadinIcon.ACCORDION_MENU, optionName,
					                null);
						
						 String idMenu = eachRow.get("idMenu").asText();
						 JsonNode rowsList1 = JSonClient.get("menu","isFKidMenu="+idMenu,false,AppConst.PRE_CONF_PARAM,500+"");
						 for (JsonNode eachRow1 : rowsList1)  {
								String optionName1 = eachRow1.get("optionName").asText();
								String resource1 = eachRow1.get("resource").asText();
								String params1 = eachRow1.get("params").asText();
								int countSubMenus1= eachRow1.get("countSubmenus").asInt();
								if (countSubMenus1 > 0)
								{
									 NaviItem submenu1 = menu.addNaviItem(submenu, optionName1,
								                null);
							//		 submenu1.
									 String idMenu1 = eachRow1.get("idMenu").asText();
									 JsonNode rowsList2 = JSonClient.get("menu","isFKidMenu="+idMenu1,false,AppConst.PRE_CONF_PARAM,500+"");
									 for (JsonNode eachRow2 : rowsList2)  {
											String optionName2 = eachRow2.get("optionName").asText();
											String resource2 = eachRow2.get("resource").asText();
											String params2 = eachRow2.get("params").asText();
											int countSubMenus2= eachRow2.get("countSubmenus").asInt();
											if (countSubMenus2 > 0)
											{
												 NaviItem submenu2 = menu.addNaviItem(VaadinIcon.ASTERISK, optionName2,
											                null);
												 String idMenu2 = eachRow2.get("idMenu").asText();
												 JsonNode rowsList3 = JSonClient.get("menu","isFKidMenu="+idMenu2,false,AppConst.PRE_CONF_PARAM,500+"");
												 for (JsonNode eachRow3 : rowsList3)  {
														String optionName3 = eachRow3.get("optionName").asText();
														String resource3 = eachRow3.get("resource").asText();
														String params3 = eachRow3.get("params").asText();
														int countSubMenus3= eachRow3.get("countSubmenus").asInt();
														if (countSubMenus3 > 0)
														{
															 NaviItem submenu3 = menu.addNaviItem(submenu2, optionName3,
														                null);
															 String idMenu3 = eachRow3.get("idMenu").asText();
														//	 JsonNode rowsList4 = JSonClient.get("menu","isFKidMenu="+idMenu,false,AppConst.PRE_CONF_PARAM,500+"");
															 
															 
														}
														else
														{
													   		Map<String,List<String>> parameters = new HashMap<>();
															parameters.put("resource", Collections.singletonList(resource3));
															parameters.put("title", Collections.singletonList(optionName3));
															parameters.put("params", Collections.singletonList(params3));
															menu.addNaviItem(submenu2, optionName3, GenericGridDetails.class, parameters);
														}
												 
												 }
												 submenu2.setSubItemsVisible(false);
												 
											}
											else
											{
												Map<String,List<String>> parameters = new HashMap<>();
												parameters.put("resource", Collections.singletonList(resource2));
												parameters.put("title", Collections.singletonList(optionName2));
												parameters.put("params", Collections.singletonList(params2));
												menu.addNaviItem(submenu1, optionName2, GenericGridDetails.class,  parameters);
											}	
									 }
									 submenu1.setSubItemsVisible(false);
								}
								else
								{
									Map<String,List<String>> parameters = new HashMap<>();
									parameters.put("resource", Collections.singletonList(resource1));
									parameters.put("title", Collections.singletonList(optionName1));
									parameters.put("params", Collections.singletonList(params1));
									menu.addNaviItem(submenu, optionName1, GenericGridDetails.class, parameters);
								}
						 
						 }
						 submenu.setSubItemsVisible(false);
					}
					else
					{
						Map<String,List<String>> parameters = new HashMap<>();
						parameters.put("resource", Collections.singletonList(resource));
						parameters.put("title", Collections.singletonList(optionName));
						parameters.put("params", Collections.singletonList(params));
						menu.addNaviItem(VaadinIcon.CIRCLE, optionName, GenericGridDetails.class, parameters); 
					}
				}		
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
//	        menu.addNaviItem(VaadinIcon.HOME, "Home", Home.class);
//	        menu.addNaviItem(VaadinIcon.INSTITUTION, "Accounts", GenericGrid.class);
//	        menu.addNaviItem(VaadinIcon.CREDIT_CARD, "Payments", GenericGridDetails.class);
//	        menu.addNaviItem(VaadinIcon.CHART, "Statistics", Statistics.class);
	//
//	        NaviItem personnel = menu.addNaviItem(VaadinIcon.USERS, "Personnel",
//	                null);
//	        menu.addNaviItem(personnel, "Accountants", Accountants.class);
//	        menu.addNaviItem(personnel, "Managers", Managers.class);
	    }


//	@Override
//	protected CrudEntityPresenter<DynamicDBean> getPresenter() {
//		// TODO Auto-generated method stub
//		return null;
//	}
}
