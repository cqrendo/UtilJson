package coop.intergal.ui.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
//import org.springframework.ldap.core.support.LdapContextSource;
//import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
//import org.springframework.security.ldap.authentication.BindAuthenticator;
//import org.springframework.security.ldap.authentication.LdapAuthenticationProvider;
//import org.springframework.security.ldap.authentication.LdapAuthenticator;
//import org.springframework.security.ldap.authentication.ad.ActiveDirectoryLdapAuthenticationProvider;
//import org.springframework.security.ldap.ppolicy.PasswordPolicyAwareContextSource;
//import org.springframework.security.ldap.search.FilterBasedLdapUserSearch;
//import org.springframework.security.ldap.userdetails.UserDetailsContextMapper;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;


/**
 * Configures spring security, doing the following:
 * <li>Bypass security checks for static resources,</li>
 * <li>Restrict access to the application, allowing only logged in users,</li>
 * <li>Set up the login form,</li>
 * <li>Configures the {@link UserDetailsServiceImpl}.</li>
 */
@EnableWebSecurity
@Configuration
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

    private static final String LOGIN_PROCESSING_URL = "/login";
    private static final String LOGIN_FAILURE_URL = "/login?error";
    private static final String LOGIN_URL = "/login";
    private static final String LOGOUT_SUCCESS_URL = "/";
	@Value("${ldap.urls}")

	private String ldapUrls;

	@Value("${ldap.base.dn}")

	private String ldapBaseDn;

	@Value("${ldap.username}")

	private String ldapSecurityPrincipal;

	@Value("${ldap.password}")

	private String ldapPrincipalPassword;

	@Value("${ldap.user.dn.pattern}")

	private String ldapUserDnPattern;

	@Value("${ldap.enabled}")

	private String ldapEnabled;


//    private final UserDetailsService userDetailsService;

    @Autowired
    @Lazy
    private PasswordEncoder passwordEncoder;

//    @Autowired
//    public SecurityConfiguration(UserDetailsService userDetailsService) {
//        this.userDetailsService = userDetailsService;
//    }
//    @Bean
//    public AuthenticationProvider activeDirectoryLdapAuthenticationProvider() {
//        ActiveDirectoryLdapAuthenticationProvider provider = new ActiveDirectoryLdapAuthenticationProvider("", ldapUrls, ldapBaseDn);//, "cn=admin,dc=intergal,dc=coop");
//        provider.setConvertSubErrorCodesToExceptions(true);
//        provider.setUseAuthenticationRequestCredentials(true);
//        provider.setUserDetailsContextMapper(userDetailsContextMapper());
//        System.out.println("SecurityConfiguration.activeDirectoryLdapAuthenticationProvider() :"+ ldapBaseDn);
//        return provider;
//    }
//    @Bean
//    public AuthenticationProvider mYactiveDirectoryLdapAuthenticationProvider() {
//        LdapAuthenticationProvider provider = new LdapAuthenticationProvider(ldapAuthenticator());//, "cn=admin,dc=intergal,dc=coop");
//        provider.setUseAuthenticationRequestCredentials(true);
//        provider.setUserDetailsContextMapper(userDetailsContextMapper());
//        System.out.println("SecurityConfiguration.activeDirectoryLdapAuthenticationProvider() :"+ ldapBaseDn);
//        return provider;
//    }
//    @Bean
//    public LdapContextSource ldapContextSource() throws Exception {
//        PasswordPolicyAwareContextSource contextSource = new PasswordPolicyAwareContextSource("ldap://intergal01.cloud.netimaging.net:389");
//        contextSource.setUserDn("CN=admin,DC=intergal,DC=coop");
//        contextSource.setPassword("toorLDAP44");
//        return contextSource;
//    }
//    @Bean
//    public LdapAuthenticator ldapAuthenticator() {
//        BindAuthenticator authenticator;
//		try {
//			authenticator = new BindAuthenticator(ldapContextSource());
//		
//        authenticator.setUserSearch(new FilterBasedLdapUserSearch("cn=admin,dc=intergal,dc=coop", "uid={0}", ldapContextSource()));
//        return authenticator;
//		} catch (Exception e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//		return null;
//		
//    }
//    @Bean
//    public UserDetailsContextMapper userDetailsContextMapper() {
//         return new CustomUserMapper();
//    }
    /**
     * The password encoder to use when encrypting passwords.
     */
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
//	@Bean
//	@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
//	public CurrentUser currentUser(UserRepository userRepository) {
//		final String username = SecurityUtils.getUsername();
//		User user =
//			username != null ? userRepository.findByEmailIgnoreCase(username) :
//				null;
//		return () -> user;
//	}

    /**
     * Registers our UserDetailsService and the password encoder to be used on login attempts.
     */
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
    	System.out.println("SecurityConfiguration.configure()----------------------------");
    	if(Boolean.parseBoolean(ldapEnabled)){
    	auth.ldapAuthentication() // uid=juanjo,cn=Usuarios,ou=anpa terronio,ou=anpas,dc=intergal,dc=coop
	       .groupSearchBase("ou=groups")
	       .userSearchFilter("uid={0}")
	        .contextSource()
	        .url(ldapUrls + "/" +ldapBaseDn)
	        .managerDn(ldapSecurityPrincipal)
	        .managerPassword(ldapPrincipalPassword)
	        .and()
	        .passwordCompare()
	        .passwordEncoder(new BCryptPasswordEncoder())
	        .passwordAttribute("userPassword");
// //   	auth.userDetailsService(userDetailsService());
//    	
    	} else {
    		System.out.println("SecurityConfiguration.configure()---------------------------- .inMemoryAuthentication()");
    		auth
    		.inMemoryAuthentication()  // password = admin
    		.withUser("user").password("$2y$12$kKMEWgLzpj/Dfg7LzJVXSOAQlzAa3TMCa8XCwuFhP2YOPICnAUHKe").roles("USER")
    	.and()
    		.withUser("20user").password("$2y$12$kKMEWgLzpj/Dfg7LzJVXSOAQlzAa3TMCa8XCwuFhP2YOPICnAUHKe").roles("USER")//.roles("ADMIN");
        .and()
    		.withUser("24user").password("$2y$12$kKMEWgLzpj/Dfg7LzJVXSOAQlzAa3TMCa8XCwuFhP2YOPICnAUHKe").roles("USER");//.roles("ADMIN");

    	}
    }
    
    /**
     * Require login to access internal pages and configure login form.
     */
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        // Not using Spring CSRF here to be able to use plain HTML for the login page

    	http.csrf().disable()
                // Register our CustomRequestCache, that saves unauthorized access attempts, so
                // the user is redirected after login.
                .requestCache().requestCache(new CustomRequestCache()).and()

                // Restrict access to our application.
                .authorizeRequests()

                // Allow all flow internal requests.
                .requestMatchers(SecurityUtils::isFrameworkInternalRequest).permitAll()

                // Allow all requests by logged in users.
                .anyRequest().fullyAuthenticated()
                //.hasAnyAuthority( "guest", "admin" )

                // Configure the login page.
                .and().formLogin().loginPage(LOGIN_URL).permitAll().loginProcessingUrl(LOGIN_PROCESSING_URL)
                .failureUrl(LOGIN_FAILURE_URL)
                
    			// Register the success handler that redirects users to the page they last tried
				// to access
				.successHandler(new SavedRequestAwareAuthenticationSuccessHandler())


                // Configure logout
                .and().logout().logoutSuccessUrl(LOGOUT_SUCCESS_URL);
    }

    /**
     * Allows access to static resources, bypassing Spring security.
     */

    @Override
    public void configure(WebSecurity web) throws Exception {

        web.ignoring().antMatchers(
                // Vaadin Flow static resources
                "/VAADIN/**",

                // the standard favicon URI
                "/favicon.ico",

                // the robots exclusion standard
                "/robots.txt",

                // web application manifest
                "/manifest.webmanifest",
                "/sw.js",
                "/offline-page.html",

                // icons and images
                "/icons/**",
                "/images/**",

                // (development mode) static resources
                "/frontend/**",

                // (development mode) webjars
                "/webjars/**",

                // (development mode) H2 debugging console
                "/h2-console/**",

                // (production mode) static resources
                "/frontend-es5/**", "/frontend-es6/**");
    }
}

