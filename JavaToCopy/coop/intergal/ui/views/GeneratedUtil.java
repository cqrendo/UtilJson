package coop.intergal.ui.views;

import static coop.intergal.AppConst.PACKAGE_VIEWS;

import java.lang.reflect.Method;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.StringTokenizer;
import java.util.regex.Pattern;

import com.fasterxml.jackson.databind.JsonNode;
import com.vaadin.flow.component.AttachEvent;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.datepicker.DatePicker;
import com.vaadin.flow.component.dependency.CssImport;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.formlayout.FormLayout.FormItem;
import com.vaadin.flow.component.formlayout.FormLayout.ResponsiveStep;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.component.textfield.TextFieldVariant;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.data.converter.LocalDateToDateConverter;
import com.vaadin.flow.dom.DomEvent;
import com.vaadin.flow.router.BeforeEnterEvent;
import com.vaadin.flow.router.BeforeEnterObserver;
import com.vaadin.flow.router.HasDynamicTitle;
import com.vaadin.flow.router.Location;
import com.vaadin.flow.router.QueryParameters;

import coop.intergal.AppConst;
import coop.intergal.espresso.presutec.utils.JSonClient;
import coop.intergal.ui.components.detailsdrawer.DetailsDrawer;
import coop.intergal.ui.components.detailsdrawer.DetailsDrawerFooter;
import coop.intergal.ui.components.detailsdrawer.DetailsDrawerHeader;
import coop.intergal.ui.util.FontSize;
import coop.intergal.ui.util.LumoStyles;
import coop.intergal.ui.util.TextColor;
import coop.intergal.ui.util.UIUtils;
import coop.intergal.ui.utils.converters.CurrencyFormatter;
import coop.intergal.vaadin.rest.utils.DdbDataBackEndProvider;
import coop.intergal.vaadin.rest.utils.DynamicDBean;

//@PageTitle("Payments")
//@Route(value = "gridDetails", layout = MainLayout.class)
@CssImport(value = "./styles/monbusstyle.css")
@CssImport(value = "./styles/monbusstyle-form-item.css", themeFor = "vaadin-form-item")
public class GeneratedUtil  {//, AfterNavigationListener {


	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
private static final String CLASSNAME_FOR_FORM_QUERY = ".formMargin50.formMarginL50";
//	private Grid<DynamicDBean> grid;
	private DynamicViewGrid grid;

 //   private ListDataProvider<Payment> dataProvider;
//	private DdbDataBackEndProvider dataProvider;
    private DetailsDrawer detailsDrawer;
	private Binder<DynamicDBean> binder;
//	private FormLayout form;
//	private ArrayList<String[]> rowsColList;
//	private ArrayList<String[]> rowsFieldList;
//	private ArrayList<String[]> rowsColListGrid;
	private CurrencyFormatter currencyFormatter = new CurrencyFormatter();
	private Hashtable<String, DynamicDBean> beansToSaveAndRefresh = new Hashtable<String, DynamicDBean>(); // to send DynamicDBean to be save and refresh, the name of the one to be save is send in another param

	private String title;
	private String resource;
	private DynamicDBean bean;
	private Dialog dialogForPick;
	private String pickMapFields; 




    
    public DynamicDBean getBean() {
		return bean;
	}

	public void setBean(DynamicDBean bean) {
		this.bean = bean;
	}

	public DynamicViewGrid getGrid() {
		return grid;
	}

	public void setGrid(DynamicViewGrid grid) {
		this.grid = grid;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
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
 
 
    public Component createDetails(ArrayList<String[]> rowsFieldList, FormLayout form, Boolean isQuery ) {
 			this.binder = new Binder<DynamicDBean>(DynamicDBean.class);
//			rowsFieldList = dataProvider.getRowsFieldList(cache);
		    if (bean != null)
				binder.setBean(bean);

			Iterator<String[]> itRowsFieldList = rowsFieldList.iterator();
			if (form == null)
				form = new FormLayout();
			form.removeAll();
			form.setResponsiveSteps(
//				new ResponsiveStep("31em",1),
//				new ResponsiveStep("32em",2),
//				new ResponsiveStep("33em",3),
//				new ResponsiveStep("34em",4),
//				new ResponsiveStep("45em",5),
//				new ResponsiveStep("46em",6),
//				new ResponsiveStep("47em",7),
//				new ResponsiveStep("48em",8),
//				new ResponsiveStep("49em",9),
//				new ResponsiveStep("50em",10),
//				new ResponsiveStep("51em",11),
//				new ResponsiveStep("52em",12),
//				new ResponsiveStep("63em",13),
//				new ResponsiveStep("64em",14),
//				new ResponsiveStep("65em",15),
//				new ResponsiveStep("66em",16));
					calculateResponsiveStep(1),
					calculateResponsiveStep(2),
					calculateResponsiveStep(3),
					calculateResponsiveStep(4),
					calculateResponsiveStep(5),
					calculateResponsiveStep(6),
					calculateResponsiveStep(7),
					calculateResponsiveStep(8),
					calculateResponsiveStep(9),
					calculateResponsiveStep(10),
					calculateResponsiveStep(11),
					calculateResponsiveStep(12),
					calculateResponsiveStep(13),
					calculateResponsiveStep(14),
					calculateResponsiveStep(15),
					calculateResponsiveStep(16),
					calculateResponsiveStep(17),
					calculateResponsiveStep(18),
					calculateResponsiveStep(19),
					calculateResponsiveStep(20),
					calculateResponsiveStep(21),
					calculateResponsiveStep(22),
					calculateResponsiveStep(23),
					calculateResponsiveStep(24),
					calculateResponsiveStep(25),
					calculateResponsiveStep(26),
					calculateResponsiveStep(27),
					calculateResponsiveStep(28),
					calculateResponsiveStep(29),
					calculateResponsiveStep(30),
					calculateResponsiveStep(31),
					calculateResponsiveStep(32),
			calculateResponsiveStep(33),
			calculateResponsiveStep(34),
			calculateResponsiveStep(35),
			calculateResponsiveStep(36),
			calculateResponsiveStep(37),
			calculateResponsiveStep(38),
			calculateResponsiveStep(39),
			calculateResponsiveStep(40));
					
//					new ResponsiveStep("15em",1),
//					new ResponsiveStep("16em",2),
//					new ResponsiveStep("16.5em",3),
//					new ResponsiveStep("17em",4),
//					new ResponsiveStep("17.5em",5),
//					new ResponsiveStep("18em",6),
//					new ResponsiveStep("18.5em",7),
//					new ResponsiveStep("19em",8),
//					new ResponsiveStep("19.5em",9),
//					new ResponsiveStep("20em",10),
//					new ResponsiveStep("20.5em",11),
//					new ResponsiveStep("21em",12),
//					new ResponsiveStep("21.5em",13),
//					new ResponsiveStep("22em",14),
//					new ResponsiveStep("22.5em",15),
//					new ResponsiveStep("23em",16),
//					new ResponsiveStep("23.5em",17),
//					new ResponsiveStep("24em",18),
//					new ResponsiveStep("24.5em",19),
//					new ResponsiveStep("25em",20),
//					new ResponsiveStep("25.5em",21),
//					new ResponsiveStep("26em",22),
//					new ResponsiveStep("26.5em",23),
//					new ResponsiveStep("37em",24),
//					new ResponsiveStep("37.5em",25),
//					new ResponsiveStep("38em",26),
//					new ResponsiveStep("38.5em",27),
//					new ResponsiveStep("58em",28),
//					new ResponsiveStep("62em",19),
//					new ResponsiveStep("66em",30),
//					new ResponsiveStep("70.5em",31),
//					new ResponsiveStep("74em",32));
			
			int i = 0;
			int ii = 0;
			Div div = new Div();
		//	FormLayout.FormItem item = formLayout.addFormItem(phoneLayout, "Phone");
//			form.setColSpan(item, 2);
			int nRow = 0;
			while (itRowsFieldList.hasNext())
			{
//				Label label = new Label(itRowsColList.next()[0]);
//				label.setWidth("500px");
				String[] rowField = itRowsFieldList.next();
				String filedName = rowField[0];
				boolean isReadOnly = isReadOnly( rowField [1]);
				boolean isRequired = isRequired( rowField [1]);
				String label = rowField[6];
				String fieldNameInUI = rowField[2];
				String idFieldType = rowField[3];
				String fieldWidth = rowField[7];
				String fieldSize = rowField[13];
				String classNames =  rowField[8];
				String classNamesForm = ""; 
				String classNamesItem = ""; 
				String classNamesItemQuery = rowField[12];
				String [] tokens = classNames.split(Pattern.quote("."));
				int iii = 0;
				while (tokens.length > iii)
				{ 
					if (tokens[iii].indexOf("form") > -1 )  // form CSS must include form in his name
						classNamesForm = classNamesForm + "." + tokens[iii];
					else
						classNamesItem = classNamesItem + "." + tokens[iii];
					iii ++;
				}
				if (nRow == 0)
				{
					form.setClassName("");
					if(isQuery)
						form = addClassNames(form,CLASSNAME_FOR_FORM_QUERY);
					else
						form = addClassNames(form,classNamesForm.trim());
					title = rowField[9];
					
				}
		
				nRow ++;
				
				System.out.println("DetailsPreview.createDetails()" +" filedName " + filedName + " "+classNames  );

				if( fieldWidth.isEmpty())
					fieldWidth = "10";
				
				int idxMark = fieldWidth.indexOf("#");
				Integer colspan = 0;
				if (idxMark == -1)
				{
					colspan = new Integer (fieldWidth);
					fieldWidth = (colspan)+1+"em";
				}
				else
				{
					colspan = new Integer (fieldWidth.substring(0,idxMark));
					fieldWidth = fieldWidth.substring(idxMark+1)+"em";
				}
				
				TextField tf = new TextField();//itRowsColList.next()[0]);
				tf.setId("tf"+fieldNameInUI);
				tf.setReadOnly(isReadOnly);
				tf.addThemeVariants(TextFieldVariant.LUMO_SMALL);
				if (filedName.equals("#SPACE#"))
				{
					Span s = new Span();
					FormLayout.FormItem item = form.addFormItem(s, label );
					item = addClassNames(item, classNamesItem);
					item.setId(fieldNameInUI);
					form.setColspan(item, colspan);
				}
				else if (idFieldType.equals("1") && isQuery == false) // is Date
				{
					DatePicker dp = new DatePicker();
					dp.getElement().setAttribute("theme", "small");
					boolean isRightLabel = false;
//					if (label.endsWith("#"))isRightLabel = true;
					Div l = alingLabel(label); 
					binder.forField((DatePicker) dp)
					.withConverter(new LocalDateToDateConverter( ZoneId.systemDefault()))
					.bind(d-> d.getColDate(fieldNameInUI), (d,v)-> d.setColDate(v,fieldNameInUI));//DynamicDBean::setCol2Date);	
					FormLayout.FormItem item = form.addFormItem(dp, l );
//					if (isRightLabel)
//						item.addClassName("filabelright");
//					else
					item = addClassNames(item, classNamesItem);
					item.setId(fieldNameInUI);
					form.setColspan(item, colspan);
					dp.setWidth(fieldWidth);
				}
				else
				{
					binder.bind(tf, fieldNameInUI);
//				form.add(fi);
					boolean isRightLabel = false;
//					if (label.endsWith("#"))isRightLabel = true;
					Div l = alingLabel(label); 
					FormLayout.FormItem item = form.addFormItem(tf, l );
//					if (isRightLabel)
//						item.addClassName("filabelright");
//					else
					if (isQuery)
						{
//				        Tooltip tooltip = new Tooltip();
//				        tooltip.attachToComponent(tf);
//				        tooltip.setPosition(TooltipPosition.RIGHT);
//				        tooltip.setAlignment(TooltipAlignment.LEFT);
//				        tooltip.add("Hola");
//				        tooltip.add(new Paragraph(TranslateResource.getFieldLocale("FABORRARAVISO", AppConst.PRE_CONF_PARAM)));
						item.getElement().setAttribute("title","Ayuda busqueda...."); 
						classNamesItem = classNamesItemQuery; 
						}
					if (isReadOnly)
					{
						tf.getElement().addEventListener("click", ev->showDialogForPick(ev, fieldNameInUI, tf));
					}
					item = addClassNames(item, classNamesItem);
					item.setId(fieldNameInUI);
					form.setColspan(item, colspan);
					tf.setWidth(fieldWidth);
					if (fieldSize.length() > 0)						
						tf.setMaxLength(new Integer(fieldSize));
					tf.setRequired(isRequired);
				}
				i++;
				
			}
			return form;
	    }
	private boolean isRequired(String params) {
		if (params == null)
			return false;
		if (params.indexOf("#REQ#")>-1)
			return true;
		else 
			return false;
	}

	private ResponsiveStep calculateResponsiveStep(int i) {
		int em = i * 22;
		String strEm =  em +"";
		strEm = strEm.substring(0,strEm.length()-1 ); 
//		System.out.println("GenericGridDetails.calculateResponsiveStep()......"+ strEm + "em");
		return new ResponsiveStep(strEm+"em",i);
	}
	private FormItem addClassNames(FormItem item, String classNames) {
		StringTokenizer tokens = new StringTokenizer(classNames,".");
		while (tokens.hasMoreElements())
		{
			String eachClass = tokens.nextToken();
			item.addClassName(eachClass);
		}
	return item;
}
	private FormLayout addClassNames(FormLayout formLayout, String classNames) {
		StringTokenizer tokens = new StringTokenizer(classNames,".");
		while (tokens.hasMoreElements())
		{
			String eachClass = tokens.nextToken();
			formLayout.addClassName(eachClass);
		}
	return formLayout;
}
	private Div alingLabel(String label) {
		Div l = new Div();
		l.add(label);
		if (label.endsWith("#"))
			{
			l.addClassName("labelright");
			label=label.substring(0,label.length()-1);
			l.setText(label);
			}
		return l;
	}

	private boolean isReadOnly(String params) {
		
		if (params == null)
			return false;
		if (params.indexOf("#CNoEDT#")>-1)
			return true;
		else 
			return false;
			
	}

private Object showDialogForPick(DomEvent ev, String fieldName, TextField tf) {
		
		try {
		DynamicGridForPick dynamicGridForPick = new DynamicGridForPick(); 
		String queryFormForPickClassName = null;
//		Object queryFormClassName = PACKAGE_VIEWS+queryParameters.getParameters().get("queryFormClassName").get(0);
		DynamicDBean currentRow = binder.getBean();
		String resourceName =currentRow.getResourceName();
		String filter="tableName='"+resourceName+"'%20AND%20FieldNameInUI='"+fieldName+"'";
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
//		queryFormForPickClassName = queryFormForPickClassName;
		if (queryFormForPickClassName.startsWith("coop.intergal.ui.views") == false)
			queryFormForPickClassName = PACKAGE_VIEWS+queryFormForPickClassName;
	
		DynamicViewGrid grid = dynamicGridForPick.getGrid();
		Class<?> dynamicQuery = Class.forName(queryFormForPickClassName);
		Object queryForm = dynamicQuery.newInstance();
//		Method setGrid = dynamicQuery.getMethod("setGrid", new Class[] {coop.intergal.tys.ui.views.DynamicViewGrid.class} );
		Method setGrid = dynamicQuery.getMethod("setGrid", new Class[] {coop.intergal.ui.views.DynamicViewGrid.class} );

		setGrid.invoke(queryForm,grid);
		if (queryFormForPickClassName.indexOf("Generated") > -1)
		{
			
			DdbDataBackEndProvider dataProvider = new DdbDataBackEndProvider();
			dataProvider.setPreConfParam(AppConst.PRE_CONF_PARAM);
			dataProvider.setResourceName(parentResource);
			Method setDataProvider= dynamicQuery.getMethod("setDataProvider", new Class[] {coop.intergal.vaadin.rest.utils.DdbDataBackEndProvider.class} );
			Method createContent= dynamicQuery.getMethod("createDetails");
			Method setRowsColList = dynamicQuery.getMethod("setRowsColList", new Class[] {java.util.ArrayList.class} );
			setDataProvider.invoke(queryForm,dataProvider );
//			setRowsColList.invoke(queryForm,rowsColList);
//			Method createContent= dynamicQuery.getMethod("createDetails");
//			queryForm = 
			createContent.invoke(queryForm);
		}
		dynamicGridForPick.getDivQuery().add((Component)queryForm);

		
		grid.setButtonsRowVisible(false);
		grid.setResourceName(parentResource);
		grid.setupGrid();
//			subDynamicViewGrid.getElement().getStyle().set("height","100%");
//		subDynamicViewGrid.setResourceName(resourceSubGrid);
//		if (resourceSubGrid.indexOf(".")> -1)
//			subDynamicViewGrid.setFilter(componFKFilter(bean, resourceSubGrid));
//		subDynamicViewGrid.setupGrid();
//		dynamicGridForPick.setRowsColList(currentRow.getRowsColList());
		dynamicGridForPick.addAcceptPickListener(e -> fillDataForPickAndAccept(grid.getGrid().getSelectedItems(),dialogForPick,currentRow, pickMapFields ));
		if (dialogForPick == null)
			dialogForPick = new Dialog();
		dialogForPick.removeAll();
		dialogForPick.add(dynamicGridForPick);
		dialogForPick.open();
		
		
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
		binder.setBean(currentRow);
		dialogForPick.close();
		return null;
	}
 
}
