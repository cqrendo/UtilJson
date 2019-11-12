package coop.intergal.ui.views;

import static coop.intergal.AppConst.PACKAGE_VIEWS;
import static coop.intergal.AppConst.PAGE_PRODUCTS;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;
import java.util.Set;
import java.util.StringTokenizer;

import com.fasterxml.jackson.databind.JsonNode;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.Tag;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.dependency.JsModule;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.grid.ColumnTextAlign;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.grid.Grid.Column;
import com.vaadin.flow.component.gridpro.GridPro;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.polymertemplate.Id;
import com.vaadin.flow.component.polymertemplate.PolymerTemplate;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.data.renderer.ComponentRenderer;
import com.vaadin.flow.data.renderer.LocalDateRenderer;
import com.vaadin.flow.router.AfterNavigationEvent;
import com.vaadin.flow.router.AfterNavigationObserver;
import com.vaadin.flow.router.BeforeEnterEvent;
import com.vaadin.flow.router.BeforeEnterObserver;
import com.vaadin.flow.router.HasDynamicTitle;
import com.vaadin.flow.router.QueryParameters;
import com.vaadin.flow.templatemodel.TemplateModel;

import coop.intergal.AppConst;
import coop.intergal.espresso.presutec.utils.JSonClient;
import coop.intergal.ui.components.FormButtonsBar;
import coop.intergal.ui.utils.TranslateResource;
import coop.intergal.ui.utils.converters.CurrencyFormatter;
import coop.intergal.vaadin.rest.utils.DataService;
import coop.intergal.vaadin.rest.utils.DdbDataBackEndProvider;
import coop.intergal.vaadin.rest.utils.DynamicDBean;
import coop.intergal.vaadin.rest.utils.RestData;

//@Tag("dynamic-view-grid")
@Tag("dynamic-grid")
@JsModule("./src/views/generic/dynamic-grid.js")
//@Route(value = PAGE_DYNAMIC)//, layout = MainView.class)
//@PageTitle(AppConst.TITLE_PRODUCTS)
//@Secured(Role.ADMIN)
//public class DynamicViewGrid extends CrudViewREST<DynamicDBean,TemplateModel> implements BeforeEnterObserver,AfterNavigationObserver, HasDynamicTitle  {
public class DynamicViewGrid extends PolymerTemplate<TemplateModel> implements BeforeEnterObserver,AfterNavigationObserver, HasDynamicTitle  {

//	@Autowired
//	public DynamicViewGrid(String entityName, CrudForm form) {
//		super("DynamicDBean", form);
//		// TODO Auto-generated constructor stub
//	}

//PolymerTemplate<TemplateModel> 
//	private ArrayList <String> rowsColList; //= getRowsCnew String[] { "code_customer", "name_customer", "cif", "amountUnDisbursedPayments" };
	private ArrayList<String[]> rowsColListGrid;//=new ArrayListString[]();
//	private ArrayList<String[]> rowsColListForm;
	private String preConfParam;
	private String displayFormClassName;
	private String resourceSubGrid;
	private Hashtable<String, DynamicDBean> beansToSaveAndRefresh = new Hashtable<String, DynamicDBean>(); // to send DynamicDBean to be save and refresh, the name of the one to be save is send in another param
//public DynamicViewGrid() {
//		// TODO Auto-generated constructor stub
//	}

//	public DynamicViewGrid(String resourceSubGrid) {
//		this.resourceName = resourceSubGrid;
//	}

	public ArrayList<String[]> getRowsColListGrid() {
		return rowsColListGrid;
	}

	public void setRowsColList(ArrayList<String[]> rowsColListGrid) {
		this.rowsColListGrid = rowsColListGrid;
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
	private GridPro<DynamicDBean> grid;

//	private CrudEntityPresenter<DynamicDBean> presenter;

	private final Binder<DynamicDBean> binder = new Binder<>(DynamicDBean.class);
	

	private CurrencyFormatter currencyFormatter = new CurrencyFormatter();

	private String resourceName;
	private String title;
	private String filter;
//	private DynamicForm display;
	private DynamicGridDisplay layout;
//	private SplitLayout gridSplitDisplay;
//	private Div divDisplay;
	private Div divDisplay;
	private String className = "C1";
	private Div divSubGrid;
	private DdbDataBackEndProvider dataProvider;
	private DynamicDBean selectedRow;
	private Object display;
	private Method setBean;
	private DynamicDBean keepRowBeforChanges;
	@Id("newRow")
	private Button newRow;
	private DynamicDBean rowIsInserted;
	private DynamicDBean parentRow;
	private Method setBeanParent;
//	private boolean isInError = false;
	private boolean isInsertingALine = false;
private Dialog dialogForPick;
private String pickMapFields;
@Id("deleteRow")
private Button deleteRow;
private FormButtonsBar buttonsForm;

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

public void setupGrid() { // by Default the grid is not editable, to be editable, call setupGrid(true)
	setupGrid(false);
	
}

	public void setupGrid(boolean isGridEditable) {
	
	//	grid.scrollTo(1); 
	//	grid.getDataProvider().
	//	DdbDataProvider dataProvider = new DdbDataProvider();
		dataProvider = new DdbDataBackEndProvider();
		dataProvider.setPreConfParam(AppConst.PRE_CONF_PARAM);
		dataProvider.setResourceName(getResourceName());
		dataProvider.setFilter(getFilter());
//		grid = new Grid<>(DynamicDBean.class); 
		grid.setDataProvider(dataProvider);
		grid.setEnterNextRow(true);
		grid.setMultiSort(true);
		
//		Crud<DynamicDBean> crud = new Crud<>();
//		crud.setDataProvider(dataProvider);
//		grid.addColumn(DynamicDBean::getCol1).setHeader("Product Name").setFlexGrow(10);
		rowsColListGrid = dataProvider.getRowsColList();
		newRow.addClickListener(e -> insertBeanInList());
		deleteRow.addClickListener(e -> deleteBeanFromList());
//		grid.removeAllColumns();
		int numberOFCols = rowsColListGrid.size();//length;
	//       addColumn(Customer::getId, new NumberRenderer()).setCaption("Id");
		for (int i=0;i<numberOFCols; i++)
		{
			Column<DynamicDBean> col = addFormatedColumn(i, isGridEditable);
			if (col != null)
				col.setAutoWidth(true);
		}
//		grid.getColumns().forEach(column -> column.setAutoWidth(true));

}

private Object deleteBeanFromList() {
	System.out.println("DynamicViewGrid.deleteBeanFromList()");
	if (grid.getSelectedItems().size() == 0)
		DataService.get().showError("Debe de selecionar una linea, para eliminar");
	else		
	{
	DynamicDBean rowTobeDelete = grid.getSelectedItems().iterator().next();
	if (rowTobeDelete.getRowJSon() == null) // is a new Row that is inserting 		
	{
		dataProvider.refreshAll();
		newRow.setText("nueva linea");
		dataProvider.setHasNewRow(false);
	}
	else
	{
	beansToSaveAndRefresh.clear();
	beansToSaveAndRefresh.put(rowTobeDelete.getResourceName(), rowTobeDelete);
	beansToSaveAndRefresh.put(parentRow.getResourceName(), parentRow);
	deleteRowInGrid(beansToSaveAndRefresh, rowTobeDelete.getResourceName());
		if (beansToSaveAndRefresh.containsKey("ERROR") == false) 
		{
			dataProvider.setHasNewRow(false);
			try {
//			RestData.refresh(selectedRow);
//			parentRow = RestData.getOneRow(parentRow.getResourceName(), "N_PEDIDO=1", preConfParam, parentRow.getRowsColList());
//			dataProvider.refresh(parentRow);
				setBeanParent.invoke(display,parentRow);
			} catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			}
//			newRow.setText("nueva linea");
//			isInError = false;
		}
		else // has error is waiting for a new save
		{
			dataProvider.setHasNewRow(true);
//			isInError = true;
		}
	}
	}
	return null;
}
	

public DdbDataBackEndProvider getDataProvider() {
		return dataProvider;
	}

	public void setDataProvider(DdbDataBackEndProvider dataProvider) {
		this.dataProvider = dataProvider;
	}
	private Column<DynamicDBean> addFormatedColumn(int i, boolean isGridEditable) {
		String colName = "col"+i;
		String[] colData = rowsColListGrid.get(i);
		String colType = colData[3];
		String colHeader = colData[6];
		Column<DynamicDBean> col = null;
		boolean isNotAParentField = colData[1].indexOf("#SORT")>-1; // parent field for now can not be use as sort column
		boolean isCOlEditable = true;;
		if (colData[1].indexOf("#CNoEDT#")>-1)
			isCOlEditable = false;
		if (colData[1].indexOf("#SIG#")>-1) { // #SIG# = Show In Grid
//			String header = TranslateResource.getFieldLocale(colData[0], preConfParam);
			String header = colHeader;
			if (isDate(header, colType)) {
				if (header.indexOf("#")>0)
					header = header.substring(2); // to avoid date typ indicator "D#"
				if (isCOlEditable && isGridEditable)
//					if (isNotAParentField)
//						grid.addEditColumn(new LocalDateRenderer<>(d -> d.getColLocalDate(colName), "dd/MM/yyyy")).text((item, newValue) -> colChanged(item,colName,newValue)).setHeader(header)
//						.setResizable(true).setSortProperty(colData[0]);
//					else
//						grid.addEditColumn(new LocalDateRenderer<>(d -> d.getColLocalDate(colName), "dd/MM/yyyy")).text((item, newValue) -> colChanged(item,colName,newValue)).setHeader(header)
//						.setResizable(true);
					System.err.println(" REVISAR ESTE CODIGO AL MIGRAR A 14 da error");
				else
					if (isNotAParentField)
						col = grid.addColumn(new LocalDateRenderer<>(d -> d.getColLocalDate(colName), "dd/MM/yyyy")).setHeader(header).setSortProperty(colData[0])
						.setResizable(true).setSortProperty(colData[0]);
					else
						col = grid.addColumn(new LocalDateRenderer<>(d -> d.getColLocalDate(colName), "dd/MM/yyyy")).setHeader(header).setSortProperty(colData[0])
						.setResizable(true);
						
				
//		grid.addColumn(d ->d.getCol(i)).setHeader(header).setResizable(true);
			} else if (isCurrency(header,colType)) {
				if (header.indexOf("#")>0)
					header = header.substring(2);
				if (isCOlEditable  && isGridEditable) 
					if (isNotAParentField)
					{
						col = grid.addEditColumn(d -> currencyFormatter.encode(currencyFormatter.getCents(d.getCol(colName)))).text((item, newValue) -> colChanged(item,colName,newValue)).setHeader(header)
						.setTextAlign(ColumnTextAlign.END).setResizable(true).setSortProperty(colData[0]);
					}
					else
					{
						col = grid.addEditColumn(d -> currencyFormatter.encode(currencyFormatter.getCents(d.getCol(colName)))).text((item, newValue) -> colChanged(item,colName,newValue)).setHeader(header)
						.setTextAlign(ColumnTextAlign.END).setResizable(true);
					}
				else
					if (isNotAParentField)
						{
						col = grid.addColumn(d -> currencyFormatter.encode(currencyFormatter.getCents(d.getCol(colName)))).setHeader(header)
						.setTextAlign(ColumnTextAlign.END).setResizable(true).setSortProperty(colData[0]);
						}
					else
						{
						col = grid.addColumn(d -> currencyFormatter.encode(currencyFormatter.getCents(d.getCol(colName)))).setHeader(header)
						.setTextAlign(ColumnTextAlign.END).setResizable(true);
						}

			}  else if (isBoolean(header,colType)) {
				if (header.indexOf("#")>0)
					header = header.substring(2);
				if (isCOlEditable  && isGridEditable) {
					col = grid.addEditColumn(d -> d.getColBoolean(colName)).checkbox((item, newValue) -> colChanged(item,colName,newValue)).setHeader(header);
//		V14			gridPro.addEditColumn(b -> b.isBoolean(), new IconRenderer<>(
//					        obj -> obj.isBoolean() ? 
//					                VaadinIcon.CHECK.create() : VaadinIcon.CLOSE.create()))
//					        .checkbox(Bean::setAtHome);
//					grid.addEditColumn(new IconRenderer<DynamicDBean>(obj -> {
//						if (obj.getColBoolean(colName)) {
//						return VaadinIcon.CHECK.create();
//						} else {
//						return VaadinIcon.CLOSE.create();
//						}
//						}, obj->"")).checkbox((item, newValue) -> colChanged(item,colName,newValue));
						 
//					if (isNotAParentField)
//					{
//						grid.addEditColumn(d -> currencyFormatter.encode(currencyFormatter.getCents(d.getCol(colName)))).text((item, newValue) -> colChanged(item,colName,newValue)).setHeader(header)
//						.setTextAlign(ColumnTextAlign.END).setResizable(true).setSortProperty(colData[0]);
//					}
//					else
//					{
//						grid.addEditColumn(d -> currencyFormatter.encode(currencyFormatter.getCents(d.getCol(colName)))).text((item, newValue) -> colChanged(item,colName,newValue)).setHeader(header)
//						.setTextAlign(ColumnTextAlign.END).setResizable(true);
					}
				else
					if (isNotAParentField)
						{
						col = grid.addColumn(d -> currencyFormatter.encode(currencyFormatter.getCents(d.getCol(colName)))).setHeader(header)
						.setTextAlign(ColumnTextAlign.END).setResizable(true).setSortProperty(colData[0]);
						}
					else
						{
						col = grid.addColumn(d -> currencyFormatter.encode(currencyFormatter.getCents(d.getCol(colName)))).setHeader(header)
						.setTextAlign(ColumnTextAlign.END).setResizable(true);
						}

			}
			else
				if ((isCOlEditable && isGridEditable))
					if (isNotAParentField)						
						col = grid.addEditColumn(d -> d.getCol(colName)).text((item, newValue) -> colChanged(item,colName,newValue)).setHeader(header).setResizable(true).setSortProperty(colData[0]);
					else
						col = grid.addEditColumn(d -> d.getCol(colName)).text((item, newValue) -> colChanged(item,colName,newValue)).setHeader(header).setResizable(true);				
				else if (isGridEditable && isCOlEditable == false ) 
				{
				//	 grid.addColumn(d -> d.getCol(colName)).setHeader(header).setResizable(true).getElement());
				//	 grid.addColumn(d -> d.getCol(colName)).setHeader(header).setResizable(true);
					//	 Grid<String> grid = new Grid<>();
//					 grid.addColumn(d-> new ComponentRenderer<Label,String>(c->{
//					 Label l = new Label(d.getCol(colName));
//					 l.getElement().addEventListener("click", ev->System.out.println("clicked"));
//					 return l;
//					 })).setResizable(true);;
					if (isNotAParentField)
						{
						col = grid.addColumn(d -> d.getCol(colName)).setHeader(header).setResizable(true).setSortProperty(colData[0]) ;
						}
					else {
						col = grid.addColumn(new ComponentRenderer<Label,DynamicDBean>(item->{
							 Label l = new Label("Buscar....");
							 if (item.getCol(colName) != null && item.getCol(colName).isEmpty() == false)
								 l = new Label(item.getCol(colName));
							 l.getElement().addEventListener("click", ev->pickParent(colName, item));
							 return l;
							 })).setResizable(true).setHeader(header).setSortProperty(colData[0]);
						
					}
				//	 grid.setItems("1", "2", "3", "4", "5", "6", "7", "8", "9", "10");
		//			 add(grid);
				}	
				else
				{
					if (isNotAParentField)
						col = grid.addColumn(d -> d.getCol(colName)).setHeader(header).setResizable(true).setSortProperty(colData[0]) ;
					else 
						col = grid.addColumn(d -> d.getCol(colName)).setHeader(header).setResizable(true) ;
				}
		}
		return col;
	}
private Column<DynamicDBean> addFormatedColumnOLD(int i, boolean isGridEditable) {
		String colName = "col"+i;
		String[] colData = rowsColListGrid.get(i);
		String colType = colData[3];
		Column<DynamicDBean> col = null;
		boolean isNotAParentField = colData[1].indexOf("#SORT")>-1; // parent field for now can not be use as sort column
		boolean isCOlEditable = true;;
		if (colData[1].indexOf("#CNoEDT#")>-1)
			isCOlEditable = false;
		if (colData[1].indexOf("#SIG#")>-1) { // #SIG# = Show In Grid
			String header = TranslateResource.getFieldLocale(colData[0], preConfParam);
			if (isDate(header, colType)) {
				if (header.indexOf("#")>0)
					header = header.substring(2); // to avoid date typ indicator "D#"
				if (isCOlEditable && isGridEditable)
//					if (isNotAParentField)
//						grid.addEditColumn(new LocalDateRenderer<>(d -> d.getColLocalDate(colName), "dd/MM/yyyy")).text((item, newValue) -> colChanged(item,colName,newValue)).setHeader(header)
//						.setResizable(true).setSortProperty(colData[0]);
//					else
//						grid.addEditColumn(new LocalDateRenderer<>(d -> d.getColLocalDate(colName), "dd/MM/yyyy")).text((item, newValue) -> colChanged(item,colName,newValue)).setHeader(header)
//						.setResizable(true);
					System.err.println(" REVISAR ESTE CODIGO AL MIGRAR A 14 da error");
				else
					if (isNotAParentField)
						col = grid.addColumn(new LocalDateRenderer<>(d -> d.getColLocalDate(colName), "dd/MM/yyyy")).setHeader(header).setSortProperty(colData[0])
						.setResizable(true).setSortProperty(colData[0]);
					else
						col = grid.addColumn(new LocalDateRenderer<>(d -> d.getColLocalDate(colName), "dd/MM/yyyy")).setHeader(header).setSortProperty(colData[0])
						.setResizable(true);
						
				
//		grid.addColumn(d ->d.getCol(i)).setHeader(header).setResizable(true);
			} else if (isCurrency(header,colType)) {
				if (header.indexOf("#")>0)
					header = header.substring(2);
				if (isCOlEditable  && isGridEditable) 
					if (isNotAParentField)
					{
						col = grid.addEditColumn(d -> currencyFormatter.encode(currencyFormatter.getCents(d.getCol(colName)))).text((item, newValue) -> colChanged(item,colName,newValue)).setHeader(header)
						.setTextAlign(ColumnTextAlign.END).setResizable(true).setSortProperty(colData[0]);
					}
					else
					{
						col = grid.addEditColumn(d -> currencyFormatter.encode(currencyFormatter.getCents(d.getCol(colName)))).text((item, newValue) -> colChanged(item,colName,newValue)).setHeader(header)
						.setTextAlign(ColumnTextAlign.END).setResizable(true);
					}
				else
					if (isNotAParentField)
						{
						col = grid.addColumn(d -> currencyFormatter.encode(currencyFormatter.getCents(d.getCol(colName)))).setHeader(header)
						.setTextAlign(ColumnTextAlign.END).setResizable(true).setSortProperty(colData[0]);
						}
					else
						{
						col = grid.addColumn(d -> currencyFormatter.encode(currencyFormatter.getCents(d.getCol(colName)))).setHeader(header)
						.setTextAlign(ColumnTextAlign.END).setResizable(true);
						}

			}  else if (isBoolean(header,colType)) {
				if (header.indexOf("#")>0)
					header = header.substring(2);
				if (isCOlEditable  && isGridEditable) {
					col = grid.addEditColumn(d -> d.getColBoolean(colName)).checkbox((item, newValue) -> colChanged(item,colName,newValue)).setHeader(header);
//		V14			gridPro.addEditColumn(b -> b.isBoolean(), new IconRenderer<>(
//					        obj -> obj.isBoolean() ? 
//					                VaadinIcon.CHECK.create() : VaadinIcon.CLOSE.create()))
//					        .checkbox(Bean::setAtHome);
//					grid.addEditColumn(new IconRenderer<DynamicDBean>(obj -> {
//						if (obj.getColBoolean(colName)) {
//						return VaadinIcon.CHECK.create();
//						} else {
//						return VaadinIcon.CLOSE.create();
//						}
//						}, obj->"")).checkbox((item, newValue) -> colChanged(item,colName,newValue));
						 
//					if (isNotAParentField)
//					{
//						grid.addEditColumn(d -> currencyFormatter.encode(currencyFormatter.getCents(d.getCol(colName)))).text((item, newValue) -> colChanged(item,colName,newValue)).setHeader(header)
//						.setTextAlign(ColumnTextAlign.END).setResizable(true).setSortProperty(colData[0]);
//					}
//					else
//					{
//						grid.addEditColumn(d -> currencyFormatter.encode(currencyFormatter.getCents(d.getCol(colName)))).text((item, newValue) -> colChanged(item,colName,newValue)).setHeader(header)
//						.setTextAlign(ColumnTextAlign.END).setResizable(true);
					}
				else
					if (isNotAParentField)
						{
						col = grid.addColumn(d -> currencyFormatter.encode(currencyFormatter.getCents(d.getCol(colName)))).setHeader(header)
						.setTextAlign(ColumnTextAlign.END).setResizable(true).setSortProperty(colData[0]);
						}
					else
						{
						col = grid.addColumn(d -> currencyFormatter.encode(currencyFormatter.getCents(d.getCol(colName)))).setHeader(header)
						.setTextAlign(ColumnTextAlign.END).setResizable(true);
						}

			}
			else
				if ((isCOlEditable && isGridEditable))
					if (isNotAParentField)						
						col = grid.addEditColumn(d -> d.getCol(colName)).text((item, newValue) -> colChanged(item,colName,newValue)).setHeader(header).setResizable(true).setSortProperty(colData[0]);
					else
						col = grid.addEditColumn(d -> d.getCol(colName)).text((item, newValue) -> colChanged(item,colName,newValue)).setHeader(header).setResizable(true);				
				else if (isGridEditable && isCOlEditable == false ) 
				{
				//	 grid.addColumn(d -> d.getCol(colName)).setHeader(header).setResizable(true).getElement());
				//	 grid.addColumn(d -> d.getCol(colName)).setHeader(header).setResizable(true);
					//	 Grid<String> grid = new Grid<>();
//					 grid.addColumn(d-> new ComponentRenderer<Label,String>(c->{
//					 Label l = new Label(d.getCol(colName));
//					 l.getElement().addEventListener("click", ev->System.out.println("clicked"));
//					 return l;
//					 })).setResizable(true);;
					if (isNotAParentField)
						{
						col = grid.addColumn(d -> d.getCol(colName)).setHeader(header).setResizable(true).setSortProperty(colData[0]) ;
						}
					else {
						col = grid.addColumn(new ComponentRenderer<Label,DynamicDBean>(item->{
							 Label l = new Label("Buscar....");
							 if (item.getCol(colName) != null && item.getCol(colName).isEmpty() == false)
								 l = new Label(item.getCol(colName));
							 l.getElement().addEventListener("click", ev->pickParent(colName, item));
							 return l;
							 })).setResizable(true).setHeader(header).setSortProperty(colData[0]);
						
					}
				//	 grid.setItems("1", "2", "3", "4", "5", "6", "7", "8", "9", "10");
		//			 add(grid);
				}	
				else
				{
					if (isNotAParentField)
						col = grid.addColumn(d -> d.getCol(colName)).setHeader(header).setResizable(true).setSortProperty(colData[0]) ;
					else 
						col = grid.addColumn(d -> d.getCol(colName)).setHeader(header).setResizable(true) ;
				}
		}
		return col;
	}





// ****** the isType, are prefix that determines the type ( D# = data; C# = currency) this prefix are fill in the labels names, by example ResourceBundle...

private Object pickParent(String colName, DynamicDBean item) {
	System.out.println("clicked "+colName + item.getCol(colName));
//	return null;
	try {
	DynamicGridForPick dynamicGridForPick = new DynamicGridForPick(); 
	String queryFormForPickClassName = null;
//	Object queryFormClassName = PACKAGE_VIEWS+queryParameters.getParameters().get("queryFormClassName").get(0);
	DynamicDBean currentRow = item;
	String filter="tableName='"+currentRow.getResourceName()+"'%20AND%20FieldNameInUI='"+colName+"'";
	String parentResource = "";
	
	JsonNode rowsList = JSonClient.get("FieldTemplate",filter,true,"metadata","1");
	for (JsonNode eachRow : rowsList)  {
		if (eachRow.size() > 0)
		{
			parentResource = eachRow.get("parentResource").asText();
			pickMapFields =  eachRow.get("pickMapFields").asText();
			queryFormForPickClassName =  eachRow.get("queryFormForPickClassName").asText();
		}
	}
	queryFormForPickClassName = PACKAGE_VIEWS+queryFormForPickClassName;
	DynamicViewGrid grid = dynamicGridForPick.getGrid();
	Class<?> dynamicQuery = Class.forName(queryFormForPickClassName);
	Object queryForm = dynamicQuery.newInstance();
	Method setGrid = dynamicQuery.getMethod("setGrid", new Class[] {coop.intergal.ui.views.DynamicViewGrid.class} );
	setGrid.invoke(queryForm,grid);
	dynamicGridForPick.getDivQuery().add((Component)queryForm);

	
	grid.setButtonsRowVisible(false);
	grid.setResourceName(parentResource);
	grid.setupGrid();
//		subDynamicViewGrid.getElement().getStyle().set("height","100%");
//	subDynamicViewGrid.setResourceName(resourceSubGrid);
//	if (resourceSubGrid.indexOf(".")> -1)
//		subDynamicViewGrid.setFilter(componFKFilter(bean, resourceSubGrid));
//	subDynamicViewGrid.setupGrid();
//	dynamicGridForPick.setRowsColList(currentRow.getRowsColList());
	dialogForPick = new Dialog();
	dialogForPick.setWidth(AppConst.DEFAULT_PICK_DIALOG_WITHD);
	dialogForPick.setHeight(AppConst.DEFAULT_PICK_DIALOG_HEIGHT);
	dialogForPick.add(dynamicGridForPick);
	dialogForPick.open();
	dynamicGridForPick.addAcceptPickListener(e -> fillDataForPickAndAccept(grid.getGrid().getSelectedItems(),dialogForPick,currentRow, pickMapFields ));

	
	} catch (Exception e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	
	return null;
}
	
	private Object fillDataForPickAndAccept(Set<DynamicDBean> seletedRows, Dialog dialogForPick2, DynamicDBean currentRow, String pickMapFields) {
		StringTokenizer tokens = new StringTokenizer(pickMapFields,"#");
		DynamicDBean seletedParentRow = seletedRows.iterator().next();
		while (tokens.hasMoreElements())
		{
			String eachFieldMap = tokens.nextToken();
			int idxSeparator = eachFieldMap.indexOf(";");
			String childField = eachFieldMap.substring(0, idxSeparator);
			String parentField = eachFieldMap.substring(idxSeparator+1);
			currentRow.setCol(seletedParentRow.getCol(parentField), childField);						
		}
		dataProvider.refresh(currentRow);
//		binder.setBean(currentRow);
		colChanged(currentRow, null, "");
		dialogForPick.close();
		return null;
	}


private boolean isCurrency(String header, String colType) {
	if (header.startsWith("C#")) // when there is nmot the type defined in FiledTemplate it can be defined in the name with the prefix "d#"
		return true; 
	if (colType.equals("3"))
		return true;
	return false;
}


private boolean isDate(String header, String colType) {
	if (header.startsWith("D#")) // when there is nmot the type defined in FiledTemplate it can be defined in the name with the prefix "d#"
		return true; 
	if (colType.equals("1"))
		return true;
	return false;
}
private boolean isBoolean(String header, String colType) {
	if (colType.equals("4"))
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
//	PROTECTED CRUDENTITYPRESENTER<DYNAMICDBEAN> GETPRESENTER() {
//		// TODO AUTO-GENERATED METHOD STUB
//		RETURN NULL;
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
			setVisibleRowData(true);
			if (bean.isReadOnly()) // when a bean is mark as readOnly buttons for save are hide, to mark as read only add row.readONly=true to the event of the resource in LAC 
				buttonsForm.setVisible(false);
			selectedRow = bean;
			keepRowBeforChanges = new DynamicDBean(); 
			keepRowBeforChanges = RestData.copyDatabean(bean);
//			Class<?> dynamicForm = Class.forName("coop.intergal.tys.ui.views.DynamicForm");
			Class<?> dynamicForm = Class.forName(displayFormClassName);//"coop.intergal.tys.ui.views.comprasyventas.compras.PedidoProveedorForm");
			display = dynamicForm.newInstance();
			Method setRowsColList = dynamicForm.getMethod("setRowsColList", new Class[] {java.util.ArrayList.class} );
			Method setBinder = dynamicForm.getMethod("setBinder", new Class[] {com.vaadin.flow.data.binder.Binder.class} );
			setBean = dynamicForm.getMethod("setBean", new Class[] {coop.intergal.vaadin.rest.utils.DynamicDBean.class} );
			setRowsColList.invoke(display,rowsColListGrid);
			setBinder.invoke(display,binder);
			setBean.invoke(display,bean);
			divDisplay.removeAll();
			divDisplay.add((Component)display);
			String resourceSubGrid = extractResourceSubGrid(bean);//"CR-ped_proveed_cab.List-ped_proveed_lin"; // TODO send by param
			divSubGrid.removeAll();
			if (resourceSubGrid != null)
			{
				DynamicViewGrid subDynamicViewGrid = new DynamicViewGrid();
				subDynamicViewGrid.setButtonsRowVisible(true);
	//			subDynamicViewGrid.getElement().getStyle().set("height","100%");
				subDynamicViewGrid.setResourceName(resourceSubGrid);
				if (resourceSubGrid.indexOf(".")> -1)
					subDynamicViewGrid.setFilter(componFKFilter(bean, resourceSubGrid));
				subDynamicViewGrid.setupGrid(true);
				subDynamicViewGrid.setParentRow(selectedRow);
				subDynamicViewGrid.setDisplayParent(display);
				subDynamicViewGrid.setBeanParent(setBean);
				divSubGrid.add(subDynamicViewGrid );
			}
			else
			{
				DynamicViewGrid subDynamicViewGrid = new DynamicViewGrid();
				subDynamicViewGrid.setButtonsRowVisible(true);
				divSubGrid.add(subDynamicViewGrid );
			}
	


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
	private void setBeanParent(Method setBean2) {
		this.setBeanParent = setBean2;
		
	}

	private void setDisplayParent(Object display2) {
		this.display = display2;
		
	}

	private void setParentRow(DynamicDBean parentRow) {
		this.parentRow = parentRow;
		
	}

	public void setButtonsRowVisible(boolean b) {
		newRow.setVisible(b);
		deleteRow.setVisible(b);
		
	}

	private void insertBean() {
		try {
	//		selectedRow = new bean;
			keepRowBeforChanges = new DynamicDBean(); 
			DynamicDBean bean = new DynamicDBean(); 
			
			bean.setResourceName(resourceName);
			bean.setRowsColList(rowsColListGrid);
			bean.setPreConfParam(AppConst.PRE_CONF_PARAM);
			fillDefaultValues(bean);
			selectedRow = bean;
			keepRowBeforChanges = RestData.copyDatabean(bean);
//			Class<?> dynamicForm = Class.forName("coop.intergal.tys.ui.views.DynamicForm");
			Class<?> dynamicForm = Class.forName(displayFormClassName);//"coop.intergal.tys.ui.views.comprasyventas.compras.PedidoProveedorForm");
			display = dynamicForm.newInstance();
			Method setRowsColList = dynamicForm.getMethod("setRowsColList", new Class[] {java.util.ArrayList.class} );
			Method setBinder = dynamicForm.getMethod("setBinder", new Class[] {com.vaadin.flow.data.binder.Binder.class} );
			setBean = dynamicForm.getMethod("setBean", new Class[] {coop.intergal.vaadin.rest.utils.DynamicDBean.class} );
			setRowsColList.invoke(display,rowsColListGrid);
			setBinder.invoke(display,binder);
			setBean.invoke(display,bean);
			divDisplay.removeAll();
			divDisplay.add((Component)display);
//			String resourceSubGrid = extractResourceSubGrid(bean);//"CR-ped_proveed_cab.List-ped_proveed_lin"; // TODO send by param
			divSubGrid.removeAll();
//			if (resourceSubGrid != null)
//			{
//				DynamicViewGrid subDynamicViewGrid = new DynamicViewGrid();
//				subDynamicViewGrid.getElement().getStyle().set("height","200px");
//				subDynamicViewGrid.setResourceName(resourceSubGrid);
//				if (resourceSubGrid.indexOf(".")> -1)
//					subDynamicViewGrid.setFilter(componFKFilter(bean, resourceSubGrid));
//				subDynamicViewGrid.setupGrid();
//				divSubGrid.add(subDynamicViewGrid );
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
	}
	private void fillDefaultValues(DynamicDBean bean) {
		ArrayList<String[]> rowsColList = bean.getRowsColList();
//		rowsColList.
		
		Field[] fields = bean.getClass().getDeclaredFields();
		int i=0;
	//	JsonNode eachRow =  lTxsumary.get(0); // VER EN TAbeEL como gestiona que el resultado traiga varias tablas
	//	dB.setRowJSon(eachRow); 
		for(Field field : fields )  
		{
//			field.setInt(eachRow.get("code_customer").asInt());
			try {
				if (field.getName().equals("col"+i) && i < rowsColList.size())
					{
					field.setAccessible(true);
	//				String colName = getColName(rowsColList,i);
					String defaultValue = getDefaultValue(rowsColList,i);
					if (defaultValue != null && ! defaultValue.equals("null") && defaultValue.length() > 0)
						field.set(bean, defaultValue);
					i++;
					}
			} catch (IllegalArgumentException | IllegalAccessException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			if (i>rowsColList.size()) 
				break;
		}
	}

	private String getDefaultValue(ArrayList<String[]> rowsColList, int i) {
		String colNameInCL = rowsColList.get(i)[2];
		if ( colNameInCL.equals("col"+i) || colNameInCL.isEmpty() ) // if colinIU = col... then return colName 
			return rowsColList.get(i)[5];
		else // otherwise it searchs
		{
			for (String[] row : rowsColList) // search for col.. to get his column name
			{
				if (row[2].equals("col"+i))
					return row[5];
			}
				
			return "null";
		}
	}

	
private String getColName(ArrayList<String[]> rowsColList, int i) { // normally the col.. is syncronice with i secuence, but is rowColList have some fields not in natural position then must be search the name in other way
	String colNameInCL = rowsColList.get(i)[2];
	if ( colNameInCL.equals("col"+i) || colNameInCL.isEmpty() ) // if colinIU = col... then return colName 
		return rowsColList.get(i)[0];
	else // otherwise it searchs
	{
		for (String[] row : rowsColList) // search for col.. to get his column name
		{
			if (row[2].equals("col"+i))
				return row[0];
		}
			
		return "null";
	}
}

	private String extractResourceSubGrid(DynamicDBean bean) {
		// TODO Auto-generated method stub CR-entradas_cab.List-entradas_lin/
		String rowJson = bean.getRowJSon().toString();
		if (bean.getRowJSon().get("tableName") != null)
			{
			if (bean.getRowJSon().get("tableName").asText().equals("CR-FormTemplate.List-FieldTemplate")) // // the resource is the formTemplate or FieldTemplate, to configure themselves, then there is a conflict with the names 
				return bean.getRowJSon().get("tableName").asText();
			}
		int idxResourceSubResource = rowJson.indexOf(bean.getResourceName() +".");
		if (idxResourceSubResource > -1)
		{
			int idxEndSubreourceName = rowJson.substring(idxResourceSubResource).indexOf("/")+idxResourceSubResource;
			String pathSubreourceName = null;
			if (idxResourceSubResource > -1 && idxEndSubreourceName >-1)
				pathSubreourceName = rowJson.substring(idxResourceSubResource, idxEndSubreourceName);
			return pathSubreourceName;
		}
		else
		{
			idxResourceSubResource = rowJson.indexOf("List-");
			int idxEndSubreourceName = rowJson.substring(idxResourceSubResource).indexOf("\":[")+idxResourceSubResource;
			String pathSubreourceName = rowJson.substring(idxResourceSubResource, idxEndSubreourceName);
			return bean.getResourceName()+"."+pathSubreourceName;
		}
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
			componFilter = componFilter + fKfieldName + "='" + parentValue + "'%20and%20";
			lengthFKfilter = lengthFKfilter - idXMark;
			fKfilter = fKfilter.substring(idXMark+1);
			
		}
		componFilter = componFilter.substring(0, componFilter.length()-9); // to delete last and
		return componFilter;
	}

	@Override
	public void beforeEnter(BeforeEnterEvent event) {
		setButtonsRowVisible(false); // only subgrid have newRow button, and they don't have beforeEnter event
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

	public void setLayout(DynamicGridDisplay dynamicGridDisplay) {
		this.layout = dynamicGridDisplay;
		
	}

//	public void setGridSplitDisplay(SplitLayout gridSplitDisplay) {
//		this.gridSplitDisplay = gridSplitDisplay;
//		
//	}

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

	public DynamicDBean getSelectedRow() {
		return selectedRow;
	}

	public void setSelectedRow(DynamicDBean selectedRow) {
		this.selectedRow = selectedRow;
	}

	public String getDisplayFormClassName() {
		return displayFormClassName;
	}

	public void setDisplayFormClassName(String displayFormClassName) {
		this.displayFormClassName = displayFormClassName;
	}

	public String getResourceSubGrid() {
		return resourceSubGrid;
	}

	public void setResourceSubGrid(String resourceSubGrid) {
		this.resourceSubGrid = resourceSubGrid;
	}

	public Object saveSelectedRow() {
//		System.out.println("DynamicViewGrid.saveSelectedRow() --->" + selectedRow.getRowJSon().toString());
		beansToSaveAndRefresh.clear();
		beansToSaveAndRefresh.put(selectedRow.getResourceName(), selectedRow);
		dataProvider.save(selectedRow.getResourceName(), beansToSaveAndRefresh);	
//		((Binder<DynamicDBean>) display).setBean(selectedRow);
		keepRowBeforChanges =  RestData.copyDatabean(selectedRow);
		try {
			setBean.invoke(display,selectedRow);
		} catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
			e.printStackTrace();
		}
		showBean(selectedRow);
		return null;
	}
	private Object colChanged(DynamicDBean item, String colName, String newValue) {
//		System.out.println("DynamicViewGrid.colChanged()..."+  "...colName "+ colName +"...newValue "+ newValue+"...item " + item.getRowJSon() + "...newValue "+ newValue);
		if (colName != null) // is call by the acceptPick
			item.setCol(newValue, colName );
		beansToSaveAndRefresh.clear();	
		beansToSaveAndRefresh.put(item.getResourceName(), item);
		if (parentRow != null)
			beansToSaveAndRefresh.put(parentRow.getResourceName(), parentRow);
		if (dataProvider.getHasNewRow() == false)
		{
			isInsertingALine = false;
			saveRowGridIfNotInserting(beansToSaveAndRefresh,item.getResourceName() );
		}
		rowIsInserted = item;
		return null;
	}
	private Object colChanged(DynamicDBean item, String colName, Boolean newValue) {
		
		String newValueStr = "false";
		if (newValue)
			newValueStr = "true";
		colChanged(item, colName, newValueStr);
	return null;
	}
	
	public Object saveRowGridIfNotInserting(Hashtable<String, DynamicDBean> beansToSaveAndRefresh2, String beanTobeSave) {
//		System.out.println("DynamicViewGrid.saveSelectedRow() --->" + row.getRowJSon().toString());
		if (dataProvider.getHasNewRow() == true || isInsertingALine == false)
		{
			dataProvider.save(beanTobeSave, beansToSaveAndRefresh2);	 
			dataProvider.setHasNewRow(false);
			try {
				setBeanParent.invoke(display,parentRow);
			} catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
//		((Binder<DynamicDBean>) display).setBean(selectedRow);
//		try {
//			setBean.invoke(display,selectedRow);
//		} catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//		showBean(selectedRow);
		return null;
	}
	public Object deleteRowInGrid(Hashtable<String, DynamicDBean> beansToSaveAndRefresh2, String beanTobeDelete) {
//		System.out.println("DynamicViewGrid.saveSelectedRow() --->" + row.getRowJSon().toString());
			dataProvider.delete(beanTobeDelete, beansToSaveAndRefresh2);	 
			dataProvider.setHasNewRow(false);
			try {
				setBeanParent.invoke(display,parentRow);
			} catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		return null;
	}

	public Object undoSelectedRow() {
		System.out.println("DynamicViewGrid.undoSelectedRow() --->" + keepRowBeforChanges.getRowJSon().toString());
//		dataProvider.save(selectedRow);	
//		((Binder<DynamicDBean>) display).setBean(selectedRow);
//		dataProvider.refresh(selectedRow);	
		selectedRow = RestData.copyDatabean(keepRowBeforChanges);
		dataProvider.setHasNewRow(true);
		try {
			setBean.invoke(display,selectedRow);
		} catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
//		showBean(selectedRow);
		return null;
	}

	public Object insertANewRow() {
		// TODO Auto-generated method stub
//		dataProvider.insertANewRow();	
		insertBean();
		return null;
	}
	private Object insertBeanInList() {
		System.out.println("DynamicViewGrid.insertBeanInList()");
		if (!dataProvider.getHasNewRow() ) // in this case the row is Inserted
			{
			isInsertingALine = true;
			dataProvider.setHasNewRow(true);
			grid.setDataProvider(dataProvider);
			dataProvider.refreshAll();
			newRow.setText("salvar linea");
			}
		else if (rowIsInserted!= null)// the row is save
		{ 			
			beansToSaveAndRefresh.clear();
			beansToSaveAndRefresh.put(rowIsInserted.getResourceName(), rowIsInserted);
			beansToSaveAndRefresh.put(parentRow.getResourceName(), parentRow);
			saveRowGridIfNotInserting(beansToSaveAndRefresh, rowIsInserted.getResourceName());
			if (beansToSaveAndRefresh.containsKey("ERROR") == false) 
			{
				dataProvider.setHasNewRow(false);
				try {
	//			RestData.refresh(selectedRow);
	//			parentRow = RestData.getOneRow(parentRow.getResourceName(), "N_PEDIDO=1", preConfParam, parentRow.getRowsColList());
	//			dataProvider.refresh(parentRow);
					setBeanParent.invoke(display,parentRow);
				} catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				}
				newRow.setText("nueva linea");
//				isInError = false;
			}
			else // has error is waiting for a new save
			{
				dataProvider.setHasNewRow(true);
//				isInError = true;
			}
		}
		return null;
	}

	public Object DeleteARow() {
//		System.out.println("DynamicViewGrid.saveSelectedRow() --->" + selectedRow.getRowJSon().toString());
		beansToSaveAndRefresh.clear();
		beansToSaveAndRefresh.put(selectedRow.getResourceName(), selectedRow);
		dataProvider.delete(selectedRow.getResourceName(), beansToSaveAndRefresh);	
//		((Binder<DynamicDBean>) display).setBean(selectedRow);
//		keepRowBeforChanges =  RestData.copyDatabean(selectedRow);
//		try {
//			setBean.invoke(display,selectedRow);
		if (beansToSaveAndRefresh.containsKey("ERROR") == false)
		{
			setVisibleRowData(false);
		}	
//		} catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
//			e.printStackTrace();
//		}
//		showBean(selectedRow);
		return null;
	}

	private void setVisibleRowData(boolean b) {
		divDisplay.setVisible(b);
		divSubGrid.setVisible(b);
		buttonsForm.setVisible(b);
		
	}

	public void setButtonsForm(FormButtonsBar buttons) {
		this.buttonsForm = buttons;
		
	}
	

//	@Override
//	protected CrudEntityPresenter<DynamicDBean> getPresenter() {
//		// TODO Auto-generated method stub
//		return null;
//	}
}
