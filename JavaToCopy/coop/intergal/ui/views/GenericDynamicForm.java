package coop.intergal.ui.views;
import static coop.intergal.AppConst.PACKAGE_VIEWS;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.Optional;
import java.util.Set;
import java.util.StringTokenizer;

import org.vaadin.textfieldformatter.NumeralFieldFormatter;

import com.fasterxml.jackson.databind.JsonNode;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.checkbox.Checkbox;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.polymertemplate.PolymerTemplate;
import com.vaadin.flow.component.textfield.IntegerField;
import com.vaadin.flow.component.textfield.TextArea;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.component.textfield.TextFieldVariant;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.data.converter.LocalDateToDateConverter;
import com.vaadin.flow.data.provider.ListDataProvider;
import com.vaadin.flow.dom.DomEvent;
import com.vaadin.flow.templatemodel.TemplateModel;

import coop.intergal.AppConst;
import coop.intergal.espresso.presutec.utils.JSonClient;
import coop.intergal.tys.ui.views.comprasyventas.articulos.HistogramaEvolMesForm;
import coop.intergal.ui.components.EsDatePicker;
import coop.intergal.ui.components.FormButtonsBar;
import coop.intergal.ui.util.UtilSessionData;
import coop.intergal.ui.utils.converters.CurrencyFormatter;
import coop.intergal.ui.utils.converters.DecimalFormatter;
import coop.intergal.vaadin.rest.utils.DdbDataBackEndProvider;
import coop.intergal.vaadin.rest.utils.DynamicDBean;
import coop.intergal.vaadin.rest.utils.RestData;

public class GenericDynamicForm extends PolymerTemplate<TemplateModel> {
	
/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
private ArrayList<String[]> rowsColList;
protected Binder<DynamicDBean> binder;
private CurrencyFormatter currencyFormatter = new CurrencyFormatter();
private Dialog dialogForPick;
private String pickMapFields; 
private static DecimalFormatter decimalFormatter = new DecimalFormatter();



//	public interface CrudForm<E> {
//		FormButtonsBar getButtons();
//
//		HasText getTitle();
//
//		void setBinder(Binder<E> binder);
//
//		void setBean(DynamicDBean bean);
//
//		void setRowsColList(ArrayList<String> rowsColList);
//	}

	public void setRowsColList(ArrayList<String[]> rowsColList) {
		this.rowsColList = rowsColList;
		
	}

	public ArrayList<String[]> getRowsColList() {
		return rowsColList;
	}

	public void setBinder(Binder<DynamicDBean> binder) {
		// TODO Auto-generated method stub
		
	}
	
	private FormButtonsBar buttonsForm;
	public FormButtonsBar getButtonsForm() {
		return buttonsForm;
	}

	public void setButtonsForm(FormButtonsBar buttonsForm) {
		this.buttonsForm = buttonsForm;
	}
	
	private DynamicViewGrid dVGrid;
	public DynamicViewGrid getDVGrid() {
		return dVGrid;
	}

	public void setDVGrid(DynamicViewGrid dVGrid) {
		this.dVGrid = dVGrid;
	}


//	public void setBean(DynamicDBean bean) {
//		// TODO Auto-generated method stub
//		
//	}
	
 
	public Dialog getDialogForPick() {
		return dialogForPick;
	}

	public void setDialogForPick(Dialog dialogForPick) {
		this.dialogForPick = dialogForPick;
	}

//	public void bindFields(Class<?> class1, Object object) {
//		super.bindFields(class1, object, getRowsColList().iterator(), binder); 
//	}
	public void bindFields(Class<?> class1, Object object) {
		bindFields(class1, object,null,null);
	}
	protected void bindFields(Class<?> class1, Object object,
			Binder<DynamicDBean> altBinder, String prefix) {
		Iterator<String[]> itRowsColList = getRowsColList().iterator(); // even is a field it uses RowColList (columns) , because is for a custom form where his field doesn't need to be defined as showInDisplay
		if (altBinder == null)
			altBinder = binder;
		else 
			itRowsColList = altBinder.getBean().getRowsColList().iterator();
		if (buttonsForm != null)
			buttonsForm.getCustomButtons().removeAll();
		while(itRowsColList.hasNext())
		{
			String[] rowCol = itRowsColList.next();
			String altField;
			if (prefix != null) 
				altField = rowCol [2].replace("col", prefix);
			else
				altField = rowCol [2];
			String fieldNameInUI = rowCol [2];
			String idFieldTypeStr = rowCol [3];
			String tagsForVisibility = rowCol[21].toString();
			String tagsForEdition = rowCol[22].toString();
			String idButtonBarForButtons = rowCol[25].toString();
			String label = rowCol[7].toString();//rowField[7].toString();
			boolean isPick = isPick (rowCol [1]);
			boolean isRequired = isRequired( rowCol [1]);
//			System.out.println("GenericDynamicForm.bindFields() col 0 ---> "+ altBinder.getBean().getCol0() + "/" +altBinder.getBean().getCol1());
			boolean visibleByTag = UtilSessionData.isVisibleOrEditableByTag(tagsForVisibility);
			boolean editableByTag = UtilSessionData.isVisibleOrEditableByTag(tagsForEdition);
			if (tagsForVisibility.indexOf("row.") > -1 && altBinder.getBean() != null && altBinder.getBean().getRowJSon() != null)  // visibility of the filed depends in a value of the row, create a virtual field that returns true or false , depending on condition
			{
				int idxStart = tagsForVisibility.indexOf("row.")+4;
				int idxEnd = tagsForVisibility.length();
				if (tagsForVisibility.indexOf(",") > -1 )
					idxEnd = tagsForVisibility.indexOf(",");
				String tagKey = tagsForVisibility.substring(idxStart, idxEnd )	;
				if (altBinder.getBean().getRowJSon().get(tagKey) != null)
					visibleByTag = altBinder.getBean().getRowJSon().get(tagKey).asBoolean();
			}
			int idFieldType = 0;
			if ( idFieldTypeStr.isEmpty() == false)
				idFieldType = new Integer (idFieldTypeStr);
			boolean isReadOnly = !editableByTag || isReadOnly( rowCol [1]);
			if (!fieldNameInUI.isEmpty())
			try {
				System.out.println("PedidoProveedorForm.bindFields() fieldName ...."  + fieldNameInUI);
				if (!fieldNameInUI.equals("null"))
				{	
					Field field = ((class1)).getDeclaredField(altField);//.get(instancia);
					field.setAccessible(true);
					Object fieldObj = field.get(object);
					if (idFieldType == 0)  // is Text Field
					{
						TextField tf = ((TextField) fieldObj);
						tf.setReadOnly(isReadOnly);
						if (!visibleByTag)
			//			//	tf.getElement().executeJs("this.shadowRoot.children[1].style='display: none;'");
							tf.getElement().getStyle().set("visibility","hidden");
			//			tf.setVisible(visibleByTag);
						if (isPick)
						{
							tf.getElement().addEventListener("click", ev->showDialogForPick(ev, fieldNameInUI, tf));
							Icon icon = new Icon(VaadinIcon.DOWNLOAD_ALT);
							tf.setSuffixComponent(icon);
						}
						if (isRequired)
							altBinder.forField(tf).asRequired("Requerido").bind(fieldNameInUI);
						else
							altBinder.bind(tf, fieldNameInUI);
					}
					else if (rowCol[3].equals("1")) // is Date
						{
						if (isRequired)
						{
							((EsDatePicker) fieldObj).setReadOnly(isReadOnly);
							altBinder.forField((EsDatePicker) fieldObj).asRequired("Requerido")
							.withConverter(new LocalDateToDateConverter( ZoneId.systemDefault()))
							.bind(d-> d.getColDate(fieldNameInUI), (d,v)-> d.setColDate(v,fieldNameInUI));//DynamicDBean::setCol2Date);	
	
						}
						else
						{
							((EsDatePicker) fieldObj).setReadOnly(isReadOnly);
							altBinder.forField((EsDatePicker) fieldObj)
							.withConverter(new LocalDateToDateConverter( ZoneId.systemDefault()))
							.bind(d-> d.getColDate(fieldNameInUI), (d,v)-> d.setColDate(v,fieldNameInUI));//DynamicDBean::setCol2Date);	
						}
					}
					else if (rowCol[3].equals("2")) // is TextArea
					{
						if (isRequired)
						{	((TextArea) fieldObj).setReadOnly(isReadOnly);
							if (!visibleByTag)
								((TextArea) fieldObj).getElement().getStyle().set("visibility","hidden");
							altBinder.forField((TextArea) fieldObj).asRequired("Requerido").bind(fieldNameInUI);
	//						altBinder.bind((TextArea) fieldObj, fieldName);
						}	
						else
						{
							((TextArea) fieldObj).setReadOnly(isReadOnly);
							if (!visibleByTag)
								((TextArea) fieldObj).getElement().getStyle().set("visibility","hidden");
							altBinder.bind((TextArea) fieldObj, fieldNameInUI);
						}	
					}
					else if (rowCol[3].equals("3")) // is currency
					{
			//			altBinder.bind((AmountField) fieldObj, fieldName);
						((TextField) fieldObj).addThemeVariants(TextFieldVariant.LUMO_ALIGN_RIGHT);
						((TextField) fieldObj).setReadOnly(isReadOnly);
						if (!visibleByTag)
							((TextField) fieldObj).getElement().getStyle().set("visibility","hidden");
						if (isRequired)
							altBinder.forField((TextField) fieldObj).asRequired("Requerido").bind(d -> currencyFormatter.encode(CurrencyFormatter.getCents(d.getCol(fieldNameInUI))), (d,v)-> d.setColInteger(v,fieldNameInUI));
						else
							altBinder.forField((TextField) fieldObj).bind(d -> currencyFormatter.encode(CurrencyFormatter.getCents(d.getCol(fieldNameInUI))), (d,v)-> d.setColInteger(v,fieldNameInUI));
			//			altBinder.forField((AmountField) fieldObj).bind(d-> d.getColInteger(fieldName), (d,v)-> d.setColInteger(v,fieldName));//DynamicDBean::setCol2Date);				

					}
					else if (idFieldType > 100 ) // is Decimal
					{
//						BigDecimalField bdf = new BigDecimalField();
						((TextField) fieldObj).addThemeVariants(TextFieldVariant.LUMO_ALIGN_RIGHT);
						((TextField) fieldObj).setReadOnly(isReadOnly);
						if (!visibleByTag)
							((TextField) fieldObj).getElement().getStyle().set("visibility","hidden");
						int nDecimals = idFieldType - 100 ; 

						new NumeralFieldFormatter(".", ",", nDecimals).extend(((TextField) fieldObj));
						if (isRequired)
							altBinder.forField((TextField) fieldObj)
							.asRequired("Requerido")
							.bind(d -> decimalFormatter.encode(decimalFormatter.getCents(d.getCol(fieldNameInUI),nDecimals)), (d,v)-> d.setColInteger(v,fieldNameInUI));
						else
							altBinder.forField((TextField) fieldObj)
							.bind(d -> decimalFormatter.encode(decimalFormatter.getCents(d.getCol(fieldNameInUI),nDecimals)), (d,v)-> d.setColInteger(v,fieldNameInUI));
					}
					else if (rowCol[3].equals("4")) // is Boolean
					{
						((Checkbox) fieldObj).setReadOnly(isReadOnly);
						if (!visibleByTag)
							((Checkbox) fieldObj).getElement().getStyle().set("visibility","hidden");
						if (isRequired)
							altBinder.forField((Checkbox) fieldObj)
							.asRequired("Requerido")
							.bind(d -> d.getColBoolean(fieldNameInUI), (d,v)-> d.setColBoolean(v,fieldNameInUI));
						else
							altBinder.forField((Checkbox) fieldObj).bind(d -> d.getColBoolean(fieldNameInUI), (d,v)-> d.setColBoolean(v,fieldNameInUI));
					}
					else if (idFieldType == 5 ) // is Number
					{
						IntegerField nf = ((IntegerField) fieldObj);
						nf.setReadOnly(isReadOnly);
						if (!visibleByTag)
							nf.getElement().getStyle().set("visibility","hidden");
						if (isRequired)
							altBinder.forField(nf).asRequired("Requerido").bind(d-> d.getColInteger(fieldNameInUI), (d,v)-> d.setColInteger(v,fieldNameInUI));
						else
							altBinder.forField(nf).bind(d-> d.getColInteger(fieldNameInUI), (d,v)-> d.setColInteger(v,fieldNameInUI));
					}
					else if (idFieldType == 6 ) // is Combobox
					{
						String parentResource = rowCol[20];
						ComboBox<DynamicDBean> cB = ((ComboBox<DynamicDBean>) fieldObj);
					//	cB = fillComboBox(parentResource);
						ArrayList<String[]> rowsColList = new ArrayList<String[]>();
						
						String[] fieldArr  = new String[3];
						fieldArr[0] = "STOREDVALUE";
						fieldArr[1] = "";
						fieldArr[2] = "col0";
						rowsColList.add(fieldArr);
						
						fieldArr  = new String[3];
						fieldArr[0] = "DISPLAYVALUE";
						fieldArr[1] = "";
						fieldArr[2] = "col1";
						rowsColList.add(fieldArr);
//						String parentResource = getParentResource(resourceName, fieldNameInUI);
						Collection<DynamicDBean> 	rowsListForCombo = RestData.getResourceData("!!ERROR!! combo sin Resource Parent, especificar en MetaConfig ");
						if (parentResource != null && parentResource.isEmpty() == false)
						{				
							rowsListForCombo = RestData.getResourceData(0,0,parentResource, UtilSessionData.getCompanyYear()+AppConst.PRE_CONF_PARAM, rowsColList, null, true, false, null);
						}
	//					ComboBox<DynamicDBean> cB = new ComboBox<DynamicDBean>() ;
						cB.setItems(rowsListForCombo);
						cB.setItemLabelGenerator(DynamicDBean::getCol1);
	
						
						
						cB.setId("tf"+fieldNameInUI);
//						nf.getElement().setAttribute("theme", "small");
						cB.setReadOnly(isReadOnly);
						if (!visibleByTag)
							cB.getElement().getStyle().set("visibility","hidden");
//						cB.setVisible(visibleByTag);  // in this way it covers the space with next field
						if (isRequired)
							altBinder.forField(cB).withConverter(
								item-> Optional.ofNullable(item).map(DynamicDBean::getCol0).orElse(null),
								id-> getRowById(id, cB))
							.asRequired("Requerido")
							.bind(d-> d.getCol(fieldNameInUI), (d,v)-> d.setCol(v,fieldNameInUI));
						else
							altBinder.forField(cB).withConverter(
									item-> Optional.ofNullable(item).map(DynamicDBean::getCol0).orElse(null),
									id-> getRowById(id, cB))
								.bind(d-> d.getCol(fieldNameInUI), (d,v)-> d.setCol(v,fieldNameInUI));
							
//						altBinder.forField(nf).bind(d-> d.getColInteger(fieldNameInUI), (d,v)-> d.setColInteger(v,fieldNameInUI));
					}
					else if (idFieldType == 10) // is a button
					{
			//			Button b = new Button(label);
						Button b =((Button) fieldObj);;
						b.setId(label);
						b.addClickListener(e-> proccesButton(b));
						b.setVisible(false); 
						if ((idButtonBarForButtons.equals("2") && visibleByTag)) // Botonera formulario
						{
						//	b.setVisible(false); 
						//	Button bCustom = new Button(label);
							Button bCustom =coop.intergal.ui.util.UIUtils.createPrimaryButton(label);							
							bCustom.setId(label);
							bCustom.addClickListener(e-> proccesButton(bCustom));
							if (buttonsForm != null)
								buttonsForm.getCustomButtons().add(bCustom);
						}
						else if (idButtonBarForButtons.equals("3")) // Formulario
						{
							b.setVisible(visibleByTag);
						}	
						}
				}
					
			} catch (NoSuchFieldException | SecurityException e) {
				System.err.println("Field not defined in Form... " + e.toString());
				
			} catch (IllegalArgumentException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			
			} catch (java.lang.ClassCastException e) {
				System.err.println("Field type incorret in Form with FiledTemplate... " + e.toString());
			}
		}	
			
//		}
		
	}
	private Object proccesButton(Button b) {
	System.out.println("proccesButton for col0 " + binder.getBean().getRowJSon().asText());
	GeneratedUtil generatedUtil = new GeneratedUtil();
//	binder.getBean().getCol0();
	generatedUtil.setBinder(binder);
	generatedUtil.setGrid(dVGrid);
	generatedUtil.proccesButton(b, binder.getBean());
	return null;
}

	private static boolean isPick(String params) {
		
		if (params == null)
			return false;
		if (params.indexOf("#PCK#")>-1)
			return true;
		else 
			return false;
			
	}
	private DynamicDBean getRowById(String id, ComboBox cB) {
		
		ListDataProvider<DynamicDBean> ListCombo = (ListDataProvider<DynamicDBean>) cB.getDataProvider();
		for (DynamicDBean bean : ListCombo.getItems()) {
		
			System.out.println("GeneratedUtil.getRowById()------>"+bean.getCol0());
			if(id !=null && id.equals(bean.getCol0()))
				return bean;
		}
		return null;
	}
	
	private boolean isRequired(String params) {
		if (params == null)
			return false;
		if (params.indexOf("#REQ#")>-1)
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
		String filter="tableName='"+currentRow.getResourceName()+"'%20AND%20FieldNameInUI='"+fieldName+"'";
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
//		Method setGrid = dynamicQuery.getMethod("setGrid", new Class[] {coop.intergal.tys.ui.views.DynamicViewGrid.class} );
		Method setGrid = dynamicQuery.getMethod("setGrid", new Class[] {coop.intergal.ui.views.DynamicViewGrid.class} );

		setGrid.invoke(queryForm,grid);
		if (queryFormForPickClassName.indexOf("Generated") > -1)
		{
			
			DdbDataBackEndProvider dataProvider = new DdbDataBackEndProvider();
			dataProvider.setPreConfParam(UtilSessionData.getCompanyYear()+AppConst.PRE_CONF_PARAM);
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
	//	dialogForPick.setDraggable(true);
		dialogForPick.setCloseOnEsc(true);
		dialogForPick.setResizable(true);
		

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
		if (seletedRows == null || seletedRows.isEmpty())
		{
			dialogForPick.close();
			return null;
		}
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

	private boolean isReadOnly(String params) {
		
		if (params.indexOf("#CNoEDT#")>-1)
			return true;
		else 
			return false;
			
	}


}
