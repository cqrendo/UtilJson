package coop.intergal.ui.views;

import static coop.intergal.AppConst.PACKAGE_VIEWS;
import static coop.intergal.AppConst.PAGE_PRODUCTS;
import static coop.intergal.AppConst.STYLES_CSS;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.InetAddress;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.StringTokenizer;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.vaadin.haijian.Exporter;
import org.vaadin.olli.FileDownloadWrapper;

import com.fasterxml.jackson.databind.JsonNode;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.Tag;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.dependency.CssImport;
import com.vaadin.flow.component.dependency.JsModule;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.grid.Grid.Column;
import com.vaadin.flow.component.grid.contextmenu.GridContextMenu;
import com.vaadin.flow.component.gridpro.GridPro;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.html.NativeButton;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.notification.Notification.Position;
import com.vaadin.flow.component.polymertemplate.Id;
import com.vaadin.flow.component.polymertemplate.PolymerTemplate;
import com.vaadin.flow.component.splitlayout.SplitLayout;
import com.vaadin.flow.component.splitlayout.SplitLayout.Orientation;
import com.vaadin.flow.component.tabs.Tab;
import com.vaadin.flow.component.tabs.Tabs;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.router.AfterNavigationEvent;
import com.vaadin.flow.router.AfterNavigationObserver;
import com.vaadin.flow.router.BeforeEnterEvent;
import com.vaadin.flow.router.BeforeEnterObserver;
import com.vaadin.flow.router.HasDynamicTitle;
import com.vaadin.flow.router.QueryParameters;
import com.vaadin.flow.server.StreamResource;
import com.vaadin.flow.templatemodel.TemplateModel;

import coop.intergal.AppConst;
import coop.intergal.espresso.presutec.utils.JSonClient;
import coop.intergal.metadata.ui.views.dev.lac.FieldTemplateComboRelatedForPick;
import coop.intergal.ui.components.FormButtonsBar;
import coop.intergal.ui.util.GenericClassForMethods;
import coop.intergal.ui.util.UtilSessionData;
import coop.intergal.ui.utils.ProcessParams;
import coop.intergal.ui.utils.UiComponentsUtils;
import coop.intergal.ui.utils.converters.CurrencyFormatter;
import coop.intergal.ui.utils.converters.DecimalFormatter;
import coop.intergal.vaadin.rest.utils.DataService;
import coop.intergal.vaadin.rest.utils.DdbDataBackEndProvider;
import coop.intergal.vaadin.rest.utils.DynamicDBean;
import coop.intergal.vaadin.rest.utils.RestData;


//@Tag("dynamic-view-grid")
@Tag("dynamic-grid")
@JsModule("./src/views/generic/dynamic-grid.js")
//@CssImport(
//	    themeFor = "vaadin-grid",
//	    value = "./styles/dynamic-grid-row-background-color.css")
//@Route(value = PAGE_DYNAMIC)//, layout = MainView.class)
//@PageTitle(AppConst.TITLE_PRODUCTS)
//@Secured(Role.ADMIN)
//public class DynamicViewGrid extends CrudViewREST<DynamicDBean,TemplateModel> implements BeforeEnterObserver,AfterNavigationObserver, HasDynamicTitle  {
@CssImport(value = STYLES_CSS)
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
	private Hashtable<String, String[]> resourceAndSubresources = new Hashtable<String, String[]>(); // to send DynamicDBean to be save and refresh, the name of the one to be save is send in another param
	Map<String, Dialog> allDialogs = new HashMap<>();
	//	Dialog dialogForShow = new Dialog();
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

	public DynamicViewGrid() {
		super();
//		grid.addSelectionListener(e -> {
//			if (e.getFirstSelectedItem().isPresent())
//				selectedRow =(DynamicDBean)e.getFirstSelectedItem().get();
//				System.out.println("Registro selecionado " + selectedRow.getCol0());
//
//			});
//		setupGrid();
		
	}
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
	private DecimalFormatter decimalFormatter = new DecimalFormatter();

	private String resourceName;
	private String title;
	private String filter;
//	private DynamicForm display;
	private DynamicQryGridDisplay layout;
//	private SplitLayout gridSplitDisplay;
//	private Div divDisplay;
	private Div divDisplay;
	private String className = "C1";
	private Div divSubGrid;
	private DdbDataBackEndProvider dataProvider;
	private DynamicDBean selectedRow;
	private Object display;
	private Method setBean;
	@Id("itemButtons")
	private Div itemButtons;
	private DynamicDBean keepRowBeforChanges;
	@Id("newRow")
	private Button newRow;
	private DynamicDBean rowIsInserted;
	public DynamicDBean getRowIsInserted() {
		return rowIsInserted;
	}

	public void setRowIsInserted(DynamicDBean rowIsInserted) {
		this.rowIsInserted = rowIsInserted;
	}

	private DynamicDBean parentRow;
	private Method setBeanParent;
public Div getDivDisplay() {
		return divDisplay;
	}

	public void setDivDisplay(Div divDisplay) {
		this.divDisplay = divDisplay;
	}

	//	private boolean isInError = false;
	private boolean isInsertingALine = false;
private Dialog dialogForPick;
private String pickMapFields;
@Id("deleteRow")
private Button deleteRow;
private FormButtonsBar buttonsForm;
private boolean hasSideDisplay = true;
private boolean autoSaveGrid = true;
private boolean cache = UtilSessionData.getCache();
private Object divInDisplay;
@Id("divExporter")
private Div divExporter;
private Boolean isResourceReadOnly;
private int position = 20;
//private Dialog dialogForShow;
//private Dialog dialogForShow;
//private Button bCloseDialog = new Button ("X", e -> dialogForShow.close());
//private String openIds = "";
//private DdbDataBackEndProvider dataProviderpopup;
//private String displayFormClassNamePopup;
@Id("showHideQuery")
private Button showHideQuery;


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

public boolean isCache() {
	return cache;
}

public void setCache(boolean cache) {
	this.cache = cache;
}

public void setupGrid() { // by Default the grid is not editable, to be editable, call setupGrid(true)
	setupGrid(false, false);
	
}
public void setupGrid(Boolean isGridEditable, Boolean isGridEditableon) {
	setupGrid(isGridEditable, isGridEditable, false);
	
}


	public void setupGrid(Boolean isGridEditable, Boolean hasExportButton, Boolean hasShowQueryButton ) {
	
	//	grid.scrollTo(1); 
	//	grid.getDataProvider().
	//	DdbDataProvider dataProvider = new DdbDataProvider();
//		Grid gridx = new Grid();
		grid.addSelectionListener(e -> {
			if (e.getFirstSelectedItem().isPresent())
				selectedRow =(DynamicDBean)e.getFirstSelectedItem().get();
				System.out.println("Registro seleccionado " + selectedRow.getCol0());
				methodForRowSelected(selectedRow); 
			});
		
//		grid.getElement().executeJs("setTimeout(function(){var item=this._cache.getItemForIndex(0); item&&this.$connector.doSelection([item]);}.bind(this))"); 
// FOR Autoselect		grid.getElement().executeJs("setTimeout(function(){this.querySelector('vaadin-grid-cell-content').click();}.bind(this))");
		grid.addCellEditStartedListener(ev->grid.select(ev.getItem()));
		GridContextMenu<DynamicDBean> contextMenu = new GridContextMenu<>(grid);
//		GridMenuItem<DynamicDBean> opcion = contextMenu.addItem("Opcion");
//		contextMenu.addItem("Remove", e -> {
//		    e.getItem().ifPresent(dB -> {
//		    System.out.println("context menu " + dB.getCol0());});
//		});
		dataProvider = new DdbDataBackEndProvider();
		dataProvider.setPreConfParam(UtilSessionData.getCompanyYear()+AppConst.PRE_CONF_PARAM);
		dataProvider.setResourceName(getResourceName());
		dataProvider.setFilter(getFilter());
//		grid = new Grid<>(DynamicDBean.class); 
//		grid.setPageSize(333);
		grid.removeAllColumns();
		grid.setDataProvider(dataProvider);
		grid.setEnterNextRow(true);
		grid.setMultiSort(true);
//		grid.setHeightByRows(true);  /// Is you use it breaks pagination 
        grid.getElement().executeJs("this.addEventListener('keydown', function(e) {\r\n" + "  let delta = 0;\r\n"
        		+ "  if (e.key === 'ArrowUp') {\r\n" + "    delta = -1;\r\n"
        		+ "  } else if (e.key === 'ArrowDown') {\r\n" + "    delta = 1;\r\n" + "  }\r\n"
        		+ "  if (this.selectedItems[0] && delta) {\r\n"
        		+ "    const currentIndex = +this._cache.getCacheAndIndexByKey(this.selectedItems[0].key).scaledIndex;\r\n"
        		+ "    const itemToSelect = this._cache.getItemForIndex(currentIndex + delta)\r\n"
        		+ "    itemToSelect && this.$connector.doSelection([itemToSelect], true);\r\n" + "  }\r\n"
        		+ "}.bind(this));");
//        grid.setClassNameGenerator(row-> getRowStyleName(row));
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
		//	Column<DynamicDBean> col = addFormatedColumn(i, isGridEditable);
			GeneratedUtil generatedUtil = new GeneratedUtil();
			generatedUtil.setGrid(this);
		//	generatedUtil.setDivSubGrid(divSubGrid); // to run methods for buttons
			Column<DynamicDBean> col = generatedUtil.addFormatedColumn(i, rowsColListGrid, this, grid, isGridEditable, itemButtons);
			if (col != null)
				col.setAutoWidth(true);
		}
//		ExporterOption exporterOption = new ExporterOption();
//		exporterOption.getColumnOption("col0").columnName("My Name").toUpperCase();
//		int nCols = list.getRowsColList().size();
//		int i = 1;
//		while (i < nCols)
//		{
//			exporterOption.getColumnOption("col"+i).columnName(TranslateResource.getFieldLocale(list.getRowsColList().get(i), preConfParam));
//			i++;
//		}
	//	Anchor anchor = new Anchor(new StreamResource("my-excel.xlsx", Exporter.exportAsExcel(grid)), "Download As Excel");
        if (hasShowQueryButton)
        {
        	showHideQuery.addClickListener(e -> showHideQuery());
        }
        else
        {
        	showHideQuery.setVisible(false);
        }
		if (hasExportButton && grid.getColumns().isEmpty() == false)
        {
     //   	export.setVisible(true);
        	divExporter.removeAll();
        	Button b = new Button(new Icon(VaadinIcon.FILE_TABLE));
        	b.addThemeName("small");
        	
        	FileDownloadWrapper buttonWrapper = new FileDownloadWrapper(
            new StreamResource("export.xls", Exporter.exportAsExcel(grid)));
        	buttonWrapper.wrapComponent(b);
        	divExporter.add(buttonWrapper);  
        }
        else
        {
  //      	export.setVisible(false);
        }
//		itemButtons.add(anchor);
//		grid.getColumns().forEach(column -> column.setAutoWidth(true));

}


private Object showHideQuery() {
		if (layout.getDivQuery().isVisible())
			layout.getDivQuery().setVisible(false);
		else
			layout.getDivQuery().setVisible(true);
		return null;
	}

private void methodForRowSelected(DynamicDBean selectedRow2) {
		
		String method = selectedRow2.getMethodForRowSelected();
		if (method != null)
		{
			runMethodFor(method, selectedRow2);
		}
		else
			System.out.println("DynamicViewGrid.methodForRowSelected() NOT method assigned");
		}
		
	private void runMethodFor(String methodName, DynamicDBean selectedRow2) {
		System.out.println("method to run "+ methodName);
//		Class<?> dynamicQuery;
		try {
			Class<?> classForMethods = Class.forName(AppConst.CLASS_FOR_METHODS);
			Object oClassForMethods = classForMethods.newInstance();
			Method method = classForMethods.getMethod(methodName, new Class[] {coop.intergal.vaadin.rest.utils.DynamicDBean.class, coop.intergal.ui.views.DynamicViewGrid.class} );
//			this.getParent().get().getParent().get().getParent().get().getParent().get().getParent().get().getChildren().findFirst();
//			UI.getCurrent().getChildren().findFirst();
			method.invoke(oClassForMethods,selectedRow2, this);
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InstantiationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NoSuchMethodException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SecurityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}


	
}

	

private String getRowStyleName(DynamicDBean row) {
		String valueStr = row.getCol0(); 
		int value = 0;
		if (valueStr != null || valueStr.equals("null") == false )
		{   
			value = new Integer(row.getCol0());
			if (value > 5 )
				return "warn";
		}	
		return null;
	}

private Object deleteBeanFromList() {
	System.out.println("DynamicViewGrid.deleteBeanFromList()");
	if (grid.getSelectedItems().size() == 0)
		DataService.get().showError("Debe seleccionar una linea para poder eliminarla");
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





// ****** the isType, are prefix that determines the type ( D# = data; C# = currency) this prefix are fill in the labels names, by example ResourceBundle...

	public Object pickParentComboTwinFormT (String colName, DynamicDBean item) {
		System.out.println("clicked "+colName + item.getCol(colName));
//		return null;
		if (item.getCol10() == null || item.getCol10().isEmpty())
		{
			showError("campo \"recurso Padre\" sin rellenar");
			return null;
		}
		try {
		FieldTemplateComboRelatedForPick fieldTemplateComboRelatedForPick = new FieldTemplateComboRelatedForPick(item.getCol6(),item.getCol10()); //col6 = tableName (ChildReource) col19=parentResource
		String queryFormForPickClassName = null;
//		Object queryFormClassName = PACKAGE_VIEWS+queryParameters.getParameters().get("queryFormClassName").get(0);
		DynamicDBean currentRow = item;
		String filter="tableName='"+currentRow.getResourceName()+"'%20AND%20FieldNameInUI='"+colName+"'";
		String parentResource = "";
		
		JsonNode rowsList = JSonClient.get("FieldTemplate",filter,true,AppConst.PRE_CONF_PARAM_METADATA,"1");
		for (JsonNode eachRow : rowsList)  {
			if (eachRow.size() > 0)
			{
				parentResource = eachRow.get("parentResource").asText();
				pickMapFields =  eachRow.get("pickMapFields").asText();
				queryFormForPickClassName =  eachRow.get("queryFormForPickClassName").asText();
			}
		}
		queryFormForPickClassName = PACKAGE_VIEWS+queryFormForPickClassName;
//		DynamicViewGrid grid = dynamicGridForPick.getGrid();
//		Class<?> dynamicQuery = Class.forName(queryFormForPickClassName);
//		Object queryForm = dynamicQuery.newInstance();
//		Method setGrid = dynamicQuery.getMethod("setGrid", new Class[] {coop.intergal.ui.views.DynamicViewGrid.class} );
//		setGrid.invoke(queryForm,grid);
//		dynamicGridForPick.getDivQuery().add((Component)queryForm);

		
//		grid.setButtonsRowVisible(false);
//		grid.setResourceName(parentResource);
//		grid.setupGrid();
//			subDynamicViewGrid.getElement().getStyle().set("height","100%");
//		subDynamicViewGrid.setResourceName(resourceSubGrid);
//		if (resourceSubGrid.indexOf(".")> -1)
//			subDynamicViewGrid.setFilter(componFKFilter(bean, resourceSubGrid));
//		subDynamicViewGrid.setupGrid();
//		dynamicGridForPick.setRowsColList(currentRow.getRowsColList());
		dialogForPick = new Dialog();
		dialogForPick.setWidth(AppConst.DEFAULT_PICK_DIALOG_WITHD);
		dialogForPick.setHeight(AppConst.DEFAULT_PICK_DIALOG_HEIGHT);
		dialogForPick.add(fieldTemplateComboRelatedForPick);
		dialogForPick.open();
//		String mapedCols = fieldTemplateComboRelatedForPick.getMapedCols();
		fieldTemplateComboRelatedForPick.addAcceptPickListener(e -> fillDataForPickAndAcceptComboMap(e.getSource(), dialogForPick,currentRow ));

		
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return null;
	}
private Object fillDataForPickAndAcceptComboMap(FieldTemplateComboRelatedForPick fieldTemplateComboRelatedForPick, Dialog dialogForPick2, DynamicDBean currentRow) {

	String mapedCols = fieldTemplateComboRelatedForPick.getMapedCols();
	currentRow.setCol(mapedCols, "col11");	//	col11 =pickMapFields	
	if (currentRow.getCol12() == null || currentRow.getCol12().isEmpty())
		currentRow.setCol12("coop.intergal.ui.views.GeneratedQuery"); // col12 = queryFormForPickClassName
	dataProvider.refresh(currentRow);
//	binder.setBean(currentRow);
	colChanged(currentRow, null, "");
	dialogForPick.close();
	return null;

}
public Object pickParentOLD(String colName, DynamicDBean item) {
	return null;
}
public Object pickParentOLD1(String colName, DynamicDBean item) {
	System.out.println("clicked "+colName + item.getCol(colName));
//	return null;
	try {
	DynamicGridForPick dynamicGridForPick = new DynamicGridForPick(); 
	String queryFormForPickClassName = null;
//	Object queryFormClassName = PACKAGE_VIEWS+queryParameters.getParameters().get("queryFormClassName").get(0);
	DynamicDBean currentRow = item;
	String filter="tableName='"+currentRow.getResourceName()+"'%20AND%20FieldNameInUI='"+colName+"'";
	String parentResource = "";
	
	JsonNode rowsList = JSonClient.get("FieldTemplate",filter,true,AppConst.PRE_CONF_PARAM_METADATA,"1");
	for (JsonNode eachRow : rowsList)  {
		if (eachRow.size() > 0)
		{
			parentResource = eachRow.get("parentResource").asText();
			pickMapFields =  eachRow.get("pickMapFields").asText();
			queryFormForPickClassName =  eachRow.get("queryFormForPickClassName").asText();
		}
	}
	if (queryFormForPickClassName.startsWith("coop.intergal.ui.views") == false)
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
private boolean isDecimal(String header, String colType) {
	if (colType.equals("6"))
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

		    
		if (hasSideDisplay )
		{
		grid.addSelectionListener(e -> {
//			getBinder()..select(e.getFirstSelectedItem());
//			e.getFirstSelectedItem().ifPresent(entity -> {
//				navigateToEntity(entity.toString());
//			setBean(DynamicDBean bean)
			if (e.getFirstSelectedItem().isPresent())
			{
				selectedRow =(DynamicDBean)e.getFirstSelectedItem().get();
				showBean(selectedRow);
				methodForRowSelected(selectedRow); 

				
			}	
//				getGrid().deselectAll();
			});
		}
//		else // for deleting and other events that need the selectedRow 
//		{
//			grid.addSelectionListener(e -> {
//				if (e.getFirstSelectedItem().isPresent())
//					selectedRow =(DynamicDBean)e.getFirstSelectedItem().get();
//					System.out.println("Registro selecionado " + selectedRow.getCol0());
//				});
//		}
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

	void showBean(DynamicDBean bean ) {
		try {

			setVisibleRowData(true);
			if (bean.isReadOnly() || isSubResourceReadOnly(bean.getResourceName())) // when a bean is mark as readOnly buttons for save are hide, to mark as read only add row.readONly=true to the event of the resource in LAC or as Extended property
				buttonsForm.setVisible(false);
			else
				buttonsForm.setVisible(true);
			selectedRow = bean;
			keepRowBeforChanges = new DynamicDBean(); 
			keepRowBeforChanges = RestData.copyDatabean(bean);
//			Class<?> dynamicForm = Class.forName("coop.intergal.tys.ui.views.DynamicForm");
			Class<?> dynamicForm = Class.forName(displayFormClassName);//"coop.intergal.tys.ui.views.comprasyventas.compras.PedidoProveedorForm");
			display = dynamicForm.newInstance();
			Method setRowsColList = dynamicForm.getMethod("setRowsColList", new Class[] {java.util.ArrayList.class} );
			Method setBinder = dynamicForm.getMethod("setBinder", new Class[] {com.vaadin.flow.data.binder.Binder.class} );
			Method setDataProvider= dynamicForm.getMethod("setDataProvider", new Class[] {coop.intergal.vaadin.rest.utils.DdbDataBackEndProvider.class} );
			Method getButtonsForm= dynamicForm.getMethod("setButtonsForm",new Class[] { FormButtonsBar.class});
			setBean = dynamicForm.getMethod("setBean", new Class[] {coop.intergal.vaadin.rest.utils.DynamicDBean.class} );
			setRowsColList.invoke(display,rowsColListGrid);
			getButtonsForm.invoke(display, buttonsForm);
			setBean.invoke(display,bean);
			setBinder.invoke(display,binder);
			
			
			setDataProvider.invoke(display, dataProvider);
			divDisplay.removeAll();
			Method setdVGrid= dynamicForm.getMethod("setDVGrid", new Class[] {coop.intergal.ui.views.DynamicViewGrid.class});
			setdVGrid.invoke(display, this); // to use methods in this class
			if (displayFormClassName.indexOf("NODISPLAY") > -1)
			{
				divDisplay.setVisible(false);
			}
			else
			{
				divDisplay.setVisible(true);
			}
			if (displayFormClassName.indexOf("Generated") > -1)
			{
			//	setDataProvider.invoke(display, dataProvider);
				Method createContent= dynamicForm.getMethod("createContent",new Class[] { FormButtonsBar.class});
//				Method setdVGrid= dynamicForm.getMethod("setDVGrid", new Class[] {coop.intergal.ui.views.DynamicViewGrid.class});
//				setdVGrid.invoke(display, this); // to use methods in this class
				divInDisplay = createContent.invoke(display, buttonsForm );
				divDisplay.add((Component)divInDisplay);
				
			}
			else
			{
				divDisplay.add((Component)display);
			}

			
	//		divDisplay.remove((Component) display);
			
			String resourceSubGrid = extractResourceSubGrid(bean,0);
			divSubGrid.removeAll();
			String tabsList = rowsColListGrid.get(0)[12];
			if (resourceSubGrid != null && (tabsList == null || tabsList.length() == 0)) // there only one tab
			{
			//	divSubGrid.add(componSubgrid(bean, resourceSubGrid));
				Div content0=new Div(); 
				divSubGrid.add(fillContent(content0, 0 , bean));	
//				generatedUtil.setDivSubGrid(divSubGrid); // to run methods for buttons
				
				Method setDivSubGrid= dynamicForm.getMethod("setDivSubGrid", new Class[] {com.vaadin.flow.component.html.Div.class});
				setDivSubGrid.invoke(display, divSubGrid); // to use methods in this class

	//??			setDataProvider.invoke(display, subDynamicViewGrid.getDataProvider());
			}
			else if (resourceSubGrid != null) //123456789
			{
//				createTabs(DynamicDBean bean)
				divSubGrid.removeAll();
				divSubGrid.add(createSubTabs(bean, tabsList));
				Method setDivSubGrid= dynamicForm.getMethod("setDivSubGrid", new Class[] {com.vaadin.flow.component.html.Div.class});
				setDivSubGrid.invoke(display, divSubGrid); // to use methods in this class

//				DynamicViewGrid subDynamicViewGrid = new DynamicViewGrid();
//				subDynamicViewGrid.setButtonsRowVisible(false);//(true);
//				divSubGrid.add(subDynamicViewGrid );
			}
			else // resourceSubGrid is null
			{
				layout.getDisplaySplitSubGrid().setOrientation(Orientation.HORIZONTAL); // to hide split
			}
			if (layout != null)
			{

				if (bean.getParams() != null )
				{		
				if (bean.getParams().indexOf("classForLayout") != -1)
				{
					
					String parClassForLayout = bean.getParams().substring(bean.getParams().indexOf("classForLayout")+15);
					int idxNextAnd = parClassForLayout.indexOf("&");
					int  idxLast = parClassForLayout.length();
					if (idxNextAnd > -1)
						idxLast = idxNextAnd;
					String classForLayout = parClassForLayout.substring(1,idxLast-1);
					String[] methodAndValue = classForLayout.split(":");
					String method = methodAndValue[0];
					String value = methodAndValue[1];
					Class<?> classLayout = Class.forName("coop.intergal.ui.views.DynamicQryGridDisplay");
					Method getDiv = classLayout.getMethod(method);
					Div div = (Div) getDiv.invoke(layout);
					div.addClassName(value);
	
				}
				if (bean.getParams() == null || bean.getParams().indexOf("splitGridDisplay") == -1)
		//			layout.getGrid().getElement().getStyle().set("flex-basis", "70px");
					System.out.println("DynamicViewGrid.showBean() NOT splitGridDisplay");
				if (bean.getParams() != null && bean.getParams().indexOf("splitGridDisplay") != -1) 
					{
					String params = bean.getParams().substring(bean.getParams().indexOf("splitGridDisplay")+17);
					int idxNextAnd = params.indexOf("&");
					int  idxLast = params.length();
					if (idxNextAnd > -1)
						idxLast = idxNextAnd;
					String splitPos = params.substring(1,idxLast-1);
					System.out.println("DynamicViewGrid.showBean() splitPos <"+ splitPos +">");
					layout.getGridSplitDisplay().setSplitterPosition(new Double(splitPos));
	//				layout.getDisplaySplitSubGrid().setSplitterPosition(50);
					}
				else
					{
					layout.getGridSplitDisplay().setSplitterPosition(AppConst.DEFAULT_SPLIT_POS_GRID_DISPLAY);
					}
				}
				layout.getDivQuery().setVisible(false); /// each time yoy select a row QUERY is hide
// ************ SPLITTER POSITION Doesn't work for now this code is comented				
//				if (bean.getParams() != null && bean.getParams().indexOf("splitQuery") != -1) 
//					{
//					String params = bean.getParams().substring(bean.getParams().indexOf("splitQuery")+11);
//					int idxNextAnd = params.indexOf("&");
//					int  idxLast = params.length();
//					if (idxNextAnd > -1)
//						idxLast = idxNextAnd;
//					String splitPos = params.substring(1,idxLast-1);
//					System.out.println("DynamicViewGrid.showBean() splitPos splitQuery <"+ splitPos +">");
//					layout.getQuerySplitGrid().setSplitterPosition(new Double(splitPos));
//					}
//				else
//					{
//					SplitLayout splitLayout = layout.getQuerySplitGrid();//.setSplitterPosition(AppConst.DEFAULT_SPLIT_POS_QUERY_GRID);
//					layout.getDivQuery().setVisible(false);
//					layout.getDivDisplay().add(new Button("+", e->{
//		                position += 10;
//		                splitLayout.setSplitterPosition(position);
//		                System.out.println("DynamicViewGrid.showBean() splitPos splitQuery + <"+ position +">");
//		               }), new Button("-", e->{
//		                 position -= 10;
//		                 splitLayout.setSplitterPosition(position);
//		                 System.out.println("DynamicViewGrid.showBean() splitPos splitQuery - <"+ position +">");
//		       	              }));
//					}
//				if (bean.getParams() != null && bean.getParams().indexOf("splitDisplaySubGrid") != -1) 
//					{
//					String params = bean.getParams().substring(bean.getParams().indexOf("splitDisplaySubGrid")+20);
//					int idxNextAnd = params.indexOf("&");
//					int  idxLast = params.length();
//					if (idxNextAnd > -1)
//						idxLast = idxNextAnd;
//					String splitPos = params.substring(1,idxLast-1);
//					System.out.println("DynamicViewGrid.showBean() splitPos splitDisplaySubGrid <"+ splitPos +">");
//					layout.getDisplaySplitSubGrid().setSplitterPosition(new Double(splitPos));
//					}
//			}
//			else
//				{
//				layout.getDisplaySplitSubGrid().setSplitterPosition(AppConst.DEFAULT_SPLIT_POS_DISPLAY_SUBGRID);
//				}
	/// **************FIN ANULA SPLITTER 			
//				layout.getDivQuery().getStyle().set("flex-basis", "65px");
//				Div div = (Div) layout.getDivDisplay().get;
//				div.getStyle().set("flex-basis", "650px");
//				}	
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
			}
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
	public void showBeaninPopupXX	(DynamicDBean bean, String resourcePopup,String layoutClassName, String displayFormClassNamePopup, Dialog dialogForShow2, String filterForPopup ) {
			showBeaninPopup(resourcePopup);
		}
	   public void showBeaninPopup(String resourcePopup) {
	        final Dialog requestedDialog;
	        if (allDialogs.containsKey(resourcePopup)) {
	            requestedDialog = allDialogs.get(resourcePopup);
	        } else {
	            requestedDialog = new Dialog();
	            Button bCloseDialog = new Button(resourcePopup, e -> requestedDialog.close());
	            requestedDialog.removeAll();
	            requestedDialog.setCloseOnOutsideClick(false);
	            requestedDialog.add(bCloseDialog/*,(Component)layoutPopup*/);
	            requestedDialog.setModal(false);
	            requestedDialog.setDraggable(true);
	            requestedDialog.setResizable(true);
	            requestedDialog.setId(resourcePopup);
	            allDialogs.put(resourcePopup, requestedDialog);
	        }

	        if (!requestedDialog.isOpened())
	            requestedDialog.open();
	    }

	public void showBeaninPopupXX(String resourcePopup) {

		final Dialog dialogForShow = new Dialog();
		String idForDialog = resourcePopup;

		Dialog dialogAlreadyOpen = isDialogAlreadyOpen(idForDialog);
//		if (dialogAlreadyOpen == null )
//			{
//			dialogForShow = new Dialog();
//			}
//		else
//			dialogForShow = dialogAlreadyOpen;
		 Button bCloseDialog = new Button(resourcePopup, e -> dialogForShow.close());
//		 bCloseDialog.addClickListener(e -> dialogForShow.close());
		 dialogForShow.removeAll();
		 dialogForShow.setCloseOnOutsideClick(false);
		 dialogForShow.add(bCloseDialog/*,(Component)layoutPopup*/);
		 dialogForShow.setModal(false);
		 dialogForShow.setDraggable(true);
		 dialogForShow.setResizable(true);
		 dialogForShow.setId(idForDialog);
		 if (dialogForShow.isOpened() == false)
			 dialogForShow.open();
	}
	private Object closeDialog(Dialog dialogForShow2) {
		// TODO Auto-generated method stub
		return null;
	}

	public void showBeaninPopup	(DynamicDBean bean, String resourcePopup,String layoutClassName, String displayFormClassNamePopup, Dialog dialogForShow2, String filterForPopup , DynamicDisplayForAskData dynamicDisplayForAskData) {
		Binder<DynamicDBean> binderForDialog = new Binder<>(DynamicDBean.class);
		try {
//			if (dialogForShow2 != null)
//				dialogForShow = dialogForShow2;

			DdbDataBackEndProvider dataProviderPopup = new DdbDataBackEndProvider();
			dataProviderPopup.setPreConfParam(UtilSessionData.getCompanyYear()+AppConst.PRE_CONF_PARAM);
			dataProviderPopup.setResourceName(resourcePopup);
			
			Class<?> dynamicLayout = Class.forName(layoutClassName);//"coop.intergal.tys.ui.views.comprasyventas.compras.PedidoProveedorForm");
			Object layoutPopup = dynamicLayout.newInstance();
			if ( dynamicDisplayForAskData != null)
				layoutPopup = dynamicDisplayForAskData;
			if (layoutClassName.indexOf("DynamicViewGrid") > -1) // is a Layout that shows a Grid
			{
				Method setResourceName = dynamicLayout.getMethod("setResourceName",new Class[] {String.class} );
				Method setFilter = dynamicLayout.getMethod("setFilter",new Class[] {String.class} );
				Method setupGrid = dynamicLayout.getMethod("setupGrid",new Class[] {Boolean.class, Boolean.class} );
//				Method setButtonsRowVisible = dynamicLayout.getMethod("setButtonsRowVisible",new Class[] {Boolean.class, Boolean.class} );
				Method setButtonsRowVisible = dynamicLayout.getMethod("setButtonsRowVisible",new Class[] {Boolean.class} );

				setResourceName.invoke(layoutPopup, resourcePopup);
				String filter = ProcessParams.componFilterFromParams(filterForPopup, bean);
				setFilter.invoke(layoutPopup, filter);
				setupGrid.invoke(layoutPopup, true, true);
				if (bean != null && (bean.isReadOnly() || isSubResourceReadOnly(bean.getResourceName()))) // when a bean is mark as readOnly buttons for save are hide, to mark as read only add row.readONly=true to the event of the resource in LAC or as Extended property
					{
					setButtonsRowVisible.invoke("layoutPopup", "true");
					}

			}
			else if (layoutClassName.indexOf("DynamicQryGridDisplay") > -1) // is a Layout that shows a QRY with Grid
			{
				Method setResourceName = dynamicLayout.getMethod("setResourceName",new Class[] {String.class} );
				Method setFilter = dynamicLayout.getMethod("setFilter",new Class[] {String.class} );
				Method prepareLayout = dynamicLayout.getMethod("prepareLayout",new Class[] {String.class, String.class} );
//@@?				Method setupGrid = dynamicLayout.getMethod("setupGrid",new Class[] {Boolean.class, Boolean.class} );
//				Method setButtonsRowVisible = dynamicLayout.getMethod("setButtonsRowVisible",new Class[] {Boolean.class, Boolean.class} );
//				Method setButtonsRowVisible = dynamicLayout.getMethod("setButtonsRowVisible",new Class[] {Boolean.class} );

				setResourceName.invoke(layoutPopup, resourcePopup);
				String filter = ProcessParams.componFilterFromParams(filterForPopup, bean);
				setFilter.invoke(layoutPopup, filter);
				String classForQuery = "coop.intergal.ui.views.GeneratedQuery"; // @@ TODO for now is using automatic query form, inthe future use a parameter 
				prepareLayout.invoke(layoutPopup,classForQuery, displayFormClassNamePopup);
//@@?				setupGrid.invoke(layoutPopup, true, true);
//				if (bean != null && (bean.isReadOnly() || isSubResourceReadOnly(bean.getResourceName()))) // when a bean is mark as readOnly buttons for save are hide, to mark as read only add row.readONly=true to the event of the resource in LAC or as Extended property
//					{
//					setButtonsRowVisible.invoke(layoutPopup, true);
//					}

			}
			else if (displayFormClassNamePopup != null && displayFormClassNamePopup.isEmpty() == false) // is a layout that shows one row + children if exist
				{
				Div divSubGridPopup = null;
				Method getDivDisplay = dynamicLayout.getMethod("getDivDisplay");
				Method getButtons = dynamicLayout.getMethod("getButtons");
				FormButtonsBar formButtonsBar = (FormButtonsBar)getButtons.invoke(layoutPopup);
				if (formButtonsBar != null &&(bean.isReadOnly() || isSubResourceReadOnly(bean.getResourceName()))) // when a bean is mark as readOnly buttons for save are hide, to mark as read only add row.readONly=true to the event of the resource in LAC or as Extended property
					formButtonsBar.setVisible(false);
				Div divDisplayPopup = (Div) getDivDisplay.invoke(layoutPopup);
				if (layoutClassName.indexOf("DynamicDisplayForAskData") == -1) // is a form thta ask data for a process
					{
					Method getDivSubGrid = dynamicLayout.getMethod("getDivSubGrid");
					divSubGridPopup = (Div) getDivSubGrid.invoke(layoutPopup);
					}
					
				
			
//			DynamicDisplaySubgrid dynamicDisplaySubgrid = new DynamicDisplaySubgrid();
//			Div divDisplayPopup = dynamicDisplaySubgrid.getDivDisplay();
//			Div divSubGridPopup =  dynamicDisplaySubgrid.getDivSubGrid();//new Div();
				Object divInDisplayPopup = new Div();
				setVisibleRowData(true);
//				if (bean.isReadOnly() || isSubResourceReadOnly(bean.getResourceName())) // when a bean is mark as readOnly buttons for save are hide, to mark as read only add row.readONly=true to the event of the resource in LAC or as Extended property
//					buttonsForm.setVisible(false);
				selectedRow = bean;
				keepRowBeforChanges = new DynamicDBean(); 
				keepRowBeforChanges = RestData.copyDatabean(bean);
//			Class<?> dynamicForm = Class.forName("coop.intergal.tys.ui.views.DynamicForm");
				Class<?> dynamicForm = Class.forName(displayFormClassNamePopup);//"coop.intergal.tys.ui.views.comprasyventas.compras.PedidoProveedorForm");
				Object displayPopup = dynamicForm.newInstance();
				Method setRowsColList = dynamicForm.getMethod("setRowsColList", new Class[] {java.util.ArrayList.class} );
				Method setBinder = dynamicForm.getMethod("setBinder", new Class[] {com.vaadin.flow.data.binder.Binder.class} );
				Method setDataProvider= dynamicForm.getMethod("setDataProvider", new Class[] {coop.intergal.vaadin.rest.utils.DdbDataBackEndProvider.class} );
			
				setBean = dynamicForm.getMethod("setBean", new Class[] {coop.intergal.vaadin.rest.utils.DynamicDBean.class} );
				ArrayList<String[]> rowsColListGridPopup = dataProviderPopup.getRowsColList();
				setRowsColList.invoke(displayPopup,rowsColListGridPopup);
				setBinder.invoke(displayPopup,binderForDialog);
				setDataProvider.invoke(displayPopup, dataProviderPopup);
				setBean.invoke(displayPopup,bean);
				resourcePopup = bean.getResourceName(); // when is a display the bean is the on e to show and has the resourcename
 
				divDisplayPopup.removeAll();
				if (displayFormClassNamePopup.indexOf("Generated") > -1)
				{
			//	setDataProvider.invoke(display, dataProvider);
					Method createContent= dynamicForm.getMethod("createContent",new Class[] { FormButtonsBar.class, GenericClassForMethods.class});
//					Method setdVGrid= dynamicForm.getMethod("setDVGrid", new Class[] {coop.intergal.ui.views.DynamicViewGrid.class});
//					setdVGrid.invoke(display, this); // to use methods in this class
					divInDisplayPopup = createContent.invoke(displayPopup, buttonsForm, dynamicDisplayForAskData.getGenericClassForMethods() );
					divDisplayPopup.add((Component)divInDisplayPopup);
				}
				else
				{
					divDisplayPopup.add((Component)displayPopup);
				}
			
			
	//		divDisplay.remove((Component) display);
				if (layoutClassName.indexOf("DynamicDisplayForAskData") == -1 && (layoutClassName.indexOf("DynamicDisplayOnly") == -1)) // this layout for now doesn't have subgrid
					{
					String resourceSubGrid = extractResourceSubGrid(bean,0);
					divSubGridPopup.removeAll();
					String tabsList = rowsColListGrid.get(0)[12];
					if (resourceSubGrid != null && (tabsList == null || tabsList.length() == 0)) // there only one tab
						{
			//	divSubGrid.add(componSubgrid(bean, resourceSubGrid));
						Div content0=new Div(); 
						divSubGridPopup.add(fillContent(content0, 0 , bean));	
	//??			setDataProvider.invoke(display, subDynamicViewGrid.getDataProvider());
					}
					else if (resourceSubGrid != null)
					{
						divSubGridPopup.removeAll();
						divSubGridPopup.add(createSubTabs(bean, tabsList));
					}
				}
			}
			String idForDialog = resourcePopup+"@DFC@"+displayFormClassNamePopup+"@F@"+filterForPopup+"@L@"+layoutClassName;
//			if (dialogForShow != null && dialogForShow.getId().isPresent() )
//			{ 
			final Dialog dialogForShow;// = new Dialog();
				Dialog dialogAlreadyOpen = isDialogAlreadyOpen(idForDialog);
				if (dialogAlreadyOpen == null )
					{
					dialogForShow = new Dialog();
		            Button bCloseDialog = new Button("X", e -> dialogForShow.close());
		            dialogForShow.removeAll();
		            dialogForShow.setCloseOnOutsideClick(false);
		            dialogForShow.add(bCloseDialog, (Component)layoutPopup);
		            dialogForShow.setModal(false);
		            dialogForShow.setDraggable(true);
		            dialogForShow.setResizable(true);
		            dialogForShow.setId(idForDialog);
					}
				else
				{
					dialogForShow = dialogAlreadyOpen;
					dialogForShow.removeAll();
		            Button bCloseDialog = new Button("X", e -> dialogForShow.close());
		            dialogForShow.add(bCloseDialog, (Component)layoutPopup);

				}
		        if (!dialogForShow.isOpened())
		        	dialogForShow.open();

//			}
//ialogForShow.set

//			dialogForShow.removeAll();
//			dialogForShow.setCloseOnOutsideClick(false);
//			dialogForShow.add(new Button ("X", e -> dialogForShow.close()),(Component)layoutPopup);
//			dialogForShow.setModal(false);
//			dialogForShow.setDraggable(true);
//			dialogForShow.setResizable(true);
//			dialogForShow.setId(idForDialog);
//			if (dialogForShow.isOpened() == false)
//				dialogForShow.open();
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

//	private Object closePoppup(ClickEvent<Button> e) {
//		Dialog dialog = e.;
//		return null;
//	}

	private Dialog isDialogAlreadyOpen(String idToChech) {
		for (Component child : UI.getCurrent().getChildren().filter(c->c instanceof Dialog).collect(Collectors.toList()))
		{
			Dialog dialog = (Dialog) child;
			String idDialogOpen = dialog.getId().get();
			if (idToChech.equals(idDialogOpen))
				return dialog;
		}
		return null;
	}

	private Div componSubgrid(DynamicDBean bean, String resourceSubGrid2) {
		DynamicViewGrid subDynamicViewGrid = new DynamicViewGrid();
		subDynamicViewGrid.setCache(cache);
		subDynamicViewGrid.setButtonsRowVisible(isSubResourceReadOnly(resourceSubGrid2)== false);
//			subDynamicViewGrid.getElement().getStyle().set("height","100%");
		subDynamicViewGrid.setResourceName(resourceSubGrid2);
		if (resourceSubGrid2.indexOf(".")> -1)
			subDynamicViewGrid.setFilter(componFKFilter(bean, resourceSubGrid2));
		subDynamicViewGrid.setupGrid(true,true);
		subDynamicViewGrid.setParentRow(selectedRow);
		subDynamicViewGrid.setDisplayParent(display);
		subDynamicViewGrid.setBeanParent(setBean);
		divSubGrid.add(subDynamicViewGrid );
		keepSubGridToBeAlterExternally(subDynamicViewGrid);
		Div divTab = new Div();
		divTab.add(subDynamicViewGrid );
		return divTab;
		
	}
	private void keepSubGridToBeAlterExternally(DynamicViewGrid subDynamicViewGrid) {
		DynamicQryGridDisplay dQGD = (DynamicQryGridDisplay) UiComponentsUtils.findComponent(UI.getCurrent(), "DQGD");
		dQGD.getDvgIntheForm().put(subDynamicViewGrid.getResourceName(),subDynamicViewGrid );
		
	}

	private boolean isSubResourceReadOnly(String resourceSubGrid2) {
		try {
//			if (isResourceReadOnly != null)
//				return isResourceReadOnly;
			JsonNode extProp = JSonClient.get("JS_ExtProp", resourceSubGrid2, cache, UtilSessionData.getCompanyYear()+AppConst.PRE_CONF_PARAM);
			if (extProp.get("isReadOnly") != null)
			{
				isResourceReadOnly = extProp.get("isReadOnly").asBoolean();
				return isResourceReadOnly;
			}
			else
			{
				isResourceReadOnly = false;
				return isResourceReadOnly;
			}	
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return false;
	}

	public Component  dummy2 ()
	{
		Tab timecard = new Tab("Time card");
		Tab comments = new Tab("Comments");
		Tabs tabs = new Tabs(timecard, comments);

		Div div = new Div();
		Div timecardPage = new Div();
		timecardPage.add("This is the time card page");
//		timecardPage.add(new TextField());
		timecardPage.add(grid);
		Div commentsPage = new Div();
		commentsPage.add("This is the comments page");
//		commentsPage.add(new TextField());
		Map<Tab, Component> tabsToPages = new HashMap<>();
		tabsToPages.put(timecard, timecardPage);
		tabsToPages.put(comments, commentsPage);

		Div pages = new Div(timecardPage);
		Set<Component> pagesShown = Stream.of(commentsPage).collect(Collectors.toSet());

		tabs.addSelectedChangeListener(event -> {
		pages.removeAll();
		pagesShown.clear();
		Component selectedPage = tabsToPages.get(tabs.getSelectedTab());
		pages.add(selectedPage);
		pagesShown.add(selectedPage);
		});
//		add(tabs, pages);
       	Div content = new Div();
    	content.add(tabs, pages);
		return content;
	}

	public Component createSubTabs(DynamicDBean bean, String tabsList) {//ArrayList<String[]> rowsFieldList, Boolean isQuery, Boolean cache,String tabsLabels) {
//		String tabsLabels="1,2,3,4,5";
		String [] tokens = tabsList.split(Pattern.quote(","));
//		int i = 0;
//	   	Div contentyDiv0 = new Div(); 
		Tab tab0 =null ;Tab tab1=null ;Tab tab2=null ;Tab tab3=null ;Tab tab4=null ;Tab tab5=null ;Tab tab6=null ;Tab tab7=null ;
		Div content0=new Div(); 
		Div content1=new Div();
		Div content2=new Div(); Div content3=new Div();
		Div content4=new Div(); 
		Div content5=new Div(); 
		Div content6=new Div(); 
		Div content7=new Div(); 
//		FlexBoxLayout content7=null; 

	   	int nTabs = tokens.length;
	   	String tabTitle;
//		while (tokens.length > i)
//		{ 
			if (nTabs > 0)
			{
				tabTitle = tokens[0];
				tab0 = new Tab(tabTitle);				
				content0 = fillContent(content0, 0, bean);	
				content0.setId("0");
			}
			if (nTabs > 1)
			{
				tabTitle = tokens[1];
				tab1 = new Tab(tabTitle);
				content1.setId("1");
//				content1 = fillContent(content1, 1, bean);	
//				content1.setVisible(false);
				
			}
			if (nTabs > 2)
			{
				tabTitle = tokens[2];
				tab2 = new Tab(tabTitle);
				content2.setId("2");
//				content2 = fillContent(content2, 2, bean);	
				content2.setVisible(false);
				
			}
			if (nTabs > 3)
			{
				tabTitle = tokens[3];
				tab3 = new Tab(tabTitle);
				content3.setId("3");
//				content3 = fillContent(content3, 3, bean);	
				content3.setVisible(false);

				
			}
			if (nTabs > 4)
			{
				tabTitle = tokens[4];
				tab4 = new Tab(tabTitle);
				content4.setId("4");
//				content4 = fillContent(content4, 4, bean);	
				content4.setVisible(false);

			}
			if (nTabs > 5)
			{
				tabTitle = tokens[5];
				tab5 = new Tab(tabTitle);
				content5.setId("5");
//				content5 = fillContent(content5, 5, bean);	
				content5.setVisible(false);
				
			}
			if (nTabs > 6)
			{
				tabTitle = tokens[6];
				tab6 = new Tab(tabTitle);
				content6.setId("6");
//				content6 = fillContent(content6, 6, bean);	
				content6.setVisible(false);

				
			}
			if (nTabs > 7)
			{
				tabTitle = tokens[7];
				tab7 = new Tab(tabTitle);
				content7.setId("7");
//				content7 = fillContent(content7, 7, bean);	
				content7.setVisible(false);

			}				
//			i++;
//		}

 
    	Map<Tab, Component> tabsToPages = new HashMap<>();
//		Set<Component> pagesShown = Stream.of(commentsPage).collect(Collectors.toSet());

 //   	Tabs tabs = new Tabs(tab0,tab1);
    	Div pages =null ;
      	if (nTabs > 7)
		{
      		tabsToPages.put(tab0, content0);
      		tabsToPages.put(tab1, content1);
      		tabsToPages.put(tab2, content2);
      		tabsToPages.put(tab3, content3);
      		tabsToPages.put(tab4, content4);
      		tabsToPages.put(tab5, content5);
      		tabsToPages.put(tab6, content6);
      		tabsToPages.put(tab7, content7);
      		Tabs tabs = new Tabs(tab0, tab1, tab2, tab3, tab4, tab5, tab6,tab7);
  //    		pages = new Div(content0, content1, content2,content3, content4, content5, content6 , content7);
      		Div pages2 = new Div(content0, content1, content2,content3, content4, content5, content6 , content7);
    		tabs.addSelectedChangeListener(event -> {
    			pages2.removeAll();
    			Component selectedPage = tabsToPages.get(tabs.getSelectedTab());
    			selectedPage=fillContentSelectedPage(selectedPage, tabsToPages, bean);
    			pages2.add(selectedPage);
    		});
    		Div content = new Div();
    		content.add(tabs, pages);
    		return content;
		}
      	else     	    	
      	if (nTabs > 6)
		{
      		tabsToPages.put(tab0, content0);
      		tabsToPages.put(tab1, content1);
      		tabsToPages.put(tab2, content2);
      		tabsToPages.put(tab3, content3);
      		tabsToPages.put(tab4, content4);
      		tabsToPages.put(tab5, content5);
      		tabsToPages.put(tab6, content6);
      		Tabs tabs = new Tabs(tab0, tab1, tab2, tab3, tab4, tab5, tab6);
    //  		pages = new Div(content0, content1, content2,content3, content4, content5, content6 );
      		Div pages2 = new Div(content0, content1, content2,content3, content4, content5, content6 );
    		tabs.addSelectedChangeListener(event -> {
    			pages2.removeAll();
    			Component selectedPage = tabsToPages.get(tabs.getSelectedTab());
    			selectedPage=fillContentSelectedPage(selectedPage, tabsToPages, bean);
    			pages2.add(selectedPage);
    		});
    		Div content = new Div();
    		content.add(tabs, pages);
    		return content;
		}
      	else     	
      	if (nTabs > 5)
		{
      		tabsToPages.put(tab0, content0);
      		tabsToPages.put(tab1, content1);
      		tabsToPages.put(tab2, content2);
      		tabsToPages.put(tab3, content3);
      		tabsToPages.put(tab4, content4);
      		tabsToPages.put(tab5, content5);
      		Tabs tabs = new Tabs(tab0, tab1, tab2, tab3, tab4, tab5);
 //     		pages = new Div(content0, content1, content2,content3, content4, content5 );
    		Div pages2 = new Div(content0, content1, content2,content3, content4, content5);
    		tabs.addSelectedChangeListener(event -> {
    			pages2.removeAll();
    			Component selectedPage = tabsToPages.get(tabs.getSelectedTab());
    			selectedPage=fillContentSelectedPage(selectedPage, tabsToPages, bean);
    			pages2.add(selectedPage);
    		});
    		Div content = new Div();
    		content.add(tabs, pages);
    		return content;
		}
      	else     	
      	if (nTabs > 4)
		{
      		tabsToPages.put(tab0, content0);
      		tabsToPages.put(tab1, content1);
      		tabsToPages.put(tab2, content2);
      		tabsToPages.put(tab3, content3);
      		tabsToPages.put(tab4, content4);
      		Tabs tabs = new Tabs(tab0, tab1, tab2, tab3, tab4);
      		Div pages2 = new Div(content0, content1, content2,content3, content4 );
      		tabs.addSelectedChangeListener(event -> {
			pages2.removeAll();
			Component selectedPage = tabsToPages.get(tabs.getSelectedTab());
			selectedPage=fillContentSelectedPage(selectedPage, tabsToPages, bean);
			pages2.add(selectedPage);
      		});
      		Div content = new Div();
      		content.add(tabs, pages2);
      		return content;
		}
    	else if (nTabs > 3)
    		{
    		tabsToPages.put(tab0, content0);
    		tabsToPages.put(tab1, content1);
       		tabsToPages.put(tab2, content2);
    		tabsToPages.put(tab3, content3);
    		Tabs tabs = new Tabs(tab0, tab1, tab2, tab3);
    		Div pages2 = new Div(content0, content1, content2,content3);
    		tabs.addSelectedChangeListener(event -> {
    			pages2.removeAll();
    			Component selectedPage = tabsToPages.get(tabs.getSelectedTab());
    			selectedPage=fillContentSelectedPage(selectedPage, tabsToPages, bean);
    			pages2.add(selectedPage);
    		});
    		Div content = new Div();
        	content.add(tabs, pages2);
    		return content;

    		}
      	else if (nTabs > 2)
      		{
      		tabsToPages.put(tab0, content0);
      		tabsToPages.put(tab1, content1);
      		tabsToPages.put(tab2, content2);
      		Tabs tabs = new Tabs(tab0, tab1, tab2);
//      		pages = new Div(content0, content1, content2);
    		Div pages2 = new Div(content0, content1, content2);
    		tabs.addSelectedChangeListener(event -> {
    			pages2.removeAll();
    			Component selectedPage = tabsToPages.get(tabs.getSelectedTab());
    			selectedPage=fillContentSelectedPage(selectedPage, tabsToPages, bean);
    			pages2.add(selectedPage);
    		});
    		Div content = new Div();
        	content.add(tabs, pages2);
        	content.setWidthFull();
    		return content;

      		}
    	else
    		{
     		tabsToPages.put(tab0, content0);
    		tabsToPages.put(tab1, content1);
    		Tabs tabs = new Tabs(tab0, tab1);
//    		pages = new Div(content0, content1 );
    		Div pages2 = new Div(content0, content1);
    		tabs.addSelectedChangeListener(event -> {
    			pages2.removeAll();
    			Component selectedPage = tabsToPages.get(tabs.getSelectedTab());
    			selectedPage=fillContentSelectedPage(selectedPage, tabsToPages, bean);
    			pages2.add(selectedPage);
    		});
    		Div content = new Div();
        	content.add(tabs, pages2);
    		return content;


    		}


    }

	private Component fillContentSelectedPage(Component selectedPage, Map<Tab, Component> tabsToPages, DynamicDBean bean) {
		Optional<String> id = selectedPage.getId();
		Integer idInt = new Integer (id.get());
//		if (id.equals("0"))
//		{
			Div content = (Div) tabsToPages.get(selectedPage);
			content = fillContent(content, idInt, bean);
			return content;
		
	}

	private Div fillContent(Div content, int i, DynamicDBean bean) {
		String resourceSubGrid = extractResourceSubGrid(bean,i);
		int idxFormExt = resourceSubGrid.indexOf("FormExt");
		if ( idxFormExt> -1)
			content = new Div(componSubForm(bean, resourceSubGrid));
		else
			content = new Div(componSubgrid(bean, resourceSubGrid));
		content.setWidthFull();
		return content;
	}

	private Component componSubForm(DynamicDBean bean, String resourceSubGrid0) {
		Div divSubForm = new Div();
//		divSubForm.add(new Label(" FORMULARIO "));
		JsonNode jsonNode = bean.getRowJSon();
		int idxPoint = resourceSubGrid0.indexOf(".");
		if (idxPoint > -1)
			resourceSubGrid0 = resourceSubGrid0.substring(idxPoint+1);
		JsonNode subGridFormExt = jsonNode.get(resourceSubGrid0); 
		String subLayoutClassName = "NO LAYOUT";
		if (subGridFormExt.get("subLayoutClassName") != null)
			subLayoutClassName = subGridFormExt.get("subLayoutClassName").asText();
		String subFormClassName = subGridFormExt.get("displaySubFormClassName").asText();
		String subFormFilter = subGridFormExt.get("filter").asText();
		String subFormResource = subGridFormExt.get("resource").asText();
		DdbDataBackEndProvider dataProviderSub = new DdbDataBackEndProvider();
		dataProviderSub.setPreConfParam(UtilSessionData.getCompanyYear()+AppConst.PRE_CONF_PARAM);
		dataProviderSub.setResourceName(subFormResource);
		dataProviderSub.setFilter(subFormFilter);
		if (subLayoutClassName.indexOf("DynamicGridDisplay") > -1)
		{
			return componDynamicGridDisplay (subLayoutClassName, subFormResource, subFormFilter, subFormClassName, divSubForm, true );
		}
		else
		{	
		DynamicDBean subBean = RestData.getOneRow(subFormResource, subFormFilter, AppConst.PRE_CONF_PARAM);
		if (subBean != null)
			return componForm (dataProviderSub, subBean, subFormClassName, divSubForm, true );
		else
			return new Label ("Sin datos");
		}
	}
	private Component componDynamicGridDisplay(String subLayoutClassName, String subFormResource, String subFormFilter,
			String subFormClassName, Div divSubForm, boolean b) {
		try {
//			setVisibleRowData(true);
//			ArrayList<String[]> rowsColList = bean2.getRowsColList();
//			if (bean2.isReadOnly()) // when a bean is mark as readOnly buttons for save are hide, to mark as read only add row.readONly=true to the event of the resource in LAC 
//				buttonsForm.setVisible(false);
//			if (isSub == false)
//				selectedRow = bean2;
//			keepRowBeforChanges = new DynamicDBean(); 
//			keepRowBeforChanges = RestData.copyDatabean(bean);
//			Class<?> dynamicForm = Class.forName("coop.intergal.tys.ui.views.DynamicForm");
			Class<?> dynamicForm = Class.forName(subLayoutClassName);//"coop.intergal.tys.ui.views.comprasyventas.compras.PedidoProveedorForm");
			Object oDynamicForm = dynamicForm.newInstance();
//			Method setRowsColList = dynamicForm.getMethod("setRowsColList", new Class[] {java.util.ArrayList.class} );
//			Method setBinder = dynamicForm.getMethod("setBinder", new Class[] {com.vaadin.flow.data.binder.Binder.class} );
//			Method setDataProvider= dynamicForm.getMethod("setDataProvider", new Class[] {coop.intergal.vaadin.rest.utils.DdbDataBackEndProvider.class} );
			Method setDisplayFormClassName = dynamicForm.getMethod("setDisplayFormClassName", new Class[] {String.class} );
//			Method setQueryFormClassName = dynamicForm.getMethod("setQueryFormClassName", new Class[] {String.class} );
			Method setResourceName = dynamicForm.getMethod("setResourceName", new Class[] {String.class} );
			Method setFilter = dynamicForm.getMethod("setFilter", new Class[] {String.class} );
			
			setDisplayFormClassName.invoke(oDynamicForm, subFormClassName);
			setResourceName.invoke(oDynamicForm, subFormResource);
			setFilter.invoke(oDynamicForm, subFormFilter);
//			setBean = dynamicForm.getMethod("setBean", new Class[] {coop.intergal.vaadin.rest.utils.DynamicDBean.class} );
//			setRowsColList.invoke(display,rowsColList);//rowsColListGrid);
//			setBinder.invoke(display,binder);
			
//			setBean.invoke(display,bean2);
//			setDataProvider.invoke(display, dataProviderForm);
			divSubForm.removeAll();
			Method createContent= dynamicForm.getMethod("createContent");
			Object divInSubDisplay = createContent.invoke(oDynamicForm);
			divSubForm.add((Component)divInSubDisplay);
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
		
		
		return divSubForm;
	}

	private Component componForm (DdbDataBackEndProvider dataProviderForm, DynamicDBean bean2, String subFormClassName, Div divSubForm, boolean isSub )
	{
		try {
//			setVisibleRowData(true);
			ArrayList<String[]> rowsColList = bean2.getRowsColList();
			if (bean2.isReadOnly()) // when a bean is mark as readOnly buttons for save are hide, to mark as read only add row.readONly=true to the event of the resource in LAC 
				buttonsForm.setVisible(false);
			if (isSub == false)
				selectedRow = bean2;
//			keepRowBeforChanges = new DynamicDBean(); 
//			keepRowBeforChanges = RestData.copyDatabean(bean);
//			Class<?> dynamicForm = Class.forName("coop.intergal.tys.ui.views.DynamicForm");
			Class<?> dynamicForm = Class.forName(subFormClassName);//"coop.intergal.tys.ui.views.comprasyventas.compras.PedidoProveedorForm");
			display = dynamicForm.newInstance();
			Method setRowsColList = dynamicForm.getMethod("setRowsColList", new Class[] {java.util.ArrayList.class} );
			Method setBinder = dynamicForm.getMethod("setBinder", new Class[] {com.vaadin.flow.data.binder.Binder.class} );
			Method setDataProvider= dynamicForm.getMethod("setDataProvider", new Class[] {coop.intergal.vaadin.rest.utils.DdbDataBackEndProvider.class} );
			
			setBean = dynamicForm.getMethod("setBean", new Class[] {coop.intergal.vaadin.rest.utils.DynamicDBean.class} );
			setRowsColList.invoke(display,rowsColList);//rowsColListGrid);
			setBinder.invoke(display,binder);
			
			setBean.invoke(display,bean2);
			setDataProvider.invoke(display, dataProviderForm);
			divSubForm.removeAll();
			if (subFormClassName.indexOf("Generated") > -1)
			{
			//	setDataProvider.invoke(display, dataProvider);
				Method createContent= dynamicForm.getMethod("createContent");
				Object divInSubDisplay = createContent.invoke(display);
				divSubForm.add((Component)divInSubDisplay);
			}
			else
			{
				divSubForm.add((Component)display);
			}
// ADDING SUB GRIDS			
			String resourceSubGrid = extractResourceSubGrid(bean2,0);//"CR-ped_proveed_cab.List-ped_proveed_lin"; // TODO adapt to use more than one subresource , use a variable instead of 9
//			divSubForm.removeAll();
			Div divSubFormSubGrid = new Div(); 
			String tabsList = rowsColList.get(0)[12];
			if (resourceSubGrid != null && (tabsList == null || tabsList.length() == 0)) // there only one tab
			{
				divSubFormSubGrid.add(componSubgrid(bean2, resourceSubGrid));
			}
			else
			{
				divSubFormSubGrid.removeAll();
				divSubFormSubGrid.add(createSubTabs(bean2, tabsList));
			}
			divSubForm.add(divSubFormSubGrid);
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
		
		
		return divSubForm;
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

	public void setButtonsRowVisible(Boolean b) {
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
			bean.setPreConfParam(UtilSessionData.getCompanyYear()+AppConst.PRE_CONF_PARAM);
			GeneratedUtil.fillDefaultValues(bean);
			selectedRow = bean;
			keepRowBeforChanges = RestData.copyDatabean(bean);
//			Class<?> dynamicForm = Class.forName("coop.intergal.tys.ui.views.DynamicForm");
			Class<?> dynamicForm = Class.forName(displayFormClassName);//"coop.intergal.tys.ui.views.comprasyventas.compras.PedidoProveedorForm");
			display = dynamicForm.newInstance();
			
			Method setRowsColList = dynamicForm.getMethod("setRowsColList", new Class[] {java.util.ArrayList.class} );
			Method setBinder = dynamicForm.getMethod("setBinder", new Class[] {com.vaadin.flow.data.binder.Binder.class} );
			Method setDataProvider= dynamicForm.getMethod("setDataProvider", new Class[] {coop.intergal.vaadin.rest.utils.DdbDataBackEndProvider.class} );

			setBean = dynamicForm.getMethod("setBean", new Class[] {coop.intergal.vaadin.rest.utils.DynamicDBean.class} );
			setRowsColList.invoke(display,rowsColListGrid);
			setBean.invoke(display,bean);
			setBinder.invoke(display,binder);
			
			setDataProvider.invoke(display, dataProvider);
			divDisplay.removeAll();
			if (displayFormClassName.indexOf("Generated") > -1)
			{
			//	setDataProvider.invoke(display, dataProvider);
				Method createContent= dynamicForm.getMethod("createContent");
				divInDisplay = createContent.invoke(display);
				divDisplay.add((Component)divInDisplay);
				

			}
			else
			{
				divDisplay.add((Component)display);
			}
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


	
//private String getColName(ArrayList<String[]> rowsColList, int i) { // normally the col.. is syncronice with i secuence, but is rowColList have some fields not in natural position then must be search the name in other way
//	String colNameInCL = rowsColList.get(i)[2];
//	if ( colNameInCL.equals("col"+i) || colNameInCL.isEmpty() ) // if colinIU = col... then return colName 
//		return rowsColList.get(i)[0];
//	else // otherwise it searchs
//	{
//		for (String[] row : rowsColList) // search for col.. to get his column name
//		{
//			if (row[2].equals("col"+i))
//				return row[0];
//		}
//			
//		return "null";
//	}
//}

	private String extractResourceSubGrid(DynamicDBean bean, int idx) {
		// TODO Auto-generated method stub CR-entradas_cab.List-entradas_lin/
		if (resourceAndSubresources.get(bean.getResourceName()+idx) != null)
			{
			String[] subResourcesOfResource = resourceAndSubresources.get(bean.getResourceName()+idx);
			if (subResourcesOfResource.length > idx)
				{
				if (subResourcesOfResource[idx].isEmpty() == false)
					return subResourcesOfResource[idx];
				}
			}	
		String rowJson = bean.getRowJSon().toString();
		if (bean.getRowJSon().get("tableName") != null)
			{
			if (bean.getRowJSon().get("tableName").asText().equals("CR-FormTemplate.List-FieldTemplate")) // // the resource is the formTemplate or FieldTemplate, to configure themselves, then there is a conflict with the names 
				return bean.getRowJSon().get("tableName").asText();
			}
		int idxResourceSubResource = rowJson.indexOf(bean.getResourceName() +".");
		if (rowJson.substring(idxResourceSubResource + bean.getResourceName().length()).startsWith(".FK") )
			idxResourceSubResource = -1;    //   to avoid the use as subresources the FK, not combined	
		if (idxResourceSubResource > -1 && rowJson.substring(idxResourceSubResource -12 ).startsWith("\"resource\"") == false) // when beside to "Resource"  is ExtForm
		{
			int idxEndSubreourceName = rowJson.substring(idxResourceSubResource).indexOf("/")+idxResourceSubResource;
			String pathSubreourceName = null;
			if (idxResourceSubResource > -1 && idxEndSubreourceName >-1)
				pathSubreourceName = rowJson.substring(idxResourceSubResource, idxEndSubreourceName);
			keepSubResourcesNames(bean.getResourceName()+idx,pathSubreourceName);	
			return pathSubreourceName;
		}
		else
		{
			idxResourceSubResource = rowJson.indexOf("List-");
		//	idxResourceSubResource = addBackwardsChars(idxResourceSubResource, rowJson);
			if (idx > 0)
			{
				int i=0;//idx-1;
				int keepIdx = 0;
				while (i < idx)
				{
					
					idxResourceSubResource = rowJson.substring(idxResourceSubResource+idx).indexOf("List-")+idxResourceSubResource+1;	
					keepIdx = idxResourceSubResource;
					i++;
				}
//				idxResourceSubResource =addBackwardsChars(idxResourceSubResource, rowJson);
				idxResourceSubResource = idxResourceSubResource+idx-1;
			}
			if (idxResourceSubResource > -1)
			{
				idxResourceSubResource =addBackwardsChars(idxResourceSubResource, rowJson);
				int idxEndSubreourceName = rowJson.substring(idxResourceSubResource).indexOf("\":")+idxResourceSubResource;//indexOf("\":[")+idxResourceSubResource;
				String pathSubreourceName = rowJson.substring(idxResourceSubResource, idxEndSubreourceName);		
				pathSubreourceName = bean.getResourceName()+"."+pathSubreourceName;
				keepSubResourcesNames(bean.getResourceName()+idx,pathSubreourceName);	
				return pathSubreourceName;
			}
			else
				return null;
		}
	}

	private int addBackwardsChars(int idxResourceSubResource, String rowJson) {
		while (true) // search backwards to add extra numbers in List
		{
			if (rowJson.substring(idxResourceSubResource-1,idxResourceSubResource).equals("\""))
				break;
			else
				idxResourceSubResource--;
		}
		return idxResourceSubResource;
}

	private void keepSubResourcesNames(String resourceName2, String pathSubreourceName) {
		String[] subResourcesOfResource = null;
		if (resourceAndSubresources.contains(resourceName2)==true)
		{
			subResourcesOfResource = resourceAndSubresources.get(resourceName2);
			List<String> arrlist = new ArrayList<String>( 
            Arrays.asList(subResourcesOfResource)); 
			arrlist.add(pathSubreourceName); 
			subResourcesOfResource = arrlist.toArray(subResourcesOfResource);
			resourceAndSubresources.remove(resourceName2);
			resourceAndSubresources.put(resourceName2,subResourcesOfResource);
		}
		else
		{
			subResourcesOfResource = new String[1]; 
			subResourcesOfResource [0] = pathSubreourceName;
			resourceAndSubresources.put(resourceName2,subResourcesOfResource);
		}
		
	}

	public String componFKFilter(DynamicDBean bean, String resourceSubGrid) {
		String fKfilter = JSonClient.getHt().get(resourceSubGrid);
	//	"FASE_CABEZERA" = ["FASE"]
	//			 and "CLAVE_ALMACEN" = ["CLAVE_ALMACEN"]
	//			 and "N_PEDIDO" = ["N_PEDIDO"]}
		int step = 0;
		String componFilter = "";
		int lengthFKfilter = 0; 
		if (fKfilter != null)
			lengthFKfilter = fKfilter.length();
		else
			System.err.println("ERROR FK NO CARGADA -------"+ resourceSubGrid );
//		int leftLength = lengthFKfilter;
		while (lengthFKfilter > 0 || fKfilter.length()  > 0)
		{
			int idXEqual = fKfilter.indexOf("=");
			if (idXEqual == -1)
				break;
			int idXMark = fKfilter.indexOf("]");
			if ((fKfilter.startsWith("\n and"))|| 
			    (fKfilter.startsWith("\n AND"))) 
				step = 6;
			else if (((fKfilter.indexOf("and") > -1 && fKfilter.indexOf("and") < 5)) ||
				     ((fKfilter.indexOf("AND") > -1 && fKfilter.indexOf("AND") < 5)))
				     
			{
				step = fKfilter.indexOf("AND") + 4;
			}
			else
				step = 0;
			String fKfieldName = fKfilter.substring(step+1, idXEqual - 2  );
			String parentfieldName = fKfilter.substring(4+idXEqual, idXMark - 1  );
			String parentValue = bean.getRowJSon().get(parentfieldName).asText();
			if (isADate(parentValue) && AppConst.FORMAT_FOR_DATETIME_FOR_JOIN.length() > 0) {
				parentValue = "=" +AppConst.FORMAT_FOR_DATETIME_FOR_JOIN.replace("#value#",parentValue ) + "%20and%20";
				}
			else 
			{
				parentValue = "='" +parentValue+ "'%20AND%20";
			}
			componFilter = componFilter + fKfieldName + parentValue;
			lengthFKfilter = lengthFKfilter - idXMark;
			fKfilter = fKfilter.substring(idXMark+1);
			
		}
		if (componFilter.length()>9)
			componFilter = componFilter.substring(0, componFilter.length()-9); // to delete last and
		return componFilter;
	}

	private boolean isADate(String parentValue) {
//		2021-01-11T00:00:00
		if (parentValue.length() != 19)
			return false;
		if (parentValue.substring(10,11).equals("T") && parentValue.substring(13,14).equals(":") && parentValue.substring(16,17).equals(":"))
			return true;
		return false;
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
		title="";//"..";
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

	public void setLayout(DynamicQryGridDisplay dynamicQryGridDisplay) {
		this.layout = dynamicQryGridDisplay;
		
	}

//	public void setGridSplitDisplay(SplitLayout gridSplitDisplay) {
//		this.gridSplitDisplay = gridSplitDisplay;
//		
//	}

	public Div getDivSubGrid() {
		return this.divSubGrid;
		
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

//	public Dialog getDialogForShow() {
//		return dialogForShow;
//	}
//
//	public void setDialogForShow(Dialog dialogForShow) {
//		this.dialogForShow = dialogForShow;
//	}

	public Object saveSelectedRow(String apiname) {
//		System.out.println("DynamicViewGrid.saveSelectedRow() --->" + selectedRow.getRowJSon().toString());
		if (selectedRow.getResourceName().equals("CR-FormTemplate")) 
			selectedRow.setCol9(getApiID(apiname));
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
	private String getApiID(String apiname) { // sets Id ApiTemplate see APITemplate table
		if ("anpas".equals(apiname))
			return "1";
		else if ("GFER".equals(apiname))
			return "2";
		else if ("monbus".equals(apiname))
			return "3";
		else if ("metadata".equals(apiname))
			return "4";		
		else if ("XesAcademy".equals(apiname))
			return "5";
		return "999999";
	}

	public Object colChanged(DynamicDBean item, String colName, LocalDate newDate) {
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
		String newValue = null;
		if (newDate != null)
			newValue = df.format(java.sql.Date.valueOf(newDate));
		colChanged(item, colName, newValue);
		return null;
		
	}
	public Object colChanged(DynamicDBean item, String colName, String newValue) {
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
	public Object colChanged(DynamicDBean item, String colName, Boolean newValue) {
		
		String newValueStr = "false";
		if (newValue)
			newValueStr = "true";
		colChanged(item, colName, newValueStr);
	return null;
	}
	public Object colChangedComboBox(DynamicDBean item, String colName, DynamicDBean newValue) {
		
		System.out.println("DynamicViewGrid.colChangedComboBox() colName " + colName + " Value " + item.getCol0()+  "/" + item.getCol117()  + " newValue " + newValue.getCol0() +  "/" + newValue.getCol11() );
		colChanged(item, colName, newValue.getCol0()); // gets the id from bean select in comboBox
		return null;
	}
	
	public Object saveRowGridIfNotInserting(Hashtable<String, DynamicDBean> beansToSaveAndRefresh2, String beanTobeSave) {
//		System.out.println("DynamicViewGrid.saveSelectedRow() --->" + row.getRowJSon().toString());
		if ((dataProvider.getHasNewRow() == true || isInsertingALine == false) && autoSaveGrid == true)
		{
			dataProvider.save(beanTobeSave, beansToSaveAndRefresh2);	 
			dataProvider.setHasNewRow(false);
			try {
				if (display!= null && parentRow !=null)
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
		if (keepRowBeforChanges != null)
			return null;
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
		System.out.println("DynamicViewGrid.insertANewRow()");
		// TODO Auto-generated method stub
//		dataProvider.insertANewRow();	
		insertBean();
		return null;
	}
	public Object insertBeanInList() {
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
		if (divDisplay != null)
			divDisplay.setVisible(b);
		if (divSubGrid != null)
			divSubGrid.setVisible(b);
//		if (buttonsForm != null)
//			buttonsForm.setVisible(b);
		
	}

	public void setButtonsForm(FormButtonsBar buttons) {
		this.buttonsForm = buttons;
		
	}

	public boolean isHasSideDisplay() {
		return hasSideDisplay;
	}

	public void setHasSideDisplay(boolean hasSideDisplay) {
		this.hasSideDisplay = hasSideDisplay;
	}

	public boolean isAutoSaveGrid() {
		return autoSaveGrid;
	}

	public void setAutoSaveGrid(boolean autoSaveGrid) {
		this.autoSaveGrid = autoSaveGrid;
	}
	
	public void showError(String error) {
		Label content = new Label(error);
		NativeButton buttonInside = new NativeButton("Cerrar");
		Notification notification = new Notification(content, buttonInside);
//		notification.setDuration(3000);
		buttonInside.addClickListener(event -> notification.close());
		notification.setPosition(Position.MIDDLE);
		notification.open();
			
		}
//	@Override
//	protected CrudEntityPresenter<DynamicDBean> getPresenter() {
//		// TODO Auto-generated method stub
//		return null;
//	}

	public Object PrintARow() {
//		String urlBase = "../dymanic";
//		String hostName = InetAddress.getLocalHost().getHostName() ;
//		if (hostName.indexOf(".local") == -1) // to diferent when is running in local (Maven) or in remote (tys.war -> tomcat)
//			urlBase= "../tys/dymanic";
//		String reportName = "repo%3A%2F%7E%2Ftys%2FPropPedido%2520JS%2520CR.rpt";
		System.out.println("DynamicViewGrid.PrintARow()");
		if (selectedRow.getParams() != null )
		{		
		if (selectedRow.getParams().indexOf("reportName") != -1 && selectedRow.getParams().indexOf("reportSf") != -1)
		{
			
			String parReportName = selectedRow.getParams().substring(selectedRow.getParams().indexOf("reportName")+11);
			int idxNextAnd = parReportName.indexOf("&");
			int  idxLast = parReportName.length();
			if (idxNextAnd > -1)
				idxLast = idxNextAnd;
			String reportName = parReportName.substring(1,idxLast-1);
			String parReportSf = selectedRow.getParams().substring(selectedRow.getParams().indexOf("reportSf")+9);
			idxNextAnd = parReportSf.indexOf("&");
			idxLast = parReportSf.length();
			if (idxNextAnd > -1)
				idxLast = idxNextAnd;
			String reportSf = parReportSf.substring(1,idxLast-1);
			int idxStartField = reportSf.indexOf("<<")+ 2;  // @@TODO prepare more more than one field
			if (idxStartField > 1)
			{
				int idxEndField = reportSf.indexOf(">>");
				String field=reportSf.substring(idxStartField, idxEndField);
				if ( selectedRow.getRowJSon().get(field) != null)
				{
					String value = selectedRow.getRowJSon().get(field).asText();
					reportSf = reportSf.replace("<<"+field+">>", value ); // TODO @@ poner comillas si el valor  es un string necesario pero encode en HTML
				}
				else
				{
					DataService.get().showError("Fórmula de selección se refiere a un campo que no existe en el recurso y/o formula de selección ( revisar en el  recurso row.reportSf los <<nombrecampo>>)");
				}
			}
			String sf = "&sf=" + reportSf;
			String datasource = "&DataSource=DB11_"+ UtilSessionData.getCompanyYear();
			String url = AppConst.CLEAR_REPORT_SERVER+"?report="+reportName+"&init=htm"+sf+datasource;
			System.out.println("DynamicViewGrid.PrintARow() URL para report ->"+url);
			UI.getCurrent().getPage().executeJs("window.open('"+url+"', '_blank');");
			}
			else
			{
				DataService.get().showError("Falta por definir en los parametros de el recurso nombre de report y/o formula de selección ( poner en el evento del recurso row.reportName y row.reportSf)");
			}
		}
		else
		{
			DataService.get().showError("Falta por definir en el recurso los parametros con el nombre de report y/o formula de selección ( poner en el evento del recurso row.reportName y row.reportSf)");
		}
		return null;
	}



}
