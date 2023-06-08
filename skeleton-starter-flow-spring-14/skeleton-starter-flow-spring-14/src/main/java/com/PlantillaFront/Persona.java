package org.vaadin.example;

import com.nimbusds.jose.shaded.gson.Gson;
import com.nimbusds.jose.shaded.gson.JsonArray;
import com.nimbusds.jose.shaded.gson.JsonObject;
import org.vaadin.example.Compras;

import java.text.SimpleDateFormat;
import java.util.ArrayList;

public class Persona {
    private int id;
    private String nombre;
    private String password;
    private ArrayList<Compras> compras;
    public Persona() {
    }

    public Persona(int id, String nombre, String password, ArrayList<Compras> compras) {
        this.id = id;
        this.nombre = nombre;
        this.password = password;
        this.compras = compras;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public ArrayList<Compras> getCompras() {
        return compras;
    }

    public void setCompras(ArrayList<Compras> compras) {
        this.compras = compras;
    }

    @Override
    public String toString() {
        return "Persona{" +
                "id=" + id +
                ", nombre='" + nombre + '\'' +
                ", password='" + password + '\'' +
                ", compras='" + compras + '\'' +
                '}';
    }
    public String montarJSON() {
        Gson gson = new Gson();

        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("id", id);
        jsonObject.addProperty("nombre", nombre);
        jsonObject.addProperty("password", password);

        JsonArray comprasArray = new JsonArray();
        SimpleDateFormat dateFormat = new SimpleDateFormat("MMM dd, yyyy, hh:mm:ss a");
        if(compras != null){
            for (Compras compra : compras) {
                JsonObject compraObj = new JsonObject();
                compraObj.addProperty("id", compra.getId());
                compraObj.addProperty("nombre", compra.getNombre());
                String fechaCompraStr = dateFormat.format(compra.getFechaCompra());
                compraObj.addProperty("fechaCompra", fechaCompraStr);
                comprasArray.add(compraObj);
            }
            jsonObject.add("compras", comprasArray);
        }
        return gson.toJson(jsonObject);
    }
}
