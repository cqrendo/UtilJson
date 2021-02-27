package coop.intergal.ui.utils;

import com.vaadin.flow.server.VaadinSession;

public class UtilSessionData {
	
	public static boolean getCache() {
		Object cacheStr = VaadinSession.getCurrent().getAttribute("cache");
		boolean cache = true ;
		if (cacheStr != null && cacheStr.equals("false"))
			cache = false;
		System.out.println("UtilSessionData.getCache() ->" +cache );
		return cache; 
	}
	public static void setCache(String cache) {
		VaadinSession.getCurrent().setAttribute("cache", cache);
		System.out.println("UtilSessionData.setCache() ->" + cache);
	}
}
