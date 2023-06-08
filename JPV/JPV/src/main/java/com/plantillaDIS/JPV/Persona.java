package com.plantillaDIS.JPV;

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
}
