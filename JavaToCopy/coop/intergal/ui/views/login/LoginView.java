package coop.intergal.ui.views.login;

import java.io.UnsupportedEncodingException;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.naming.NamingException;

import com.vaadin.flow.component.AttachEvent;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.html.NativeButton;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.login.LoginI18n;
import com.vaadin.flow.component.login.LoginOverlay;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.notification.Notification.Position;
import com.vaadin.flow.component.orderedlayout.FlexLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.AfterNavigationEvent;
import com.vaadin.flow.router.AfterNavigationObserver;
import com.vaadin.flow.router.Route;

import coop.intergal.AppConst;
import coop.intergal.ui.security.ldap.LdapClient;
import coop.intergal.ui.util.SendEmail;
import coop.intergal.ui.utils.TranslateResource;

@Route(value = "login")
public class LoginView extends FlexLayout implements AfterNavigationObserver {
	
    private final LoginOverlay login;
	String CARACTERES = "23456789abcdekrsxyz";
	private String usuario;
	private String psw;
	private TextField user;
	private String elUser;

    public LoginView(){
		UI.getCurrent().setLocale(new Locale("es", "ES"));

        login = new LoginOverlay();
        login.setI18n(translateSpanish());

        login.setForgotPasswordButtonVisible(true);
        login.addForgotPasswordListener(ev -> olvidePassword());
        login.setAction("login");
        login.setTitle(AppConst.LOGIN_NAME);
        login.setDescription(AppConst.LOGIN_DESCRIPTION);

        add(login);
    }

    private void olvidePassword() {
    	Span content = new Span(AppConst.LOGIN_FORGETPASS_CONTENT);
    	content.getStyle().set("font-size", "small");
    	user = new TextField();
    	user.setLabel("Tu email");
    	user.getStyle().set("width", "100%");
    	Button button = new Button("Enviar");
    	button.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
    	Button buttonCancel = new Button("Cancelar");
    	buttonCancel.addThemeVariants(ButtonVariant.LUMO_TERTIARY);
    	Notification notification = new Notification(content,user,button,buttonCancel);
    	button.addClickListener(event -> aceptar(notification));
    	buttonCancel.addClickListener(e -> notification.close());
    	notification.setPosition(Position.MIDDLE);
    	notification.open();
	}
 
    private void aceptar(Notification notification) {
    	if (!user.isEmpty()) {
    		elUser = user.getValue();
			if (compruebaEmail(elUser) == true) {
				usuario = "uid="+elUser+AppConst.LDAP_BASE;
				psw = getPassword(CARACTERES,5);
				try {
					String estado = LdapClient.changePassword(usuario, "", psw, true, true);
					if (estado == "OK") {
	//			    	Notification notification2 = new Notification("La contraseña se ha cambiado correctamente", 3000, Position.MIDDLE);
	//			    	notification2.open();
						notification.close();
						user.clear();
						enviarEmail();
					}
					else {
				    	Notification notification2 = new Notification(AppConst.LOGIN_FORGETPASS_ERROR, 8000, Position.MIDDLE);
				    	notification2.open();
						notification.close();
						user.clear();		
					}
				} catch (UnsupportedEncodingException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (NamingException e) {
			    	Notification notification2 = new Notification(AppConst.LOGIN_FORGETPASS_ERROR, 8000, Position.MIDDLE);
			    	notification2.open();
//					e.printStackTrace();
				}
			}
		}
	}
	private void enviarEmail() {
		    String destinatario = elUser; 
		    String asunto = "Nueva contraseña";
		    String cuerpo = "<p>Esta es tu nueva contraseña:</p><p style=\"font-size:16px;\">"+psw+"</p><p>Puedes cambiarla una vez que entres en la aplicación.</p>";	    
		    SendEmail.SendEmail(destinatario, asunto, cuerpo);
	    	Notification notification = new Notification("Te hemos enviado un email con tu nueva contraseña. Si no aparece en la carpeta de \"entrada\" revisa la de \"correo no deseado\" (spam)", 8000, Position.MIDDLE);
    		notification.open();
	}
	private boolean compruebaEmail(String email) {
		// Patrón para validar el email
		Pattern pattern = Pattern
				.compile("^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
					+ "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$");
		Matcher mather = pattern.matcher(email);

		if (mather.find() == true) {
//			System.out.println("El email ingresado es válido.");
			return true;
		} else {
//			System.out.println("El email ingresado es inválido.");
			NativeButton buttonInside = new NativeButton(TranslateResource.getFieldLocale("CERRAR"));
			Label content = new Label(elUser + AppConst.LOGIN_FORGETPASS_NOTEMAIL);
	    	content.getStyle().set("font-size", "small");
    		Notification notification = new Notification(content, buttonInside);
    		notification.setPosition(Position.MIDDLE);
    		notification.open();
    		buttonInside.addClickListener(event -> notification.close());
			return false;
		}
	}
	public String getPassword(String key, int length) {
		String pswd = "";
		for (int i = 0; i < length; i++) {
			pswd+=(key.charAt((int)(Math.random() * key.length())));
		}
		return pswd;
	}

	private LoginI18n translateSpanish() {
    	    final LoginI18n i18n = LoginI18n.createDefault();

    	    i18n.setHeader(new LoginI18n.Header());
    	    i18n.getForm().setUsername("Usuario");
    	    i18n.getForm().setTitle("Entra a tu cuenta");
    	    i18n.getForm().setSubmit("Entrar");
    	    i18n.getForm().setPassword("Contraseña");
    	    i18n.getForm().setForgotPassword("Olvidé mi contraseña");
    	    i18n.getErrorMessage().setTitle("Usuario o contraseña incorrectos.");
    	    i18n.getErrorMessage()
    	        .setMessage("Revisa tu usuario y contraseña y vuelve a intentarlo.");
    	    i18n.getHeader().setTitle(AppConst.LOGIN_NAME);
    	    i18n.getHeader().setDescription(AppConst.LOGIN_DESCRIPTION);
    	    i18n.setAdditionalInformation("");
    	    return i18n;	
    }

	@Override
    protected void onAttach(AttachEvent attachEvent) {
        super.onAttach(attachEvent);
        login.setOpened(true);
    }

    @Override
    public void afterNavigation(AfterNavigationEvent event) {
        login.setError(
                event.getLocation().getQueryParameters().getParameters().containsKey(
                        "error"));
    }
}

