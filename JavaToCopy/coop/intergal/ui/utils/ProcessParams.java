package coop.intergal.ui.utils;

import com.fasterxml.jackson.databind.JsonNode;

import coop.intergal.vaadin.rest.utils.DynamicDBean;

public class ProcessParams {
	
	public static String componFilterFromParams(String componFilter, String filterForNavigation, DynamicDBean selectRow) {
		filterForNavigation = filterForNavigation.substring(12); // clean row.Subgrid @@ TODO adapt to other combinations
		int idxEnd = filterForNavigation.indexOf("=");
		String fieldForValue = filterForNavigation.substring(0, idxEnd);
		if (selectRow != null)
		{
			JsonNode rowJson = selectRow.getRowJSon();
			String valueKey = rowJson.get(fieldForValue).asText();
			int idxStart = filterForNavigation.indexOf("rowtarget.")+10;
			String targetKey = filterForNavigation.substring(idxStart);
			int idxSemicolon = filterForNavigation.indexOf(";");
			if ( idxSemicolon > -1 ) {
				targetKey = filterForNavigation.substring(idxStart,idxSemicolon );
				if (componFilter.length()> 0) 
					componFilter = componFilter + "%20AND%20"+targetKey+"='"+valueKey+"'";
				else
					componFilter = targetKey+"='"+valueKey+"'";
				componFilterFromParams(componFilter, filterForNavigation.substring(idxSemicolon+1), selectRow);
			}
			if (componFilter.length()> 0) 
				return componFilter + "%20AND%20"+targetKey+"='"+valueKey+"'";
			return targetKey+"='"+valueKey+"'";
		}
		return null;
	}


}
