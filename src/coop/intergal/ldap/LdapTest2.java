package coop.intergal.ldap;
 
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
 
public class LdapTest2 {
 
    public void run() {
        try {
            DirContext context = getContext();
            String name = "uid=bobx,ou=people";
//            createLDAPObject(context, name);
//            createAttribute(context, name, "displayName", "JOBS");
            viewAttribute(context, name, "displayName");
            updateAttribute(context, name, "displayName", "STEVE");
            viewAttribute(context, name, "displayName");
    //        removeAttribute(context, name, "displayName");
    //        removeLDAPObject(context, name);
        } catch (NamingException e) {
            e.printStackTrace();
        }
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
        cn.add("Jobs");
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
 
    private DirContext getContext() throws NamingException {
        Properties properties = new Properties();
        properties.put(Context.INITIAL_CONTEXT_FACTORY,
                "com.sun.jndi.ldap.LdapCtxFactory");
        properties.put(Context.PROVIDER_URL, "ldap://intergal01.cloud.netimaging.net:389/dc=intergal,dc=coop");
        properties.put(Context.SECURITY_CREDENTIALS, "toorLDAP44");
        properties.put(Context.SECURITY_PRINCIPAL, "cn=admin,dc=intergal,dc=coop");
        return new InitialDirContext(properties);
    }
 
    public static void main(String[] args) {
        new LdapTest2().run();
    }
}