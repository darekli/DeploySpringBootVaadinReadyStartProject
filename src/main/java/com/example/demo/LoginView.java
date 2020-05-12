package com.example.demo;

import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.login.LoginForm;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.BeforeEnterEvent;
import com.vaadin.flow.router.BeforeEnterObserver;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;

import java.util.Collections;

@Route("login")
@PageTitle("login")
public class LoginView extends VerticalLayout implements BeforeEnterObserver {

    //public static final String ROUTE = "login";
    LoginForm login = new LoginForm();

    public LoginView() {
     addClassName("login-view");
     setSizeFull();

     setJustifyContentMode(JustifyContentMode.CENTER);
     setAlignItems(Alignment.CENTER);


    //login.setAction("login");
    login.setAction("testView");
     add(
             new H1("Vaadi2n"),
             login
     );
    }

    @Override
    public void beforeEnter(BeforeEnterEvent beforeEnterEvent) {
if(!beforeEnterEvent.getLocation()
    .getQueryParameters()
    .getParameters()
    .getOrDefault("error", Collections.emptyList())
    .isEmpty()){
    login.setError(true);
    }}
        private void authenticateNavigation(BeforeEnterEvent event) {
            if (!LoginView.class.equals(event.getNavigationTarget())
                    && !SecurityUtils.isUserLoggedIn()) {
                UI.getCurrent().getPage().reload();
            }
        }
    }

