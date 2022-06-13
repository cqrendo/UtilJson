package coop.intergal.ui.util;

import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;
import java.util.Collection;
import java.util.Iterator;
import java.util.StringTokenizer;
import java.util.regex.Pattern;

import javax.naming.NamingException;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.server.VaadinSession;

import coop.intergal.AppConst;
import coop.intergal.espresso.presutec.utils.JSonClient;
import coop.intergal.ui.security.SecurityUtils;
import coop.intergal.ui.security.ldap.LdapClient;
import coop.intergal.ui.views.DynamicQryGridDisplay;
import coop.intergal.vaadin.rest.utils.RestData;

public class UtilSessionData {
	
	public static boolean getCache() {
		Object cacheStr = VaadinSession.getCurrent().getAttribute("cache");
		boolean cache = true ;
		if (cacheStr != null && cacheStr.equals("false"))
			cache = false;
//		System.out.println("UtilSessionData.getCache() ->" +cache );
		return cache; 
	}
	public static void setCache(String cache) {
		VaadinSession.getCurrent().setAttribute("cache", cache);
		System.out.println("UtilSessionData.setCache() ->" + cache);
	}
	public static String getFormParams(String key) {
		String actualFormParams = (String) VaadinSession.getCurrent().getAttribute("formParams");
		if (actualFormParams == null)
			return null;
		if (key.equals("ALL"))
			return actualFormParams;
		if ( actualFormParams.indexOf(key) == -1)
			return null;
		int idxStart = actualFormParams.indexOf(key) + key.length()+1;
		int idxEnd =idxStart+actualFormParams.substring(idxStart).indexOf("#");
		return actualFormParams.substring(idxStart, idxEnd);
	}
	public static void setFormParams(String key, String formParams) { // format key=value# where key is className.varName
		String actualFormParams = getFormParams("ALL");
		if (actualFormParams != null && actualFormParams.indexOf(key) > -1) 
		{
			int idxStart = actualFormParams.indexOf(key) + key.length()+1;
			int idxEnd =idxStart+actualFormParams.substring(idxStart).indexOf("#");
			actualFormParams = actualFormParams.substring(0,idxStart) + formParams + actualFormParams.substring(idxEnd);			
		}
		else
		{
			if (actualFormParams!= null)
				actualFormParams = actualFormParams + key + "=" + formParams +"#";
			else
				actualFormParams = key + "=" + formParams +"#";
		}
		VaadinSession.getCurrent().setAttribute("formParams", actualFormParams);
		System.out.println("UtilSessionData.setformParams() ->" + actualFormParams);
	}
	public static String getCompanyYear() {
		Object companyYear = VaadinSession.getCurrent().getAttribute("companyYear");
		System.out.println("UtilSessionData.getCompanyYear() ->" +companyYear );
		if (companyYear == null)
			companyYear ="";
		return (String) companyYear; 
	}
	public static void setCompanyYear(String companyYear) {
		VaadinSession.getCurrent().setAttribute("companyYear", companyYear);
		System.out.println("UtilSessionData.setCompanyYear() ->" + companyYear);
	}
	public static String getCompany() {
		Object company = VaadinSession.getCurrent().getAttribute("company");
		System.out.println("UtilSessionData.getCompany() ->" +company );
		if (company == null)
			company ="";
		return (String) company; 
	}
	public static void setCompany(String company) {  // the selected company
		VaadinSession.getCurrent().setAttribute("company", company);
		System.out.println("UtilSessionData.setCompany() ->" + company);
	}
	public static String getCompanies() {
		Object companies = VaadinSession.getCurrent().getAttribute("companies");
		System.out.println("UtilSessionData.getCompanies() ->" +companies );
		if (companies == null)
			companies ="";
		return (String) companies; 
	}
	public static void setCompanies(String companies) { // a list of possible selected companies
		VaadinSession.getCurrent().setAttribute("companies", companies);
		System.out.println("UtilSessionData.setCompanies() ->" + companies);
	}


	public static boolean isVisibleOrEditableByTag(String tagsForVisibility) {
		if (tagsForVisibility == null || tagsForVisibility.isEmpty())
			return true;
	    StringTokenizer tokens = new StringTokenizer(tagsForVisibility,","); 
	      int i = 0;
	      while (tokens.hasMoreTokens())
	        {
	    	String tag = tokens.nextToken();
	    	int idxColon = tag.indexOf(":");
	    	if (idxColon == -1)
	    	{
	    		System.out.println("UtilSessionData.isVisibleOrEditableByTag() ERROR tag mal definido , user key:value, por ejemplo company:TYSH");
	    		return false; 
	    	}			
	    	String tagKey = tag.substring(0,idxColon);
	    	boolean trueOrFalse = true;
	    	if (tagKey.indexOf("!") != -1)
	    	{
	    		trueOrFalse = false;
	    		tagKey = tag.substring(0,idxColon-1);
	    	}
	    	String tagValue = tag.substring(idxColon+1);
	  	    Object keyValue = getKeyValue(tagKey);
	  	    if (tagKey.startsWith("ldap."))
	  	    {
	  	    	String [] typesTag = tagValue.split(Pattern.quote(","));
	  	    	return userHasTypes(typesTag); 
	  	    }			
	  	    if (keyValue!=null && keyValue.equals(tagValue))
	  	    	return trueOrFalse;
	  	    }
		return false;
	}
	public static String getKeyValue(String tagkey) {
		if (AppConst.TAGS_LIST.indexOf(tagkey) == -1)
			System.out.println("tag not in TAGS_LIST");
		if (tagkey.equals("user"))
			return SecurityUtils.getUsername();
		if (tagkey.startsWith("user."))
			return getValueFromTableUser(tagkey);
		if (tagkey.startsWith("ldap."))
			return getValueFromLdap(tagkey);
		return (String) VaadinSession.getCurrent().getAttribute(tagkey); // gets other attributtes fill in the session
	}
	private static String getValueFromLdap(String tagkey) {
		try {
			return LdapClient.getAttributeValue( tagkey.substring(tagkey.indexOf(".")+1));
		} catch (NamingException e) {
			e.printStackTrace();
		}
		return null;
	}
	public static String getValueFromTableUser(String tagkey, String appConst) {
		String field = tagkey.substring(tagkey.indexOf(".")+1);
		String filter = AppConst.USER_PK.replace("<<user>>", SecurityUtils.getUsername());
		return RestData.getDataValueFromAFieldOfAResource(AppConst.USER_TABLE, field, filter, appConst).asText();
	}
	public static String getValueFromTableUser(String tagkey) {
		return getValueFromTableUser (tagkey, UtilSessionData.getCompanyYear()+AppConst.PRE_CONF_PARAM);
	}
	public static String addCompanyToTitle(String optionName) {
			String title = optionName+" ("+UtilSessionData.getCompanyYear()+")";
//			try {
//				title = java.net.URLDecoder.decode(title, StandardCharsets.UTF_8.name());
//			} catch (UnsupportedEncodingException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			}
			return  title;
		}
	public static Component  getOpenUI(String idMenu) {
		
		Iterator<UI> uIs = VaadinSession.getCurrent().getUIs().iterator();
		while (uIs.hasNext()) {
			UI uI = uIs.next(); 
			Iterator<Component> components = uI.getChildren().iterator();
			while (components.hasNext()) {
				Component comp = components.next();
//				comp.isAttached();
				if (comp instanceof DynamicQryGridDisplay)
				{
					DynamicQryGridDisplay dQGD = (DynamicQryGridDisplay) comp;
//					isInBrowser = comp.isAttached();
					System.out.println("UtilSessionData. comp "+ dQGD.getPageTitle() );
					if (dQGD.getIdMenu() != null)
					{
						if (dQGD.getIdMenu().equals(idMenu)) 
							return dQGD;
					}
				}
				
			}
			
		}
		return null;
		
	}
	public static boolean userHasRole(String Role)
	{
		boolean hasRoles;
		try {
			hasRoles = LdapClient.isMemberOf( new String[] { Role });
			System.out.println("HAS ROLE ADMIN " + hasRoles);

		} catch (NamingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return false;
		
	}
	public static boolean userHasTypes(String[] types)
	{
		boolean hasRoles;
		hasRoles = LdapClient.userHasAnyOfThisTypes( types );
		System.out.println("HAS ROLE ADMIN " + hasRoles);
		
		return hasRoles;
		
	}
	public static boolean userIsMemberOfOU(String ou)
	{
		boolean belongsToOU = false;
		try {
			belongsToOU = LdapClient.isMemberOfOu(ou);
			System.out.println(" " + belongsToOU);

		} catch (NamingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return belongsToOU;
		
	}


}
