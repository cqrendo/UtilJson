package coop.intergal.espresso.presutec.utils;

import com.fasterxml.jackson.databind.JsonNode;


public class ReportBean {
	
	String title ; 
	String fieldList;
	String destinationFieldList;
	String fieldLabels;
	String resource;
	String path;
	String filter;
	String titleFrontPage;
	String otherFilterForSubreport;
	
	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getFieldList() {
		return fieldList;
	}

	public void setFieldList(String fieldList) {
		this.fieldList = fieldList;
	}

	public String getDestinationFieldList() {
		return destinationFieldList;
	}

	public void setDestinationFieldList(String destinationFieldList) {
		this.destinationFieldList = destinationFieldList;
	}

	public String getFieldLabels() {
		return fieldLabels;
	}

	public void setFieldLabels(String fieldLabels) {
		this.fieldLabels = fieldLabels;
	}

	public String getResource() {
		return resource;
	}

	public void setResource(String resource) {
		this.resource = resource;
	}

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

	public String getFilter() {
		return filter;
	}

	public void setFilter(String filter) {
		this.filter = filter;
	}

	public String getTitleFrontPage() {
		return titleFrontPage;
	}

	public void setTitleFrontPage(String titleFrontPage) {
		this.titleFrontPage = titleFrontPage;
	}

	public String getOtherFilterForSubreport() {
		return otherFilterForSubreport;
	}

	public void setOtherFilterForSubreport(String otherFilterForSubreport) {
		this.otherFilterForSubreport = otherFilterForSubreport;
	}

	public ReportBean (int idReport, String preConfParam)
	{
		try {
			String filtro = "idReport="+idReport;
				JsonNode rowsReport = JSonClient.get("Report", filtro, false, preConfParam, "10"); // is use to pass as lTxsumary in the case this is empty for not changes
			if (rowsReport != null)
			{
				JsonNode rowReport = rowsReport.get(0);
				setTitle(rowReport.get("title").asText());
				setFieldList(rowReport.get("fieldList").asText());
				setDestinationFieldList(rowReport.get("destinationFieldList").asText());
				setFieldLabels(rowReport.get("fieldLabels").asText());
				setResource(rowReport.get("resource").asText());
				setPath(rowReport.get("path").asText());
				setFilter(rowReport.get("filter").asText());
				setTitleFrontPage(rowReport.get("titleFrontPage").asText());
				setOtherFilterForSubreport(rowReport.get("otherFilterForSubreport").asText());
			}
		
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
