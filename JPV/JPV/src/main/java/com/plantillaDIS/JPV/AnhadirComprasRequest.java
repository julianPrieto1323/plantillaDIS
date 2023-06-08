package com.plantillaDIS.JPV;

public class AnhadirComprasRequest {
    private Compras compra;
    private Persona persona;

    public AnhadirComprasRequest() {
    }

    public AnhadirComprasRequest(Compras compra, Persona persona) {
        this.compra = compra;
        this.persona = persona;
    }

    public Compras getCompra() {
        return compra;
    }

    public void setCompra(Compras compra) {
        this.compra = compra;
    }

    public Persona getPersona() {
        return persona;
    }

    public void setPersona(Persona persona) {
        this.persona = persona;
    }
}
