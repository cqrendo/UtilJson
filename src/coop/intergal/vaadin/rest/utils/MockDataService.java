package coop.intergal.vaadin.rest.utils;

import java.lang.reflect.Field;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.JsonNodeFactory;
import com.fasterxml.jackson.databind.node.ObjectNode;

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
	public synchronized List<DynamicDBean> getAllDynamicDBean(int offset, int limit, boolean refreshFromServer, String resourceName, String preConfParam, ArrayList<String[]> rowsColList, String filtro) { // RestData.getResourceDaily(); 
	//	if (refreshFromServer)
			rows = RestData.getResourceData(offset,limit, resourceName, preConfParam, rowsColList, filtro, refreshFromServer);// refresh data from server each interaction with grid
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
				JsonNode postResult;
				try {
					
					postResult = JSonClient.post(resourceName, newEntityinfo, preConfParam);// TODO  -->//globalVars.getPreConfParam()); 

					if (postResult.get("statusCode").intValue() != 201)
					{
						throw new IllegalArgumentException("Unable to insert: " + postResult);
//						fieldGroup.discard();
//						showError(postResult.get("errorMessage").asText().substring(22));
//
//						throw new RuntimeException("Unable to insert: " + postResult);
					}
					else
					{
						
						JsonNode lTxsumary = postResult.get("txsummary");
						lTxsumary = getResourceFromResult(lTxsumary, preConfParam, resourceName);
						dB.setRowJSon(lTxsumary);
						putJSonData(lTxsumary, null,false);
						rows.add(dB);
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
					postResult = JSonClient.put(resourceName, rowJsonChanged,  preConfParam);// TODO  -->//globalVars.getPreConfParam()); 

					if (postResult.get("statusCode").intValue() != 200)
					{
						//		fieldGroup.discard();
						//		fieldGroup.setItemDataSource(fieldBeforeCommit) ;
						//		tableEL.putJSonData(fieldBeforeCommit);
						//		fieldGroup.commit();
				//		showError(postResult.get("errorMessage").asText().substring(22));

						throw new RuntimeException("Unable to update: " + postResult);
					}
					else
					{
						JsonNode lTxsumary = postResult.get("txsummary");
						if (lTxsumary.size() > 0 ) //&& tableEL != null)
						{
							lTxsumary = getResourceFromResult(lTxsumary, preConfParam, resourceName);
							putJSonData(lTxsumary, dB, true);
						}
						//		idEntity = lTxsumary.get(0).get("idEntity").intValue();
						System.err.println(" result  "+ lTxsumary); 
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
		}

		catch (Exception e ){//CommitException e) {
			// TODO @@revisar
			e.printStackTrace();
		}




	} 
		
private String getTableName(JsonNode rowJson) {
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
			newEntityinfo.put("@metadata",metadata);
			int i= 0;
			while (true)
			{
				String pkfield = JSonClient.getResourceHtPK().get(tableName).get("pkField"+i);
				if (pkfield == null)
					break;
				newEntityinfo.put(pkfield,rowJSon.get(pkfield));
				i++;
			}
		}
//		Iterator<Component> fieldList = editorForm.iterator(); 
		Field[] fields = dB.getClass().getDeclaredFields();
		ArrayList rowsColList = dB.getRowsColList();
		
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
						
						if (((String) rowsColList.get(i)).startsWith("FK-")) // Parent and Grand parent fields --> even doesn't have value must be fill to get filters from possible grant parents
						{
							//				setFKIdsForFilter(field.getId(), (String) field.getValue()); 
						}
						else // are "normal fields"
						{
							if(isDate(o))	
							{
								DateFormat fechaIni = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
								if (value != null)
									newEntityinfo.put(rowsColList.get(i).toString(),fechaIni.format(value) );
							}
							else
								if (isCheckBox(o))	
								{
									newEntityinfo.put(rowsColList.get(i).toString(), (Boolean) value);  
								}
								else
									if (isInteger(o))	
									{
										//			((AbstractField<String>) field).setConverter(new StringToIntegerConverter());

										newEntityinfo.put(rowsColList.get(i).toString(), (Integer) delPoints((String) rowsColList.get(i).toString()));  
									}
									else // normal fields that belongs to row
										if (rowsColList.get(i).toString().toString().length() > 0)
											newEntityinfo.put(rowsColList.get(i).toString(), (String) ""+value);  
										else
											newEntityinfo.put(rowsColList.get(i).toString(), "");
							i++;
						}
				}	
			}
			} catch (IllegalArgumentException | IllegalAccessException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}
		return newEntityinfo;
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
	public void putJSonData(JsonNode lTxsumary, DynamicDBean dB, boolean isUpdate) { // same values are calculate in espresso and the nue value is returned in the lTxsumary, here fills the table 
		//		item.getItemProperty(id)
		boolean isNewItem = false; /// VER EN FormEL lo que hacía
		Field[] fields = dB.getClass().getDeclaredFields();
		int i=0;
		ArrayList rowsColList = dB.getRowsColList();
		JsonNode eachRow = lTxsumary.get(0); // VER EN TAbeEL como gestiona que el resultado traiga varias tablas
		dB.setRowJSon(eachRow); 
		for(Field field : fields )  
		{
//			field.setInt(eachRow.get("code_customer").asInt());
			try {
				if (field.getName().equals("col"+i) && i < rowsColList.size())
					{
					field.setAccessible(true);
					field.set(dB, eachRow.get((String)rowsColList.get(i)).asText());
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

}
