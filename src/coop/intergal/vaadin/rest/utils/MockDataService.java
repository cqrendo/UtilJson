package coop.intergal.vaadin.rest.utils;

import java.io.IOException;
import java.lang.reflect.Field;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.io.IOUtils;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.JsonNodeFactory;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.html.NativeButton;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.notification.Notification.Position;
import com.vaadin.flow.data.provider.QuerySortOrder;

import coop.intergal.espresso.presutec.utils.JSonClient;



/**
 * Mock data model. This implementation has very simplistic locking and does not
 * notify users of modifications.
 */
public class MockDataService extends DataService {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private static MockDataService INSTANCE;

    private List<DynamicDBean> rows;
    private int nextProductId = 0;

	private JsonNode lTxsumary;

	private JsonNode allSaveSata;

	private boolean errorSaving;

    private MockDataService() {
//        categories = MockDataGenerator.createCategories();
//        products = MockDataGenerator.createProducts(categories);
//        productSum = MockDataGenerator.createProductsSum();
//        nextProductId = products.size() + 1;
    }

    public synchronized static DataService getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new MockDataService();
        }
        return INSTANCE;
    }

	@Override
//	public Collection<DynamicDBean> getAllDynamicDBean(int offset, int limit, boolean b, String s, String s0,
		public Collection<DynamicDBean> getAllDynamicDBean(int offset, int limit, boolean refreshFromServer, String resourceName, String preConfParam,
				ArrayList<String[]> rowsColList, String filtro, List<QuerySortOrder> sortOrdersFields, Boolean hasNewRow) {
		//	if (refreshFromServer)
		if (sortOrdersFields.isEmpty() == false)
		{
			Iterator<QuerySortOrder> itSortOrdersFields = sortOrdersFields.iterator();
			if (filtro !=null)
				filtro=filtro+"&order=";
			else
				filtro="order=";
			int nFields = 0;
			while (itSortOrdersFields.hasNext())
			{
				QuerySortOrder sortOrdersField = itSortOrdersFields.next();
				String QuerySortOrderDir = "DESC";
				if (sortOrdersField.getDirection().toString().startsWith("ASC"))
						QuerySortOrderDir = "ASC";
				if (nFields > 0)
					filtro=filtro+",%20"+sortOrdersField.getSorted()+"%20"+QuerySortOrderDir;
				else
					filtro=filtro+sortOrdersField.getSorted()+"%20"+QuerySortOrderDir;
				nFields++;
			}
		}
				rows = RestData.getResourceData(offset,limit, resourceName, preConfParam, rowsColList, filtro, refreshFromServer, hasNewRow);// refresh data from server each interaction with grid
	        return rows;
    }

	@Override
	public DynamicDBean getDynamicDBeanById(int productId) {
	       for (int i = 0; i < rows.size(); i++) {
	            if (new Integer (rows.get(i).getCol0()) == productId) {
	                return rows.get(i);
	            }
	        }
	        return null;
	}

	@Override
	public void updateDynamicDBean(DynamicDBean dB) {
		try
		{
			//	Item fieldBeforeCommit = fieldGroup.getItemDataSource();
			//	fieldGroup.commit();
			//				insertRow();
			//				fireEvent(new EditorSavedEvent(this, comentarioItem)); 
			final JsonNodeFactory nodeFactory = JsonNodeFactory.instance;
			JsonNode rowJson = dB.getRowJSon();
			String resourceName = dB.getResourceName();
			String preConfParam = dB.getPreConfParam();
			//** Creo la Entidad
			
			if (rowJson == null) // is INSERT
			{
				ObjectNode newEntityinfo = putValuesOnObject(true, nodeFactory, dB, resourceName);// new ObjectNode(nodeFactory);
				//					Iterator<Component> fieldList = editorForm.iterator(); 
				//@@CQR gestionar PK auto numbers				newEntityinfo.put("idEntity", 0);
				newEntityinfo = addImagenIfExist(dB, newEntityinfo);
				JsonNode postResult;
				try {
					
					postResult = JSonClient.post(resourceName, newEntityinfo, preConfParam);

					if (postResult.get("statusCode").intValue() != 201)
					{
		//				throw new IllegalArgumentException("Unable to insert: " + postResult);
						errorSaving = true;
						showError(postResult.get("errorMessage").asText());
						
//						fieldGroup.discard();
//						showError(postResult.get("errorMessage").asText().substring(22));
//
//						throw new RuntimeException("Unable to insert: " + postResult);
					}
					else
					{
						
						errorSaving = false;
						lTxsumary = postResult.get("txsummary");
						allSaveSata = lTxsumary;
						lTxsumary = getResourceFromResult(lTxsumary, preConfParam, resourceName);
						dB.setRowJSon(lTxsumary);
						JsonNode eachRow =  lTxsumary.get(0);
						putJSonData(eachRow, dB,false);
						if (rows != null) // when the insert doesn't comes from a list rows is null
							rows.add(dB);
						showConfirmationSave("Registro salvado con éxito!");
				//		tableEL.getTable().select(itemId);
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			else // is UPDATE row *********
			{
				JsonNode postResult;
				try {
					ObjectNode rowJsonChanged = putValuesOnObject(false, nodeFactory,dB, resourceName);
					postResult = JSonClient.put(resourceName, rowJsonChanged,  preConfParam); 

					if (postResult.get("statusCode").intValue() != 200)
					{
						//		fieldGroup.discard();
						//		fieldGroup.setItemDataSource(fieldBeforeCommit) ;
						//		tableEL.putJSonData(fieldBeforeCommit);
						//		fieldGroup.commit();
				//		showError(postResult.get("errorMessage").asText().substring(22));

			//			throw new RuntimeException("Unable to update: " + postResult);
						errorSaving = true;
						showError(postResult.get("errorMessage").asText());
						
					}
					else
					{
						errorSaving = false;
						lTxsumary = postResult.get("txsummary");
						allSaveSata = lTxsumary;
						if (lTxsumary.size() > 0 ) //&& tableEL != null)
						{
							lTxsumary = getResourceFromResult(lTxsumary, preConfParam, resourceName);
							JsonNode eachRow =  lTxsumary.get(0);
							putJSonData(eachRow, dB, true);
						}
						//		idEntity = lTxsumary.get(0).get("idEntity").intValue();
						System.err.println(" result  "+ lTxsumary); 
						showConfirmationSave("Registro salvado con éxito!");
//						if (customerPickComponents != null)
//						{
//							Enumeration<Component> listCustomerPickComponents = customerPickComponents.elements();
//							while (listCustomerPickComponents.hasMoreElements())
//								{
//								Component pickComp =listCustomerPickComponents.nextElement();	
//								if (pickComp instanceof Label) 
//									((Label)pickComp).setValue(lTxsumary.get(0).get(pickComp.getId()).asText()); 
//								}
//							}
						}
					
				} catch (Exception e) {
					e.printStackTrace();
				}

			}

		}
		catch (java.lang.NullPointerException nullE)
		{   
			System.err.println(" Paso por el Segundo Catch :");
			nullE.printStackTrace();
		}

		catch (Exception e ){//CommitException e) {
			// TODO @@revisar
			e.printStackTrace();
		}




	} 
		 
private ObjectNode addImagenIfExist(DynamicDBean dB, ObjectNode newEntityinfo) {  // it must exists a field call "Imagen" of the the type BLOD use to keep a imagen
		if (dB.getBytes() != null && dB.getBytes().toString().length() > 0)
		{
//				bytes = IOUtils.toByteArray(dB.getInputStream());			
				String encoded =   "0x"+bytesToHex(dB.getBytes());
				newEntityinfo.put("Imagen", encoded);
		}
			
		return newEntityinfo;
	}

public static String bytesToHex(byte[] in) {
    final StringBuilder builder = new StringBuilder();
    for(byte b : in) {
        builder.append(String.format("%02x", b));
    }
    return builder.toString();
}

public void showError(String error) {
	Label content = new Label(error);
	NativeButton buttonInside = new NativeButton("Cerrar");
	Notification notification = new Notification(content, buttonInside);
//	notification.setDuration(3000);
	buttonInside.addClickListener(event -> notification.close());
	notification.setPosition(Position.MIDDLE);
	notification.open();
		
	}
private void showConfirmationSave(String error) {
	Label content = new Label(error);
//	NativeButton buttonInside = new NativeButton("Cerrar");
	Notification notification = new Notification(content);
	notification.setDuration(3000);
//	buttonInside.addClickListener(event -> notification.close());
	notification.setPosition(Position.TOP_STRETCH);
	notification.open();
		
	}

private String getTableName(JsonNode rowJson) {    // TODO @CQR make an alterntive way, not sequential taking in consideration tal colx coiuld be not secuentail
		String href = rowJson.get("@metadata").get("href").asText();
		return null;
	}

	//	@SuppressWarnings("unchecked")
	private ObjectNode putValuesOnObject(boolean isInsert, JsonNodeFactory nodeFactory, DynamicDBean dB, String resourceName) {
		ObjectNode newEntityinfo = new ObjectNode(nodeFactory);
		JsonNode rowJSon = dB.getRowJSon();
		String tableName = resourceName;
		if (resourceName.startsWith("CR-")) // TODO put in the "convention names" all the resources that starts with CR- are custom resources and the tablename is after CR- and before....
			tableName = resourceName.substring(3); // TODO calculate after table name.....
		if (!isInsert)
		{
			//	newEntityinfo = (ObjectNode) rowJSon;// new ObjectNode(nodeFactory);
			ObjectNode metadata = new ObjectNode(nodeFactory);
			metadata.put("checksum",rowJSon.get("@metadata").get("checksum").asText());
			metadata.put("href",rowJSon.get("@metadata").get("href").asText());
			newEntityinfo.put("@metadata",metadata);
			int i= 0;
			if (JSonClient.getResourceHtPK().get(tableName) != null) // when doesn't exist in field template has PK  data in ResourceHtPK
			{
				while (true)
				{
					String pkfield = JSonClient.getResourceHtPK().get(tableName).get("pkField"+i);
					if (pkfield == null)
						break;
					newEntityinfo.put(pkfield,rowJSon.get(pkfield));
					i++;
				}
			}
		}
		else // isInsert Put Data from filter
		{
			String fKfilter = dB.getFilter();
			if (fKfilter != null)
			{
	//			int fKfilterLength = fKfilter.length();
				//FASE_CABEZERA='3'%20and%20CLAVE_ALMACEN='32'%20and%20N_PEDIDO='1'
				int beginIndex = 0;
				int endIndex = fKfilter.indexOf("=");
				while (true)
				{
					String fieldName = fKfilter.substring(beginIndex, endIndex);
					beginIndex = fKfilter.indexOf("'")+1;
					fKfilter = fKfilter.substring(beginIndex);
					endIndex = fKfilter.indexOf("'");
					String fieldValue = fKfilter.substring(0, endIndex);
					newEntityinfo.put(fieldName, fieldValue);
					beginIndex = fKfilter.indexOf("%20and%20")+ 9;
					if (beginIndex < 9)
						break;
					fKfilter = fKfilter.substring(beginIndex);
					endIndex = fKfilter.indexOf("=");
					beginIndex = 0;
				}	
			}
			
		}
			
//		Iterator<Component> fieldList = editorForm.iterator(); 
		Field[] fields = dB.getClass().getDeclaredFields();
		ArrayList<String[]> rowsColList = dB.getRowsColList();
//		Iterator<String[]> itRowsColList = rowsColList.iterator();
		
		int i=0; 
		for(Field field : fields )  
		{
			if (i >= rowsColList.size()) // the max attributes are put are number of columns in resource. 
				break;
//		while (fieldList.hasNext())
//		{
			Object o = field;//.next();
			try {
				field.setAccessible(true);
				Object value = field.get(dB);
			if (o instanceof Field)
			{
//				Field<?> field = (Field<?>) o ; // TODO porque FK- tiene id Nulo?
				if (field.getName() != null && value != null && value.equals("null") == false && value.toString().equals("") == false 
						&& value.toString().length() > 0 ) //&& field.getCaption().startsWith("HIDE @ FIELD") == false) // the Hide fields are not send in the data to PUT 
				{
					//					System.err.println("FIELD......."+field.getId() + " VALUE.... "+ (String) field.getValue());
					if (field.getName().startsWith("col") == false) // all the "normal" fields starts with col (col0, col1.....) 
					{
						// do Nothing the rowJSon is not a field
					}
					else
						
						if (getColName(rowsColList,i).startsWith("FK-")) // Parent and Grand parent fields --> even doesn't have value must be fill to get filters from possible grant parents
						{
							//				setFKIdsForFilter(field.getId(), (String) field.getValue()); 
						}
						else // are "normal fields"
						{
							if(isDate(o))	
							{
								DateFormat fechaIni = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
								if (value != null)
									newEntityinfo.put(getColName(rowsColList,i),fechaIni.format(value) );
							}
							else
								if (isCheckBox(o) || value.equals("true") ||  value.equals("false") )	
								{
									if (value.equals("true")) 
										newEntityinfo.put(getColName(rowsColList,i), (Boolean) true);  
									else if (  value.equals("false") )
										newEntityinfo.put(getColName(rowsColList,i), (Boolean) false);  
									else
										newEntityinfo.put(getColName(rowsColList,i), (Boolean) value);  
								}
								else
									if (isInteger(o))	
									{
										//			((AbstractField<String>) field).setConverter(new StringToIntegerConverter());

										newEntityinfo.put(getColName(rowsColList,i), (Integer) delPoints((String) rowsColList.get(i)[0]));  
									}
									else // normal fields that belongs to row
										if (rowsColList.get(i)[0].length() > 0)
											newEntityinfo.put(getColName(rowsColList,i), (String) ""+value);  
										else
											newEntityinfo.put(getColName(rowsColList,i), "");
							i++;
						}
				}
				else if(field.getName() != null && field.getName().startsWith("col") == true)  // even there is not data i must continue to keep syncronice col1, col2... 
				{
					i++;
				}
					
			}
			} catch (IllegalArgumentException | IllegalAccessException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}
		return newEntityinfo;
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
	

	private boolean isDate(Object o) {
//		if (o instanceof com.vaadin.ui.DateField)
//			return true;
		return false;
	}
	private boolean isCheckBox(Object o) {
//		if (o instanceof CheckBox )
//			return true;
		return false;
	}
	@SuppressWarnings("unchecked")
	private boolean isInteger(Object o) {
//		if (((AbstractField<String>) o).getConverter() != null)
//			if (((AbstractField<String>) o).getConverter().toString().indexOf("StringToIntegerConverter") > -1)
//				return true;
		return false;
	}
	private Integer delPoints(String value) {
		while (value.indexOf(".") > -1)
		{
			value = value.substring(0, value.indexOf(".")) + value.substring(value.indexOf(".")+ 1);
			System.err.println("VALUE .... sin puntos " + value);
		}

		return Integer.parseInt(value);
	}
	public void putJSonData(JsonNode eachRow, DynamicDBean dB, boolean isUpdate) { // same values are calculate in espresso and the nue value is returned in the lTxsumary, here fills the table 
		//		item.getItemProperty(id)
		boolean isNewItem = false; /// VER EN FormEL lo que hacía
		Field[] fields = dB.getClass().getDeclaredFields();
		int i=0;
		ArrayList<String[]> rowsColList = dB.getRowsColList();
		 // VER EN TAbeEL como gestiona que el resultado traiga varias tablas
		dB.setRowJSon(eachRow); 
		for(Field field : fields )  
		{
//			field.setInt(eachRow.get("code_customer").asInt());
			try {
				if (field.getName().equals("col"+i) && i < rowsColList.size())
					{
					field.setAccessible(true);
					String colName = getColName(rowsColList,i);
					if (eachRow.get(colName) != null && eachRow.get(colName).asText().equals("null") == false)
						field.set(dB, eachRow.get(colName).asText());
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

	private JsonNode getResourceFromResult(JsonNode lTxsumary, String preConfParam, String resourceName) {
//		String pathtables = tableEL.getPathtables();
		String href = searchRootResource(lTxsumary, resourceName);
		String url = href;
//		if (pathtables != null)
//			url = href.replace("main:"+tableName, pathtables);
		try {
			return JSonClient.get(url, preConfParam);//globalVars.getPreConfParam());
		} catch (Exception e) {
			e.printStackTrace();
		} 
		//		return lTxsumary;
		return null;
	}

	private String searchRootResource(JsonNode lTxsumary, String resourceName) { // in lTxsunary also comes subreources, we just want root resource
		int i = 0; 
		while (i < lTxsumary.size())
		{
		 String resource = lTxsumary.get(i).get("@metadata").get("resource").asText();
//		 if (resource.indexOf(".")> -1) // is a sub-resource
		 if (resource.equals(resourceName))
			 return lTxsumary.get(i).get("@metadata").get("href").asText();
		 else
			 i ++;
//		 else if ()
//			 return lTxsumary.get(i).get("@metadata").get("href").asText();
		}
		return null;
	}


	@Override
	public void updateDynamicDBean(String resourceTobeSave, Hashtable<String, DynamicDBean> beansToSaveAndRefresh) {
		updateDynamicDBean(beansToSaveAndRefresh.get(resourceTobeSave)); // Saves
		if (errorSaving == false)
		{
			int i = 0;
			while (i < allSaveSata.size()) // refresh the rest
			{
				String resourcelTX = allSaveSata.get(i).get("@metadata").get("resource").asText();
				if (resourcelTX.equals(resourceTobeSave) == false)
				{
					DynamicDBean beanTBR = beansToSaveAndRefresh.get(resourcelTX);
					if (beanTBR != null)
					{
					putJSonData(allSaveSata.get(i), beanTBR, false);
					}
				}
			i++;
			}
		}
		else
		{
			beansToSaveAndRefresh.put("ERROR", new DynamicDBean());
		}
		
	}

	@Override
	public void deleteDynamicDBean(String resourceTobeSave, Hashtable<String, DynamicDBean> beansToSaveAndRefresh) {
		deleteDynamicDBean(beansToSaveAndRefresh.get(resourceTobeSave)); // deletes
		if (errorSaving == false)
		{
			int i = 0;
			while (i < allSaveSata.size()) // refresh the rest
			{
				String resourcelTX = allSaveSata.get(i).get("@metadata").get("resource").asText();
				if (resourcelTX.equals(resourceTobeSave) == false)
				{
					DynamicDBean beanTBR = beansToSaveAndRefresh.get(resourcelTX);
					if (beanTBR != null)
					{
					putJSonData(allSaveSata.get(i), beanTBR, false);
					}
				}
			i++;
			}
		}
		else
		{
			beansToSaveAndRefresh.put("ERROR", new DynamicDBean());
		}
		
	}

	private void deleteDynamicDBean(DynamicDBean dB) {
		try
		{
	//		final JsonNodeFactory nodeFactory = JsonNodeFactory.instance;
			JsonNode rowJson = dB.getRowJSon();
			String resourceName = dB.getResourceName();
			String preConfParam = dB.getPreConfParam();
			//** Creo la Entidad
			

				JsonNode postResult;
				try {
		//			ObjectNode rowJsonChanged = putValuesOnObject(false, nodeFactory,dB, resourceName);
					postResult = JSonClient.delete( rowJson,  preConfParam); 

					if (postResult.get("statusCode").intValue() != 200)
					{
						//		fieldGroup.discard();
						//		fieldGroup.setItemDataSource(fieldBeforeCommit) ;
						//		tableEL.putJSonData(fieldBeforeCommit);
						//		fieldGroup.commit();
				//		showError(postResult.get("errorMessage").asText().substring(22));

			//			throw new RuntimeException("Unable to update: " + postResult);
						errorSaving = true;
						showError(postResult.get("errorMessage").asText());
						
					}
					else
					{
						errorSaving = false;
						lTxsumary = postResult.get("txsummary");
						allSaveSata = lTxsumary;
						if (lTxsumary.size() > 0 ) //&& tableEL != null)
						{
							lTxsumary = getResourceFromResult(lTxsumary, preConfParam, resourceName);
					//		JsonNode eachRow =  lTxsumary.get(0);
					//		putJSonData(eachRow, dB, true);
						}
						//		idEntity = lTxsumary.get(0).get("idEntity").intValue();
						System.err.println(" result  "+ lTxsumary); 
						showConfirmationSave("Registro eliminado con éxito!");
//						if (customerPickComponents != null)
//						{
//							Enumeration<Component> listCustomerPickComponents = customerPickComponents.elements();
//							while (listCustomerPickComponents.hasMoreElements())
//								{
//								Component pickComp =listCustomerPickComponents.nextElement();	
//								if (pickComp instanceof Label) 
//									((Label)pickComp).setValue(lTxsumary.get(0).get(pickComp.getId()).asText()); 
//								}
//							}
						}
					
				} catch (Exception e) {
					e.printStackTrace();
				}

			

		}
		catch (java.lang.NullPointerException nullE)
		{   
			System.err.println(" Paso por el Segundo Catch :");
			nullE.printStackTrace();
		}

		catch (Exception e ){//CommitException e) {
			// TODO @@revisar
			e.printStackTrace();
		}




		
	}
}



