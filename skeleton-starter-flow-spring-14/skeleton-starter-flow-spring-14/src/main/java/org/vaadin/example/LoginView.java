package org.vaadin.example;


import com.vaadin.flow.component.Text;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.html.Image;
import com.vaadin.flow.component.login.LoginForm;
import com.vaadin.flow.component.login.LoginI18n;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.radiobutton.RadioButtonGroup;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.StreamResource;
import com.vaadin.flow.theme.Theme;
import com.vaadin.flow.theme.lumo.Lumo;
import org.vaadin.example.Persona;

import java.io.IOException;
import java.net.URISyntaxException;

import static com.helger.commons.string.StringHelper.removeAll;
import static org.apache.commons.lang3.ArrayUtils.add;

public class LoginView extends VerticalLayout{
    public void PaginaLoginWiew() {
        LoginI18n i18n = LoginI18n.createDefault();

        LoginI18n.Form i18nForm = i18n.getForm();
        i18nForm.setTitle("Inicio de Sesión");
        i18nForm.setUsername("Nombre");
        i18nForm.setPassword("Contraseña");
        i18nForm.setSubmit("Iniciar Sesión");
        i18nForm.setForgotPassword("¿No estás registrado?");
        i18n.setForm(i18nForm);

        LoginI18n.ErrorMessage i18nErrorMessage = i18n.getErrorMessage();
        i18nErrorMessage.setTitle("ERROR!");
        i18nErrorMessage.setMessage("ERROR, usuario no encontrado");
        i18n.setErrorMessage(i18nErrorMessage);

        LoginForm loginForm = new LoginForm();
        loginForm.setI18n(i18n);

        this.add(loginForm);

        loginForm.addLoginListener(event -> {
            String nombre = event.getUsername();
            String password = event.getPassword();
            //Debemos realizar un control y ver si has seleccionado algún radiobutton, si no salta un mensaje de que debo seleccionar
            //Un tipo de usuario para continuar
            try {//Validamos el Log in si se rellenan los campos y se selecciona un tipo de usuario
                Persona persona = isvalid(nombre, password);
                if (persona.getNombre() == null) {
                    Notification.show("El email o la contraseña son incorrectos.");
                    loginForm.setError(true);
                    i18nForm.setUsername("");
                    i18nForm.setPassword("");
                }else{
                    persona.setNombre(nombre);
                    persona.setPassword(password);
                    removeAll();
                    Notification.show("Inicio de sesión correcto.");
                    GridsView grids = new GridsView();
                    grids.PaginaGridsView(persona);
                    this.add(grids);
                }
            } catch (IOException e) {
                throw new RuntimeException(e);
            } catch (URISyntaxException e) {
                throw new RuntimeException(e);
            }
        });
        loginForm.addForgotPasswordListener(event-> {
            removeAll();
            RegisterView registerView = new RegisterView();
            registerView.paginaRegister();
            this.add(registerView);
        });
    }

    public Persona isvalid(String nombre, String password) throws IOException, URISyntaxException {
        boolean result = false;
        DataService data = new DataService();
        Persona persona = new Persona();
        Persona personaAux = new Persona();
        personaAux.setNombre(nombre);
        personaAux.setPassword(password);
        persona = data.comprobarInicio(personaAux);
        if (persona == null) {
            result = false;
        } else {
            persona.setNombre(nombre);
            persona.setPassword(password);
            result = true;
            removeAll();
            GridsView paginaGrids = new GridsView();
            try {
                paginaGrids.PaginaGridsView(persona);
            } catch (URISyntaxException e) {
                throw new RuntimeException(e);
            }
        }
        return persona;
    }
}
