package coop.intergal.ui.views;

import java.lang.reflect.Field;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Iterator;
import java.util.StringTokenizer;
import java.util.stream.Stream;

import com.fasterxml.jackson.databind.JsonNode;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.HasValue;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.formlayout.FormLayout.FormItem;
import com.vaadin.flow.component.polymertemplate.PolymerTemplate;
import com.vaadin.flow.component.textfield.TextArea;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.BeanValidationBinder;
import com.vaadin.flow.dom.Element;
import com.vaadin.flow.templatemodel.TemplateModel;

import coop.intergal.espresso.presutec.utils.JSonClient;
import coop.intergal.ui.utils.converters.CurrencyFormatter;
import coop.intergal.vaadin.rest.utils.DynamicDBean;
import coop.intergal.vaadin.rest.utils.RestData;

public class GenericDynamicQuery extends PolymerTemplate<TemplateModel> {

	/**
		 * 
		 */
	private static final long serialVersionUID = 1L;
	private ArrayList<String[]> rowsColList;
	protected BeanValidationBinder<DynamicDBean> binder;
	private CurrencyFormatter currencyFormatter = new CurrencyFormatter();
	protected String stringFilter= "";
	protected String keysFromParent = "";
	private String childName = "";
	private String childFkField = "";
	protected String preConfParam;

//	public interface CrudForm<E> {
//		FormButtonsBar getButtons();
//
//		HasText getTitle();
//
//		void setBinder(BeanValidationBinder<E> binder);
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

	public void setBinder(BeanValidationBinder<DynamicDBean> binder) {
		// TODO Auto-generated method stub

	}

	public void setBean(DynamicDBean bean) {
		// TODO Auto-generated method stub

	}

	public String getFieldsDataForFilter(Class<?> class1, Object object, String ResourceName) {
			if (class1.getName().equals("coop.intergal.ui.views.GeneratedQuery"))
					return prepareFilter(class1, object, ResourceName, true );
				else				
					return prepareFilter(class1, object, ResourceName, false);
	}
	private String componeDateFilter(String filter, String field, String value) {
		if (value.indexOf("/") > 0)
			value = value.replaceAll("/", "-");
		if (value.trim().length() == 0)
			return filter;
		if (filter.trim().length() == 0)
			return componeDateFilter(field, value);
		else
			return filter + "%20AND%20" + componeDateFilter(field, value);
	}

	private String componefilter(String filter, String field, String value) {
		if (value.trim().length() == 0)
			return filter;
		if (filter.trim().length() == 0)
			return field + "='" + value + "'";
		else
			return filter + "%20AND%20" + field + "='" + value + "'";
	}

	public static String componeDateFilter(String field, String value) {
		if (value == null)
			return null;
		// posible formats ( DD-MM-AAAA // DD-MM-AAAA hh:mm // DD-MM-AAAA::DD-MM-AAAA //
		// DD-MM-AAAA hh:mm::DD-MM-AAAA hh:mm // >DD-MM-AAAA hh:mm..... )
		// DD-MM-AAAA
		if (value.indexOf("/") > 0)
			value = value.replaceAll("/", "-");
		if (value.startsWith("<") || value.startsWith(">")) {
			String op = "%3E";
			if (value.startsWith("<"))
				op = "%3C";
			String value1 = changeFromAAAAMMDDtoDDMMAAAA(value.substring(1));
			return field + op + "'" + value1.replaceAll(" ", "%20") + "'";
		}
		// DD-MM-AAAA HH:MM
		if (value.length() < 17) {
			String value1 = changeFromAAAAMMDDtoDDMMAAAA(value);
			String value2 = changeFromAAAAMMDDtoDDMMAAAANextDay(value);
			return field + "%3E='" + value1.replaceAll(" ", "%20") + "'%20AND%20" + field + "%3C'"
					+ value2.replaceAll(" ", "%20") + "'";
		} else // a date range DD-MM-AAAA::DD-MM-AAAA or DD-MM-AAAA hh:mm::DD-MM-AAAA hh:mm
		{
			String[] tokens = value.split("::"); // the :: is use to avoid conflict with : of HH:mm
			String date1 = changeFromAAAAMMDDtoDDMMAAAA(tokens[0]);
			String date2 = changeFromAAAAMMDDtoDDMMAAAA(tokens[1]);
			if (date2.indexOf(":") == -1) // is time is not included then 23:59 is add to consider the last date
											// inclusive
				date2 = date2 + " 23:59:59";
			else
				date2 = date2 + ":59"; // the last time is complement with 59 seconds to be inclusive
			return field + "%3E='" + date1.replaceAll(" ", "%20") + "'%20AND%20" + field + "%3C='"
					+ date2.replaceAll(" ", "%20") + "'";

		}
	}

	private static String changeFromAAAAMMDDtoDDMMAAAA(String value) {
		String vDay, vMonth, vYear;
		StringTokenizer tokens = new StringTokenizer(value, "-");
		vDay = tokens.nextToken();
		vMonth = tokens.nextToken();
		vYear = tokens.nextToken();
		if (vYear.length() > 4) // then it comes in the format DD-MM-AAAA HH:MM
		{
			vDay = vDay + vYear.substring(4);
			vYear = vYear.substring(0, 4);

		}
		return vYear + "-" + vMonth + "-" + vDay;
	}

	private static String changeFromAAAAMMDDtoDDMMAAAANextDay(String value) {
		value = changeFromAAAAMMDDtoDDMMAAAA(value);
//		String dt = "2008-01-01";  // Start date
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		int timeToAdd;
		if (value.length() < 11) // only date not time
		{
			timeToAdd = Calendar.DATE;
		} else {
			sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
			timeToAdd = Calendar.MINUTE;
		}

		Calendar c = Calendar.getInstance();
		try {
			c.setTime(sdf.parse(value));
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		c.add(timeToAdd, 1); // number of days to add
		value = sdf.format(c.getTime()); // dt is now the new date
		return value;
//		String vMonth, vYear, vHour, vDay, vMinute;
//		StringTokenizer tokens = new StringTokenizer(value,"-"); 
//		vDay = tokens.nextToken(); 
//		vMonth = tokens.nextToken();
//		vYear = tokens.nextToken(); 		
//		
//		if (vYear.length() > 4) // then it comes in the format  DD-MM-AAAA HH:MM
//		{
//			StringTokenizer tokensDate = new StringTokenizer(vYear,":"); 
//			vHour = tokensDate.nextToken().substring(5);
//			vMinute =tokensDate.nextToken();
//			vDay =  vDay + vHour + vMinute;
//			vYear = vYear.substring(0,4);
//			
//		}
//		return vYear+"-"+vMonth+"-"+vDay; 
	}

	private Integer getCents(String col) {
		String cents = col;
		if (col.indexOf(".") > -1)
			cents = col.substring(0, col.indexOf(".")) + col.substring(col.indexOf(".") + 1); // take off "."
		return new Integer(cents);
	}
	// ******** QUERY that Combines data from different resources (tables) ****
	// compose the filter combining ROW fields and parents and grand parent fields
	// points to consider ->
	// the name of the resources must be FK-tableName for parents
	// the id fields should be names idTable
	// in childs must be the FK fieslds and a param in row event call - >
	// row.p_a_r_filterPK="idPkeyParent"; <- in the child (if are different)

	protected String prepareFilter(Class<?> class1, Object object, String ResourceName, boolean isGeneratedForm) { // compose the filter
																							// combining ROW fields and
																							// parents and grand parent
																							// fields
		Iterator<String[]> itRowsColList = RestData
				//.getRowsColList(rowsColList, ResourceName, preConfParam).iterator();
				.getRowsQueryFieldList(rowsColList, ResourceName, preConfParam, true).iterator();

		
		FormLayout form = null ;
		if (isGeneratedForm)
		{
			Field fieldForm;
			try {
				fieldForm = ((class1)).getDeclaredField("form");
				fieldForm.setAccessible(true);
				Object fieldObj = fieldForm.get(object);
				form = ((FormLayout) fieldObj);
			} catch (NoSuchFieldException | SecurityException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IllegalArgumentException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	
		}	
		
		String filter = "";
		while (itRowsColList.hasNext()) {
			String[] rowCol = itRowsColList.next();
			String fieldName = rowCol[2];
			if (!fieldName.isEmpty())
				try {//		System.out.println("PedidoProveedorForm.bindFields() fieldName ...."  + fieldName);
					String id = rowCol[2];
					if (!fieldName.equals("null")) {
						Field field = null ;
						Object fieldObj =null;
						if (isGeneratedForm == false)
							{
							field = ((class1)).getDeclaredField(fieldName);// .get(instancia);
							field.setAccessible(true);
							fieldObj = field.get(object);
							}
						if (rowCol[4].length() > 0) // it means a parent field , the field "PathToParentField" comes in 4 position  if have FK the field the path is fill it 
							{
							String value = getValueFromField(form, id, fieldObj, isGeneratedForm);
							value=addAutoComodin(value);
							setFKIdsForFilter(rowCol[4], value);
							}	

						else if (rowCol[3].isEmpty() || rowCol[3].equals("0")) { // text field 
								String value = getValueFromField(form, id, fieldObj, isGeneratedForm);								
								if (!value.isEmpty()) {
									value=addAutoComodin(value);
									System.out.println("GenericDynamicForm.getFieldsData() fieldName " + rowCol[0]
										+ " valor :" + value + "");
								// filter=componefilter(filter, rowCol[0], ((TextField) fieldObj).getValue());
									if (filter.length() > 1)
										filter = filter + "%20AND%20" + rowCol[0]
												+ determineOperator(value);
									else
										filter = rowCol[0] + determineOperator(value);
								}
						} 
						else if (rowCol[3].equals("5")) { // number field 
							String value = getValueFromField(form, id, fieldObj, isGeneratedForm);								
							if (!value.isEmpty()) {
						//		value=addAutoComodin(value);
								System.out.println("GenericDynamicForm.getFieldsData() fieldName " + rowCol[0]
									+ " valor :" + value + "");
							// filter=componefilter(filter, rowCol[0], ((TextField) fieldObj).getValue());
								if (filter.length() > 1)
									filter = filter + "%20AND%20" + rowCol[0]
											+ componeNumberFilte(value);// determineOperator(value);
								else
									filter = rowCol[0] + componeNumberFilte(value);// determineOperator(value);
							}
					}  

						else if (rowCol[3].equals("1")) // is Date
							{
							String value = getValueFromField(form, id, fieldObj, isGeneratedForm);
							if (!value.isEmpty()) {
								System.out.println("GenericDynamicForm.getFieldsData() fieldName " + rowCol[0]
										+ " valor :" + value);
//				filter=componeDateFilter(filter, rowCol[0], ((TextField) fieldObj).getValue());
								if (filter.length() > 1)
									filter = filter + "%20AND%20("
											+ componeDateFilter(rowCol[0], value) + ")";
								else
									filter = componeDateFilter(rowCol[0], value);
							}

						} else if (rowCol[3].equals("2")) // is TextArea
							
						{
							System.out.println("GenericDynamicForm.getFieldsData() fieldName " + fieldName + " valor :"
									+ ((TextArea) fieldObj).getValue());
						} else if (rowCol[3].equals("3")) // is currency
						{
							System.out.println("GenericDynamicForm.getFieldsData() fieldName " + fieldName + " valor :"
									+ ((TextArea) fieldObj).getValue());
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
//}	

//}
//return filter;

		}
		String parentKeys = getParentKeys();
		if (filter.length() > 1 && parentKeys.length() > 1) // if have filters from the Row it self adds the parent
															// generated filters
			filter = filter + "%20AND%20" + parentKeys;
		else if (parentKeys.length() > 1)
			filter = parentKeys;
		System.err.println("filter------" + filter);
		return filter;
	}

	private String componeNumberFilte(String value) {
		if (value.indexOf(":")> 0)  // is a range
			return componeNumberRange(value);
		else
			return determineOperator(value);
	}

	private String getValueFromField(FormLayout form, String id, Object fieldObj, boolean isGeneratedForm) {
		String value = "";
		if (isGeneratedForm)
		{
			id = "tf"+id;
			value = (String) getValueFromField(form, id );
		}
		else
			value = ((TextField) fieldObj).getValue() + "";
		return value;
	}

//	protected String prepareFilterWithGeneratedForm(Class<?> class1, Object object, String ResourceName) { // compose the
//																										// filter
//		// combining ROW fields and
//		// parents and grand parent
//		// fields
//		Iterator<String[]> itRowsColList = RestData//.getRowsColList(rowsColList, ResourceName, preConfParam).iterator();
//				.getRowsQueryFieldList(rowsColList, ResourceName, preConfParam, true).iterator();
//
//		String filter = "";
//		Field fieldForm;
//		try {
//			fieldForm = ((class1)).getDeclaredField("form");
//
//		fieldForm.setAccessible(true);
//		Object fieldObj = fieldForm.get(object);
//		FormLayout form = ((FormLayout) fieldObj);
//		Stream<Component> childs = form.getChildren();
//		Iterator<Component> itChilds = childs.iterator();
//		String id = "tfcol2";
//		while (itChilds.hasNext()) {
//			Component rowCol = itChilds.next();
////			rowCol.
//			Element el = rowCol.getElement();
//			System.out.println("GenericDynamicQuery.prepareFilterWithGeneratedForm()---"+ el.getText() + rowCol.getId());
//			Object value = getValueFromField(form, id );
//
//			//			String fieldName = rowCol[2];
////			if (!fieldName.isEmpty())
////				try {//System.out.println("PedidoProveedorForm.bindFields() fieldName ...."  + fieldName);
////					if (!fieldName.equals("null")) {
////						Field field = null;
////							field = getFieldFromForm(class1, object, fieldName);
////						if (rowCol[4].length() > 0) // it means a parent field , the field "PathToParentField" comes in
////													// 4 position if have FK the field the path is fill it
////						{
////							String value = ((TextField) fieldObj).getValue() + "";
////							value = addAutoComodin(value);
////							setFKIdsForFilter(rowCol[4], value);
////						}
////
////						else if (rowCol[3].isEmpty()) {
////							String value = ((TextField) fieldObj).getValue() + "";
////							if (!value.isEmpty()) {
////								value = addAutoComodin(value);
////								System.out.println("GenericDynamicForm.getFieldsData() fieldName " + rowCol[0]
////										+ " valor :" + value + "");// filter=componefilter(filter, rowCol[0], ((TextField) fieldObj).getValue());
////								if (filter.length() > 1)
////									filter = filter + "%20AND%20" + rowCol[0] + determineOperator(value);
////								else
////									filter = rowCol[0] + determineOperator(value);
////							}
////						} else if (rowCol[3].equals("1")) // is Date
////						{
////							String value = ((TextField) fieldObj).getValue() + "";
////							if (!value.isEmpty()) {
////								System.out.println("GenericDynamicForm.getFieldsData() fieldName " + rowCol[0]
////										+ " valor :" + value);//filter=componeDateFilter(filter, rowCol[0], ((TextField) fieldObj).getValue());
////								if (filter.length() > 1)
////									filter = filter + "%20AND%20(" + componeDateFilter(rowCol[0], value) + ")";
////								else
////									filter = componeDateFilter(rowCol[0], value);
////							}
////
////						} else if (rowCol[3].equals("2")) // is TextArea
////
////						{
////							System.out.println("GenericDynamicForm.getFieldsData() fieldName " + fieldName + " valor :"
////									+ ((TextArea) fieldObj).getValue());
////						} else if (rowCol[3].equals("3")) // is currency
////						{
////							System.out.println("GenericDynamicForm.getFieldsData() fieldName " + fieldName + " valor :"
////									+ ((TextArea) fieldObj).getValue());
////						}
////					}
////
////				} catch (NoSuchFieldException | SecurityException e) {
////					System.err.println("Field not defined in Form... " + e.toString());
////
////				} catch (IllegalArgumentException e) {// TODO Auto-generated catch block
////					e.printStackTrace();
////				} catch (IllegalAccessException e) {// TODO Auto-generated catch block
////					e.printStackTrace();
////
////				} catch (java.lang.ClassCastException e) {
////					System.err.println("Field type incorret in Form with FiledTemplate... " + e.toString());
////				}
//
//		}
////		String parentKeys = getParentKeys();
////		if (filter.length() > 1 && parentKeys.length() > 1) // if have filters from the Row it self adds the parent// generated filters
////			filter = filter + "%20AND%20" + parentKeys;
////		else if (parentKeys.length() > 1)
////			filter = parentKeys;
////		System.err.println("filter------" + filter);
//		} catch (NoSuchFieldException | SecurityException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		} catch (IllegalArgumentException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		} catch (IllegalAccessException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//		return filter;
//	}	

	private Object getValueFromField(FormLayout form, String id) {
		Object value = form.getChildren()
			    .flatMap(c->c instanceof FormItem?((FormItem)c).getChildren():Stream.of(c))
			    .filter(c->id.equals(c.getId().orElse(null))).findFirst()
			    .filter(HasValue.class::isInstance)
			.map(HasValue.class::cast)
			    .map(HasValue::getValue)
			    .orElse(null);
		if (value == null)
			value = "";
		System.out.println("VALUE........"+ value);
		return value;
	}

//	private Object clearValueFromField(FormLayout form, String id) {
//		Object value = form.getChildren()
//			    .flatMap(c->c instanceof FormItem?((FormItem)c).getChildren():Stream.of(c))
//			    .filter(c->id.equals(c.getId().orElse(null))).findFirst()
//			    .filter(HasValue.class::isInstance)
//			.map(HasValue.class::cast)
//			    .map(HasValue::clear)
//			    .orElse(null);
//		if (value == null)
//			value = "";
//		System.out.println("VALUE........"+ value);
//		return value;
//	}

	private String addAutoComodin(String value) {
	    if (value.length()>0) {
		      if (value.indexOf("%")== -1) {
		            value ="%"+value+"%";
		      }
		    }
		return value;
	}

	protected void cleanForm(Class<?> class1, Object object, String ResourceName, boolean isQryFormGenrated) { // compose the filter
		// combining ROW fields and
		// parents and grand parent
		// fields
		Iterator<String[]> itRowsColList = RestData.getRowsColList(rowsColList, ResourceName, preConfParam).iterator();
		String filter = "";
		while (itRowsColList.hasNext()) {
			String[] rowCol = itRowsColList.next();
			String fieldName = rowCol[2];
			if (!fieldName.isEmpty())
				
					//System.out.println("PedidoProveedorForm.bindFields() fieldName ...."  + fieldName);
					if (!fieldName.equals("null")) {
						Field field;
						if (isQryFormGenrated)
						{
							FormLayout form = null;
							try {
								Field fieldForm = ((class1)).getDeclaredField("form");
								fieldForm.setAccessible(true);
								Object fieldObj = fieldForm.get(object);
								form = ((FormLayout) fieldObj);
							} catch (NoSuchFieldException | SecurityException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							} catch (IllegalArgumentException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							} catch (IllegalAccessException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
							fieldName = "tf"+fieldName;
							clearField(form, fieldName);
						}
						else
						{	
						
						try {
							field = ((class1)).getDeclaredField(fieldName);
							// .get(instancia);
							field.setAccessible(true);
							Object fieldObj = field.get(object);
							((TextField) fieldObj).clear();
						
						} catch (NoSuchFieldException | SecurityException e) {
							System.err.println("Field not defined in Form... " + e.toString());
						} catch (IllegalArgumentException e) {
						// TODO Auto-generated catch block
							e.printStackTrace();
						} catch (IllegalAccessException e) {
							System.err.println("Field type incorret in Form with FiledTemplate... " + e.toString());
					}
					}	
					}
		}
	}
				
private void clearField(FormLayout form, String id) {
	
	form.getChildren()
    .flatMap(c->c instanceof FormItem?((FormItem)c).getChildren():Stream.of(c))
    .filter(c->id.equals(c.getId().orElse(null))).findFirst()
    .filter(HasValue.class::isInstance)
    .map(HasValue.class::cast)
    .ifPresent(HasValue::clear);
		
	}

//}	

//}
//return filter;
	private void setFKIdsForFilter(String id, String value) { // keeps the filters in each parent id comes with the format list- or CR-childTable#childField.FK-paremtTable-fieldnmame , childField is opctional when there is more than one parent (same table) of the same child
		int idxTable = 0;
		int idxChildField = 0; 
		if (id.startsWith("CR-") ||isAList(id)|| id.indexOf(".") >-1 )
			{
			idxTable = id.indexOf(".");
			idxChildField = id.indexOf("#");
			if (idxChildField == -1)
				idxChildField = idxTable;
			else
				childFkField = id.substring(idxChildField+1,idxTable);	// @@ TODO CQR be careful pending of adapt to multiKey
			childName = id.substring(0,idxChildField);	
			
			idxTable++; // to jump (,)
			}
		String field = id.substring(id.lastIndexOf("-") + 1);
		String table = id.substring(idxTable, id.lastIndexOf("-"));
		System.err.println("childName "+ childName +" Field " + field + " table " + table);
		searchAndPaste(table, field, value);
	}

	private boolean isAList(String id) {// could be xxList where xx is the order position for children tabs
		if (id.indexOf("List-") > -1 && id.indexOf("List-") < 3) 
			return true;
		else 
			return false;
	}

	private String getParentKeys() { /// returns the parent KEYS generate after searching in parents
		stringFilter = stringFilter.replaceAll("!FK-", "@FK-"); // the ! was use to know which tables has not fields
																// with filter
		String[] fks = stringFilter.split("@FK-");
		int i = fks.length;
		int step = 0;
		while (i > 0) {
			i--;
			System.out.println(" fks " + fks[i]);
			if (fks[i].length() > 1)
				getParentKeysOfFK(fks[i]);

		}

		return getAllParentKeys();
	}

	private String getAllParentKeys() { // concat the ids of diferent parents
		String[] fks = keysFromParent.split("@##");
		String allParentKeys = "";
		int i = fks.length;
		while (i > 0) {
			i--;
			System.out.println(" fks " + fks[i]);
			if (fks[i].length() > 1 && fks[i].startsWith("@") == false) // to avoid other work filters
			{
				if (allParentKeys.length() > 1) {
					allParentKeys = allParentKeys + "%20AND%20" + fks[i];
				} else {
					allParentKeys = fks[i];
				}
			}

		}

		return allParentKeys;
	}

	private void getParentKeysOfFK(String fks) { // search the parent rows using the input filter and generate the list
													// of parent keys
		String tableFk = fks.substring(0, fks.indexOf("##"));
		String table = "";
		String tableFkChild = "";
		if (tableFk.lastIndexOf("-") == -1)
			table = tableFk;
		else
			table = tableFk.substring(tableFk.lastIndexOf("-") + 1);
		if (fks.indexOf(table) > 4)
			tableFkChild = fks.substring(0, fks.indexOf(table) - 4);
		String filter = fks.substring(fks.indexOf("##") + 2);
		System.out.println("///tableFk " + tableFk + " ///table " + table + " ///filter " + filter + " ///tableFkChild "
				+ tableFkChild);
		try {
			if (keysFromParent.indexOf("@" + table) != -1) // this table has Parent Ids to add to filter
			{
				int startFilter = keysFromParent.indexOf("@" + table) + table.length() + 3; // adds ## separator
				int endFilter = keysFromParent.length(); // by default there is not more tables
				if (keysFromParent.substring(keysFromParent.indexOf("@" + table) + 1).indexOf("@") != -1) // checks If
																											// there is
																											// another
																											// table
					endFilter = keysFromParent.substring(keysFromParent.indexOf("@" + table) + 1).indexOf("@")
							+ startFilter;
				if (filter.length() > 1)
					filter = filter + "%20AND%20" + keysFromParent.substring(startFilter, endFilter);
				else
					filter = keysFromParent.substring(startFilter, endFilter);
			}
			if (filter.length() > 0) {
				filter = filter.replaceAll(" ", "%20");
				// filter = filter.replaceAll("'", "%5C'"); // to void problems with "'" that
				// conflicts with text delimitation BY example : 'Bob's' will be 'Bob\'s'
				// listIds = listIds + "'" +filterValue + "',";
				JsonNode rowsList = JSonClient.get(table, filter, false, preConfParam, "2000"); // @@ TODO a general param ? for paremts that fits
				String idField = JSonClient.getPKTable(table, preConfParam); // @@ TODO CQR be careful pending of adapt to multiKey
				String childTableToGetFK = tableFkChild;

				if (tableFkChild.length() == 0)
					if (childName.length() > 1)
						if (isAList(childName))
							childTableToGetFK = childName.substring(5);
						else if (childName.startsWith("CR-"))
							childTableToGetFK = childName.substring(3);
						else
							childTableToGetFK = childName;
					else
						childTableToGetFK = JSonClient.getTableNameRoot();
				String fkidField = childFkField; // when a child field could be several because more of one parent of the table, the used FK is send by param in the FieldTemplate
				if (fkidField.length() == 0) 
					fkidField = JSonClient.getFKFromTable(table, childTableToGetFK, preConfParam); // antes
																													// chldname.substring(5)
				String listIds = fkidField + "%20IN%20(";
				for (JsonNode eachRow : rowsList) {
					String filterValue = eachRow.get(idField).asText().replaceAll(" ", "%20");
					filterValue = filterValue.replaceAll("'", "%5C'"); // to void problems with "'" that conflicts with
																		// text delimitation BY example : 'Bob's' will
																		// be 'Bob\'s'
					listIds = listIds + "'" + filterValue + "',";
				}
				listIds = listIds.substring(0, listIds.length() - 1) + ")"; // depreciate last ","
				searchAndPasteKFP(tableFkChild, listIds);
				System.out.println("listIds " + listIds);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void searchAndPaste(String table, String field, String value) { // prepares the search string to search parent rows 
		if ((stringFilter.indexOf(table) != -1) )
		{
			if ((value.length() > 0))  
			{
				int startFK = stringFilter.indexOf(table); 
				int endFK = + startFK + table.length()+2; 	// add ## + table length	
				if (stringFilter.substring(stringFilter.indexOf(table) -1 , stringFilter.indexOf(table) ).equals("@")) // is a field over fk that already have filter 
					stringFilter = stringFilter.substring(0, endFK) + field + determineOperator(value) + "%20AND%20" + stringFilter.substring(endFK) ;
				else
				{
					stringFilter = stringFilter.substring(0, endFK) + field + determineOperator(value) + stringFilter.substring(endFK) ;
					stringFilter = stringFilter.substring(0, stringFilter.indexOf(table) -1) + "@" + stringFilter.substring(stringFilter.indexOf(table));

				}		
			}
		}
		else
			if (value.length() < 1)
			{
				stringFilter = stringFilter +"!"+ table + "##"; // marks table with not fields 
			}
			else
			{
				stringFilter = stringFilter +"@"+ table + "##" + field + determineOperator(value) ;
			}
		System.err.println("stringFilter <<<< " +stringFilter);
	}

	private void searchAndPasteKFP(String table, String filtro) { // prepares the search string adding the FK Keys with
																	// his table

		keysFromParent = keysFromParent + "@" + table + "##" + filtro;
		System.err.println("keysFromParent <<<< " + keysFromParent);
	}

	private String determineOperator(String value) { // determines to use = like or > <
		value = value.trim(); // deletes extra blanks pre and post
		if (value.indexOf("%") >= 0) {
			value = value.replaceAll("%", "%25");
			if (value.indexOf(" ") >= 0)
				value = value.replaceAll(" ", "%20");
			return "%20like('" + value + "')";
		}
		if (value.indexOf("<") == 0) {
			return value.replaceAll("<", "%3C");
		}
		if (value.indexOf(">") == 0) {
			return value.replaceAll(">", "%3E");
		}

		return "='" + value + "'";
	}
	private String componeNumberRange(String value) { // determines to use = like or > <
		value = value.trim(); // deletes extra blanks pre and post
		int posColon = value.indexOf(":");
		String fromNumber = value.substring(0,posColon );
		String toNumber = value.substring(posColon+1 );
		
		return "%20BETWEEN%20" + fromNumber + "%20AND%20" + toNumber ;
	}

}
