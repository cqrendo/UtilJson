package coop.intergal.vaadin.rest.utils;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import com.fasterxml.jackson.databind.JsonNode;
import com.vaadin.flow.server.InputStreamFactory;
import com.vaadin.flow.server.StreamResource;
import com.vaadin.flow.server.StreamResourceWriter;
import com.vaadin.flow.server.communication.StreamResourceHandler;

import coop.intergal.espresso.presutec.utils.JSonClient;



public class RestData {


	public static List<DynamicDBean> getResourceData(int offset, int limit, String resourceName, String preConfParam, ArrayList<String[]> rowsColList, String filter, boolean cache, Boolean hasNewRow) {

		if (resourceName  == null ||  resourceName.trim().length() == 0)
		{
// yolaqr			throw new UserFriendlyDataException("NO hay tabla seleccionada, revise el link"); //"XesbentaLAC"
//			return null;
		}
		String pagesize = limit+""; 
		if (limit==0) // when is not using a dataprovoder with paging and pr-count of rows limit is cero; 
			pagesize="50";
		
		List<DynamicDBean> customerList = new ArrayList<DynamicDBean>();
		JsonNode rowsList = null;
		try { //TODO CACHE IS FALSE always , put as param, 
		//	String filtro = null;
			if (filter == null || filter.equals("null"))
				filter="";
			if (offset > 0)
				filter=filter+"&offset="+offset;
//			System.out.println("RestData.getResourceCustomer() before GET" + new Date());
			rowsList = JSonClient.get(resourceName,filter,false,preConfParam,pagesize+"");//"40"); // vamos a probar sin cache, para activarlo poner "cache" en vez de false
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
			DynamicDBean d = fillRowDefaultValues(rowsColList);
			d.setResourceName(resourceName);
			d.setPreConfParam(preConfParam);
			d.setFilter(filter); // for newRows fill data from filter normally parent data
			customerList.add(d);
		}	
		for (JsonNode eachRow : rowsList)  {
			String col1name = rowsColList.get(0)[0]; 
			if (eachRow.get(col1name) !=null) // when are more rows than a pagesize it comes a row with out data TODO handle this page
			{
				DynamicDBean d = fillRow(eachRow, rowsColList, preConfParam);//, cols.get(0)); 
				d.setResourceName(resourceName);
				d.setPreConfParam(preConfParam);
				d.setFilter(filter);
				customerList.add(d);
			}
		}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}// preConfParam, null);//globalVars.getPagesize());
		System.out.println("RestData.getResourceCustomer() after FILL LIST "+ resourceName + " Filter:" +filter + " " + new Date());
		return customerList;
	}

	private static DynamicDBean fillRowDefaultValues(ArrayList<String[]> rowsColList) {
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
		for(Field field : fields )  
		{
//			field.setInt(eachRow.get("code_customer").asInt());
			try {
				if (field.getName().equals("col"+i) && i < rowsColList.size())
					{
					field.setAccessible(true);
					if (rowsColList.get(i) !=null)
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
			if (i>rowsColList.size()) 
				break;
		}
//		c.setCodeCustomer(eachRow.get("code_customer").asInt());
//		c.setNameCustomer(eachRow.get("name_customer").asText());
		

		return dB;
	}

	private static DynamicDBean fillRow(JsonNode eachRow, ArrayList<String[]> rowsColList, String preConfParam) {// JsonNode cols) {
//		Class dynamicDBeanClass = Class.forName("coop.intergal.xespropan.production.samples.backend.data.DynamicDBean");
		
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
		for(Field field : fields )  
		{
//			field.setInt(eachRow.get("code_customer").asInt());
			try {
				if (field.getName().equals("col"+i) && i < rowsColList.size())
					{
					field.setAccessible(true);
					if (rowsColList.get(i) !=null)
						{
						String colName = getColName(rowsColList,i);
						if (eachRow.get(colName)==null)
							field.set(dB, null);
						else
						{
							if (isNotABinary(eachRow.get(colName)))
							{
								String value = eachRow.get(colName).asText();
								if (value.equals("null"))
									value= "";
								field.set(dB, value);
							}
							else
							{
								
								JsonNode binaryData= eachRow.get(colName);
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
			if (i>rowsColList.size()) 
				break;
		}
//		c.setCodeCustomer(eachRow.get("code_customer").asInt());
//		c.setNameCustomer(eachRow.get("name_customer").asText());
		

		return dB;
	}
	private static void keepStreaminDb(DynamicDBean dB, String url, String preConfParam) {
		try {
			dB.setInputStream(new ByteArrayInputStream(JSonClient.get(url, preConfParam).binaryValue()));
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

	private static String getColName(ArrayList<String[]> rowsColList, int i) { // normally the col.. is syncronice with i secuence, but is rowColList have some fields not in natural position then must be search the name in other way
		String colNameInCL = rowsColList.get(i)[2];
		if ( colNameInCL.equals("col"+i) || colNameInCL.isEmpty() ) // if colinIU = col... then return colName 
			return rowsColList.get(i)[0];
		else // otherwise it searches
		{
			System.out.println("RestData.getColName() ----> Fields witn col... not in order "+ "col"+i );
			for (String[] row : rowsColList) // search for col.. to get his column name
			{
				if (row[2].equals("col"+i))
					return row[0];
			}
				
			return "null";
		}
	}
	private static String getDefaultValue(ArrayList<String[]> rowsColList, int i) {
		String colNameInCL = rowsColList.get(i)[2];
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
			rowsList = JSonClient.get("Count_"+resourceName,filter,cache,preConfParam,"1"); 
			count = rowsList.get(0).get("count(*)").asInt();

		
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}// preConfParam, null);//globalVars.getPagesize());
//		System.out.println("RestData.getResourceCustomer() after FILL LIST" + new Date());
		if (hasNewRow)
			count = count+1;
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
			rowsColList =  getRowsColList(rowsColList, resourceName, preConfParam);//, "");
			for (JsonNode eachRow : rowsList)  {
				if (eachRow.get(rowsColList.get(0)[0]) !=null)
				{
					DynamicDBean d = fillRow(eachRow, rowsColList, preConfParam);//, cols.get(0)); 
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
	public static ArrayList<String[]> getRowsColList(ArrayList<String[]> rowsColList, String resourceName, String preConfParam){//, String variant) { // variant is use to have different lists of fields in the same resource
			if (rowsColList == null || rowsColList.isEmpty())
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
					System.out.println("RestData.getRowsColList()  tablename to search = "+tableNameToSearch );
					cols = JSonClient.get("FieldTemplate","tableName='"+tableNameToSearch+"'&order=colOrder", true, preConfParam);
					if (cols != null && cols.size() > 0 && cols.get("errorMessage") == null)
					{
						rowsColList = new ArrayList<String[]>();
						int i = 0;
						for (JsonNode col :cols)
						{
							String[] fieldArr  = new String[6];
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
						
							JsonNode resource = JSonClient.get("@resources/"+ident,null,true,preConfParam);  
							JSonClient.keepJoinConditionSubResources(resource); 
							}
					}
					
					else	
					{
						cols = JSonClient.getColumnsFromTable(resourceName, null, true, preConfParam);
						
						rowsColList = new ArrayList<String[]>();
						Iterator<String> fN = cols.get(0).fieldNames();
						int i = 0;
						while (fN.hasNext()) {
							String[] fieldArr  = new String[6];
							String fieldName = fN.next();
							fieldArr[0] =fieldName;
							
							fieldArr[1] = "#SIG#";							
							String type = cols.get(0).get(fieldName).asText();		
							fieldArr[2] = "col"+i;								
							fieldArr[3] = "";
							fieldArr[4] = ""; // @@ TODO get FK data
							fieldArr[5] = "";
							if (type.equals("Date"))
								fieldArr[3] = "1";
							rowsColList.add(fieldArr);
							i++;
						}
					}
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
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

}
