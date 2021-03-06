package coop.intergal.ui.security.ldap;
 
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.Properties;
import java.util.regex.Pattern;

import javax.naming.CompositeName;
import javax.naming.Context;
import javax.naming.Name;
import javax.naming.NameParser;
import javax.naming.NamingEnumeration;
import javax.naming.NamingException;
import javax.naming.directory.Attribute;
import javax.naming.directory.Attributes;
import javax.naming.directory.BasicAttribute;
import javax.naming.directory.BasicAttributes;
import javax.naming.directory.DirContext;
import javax.naming.directory.InitialDirContext;
import javax.naming.directory.ModificationItem;
import javax.naming.directory.SearchControls;
import javax.naming.directory.SearchResult;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.ldap.userdetails.LdapUserDetails;

import coop.intergal.espresso.presutec.utils.JSonClient;

 
@Configuration
@PropertySource("classpath:application.properties")

public class LdapClient {
	@Value("${ldap.urls}")
	private static String ldapUrls;

	@Value("${ldap.base.dn}")
	private static String ldapBaseDn;

	@Value("${ldap.username}")
	private static String ldapSecurityPrincipal;

	@Value("${ldap.password}")
	private static String ldapPrincipalPassword;

	private static String uidOu;

	private static LdapConnection ldapConnection;
	
	@Value("${ldap.user.dn.pattern}")
	private String ldapUserDnPattern;
	
	@Value("${ldap.enabled}")
	private String ldapEnabled;
	
	@Bean
	public LdapConnection getDBConnection() {
		LdapConnection ldapConnection = new LdapConnection();
		return ldapConnection;
	}

    private static final String MEMBER_OF = "memberOf";//"memberOf";displayName
    private static final String[] attrIdsToSearch = new String[] { MEMBER_OF, "displayName" };
    public static final String SEARCH_BY_SAM_ACCOUNT_NAME = "(uid=%s)";//"(sAMAccountName=%s)";
    public static final String SEARCH_GROUP_BY_GROUP_CN = "(&(objectCategory=group)(cn={0}))";

    private void removeLDAPObject(DirContext context, String name) throws NamingException {
        context.destroySubcontext(name);
    }
 
    public static void createLDAPUser(String name, String password, Hashtable<String, String> fieldsAndData, String snvalue, String cnvalue ) throws NamingException {
    	DirContext context = getContext();
        Attributes attributes = new BasicAttributes();
 
        Attribute attribute = new BasicAttribute("objectClass");
        attribute.add("inetOrgPerson");
        attribute.add("organizationalPerson");
        attribute.add("person");
        attribute.add("top");
        attributes.put(attribute);
 
//        Attribute oc = new BasicAttribute("objectClass");
//        oc.add("organizationalPerson");
//        attributes.put(oc);
// 
//        Attribute person = new BasicAttribute("objectClass");
//        person.add("person");
//        attributes.put(person);
        
//        Attribute calEntry = new BasicAttribute("objectClass");
//        calEntry.add("calEntry");
//        attributes.put(calEntry);

//        attribute = new BasicAttribute("objectClass");
//        attribute.add("top");
//        attributes.put(attribute);
       

        Attribute sn = new BasicAttribute("sn");
        sn.add(snvalue);
        attributes.put(sn);
 
        Attribute cn = new BasicAttribute("cn");
        cn.add(cnvalue);
        attributes.put(cn);
        
//        Attribute roles = new BasicAttribute("roles");
//        roles.add(rolesValue);
//        attributes.put(roles);
        
//       Attribute uid = new BasicAttribute("uid");
//       uid.add("bob2");
//       attributes.put(uid);
// 
 
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
 //       encoder.encode("1234");
        Attribute userPassword = new BasicAttribute("userPassword");
        userPassword.add(encoder.encode(password)); //"$2a$10$c6bSeWPhg06xB1lvmaWNNe4NROmZiSpYhlocU/98HNr2MhIOiSt36");
        attributes.put(userPassword);
        if ( fieldsAndData != null)
        {
        	Enumeration<String> fieldsAndDataELE = fieldsAndData.keys();
        	while (fieldsAndDataELE.hasMoreElements())
        	{
        		String field = fieldsAndDataELE.nextElement();
        		String data = fieldsAndData.get(field);
        		attributes.put(field,data );
        	}
        }	

//        Attribute dname = new BasicAttribute("texto");
//        dname.add(name);
//        attributes.put(dname);
 //      attributes.put("telephoneNumber", "1234");
        context.createSubcontext(name, attributes);
    }
    public static String changePassword(String name , String oldPass, String newPass, Boolean force, Boolean externoSN) throws NamingException, UnsupportedEncodingException {
    	DirContext context;
		if (externoSN == true) context = getContextExterno();
    	else context = getContext();
    	//
    	ModificationItem[] mods = new ModificationItem[1];
		BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
		Attributes attrs = context.getAttributes(name);
		byte[] v = (byte[]) attrs.get("userPassword").get();
		String saveOldPass = (String) new String(v);
 		Attribute oldUserPassword = new BasicAttribute("userPassword");
		boolean isSavedPass = encoder.matches(oldPass,saveOldPass);
		if (isSavedPass || force)
		{
			oldUserPassword.add(encoder.encode(newPass));
			mods[0] = new ModificationItem(DirContext.REPLACE_ATTRIBUTE, oldUserPassword);
			context.modifyAttributes(name, mods);
			return "OK";
		}
		else
		{
			return "WRONG OLD PASSWORD";
		}
  	//
    }
 
    private void removeAttribute(DirContext context, String name , String attrName) throws NamingException {
        Attribute attribute = new BasicAttribute(attrName);
        ModificationItem[] item = new ModificationItem[1];
        item[0] = new ModificationItem(DirContext.REMOVE_ATTRIBUTE, attribute);
        context.modifyAttributes(name, item);
    }

 
    private void createAttribute(DirContext context, String name , String attrName, Object attrValue) throws NamingException {
        Attribute attribute = new BasicAttribute(attrName, attrValue);
        ModificationItem[] item = new ModificationItem[1];
        item[0] = new ModificationItem(DirContext.ADD_ATTRIBUTE, attribute);
        context.modifyAttributes(name, item);
    }
 
    private void updateAttribute(DirContext context, String name , String attrName, Object attrValue) throws NamingException {
        Attribute attribute = new BasicAttribute(attrName, attrValue);
        ModificationItem[] item = new ModificationItem[1];
        item[0] = new ModificationItem(DirContext.REPLACE_ATTRIBUTE, attribute);
        context.modifyAttributes(name, item);
    }
 
    public static void viewAttribute( String attrName) throws NamingException {
    	DirContext context = getContext();
    	Attributes attrs = context.getAttributes(uidOu);
        if (attrs.get(attrName) == null)
        	System.out.println(attrName + " **** NO EXISTE **** ");
        else 	 
        	System.out.println(attrName + ":" + attrs.get(attrName).get());
    }
    public static String getAttributeValue( String attrName) throws NamingException {
    	DirContext context = getContext();
    	Attributes attrs = context.getAttributes(uidOu);
        if (attrs.get(attrName) == null)
        {
        	System.out.println(attrName + " **** NO EXISTE **** ");
        	return null;
        }
        else
        {
        	String value = (String) attrs.get(attrName).get();
        	System.out.println(attrName + ":" + value);
        	return value;
        }	
    }
//    public static void viewAttribute( String name , String attrName) throws NamingException {
//        Attributes attrs = getContext().getAttributes(name);
//        System.out.println(attrName + ":" + attrs.get(attrName).get());
//    }
    public static boolean isMemberOf(String[] roles) throws NamingException {

  //  	LdapClient.viewAttribute("displayName");
    	DirContext context = getContext();
    	ldapBaseDn = ldapConnection.getLdapBaseDn();
//    	DirContext context = getContext();
        String              filter  = String.format(SEARCH_BY_SAM_ACCOUNT_NAME, getUid(uidOu));
        SearchControls      constraints = new SearchControls();
        constraints.setSearchScope(SearchControls.SUBTREE_SCOPE);
        constraints.setReturningAttributes(attrIdsToSearch);
        NamingEnumeration results = context.search("", filter, constraints);


        if (results == null || !results.hasMore()) {
            System.out.println("No result found");
            return false;
        }
        // Get result for the first entry found
        SearchResult result = (SearchResult) results.next();
        result.getName();
        // Get the entry's distinguished name
        NameParser parser = context.getNameParser("");
        Name contextName = parser.parse(context.getNameInNamespace());
        Name baseName = parser.parse(ldapBaseDn);

        Name entryName = parser.parse(new CompositeName(result.getName())
                .get(0));

        // Get the entry's attributes
        Attributes attrs = result.getAttributes();
        Attribute attr = attrs.get(attrIdsToSearch[0]);
        
        if (attr != null)
        {
        NamingEnumeration<?> e = attr.getAll();
        System.out.println("Member of");
        while (e.hasMore()) {
            String value = (String) e.next();
            int i = 0;
            while (i < roles.length)
            {
            	if (value.indexOf("cn="+roles[i]) > -1)
            		return true;
            	i++;
            }
            System.out.println(value);
        	}
        }
        return false;
        }
    public static boolean isMemberOfOu(String ou) throws NamingException {

    	LdapClient.viewAttribute("cn");
    	
    	DirContext context = getContext();
    	ldapBaseDn = ldapConnection.getLdapBaseDn();
    	
        String              filter  = String.format(SEARCH_BY_SAM_ACCOUNT_NAME, getUid(uidOu));
        SearchControls      constraints = new SearchControls();
        constraints.setSearchScope(SearchControls.SUBTREE_SCOPE);
        constraints.setReturningAttributes(attrIdsToSearch);
        NamingEnumeration<?> results = context.search("", filter, constraints);


        if (results == null || !results.hasMore()) {
            System.out.println("No result found");
            return false;
        }
        // Get result for the first entry found
        SearchResult result = (SearchResult) results.next();
        String name = result.getName();
        if (name.indexOf("ou="+ou) != -1)
        	return true;
        else
        	return false;
    }  
     
    private static Object getUid(String uidOu) {
    	//uid=bobx,ou=people
    	int idxStart = uidOu.indexOf("uid=")+4;
    	int idxEnd = uidOu.indexOf(",");
    	return uidOu.substring(idxStart, idxEnd);
}

	public static DirContext getContext() throws NamingException {
		SecurityContext context = SecurityContextHolder.getContext();
		if (context.getAuthentication() != null){
			Object details = context.getAuthentication().getDetails();
			System.out.println("SecurityUtils.getUsername() details "+ details.toString());
//			Object autorities = ((LdapUserDetails) context.getAuthentication().getPrincipal()).getAuthorities();
			Object principal = ((LdapUserDetails) context.getAuthentication().getPrincipal());
			if (principal instanceof LdapUserDetails) {
				String dn = ((LdapUserDetails) principal).getDn();
				uidOu = getUidOu(dn); // "uid=bobx,ou=people"
			}
			else {
				
			}
		}
		AnnotationConfigApplicationContext anotContext = new AnnotationConfigApplicationContext();
		anotContext.scan("coop.intergal.ui.security");
		anotContext.refresh();
		System.out.println("Refreshing the spring context");
		ldapConnection = anotContext.getBean(LdapConnection.class);
		
		DirContext ldpaContex = null;	
		anotContext.close();
		try {
				ldpaContex = ldapConnection.getContext();
				
		} catch (NamingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
          return ldpaContex;
    }
    private static DirContext getContextExterno() throws NamingException {
        Properties properties = new Properties();
        cargaProperties();
        properties.put(Context.INITIAL_CONTEXT_FACTORY, "com.sun.jndi.ldap.LdapCtxFactory");
        properties.put(Context.PROVIDER_URL, ldapUrls);
        properties.put(Context.SECURITY_CREDENTIALS, ldapPrincipalPassword);
        properties.put(Context.SECURITY_PRINCIPAL, ldapSecurityPrincipal);
        return new InitialDirContext(properties);
    }

	private static void cargaProperties() {
         InputStream is = JSonClient.class.getResourceAsStream("/application.properties");
		 Properties prop = new Properties();
		 try {
			prop.load(is);
			ldapUrls = prop.getProperty("ldap.urls");
			ldapPrincipalPassword = prop.getProperty("ldap.password");
			ldapSecurityPrincipal = prop.getProperty("ldap.username");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	private static String getUidOu(String dn) {
		int idxEnd = dn.indexOf(",dc=");
		if (idxEnd > -1)
			return dn.substring(0,idxEnd );
		return dn;
	}

	public static boolean userHasAnyOfThisTypes(String[] typesToCheck) {
    	DirContext context;
		try {
			context = getContext();
	   	Attributes attrs = context.getAttributes(uidOu);
        if (attrs.get("employeeType") == null)
        	System.out.println("employeeType  **** NO EXISTE **** ");
        else 
        {
        	String employeeType = (String) attrs.get("employeeType").get();
        	String [] typesEmployee = employeeType.split(Pattern.quote(","));
        	System.out.println("employeeType :" + attrs.get("employeeType").get());
        	int i = 0;
        	while (typesToCheck.length > i)
        	{
        		int ii = 0;
    			while (typesEmployee.length > ii)
    			{ 
    				if (typesToCheck[i].equals(typesEmployee[ii]))
    					return true;
    				ii++;
    			}
        			
        		i++;
        	}
        }
		} catch (NamingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
 
   
		return false;
	}
 
//    public static void main(String[] args) {
//        new LdapClient().run();
//    }
}