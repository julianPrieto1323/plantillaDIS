package com.plantillaDIS.JPV;

import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import com.plantilla.plantillaJPV.Persona;
@RestController
public class Controller {
    DataHanding data = new DataHanding();
    @PostMapping("/Users")//Inicio de sesion
    public Persona inicioSesion(@RequestBody Persona persona){
        return data.comprobarInicio(persona);
    }
    @GetMapping("/TodoUsers")//Muestreo de todos los usuarios
    public ArrayList<Persona> recogerUsuarios(){
        return data.mostrarUsers();
    }
    @PostMapping ("/NuevoUsers")//Registro de nuevos usuarios
    public ArrayList<Persona> registrarUsuarios(@RequestBody Persona persona){
        return data.resgistrarUsers(persona);
    }
    @PutMapping ("/EditUsers")//Editar usuarios
    public ArrayList<Persona> editarUsuarios(@RequestBody ArrayList<Persona> usuarios){
        Persona personaAntigua = usuarios.get(0);//Usuarios Antiguo
        Persona personaNueva = usuarios.get(1); //Usuario Nuevo
        return data.editarUsers(personaAntigua, personaNueva);
    }
    @DeleteMapping ("/EliminarUsers{id}")//Eliminar Usuarios
    public ArrayList<Persona> eliminarUsuarios(@RequestParam int id){
        Persona persona = new Persona();
        persona.setId(id);
        return data.eliminarUsers(persona);
    }
    @PostMapping("/Compras")//Muestreo de todos los usuarios
    public ArrayList<Compras> recogerCompras(@RequestBody Persona persona){
        return data.mostrarCompras(persona);
    }
    @PutMapping("/AnhadirCompras")
    public Persona anhadirCompras(@RequestBody AnhadirComprasRequest reuqest){
        return data.anhadirCompas(reuqest.getCompra(), reuqest.getPersona());
    }

}
