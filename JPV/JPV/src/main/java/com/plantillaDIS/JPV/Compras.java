package com.plantillaDIS.JPV;

import java.text.DateFormat;
import java.util.Date;

public class Compras {
    private String id;
    private String nombre;
    private String fechaCompra;

    public Compras(String id, String nombre, String fechaCompra) {
        this.id = id;
        this.nombre = nombre;
        this.fechaCompra = fechaCompra;
    }

    public Compras() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getFechaCompra() {
        return fechaCompra;
    }

    public void setFechaCompra(String fechaCompra) {
        this.fechaCompra = fechaCompra;
    }

    public String mostrarJson() {
        return "{\n" +
                "\"id\": " +  id + "," + "\n" +
                "\"nombre\": " + "\"" + nombre  + "\"," + "\n" +
                "\"fechaCompra\": " + "\"" + fechaCompra + "\"" +  ",\n" +
                "}";
    }

    @Override
    public String toString() {
        return "Compras{" +
                "id='" + id + '\'' +
                ", nombre='" + nombre + '\'' +
                ", fechaCompra=" + fechaCompra +
                '}';
    }
}
