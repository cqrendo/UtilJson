package coop.intergal.ui.utils;

import java.util.Enumeration;
import java.util.Locale;
import java.util.ResourceBundle;

import com.fasterxml.jackson.databind.JsonNode;

import coop.intergal.espresso.presutec.utils.JSonClient;

public class TranslateResource {
	public static String getFieldLocale(String field, String preConfParam) {
		return getFieldLocale(field, preConfParam, null);
	}
	public static String getFieldLocale(String field, String preConfParam, String tableName) {
		String clave =field; 
		ResourceBundle labels = ResourceBundle.getBundle("ResourceBundle", new Locale("es", "ES"));
		Enumeration bundleKeys = labels.getKeys();
		try
		{
			clave = labels.getString(field);
		}
		catch (java.util.MissingResourceException e)
		{
			clave = getClaveFromFieldTemplate(field, preConfParam, tableName);
		}
	    return clave;
	}

	private static String getClaveFromFieldTemplate(String field, String preConfParam, String tableName) {
		try {
			String filtro = "fieldName='"+field +"'";
			if (tableName != null)
			{
				if (tableName.startsWith("CHAIN:")) // CHAIN is not the realname of the table, is to handle table in a way that follows the next form path
					tableName = tableName.substring(6);
				filtro = filtro + "%20AND%20tableName='"+tableName+"'";
			}
			JsonNode rowsFT = JSonClient.get("FieldTemplate", filtro, true, "metadata", "10"); // is use to pass as lTxsumary in the case this is empty for not changes
			if (rowsFT != null && rowsFT.get("errorMessage") == null)
			{
				if (rowsFT.size() > 0)
				{
					return rowsFT.get(0).get("question").asText();
				}
			}
		
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return field;
	}

}