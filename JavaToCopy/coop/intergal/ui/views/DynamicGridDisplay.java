package coop.intergal.ui.views;
import static coop.intergal.AppConst.PACKAGE_VIEWS;
import static coop.intergal.AppConst.PAGE_PRODUCTS;
import static coop.intergal.AppConst.STYLES_CSS;
import static coop.intergal.AppConst.STYLES_FORM_LAYOUT_ITEM_CSS;

import java.util.ArrayList;
import java.util.List;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.Tag;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.dependency.CssImport;
import com.vaadin.flow.component.dependency.JsModule;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.polymertemplate.Id;
import com.vaadin.flow.component.polymertemplate.PolymerTemplate;
import com.vaadin.flow.component.polymertemplate.TemplateParser;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.router.BeforeEnterEvent;
import com.vaadin.flow.router.BeforeEnterObserver;
import com.vaadin.flow.router.HasDynamicTitle;
import com.vaadin.flow.router.QueryParameters;
import com.vaadin.flow.server.VaadinService;
import com.vaadin.flow.templatemodel.TemplateModel;

import coop.intergal.ui.components.FormButtonsBar;
import coop.intergal.ui.util.UtilSessionData;
import coop.intergal.vaadin.rest.utils.DdbDataBackEndProvider;
import coop.intergal.vaadin.rest.utils.DynamicDBean;
import com.vaadin.flow.component.splitlayout.SplitLayout;


//@Tag("dynamic-view-grid")
@Tag("dynamic-grid-display")
@JsModule("./src/views/generic/layout/dynamic-grid-display.js")
//@Route(value = PAGE_DYNAMIC, layout = MainLayout.class)   /// @@ TODO cambiado para metaconfig comprobar que no afecta en otros proyectos
//@PageTitle(AppConst.TITLE_PRODUCTS)
//@Secured(Role.ADMIN)
@CssImport(value = STYLES_CSS, themeFor = "dynamic-grid-display")
@CssImport(value = STYLES_FORM_LAYOUT_ITEM_CSS, themeFor = "vaadin-form-layout")
public class DynamicGridDisplay extends PolymerTemplate<TemplateModel> implements BeforeEnterObserver, HasDynamicTitle{//, VaadinServiceInitListener  {
//public class DynamicGridDisplay extends ThemableMixin(PolymerElement<TemplateModel>) implements BeforeEnterObserver, HasDynamicTitle  {
	private ArrayList <String> rowsColList; //= getRowsCnew String[] { "code_customer", "name_customer", "cif", "amountUnDisbursedPayments" };
//	private String preConfParam;
//	private Binder<DynamicDBean> binder;
	public ArrayList<String> getRowsColList() {
		return rowsColList;
	}

	public void setRowsColList(ArrayList<String> rowsColList) {
		this.rowsColList = rowsColList;
	}	

	public DynamicGridDisplay() {
		super();
		setId("DGD");
//		setupGrid();
		
	}

	public DynamicGridDisplay(TemplateParser parser, VaadinService service) {
		super(parser, service);
		// TODO Auto-generated constructor stub
	}

	public DynamicGridDisplay(TemplateParser parser) {
		super(parser);
		// TODO Auto-generated constructor stub
	}

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

//	@Id("search")
//	private SearchBar search;
	
	@Id("grid")
	private DynamicViewGrid grid;
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

	private Binder<DynamicDBean> binder = new Binder<>(DynamicDBean.class);
	private DdbDataBackEndProvider dataProvider; 
	private DynamicDBean bean;
	

	public DynamicDBean getBean() {
		return bean;
	}

	public void setBean(DynamicDBean bean) {
		this.bean = bean;
	}

	public DdbDataBackEndProvider getDataProvider() {
		return dataProvider;
	}

	public void setDataProvider(DdbDataBackEndProvider dataProvider) {
		this.dataProvider = dataProvider;
	}

//	private CurrencyFormatter currencyFormatter = new CurrencyFormatter();
	private String extraFilterToSelect;
	public String getExtraFilterToSelect() {
		return extraFilterToSelect;
	}

	public void setExtraFilterToSelect(String extraFilterToSelect) {
		this.extraFilterToSelect = extraFilterToSelect;
	}

	private String resourceName;
	private String title;
	private String filter;
	@Id("buttons")
	private FormButtonsBar buttons;
	private String apiname;
	private boolean cache = true;
//	private Object divInDisplay;
	private String queryFormClassName;
	public String getQueryFormClassName() {
		return queryFormClassName;
	}

	public void setQueryFormClassName(String queryFormClassName) {
		this.queryFormClassName = queryFormClassName;
	}

	//	@Id("splitQryAndResult")
//	private SplitLayout splitQryAndResult;
	private String displayFormClassName;

	@Id("displaySplitSubGrid")
	private SplitLayout displaySplitSubGrid;
	public SplitLayout getDisplaySplitSubGrid() {
		return displaySplitSubGrid;
	}

	public void setDisplaySplitSubGrid(SplitLayout displaySplitSubGrid) {
		this.displaySplitSubGrid = displaySplitSubGrid;
	}



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
	public void setBinder(Binder<DynamicDBean> binder) {
		this.binder = binder;
	}

	public String getFilter() {
		return filter;
	}

	public void setFilter(String filter) {
		this.filter = filter;
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

public String getResourceName() {
		return resourceName;
	}

	public void setResourceName(String resourceName) {
		this.resourceName = resourceName;
	}

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
		title="";
//		String queryFormClassName = null;
//		String displayFormClassName  = null;
//		String resourceSubGrid = null;
		if (queryParameters != null && !queryParameters.getParameters().isEmpty())
		{
			title=queryParameters.getParameters().get("title").get(0);
			resourceName = queryParameters.getParameters().get("resourceName").get(0);
			if (queryParameters.getParameters().get("apiname") != null)
				apiname = queryParameters.getParameters().get("apiname").get(0);
			if (queryParameters.getParameters().get("cache") != null)
				{
				String cacheStr = queryParameters.getParameters().get("cache").get(0);
				if (cacheStr.equals("false"))
					cache = false;
				else
					cache = true;
				}
			//*** PACKAGE_VIEWS is used when the class is no generic for several projects. and corresponds a particular class for the form
			queryFormClassName = queryParameters.getParameters().get("queryFormClassName").get(0);
			displayFormClassName= queryParameters.getParameters().get("displayFormClassName").get(0);
			if (queryFormClassName.startsWith("coop.intergal.ui.views") == false)
				queryFormClassName = PACKAGE_VIEWS+queryParameters.getParameters().get("queryFormClassName").get(0);
			if (displayFormClassName.startsWith("coop.intergal.ui.views") == false)
				displayFormClassName = PACKAGE_VIEWS+queryParameters.getParameters().get("displayFormClassName").get(0);
			
//			resourceSubGrid =  queryParameters.getParameters().get("resourceSubGrid").get(0);
		}
		createContent() ;
	}
		public Component createContent() {
			return createContent(null);
		}
		public Component createContent(DynamicDBean preSelectRow) {
		
//		splitQryAndResult.setSplitterPosition(2);
//		gridDisplaySubGrid.setMinWidth("20px");

		grid.setDisplayFormClassName(displayFormClassName);
		grid.setDisplay(divDisplay);
		grid.setDivSubGrid(divSubGrid);
		grid.setButtonsForm(buttons);
		grid.setButtonsRowVisible(false);  // @@ TODO set by parameter  
		grid.setLayout(this);
//		grid.setGridSplitDisplay(gridSplitDisplay);
		grid.setResourceName(resourceName);
		grid.getGrid().addSelectionListener(e -> {
			DynamicDBean seletedRow;
			if (e.getFirstSelectedItem().isPresent())
				{
					seletedRow = (DynamicDBean)e.getFirstSelectedItem().get();
					if (extraFilterToSelect != null)
						keepSessionDataForFilter(seletedRow,extraFilterToSelect);
					grid.showBean(seletedRow);
				}
			});
		if ((apiname == null || apiname.length() == 0) == false)
		{
			if (filter != null  && filter.length() > 0)
			{
				filter = filter + "%20%AND%20APIname='"+apiname+"'";
			}
			else
			{
			filter = "APIname='"+apiname+"'";
			}
		}
		grid.setFilter(filter);
		System.out.println("DynamicGridDisplay.beforeEnter() CACHE "+ cache);
		grid.setCache(cache);
		grid.setupGrid(false, true);
		if (preSelectRow != null)
			grid.showBean(preSelectRow);
//		divGrid.add(grid );
		buttons.setVisible(false);
		buttons.addSaveListener(e -> grid.saveSelectedRow(apiname));
		buttons.addCancelListener(e -> grid.undoSelectedRow());
		buttons.addAddListener(e -> grid.insertANewRow());
		buttons.addDeleteListener(e -> grid.DeleteARow());
		buttons.addPrintListener(e -> grid.PrintARow());
	
//		queryButtonsBar.addClearSearchListener(e -> cleanQryForm());//System.out.println("PedidoProveedorQuery.beforeEnter() BUSCAR>>>>"));

		return this;
	}

	private void keepSessionDataForFilter(DynamicDBean seletedRow, String extraFilterToSelect2) {
		if (extraFilterToSelect2 != null &&extraFilterToSelect2.indexOf("#SVKN#") > -1) // #SVN = Session Variable Key Name  By Example- > CLAVE_ALMACEN=#SVKN#"DynamicViewGrid.lastAlmacen#SVNKEnd#
		{
			int idxStart = extraFilterToSelect.indexOf("#SVKN#")+6;
			int idxEnd = extraFilterToSelect.indexOf("#SVNKEnd#");
			String sVkn = extraFilterToSelect.substring(idxStart,idxEnd );
			String field = extraFilterToSelect.substring(0,idxStart-7);
			String valueOfField = seletedRow.getRowJSon().get(field).asText();
			UtilSessionData.setFormParams(sVkn,valueOfField);
		}	
			
		}

	public String getDisplayFormClassName() {
			return displayFormClassName;
		}

		public void setDisplayFormClassName(String displayFormClassName) {
			this.displayFormClassName = displayFormClassName;
		}

	@Override
	public String getPageTitle() {
		return title;
	}


//	@Override
//	public void serviceInit(ServiceInitEvent event) {
//	      // add view only during development time
//        if (!event.getSource()
//                .getDeploymentConfiguration()
//                .isProductionMode()) {
//            RouteConfiguration configuration =
//               RouteConfiguration.forApplicationScope();
//
//            configuration.setRoute(PAGE_DYNAMIC,
//               MainLayout.class);
//        }
//    }
//	@Override
//	protected CrudEntityPresenter<DynamicDBean> getPresenter() {
//		// TODO Auto-generated method stub
//		return null;
//	}
}
