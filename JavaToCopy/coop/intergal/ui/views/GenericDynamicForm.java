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
import com.vaadin.flow.component.checkbox.Checkbox;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.html.Div;
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
import coop.intergal.ui.components.EsDatePicker;
import coop.intergal.ui.utils.UtilSessionData;
import coop.intergal.ui.utils.converters.CurrencyFormatter;
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
		Iterator<String[]> itRowsColList = getRowsColList().iterator();
		
		while(itRowsColList.hasNext())
		{
			String[] rowCol = itRowsColList.next();
			String fieldName = rowCol [2];
			String idFieldTypeStr = rowCol [3];
			String fieldNameInUI = rowCol [2];
			String tagsForVisibility = rowCol[21].toString();
			String tagsForEdition = rowCol[22].toString();
			boolean isPick = isPick (rowCol [1]);

			boolean visibleByTag = UtilSessionData.isVisibleOrEditableByTag(tagsForVisibility);
			boolean editableByTag = UtilSessionData.isVisibleOrEditableByTag(tagsForEdition);
 
			int idFieldType = 0;
			if ( idFieldTypeStr.isEmpty() == false)
				idFieldType = new Integer (idFieldTypeStr);
			boolean isReadOnly = !editableByTag || isReadOnly( rowCol [1]);
			if (!fieldName.isEmpty())
			try {
//				System.out.println("PedidoProveedorForm.bindFields() fieldName ...."  + fieldName);
				if (!fieldName.equals("null"))
				{	
					Field field = ((class1)).getDeclaredField(fieldName);//.get(instancia);
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
							tf.getElement().addEventListener("click", ev->showDialogForPick(ev, fieldName, tf));
							Icon icon = new Icon(VaadinIcon.DOWNLOAD_ALT);
							tf.setSuffixComponent(icon);
						}
						binder.bind(tf, fieldName);
					}
					else if (rowCol[3].equals("1")) // is Date
						{
						((EsDatePicker) fieldObj).setReadOnly(isReadOnly);
						binder.forField((EsDatePicker) fieldObj)
						.withConverter(new LocalDateToDateConverter( ZoneId.systemDefault()))
						.bind(d-> d.getColDate(fieldName), (d,v)-> d.setColDate(v,fieldName));//DynamicDBean::setCol2Date);				
					}
					else if (rowCol[3].equals("2")) // is TextArea
					{
						
						((TextArea) fieldObj).setReadOnly(isReadOnly);
						if (!visibleByTag)
							((TextArea) fieldObj).getElement().getStyle().set("visibility","hidden");
						binder.bind((TextArea) fieldObj, fieldName);
					}
					else if (rowCol[3].equals("3")) // is currency
					{
			//			binder.bind((AmountField) fieldObj, fieldName);
						((TextField) fieldObj).addThemeVariants(TextFieldVariant.LUMO_ALIGN_RIGHT);
						((TextField) fieldObj).setReadOnly(isReadOnly);
						if (!visibleByTag)
							((TextField) fieldObj).getElement().getStyle().set("visibility","hidden");
						binder.forField((TextField) fieldObj).bind(d -> currencyFormatter.encode(CurrencyFormatter.getCents(d.getCol(fieldName))), (d,v)-> d.setColInteger(v,fieldName));
			//			binder.forField((AmountField) fieldObj).bind(d-> d.getColInteger(fieldName), (d,v)-> d.setColInteger(v,fieldName));//DynamicDBean::setCol2Date);				

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
						binder.forField((TextField) fieldObj)
		//					.bind(d -> currencyFormatter.encode(CurrencyFormatter.getCents(d.getCol(fieldName))), (d,v)-> d.setColInteger(v,fieldName));
							.bind(d-> d.getColDecimalPoint(fieldNameInUI,nDecimals), (d,v)-> d.setColDecimalPoint(v,fieldName));
					}
					else if (rowCol[3].equals("4")) // is Boolean
					{
						((Checkbox) fieldObj).setReadOnly(isReadOnly);
						if (!visibleByTag)
							((Checkbox) fieldObj).getElement().getStyle().set("visibility","hidden");
						binder.forField((Checkbox) fieldObj).bind(d -> d.getColBoolean(fieldName), (d,v)-> d.setColBoolean(v,fieldName));//DynamicDBean::setCol2Date);				
					}
					else if (idFieldType == 5 ) // is Number
					{
						IntegerField nf = ((IntegerField) fieldObj);
//						nf.setValueChangeMode(ValueChangeMode.EAGER); 
//						nf.setId("tf"+fieldNameInUI);
//						nf.getElement().setAttribute("theme", "small");
						nf.setReadOnly(isReadOnly);
						if (!visibleByTag)
							nf.getElement().getStyle().set("visibility","hidden");
						binder.forField(nf).bind(d-> d.getColInteger(fieldNameInUI), (d,v)-> d.setColInteger(v,fieldNameInUI));
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
						binder.forField(cB).withConverter(
								item-> Optional.ofNullable(item).map(DynamicDBean::getCol0).orElse(null),
								id-> getRowById(id, cB))
						.bind(d-> d.getCol(fieldNameInUI), (d,v)-> d.setCol(v,fieldNameInUI));

//						binder.forField(nf).bind(d-> d.getColInteger(fieldNameInUI), (d,v)-> d.setColInteger(v,fieldNameInUI));
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
			if(id.equals(bean.getCol0()))
				return bean;
		}
		return null;
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
