package coop.intergal.ui.utils;

import com.fasterxml.jackson.databind.JsonNode;

import coop.intergal.vaadin.rest.utils.DynamicDBean;

public class ProcessParams {
	
	public static String componFilterFromParams(String filterForNavigation, DynamicDBean selectRow) {
		filterForNavigation = filterForNavigation.substring(12); // clean row.Subgrid @@ TODO adapt to other combinations
		int idxEnd = filterForNavigation.indexOf("=");
		String fieldForValue = filterForNavigation.substring(0, idxEnd);
		if (selectRow != null)
		{
			JsonNode rowJson = selectRow.getRowJSon();
			String valueKey = rowJson.get(fieldForValue).asText();
			int idxStart = filterForNavigation.indexOf("rowtarget.")+10;
			String targetKey = filterForNavigation.substring(idxStart);
			return targetKey+"='"+valueKey+"'";
		}
		return null;
	}


}
