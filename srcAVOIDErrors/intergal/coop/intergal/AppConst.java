package coop.intergal;

import java.util.Locale;





public class AppConst {   // @@ TODO this class for now is copy and represents the default values, for generic utils class as converters, if you want to have different per project you must consider this 

	public static final String PRE_CONF_PARAM = "monbus";//"GferJetty";
	public static final String PRE_CONF_PARAM_METADATA = "metadata";
	public static final Locale APP_LOCALE = new Locale("es","ES");

	public static final String PAGE_ROOT = "";
	public static final String PAGE_SUBMENU = "submenu";
	public static final String PAGE_STOREFRONT = "storefront";
	public static final String PAGE_STOREFRONT_EDIT = "storefront/edit";
	public static final String PAGE_DASHBOARD = "dashboard";
	public static final String PAGE_USERS = "users";
	public static final String PAGE_PRODUCTS = "products";
	public static final String PAGE_DYNAMIC = "dymanic";
	public static final String PAGE_DYNAMIC_TREE = "dymanicTree";
	public static final String PAGE_LOGOUT = "logout";
	public static final String PAGE_DEFAULT = PAGE_STOREFRONT;

	public static final String ICON_STOREFRONT = "edit";
	public static final String ICON_DASHBOARD = "clock";
	public static final String ICON_USERS = "user";
	public static final String ICON_PRODUCTS = "calendar";
	public static final String ICON_LOGOUT = "arrow-right";

	public static final String TITLE_STOREFRONT = "Storefront";
	public static final String TITLE_DASHBOARD = "Dashboard";
	public static final String TITLE_USERS = "Users";
	public static final String TITLE_PRODUCTS = "Products";
	public static final String TITLE_LOGOUT = "Logout";
	public static final String TITLE_NOT_FOUND = "Page was not found";
	public static final String TITLE_ACCESS_DENIED = "Access denied";
	public static final String TITLE_MENU_PRINCIPAL = "Menu principal";
	public static final String PACKAGE_VIEWS = "coop.intergal.metadata.ui.views.";
	

	public static final String[] ORDER_SORT_FIELDS = {"dueDate", "dueTime", "id"};
//	public static final Sort.Direction DEFAULT_SORT_DIRECTION = Sort.Direction.ASC;

	public static final String VIEWPORT = "width=device-width, minimum-scale=1, initial-scale=1, user-scalable=yes";
	public static final String DEFAULT_PICK_DIALOG_WITHD = "1024px";
	public static final String DEFAULT_PICK_DIALOG_HEIGHT = "800px";
	public static final String DEFAULT_API_NAME = "monbus";
	
	public static final String STYLES_CSS = "./styles/monbusstyle.css";
	public static final String STYLES_FORM_ITEM_CSS= "./styles/monbusstyle-form-item.css";
	public static final String QUERY_FIELD_DEFAULT_SIZE = "25#25";
	public static final String DEFAULT_CSS_STYLE_QRY_FIELD =  ".labelM";
	public static final int MAX_NUMBER_OF_FIELDS_PER_TABLE = 150;
	public static final String LOGIN_FORGETPASS_CONTENT = "Escribe tu usuario a continuación y te enviaremos un email con tu nueva contraseña.";
	public static final String LOGIN_FORGETPASS_ERROR = "Ha habido un error. Posiblemente ese usuario no existe. Revísalo.";  
	public static final String LOGIN_FORGETPASS_NOTEMAIL = " no es un email válido. Revísalo.";	
	public static final String EMAIL_REMITENTE = "ampafacil.com@gmail.com";
	public static final String EMAIL_CLAVE = "Susita123%";
	public static final String EMAIL_HOST = "smtp.gmail.com";
	public static final String EMAIL_PORT = "587";
	public static final String LDAP_BASE = ",ou=anpas,dc=cloud";
	public static final boolean DEBUG_GET_DATA_FROM_BACK_END = false;
	public static final String COUNT_USED_BY_DB = "count(*)";// depending on DB the result for count is different by example  "count(*)"; para mysql y COUNT(*) para Oracle // 


	// Mutable for testing.
	public static int NOTIFICATION_DURATION = 4000;


}
