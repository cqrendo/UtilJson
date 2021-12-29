package coop.intergal.ldap;
 
import java.io.UnsupportedEncodingException;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.Properties;

import javax.naming.Context;
import javax.naming.NamingException;
import javax.naming.directory.Attribute;
import javax.naming.directory.Attributes;
import javax.naming.directory.BasicAttribute;
import javax.naming.directory.BasicAttributes;
import javax.naming.directory.DirContext;
import javax.naming.directory.InitialDirContext;
import javax.naming.directory.ModificationItem;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
 
public class LdapTest2 {
 
    public void run() {
        try {
            DirContext context = getContext();
            String name = "cn=terra3,dc=intergal,dc=coop";
 //           createLDAPObject(context, name);
            createLDAPUser(name, "password", null, "snvalue", "cnvalue" );
//            createAttribute(context, name, "displayName", "JOBS");
 //           System.out.println("result "+changePassword(name , "admin2", "admin3"));
//            viewAttribute(context, name, "displayName");
//            updateAttribute(context, name, "displayName", "STEVE");
//            viewAttribute(context, name, "displayName");
    //        removeAttribute(context, name, "displayName");
    //        removeLDAPObject(context, name);
        } catch (NamingException e) {
            e.printStackTrace();
            
//        } catch (UnsupportedEncodingException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//
          }
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
        
//       Attribute uid = new BasicAttribute("uid");
//       uid.add("bob2");
//       attributes.put(uid);
// 
 
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
 //       encoder.encode("1234");
        Attribute userPassword = new BasicAttribute("userPassword");
        userPassword.add(encoder.encode(password));//"$2a$10$c6bSeWPhg06xB1lvmaWNNe4NROmZiSpYhlocU/98HNr2MhIOiSt36");
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
    public static String changePassword(String name , String oldPass, String newPass) throws NamingException, UnsupportedEncodingException {
    	DirContext context = getContext();
    	//
    	ModificationItem[] mods = new ModificationItem[1];
		BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
		Attributes attrs = context.getAttributes(name);
		byte[] v = (byte[]) attrs.get("userPassword").get();
		String saveOldPass = (String) new String(v);
		// Test the new password
         // Test the bind - if this fails, an exception is thrown
 		Attribute oldUserPassword = new BasicAttribute("userPassword");
 //		String encodeOldPass = encoder.encode(oldPass);
		boolean isSavedPass = encoder.matches(oldPass,saveOldPass);
//		BasicAttribute pon = new BasicAttribute("userPassword", ("\"" + oldPass + "\"").getBytes("UTF-16LE"));
		if (isSavedPass)
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
    private void removeLDAPObject(DirContext context, String name) throws NamingException {
        context.destroySubcontext(name);
    }
 
    private void createLDAPObject(DirContext context, String name) throws NamingException {
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
        sn.add("Steve2");
        attributes.put(sn);
 
        Attribute cn = new BasicAttribute("cn");
        cn.add("terra");
        attributes.put(cn);
        
//       Attribute uid = new BasicAttribute("uid");
//       uid.add("bob2");
//       attributes.put(uid);
// 
 
        Attribute userPassword = new BasicAttribute("userPassword");
        userPassword.add("$2a$10$c6bSeWPhg06xB1lvmaWNNe4NROmZiSpYhlocU/98HNr2MhIOiSt36");
        attributes.put(userPassword);
 

//        Attribute dname = new BasicAttribute("texto");
//        dname.add(name);
//        attributes.put(dname);
       attributes.put("telephoneNumber", "1234");
        context.createSubcontext(name, attributes);
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
 
    private void viewAttribute(DirContext context, String name , String attrName) throws NamingException {
        Attributes attrs = context.getAttributes(name);
        System.out.println(attrName + ":" + attrs.get(attrName).get());
    }
 
//    private static DirContext getContext() throws NamingException {
//        Properties properties = new Properties();
//        properties.put(Context.INITIAL_CONTEXT_FACTORY,
//                "com.sun.jndi.ldap.LdapCtxFactory");
//        properties.put(Context.PROVIDER_URL, "ldap://intergal01.cloud.netimaging.net:389/dc=intergal,dc=coop");
//        properties.put(Context.SECURITY_CREDENTIALS, "toorLDAP44");
//        properties.put(Context.SECURITY_PRINCIPAL, "cn=admin,dc=intergal,dc=coop");
//        return new InitialDirContext(properties);
//    }
    private static DirContext getContext() throws NamingException {
        Properties properties = new Properties();
        properties.put(Context.INITIAL_CONTEXT_FACTORY,
                "com.sun.jndi.ldap.LdapCtxFactory");
        properties.put(Context.PROVIDER_URL, "ldap://lac1.intergal.coop:389");
        properties.put(Context.SECURITY_CREDENTIALS, "intergalldapadmin");
        properties.put(Context.SECURITY_PRINCIPAL, "cn=admin,dc=intergal,dc=coop");
        return new InitialDirContext(properties);
    }
 
    public static void main(String[] args) {
        new LdapTest2().run();
    }
}