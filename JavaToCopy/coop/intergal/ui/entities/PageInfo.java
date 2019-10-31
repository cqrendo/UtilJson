package coop.intergal.ui.entities;

public class PageInfo {
	private final String link;
	private final String icon;
	private final String title;
	private final String paramsList;

	public PageInfo(String link, String icon, String title, String paramsList) {
		this.link = link;
		this.icon = icon;
		this.title = title;
		this.paramsList = paramsList;
	}

	public String getLink() {
		return link;
	}

	public String getIcon() {
		return icon;
	}

	public String getTitle() {
		return title;
	}
	
	public String getParamsList() {
		return paramsList;
	}

}
