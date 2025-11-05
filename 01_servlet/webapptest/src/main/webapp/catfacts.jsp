<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="java.net.http.HttpClient"%>
<%@page import="java.net.http.HttpRequest"%>
<%@page import="java.net.http.HttpResponse"%>
<%@page import="java.net.URI"%>
<%@page import="java.io.IOException"%>
<%@page import="javax.json.Json"%>
<%@page import="javax.json.JsonReader"%>
<%@page import="javax.json.JsonObject"%>
<%@page import="javax.json.JsonException"%>
<%@page import="java.io.StringReader"%>
<html>
    <head>
        <title>Cat Facts</title>
        <style>
            body { font-family: Arial, sans-serif; margin: 40px; }
            h2 { color: #333; }
            a { color: #007cba; text-decoration: none; padding: 10px; display: inline-block; }
            a:hover { text-decoration: underline; }
        </style>
    </head>
    <body>
        <h2>Cat Facts</h2>
        <p>Benvenuto nella pagina dei fatti sui gatti!</p>
        <%
            try {
                HttpClient client = HttpClient.newHttpClient();
                HttpRequest httpRequest = HttpRequest.newBuilder()
                        .uri(URI.create("https://catfact.ninja/fact"))
                        .GET()
                        .build();

                for(int i = 0; i < 10; i++) {
                    HttpResponse<String> httpResponse = client.send(httpRequest, HttpResponse.BodyHandlers.ofString());
                    String responseBody = httpResponse.body();
                    
                    if (responseBody != null && responseBody.contains("\"fact\":\"")) {
                        try {
                            JsonReader jsonReader = Json.createReader(new StringReader(responseBody));
                            JsonObject jsonObject = jsonReader.readObject();
                            String fact = jsonObject.getString("fact");
                            out.println("<p>" + fact + "</p>");
                            jsonReader.close();
                        } catch (JsonException je) {
                            // Fallback to manual parsing if JSON parsing fails
                            String[] parts = responseBody.split("\"fact\":\"");
                            if (parts.length > 1) {
                                String fact = parts[1].split("\",")[0];
                                out.println("<p>" + fact + "</p>");
                            }
                        }
                    }
                }
            } catch (IOException | InterruptedException e) {
                out.println("<p>Errore nel caricamento dei fatti sui gatti: " + e.getMessage() + "</p>");
            }
        %>
    </body>
</html>