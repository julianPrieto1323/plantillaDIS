package org.vaadin.example;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.datepicker.DatePicker;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.PasswordField;
import com.vaadin.flow.component.textfield.TextField;
import org.springframework.util.StringValueResolver;
import org.vaadin.example.Compras;
import org.vaadin.example.Persona;

import java.net.URISyntaxException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Date;
import java.util.Locale;

public class Anhadir_Compras extends VerticalLayout {
    DataService data = new DataService();
    public void Anhadir_ComprasView(Persona persona){
        Compras compras = new Compras();

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
            compras.setFechaCompra(datePicker.getValue());
            if(persona.getCompras() != null){
                compras.setId(String.valueOf(persona.getCompras().size() + 1));
            }else{
                compras.setId(String.valueOf(0));
            }
            data.anhadirCompras(compras, persona);
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
