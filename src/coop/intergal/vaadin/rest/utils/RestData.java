package coop.intergal.vaadin.rest.utils;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import com.fasterxml.jackson.databind.JsonNode;

import coop.intergal.espresso.presutec.utils.JSonClient;



public class RestData {


	public static List<DynamicDBean> getResourceData(int offset, int limit, String resourceName, String preConfParam, ArrayList<String[]> rowsColList, String filter, boolean cache) {

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

		for (JsonNode eachRow : rowsList)  {
			if (eachRow.get(rowsColList.get(0)[0]) !=null) // when are more rows than a pagesize it comes a row with out data TODO handle this page
			{
				DynamicDBean d = fillRowDaily(eachRow, rowsColList);//, cols.get(0)); 
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
		System.out.println("RestData.getResourceCustomer() after FILL LIST "+ resourceName + " " + new Date());
		return customerList;
	}

	private static DynamicDBean fillRowDaily(JsonNode eachRow, ArrayList<String[]> rowsColList) {// JsonNode cols) {
//		Class dynamicDBeanClass = Class.forName("coop.intergal.xespropan.production.samples.backend.data.DynamicDBean");
		
		DynamicDBean dB = new DynamicDBean();
		dB.setRowJSon(eachRow);
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
						if (eachRow.get(rowsColList.get(i)[0]) == null)
							field.set(dB, null);
						else
						{
							String value = eachRow.get(rowsColList.get(i)[0]).asText();
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

	public static void refresh(DynamicDBean dDb) {
		getResourceData(0,0,dDb.getResourceName(), dDb.getPreConfParam(), dDb.getRowsColList(), dDb.getFilter(), true);
		
	}
	public static int  getCountRows(String resourceName, String preConfParam, String filter, boolean cache) {

		if (resourceName  == null ||  resourceName.trim().length() == 0)
		{
// yolaqr			throw new UserFriendlyDataException("NO hay tabla seleccionada, revise el link"); //"XesbentaLAC"
//			return null;
		}

		int count = 0;
		List<DynamicDBean> customerList = new ArrayList<DynamicDBean>();
		JsonNode rowsList = null;
		try { //TODO CACHE IS FALSE always , put as param
		//	String filtro = null;
			rowsList = JSonClient.get("Count_"+resourceName,filter,cache,preConfParam,"1"); // more then 120 it seems a problem with grid, that repeats rows 
			count = rowsList.get(0).get("count(*)").asInt();

		
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}// preConfParam, null);//globalVars.getPagesize());
//		System.out.println("RestData.getResourceCustomer() after FILL LIST" + new Date());
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
			rowsColList =  getRowsColList(rowsColList, resourceName, preConfParam, "");
			for (JsonNode eachRow : rowsList)  {
				if (eachRow.get(rowsColList.get(0)[0]) !=null)
				{
					DynamicDBean d = fillRowDaily(eachRow, rowsColList);//, cols.get(0)); 
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
	public static ArrayList<String[]> getRowsColList(ArrayList<String[]> rowsColList, String resourceName, String preConfParam, String variant) {
			if (rowsColList == null || rowsColList.isEmpty())
				{
				JsonNode cols;
				try {				
					String genericResourceName = resourceName;
					int indx__ = genericResourceName.indexOf("__"); // -- indicates variations over same resource, or same means same field list
					if (indx__ > 1)
						genericResourceName = resourceName.substring(0, indx__);
					cols = JSonClient.get("FieldTemplate","tableName='"+genericResourceName+variant+"'&order=colOrder,idFieldTemplate", true, preConfParam);
					if (cols != null && cols.size() > 0 && cols.get("errorMessage") == null)
					{
						rowsColList = new ArrayList<String[]>();
						for (JsonNode col :cols)
						{
							String[] fieldArr  = new String[4];
							fieldArr[0] = col.get("fieldName").asText();
							if ( col.get("showInGrid").asBoolean())
								fieldArr[1] = "#SIG#";
							else
								fieldArr[1] = "";
							if ( col.get("FieldNameInUI").asText().isEmpty())
								fieldArr[2] = "";
							else
								fieldArr[2] = col.get("FieldNameInUI").asText();
							if ( col.get("idFieldType").asText().isEmpty() || col.get("idFieldType").asText().equals("null"))
								fieldArr[3] = "";
							else
								fieldArr[3] = col.get("idFieldType").asText();
							rowsColList.add(fieldArr);
						}
						// **** As the getColumnsFromTable is not call the keepJoinConditionSubResources is call from here
						int idxPoint = resourceName.indexOf(".");
						if (idxPoint > -1) 
							resourceName = resourceName.substring(0, idxPoint);
						String ident = JSonClient.getIdentOfResuorce(resourceName, true,preConfParam);
						
						JsonNode resource = JSonClient.get("@resources/"+ident,null,true,preConfParam);  
						JSonClient.keepJoinConditionSubResources(resource); 

					}
					
					else	
					{
						cols = JSonClient.getColumnsFromTable(resourceName, null, true, preConfParam);
						
						rowsColList = new ArrayList<String[]>();
						Iterator<String> fN = cols.get(0).fieldNames();
						while (fN.hasNext()) {
							String[] fieldArr  = new String[4];
							String fieldName = fN.next();
							fieldArr[0] =fieldName;
							
							fieldArr[1] = "#SIG#";							
							String type = cols.get(0).get(fieldName).asText();		
							fieldArr[2] = "";								
							fieldArr[3] = "";
							if (type.equals("Date"))
								fieldArr[3] = "1";
							rowsColList.add(fieldArr);
						}
					}
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				}	
			return rowsColList;
		}

}
