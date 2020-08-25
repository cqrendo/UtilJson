package coop.intergal.ui.views.login;

import java.util.Locale;

import com.vaadin.flow.component.AttachEvent;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.login.LoginOverlay;
import com.vaadin.flow.component.orderedlayout.FlexLayout;
import com.vaadin.flow.router.AfterNavigationEvent;
import com.vaadin.flow.router.AfterNavigationObserver;
import com.vaadin.flow.router.Route;

import coop.intergal.AppConst;

@Route(value = "login")
public class LoginView extends FlexLayout implements AfterNavigationObserver {

    private final LoginOverlay login;

    public LoginView(){
		UI.getCurrent().setLocale(new Locale("es", "ES"));

        login = new LoginOverlay();

        login.setForgotPasswordButtonVisible(true);
        login.setAction("login");
        login.setTitle(AppConst.LOGIN_NAME);
        login.setDescription(AppConst.LOGIN_DESCRIPTION);

        add(login);
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

