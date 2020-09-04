package coop.intergal.ui.views;
import static coop.intergal.AppConst.PACKAGE_VIEWS;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Set;
import java.util.StringTokenizer;

import com.fasterxml.jackson.databind.JsonNode;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.checkbox.Checkbox;
import com.vaadin.flow.component.datepicker.DatePicker;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.polymertemplate.PolymerTemplate;
import com.vaadin.flow.component.textfield.IntegerField;
import com.vaadin.flow.component.textfield.TextArea;
import com.vaadin.flow.component.textfield.TextField;

import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.data.converter.LocalDateToDateConverter;
import com.vaadin.flow.dom.DomEvent;
import com.vaadin.flow.templatemodel.TemplateModel;

import coop.intergal.espresso.presutec.utils.JSonClient;
import coop.intergal.ui.utils.converters.CurrencyFormatter;
import coop.intergal.vaadin.rest.utils.DynamicDBean;

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

	public void setBean(DynamicDBean bean) {
		// TODO Auto-generated method stub
		
	}
	

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
			int idFieldType = 0;
			if ( idFieldTypeStr.isEmpty() == false)
				idFieldType = new Integer (idFieldTypeStr);
			boolean isReadOnly = isReadOnly( rowCol [1]);
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
						if (isReadOnly)
						{
							tf.getElement().addEventListener("click", ev->showDialogForPick(ev, fieldName, tf));
						}
						binder.bind(tf, fieldName);
					}
					else if (rowCol[3].equals("1")) // is Date
						{
						binder.forField((DatePicker) fieldObj)
						.withConverter(new LocalDateToDateConverter( ZoneId.systemDefault()))
						.bind(d-> d.getColDate(fieldName), (d,v)-> d.setColDate(v,fieldName));//DynamicDBean::setCol2Date);				
					}
					else if (rowCol[3].equals("2")) // is TextArea
					{
						binder.bind((TextArea) fieldObj, fieldName);
					}
					else if (rowCol[3].equals("3")) // is currency
					{
			//			binder.bind((AmountField) fieldObj, fieldName);
						binder.forField((TextField) fieldObj).bind(d -> currencyFormatter.encode(currencyFormatter.getCents(d.getCol(fieldName))), (d,v)-> d.setColInteger(v,fieldName));
			//			binder.forField((AmountField) fieldObj).bind(d-> d.getColInteger(fieldName), (d,v)-> d.setColInteger(v,fieldName));//DynamicDBean::setCol2Date);				

					}
					else if (rowCol[3].equals("4")) // is Boolean
					{
						binder.forField((Checkbox) fieldObj).bind(d -> d.getColBoolean(fieldName), (d,v)-> d.setColBoolean(v,fieldName));//DynamicDBean::setCol2Date);				
					}
					else if (idFieldType == 5 ) // is Number
					{
						IntegerField nf = ((IntegerField) fieldObj);
//						nf.setValueChangeMode(ValueChangeMode.EAGER); 
//						nf.setId("tf"+fieldNameInUI);
//						nf.getElement().setAttribute("theme", "small");
						nf.setReadOnly(isReadOnly);
						binder.forField(nf).bind(d-> d.getColInteger(fieldNameInUI), (d,v)-> d.setColInteger(v,fieldNameInUI));
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

	private Object showDialogForPick(DomEvent ev, String fieldName, TextField tf) {
		
		try {
		DynamicGridForPick dynamicGridForPick = new DynamicGridForPick(); 
		String queryFormForPickClassName = null;
//		Object queryFormClassName = PACKAGE_VIEWS+queryParameters.getParameters().get("queryFormClassName").get(0);
		DynamicDBean currentRow = binder.getBean();
		String filter="tableName='"+currentRow.getResourceName()+"'%20AND%20FieldNameInUI='"+fieldName+"'";
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
//		Method setGrid = dynamicQuery.getMethod("setGrid", new Class[] {coop.intergal.tys.ui.views.DynamicViewGrid.class} );
		Method setGrid = dynamicQuery.getMethod("setGrid", new Class[] {coop.intergal.ui.views.DynamicViewGrid.class} );

		setGrid.invoke(queryForm,grid);
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

	private boolean isReadOnly(String params) {
		
		if (params.indexOf("#CNoEDT#")>-1)
			return true;
		else 
			return false;
			
	}


}
