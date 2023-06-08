package org.vaadin.example;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.PasswordField;
import com.vaadin.flow.component.textfield.TextField;
import org.vaadin.example.Persona;
import org.w3c.dom.Text;

import java.net.URISyntaxException;
import java.util.ArrayList;

public class EditarUsuario extends VerticalLayout {
    DataService data = new DataService();
    public void paginaEditar(Persona persona){
        TextField Nombre = new TextField("Nombre Usuario");
        PasswordField passwordField = new PasswordField("Contraseña");

        Button salir = new Button("Salir");
        salir.addClickListener(event -> {
            removeAll();
            GridsView gridsView = new GridsView();
            try {
                gridsView.PaginaGridsView(persona);
            } catch (URISyntaxException e) {
                throw new RuntimeException(e);
            }
            this.add(gridsView);
        });

        Button confirmar = new Button("Confirmar");
        confirmar.addClickListener(event -> {
            removeAll();
            Persona personaNueva = persona;
            personaNueva.setNombre(Nombre.getValue());
            personaNueva.setPassword(passwordField.getValue());
            ArrayList<Persona> lista = new ArrayList<Persona>();
            lista.add(personaNueva);
            lista.add(persona);
            System.out.println(lista);
            data.editarUsuario(lista);
            GridsView gridsView = new GridsView();
            try {
                gridsView.PaginaGridsView(persona);
            } catch (URISyntaxException e) {
                throw new RuntimeException(e);
            }
            this.add(gridsView);
        });
        HorizontalLayout horizontalLayout = new HorizontalLayout();
        horizontalLayout.add(Nombre, passwordField);
        HorizontalLayout horizontalLayout1 = new HorizontalLayout();
        horizontalLayout1.add(confirmar, salir);
        this.add(horizontalLayout, horizontalLayout1);
    }
}
