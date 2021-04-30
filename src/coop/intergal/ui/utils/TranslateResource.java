package coop.intergal.ui.utils;

import java.util.Enumeration;
import java.util.Locale;
import java.util.ResourceBundle;

import com.fasterxml.jackson.databind.JsonNode;
import com.vaadin.flow.component.UI;

import coop.intergal.AppConst;
import coop.intergal.espresso.presutec.utils.JSonClient;

public class TranslateResource {
	public static String getFieldLocale(String field) {
		return getFieldLocale(field, null);
	}
	public static String getFieldLocale(String field, String tableName) {
		String clave =field;
		Locale locale = UI.getCurrent().getLocale();
		try
		{
		System.out.println("TranslateResource.getFieldLocale() " + locale.getCountry());
	    ResourceBundle labels = ResourceBundle.getBundle("ResourceBundle", locale);
//		ResourceBundle labels = ResourceBundle.getBundle("ResourceBundle", new Locale("es", "ES"));
//		Enumeration bundleKeys = labels.getKeys();
	    if (labels != null && field != null)
	    	clave = labels.getString(field);
		}
//		catch ( java.util.MissingResourceException)
		catch (java.util.MissingResourceException e)
		{
			try
			{
			ResourceBundle labels = ResourceBundle.getBundle("ResourceBundle", new Locale("es", "ES"));
			clave = labels.getString(field);
			}
			catch (java.util.MissingResourceException e2)
			{
				clave = getClaveFromFieldTemplate(field, tableName);	
			}
		}
	    return clave;
	}

	private static String getClaveFromFieldTemplate(String field, String tableName) {
		try {
			String filtro = "fieldName='"+field +"'";
			if (tableName != null)
			{
				if (tableName.startsWith("CHAIN:")) // CHAIN is not the realname of the table, is to handle table in a way that follows the next form path
					tableName = tableName.substring(6);
				filtro = filtro + "%20AND%20tableName='"+tableName+"'";
			}
			JsonNode rowsFT = JSonClient.get("FieldTemplate", filtro, true, AppConst.PRE_CONF_PARAM_METADATA, "10"); // is use to pass as lTxsumary in the case this is empty for not changes
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
