package org.vaadin.example;

import java.io.*;
import java.net.*;
import java.net.http.HttpClient;
import java.net.http.HttpHeaders;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;

import com.googlecode.gentyref.TypeToken;
import com.nimbusds.jose.shaded.gson.Gson;
import com.nimbusds.jose.shaded.gson.JsonArray;
import com.nimbusds.jose.shaded.gson.JsonElement;
import com.nimbusds.jose.shaded.gson.JsonObject;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpResponseException;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.vaadin.example.Compras;
import org.vaadin.example.Persona;

import static com.vaadin.flow.router.RouteParameterRegex.getType;

@Service
public class DataService implements Serializable {
    private static final String urlPrefix = "http://localhost:8081/";
    public ArrayList<Persona> mostrarUsers() throws URISyntaxException {
        HttpRequest httpRequest = HttpRequest.newBuilder().uri(new URI(urlPrefix + "TodoUsers")).GET().build();
        ArrayList<Persona> listaPersonas = new ArrayList<Persona>();
        Gson gson = new Gson();
        String resultado = null;
        HttpResponse<String> respuesta = null;

        try {
            respuesta = HttpClient.newBuilder().build().send(httpRequest, HttpResponse.BodyHandlers.ofString());
            resultado = respuesta.body();
            listaPersonas = gson.fromJson(resultado, new TypeToken<ArrayList<Persona>>(){}.getType());

        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        return listaPersonas;
    }

    public Persona comprobarInicio(Persona persona) throws IOException, URISyntaxException {
        Gson gson = new Gson();
        try {
            HttpClient httpClient = HttpClient.newHttpClient();
            HttpRequest.Builder builder = HttpRequest.newBuilder();
            builder.uri(new URI(urlPrefix + "Users"));
            builder.header("Content-Type", "application/json");
            builder.POST(HttpRequest.BodyPublishers.ofString(gson.toJson(persona)));
            HttpRequest httpRequest = builder.build();

            HttpResponse<String> httpResponse = httpClient.send(httpRequest, HttpResponse.BodyHandlers.ofString());

            // Procesa la respuesta del backend
            int statusCode = httpResponse.statusCode();
            String responseBody = httpResponse.body();

            // Aquí puedes realizar las operaciones necesarias con la respuesta recibida
            System.out.println("Código de respuesta: " + statusCode);
            System.out.println("Cuerpo de la respuesta: " + responseBody);

            // Verificar si la respuesta fue exitosa (código 200)
            if (statusCode == 200) {
                // Procesar la respuesta y actualizar la instancia de persona si es necesario
                persona = gson.fromJson(responseBody, Persona.class);
            } else {
                // La respuesta no fue exitosa, puedes manejar el error de acuerdo a tus requerimientos
                System.out.println("Error" + statusCode);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return persona;
    }


    public ArrayList<Persona> RegistrarPersona(Persona persona, ArrayList<Persona> listausuarios){
        Gson gson = new Gson();
        CloseableHttpClient httpClient = HttpClients.createDefault();
        String datospasar = persona.montarJSON();
        StringEntity entidad = null;
        try {
            entidad = new StringEntity(datospasar);
            HttpPost requestpuesta = new HttpPost(urlPrefix + "NuevoUsers");
            requestpuesta.setHeader("Content-Type", "application/json");
            requestpuesta.setHeader("Accept", "application/json");
            requestpuesta.setEntity(entidad);
            CloseableHttpResponse response = null;
            response = httpClient.execute(requestpuesta);
            String respuestaActual = new BasicResponseHandler().handleResponse(response);
            listausuarios = gson.fromJson(respuestaActual, new TypeToken<ArrayList<Persona>>(){}.getType());
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException(e);
        } catch (HttpResponseException e) {
            throw new RuntimeException(e);
        } catch (ClientProtocolException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return listausuarios;
    }

    public ArrayList<Compras> getCompras(Persona persona) {
        try {
            Gson gson = new Gson();
            HttpClient httpClient = HttpClient.newHttpClient();
            String personaJson = gson.toJson(persona);
            HttpRequest httpRequest = HttpRequest.newBuilder()
                    .uri(new URI(urlPrefix + "Compras"))
                    .header("Content-Type", "application/json")
                    .POST(HttpRequest.BodyPublishers.ofString(personaJson))
                    .build();

            HttpResponse<String> httpResponse = httpClient.send(httpRequest, HttpResponse.BodyHandlers.ofString());

            // Procesa la respuesta del backend
            int statusCode = httpResponse.statusCode();
            String responseBody = httpResponse.body();

            // Aquí puedes realizar las operaciones necesarias con la respuesta recibida
            System.out.println("Código de respuesta: " + statusCode);
            System.out.println("Cuerpo de la respuesta Compras: " + responseBody);

            // Verificar si la respuesta fue exitosa (código 200)
            if (statusCode == 200) {
                ArrayList<Compras> compras = gson.fromJson(responseBody, new TypeToken<ArrayList<Compras>>() {}.getType());
                return compras;
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return new ArrayList<>(); // Devuelve una lista vacía en caso de error
    }
    public void eliminarUsuario(Persona persona) {
        try {
            Gson gson = new Gson();
            URL url = new URL(urlPrefix + "EliminarUsers?id=" + persona.getId());
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("DELETE");
            connection.setRequestProperty("Content-Type", "application/json");
            connection.setDoOutput(true);

            // Enviar el objeto persona como JSON en el cuerpo de la solicitud
            OutputStreamWriter writer = new OutputStreamWriter(connection.getOutputStream());
            writer.write(gson.toJson(persona));
            writer.flush();

            int statusCode = connection.getResponseCode();

            // Aquí puedes realizar las operaciones necesarias con el código de respuesta

            writer.close();
            connection.disconnect();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public Persona anhadirCompras(Compras compras, Persona persona) {
        try {
            if (persona == null) {
                // Manejar el caso en que persona sea null
                System.out.println("Error: persona es null");
                return null;
            }

            ArrayList<Compras> listaCompras = persona.getCompras();
            if (listaCompras == null) {
                // Si la lista de compras es null, crea una nueva lista
                listaCompras = new ArrayList<>();
            }

            listaCompras.add(compras);
            persona.setCompras(listaCompras);

            Gson gson = new Gson();
            HttpClient httpClient = HttpClient.newHttpClient();
            HttpRequest.Builder builder = HttpRequest.newBuilder();
            builder.uri(new URI(urlPrefix + "AnhadirCompras"));
            builder.header("Content-Type", "application/json");
            builder.PUT(HttpRequest.BodyPublishers.ofString(gson.toJson(persona)));
            HttpRequest httpRequest = builder.build();

            HttpResponse<String> httpResponse = httpClient.send(httpRequest, HttpResponse.BodyHandlers.ofString());

            // Procesa la respuesta del backend
            int statusCode = httpResponse.statusCode();
            String responseBody = httpResponse.body();

            // Aquí puedes realizar las operaciones necesarias con la respuesta recibida
            System.out.println("Código de respuesta: " + statusCode);
            System.out.println("Cuerpo de la respuesta: " + responseBody);

            // Verificar si la respuesta fue exitosa (código 200)
            if (statusCode == 200) {
                // Procesar la respuesta y actualizar la instancia de persona si es necesario
                persona = gson.fromJson(responseBody, Persona.class);
            } else {
                // La respuesta no fue exitosa, puedes manejar el error de acuerdo a tus requerimientos
                System.out.println("Error" + statusCode);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return persona;
    }
    public void editarUsuario(ArrayList<Persona> lista){
        try {
            Gson gson = new Gson();
            URL url = new URL(urlPrefix + "EditUsers");
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("PUT");
            connection.setRequestProperty("Content-Type", "application/json");
            connection.setDoOutput(true);

            // Enviar el objeto persona como JSON en el cuerpo de la solicitud
            OutputStreamWriter writer = new OutputStreamWriter(connection.getOutputStream());
            writer.write(gson.toJson(lista));
            writer.flush();

            int statusCode = connection.getResponseCode();

            // Aquí puedes realizar las operaciones necesarias con el código de respuesta

            writer.close();
            connection.disconnect();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public static ArrayList<Compras> eliminarCompras(String idCompras, int idPersona) throws URISyntaxException, IOException {
        ArrayList<Compras> listaTweets = new ArrayList<Compras>();

        try {
            HttpClient httpClient = HttpClient.newHttpClient();
            HttpRequest httpRequest = HttpRequest.newBuilder()
                    .uri(new URI(urlPrefix + "EliminarCompras?idCompras=" + idCompras + "&idPersona=" + idPersona))
                    .header("Content-Type", "application/json")
                    .DELETE()
                    .build();

            HttpResponse<String> httpResponse = httpClient.send(httpRequest, HttpResponse.BodyHandlers.ofString());

            // Procesa la respuesta del backend
            int statusCode = httpResponse.statusCode();
            String responseBody = httpResponse.body();

            // Verifica el código de respuesta
            if (statusCode == 200) {
                // Utiliza Gson para convertir la respuesta JSON en una lista de tweets
                com.google.gson.Gson gson = new com.google.gson.Gson();
                listaTweets = gson.fromJson(responseBody, new TypeToken<ArrayList<Compras>>() {}.getType());

                // Aquí puedes realizar las operaciones necesarias con la lista de tweets
                System.out.println("El tweet se eliminó correctamente.");
                System.out.println("Lista de tweets actualizada: " + listaTweets);
            } else {
                System.out.println("Error al eliminar el tweet. Código de respuesta: " + statusCode);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return listaTweets;
    }
    public void editarCompras(@RequestParam String idCompra, @RequestParam String nombreCompra, @RequestParam String fechaCompra, @RequestParam int idPersona){
        try {
            Gson gson = new Gson();
            URL url = new URL(urlPrefix + "EditarCompras?idCompra=" + idCompra + "&nombreCompra=" + nombreCompra + "&fechaCompra=" + fechaCompra + "&idPersona=" + idPersona);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("PUT");
            connection.setRequestProperty("Content-Type", "application/json");
            connection.setDoOutput(true);
            int statusCode = connection.getResponseCode();

            // Aquí puedes realizar las operaciones necesarias con el código de respuesta

            connection.disconnect();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public String comprobarFecha(String fecha){
        StringBuilder resultado = new StringBuilder();
        char caracterOriginal = '/';
        char caracterReemplazo = '-';
        for (int i = 0; i < fecha.length(); i++) {
            char caracter = fecha.charAt(i);

            if (caracter == caracterOriginal) {
                resultado.append(caracterReemplazo);
            } else {
                resultado.append(caracter);
            }
        }

        return resultado.toString();
    }

}
