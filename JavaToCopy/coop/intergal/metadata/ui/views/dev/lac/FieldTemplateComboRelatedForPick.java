package coop.intergal.metadata.ui.views.dev.lac;

import static coop.intergal.AppConst.PACKAGE_VIEWS;
import static coop.intergal.AppConst.PAGE_PRODUCTS;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.persistence.criteria.CriteriaBuilder.Case;

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

import coop.intergal.AppConst;
//import coop.intergal.tys.ui.views.comprasyventas.compras.ArticulosQueryForPick;//
//import coop.intergal.tys.ui.views.comprasyventas.compras.ProveedorQueryForPick;
import coop.intergal.ui.utils.converters.CurrencyFormatter;
import coop.intergal.ui.views.DynamicGridForPick.AcceptPickEvent;
import coop.intergal.vaadin.rest.utils.DynamicDBean;
import coop.intergal.vaadin.rest.utils.RestData;

import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.combobox.ComboBox;

//@Tag("dynamic-view-grid")
@Tag("combo-twin-for-pick")
@JsModule("./src/views/dev/lac/combo-twin-for-pick.js")
//@Route(value = PAGE_DYNAMIC+"forPICK")//, layout = MainView.class)
//@PageTitle(AppConst.TITLE_PRODUCTS)
//@Secured(Role.ADMIN)
//@Uses(ArticulosQueryForPick.class)  TODO check is works with last releases of vaadin
//@Uses(ProveedorQueryForPick.class) 
public class FieldTemplateComboRelatedForPick extends PolymerTemplate<TemplateModel> implements  HasDynamicTitle  {
	private ArrayList <String> rowsColList; //= getRowsCnew String[] { "code_customer", "name_customer", "cif", "amountUnDisbursedPayments" };
	private String preConfParam;
	public ArrayList<String> getRowsColList() {
		return rowsColList;
	}

	public void setRowsColList(ArrayList<String> rowsColList) {
		this.rowsColList = rowsColList;
	}	

	public FieldTemplateComboRelatedForPick(String childResourceName, String parentResourceName) {
		super();
		removeCombi1.setDisableOnClick(true);
		addNewCombo.addClickListener(e -> showNext());
		removeCombi2.addClickListener(e -> vlComboTwin2.setVisible(false));
		removeCombi3.addClickListener(e -> vlComboTwin3.setVisible(false));
		removeCombi4.addClickListener(e -> vlComboTwin4.setVisible(false));
		removeCombi5.addClickListener(e -> vlComboTwin5.setVisible(false));
		removeCombi6.addClickListener(e -> vlComboTwin6.setVisible(false));
		removeCombi7.addClickListener(e -> vlComboTwin7.setVisible(false));
		removeCombi8.addClickListener(e -> vlComboTwin8.setVisible(false));
		removeCombi9.addClickListener(e -> vlComboTwin9.setVisible(false));
		acceptPick.addClickShortcut(Key.F10);
		hideAll();
		cbChild1.setLabel(childResourceName);
		cBParent1.setLabel(parentResourceName);
		fillColletions(childResourceName, parentResourceName);
		fillCombo(1);

//		setupGrid();
		
	}

	public FieldTemplateComboRelatedForPick(TemplateParser parser, VaadinService service) {
		super(parser, service);
		// TODO Auto-generated constructor stub
	}

	public FieldTemplateComboRelatedForPick(TemplateParser parser) {
		super(parser);
		// TODO Auto-generated constructor stub
	}

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;


	private final Binder<DynamicDBean> binder = new Binder<>(DynamicDBean.class);
	

	private CurrencyFormatter currencyFormatter = new CurrencyFormatter();

	private String resourceName;
	private String title;
	private String filter;
	private static String mapedCols;
	@Id("acceptPick")
	private Button acceptPick;
	@Id("vlComboTwin1")
	private HorizontalLayout vlComboTwin1;
	@Id("vlComboTwin4")
	private HorizontalLayout vlComboTwin4;
	@Id("vlComboTwin5")
	private HorizontalLayout vlComboTwin5;
	@Id("vlComboTwin6")
	private HorizontalLayout vlComboTwin6;
	@Id("vlComboTwin7")
	private HorizontalLayout vlComboTwin7;
	@Id("vlComboTwin8")
	private HorizontalLayout vlComboTwin8;
	@Id("vlComboTwin9")
	private HorizontalLayout vlComboTwin9;
	@Id("vaadinHorizontalLayout")
	private HorizontalLayout vaadinHorizontalLayout;
	@Id("dummy")
	private Button removeCombi1;
	@Id("cbChild1")
	private ComboBox<DynamicDBean> cbChild1;
	@Id("cBParent1")
	private ComboBox<DynamicDBean> cBParent1;
	@Id("cbChild2")
	private ComboBox<DynamicDBean> cbChild2;
	@Id("cBParent2")
	private ComboBox<DynamicDBean> cBParent2;
	@Id("vlComboTwin3")
	private HorizontalLayout vlComboTwin3;
	@Id("removeCombi3")
	private Button removeCombi3;
	@Id("cbChild3")
	private ComboBox<DynamicDBean> cbChild3;
	@Id("cBParent3")
	private ComboBox<DynamicDBean> cBParent3;
	@Id("removeCombi4")
	private Button removeCombi4;
	@Id("cbChild4")
	private ComboBox<DynamicDBean> cbChild4;
	@Id("cBParent4")
	private ComboBox<DynamicDBean> cBParent4;
	@Id("vlComboTwin2")
	private HorizontalLayout vlComboTwin2;
	@Id("removeCombi2")
	private Button removeCombi2;
	@Id("removeCombi5")
	private Button removeCombi5;
	@Id("cbChild5")
	private ComboBox<DynamicDBean> cbChild5;
	@Id("cBParent5")
	private ComboBox<DynamicDBean> cBParent5;
	@Id("removeCombi6")
	private Button removeCombi6;
	@Id("cbChild6")
	private ComboBox<DynamicDBean> cbChild6;
	@Id("cBParent6")
	private ComboBox<DynamicDBean> cBParent6;
	@Id("removeCombi7")
	private Button removeCombi7;
	@Id("cbChild7")
	private ComboBox<DynamicDBean> cbChild7;
	@Id("cBParent7")
	private ComboBox<DynamicDBean> cBParent7;
	@Id("removeCombi8")
	private Button removeCombi8;
	@Id("cbChild8")
	private ComboBox<DynamicDBean> cbChild8;
	@Id("cBParent8")
	private ComboBox<DynamicDBean> cBParent8;
	@Id("removeCombi9")
	private Button removeCombi9;
	@Id("cbChild9")
	private ComboBox<DynamicDBean> cbChild9;
	@Id("cBParent9")
	private ComboBox<DynamicDBean> cBParent9;
	@Id("addNewCombo")
	private Button addNewCombo;

	Collection<DynamicDBean> dbeanListChild  ;
	Collection<DynamicDBean> dbeanListParent ;
	@Id("dummy")
	private Button dummy;


	protected String getBasePage() {
		return PAGE_PRODUCTS;
	}

//	@Override
	protected Binder<DynamicDBean> getBinder() {
		return binder;
	}

	public Button showButtonClickedMessage()
	{
		return null;
		
	}

//	@Override
//	public void beforeEnter(BeforeEnterEvent event) {
//		QueryParameters queryParameters = event.getLocation().getQueryParameters();
//		filter = null; 
//		List<String> parFIlter = queryParameters.getParameters().get("filter");
//		if (parFIlter != null)
//			{
//			filter = parFIlter.get(0);
//			filter=filter.replace("EEQQ", "=");
//			}
//		title="..";
//		String queryFormClassName = null;
//		String displayFormClassName  = null;
//		String resourceSubGrid = null;
//		if (queryParameters != null && !queryParameters.getParameters().isEmpty())
//		{
//			title=queryParameters.getParameters().get("title").get(0);
//			resourceName = queryParameters.getParameters().get("resourceName").get(0);
//			queryFormClassName = PACKAGE_VIEWS+queryParameters.getParameters().get("queryFormClassName").get(0);
//			displayFormClassName = PACKAGE_VIEWS+queryParameters.getParameters().get("displayFormClassName").get(0);
////			resourceSubGrid =  queryParameters.getParameters().get("resourceSubGrid").get(0);
//		}
//		try {
//			Class<?> dynamicQuery = Class.forName(queryFormClassName);
//			Object queryForm = dynamicQuery.newInstance();
//			Method setGrid = dynamicQuery.getMethod("setGrid", new Class[] {coop.intergal.ui.views.DynamicViewGrid.class} );
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
//		} 		

//		grid.setDisplayFormClassName(displayFormClassName);
//		grid.setResourceName(resourceName);
//		grid.setupGrid();

		
//	}
	private void fillColletions(String chldTable, String parentTable) {
		
//		comboTopCustomer
//		Collection<DynamicDBean> countriesData = ExampleUtil.getCountriesData();
		String filterChild = "tableName='"+chldTable+"'%20AND%20fieldName%3C%3E'%23SPACE%23'&order=fieldName"; //.> AND fieldName<>'%23SPACE%23'&order=fieldName"
		String filterParent = "tableName='"+parentTable+"'%20AND%20fieldName%3C%3E'%23SPACE%23'&order=fieldName";
//		dataProvider.setFilter(filter);
		ArrayList<String[]> rowsColList = new ArrayList<String[]>();
		String[] fieldArr  = new String[3];
		fieldArr[0]  = "fieldName";
		fieldArr[1] = "";
		fieldArr[2] = "col0";	
		rowsColList.add(fieldArr);
		fieldArr  = new String[3];
		fieldArr[0]  = "FieldNameInUI";
		fieldArr[1] = "";
		fieldArr[2] = "col1";
		rowsColList.add(fieldArr);
		dbeanListChild  = RestData.getResourceData(0,0,"FieldTemplate", AppConst.PRE_CONF_PARAM_METADATA, rowsColList, filterChild, false, new Boolean(false));
		dbeanListParent = RestData.getResourceData(0,0,"FieldTemplate", AppConst.PRE_CONF_PARAM_METADATA, rowsColList, filterParent, false, new Boolean(false));
	}
	private void fillCombo(int line) {
		switch (line)
		{
		     case 1:
		 		cbChild1.setItems(dbeanListChild);
				cbChild1.setItemLabelGenerator(DynamicDBean::getCol0);
				cBParent1.setItems(dbeanListParent);
				cBParent1.setItemLabelGenerator(DynamicDBean::getCol0);
		     ;
		     case 2:
			 	cbChild2.setItems(dbeanListChild);
				cbChild2.setItemLabelGenerator(DynamicDBean::getCol0);
				cBParent2.setItems(dbeanListParent);
				cBParent2.setItemLabelGenerator(DynamicDBean::getCol0);
		     ;
		     case 3:
			 	cbChild3.setItems(dbeanListChild);
				cbChild3.setItemLabelGenerator(DynamicDBean::getCol0);
				cBParent3.setItems(dbeanListParent);
				cBParent3.setItemLabelGenerator(DynamicDBean::getCol0);
		     ;
		     case 4:
			 	cbChild4.setItems(dbeanListChild);
				cbChild4.setItemLabelGenerator(DynamicDBean::getCol0);
				cBParent4.setItems(dbeanListParent);
				cBParent4.setItemLabelGenerator(DynamicDBean::getCol0);
		     ;
		     case 5:
			 	cbChild5.setItems(dbeanListChild);
				cbChild5.setItemLabelGenerator(DynamicDBean::getCol0);
				cBParent5.setItems(dbeanListParent);
				cBParent5.setItemLabelGenerator(DynamicDBean::getCol0);
		     ;
		     case 6:
			 	cbChild6.setItems(dbeanListChild);
				cbChild6.setItemLabelGenerator(DynamicDBean::getCol0);
				cBParent6.setItems(dbeanListParent);
				cBParent6.setItemLabelGenerator(DynamicDBean::getCol0);
		     ;
		     case 7:
			 	cbChild7.setItems(dbeanListChild);
				cbChild7.setItemLabelGenerator(DynamicDBean::getCol0);
				cBParent7.setItems(dbeanListParent);
				cBParent7.setItemLabelGenerator(DynamicDBean::getCol0);
		     ;
		     case 8:
			 	cbChild8.setItems(dbeanListChild);
				cbChild8.setItemLabelGenerator(DynamicDBean::getCol0);
				cBParent8.setItems(dbeanListParent);
				cBParent8.setItemLabelGenerator(DynamicDBean::getCol0);
		     ;
		     case 9:
			 	cbChild9.setItems(dbeanListChild);
				cbChild9.setItemLabelGenerator(DynamicDBean::getCol0);
				cBParent9.setItems(dbeanListParent);
				cBParent9.setItemLabelGenerator(DynamicDBean::getCol0);
		     ;
		     default:
//			 	cbChild1.setItems(dbeanListChild);
//				cbChild1.setItemLabelGenerator(DynamicDBean::getCol0);
//				cBParent1.setItems(dbeanListParent);
//				cBParent1.setItemLabelGenerator(DynamicDBean::getCol0);		     ;
		}

		
	}

	private void hideAll() {
		vlComboTwin2.setVisible(false);
		vlComboTwin3.setVisible(false);
		vlComboTwin4.setVisible(false);
		vlComboTwin5.setVisible(false);
		vlComboTwin6.setVisible(false);
		vlComboTwin7.setVisible(false);
		vlComboTwin8.setVisible(false);
		vlComboTwin9.setVisible(false);
		
	}
	private void showNext() {
		
		if (vlComboTwin8.isVisible())
		{
			vlComboTwin9.setVisible(true);
		}
		else if (vlComboTwin7.isVisible())
		{
			vlComboTwin8.setVisible(true);
		}
		else if (vlComboTwin6.isVisible())
		{
			vlComboTwin7.setVisible(true);
		}
		else if (vlComboTwin5.isVisible())
		{
			vlComboTwin6.setVisible(true);
		}
		else if (vlComboTwin4.isVisible())
		{
			vlComboTwin5.setVisible(true);
		}
		else if (vlComboTwin3.isVisible())
		{
			vlComboTwin4.setVisible(true);
		}
		else if (vlComboTwin2.isVisible())
		{
			vlComboTwin3.setVisible(true);
		}
		else if (vlComboTwin1.isVisible())
		{
			fillCombo(2);
			vlComboTwin2.setVisible(true);
		}

		
	}
	@Override
	public String getPageTitle() {
		return title;
	}

	public static class AcceptPickEvent extends ComponentEvent<FieldTemplateComboRelatedForPick> {
		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;

		public AcceptPickEvent(FieldTemplateComboRelatedForPick source, boolean fromClient) {
			
			super(source, fromClient);
		}
	}

	public Registration addAcceptPickListener(ComponentEventListener<AcceptPickEvent> listener) {
	return acceptPick.addClickListener(e -> {compomMapedCols();listener.onComponentEvent(new AcceptPickEvent(this, true));});
}


	private void compomMapedCols() {
		DynamicDBean child1 = cbChild1.getValue();
		DynamicDBean parent1 = cBParent1.getValue();
		mapedCols =  child1.getCol1() + ";"+ parent1.getCol1();
		if (vlComboTwin2.isVisible())
		{
			DynamicDBean child2 = cbChild2.getValue();
			DynamicDBean parent2 = cBParent2.getValue();
			mapedCols =  mapedCols + "#"+ child2.getCol1() + ";"+ parent2.getCol1();
		}
		if (vlComboTwin3.isVisible())
		{
			DynamicDBean child3 = cbChild3.getValue();
			DynamicDBean parent3 = cBParent3.getValue();
			mapedCols =  mapedCols + "#"+ child3.getCol1() + ";"+ parent3.getCol1();
		}
		if (vlComboTwin4.isVisible())
		{
			DynamicDBean child4 = cbChild4.getValue();
			DynamicDBean parent4 = cBParent4.getValue();
			mapedCols =  mapedCols + "#"+ child4.getCol1() + ";"+ parent4.getCol1();
		}
		if (vlComboTwin5.isVisible())
		{
			DynamicDBean child5 = cbChild5.getValue();
			DynamicDBean parent5 = cBParent5.getValue();
			mapedCols =  mapedCols + "#"+ child5.getCol1() + ";"+ parent5.getCol1();
		}
		if (vlComboTwin6.isVisible())
		{
			DynamicDBean child6 = cbChild6.getValue();
			DynamicDBean parent6 = cBParent6.getValue();
			mapedCols =  mapedCols + "#"+ child6.getCol1() + ";"+ parent6.getCol1();
		}
		if (vlComboTwin7.isVisible())
		{
			DynamicDBean child7 = cbChild7.getValue();
			DynamicDBean parent7 = cBParent7.getValue();
			mapedCols =  mapedCols + "#"+ child7.getCol1() + ";"+ parent7.getCol1();
		}
		if (vlComboTwin8.isVisible())
		{
			DynamicDBean child8 = cbChild8.getValue();
			DynamicDBean parent8 = cBParent8.getValue();
			mapedCols =  mapedCols + "#"+ child8.getCol1() + ";"+ parent8.getCol1();
		}
		if (vlComboTwin9.isVisible())
		{
			DynamicDBean child9 = cbChild9.getValue();
			DynamicDBean parent9 = cBParent9.getValue();
			mapedCols =  mapedCols + "#"+ child9.getCol1() + ";"+ parent9.getCol1();
		}
		
	}

	public String getMapedCols() {
		// TODO Auto-generated method stub
		return mapedCols;
	}


//	@Override
//	protected CrudEntityPresenter<DynamicDBean> getPresenter() {
//		// TODO Auto-generated method stub
//		return null;
//	}
}
