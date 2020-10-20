package coop.intergal.vaadin.rest.utils;

import java.io.InputStream;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Date;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;

import com.fasterxml.jackson.databind.JsonNode;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.html.NativeButton;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.notification.Notification.Position;

import coop.intergal.AppConst;
import coop.intergal.AppConstGeneric;
import coop.intergal.espresso.presutec.utils.JSonClient;



public class RestData {


	private static final boolean CACHE_TRUE = true;
	static Hashtable<String, String> keepFieldName = new Hashtable<String, String>();

	public static List<DynamicDBean> getResourceData(int offset, int limit, String resourceName, String preConfParam, ArrayList<String[]> rowsColList, String filter, boolean cache, Boolean hasNewRow) {

		if (cache == false)
			keepFieldName.clear();
		if (resourceName  == null ||  resourceName.trim().length() == 0)
		{
// yolaqr			throw new UserFriendlyDataException("NO hay tabla seleccionada, revise el link"); //"XesbentaLAC"
//			return null;
		}
		String pagesize = limit+""; 
		if (limit==0) // when is not using a dataprovoder with paging and pr-count of rows limit is cero; 
			pagesize=AppConstGeneric.DEFAULT_PAGESIZE;
		
		List<DynamicDBean> customerList = new ArrayList<DynamicDBean>();
		JsonNode rowsList = null;
		try { //TODO CACHE IS FALSE always , put as param, 
		//	String filtro = null;
			if (filter == null || filter.equals("null"))
				filter="";
			if (offset > 0)
				filter=filter+"&offset="+offset;
//			System.out.println("RestData.getResourceCustomer() before GET" + new Date());
			//"40"); // vamos a probar sin cache, para activarlo poner "cache" en vez de false
//			System.out.println("RestData.getResourceCustomer() after GET" + new Date());
//			JsonNode cols = JSonClient.getColumnsFromTable(resourceName, null, true, preConfParam);
//			
//	//		String[] rowsColList = new String[] {};
//			ArrayList<String> rowsColList = new ArrayList<String>();
//			Iterator<String> fN = cols.get(0).fieldNames();
//			while (fN.hasNext()) {
//				rowsColList.add(fN.next());
//			}
	//		String[] rowsColList = new String[] { "code_customer", "name_customer", "cif" , "amount_un_disbursed_payments"};
			if (hasNewRow)
			{
				DynamicDBean d = fillRowDefaultValues(rowsColList, resourceName);
				d.setResourceName(resourceName);
				d.setPreConfParam(preConfParam);
				d.setFilter(filter); // for newRows fill data from filter normally parent data
				int maxNumberOfFields = AppConst.MAX_NUMBER_OF_FIELDS_PER_TABLE;
				String maxNumberOfFieldsSTR = "";
				if (rowsColList.size() > 15)
					maxNumberOfFieldsSTR = rowsColList.get(0)[15];				
				System.out.println("RestData.fillRow() maxNumberOfFieldsSTR <<"+  maxNumberOfFieldsSTR + ">>");
				if (maxNumberOfFieldsSTR.length() > 0)
					maxNumberOfFields = new Integer(maxNumberOfFieldsSTR);
				d.setMaxColNumber(maxNumberOfFields);
				customerList.add(d);
				int pagesizeInt = new Integer(pagesize);
				if (pagesizeInt > 0)
					pagesize = pagesizeInt- 1 +"";
			}
			rowsList = JSonClient.get(resourceName,filter,false,preConfParam,pagesize+"");
			if (rowsList.get("statusCode") != null)
			{
				String errorMsg = rowsList.get("errorMessage").asText();
		
				//	showError(errorMsg);  doesn't work in this scope, 
				System.err.println("*********** ERROR ******* "+errorMsg);
				DynamicDBean d = new DynamicDBean();
				d.setCol0("##ERROR## "+ errorMsg);
				customerList.add(d);
				return customerList;
			}
			else
			{
			String col1name = rowsColList.get(0)[0]; 
			int i = 0;
			while (col1name.equals("#SPACE#"))  // to get the first real field
			{
				col1name = rowsColList.get(i)[0];
				i ++;
			}
			if ( rowsList.get(col1name) != null) // it means that the result is only one row, not an array, then no loop  
			{
				DynamicDBean d = fillRow(rowsList, rowsColList, preConfParam, resourceName);//, cols.get(0)); 
				d.setResourceName(resourceName);
				d.setPreConfParam(preConfParam);
				d.setFilter(filter);
				customerList.add(d);				
			}
			else
			{	
			for (JsonNode eachRow : rowsList)  {
				
//				i = 0;
//				while (col1name.equals("#SPACE#"))  // to get the first real field
//				{
//					col1name = rowsColList.get(i)[0];
//					i ++;
//				}
//				while (isNotCombined(col1name, eachRow)) // combined fields, doesn't exist always
//				{
//					i++;
//					col1name = rowsColList.get(i)[0];					
//				}
					
//				System.out.println("RestData.getResourceData() col1name "+ col1name +" "+ eachRow.get(col1name));
				if (isARealRow(eachRow)) // when are more rows than a pagesize it comes a row with out data TODO handle this page
				{
					DynamicDBean d = fillRow(eachRow, rowsColList, preConfParam, resourceName);//, cols.get(0)); 
					d.setResourceName(resourceName);
					d.setPreConfParam(preConfParam);
					d.setFilter(filter);
					customerList.add(d);
				}
			}	
		}
		}
		} catch (Exception e) {
			e.printStackTrace();
		}// preConfParam, null);//globalVars.getPagesize());
		System.out.println("RestData.getResourceCustomer() after FILL LIST "+ resourceName + " Filter:" +filter + " " + new Date());
		return customerList;
	}

	private static boolean isARealRow(JsonNode eachRow) {
		JsonNode metadata = eachRow.get("@metadata");
		if (metadata == null) // not always a row comes with @metadata
			return true;
		if (metadata.get("next_batch") == null && metadata.get("previous_batch") == null)
			return true;
		else
			return false;
	}

	private static boolean isCombined(String col1name, JsonNode eachRow) {
		JsonNode metadata = eachRow.get("@metadata");
		JsonNode combined = metadata.get("combined");
		if (combined == null)
			return false;
//		if (marks.indexOf("#SIG#"))
//			return true;
		for (JsonNode eachfield : combined) 
		{
			if (col1name.equals(eachfield.asText()))
					return true;
		}
		
		return true;
	}

	private static DynamicDBean fillRowDefaultValues(ArrayList<String[]> rowsColList, String resourceName) {
		// TODO Auto-generated method stub
		DynamicDBean dB = new DynamicDBean();
//		dB.setRowJSon(eachRow);
		dB.setRowsColList(rowsColList); // TODO instead of put in each row, think in a way for only once (maybe row o) 
//		dB.setRowColTypeList(cols); TODO Keep also cols type
//		Object userInstance = DynamicDBean.getConstructor(new Class[] {String.class}).newInstance(new Object[] {"José González"});
//		Field aliasField = userClass.getDeclaredField("alias");
//		aliasField.setAccessible(true);
//		aliasField.set(userInstance, "Pepe");
		Field[] fields = dB.getClass().getDeclaredFields();
		int i=0;
		int maxNumberOfFields = AppConst.MAX_NUMBER_OF_FIELDS_PER_TABLE;
		String maxNumberOfFieldsSTR = rowsColList.get(0)[15];
		if (maxNumberOfFieldsSTR.length() > 0)
			maxNumberOfFields = new Integer(maxNumberOfFieldsSTR);
		for(Field field : fields )  
		{
//			field.setInt(eachRow.get("code_customer").asInt());
			try {
				if (field.getName().equals("col"+i) && i < maxNumberOfFields)//rowsColList.size())
					{
					field.setAccessible(true);
					String colName = getColName(rowsColList,i,resourceName);
					if ("null".equals(colName) == false)
//					if (rowsColList.get(i) !=null)
						{
				//		String colName = getColName(rowsColList,i);
						String defaultValue = getDefaultValue(rowsColList,i);
						if (defaultValue==null || defaultValue.equals("null"))
							field.set(dB, null);
						else
						{
							String value = defaultValue;
							if (value.equals("null"))
								value= "";
							field.set(dB, value);
						}	
						}
					i++;
					}
			} catch (IllegalArgumentException | IllegalAccessException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			if (i>maxNumberOfFields)//rowsColList.size()) 
				break;
		}
//		c.setCodeCustomer(eachRow.get("code_customer").asInt());
//		c.setNameCustomer(eachRow.get("name_customer").asText());
		

		return dB;
	}

	private static DynamicDBean fillRow(JsonNode eachRow, ArrayList<String[]> rowsColList, String preConfParam, String resourceName) {// JsonNode cols) {
//		Class dynamicDBeanClass = Class.forName("coop.intergal.xespropan.production.samples.backend.data.DynamicDBean");
//		System.out.println("RestData.fillRow()");
		DynamicDBean dB = new DynamicDBean();
		dB.setRowJSon(eachRow);
		dB.setRowsColList(rowsColList); // TODO instead of put in each row, think in a way for only once (maybe row o) 
		if (eachRow.get("readOnly") != null) // to mark as read only add row.readONly=true to the event of the resource in LAC 
		{
			if (eachRow.get("readOnly").asBoolean())
				dB.setReadOnly(true);
			else
				dB.setReadOnly(false);
				
		}
		else
			dB.setReadOnly(false);
//		dB.setRowColTypeList(cols); TODO Keep also cols type
//		Object userInstance = DynamicDBean.getConstructor(new Class[] {String.class}).newInstance(new Object[] {"José González"});
//		Field aliasField = userClass.getDeclaredField("alias");
//		aliasField.setAccessible(true);
//		aliasField.set(userInstance, "Pepe");
		Field[] fields = dB.getClass().getDeclaredFields();
		int i=0;
		int maxNumberOfFields = AppConst.MAX_NUMBER_OF_FIELDS_PER_TABLE;
		String maxNumberOfFieldsSTR = "";
		if (rowsColList.size() > 15)
			maxNumberOfFieldsSTR = rowsColList.get(0)[15];
		
//		System.out.println("RestData.fillRow() maxNumberOfFieldsSTR <<"+  maxNumberOfFieldsSTR + ">>");
		if (maxNumberOfFieldsSTR.length() > 0)
			maxNumberOfFields = new Integer(maxNumberOfFieldsSTR);
		dB.setMaxColNumber(maxNumberOfFields);
		for(Field field : fields )  
		{
//			System.out.println("RestData.fillRow() field "+ field.getName());
//			field.setInt(eachRow.get("code_customer").asInt());
			try {
				if (field.getName().equals("col"+i) && i <= maxNumberOfFields)//rowsColList.size())//maxNumberOfFields)
					{
					field.setAccessible(true);
					String colName = getColName(rowsColList,i, resourceName);
					if ("null".equals(colName) == false)
						{
						
						JsonNode data = eachRow.get(colName);
						if (colName.indexOf("FK-") > -1)
						{
							data = getDataFromFGNode(eachRow,colName);
						}
							
						if (data==null)
							field.set(dB, null);
						else
						{
							if (isNotABinary(data))
							{
								String value = data.asText();
								if (value.equals("null"))
									value= "";
								field.set(dB, value);
							}
							else
							{
								
								JsonNode binaryData= data;
								String url = binaryData.get("url").asText();
								keepStreaminDb(dB, url,preConfParam );
							}
						}	
						}
					i++;
					}
			} catch (IllegalArgumentException | IllegalAccessException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			if (i>maxNumberOfFields) 
				break;
		}
//		c.setCodeCustomer(eachRow.get("code_customer").asInt());
//		c.setNameCustomer(eachRow.get("name_customer").asText());
		

		return dB;
	}
	private static JsonNode getDataFromFGNode(JsonNode eachRow, String colName) {
		while (true)
		{
		String fkNodeName = colName.substring(0, colName.indexOf("."));
		JsonNode node = eachRow.get(fkNodeName);
		if (node == null)
			return null;
		colName = colName.substring(colName.indexOf(".")+1);
		if (colName.indexOf("FK-") == -1)
			{
			return node.get(colName);
			}
		eachRow = node;
		}
	}

	private static void keepStreaminDb(DynamicDBean dB, String url, String preConfParam) {
		try {
			InputStream inputStream = JSonClient.getStream(url, preConfParam);
//			byte[] bytes = IOUtils.toByteArray(inputStream);
//			String encoded =   "0x"+bytesToHex(bytes);//Base64.getEncoder().encodeToString(bytes);
			dB.setInputStream(inputStream);
//			JSonClient.getClientStream().close();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}			
	}


	private static boolean isNotABinary(JsonNode jsonNode) {
		if (jsonNode.get("type") != null)
		{
			if (jsonNode.get("type").asText().equals("binary"))
				return false;
		}
		// TODO Auto-generated method stub
		return true;
	}

	private static String getColName(ArrayList<String[]> rowsColList, int i, String resourceName) { // normally the col.. is syncronice with i secuence, but is rowColList have some fields not in natural position then must be search the name in other way
		String colNameInUIinColList ="null";
		String colNameInUIGenByI = "col"+i;
		String resourceAndFieldNinUI = resourceName + "&" + colNameInUIGenByI;
		if (keepFieldName.get(resourceAndFieldNinUI) != null)
		{
//			System.out.println("RestData.getColName() FOUND IN HASHTABLE");
			if (keepFieldName.get(resourceAndFieldNinUI).equals("null") == false)
				return keepFieldName.get(resourceAndFieldNinUI);
		}
		if (rowsColList.size() > i)
			colNameInUIinColList = rowsColList.get(i)[2];
		if ( colNameInUIinColList.equals(colNameInUIGenByI) || colNameInUIinColList.isEmpty() ) // if colinIU = col... then return colName 
			return rowsColList.get(i)[0];
		else // otherwise it searches
		{
//			System.out.println("RestData.getColName() ----> Fields witn col... not in order "+ "col"+i );
			int cont = 0;
			for (String[] row : rowsColList) // search for col.. to get his column name
			{
				cont ++;
				if (row[2].equals(colNameInUIGenByI))
				{
//					System.out.println("FOUND... "+ row[2] ) ;
					keepFieldName.put(resourceAndFieldNinUI,row[0] );
					return row[0];
				}	
//				System.out.println("RestData.getColName() search colname row[2] "+ row[2] + " "+ colNameInUI +" "+ new Date() + " paso:"+ cont);
			}
			keepFieldName.put(resourceAndFieldNinUI,"null" );	
			return "null";
		}
	}
	private static String getDefaultValue(ArrayList<String[]> rowsColList, int i) {
		String colNameInCL ="null";
		if (rowsColList.size() > i)
			colNameInCL = rowsColList.get(i)[2];
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
	public static void refresh(DynamicDBean dDb) {
		getResourceData(0,0,dDb.getResourceName(), dDb.getPreConfParam(), dDb.getRowsColList(), dDb.getFilter(), true, false);
		
	}
	public static int  getCountRows(String resourceName, String preConfParam, String filter, boolean cache, boolean hasNewRow) {

		if (resourceName  == null ||  resourceName.trim().length() == 0)
		{
// yolaqr			throw new UserFriendlyDataException("NO hay tabla seleccionada, revise el link"); //"XesbentaLAC"
//			return null;
		}

		int count = 0;
		List<DynamicDBean> customerList = new ArrayList<DynamicDBean>();
		JsonNode rowsList = null;
		if (resourceName.startsWith("@")) // is to handle @resources by example because the resource name it changes @ by _
			resourceName = resourceName.replace("@", "_");
		try { //TODO CACHE IS FALSE always , put as param
		//	String filtro = null;
			System.out.println("RestData.getCountRows() resourceName " + resourceName + " filter " + filter +  " preConfParam " + preConfParam);
			rowsList = JSonClient.get("Count_"+resourceName,filter,cache,preConfParam,"1"); 
			if (rowsList.get("statusCode") != null)
			{
				showError(rowsList.get("errorMessage").asText());
			}
			else
			{
				count = rowsList.get(0).get("count(*)").asInt();
			}

		
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}// preConfParam, null);//globalVars.getPagesize());
//		System.out.println("RestData.getResourceCustomer() after FILL LIST" + new Date());
		System.out.println("RestData.getCountRows()--------"+ count);
//		if (hasNewRow)
//			{
//			count = count+1;
//			System.out.println("RestData.getCountRows()--------(hasNewRow) "+ count);
//			
//			Integer limit = new Integer (AppConstGeneric.DEFAULT_PAGESIZE);
//			Integer actualCount =  new Integer (count);
//			if (actualCount > limit)
//				count = limit+1;
//			}
		return count;
	}

	public static JsonNode getDataValueFromAFieldOfAResource(String resourceName, String field, String filter, String preConfParam) {
		JsonNode value = null;
		try {
			JsonNode rowsList = JSonClient.get(resourceName,filter,false,preConfParam,"40");
			if (rowsList != null)
				if (rowsList.get(0) != null)
				{
					value = rowsList.get(0).get(field);
				}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return value; 
		
	}

	public static DynamicDBean getOneRow(String resourceName, String filter, String preConfParam, ArrayList<String[]> rowsColList)
	{
		try {
			JsonNode rowsList = JSonClient.get(resourceName,filter,false,preConfParam,"20");
			rowsColList =  getRowsColList(rowsColList, resourceName, preConfParam, null);//, "");
			for (JsonNode eachRow : rowsList)  {
				if (eachRow.get(rowsColList.get(0)[0]) !=null)
				{
					DynamicDBean d = fillRow(eachRow, rowsColList, preConfParam, resourceName);//, cols.get(0)); 
					d.setResourceName(resourceName);
					d.setPreConfParam(preConfParam);
					d.setFilter(filter);
					return d;
				}
			}	
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}//"40"); // vamos a probar sin cache, para activarlo poner "cache" en vez de false

		return null;
		
	}
	public static boolean checkIfExist(String resourceName, String filter , String preConfParam)
	{
		try {
			JsonNode rowsList = JSonClient.get(resourceName,filter,false,preConfParam,"1");
			if (rowsList.size() > 0)
				return true;
			else
				return false;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}//"40"); // vamos a probar sin cache, para activarlo poner "cache" en vez de false

		return 	false;
		
	}
	public static ArrayList<String[]> getRowsColList(ArrayList<String[]> rowsColList, String resourceName, String preConfParam, Boolean cache){//, String variant) { // variant is use to have different lists of fields in the same resource
		if (cache == null)
		{
			cache = CACHE_TRUE;
		}	
		if (rowsColList == null || rowsColList.isEmpty() || cache == false)
				{
				JsonNode cols;
				try {				
					String genericResourceName = resourceName;
//					int indx__ = genericResourceName.indexOf("__"); // -- indicates variations over same resource, or same means same field list
//					int idxPomt = resourceName.indexOf(".");
//					if (indx__ > 1 && idxPomt == -1) // only when there is not a subresource (after a point), you can extract the generic name from first name substring(0....
//						genericResourceName = resourceName.substring(0, indx__);
//					String tableNameToSearch = genericResourceName+variant;
					String tableNameToSearch = genericResourceName;
					String filter = "tableName='"+tableNameToSearch+"'%20AND%20isDataField=true&order=colOrder";

					System.out.println("RestData.getRowsColList()  tablename to search = "+tableNameToSearch  + " "+ new Date());
					cols = JSonClient.get("CR-FieldTemplate",filter, cache, AppConst.PRE_CONF_PARAM_METADATA);
					if (cols != null && cols.size() > 0 && cols.get("errorMessage") == null)
					{
						rowsColList = new ArrayList<String[]>();
						int i = 0;
						int maxColNumber = 0;
						for (JsonNode col :cols)
						{
							String[] fieldArr  = new String[17];
							fieldArr[0] = col.get("fieldName").asText();
							if ( col.get("showInGrid").asBoolean())
								fieldArr[1] = "#SIG#";
							else
								fieldArr[1] = "";
							if ( col.get("isSorteable").asBoolean())
								fieldArr[1] = fieldArr[1]+"SORT";
							fieldArr[0] = col.get("fieldName").asText();
							if ( col.get("isReadOnly") != null && col.get("isReadOnly").asBoolean())
								fieldArr[1] = fieldArr[1]+"#CNoEDT#";
							if ( col.get("parentResource") != null && col.get("parentResource").asText().trim().length() > 1 && col.get("parentResource").asText().trim().equals("null")== false) 
							{
								fieldArr[1] = fieldArr[1]+"#PCK#";
			
							}
							if (fieldArr[0].equals("pickMapFields"))  // is a field with a special PICK
								fieldArr[1] = fieldArr[1]+"#PCK#FOR#pickMapFields";
//							if ( col.get("isRequired") != null && col.get("isRequired").asBoolean())
//								fieldArr[1] = fieldArr[1]+"#REQ#";
							if ( col.get("FieldNameInUI").asText().isEmpty())
								fieldArr[2] = "col"+i;	
							else
								fieldArr[2] = col.get("FieldNameInUI").asText();
							if ( col.get("idFieldType").asText().isEmpty() || col.get("idFieldType").asText().equals("null"))
								fieldArr[3] = "";
							else
								fieldArr[3] = col.get("idFieldType").asText();
							if ( col.get("PathToParentField").asText().isEmpty() || col.get("PathToParentField").asText().equals("null"))
								fieldArr[4] = "";
							else
								fieldArr[4] = col.get("PathToParentField").asText();
							if ( col.get("defaultValue").asText().isEmpty() || col.get("defaultValue").asText().equals("null"))
								fieldArr[5] = "";
							else
								fieldArr[5] = col.get("defaultValue").asText();
							if ( col.get("colOrder").asText().isEmpty())
								fieldArr[6] = "";
							else if ( col.get("colOrder").asText().contains("#")) // after the # becames the col header
								{
								String colOrder =  col.get("colOrder").asText();
								fieldArr[6] =colOrder.substring(colOrder.indexOf("#")+1);
								}
							else
								fieldArr[6] = "";
							if ( col.get("fieldOrder").asText().isEmpty())
								fieldArr[7] = "";
							else if ( col.get("fieldOrder").asText().contains("#")) // after the # becames the field label
								{
								String fieldOrder =  col.get("fieldOrder").asText();
								fieldArr[7] =fieldOrder.substring(fieldOrder.indexOf("#")+1);
								}
							else
								fieldArr[7] = "";
							if ( col.get("queryOrder").asText().isEmpty())
								fieldArr[8] = "";
							else if ( col.get("queryOrder").asText().contains("#")) // after the # becames the query label
								{
								String fieldOrder =  col.get("queryOrder").asText();
								fieldArr[8] =fieldOrder.substring(fieldOrder.indexOf("#")+1);
								}
							else
								fieldArr[8] = "";
							if ( col.get("titleDisplay").asText().isEmpty() || col.get("titleDisplay").asText().equals("null") )
								fieldArr[9] = "";
							else
								fieldArr[9] = col.get("titleDisplay").asText();
							if ( col.get("titleQuery").asText().isEmpty() || col.get("titleQuery").asText().equals("null") )
								fieldArr[10] = "";
							else
								fieldArr[10] = col.get("titleQuery").asText();
							if ( col.get("titleGrid").asText().isEmpty() || col.get("titleGrid").asText().equals("null") )
								fieldArr[11] = "";
							else
								fieldArr[11] = col.get("titleGrid").asText();
							fieldArr[12] = ""; // is only used for query fields
							fieldArr[13] = ""; // is only used for Form fields
							fieldArr[14] = ""; // is only used for Form fields
							
							if ( col.get("maxColNumber").asText().isEmpty() || col.get("maxColNumber").asText().equals("null") )
								fieldArr[15] = AppConst.MAX_NUMBER_OF_FIELDS_PER_TABLE +"";
							else
								fieldArr[15] = col.get("maxColNumber").asText();
							fieldArr[16] = ""; // is only used for Form fields
							rowsColList.add(fieldArr);
							i++;
						}
						// **** As the getColumnsFromTable is not call the keepJoinConditionSubResources is call from here
						if (resourceName.startsWith("@")==false) // starts with @ it means system table that doesn't exist in @resources, in fact could be the @resources itself
							{
							int idxPoint = resourceName.indexOf(".");
							if (idxPoint > -1) 
							resourceName = resourceName.substring(0, idxPoint);
							String ident = JSonClient.getIdentOfResuorce(resourceName, true,preConfParam);
						
							 
							if (ident !=null) 
								{
								JsonNode resource = JSonClient.get("@resources/"+ident,null,true,preConfParam); 
								if ((resource.get("statusCode") != null && resource.get("statusCode").asInt() != 500) || resource.get("statusCode") == null)  // TODO check why sme times you get 500, by example when you do 	DynamicDBean dynamicDBean = RestData.getOneRow(RESOURCE_FIELD_TEMPLATE,filter, AppConst.PRE_CONF_PARAM_METADATA, null); in consrain.java

									JSonClient.keepJoinConditionSubResources(resource); 
								}
							}
					}
					
					else	
					{
						
						rowsColList = getColListFromTable(resourceName, preConfParam);
					}
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				}	
			return rowsColList;
		}

	public static ArrayList<String[]> getRowsFieldList(ArrayList<String[]> rowsColList, String resourceName, String preConfParam, Boolean cache){//, String variant) { // variant is use to have different lists of fields in the same resource
		if (cache == null)
		{
			cache = CACHE_TRUE;
		}
		if (rowsColList == null || rowsColList.isEmpty() || cache == false)
			{
			JsonNode cols;
			try {				
				String genericResourceName = resourceName;
//				int indx__ = genericResourceName.indexOf("__"); // -- indicates variations over same resource, or same means same field list
//				int idxPomt = resourceName.indexOf(".");
//				if (indx__ > 1 && idxPomt == -1) // only when there is not a subresource (after a point), you can extract the generic name from first name substring(0....
//					genericResourceName = resourceName.substring(0, indx__);
//				String tableNameToSearch = genericResourceName+variant;
				String tableNameToSearch = genericResourceName;
				System.out.println("RestData.getRowsFieldList()  tablename to search = "+tableNameToSearch );
				String filter = "tableName='"+tableNameToSearch+"'%20AND%20showInDisplay=true&order=fieldOrder";
				cols = JSonClient.get("CR-FieldTemplate",filter , cache, AppConst.PRE_CONF_PARAM_METADATA); // TODO put false to true
				if (cols != null && cols.size() > 0 && cols.get("errorMessage") == null)
				{
					rowsColList = new ArrayList<String[]>();
					int i = 0;
					for (JsonNode col :cols)
					{
						String[] fieldArr  = new String[17];
						fieldArr[0] = col.get("fieldName").asText();
						if ( col.get("isReadOnly") != null && col.get("isReadOnly").asBoolean())
							fieldArr[1] = fieldArr[1]+"#CNoEDT#";
						if ( col.get("isRequired") != null && col.get("isRequired").asBoolean())
							fieldArr[1] = fieldArr[1]+"#REQ#";
						if ( col.get("parentResource") != null && col.get("parentResource").asText().trim().length() > 1 && col.get("parentResource").asText().trim().equals("null")== false) 
							fieldArr[1] = fieldArr[1]+"#PCK#";
						if ( col.get("FieldNameInUI").asText().isEmpty())
							fieldArr[2] = "col"+i;	
						else
							fieldArr[2] = col.get("FieldNameInUI").asText();
						if ( col.get("idFieldType").asText().isEmpty() || col.get("idFieldType").asText().equals("null"))
							fieldArr[3] = "";
						else
							fieldArr[3] = col.get("idFieldType").asText();
						if ( col.get("PathToParentField").asText().isEmpty() || col.get("PathToParentField").asText().equals("null"))
							fieldArr[4] = "";
						else
							fieldArr[4] = col.get("PathToParentField").asText();
						if ( col.get("defaultValue").asText().isEmpty() || col.get("defaultValue").asText().equals("null"))
							fieldArr[5] = "";
						else
							fieldArr[5] = col.get("defaultValue").asText();
						if ( col.get("fieldOrder").asText().isEmpty())
							fieldArr[6] = "";
						else if ( col.get("fieldOrder").asText().contains("#")) // after the # becames the field label
							{
							String fieldOrder =  col.get("fieldOrder").asText();
							fieldArr[6] =fieldOrder.substring(fieldOrder.indexOf("#")+1);
							}
						else
							fieldArr[6] = "";
						if ( col.get("fieldWidth").asText().isEmpty() || col.get("fieldWidth").asText().equals("null") )
							fieldArr[7] = "8";
						else
							fieldArr[7] = col.get("fieldWidth").asText();
						if ( col.get("cssStyle").asText().isEmpty() || col.get("cssStyle").asText().equals("null") )
							fieldArr[8] = "";
						else
							fieldArr[8] = col.get("cssStyle").asText();
						if ( col.get("titleDisplay").asText().isEmpty() || col.get("titleDisplay").asText().equals("null") )
							fieldArr[9] = "";
						else
							fieldArr[9] = col.get("titleDisplay").asText();
						if ( col.get("titleQuery").asText().isEmpty() || col.get("titleQuery").asText().equals("null") )
							fieldArr[10] = "";
						else
							fieldArr[10] = col.get("titleQuery").asText();
						if ( col.get("titleGrid").asText().isEmpty() || col.get("titleGrid").asText().equals("null") )
							fieldArr[11] = "";
						else
							fieldArr[11] = col.get("titleGrid").asText();
//						if ( col.get("cssStyleQueryField").asText().isEmpty() || col.get("cssStyleQueryField").asText().equals("null") )
							fieldArr[12] = ""; // is only used for query fields
//						else
//							fieldArr[12] = col.get("cssStyleQueryField").asText();
						if ( col.get("fieldSize").asText().isEmpty() || col.get("fieldSize").asText().equals("null") )
							fieldArr[13] = "";
						else
							fieldArr[13] = col.get("fieldSize").asText();	
						if ( col.get("validationRuleName").asText().isEmpty() || col.get("validationRuleName").asText().equals("null") )
							fieldArr[14] = "";
						else
							fieldArr[14] = col.get("validationRuleName").asText();	
						if ( col.get("maxColNumber").asText().isEmpty() || col.get("maxColNumber").asText().equals("null") )
							fieldArr[15] = AppConst.MAX_NUMBER_OF_FIELDS_PER_TABLE +"";
						else
							fieldArr[15] = col.get("maxColNumber").asText();	
						if ( col.get("toolTip").asText().isEmpty() || col.get("toolTip").asText().equals("null") )
							fieldArr[16] = "";
						else
							fieldArr[16] = col.get("toolTip").asText();	

						rowsColList.add(fieldArr);
						i++;
					}
					// **** As the getColumnsFromTable is not call the keepJoinConditionSubResources is call from here
					if (resourceName.startsWith("@")==false) // starts with @ it means system table that doesn't exist in @resources, in fact could be the @resources itself
						{
						int idxPoint = resourceName.indexOf(".");
						if (idxPoint > -1) 
						resourceName = resourceName.substring(0, idxPoint);
						String ident = JSonClient.getIdentOfResuorce(resourceName, true,preConfParam);
						if(ident != null)
							{
							JsonNode resource = JSonClient.get("@resources/"+ident,null,true,preConfParam);  
							JSonClient.keepJoinConditionSubResources(resource); 
							}
						}
						
				}
				
				else	
				{
					rowsColList = getColListFromTable(resourceName, preConfParam);
				}
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			}	
		return rowsColList;
	}
	private static ArrayList<String[]> getColListFromTable(String resourceName, String preConfParam) {
		JsonNode cols = JSonClient.getColumnsFromTable(resourceName, null, CACHE_TRUE, preConfParam);
		
		ArrayList<String[]> rowsColList = new ArrayList<String[]>();
		Iterator<String> fN = cols.get(0).fieldNames();
		int i = 0;
		while (fN.hasNext()) {
			String[] fieldArr  = new String[17];
			String fieldName = fN.next();
			fieldArr[0] =fieldName;
			
			fieldArr[1] = "#SIG#";							
			String type = cols.get(0).get(fieldName).asText();		
			fieldArr[2] = "col"+i;								
			fieldArr[3] = "";
			fieldArr[4] = ""; // @@ TODO get FK data
			fieldArr[5] = "";
			fieldArr[6] = fieldName;
			fieldArr[7] = "8";
			fieldArr[8] = "";
			fieldArr[9] = "";
			fieldArr[10] = "";
			fieldArr[11] = "";
			fieldArr[12] = "";
			fieldArr[13] = "";
			fieldArr[14] = "";
			fieldArr[15] = AppConst.MAX_NUMBER_OF_FIELDS_PER_TABLE +"";
			fieldArr[16] = ""; // is only used for Form fields
			if (type.equals("Date"))
				fieldArr[3] = "1";
			rowsColList.add(fieldArr);
			i++;
		}
		return rowsColList;
	}

	public static DynamicDBean copyDatabean( DynamicDBean fromBean) {
		DynamicDBean toBean = new DynamicDBean();
//		toBean.setCol0(fromBean.getCol0());
		Field[] fieldsTobean = toBean.getClass().getDeclaredFields();
//		Field[] fieldsFromBean = fromBean.getClass().getDeclaredFields();
		int i=0;
		for(Field field : fieldsTobean )  
		{
//			field.setInt(eachRow.get("code_customer").asInt());
			try {
			
				field.setAccessible(true);
				if (field.getName().equals("col"+i))
				{	
				Method getColX = ((DynamicDBean.class)).getMethod("getCol"+i);
				String value = (String) getColX.invoke(fromBean);
				if (value == null|| value.equals("null"))
							value= "";
				field.set(toBean, value);
						
					i++;
				}
	
					
			} catch (IllegalArgumentException | IllegalAccessException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (NoSuchMethodException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (SecurityException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (InvocationTargetException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		
		}
		toBean.setFilter(fromBean.getFilter());
		toBean.setPreConfParam(fromBean.getPreConfParam());
		toBean.setResourceName(fromBean.getResourceName());
		toBean.setRowColTypeList(fromBean.getRowColTypeList());
		toBean.setRowsColList(fromBean.getRowsColList());
		toBean.setRowJSon(fromBean.getRowJSon());
		return toBean;
		
	}

	public static ArrayList<String[]> getRowsColList(ArrayList<String[]> rowsColList, String resourceName, String preConfParam) {
		// TODO Auto-generated method stub
		return getRowsColList(rowsColList, resourceName, preConfParam, null);
	}

	/// ******* CAMPOS QUERY ********
	public static ArrayList<String[]> getRowsQueryFieldList(ArrayList<String[]> rowsFIeldQueryList, String resourceName,String preConfParam, Boolean cache){

		if (cache == null)
		{
			cache = CACHE_TRUE;
		}
		if (rowsFIeldQueryList == null || rowsFIeldQueryList.isEmpty() || cache == false)
			{
			JsonNode cols;
			try {				
				String genericResourceName = resourceName;
//				int indx__ = genericResourceName.indexOf("__"); // -- indicates variations over same resource, or same means same field list
//				int idxPomt = resourceName.indexOf(".");
//				if (indx__ > 1 && idxPomt == -1) // only when there is not a subresource (after a point), you can extract the generic name from first name substring(0....
//					genericResourceName = resourceName.substring(0, indx__);
//				String tableNameToSearch = genericResourceName+variant;
				String tableNameToSearch = genericResourceName;
				System.out.println("RestData.getRowsFieldList()  tablename to search = "+tableNameToSearch );
				String filter = "tableName='"+tableNameToSearch+"'%20AND%20showInQuery=true&order=queryOrder";
				cols = JSonClient.get("CR-FieldTemplate",filter , cache, AppConst.PRE_CONF_PARAM_METADATA); // TODO put false to true
				if (cols != null && cols.size() > 0 && cols.get("errorMessage") == null)
				{
					rowsFIeldQueryList = new ArrayList<String[]>();
					int i = 0;
					for (JsonNode col :cols)
					{
						String[] fieldArr  = new String[17];
						fieldArr[0] = col.get("fieldName").asText();
//						if ( col.get("isReadOnly") != null && col.get("isReadOnly").asBoolean())  // Query fields are always editable
//							fieldArr[1] = fieldArr[1]+"#CNoEDT#";
						if ( col.get("FieldNameInUI").asText().isEmpty())
							fieldArr[2] = "col"+i;	
						else
							fieldArr[2] = col.get("FieldNameInUI").asText();
						if ( col.get("idFieldType").asText().isEmpty() || col.get("idFieldType").asText().equals("null"))
							fieldArr[3] = "";
						else
							fieldArr[3] = col.get("idFieldType").asText();
						if ( col.get("PathToParentField").asText().isEmpty() || col.get("PathToParentField").asText().equals("null"))
							fieldArr[4] = "";
						else
							fieldArr[4] = col.get("PathToParentField").asText();
						if ( col.get("defaultValue").asText().isEmpty() || col.get("defaultValue").asText().equals("null"))
							fieldArr[5] = "";
						else
							fieldArr[5] = col.get("defaultValue").asText();
						if ( col.get("queryOrder").asText().isEmpty())
							fieldArr[6] = "";
						else if ( col.get("queryOrder").asText().contains("#")) // after the # becames the field label
							{
							String queryOrder =  col.get("queryOrder").asText();
							fieldArr[6] =queryOrder.substring(queryOrder.indexOf("#")+1);
							}
						else
							fieldArr[6] = "";
						if ( col.get("queryFieldWidth").asText().isEmpty() || col.get("queryFieldWidth").asText().equals("null") )/// for query different fields with width
							fieldArr[7] = AppConst.QUERY_FIELD_DEFAULT_SIZE;
						else
							fieldArr[7] = col.get("queryFieldWidth").asText();  
						if ( col.get("cssStyle").asText().isEmpty() || col.get("cssStyle").asText().equals("null") )
							fieldArr[8] = "";
						else
							fieldArr[8] = col.get("cssStyle").asText();
						if ( col.get("titleDisplay").asText().isEmpty() || col.get("titleDisplay").asText().equals("null") )
							fieldArr[9] = "";
						else
							fieldArr[9] = col.get("titleDisplay").asText();
						if ( col.get("titleQuery").asText().isEmpty() || col.get("titleQuery").asText().equals("null") )
							fieldArr[10] = "";
						else
							fieldArr[10] = col.get("titleQuery").asText();
						if ( col.get("titleGrid").asText().isEmpty() || col.get("titleGrid").asText().equals("null") )
							fieldArr[11] = "";
						else
							fieldArr[11] = col.get("titleGrid").asText();
						if ( col.get("cssStyleQueryField").asText().isEmpty() || col.get("cssStyleQueryField").asText().equals("null") )
							fieldArr[12] =AppConst.DEFAULT_CSS_STYLE_QRY_FIELD;
						else
							fieldArr[12] = col.get("cssStyleQueryField").asText();
						fieldArr[13] = ""; // is only used for Form fields
						fieldArr[14] = ""; // is only used for Form fields
						if ( col.get("maxColNumber").asText().isEmpty() || col.get("maxColNumber").asText().equals("null") )
							fieldArr[15] = AppConst.MAX_NUMBER_OF_FIELDS_PER_TABLE +"";
						else
							fieldArr[15] = col.get("maxColNumber").asText();
						rowsFIeldQueryList.add(fieldArr);
						fieldArr[16] = ""; // is only used for Form fields
						i++;
					}
					// **** As the getColumnsFromTable is not call the keepJoinConditionSubResources is call from here
					if (resourceName.startsWith("@")==false) // starts with @ it means system table that doesn't exist in @resources, in fact could be the @resources itself
						{
						int idxPoint = resourceName.indexOf(".");
						if (idxPoint > -1) 
						resourceName = resourceName.substring(0, idxPoint);
						String ident = JSonClient.getIdentOfResuorce(resourceName, true,preConfParam);
						if(ident != null)
							{
							JsonNode resource = JSonClient.get("@resources/"+ident,null,true,preConfParam);  
							JSonClient.keepJoinConditionSubResources(resource); 
							}
						}
						
				}
				
				else	
				{
					rowsFIeldQueryList = getColListFromTable(resourceName, preConfParam);
				}
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			}	
		return rowsFIeldQueryList;
	}
	public static void showError(String error) {
		Label content = new Label(error);
		NativeButton buttonInside = new NativeButton("Cerrar");
		Notification notification = new Notification(content, buttonInside);
//		notification.setDuration(3000);
		buttonInside.addClickListener(event -> notification.close());
		notification.setPosition(Position.MIDDLE);
		notification.open();
			
		}

}
