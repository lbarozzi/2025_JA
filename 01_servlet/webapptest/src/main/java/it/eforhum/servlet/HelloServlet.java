package it.eforhum.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.http.HttpClient;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.net.URI ;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

import javax.json.Json;
import javax.json.JsonObject;

@WebServlet(name = "HelloServlet", urlPatterns = {"/hello"})
public class HelloServlet extends HttpServlet {
    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        
        response.setContentType("text/html;charset=UTF-8");

        String nome= request.getParameter("nome");
       
        String fact = "";
        try {
            HttpClient client = HttpClient.newHttpClient();
            HttpRequest t = HttpRequest.newBuilder()
                    .uri(URI.create("https://catfact.ninja/fact"))
                    .headers("Accept", "application/json")
                    .headers("User-Agent", "Java 11 HttpClient Bot")
                    .headers("Authorization", "Bearer cippalippa")
                    .build();

            HttpResponse<String> resp;
                resp = client.send(t, HttpResponse.BodyHandlers.ofString());
            if(resp.statusCode()==200) {
                String jsonResponse = resp.body();
                JsonObject jsonrd = Json.createReader(new java.io.StringReader(jsonResponse)).readObject();
                fact = jsonrd.getString("fact");
            }
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    
        
        try (PrintWriter out = response.getWriter()) {
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Hello Servlet</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Ciao dalla Servlet!</h1>");
            out.println("<p>Questa è la mia prima servlet</p>");
            // Controllo se il parametro "nome" è stato fornito
            if (nome == null || nome.isEmpty()) {
                out.println("<p>Parametro nome non fornito.</p>");
            } else {
                out.println("<p>Parametro nome: " + request.getParameter("nome") + "</p>");
            }

            out.println("<h2>Fatto curioso sui gatti:</h2>");
            out.println("<p>" + fact + "</p>");
            out.println("<h2>Elenco di 100 elementi:</h2>");
            out.println("<ol>");
            for(int i=1;i<101;i++) {
                out.println("<li>Elemento ." + i + "</li>");
            }
            out.println("</ol>");
            out.println("<a href='index.jsp'>Torna alla home</a>");
            out.println("</body>");
            out.println("</html>");
        }
    }
    
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        doGet(request, response);
    }
}