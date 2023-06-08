package org.vaadin.example;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Serializable;
import java.net.*;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.ArrayList;


@Service
public class GreetService implements Serializable {
    String url = "http://localhost:8081";
   public ArrayList<Compras> getCompras(Persona persona) throws URISyntaxException {
       ArrayList<Compras> listaCompras = new ArrayList<>();
       HttpRequest httpRequest = HttpRequest.newBuilder().uri(new URI(url + "/Compras")).GET().build();
       Gson gson = new Gson();
       String resultado = null;
       HttpResponse<String> respuesta = null;

       try {
           respuesta = HttpClient.newBuilder().build().send(httpRequest, HttpResponse.BodyHandlers.ofString());
           resultado = respuesta.body();
           listaCompras = gson.fromJson(resultado, new com.googlecode.gentyref.TypeToken<ArrayList<Compras>>() {
           }.getType());

       } catch (IOException e) {
           throw new RuntimeException(e);
       } catch (InterruptedException e) {
           throw new RuntimeException(e);
       }
       return listaCompras;
   }
   public Persona comprobarInicioSesion(Persona persona) throws IOException {

       String query = String.format("email=%s&password=%s",
               URLEncoder.encode(persona.getNombre(), "UTF-8"),
               URLEncoder.encode(persona.getPassword(), "UTF-8"));
       URL requestUrl = new URL(url + "/Users" + "?" + query);

       // Crear una conexión HTTP
       HttpURLConnection connection = (HttpURLConnection) requestUrl.openConnection();
       connection.setRequestMethod("GET");

       // Leer la respuesta del backend
       BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
       String inputLine;
       StringBuffer response = new StringBuffer();
       while ((inputLine = in.readLine()) != null) {
           response.append(inputLine);
       }
       in.close();
       Gson gson = new Gson();

       persona = gson.fromJson(String.valueOf(response), new TypeToken<Persona>() {
       }.getType());

       // Imprimir la respuesta del backend
       System.out.println(response.toString());

       return persona;
   }

}
