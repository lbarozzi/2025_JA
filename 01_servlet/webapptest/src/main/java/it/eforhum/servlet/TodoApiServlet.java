package it.eforhum.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import java.util.logging.Logger;

import javax.json.Json;
import javax.json.JsonArrayBuilder;
import javax.json.JsonObjectBuilder;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import it.eforhum.dao.TodoSession;
import it.eforhum.entity.TodoItem;

@WebServlet(name = "TodoApiServlet", urlPatterns = {"/api/todos"})
public class TodoApiServlet extends HttpServlet {
    
    private static final Logger logger = Logger.getLogger(TodoApiServlet.class.getName());
    private TodoSession session = new TodoSession();
    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        logger.info("TodoApiServlet: richiesta GET ricevuta");
        
        // Imposta il tipo di contenuto come JSON
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        
        try {
            // Ottiene la lista di TODO dal database
            List<TodoItem> todos = session.findAll();
            
            // Crea un array JSON per i TODO
            JsonArrayBuilder arrayBuilder = Json.createArrayBuilder();
            
            // Converte ogni TODO in JSON
            for (TodoItem todo : todos) {
                JsonObjectBuilder todoBuilder = Json.createObjectBuilder()
                    .add("id", todo.getId())
                    .add("title", todo.getTitle())
                    .add("description", todo.getDescription() != null ? todo.getDescription() : "")
                    .add("completed", todo.getCompleted())
                    .add("createdAt", todo.getCreatedAt().toString());
                
                arrayBuilder.add(todoBuilder);
            }
            
            // Crea l'oggetto JSON di risposta
            JsonObjectBuilder responseBuilder = Json.createObjectBuilder()
                .add("success", true)
                .add("count", todos.size())
                .add("todolist", arrayBuilder);
            
            // Scrive la risposta JSON
            PrintWriter out = response.getWriter();
            out.print(responseBuilder.build().toString());
            out.flush();
            
            logger.info(String.format("TodoApiServlet: restituiti %d todos", todos.size()));
            
        } catch (Exception e) {
            logger.severe("Errore nella generazione del JSON: " + e.getMessage());
            e.printStackTrace();
            
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            
            JsonObjectBuilder errorBuilder = Json.createObjectBuilder()
                .add("success", false)
                .add("error", e.getMessage());
            
            PrintWriter out = response.getWriter();
            out.print(errorBuilder.build().toString());
            out.flush();
        }
    }
}
