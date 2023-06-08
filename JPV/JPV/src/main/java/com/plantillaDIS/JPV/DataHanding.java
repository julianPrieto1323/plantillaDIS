package com.plantillaDIS.JPV;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class DataHanding {
    String rutaUsers = "users.json";
    public Persona comprobarInicio(Persona persona){
        int encontrado = 0;
        Persona personaAux = new Persona();

        JSON reader = new JSON();
        ArrayList<Persona> usuarios = reader.LeerFicheroJson(rutaUsers);
        for (int i = 0; i < usuarios.size(); i++) {
            if (usuarios.get(i).getNombre().equals(persona.getNombre()) && usuarios.get(i).getPassword().equals(persona.getPassword())) {
                encontrado = 1;
                personaAux = usuarios.get(i);
            }
        }
        if (encontrado == 1){
            System.out.println("USUARIO ENCONTRADO");
            System.out.println(personaAux);
        }
        else {
            personaAux = null;
            System.out.println("USUARIO NO ENCONTRADO");
        }
        return personaAux;
    }
    public ArrayList<Persona> mostrarUsers(){
        JSON reader = new JSON();
        ArrayList<Persona> usuarios = reader.LeerFicheroJson(rutaUsers);
        return usuarios;
    }
    public ArrayList<Persona> resgistrarUsers(Persona persona){
        int encontrado = 0;
        JSON reader = new JSON();
        ArrayList<Persona> usuarios = reader.LeerFicheroJson(rutaUsers);

        for (int i = 0; i < usuarios.size(); i++){
            if(usuarios.get(i).getNombre().equals(persona.getNombre()) && usuarios.get(i).getPassword().equals(persona.getPassword())){
                encontrado = 1;
            }else{
                encontrado = 0;
            }
        }
        if (encontrado == 1){
            System.out.println("ERROR. EL USUARIO YA EXISTE");
        }
        else {
            //ACTUALIZAR JSON
            persona.setId(usuarios.size() + 1);
            persona.setCompras(null);
            usuarios.add(persona);
            Collections.sort(usuarios, Comparator.comparingInt(Persona::getId));

            reader.escribirUsers(usuarios);
            System.out.println("USUARIO CREADO");
        }
        return usuarios;
    }
    public ArrayList<Persona> editarUsers(Persona personaAntigua, Persona personaNueva){
        int encontrado = 0;
        JSON reader = new JSON();
        ArrayList<Persona> usuarios = reader.LeerFicheroJson(rutaUsers);

        for (int i = 0; i < usuarios.size(); i++){
            if(usuarios.get(i).getNombre().equals(personaAntigua.getNombre()) && usuarios.get(i).getPassword().equals(personaAntigua.getPassword())) {
                encontrado = 1;
                usuarios.get(i).setPassword(personaNueva.getPassword());
                usuarios.get(i).setNombre(personaNueva.getNombre());
                usuarios.get(i).setCompras(personaNueva.getCompras());
                break;
            }
        }
        if (encontrado == 1){
            //ACTUALIZAR JSON
            reader.escribirUsers(usuarios);
            System.out.println("USUARIO ACTUALIZADO");
        }
        else {
            System.out.println("ERROR. USUARIO NO EXISTENTE");
        }
        return usuarios;
    }
    public ArrayList<Persona> eliminarUsers(Persona persona){
        int encontrado = 0, cont = 0;
        Persona personaAux = new Persona();
        JSON reader = new JSON();
        ArrayList<Persona> usuarios = reader.LeerFicheroJson(rutaUsers);

        for (int i = 0; i < usuarios.size(); i++){
            if(usuarios.get(i).getId() == persona.getId()) {
                encontrado = 1;
                usuarios.remove(i);
                break;
            }
        }
        if (encontrado == 1){
            //ACTUALIZAR JSON
            reader.escribirUsers(usuarios);
            System.out.println("USUARIO ELIMINADO");
        }
        else {
            System.out.println("ERROR. USUARIO NO EXISTENTE");
        }
        return usuarios;
    }
    public ArrayList<Compras> mostrarCompras(Persona persona){
        Persona personaAux = new Persona();
        personaAux = comprobarInicio(persona);
        if (personaAux != null){
             persona = personaAux;
        }else{
            persona = null;
        }
        System.out.println("Compras\n" + persona.getCompras());
        return persona.getCompras();
    }
    public Persona anhadirCompas(Compras compra, Persona persona){


        if(comprobarInicio(persona) != null){
            ArrayList<Compras> listaCompras = persona.getCompras();
            // Verificar si la lista de compras es nula
            if (listaCompras == null) {
                // Crear una nueva lista de compras
                listaCompras = new ArrayList<>();
            }// Agregar la nueva compra a la lista
            listaCompras.add(compra);
            // Actualizar la lista de compras en la persona
            persona.setCompras(listaCompras);
        }
        editarUsers(persona, persona);
        return persona;
    }
    public ArrayList<Compras> editarCompas(Persona persona, Compras nuevaCompra){
        ArrayList<Compras> listaNuevaCompras = new ArrayList<Compras>();
        persona = buscarPersona(persona);
        ArrayList<Compras> compras = persona.getCompras();
        for(int i = 0; i < compras.size(); i++){
            if(compras.get(i).getId().equals(nuevaCompra.getId())){
                compras.get(i).setFechaCompra(nuevaCompra.getFechaCompra());
                compras.get(i).setNombre(nuevaCompra.getNombre());
                break;
            }
        }
        persona.setCompras(compras);
        editarUsers(persona, persona);
        return compras;
    }
    public ArrayList<Compras> eliminarCompras(Compras compras, Persona persona){
        int encontrado = 0, cont = 0;
        persona = buscarPersona(persona);
        ArrayList<Compras> listaCompras = persona.getCompras();
        for(int i = 0; i < listaCompras.size(); i++){
            if(listaCompras.get(i).getId().equals(compras.getId())){
                listaCompras.remove(i);
                break;
            }
        }
        persona.setCompras(listaCompras);
        editarUsers(persona, persona);
        return listaCompras;
    }
    public Persona buscarPersona(Persona persona){
        JSON json = new JSON();
        ArrayList<Persona> listaPersonas = json.LeerFicheroJson(rutaUsers);
        for(int i = 0; i < listaPersonas.size(); i++){
            if(listaPersonas.get(i).getId() == persona.getId()){
                persona.setId(listaPersonas.get(i).getId());
                persona.setNombre(listaPersonas.get(i).getNombre());
                persona.setCompras(listaPersonas.get(i).getCompras());
                persona.setPassword(listaPersonas.get(i).getPassword());
                break;
            }
        }
        return persona;
    }
}
