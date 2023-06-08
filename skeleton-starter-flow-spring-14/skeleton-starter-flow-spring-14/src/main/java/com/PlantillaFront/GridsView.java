package com.PlantillaFront;

import com.vaadin.flow.component.Text;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import org.atmosphere.interceptor.AtmosphereResourceStateRecovery;
import org.vaadin.example.Compras;
import org.vaadin.example.Persona;

import javax.swing.plaf.basic.BasicOptionPaneUI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

public class GridsView extends VerticalLayout {
    DataService data = new DataService();
    public void PaginaGridsView(Persona persona) throws URISyntaxException {
        H1 h1 = new H1("Mostrando Compras de " + persona.getNombre());

        Grid<Compras> grid = new Grid<>(Compras.class, false);
        grid.addColumn(Compras::getId).setHeader("Id");
        grid.addColumn(Compras::getNombre).setHeader("Nombre");
        grid.addColumn(Compras::getFechaCompra).setHeader("Fecha");
        ArrayList<Compras> listaCompras = data.getCompras(persona);
        if(listaCompras != null){
            grid.setItems(listaCompras);
        }


        Button eliminar = new Button("Borrar Usuario");
        eliminar.addClickListener( ButtonClickevent -> {
            removeAll();
            data.eliminarUsuario(persona);
            LoginView loginView = new LoginView();
            loginView.PaginaLoginWiew();
            this.add(loginView);
        });

        Button anhadirCompras = new Button("Añadir Compras");
        anhadirCompras.addClickListener( ButtonClickevent -> {
            removeAll();
            Anhadir_Compras anhadirCompras1 = new Anhadir_Compras();
            anhadirCompras1.Anhadir_ComprasView(persona);
            this.add(anhadirCompras1);
        });

        Button editarUsuario = new Button("Editar Usuario");
        editarUsuario.addClickListener( event -> {
            removeAll();
            EditarUsuario editarUsuario1 = new EditarUsuario();
            editarUsuario1.paginaEditar(persona);
            persona.setCompras(data.mostrarCompras(persona));
            this.add(editarUsuario1);
        });
        this.add(h1, grid, eliminar, anhadirCompras, editarUsuario);
    }
}
