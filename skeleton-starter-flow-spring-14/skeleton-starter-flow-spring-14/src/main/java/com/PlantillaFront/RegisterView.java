package com.PlantillaFront;


import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.PasswordField;
import com.vaadin.flow.component.textfield.TextField;
import org.vaadin.example.Persona;

import java.net.URISyntaxException;
import java.util.ArrayList;

public class RegisterView extends VerticalLayout {
    public void paginaRegister() {
        TextField Nombre = new TextField("First name");
        PasswordField password = new PasswordField("Password");

        PasswordField confirmPassword = new PasswordField("Confirm password");

        Button registrate = new Button("Registrate");
        Button atras = new Button("Atrás");
        atras.addThemeVariants(ButtonVariant.LUMO_PRIMARY, ButtonVariant.LUMO_ERROR);
        HorizontalLayout h1 = new HorizontalLayout();
        HorizontalLayout h2 = new HorizontalLayout();
        HorizontalLayout h3 = new HorizontalLayout();

        h1.add(Nombre);
        h2.add(password, confirmPassword);
        h3.add(registrate, atras);
        this.add(h1, h2, h3);

        registrate.addClickListener(buttonClickEvent -> {
            DataService dataService = new DataService();
            ArrayList<Persona> listadoUsuarios = new ArrayList<Persona>();
            try {
                listadoUsuarios = dataService.mostrarUsers();
            } catch (URISyntaxException e) {
                throw new RuntimeException(e);
            }
            Persona persona = new Persona();
            persona.setNombre(Nombre.getValue());
            persona.setPassword(password.getValue());
            dataService.RegistrarPersona(persona, listadoUsuarios);
            removeAll();
            LoginView loginView = new LoginView();
            loginView.PaginaLoginWiew();
            this.add(loginView);
        });

        atras.addClickListener(buttonClickEvent -> {
            removeAll();
            LoginView loginView = new LoginView();
            loginView.PaginaLoginWiew();
            this.add(loginView);
        });
    }
}
