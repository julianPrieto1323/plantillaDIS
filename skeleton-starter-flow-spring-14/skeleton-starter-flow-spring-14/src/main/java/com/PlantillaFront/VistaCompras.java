package com.PlantillaFront;

import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import org.vaadin.example.Compras;
import org.vaadin.example.Persona;

import java.net.URISyntaxException;
import java.util.ArrayList;

public class VistaCompras extends VerticalLayout {
    DataService data = new DataService();
    public void comprasView(Persona persona) throws URISyntaxException {
        Grid<Compras> grid = new Grid<>(Compras.class, false);
        grid.addColumn(Compras::getId).setHeader("Id");
        grid.addColumn(Compras::getNombre).setHeader("Nombre");
        grid.addColumn(Compras::getFechaCompra).setHeader("Fecha Compra");

        ArrayList<Compras> compras = data.getCompras(persona);
        grid.setItems(compras);
    }
}
