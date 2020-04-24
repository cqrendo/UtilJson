package coop.intergal;

import javax.naming.NamingException;
import javax.naming.directory.DirContext;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.web.servlet.error.ErrorMvcAutoConfiguration;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import coop.intergal.ui.security.SecurityConfiguration;
import coop.intergal.ui.security.ldap.LdapConnection;

/**
 * The entry point of the Spring Boot application.
 */
@SpringBootApplication(scanBasePackageClasses = { SecurityConfiguration.class, Application.class}, exclude = ErrorMvcAutoConfiguration.class)
public class Application extends SpringBootServletInitializer {

    public static void main(String[] args) {
//		AnnotationConfigApplicationContext anotContext = new AnnotationConfigApplicationContext();
////		anotContext.scan("coop.intergal");
////		anotContext.refresh();
//		System.out.println("Refreshing the spring context");
//		LdapConnection ldapConnection = anotContext.getBean(LdapConnection.class);
//		try {
//			DirContext ldpaContex = ldapConnection.getContext();
//		} catch (NamingException e1) {
//			// TODO Auto-generated catch block
//			e1.printStackTrace();
//		}

        SpringApplication.run(Application.class, args);
    }

    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {
        return builder.sources(Application.class);
    }
}
/// **** TAL COMO ESTABA EN BAKERY ******
//@SpringBootApplication(scanBasePackageClasses = { SecurityConfiguration.class, MainView.class, Application.class,
//		UserService.class }, exclude = ErrorMvcAutoConfiguration.class)
//@EnableJpaRepositories(basePackageClasses = { UserRepository.class })
//@EntityScan(basePackageClasses = { User.class })
//public class Application extends SpringBootServletInitializer {
//
//	public static void main(String[] args) {
//		SpringApplication.run(Application.class, args);
//	}
//
//	@Override
//	protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
//		return application.sources(Application.class);
//	}
//}
