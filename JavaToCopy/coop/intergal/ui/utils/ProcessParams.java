package coop.intergal.ui.utils;

import com.fasterxml.jackson.databind.JsonNode;

import coop.intergal.vaadin.rest.utils.DataService;
import coop.intergal.vaadin.rest.utils.DynamicDBean;

public class ProcessParams {
	
	public static String componFilterFromParams(String filterForNavigation, DynamicDBean selectRow) {
		if ( filterForNavigation.indexOf("row.subgrid")  > -1) {
			filterForNavigation = filterForNavigation.substring(12);  // clean row.Subgrid
		}
		else if (filterForNavigation.indexOf("row.") > -1) {
			filterForNavigation = filterForNavigation.substring(4);
		}
		int idxEnd = filterForNavigation.indexOf("=");
		String fieldForValue = filterForNavigation.substring(0, idxEnd);
		if (selectRow != null)
		{
			JsonNode rowJson = selectRow.getRowJSon();
			String valueKey = rowJson.get(fieldForValue).asText();
			int idxStart = filterForNavigation.indexOf("rowtarget.")+10;
			String targetKey = filterForNavigation.substring(idxStart);
			int idxSemicolon = filterForNavigation.indexOf(";");
			if ( idxSemicolon > -1 ) 
				{
				targetKey = filterForNavigation.substring(idxStart,idxSemicolon );
				String componFilter = targetKey+"='"+valueKey+"'";
				String nextFilter = filterForNavigation.substring(idxSemicolon+1);
				if (nextFilter.length() > 0) // check for more than one filter
				{
					componFilter = componEachFilter(nextFilter, rowJson, componFilter);
					idxSemicolon = nextFilter.indexOf(";");
					if (idxSemicolon > -1) // check for more than 2 filter
					{
						nextFilter = nextFilter.substring(idxSemicolon+1);
						componFilter = componEachFilter(nextFilter, rowJson, componFilter);
						idxSemicolon = nextFilter.indexOf(";");
						if (idxSemicolon > -1) // check for more than 3 filter
						{
							nextFilter = nextFilter.substring(idxSemicolon+1);
							componFilter = componEachFilter(nextFilter, rowJson, componFilter);
							idxSemicolon = nextFilter.indexOf(";");
							if (idxSemicolon > -1) // check for more than 4 filter
							{
								nextFilter = nextFilter.substring(idxSemicolon+1);
								componFilter = componEachFilter(nextFilter, rowJson, componFilter);
								idxSemicolon = nextFilter.indexOf(";");
								if (idxSemicolon > -1) // check for more than 5 filter
								{
									nextFilter = nextFilter.substring(idxSemicolon+1);
									idxSemicolon = nextFilter.indexOf(";");
									if (idxSemicolon > -1) // check for more than 6 filter
									{	
										nextFilter = nextFilter.substring(idxSemicolon+1);
										componFilter = componEachFilter(nextFilter, rowJson, componFilter);
										idxSemicolon = nextFilter.indexOf(";");
										if (idxSemicolon > -1) // check for more than 7 filter
										{
//											nextFilter = nextFilter.substring(idxSemicolon+1);
//											idxSemicolon = nextFilter.indexOf(";");
//											if (idxSemicolon > -1)
//											{
												DataService.get().showError("Demasiados campos en el filtro, no procesados");
//											}									
										}
									}
								}
							}
						}
					}
				}	
//				if (nextFilter.length() > 0) // add last filterForNavigation
//				{
//					componFilter = componEachFilter(nextFilter, rowJson, componFilter);
//				}
				return componFilter;
			}
			else
				return targetKey+"='"+valueKey+"'";
		}
		return null;
	}

	private static String componEachFilter(String nextFilter, JsonNode rowJson, String componFilter) {
		if (nextFilter.length() == 0) {
			return componFilter;
		}
		if ( nextFilter.indexOf("row.subgrid")  > -1) {
			nextFilter = nextFilter.substring(12);  // clean row.Subgrid
		}
		else if (nextFilter.indexOf("row.") > -1) {
			nextFilter = nextFilter.substring(4);
		}
		int idxEnd = nextFilter.indexOf("=");// clean row.Subgrid @@ TODO adapt to other combinations
		String fieldForValue = nextFilter.substring(0, idxEnd);
		Object valueKey = rowJson.get(fieldForValue).asText();
		int idxStart = nextFilter.indexOf("rowtarget.")+10;
		String targetKey = nextFilter.substring(idxStart);
		int idxSemicolon = nextFilter.indexOf(";");
		if ( idxSemicolon > -1 ) {
			targetKey = nextFilter.substring(idxStart,idxSemicolon );
		}
		componFilter = componFilter + "%20AND%20"+targetKey+"='"+valueKey+"'";
		return componFilter;
	}


}
