package org.vaadin.intergal.validation;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.regex.Pattern;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;

import org.vaadin.intergal.validation.ValidationMetadata;

import com.fasterxml.jackson.databind.JsonNode;

import coop.intergal.AppConst;
import coop.intergal.espresso.presutec.utils.JSonClient;
import coop.intergal.vaadin.rest.utils.DynamicDBean;
import coop.intergal.vaadin.rest.utils.RestData;


/**
 * Restricciones utilizadas con ValidationServiceImpl
 *
 * @author Javier
 *
 */
public class Constraints {
	static Hashtable<String, String> fieldNAmeAndFieldinUI = new Hashtable<String, String>();
	private static final String RESOURCE_FIELD_TEMPLATE = "CR-FormTemplate.List-FieldTemplate";

	private static String formatMessage(ValidationMetadata<?> metadata, String message) {
		String fieldName = metadata.getProperty("name");
		if (fieldName == null) {
			return message;
		} else {
			return fieldName + " " + message;
		}
	}

	public static String positive(Integer value, ValidationMetadata<?> metadata) {
		return greaterThan(value, metadata, "0");
	}

	public static String greaterThan(Integer value, ValidationMetadata<?> metadata, String arg) {
		boolean req = false;
		int separatorPos = arg.indexOf(";");
		if (separatorPos > -1)
		{
			String reqStr = arg.substring(separatorPos+1);	
			arg = arg.substring(0,separatorPos );
			if (reqStr.equals("true"))
				req = true;
		}
		if ( req)
		{
			String result = isRequired(value, metadata);
			if (result != null)
				return result;
		}
		int limit = Integer.parseInt(arg);
		if (value == null || value > limit) {
			return null;
		} else {
			return formatMessage(metadata, "debe ser mayor a " + limit);
		}
	}

	public static String lessThan(Integer value, ValidationMetadata<?> metadata, String arg) {
//		boolean req = false;
//		int separatorPos = arg.indexOf(";");
//		if (separatorPos > -1)
//		{
//			String reqStr = arg.substring(separatorPos+1);	
//			arg = arg.substring(0,separatorPos );
//			if (reqStr.equals("true"))
//				req = true;
//		}
//		if ( req)
//		{
//			String result = isRequired(value, metadata);
//			if (result != null)
//				return result;
//		}	
		Long limit = new Long (arg);
		if (value == null || value < limit) {
			return null;
		} else {
			return formatMessage(metadata, "debe ser menor a " + limit);
		}
	}
	public static String lessThan(String valueStr, ValidationMetadata<?> metadata, String arg) {
//		boolean req = false;
//		int separatorPos = arg.indexOf(";");
//		if (separatorPos > -1)
//		{
//			String reqStr = arg.substring(separatorPos+1);	
//			arg = arg.substring(0,separatorPos );
//			if (reqStr.equals("true"))
//				req = true;
//		}
		valueStr = valueStr.replace(",",".");
		Double  value = new Double (valueStr);
//		if ( req)
//		{
//			String result = isRequired(valueStr, metadata);
//			if (result != null)
//				return result;
//		}	
		Double limit = new Double (arg);
		
		if (value == null || value > limit) {
			return null;
		} else {
			return formatMessage(metadata, "debe ser menor a " + limit);
		}
	}


	public static String minLength(String value, ValidationMetadata<?> metadata, String arg) {
		int length = Integer.parseInt(arg);
		if (value == null || value.length() >= length) {
			return null;
		} else {
			return formatMessage(metadata, "debe contener al menos " + length + " caracteres");
		}
	}
	public static String isRequired(String value, ValidationMetadata<?> metadata) {
	//	int length = Integer.parseInt(arg);
	//	System.out.println("Got (String) " + value);
		if (value == null || value.length() >= 1) {
			return null;
		} else {
			return formatMessage(metadata, "Requerido");
		}
	}
	public static String isRequired(Double value, ValidationMetadata<?> metadata) {
	//	int length = Integer.parseInt(arg);
		if (value == null || value >= 0) {
			return null;
		} else {
			return formatMessage(metadata, "Requerido");
		}
	}
	public static String isRequired(BigDecimal value, ValidationMetadata<?> metadata) {
	//	int length = Integer.parseInt(arg);
		if (value == null) 
			return formatMessage(metadata, "Requerido");
		if (value.doubleValue() > 0) 
			return null;
		else {
			return formatMessage(metadata, "Requerido");
		}
	}
	public static String isRequired(Integer value, ValidationMetadata<?> metadata) {
	//	int length = Integer.parseInt(arg);
	//	if (value == null || value >= 0) {
	//	 System.out.println("Got (a) " + value);
		if (value == null) {
			return formatMessage(metadata, "Requerido");
		} else if (value >= 0) {
			return null;
		} else {
			return formatMessage(metadata, "Requerido");
		}
	}


	public static String notInFuture(LocalDate value, ValidationMetadata<?> metadata) {
		if (value == null || value.compareTo(LocalDate.now()) <= 0) {
			return null;
		} else {
			return formatMessage(metadata, "no debe ser posterior a la fecha actual");
		}
	}

	public static String compareDates(DynamicDBean bean, ValidationMetadata<?> metadata, String startDateCol, String endDateCol) {
		//Por simplicidad se ignora el argumento
		//El validador debería extraer los valores de interés a partir del parámetro delconstraint
		if (bean.getColDate(startDateCol) ==null || bean.getColDate(endDateCol)==null || bean.getColDate(startDateCol).compareTo(bean.getColDate(endDateCol)) < 0) {
			return null;
		} else {
			return "La fecha de inicio debe ser anterior a la fecha de finalizacion";
		}
	}
	public static String validateOneCol(DynamicDBean bean, ValidationMetadata<?> metadata, String colName) {
		//Por simplicidad se ignora el argumento
		//El validador debería extraer los valores de interés a partir del parámetro delconstraint
		if (bean.getCol(colName).equals("1111") ) {
			return null;
		} else {
			return "La fecha de inicio debe ser anterior a la fecha de finalizacion";
		}
	}
	public static String validateFromBackEnd(DynamicDBean bean, ValidationMetadata<?> metadata, String validationNameANDcache) {
		//Por simplicidad se ignora el argumento
		//El validador debería extraer los valores de interés a partir del parámetro delconstraint
		Boolean cache = true;
		if (validationNameANDcache.substring(validationNameANDcache.indexOf(",")).equals(",false"))
				cache = false;			
		String validationName = validationNameANDcache.substring(0, validationNameANDcache.indexOf(","));
		String Result = validateFromBackEnd(bean,validationName, cache);
		if (Result.equals("OK") ) {
			return null;
		} else {
			return Result;
		}
	}
	public static String warningFromBackEnd(DynamicDBean bean, ValidationMetadata<?> metadata, String validationNameANDcache) {
		//Por simplicidad se ignora el argumento
		//El validador debería extraer los valores de interés a partir del parámetro delconstraint
		Boolean cache = true;
		if (validationNameANDcache.substring(validationNameANDcache.indexOf(",")).equals(",false"))
				cache = false;			
		String validationName = validationNameANDcache.substring(0, validationNameANDcache.indexOf(","));
		String Result = warningFromBackEnd(bean,validationName, cache);
		if (Result.equals("OK") ) {
			return null;
		} else {
			return Result;
		}
	}

	private static String validateFromBackEnd(DynamicDBean bean, String validationName, Boolean cache) {
		ScriptEngineManager manager = new ScriptEngineManager();
		ScriptEngine engine = manager.getEngineByName("JavaScript");
		String resourceName  = bean.getResourceName();
		String[] jSStringAndErrorTest = getJSFromBackEnd(resourceName,validationName, cache);
		if (jSStringAndErrorTest == null)
			return "ERROR en Validación, \"Nombre Validación BR\" ("+validationName+") no existe para la tabla ("+getTableNameFromResourceName(resourceName)+") correspondiente";
		String jSStringToProcess = createUsableJS(jSStringAndErrorTest[0], validationName, resourceName);
		String callJS = "valida("+getValuesFromBean(bean,resourceName, validationName)+")";
		String resultStr = "No validado";
		try {
			engine.eval(jSStringToProcess) ;
			Boolean result = (Boolean) engine.eval(callJS);
			if (result)
				resultStr="OK";
			else
				resultStr=jSStringAndErrorTest[1];
		} catch (ScriptException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return "ERROR en Validación, \"Nombre Validación BR\" ("+validationName+") ->"+e.getMessage();
		} // prints
		return resultStr;
	}
	private static String warningFromBackEnd(DynamicDBean bean, String validationName, Boolean cache) {
		ScriptEngineManager manager = new ScriptEngineManager();
		ScriptEngine engine = manager.getEngineByName("JavaScript");
		String resourceName  = bean.getResourceName();
		String[] jSStringAndErrorTest = getJSFromBackEnd(resourceName,validationName, cache);
		if (jSStringAndErrorTest == null)
			return "ERROR en Validación, \"Nombre Validación BR\" ("+validationName+") no existe para la tabla ("+getTableNameFromResourceName(resourceName)+") correspondiente";
		String jSStringToProcess = createUsableJS(jSStringAndErrorTest[0], validationName, resourceName);
		String callJS = "valida("+getValuesFromBean(bean,resourceName, validationName)+")";
		String resultStr = "No validado";
		try {
			engine.eval(jSStringToProcess) ;
			Boolean result = (Boolean) engine.eval(callJS);
			if (result)
				resultStr="OK";
			else
				resultStr="WARNING"+jSStringAndErrorTest[1];
		} catch (ScriptException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return "ERROR en Validación, \"Nombre Validación BR\" ("+validationName+") ->"+e.getMessage();
		} // prints
		return resultStr;
	}
	private static String getValuesFromBean(DynamicDBean bean, String resourceName, String validationName) {
		String [] tokens = validationName.split(Pattern.quote("_"));
		int i = 1;
		String values = "'";
		while (tokens.length > i)
		{ 
			String field = tokens[i];
			String fieldNameInUI = fieldNAmeAndFieldinUI.get(resourceName+"#"+field);
			String value = bean.getCol(fieldNameInUI);
			values = values + value + "','";
			i ++;
		}
		return values.substring(0, values.length()-2); // excludes last ",'"
	}

	private static String[] getJSFromBackEnd(String resourceName, String validationName, boolean cache) {
		try {
			String filter="\"name\"='"+validationName+"' AND \"entity_name\"='main:"+getTableNameFromResourceName(resourceName)+"'";
//			String q = "random word £500 bank $";
			String filterEncode = URLEncoder.encode(filter, StandardCharsets.UTF_8.toString());
			JsonNode rowRules = JSonClient.get("LACAdmin:rules",filterEncode,cache,AppConst.PRE_CONF_PARAM_METADATA,1+"");
			for (JsonNode eachRow : rowRules)  {
				String[] s = new String[]{eachRow.get("rule_text1").asText(), eachRow.get("rule_text2").asText()};
				return s;
			}
			
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
//		String[] s = new String[]{"if (row.ALQUILADO==\"S\" || row.ALQUILADO==\"N\" || !row.ALQUILADO || row.ALQUILADO==\" \")\r\n" + 
//				"    return true;\r\n" + 
//				"else\r\n" + 
//				"    return false;","ALQ. sólo admite los valores S, N o blanco" };
		return null;
	}

	private static String getTableNameFromResourceName(String resourceName) {
		if (resourceName.startsWith("CR-")) 
		{
			int idxEnd = resourceName.indexOf("__");
			String tableName = resourceName.substring(3);
			if (idxEnd > -1)
				tableName = resourceName.substring(3, idxEnd);
			return tableName;
		}
		if (resourceName.startsWith("List-")) 
		{
			int idxEnd = resourceName.indexOf("__");
			String tableName = resourceName.substring(5);
			if (idxEnd > -1)
				tableName = resourceName.substring(5, idxEnd);
			return tableName;
		}
		return resourceName;
	}

	private static String createUsableJS(String javascriptString, String fieldNames, String resourceName) {
		String [] tokens = fieldNames.split(Pattern.quote("_"));
		int i = 1;
		String functionDef = "function valida(";
		while (tokens.length > i)
		{ 
			String field = tokens[i];
			fillHTable(resourceName,field);
			javascriptString = javascriptString.replaceAll("row."+ field, "f"+i);
			functionDef = functionDef + "f"+i+",";
			i ++;
		}
		functionDef = functionDef.substring(0, functionDef.length()-1) + "){";
		return functionDef + javascriptString + "}";
	}
	private static void fillHTable(String resourceName, String field) {
		if (fieldNAmeAndFieldinUI.get(resourceName+"#"+field) == null)
		{
			fieldNAmeAndFieldinUI.put(resourceName+"#"+field, getColNameinUI(resourceName,field));
		}
		
	}

	private static String getColNameinUIOLD(String resourceName, String field) {
		String filter =  "tableName='"+resourceName+"'%20AND%20fieldName='"+field+"'";
//		ArrayList<String[]> rowsColList = new ArrayList<String[]>();
//		String[] fieldArr  = new String[1];
		
		DynamicDBean dynamicDBean = RestData.getOneRow(RESOURCE_FIELD_TEMPLATE,filter, AppConst.PRE_CONF_PARAM_METADATA, null);
		if (dynamicDBean.getCol19() != null)
			return dynamicDBean.getCol19();
		else
			return "colNOTFOUND";
	}
	private static String getColNameinUI(String resourceName, String field) {
		try {
			String filter =  "tableName='"+resourceName+"' AND fieldName='"+field+"'";
//			String q = "random word £500 bank $";
			String filterEncode = URLEncoder.encode(filter, StandardCharsets.UTF_8.toString());
			JsonNode rowFT = JSonClient.get("main:FieldTemplate",filterEncode,true,AppConst.PRE_CONF_PARAM_METADATA,1+""); // normally cache is optional nut in this case , is very unlikely that this data NmaeinUI is changed
			for (JsonNode eachRow : rowFT)  {
				return eachRow.get("FieldNameInUI").asText();
			}
			
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
//		String[] s = new String[]{"if (row.ALQUILADO==\"S\" || row.ALQUILADO==\"N\" || !row.ALQUILADO || row.ALQUILADO==\" \")\r\n" + 
//				"    return true;\r\n" + 
//				"else\r\n" + 
//				"    return false;","ALQ. sólo admite los valores S, N o blanco" };
		return "colNOTFOUND";
	}


	public static String valueInList(String value, ValidationMetadata<?> metadata, String arg) {
		if (value == null || Arrays.asList(arg.split(",")).contains(value)) {
			return null;
		} else {
			return "El valor no se encuentra en la lista";
		}
	}

}
