package coop.intergal.ui.views;

import static coop.intergal.AppConst.PAGE_PRODUCTS;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.Tag;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.dependency.JsModule;
import com.vaadin.flow.component.grid.ColumnTextAlign;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.polymertemplate.Id;
import com.vaadin.flow.component.polymertemplate.PolymerTemplate;
import com.vaadin.flow.component.splitlayout.SplitLayout;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.data.renderer.LocalDateRenderer;
import com.vaadin.flow.data.renderer.TemplateRenderer;
import com.vaadin.flow.router.AfterNavigationEvent;
import com.vaadin.flow.router.AfterNavigationObserver;
import com.vaadin.flow.router.BeforeEnterEvent;
import com.vaadin.flow.router.BeforeEnterObserver;
import com.vaadin.flow.router.HasDynamicTitle;
import com.vaadin.flow.router.QueryParameters;
import com.vaadin.flow.templatemodel.TemplateModel;

import coop.intergal.AppConst;
import coop.intergal.espresso.presutec.utils.JSonClient;
import coop.intergal.ui.utils.TranslateResource;
import coop.intergal.ui.utils.converters.CurrencyFormatter;
import coop.intergal.vaadin.rest.utils.DdbDataBackEndProvider;
import coop.intergal.vaadin.rest.utils.DynamicDBean;

//@Tag("dynamic-view-grid")
@Tag("dynamic-view-grid")
@JsModule("./src/views/admin/products/dynamic-view-grid.js")
//@Route(value = PAGE_DYNAMIC)//, layout = MainView.class)
//@PageTitle(AppConst.TITLE_PRODUCTS)
//@Secured(Role.ADMIN)
//public class DynamicViewGrid extends CrudViewREST<DynamicDBean,TemplateModel> implements BeforeEnterObserver,AfterNavigationObserver, HasDynamicTitle  {
public class DynamicGridRowCard extends PolymerTemplate<TemplateModel> implements BeforeEnterObserver,AfterNavigationObserver, HasDynamicTitle  {

//	@Autowired
//	public DynamicViewGrid(String entityName, CrudForm form) {
//		super("DynamicDBean", form);
//		// TODO Auto-generated constructor stub
//	}

//PolymerTemplate<TemplateModel> 
	private ArrayList<String[]> rowsColList; //= getRowsCnew String[] { "code_customer", "name_customer", "cif", "amountUnDisbursedPayments" };
	private String preConfParam;
	
//public DynamicViewGrid() {
//		// TODO Auto-generated constructor stub
//	}

//	public DynamicViewGrid(String resourceSubGrid) {
//		this.resourceName = resourceSubGrid;
//	}

	public ArrayList<String[]> getRowsColList() {
		return rowsColList;
	}

	public void setRowsColList(ArrayList<String[]> rowsColList) {
		this.rowsColList = rowsColList;
	}	

//	public DynamicViewGrid() {
//		super();
////		setupGrid();
//		
//	}
//
//	public DynamicViewGrid(TemplateParser parser, VaadinService service) {
//		super(parser, service);
//		// TODO Auto-generated constructor stub
//	}
//
//	public DynamicViewGrid(TemplateParser parser) {
//		super(parser);
//		// TODO Auto-generated constructor stub
//	}

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

//	@Id("search")
//	private SearchBar search;

	@Id("grid")
	private Grid<DynamicDBean> grid;

//	private CrudEntityPresenter<DynamicDBean> presenter;

	private final Binder<DynamicDBean> binder = new Binder<>(DynamicDBean.class);
	

	private CurrencyFormatter currencyFormatter = new CurrencyFormatter();

	private String resourceName;
	private String title;
	private String filter;
//	private DynamicForm display;
	private DynamicGridDisplay layout;
	private SplitLayout gridSplitDisplay;
//	private Div divDisplay;
	private Div divDisplay;
	private String className = "C1";
	private Div divSubGrid;
	@Id("newRow")
	private Button newRow;

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

	public void setupGrid() {
	
	//	grid.scrollTo(1); 
	//	grid.getDataProvider().
	//	DdbDataProvider dataProvider = new DdbDataProvider();
		DdbDataBackEndProvider dataProvider = new DdbDataBackEndProvider();
		dataProvider.setPreConfParam(AppConst.PRE_CONF_PARAM);
		dataProvider.setResourceName(getResourceName());
		dataProvider.setFilter(getFilter());
//		grid = new Grid<>(DynamicDBean.class); 
		grid.setDataProvider(dataProvider);
//		Crud<DynamicDBean> crud = new Crud<>();
//		crud.setDataProvider(dataProvider);
//		grid.addColumn(DynamicDBean::getCol1).setHeader("Product Name").setFlexGrow(10);
		rowsColList = dataProvider.getRowsColList();
//		grid.removeAllColumns();
		int numberOFCols = rowsColList.size();//length;  // TODO Check is work possible use of maxNumberOfFields
	//       addColumn(Customer::getId, new NumberRenderer()).setCaption("Id");
//		for (int i=0;i<numberOFCols; i++)
//		{
//			addFormatedColumn(i);
//		}
//		grid.addColumn(getTemplate()
//				.withProperty("orderCard", DynamicDBean::getCol0)
//		//		.withProperty("header", order -> presenter.getHeaderByOrderId(order.getId()))
//				.withEventHandler("cardClick",
//						order -> UI.getCurrent().navigate(AppConst.PAGE_STOREFRONT + "/" + order.getId())));
		
		grid.addColumn(TemplateRenderer.<DynamicDBean> of(
		        "<div style='border: 1px solid gray; padding: 10px; width: 100%; box-sizing: border-box;'>"
		                + "<div>Hi! My name is <b>[[item.firstName]]!</b></div>"
		                + "<div>Hi! My second name is <b>[[item.lastname]]!</b></div>"
		                + "<div><img style='height: 80px; width: 80px;' src='[[item.image]]'/></div>"
		                + "</div>")
		        .withProperty("firstName", DynamicDBean::getCol0)
		        .withProperty("lastname", DynamicDBean::getCol1)
		        .withProperty("address", DynamicDBean::getCol2)
		        .withProperty("image", DynamicDBean::getCol3)
		        .withEventHandler("handleClick", person -> {
		            grid.getDataProvider().refreshItem(person);
		        }));

}
//private  getTemplate() {
//	// TODO Auto-generated method stub
//	return null;
//}
public static TemplateRenderer<DynamicDBean> getTemplate() {
	return TemplateRenderer.of(
			  "<order-card"
			+ "  header='[[item.header]]'"
			+ "  order-card='[[item.orderCard]]'"
			+ "  on-card-click='cardClick'>"
			+ "</order-card>");
}
private void addFormatedColumn(int i) {
	String header = TranslateResource.getFieldLocale(rowsColList.get(i)[0], preConfParam);
	if (isDate(header)) 
	{
		header=header.substring(2); // to avoid date typ indicator "D#"
		grid.addColumn(new LocalDateRenderer<>(d ->d.getColLocalDate(i+""),"dd/MM/yyyy")).setHeader(header).setResizable(true);
//		grid.addColumn(d ->d.getCol(i)).setHeader(header).setResizable(true);
	}
	else if (isCurrency(header))
	{
		header=header.substring(2);
		grid.addColumn(d -> currencyFormatter.encode(getCents(d.getCol(i+"")))).setHeader(header).setTextAlign(ColumnTextAlign.END).setResizable(true);
	}
	else
		grid.addColumn(d ->d.getCol(i+"")).setHeader(header).setResizable(true);
	
}

private Integer getCents(String col) {
	String cents= col;
	if (col.indexOf(".") > -1)
		cents = col.substring(0, col.indexOf(".")) +  col.substring(col.indexOf(".")+1); // take off "."
	return new Integer (cents);
}
// ****** the isType, are prefix that determines the type ( D# = data; C# = currency) this prefix are fill in the labels names, by example ResourceBundle...

private boolean isCurrency(String header) {
	if (header.startsWith("C#"))
		return true; 
	return false;
}


private boolean isDate(String header) {
	if (header.startsWith("D#"))
		return true; 
	return false;
}

//	@Override
	public Grid<DynamicDBean> getGrid() {
		return grid;
	}

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

//	@Override
//	protected SearchBar getSearchBar() {
//		return search;
//	}

//	@Override
//	protected CrudEntityPresenter<DynamicDBean> getPresenter() {
//		// TODO Auto-generated method stub
//		return null;
//	}

	@Override
	public void afterNavigation(AfterNavigationEvent event) {
	//	grid.isColumnReorderingAllowed()
		QueryParameters queryParameters = event.getLocation().getQueryParameters();
		if (queryParameters != null && !queryParameters.getParameters().isEmpty())
			setResourceName(queryParameters.getParameters().get("resourceName").get(0));
//@@		setupEventListeners();
		grid.addSelectionListener(e -> {
//			getBinder()..select(e.getFirstSelectedItem());
//			e.getFirstSelectedItem().ifPresent(entity -> {
//				navigateToEntity(entity.toString());
//			setBean(DynamicDBean bean)
			if (e.getFirstSelectedItem().isPresent())
				showBean((DynamicDBean)e.getFirstSelectedItem().get());
//				getGrid().deselectAll();
			});
//		});
//@@		display.getButtons().addSaveListener(e -> nextRow());
//		setupGrid();
		
	}
	private Object nextRow() {
//		public static void scrollTo(Grid<?> grid, int index) {
		    UI.getCurrent().getPage().executeJavaScript("$0._scrollToIndex(" + 1 + ")", grid.getElement());
//		}
//		UI.getCurrent().getPage().executeJavaScript("$0._scrollToIndex($1)", grid, 10);
		return null;
	}

	private void showBean(DynamicDBean bean ) {
		try {
//			Class<?> dynamicForm = Class.forName("coop.intergal.tys.ui.views.DynamicForm");
			Class<?> dynamicForm = Class.forName("coop.intergal.tys.ui.views.comprasyventas.compras.PedidoProveedorForm");
			Object display = dynamicForm.newInstance();
			Method setRowsColList = dynamicForm.getMethod("setRowsColList", new Class[] {java.util.ArrayList.class} );
			Method setBinder = dynamicForm.getMethod("setBinder", new Class[] {com.vaadin.flow.data.binder.Binder.class} );
			Method setBean = dynamicForm.getMethod("setBean", new Class[] {coop.intergal.vaadin.rest.utils.DynamicDBean.class} );
			setRowsColList.invoke(display,rowsColList);
			setBinder.invoke(display,binder);
			setBean.invoke(display,bean);
			divDisplay.removeAll();
			divDisplay.add((Component)display);
			String resourceSubGrid = "CR-ped_proveed_cab.List-ped_proveed_lin"; // TODO send by param
			divSubGrid.removeAll();
			DynamicGridRowCard subDynamicViewGrid = new DynamicGridRowCard();
			
			subDynamicViewGrid.setResourceName(resourceSubGrid);
			if (resourceSubGrid.indexOf(".")> -1)
				subDynamicViewGrid.setFilter(componFKFilter(bean, resourceSubGrid));
			subDynamicViewGrid.setupGrid();
			divSubGrid.add(subDynamicViewGrid );
			


//		GenericDynamicForm  display = null; 
//		if (className.equals("C0")r
//			display = (GenericDynamicForm)new DynamicForm(); 
//		if (className.equals("C1"))
//			display = (GenericDynamicForm)new DynamicForm2(); 
//		if (display != null)
//			{
//			display.setRowsColList(rowsColList);
//			display.setBinder(binder);
//			display.setBean(bean);
//			divDisplay.removeAll();
//			divDisplay.add((Component)display);
//
//			}
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InstantiationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NoSuchMethodException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SecurityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

//		display.beforeEnter(null);
//		gridSplitDisplay.getElement().removeAllChildren();//removeChild(display.getElement());
//		gridSplitDisplay.getElement().appendChild(grid.getElement());
//		gridSplitDisplay.getElement().appendChild(display.getElement());

//	UI.getCurrent().navigate("dymanic");
}
	public String componFKFilter(DynamicDBean bean, String resourceSubGrid) {
		String fKfilter = JSonClient.getHt().get(resourceSubGrid);
	//	"FASE_CABEZERA" = ["FASE"]
	//			 and "CLAVE_ALMACEN" = ["CLAVE_ALMACEN"]
	//			 and "N_PEDIDO" = ["N_PEDIDO"]}
		int step = 0;
		String componFilter = "";
		int lengthFKfilter = fKfilter.length();
		int leftLength = lengthFKfilter;
		while (lengthFKfilter > 0 || fKfilter.length()  > 0)
		{
			int idXEqual = fKfilter.indexOf("=");
			if (idXEqual == -1)
				break;
			int idXMark = fKfilter.indexOf("]");
			if (fKfilter.startsWith("\n and"))
				step = 6;
			else
				step = 0;
			String fKfieldName = fKfilter.substring(step+1, idXEqual - 2  );
			String parentfieldName = fKfilter.substring(4+idXEqual, idXMark - 1  );
			String parentValue = bean.getRowJSon().get(parentfieldName).asText();
			componFilter = componFilter + fKfieldName + "=" + parentValue + "%20and%20";
			lengthFKfilter = lengthFKfilter - idXMark;
			fKfilter = fKfilter.substring(idXMark+1);
			
		}
		componFilter = componFilter.substring(0, componFilter.length()-9); // to delete last and
		return componFilter;
	}

	@Override
	public void beforeEnter(BeforeEnterEvent event) {
		QueryParameters queryParameters = event.getLocation().getQueryParameters();
//		filter = null; 
		List<String> parFIlter = queryParameters.getParameters().get("filter");
		if (parFIlter != null)
			{
			setFilter(parFIlter.get(0));
			setFilter(getFilter().replace("EEQQ", "="));
			}
		title="..";
		if (queryParameters != null && !queryParameters.getParameters().isEmpty())
			title=queryParameters.getParameters().get("title").get(0);
		
	}

	@Override
	public String getPageTitle() {
		return title;
	}

	public void setQueryParameters(QueryParameters queryParameters) {
		// TODO Auto-generated method stub
		
	}

//	@Override
//	public void setParameter(BeforeEvent event, Long parameter) {
//		// TODO Auto-generated method stub
//		
//	}

	public void setDisplay(Div divDisplay) {
		this.divDisplay = divDisplay;
		
	}

	public void setLayout(DynamicGridDisplay layout) {
		this.layout = layout;
		
	}

	public void setGridSplitDisplay(SplitLayout gridSplitDisplay) {
		this.gridSplitDisplay = gridSplitDisplay;
		
	}

	public void setDivSubGrid(Div divSubGrid) {
		this.divSubGrid = divSubGrid;
		
	}

	public String getResourceName() {
		return resourceName;
	}

	public void setResourceName(String resourceName) {
		this.resourceName = resourceName;
	}

	public String getFilter() {
		return filter;
	}

	public void setFilter(String filter) {
		this.filter = filter;
	}

//	@Override
//	protected CrudEntityPresenter<DynamicDBean> getPresenter() {
//		// TODO Auto-generated method stub
//		return null;
//	}
}
