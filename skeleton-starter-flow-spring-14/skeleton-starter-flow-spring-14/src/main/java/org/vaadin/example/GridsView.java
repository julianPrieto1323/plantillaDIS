package org.vaadin.example;

import com.vaadin.flow.component.Text;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.contextmenu.ContextMenu;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.notification.NotificationVariant;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import org.atmosphere.interceptor.AtmosphereResourceStateRecovery;
import org.vaadin.example.Compras;
import org.vaadin.example.Persona;
import com.vaadin.flow.component.grid.contextmenu.GridContextMenu;
import com.vaadin.flow.component.grid.contextmenu.GridMenuItem;

import javax.swing.plaf.basic.BasicOptionPaneUI;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

public class GridsView extends VerticalLayout {
    DataService data = new DataService();
    public void PaginaGridsView(Persona persona) throws URISyntaxException {
        removeAll();
        H1 h1 = new H1("Mostrando Compras de " + persona.getNombre());

        removeAll();
        ArrayList<Compras> listaCompras = data.getCompras(persona);
        Grid<Compras> grid = new Grid();
        grid.setAllRowsVisible(true);
        grid.setItems(listaCompras);
        GridContextMenu<Compras> menu = grid.addContextMenu();

        grid.addColumn(Compras::getId).setHeader("ID");
        grid.addColumn(Compras::getNombre).setHeader("Nombre");
        grid.addColumn(Compras::getFechaCompra).setHeader("Fecha");

        menu.setOpenOnClick(true);
        menu.addItem("Delete", event -> {
            try {
                data.eliminarCompras(event.getItem().get().getId(), persona.getId());
                Notification notification = Notification.show("Compra borrada con éxito!");
                notification.addThemeVariants(NotificationVariant.LUMO_SUCCESS);
                ArrayList<Compras> listaComrpas = persona.getCompras();
                grid.setItems(listaCompras);
            } catch (IOException e) {
                throw new RuntimeException(e);
            } catch (URISyntaxException e) {
                throw new RuntimeException(e);
            }
        });
        menu.addItem("Edit", event -> {
            removeAll();
            EditarCompras editarCompras = new EditarCompras();
            editarCompras.editarComprasView(persona, event.getItem().get());
            this.add(editarCompras);
        });
        Button anhadir = new Button("Añadir Compra");
        anhadir.addClickListener(ButtonClickevent ->{
           removeAll();
           Anhadir_Compras anhadirCompras = new Anhadir_Compras();
           anhadirCompras.Anhadir_ComprasView(persona);
           this.add(anhadirCompras);
        });

        Button eliminar = new Button("Borrar Usuario");
        eliminar.addClickListener( ButtonClickevent -> {
            removeAll();
            data.eliminarUsuario(persona);
            LoginView loginView = new LoginView();
            loginView.PaginaLoginWiew();
            this.add(loginView);
        });

        Button editarUsuario = new Button("Editar Usuario");
        editarUsuario.addClickListener( event -> {
            removeAll();
            EditarUsuario editarUsuario1 = new EditarUsuario();
            editarUsuario1.paginaEditar(persona);
            persona.setCompras(data.getCompras(persona));
            this.add(editarUsuario1);
        });

        HorizontalLayout horizontalLayoutH1 = new HorizontalLayout();
        HorizontalLayout horizontalLayoutGrid = new HorizontalLayout();
        HorizontalLayout horizontalLayoutBotones = new HorizontalLayout();

        horizontalLayoutBotones.add(anhadir, editarUsuario, eliminar);
        horizontalLayoutH1.add(h1);
        horizontalLayoutGrid.add(grid, menu);
        this.add(horizontalLayoutH1, horizontalLayoutGrid, horizontalLayoutBotones);
    }
}
