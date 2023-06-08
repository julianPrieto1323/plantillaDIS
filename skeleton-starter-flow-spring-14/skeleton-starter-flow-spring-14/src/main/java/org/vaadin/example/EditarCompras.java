package org.vaadin.example;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;

import java.net.URISyntaxException;

public class EditarCompras extends VerticalLayout {
    DataService data = new DataService();
    public void editarComprasView(Persona persona, Compras compras){

        H1 h1 = new H1("Añada las características de su compra. ");
        TextField Nombre = new TextField("Nombre Compra");
        TextField datePicker = new TextField("Fecha de Compra");
        add(datePicker);
        Button confirmar = new Button("Confirmar");
        Button atras = new Button("Atrás");
        HorizontalLayout horizontalLayout1 = new HorizontalLayout();
        horizontalLayout1.add(Nombre, datePicker);
        HorizontalLayout horizontalLayout2 = new HorizontalLayout();
        horizontalLayout2.add(confirmar, atras);
        this.add(h1, horizontalLayout1, horizontalLayout2);

        confirmar.addClickListener(ButtonClickevent -> {
            compras.setNombre(Nombre.getValue());
            String fecha = datePicker.getValue();
            fecha = data.comprobarFecha(fecha);
            compras.setFechaCompra(fecha);
            data.editarCompras(compras.getId(), compras.getNombre(), compras.getFechaCompra(), persona.getId());
            removeAll();
            GridsView gridsView = new GridsView();
            try {
                gridsView.PaginaGridsView(persona);
            } catch (URISyntaxException e) {
                throw new RuntimeException(e);
            }
            this.add(gridsView);
        });

        atras.addClickListener(ButtonClickevent -> {
            try {
                removeAll();
                GridsView gridsView = new GridsView();
                gridsView.PaginaGridsView(persona);
                this.add(gridsView);
            } catch (URISyntaxException e) {
                throw new RuntimeException(e);
            }
        });
    }
}
