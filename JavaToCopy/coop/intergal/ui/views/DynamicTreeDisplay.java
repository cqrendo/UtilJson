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
import java.util.stream.Stream;

import com.fasterxml.jackson.databind.JsonNode;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.Tag;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.dependency.JsModule;
import com.vaadin.flow.component.grid.Grid.Column;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.polymertemplate.Id;
import com.vaadin.flow.component.polymertemplate.PolymerTemplate;
import com.vaadin.flow.component.polymertemplate.TemplateParser;
import com.vaadin.flow.component.treegrid.TreeGrid;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.data.provider.hierarchy.AbstractBackEndHierarchicalDataProvider;
import com.vaadin.flow.data.provider.hierarchy.HierarchicalDataProvider;
import com.vaadin.flow.data.provider.hierarchy.HierarchicalQuery;
import com.vaadin.flow.router.BeforeEnterEvent;
import com.vaadin.flow.router.BeforeEnterObserver;
import com.vaadin.flow.router.HasDynamicTitle;
import com.vaadin.flow.router.QueryParameters;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.VaadinService;
import com.vaadin.flow.templatemodel.TemplateModel;

import coop.intergal.AppConst;
import coop.intergal.espresso.presutec.utils.JSonClient;
import coop.intergal.ui.components.FlexBoxLayout;
import coop.intergal.ui.components.FormButtonsBar;
import coop.intergal.ui.utils.UtilSessionData;
import coop.intergal.ui.utils.converters.CurrencyFormatter;
import coop.intergal.vaadin.rest.utils.DdbDataBackEndProvider;
import coop.intergal.vaadin.rest.utils.DdbHierarchicalDataProvider;
import coop.intergal.vaadin.rest.utils.DynamicDBean;
import coop.intergal.vaadin.rest.utils.RestData;

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
//	private NaviDrawer naviDrawer;
//	@Id("splitQryAndResult")
//	private SplitLayout splitQryAndResult;
	private FlexBoxLayout viewContainer;
	private FlexBoxLayout column;
	private FlexBoxLayout row;
	private FlexBoxLayout flexBoxLayout;
	private TreeGrid<DynamicDBean> treeGrid = new TreeGrid<>();
	private DdbHierarchicalDataProvider dataProvider;
	private ArrayList<String[]> rowsColListGrid;
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
		
		setupGrid();
//		grid.setDisplayFormClassName(displayFormClassName);
//		grid.setDisplay(divDisplay);
//		grid.setDivSubGrid(divSubGrid);
//		grid.setButtonsForm(buttons);
//		grid.setLayout(this);
////		grid.setGridSplitDisplay(gridSplitDisplay);
//		grid.setResourceName(resourceName);
		if (filter != null  && filter.length() > 0)
		{
			filter = filter + "%20%AND%20APIname='"+apiname+"'";
		}
		else
		{
			filter = "APIname='"+apiname+"'";
		}
		
//		grid.setFilter(filter);
//		grid.setupGrid(false);
//		divGrid.add(grid );
		divTree.add(treeGrid);
		buttons.setVisible(false);
//		buttons.addSaveListener(e -> grid.saveSelectedRow(apiname));
//		buttons.addCancelListener(e -> grid.undoSelectedRow());
//		buttons.addAddListener(e -> grid.insertANewRow());
//		buttons.addDeleteListener(e -> grid.DeleteARow());
	
//		queryButtonsBar.addClearSearchListener(e -> cleanQryForm());//System.out.println("PedidoProveedorQuery.beforeEnter() BUSCAR>>>>"));

		
	}

	@Override
	public String getPageTitle() {
		return title;
	}
	public void setupGrid() {
		
	//	grid.scrollTo(1); 
	//	grid.getDataProvider().
	//	DdbDataProvider dataProvider = new DdbDataProvider();
		dataProvider = new DdbHierarchicalDataProvider();
		dataProvider.setPreConfParam(UtilSessionData.getCompanyYear()+AppConst.PRE_CONF_PARAM);
		dataProvider.setResourceName(resourceName);
//		dataProvider.setFilter(getFilter());
//		grid = new Grid<>(DynamicDBean.class); 
//		grid.removeAllColumns();
		treeGrid.setDataProvider(dataProvider);
//		treeGrid.setEnterNextRow(true);
		treeGrid.setMultiSort(true);

//		Crud<DynamicDBean> crud = new Crud<>();
//		crud.setDataProvider(dataProvider);
//		grid.addColumn(DynamicDBean::getCol1).setHeader("Product Name").setFlexGrow(10);
		rowsColListGrid = dataProvider.getRowsColList();
//		newRow.addClickListener(e -> insertBeanInList());
//		deleteRow.addClickListener(e -> deleteBeanFromList());
//		grid.removeAllColumns();
		int numberOFCols = rowsColListGrid.size();//length;
		System.out.println("DynamicTreeDisplay.setupGrid() "+ numberOFCols);
		treeGrid.addHierarchyColumn(DynamicDBean::getCol0).setHeader("Account Title");
	//       addColumn(Customer::getId, new NumberRenderer()).setCaption("Id");
		for (int i=0;i<numberOFCols; i++)
		{
			Column<DynamicDBean> col = addTreeColumn(i);
			if (col != null)
				col.setAutoWidth(true);
		}
//		grid.getColumns().forEach(column -> column.setAutoWidth(true));

}
	   private Column<DynamicDBean> addTreeColumn(int i) {
		   
		   String[] colData = rowsColListGrid.get(i);
		   String colName = colData[2];
		   String colType = colData[3];
		   String colHeader = colData[6];
		   Column<DynamicDBean> col = null;
		   if (colData[1].indexOf("#SIG#")>-1) { 
			   System.out.println("DynamicTreeDisplay.addTreeColumn() "+ colName);
			   String header = colHeader;
			   col = treeGrid.addColumn(d -> d.getCol(colName)).setHeader(header).setResizable(true).setSortProperty(colData[0]) ;
		   }
		// TODO Auto-generated method stub
		return col;
	}

}
