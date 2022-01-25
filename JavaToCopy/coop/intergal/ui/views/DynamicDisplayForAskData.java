package coop.intergal.ui.views;

import static coop.intergal.AppConst.PACKAGE_VIEWS;
import static coop.intergal.AppConst.PAGE_PRODUCTS;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.ComponentEvent;
import com.vaadin.flow.component.ComponentEventListener;
import com.vaadin.flow.component.Key;
import com.vaadin.flow.component.Tag;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.dependency.JsModule;
import com.vaadin.flow.component.dependency.Uses;
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
import com.vaadin.flow.shared.Registration;
import com.vaadin.flow.templatemodel.TemplateModel;

import coop.intergal.ui.components.FormButtonsBar;
import coop.intergal.ui.util.GenericClassForMethods;
//import coop.intergal.tys.ui.views.comprasyventas.compras.ArticulosQueryForPick;//
//import coop.intergal.tys.ui.views.comprasyventas.compras.ProveedorQueryForPick;
import coop.intergal.ui.utils.converters.CurrencyFormatter;
import coop.intergal.vaadin.rest.utils.DynamicDBean;
import coop.intergal.vaadin.rest.utils.RestData;

//@Tag("dynamic-view-grid")
@Tag("dynamic-display-for-ask-data")
@JsModule("./src/views/generic/layout/dynamic-display-for-ask-data.js")
//@Route(value = PAGE_DYNAMIC+"forPICK")//, layout = MainView.class)
//@PageTitle(AppConst.TITLE_PRODUCTS)
//@Secured(Role.ADMIN)
//@Uses(ArticulosQueryForPick.class)  TODO check is works with last releases of vaadin
//@Uses(ProveedorQueryForPick.class) 
public class DynamicDisplayForAskData extends PolymerTemplate<TemplateModel> implements BeforeEnterObserver, HasDynamicTitle  {
	private ArrayList <String> rowsColList; //= getRowsCnew String[] { "code_customer", "name_customer", "cif", "amountUnDisbursedPayments" };
	private String preConfParam;
	public ArrayList<String> getRowsColList() {
		return rowsColList;
	}

	public void setRowsColList(ArrayList<String> rowsColList) {
		this.rowsColList = rowsColList;
	}	

	public DynamicDisplayForAskData() {
		super();
//		setupGrid();
		
	}

	public DynamicDisplayForAskData(TemplateParser parser, VaadinService service) {
		super(parser, service);
		// TODO Auto-generated constructor stub
	}

	public DynamicDisplayForAskData(TemplateParser parser) {
		super(parser);
		// TODO Auto-generated constructor stub
	}

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

//	@Id("search")
//	private SearchBar search;
	
//	private DynamicGridRowCard grid;
//	DynamicViewGrid grid = new DynamicViewGrid();
	
//	@Id("divGrid")
//	private Div divGrid;
	
	
//	@Id("querySplitListSplitDisplay")
//	private SplitLayout gridSplitDisplay;

//	private CrudEntityPresenter<DynamicDBean> presenter;

	private final Binder<DynamicDBean> binder = new Binder<>(DynamicDBean.class);
	

//	private CurrencyFormatter currencyFormatter = new CurrencyFormatter();
//
//	private String resourceName;
	private String title;
	private String filter;
	@Id("divDisplay")
	private Div divDisplay;
	@Id("acceptDataAndContinue")
	private Button acceptDataAndContinue;
	
//	@Id("buttons") // dummy for generic code
	private FormButtonsBar buttons;
	public FormButtonsBar getButtons() {
		return buttons;
	}

	public void setButtons(FormButtonsBar buttons) {
		this.buttons = buttons;
	}
	
	private GenericClassForMethods genericClassForMethods;
	
	public GenericClassForMethods getGenericClassForMethods() {
	return genericClassForMethods;
}

	public void setGenericClassForMethods(GenericClassForMethods genericClassForMethods) {
	this.genericClassForMethods = genericClassForMethods;
}



	public Div getDivDisplay() {
		return divDisplay;
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
	public void beforeEnter(BeforeEnterEvent event) {
//		acceptDataAndContinue.addClickShortcut(Key.F10);
		acceptDataAndContinue.setVisible(false); // for now this generic button is hide is left the code to reactivate
		QueryParameters queryParameters = event.getLocation().getQueryParameters();
		filter = null; 
		List<String> parFIlter = queryParameters.getParameters().get("filter");
		if (parFIlter != null)
			{
			filter = parFIlter.get(0);
			filter=filter.replace("EEQQ", "=");
			}
		title="";
		String queryFormClassName = null;
		String displayFormClassName  = null;
//		String resourceSubGrid = null;
		if (queryParameters != null && !queryParameters.getParameters().isEmpty())
		{
			title=queryParameters.getParameters().get("title").get(0);
//			resourceName = queryParameters.getParameters().get("resourceName").get(0);
			queryFormClassName = PACKAGE_VIEWS+queryParameters.getParameters().get("queryFormClassName").get(0);
			displayFormClassName = PACKAGE_VIEWS+queryParameters.getParameters().get("displayFormClassName").get(0);
//			resourceSubGrid =  queryParameters.getParameters().get("resourceSubGrid").get(0);
		}
//		try {
//			Class<?> dynamicForm = Class.forName(displayFormClassName);
//			Object queryForm = dynamicForm.newInstance();
////			Method setGrid = dynamicForm.getMethod("setGrid", new Class[] {coop.intergal.ui.views.DynamicViewGrid.class} );
////			setGrid.invoke(queryForm,grid);
//			getDivQuery().add((Component)queryForm);
//		} catch (ClassNotFoundException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		} catch (InstantiationException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		} catch (IllegalAccessException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		} catch (NoSuchMethodException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		} catch (SecurityException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		} catch (IllegalArgumentException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		} catch (InvocationTargetException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//		
//
//
//		
	}
//	void showBean(DynamicDBean bean, String displayFormClassName ) {
//		try {
////			selectedRow = bean;
////			Class<?> dynamicForm = Class.forName("coop.intergal.tys.ui.views.DynamicForm");
//			Class<?> dynamicForm = Class.forName(displayFormClassName);//"coop.intergal.tys.ui.views.comprasyventas.compras.PedidoProveedorForm");
//			Object display = dynamicForm.newInstance();
//			Method setRowsColList = dynamicForm.getMethod("setRowsColList", new Class[] {java.util.ArrayList.class} );
//			Method setBinder = dynamicForm.getMethod("setBinder", new Class[] {com.vaadin.flow.data.binder.Binder.class} );
//			Method setDataProvider= dynamicForm.getMethod("setDataProvider", new Class[] {coop.intergal.vaadin.rest.utils.DdbDataBackEndProvider.class} );
//			
//			Method setBean = dynamicForm.getMethod("setBean", new Class[] {coop.intergal.vaadin.rest.utils.DynamicDBean.class} );
//			setRowsColList.invoke(display,rowsColListGrid);
//			setBinder.invoke(display,binder);
//			
//			setBean.invoke(display,bean);
//			setDataProvider.invoke(display, dataProvider);
//			divDisplay.removeAll();
//			if (displayFormClassName.indexOf("Generated") > -1)
//			{
//			//	setDataProvider.invoke(display, dataProvider);
//				Method createContent= dynamicForm.getMethod("createContent");
//				Method setdVGrid= dynamicForm.getMethod("setDVGrid", new Class[] {coop.intergal.ui.views.DynamicViewGrid.class});
//				setdVGrid.invoke(display, this); // to use methods in this class
//				divInDisplay = createContent.invoke(display);
//				divDisplay.add((Component)divInDisplay);
//			}
//			else
//			{
//				divDisplay.add((Component)display);
//			}
//	
//		}	
	@Override
	public String getPageTitle() {
		return title;
	}

	public static class AcceptDataAndContinueEvent extends ComponentEvent<DynamicDisplayForAskData> {
		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;

		public AcceptDataAndContinueEvent(DynamicDisplayForAskData source, boolean fromClient) {
			super(source, fromClient);
		}
	}

//	public Registration addAcceptDataAndContinueListener(ComponentEventListener<AcceptDataAndContinueEvent> listener) {
//		return acceptDataAndContinue.addClickListener(e -> listener.onComponentEvent(new AcceptDataAndContinueEvent(this, true)));
//}

	public Div getDivQuery() {
		return divDisplay;
	}

	public void setDivQuery(Div divQuery) {
		this.divDisplay = divQuery;
	}

	public void setClassForMethod(GenericClassForMethods genericClassForMethods) {
		this.genericClassForMethods = genericClassForMethods;
		
	}


//	@Override
//	protected CrudEntityPresenter<DynamicDBean> getPresenter() {
//		// TODO Auto-generated method stub
//		return null;
//	}
}
