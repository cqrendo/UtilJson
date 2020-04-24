package coop.intergal.ui.security.ldap;
 
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

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.web.bind.annotation.RestController;
 
//@Configuration
//@RestController
//@PropertySource("classpath:application.properties")

public class LdapConnection {
	@Value("${ldap.urls}")
	private String ldapUrls;

	@Value("${ldap.base.dn}")
	private String ldapBaseDn;

	public String getLdapBaseDn() {
		return ldapBaseDn;
	}


	@Value("${ldap.username}")
	private String ldapSecurityPrincipal;

	@Value("${ldap.password}")
	private String ldapPrincipalPassword;

	@Value("${ldap.user.dn.pattern}")
	private String ldapUserDnPattern;

	@Value("${ldap.enabled}")
	private String ldapEnabled;

	
//    public void run() {
//        try {
//            DirContext context = getContext();
//            String name = "uid=bobx,ou=people";
////            createLDAPObject(context, name);
////            createAttribute(context, name, "displayName", "JOBS");
//            viewAttribute(context, name, "displayName");
//            updateAttribute(context, name, "displayName", "STEVE");
//            viewAttribute(context, name, "displayName");
//    //        removeAttribute(context, name, "displayName");
//    //        removeLDAPObject(context, name);
//        } catch (NamingException e) {
//            e.printStackTrace();
//        }
//    }
 
    
    public DirContext getContext() throws NamingException {
//    	LdapConfig x = new LdapConfig() ;//= ldapConfig();
//    	String ldapUrl = env.getProperty("ldap.urls");
        Properties properties = new Properties();
        properties.put(Context.INITIAL_CONTEXT_FACTORY,
                "com.sun.jndi.ldap.LdapCtxFactory");
        properties.put(Context.PROVIDER_URL, ldapUrls + "/" +ldapBaseDn);// "ldap://intergal01.cloud.netimaging.net:389/dc=intergal,dc=coop"); // ldapUrls);
        properties.put(Context.SECURITY_CREDENTIALS,ldapPrincipalPassword);// "toorLDAP44"); // ,ldapPrincipalPassword);
        properties.put(Context.SECURITY_PRINCIPAL, ldapSecurityPrincipal);//"cn=admin,dc=intergal,dc=coop"); // ldapSecurityPrincipal);
        return new InitialDirContext(properties);
    }
 
}