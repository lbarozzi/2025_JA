package it.eforhum.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import it.eforhum.dao.TodoSession;
import it.eforhum.entity.TodoItem;

@WebServlet(name = "TodoServlet", urlPatterns = {"/todos"})
public class TodoServlet extends HttpServlet {
    
    private TodoSession session = new TodoSession();
    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        
        response.setContentType("text/html;charset=UTF-8");
        
        try (PrintWriter out = response.getWriter()) {
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Todo List</title>");
            out.println("<style>");
            out.println("body { font-family: Arial, sans-serif; margin: 40px; }");
            out.println("table { border-collapse: collapse; width: 100%; }");
            out.println("th, td { border: 1px solid #ddd; padding: 12px; text-align: left; }");
            out.println("th { background-color: #f2f2f2; }");
            out.println(".completed { text-decoration: line-through; color: #888; }");
            out.println(".form-container { margin: 20px 0; padding: 20px; border: 1px solid #ddd; }");
            out.println("input[type=text], textarea { width: 300px; padding: 8px; margin: 5px 0; }");
            out.println("input[type=submit] { padding: 10px 20px; background-color: #007cba; color: white; border: none; cursor: pointer; }");
            out.println("</style>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Lista Todo</h1>");
            
            // Form per aggiungere nuovo todo
            out.println("<div class='form-container'>");
            out.println("<h3>Aggiungi nuovo Todo</h3>");
            out.println("<form method='post'>");
            out.println("<input type='hidden' name='action' value='add'>");
            out.println("<div>");
            out.println("<label>Titolo:</label><br>");
            out.println("<input type='text' name='title' required>");
            out.println("</div>");
            out.println("<div>");
            out.println("<label>Descrizione:</label><br>");
            out.println("<textarea name='description' rows='3'></textarea>");
            out.println("</div>");
            out.println("<input type='submit' value='Aggiungi Todo'>");
            out.println("</form>");
            out.println("</div>");
            
            // Lista todos
            try {
                List<TodoItem> todos = session.findAll();
                
                if (todos.isEmpty()) {
                    out.println("<p>Nessun todo trovato. Aggiungine uno!</p>");
                } else {
                    out.println("<table>");
                    out.println("<tr>");
                    out.println("<th>ID</th>");
                    out.println("<th>Titolo</th>");
                    out.println("<th>Descrizione</th>");
                    out.println("<th>Completato</th>");
                    out.println("<th>Data Creazione</th>");
                    out.println("<th>Azioni</th>");
                    out.println("</tr>");
                    
                    for (TodoItem todo : todos) {
                        String cssClass = todo.getCompleted() ? "completed" : "";
                        out.println("<tr class='" + cssClass + "'>");
                        out.println("<td>" + todo.getId() + "</td>");
                        out.println("<td>" + escapeHtml(todo.getTitle()) + "</td>");
                        out.println("<td>" + escapeHtml(todo.getDescription()) + "</td>");
                        out.println("<td>" + (todo.getCompleted() ? "Sì" : "No") + "</td>");
                        out.println("<td>" + todo.getCreatedAt() + "</td>");
                        out.println("<td>");
                        
                        // Toggle completion
                        if (!todo.getCompleted()) {
                            out.println("<form style='display:inline' method='post'>");
                            out.println("<input type='hidden' name='action' value='complete'>");
                            out.println("<input type='hidden' name='id' value='" + todo.getId() + "'>");
                            out.println("<input type='submit' value='Completa'>");
                            out.println("</form>");
                        }
                        
                        // Delete
                        out.println("<form style='display:inline; margin-left:10px' method='post'>");
                        out.println("<input type='hidden' name='action' value='delete'>");
                        out.println("<input type='hidden' name='id' value='" + todo.getId() + "'>");
                        out.println("<input type='submit' value='Elimina' onclick='return confirm(\"Sei sicuro?\")'>");
                        out.println("</form>");
                        
                        out.println("</td>");
                        out.println("</tr>");
                    }
                    out.println("</table>");
                }
            } catch (Exception e) {
                out.println("<p style='color:red'>Errore nel caricamento dei todo: " + e.getMessage() + "</p>");
                e.printStackTrace();
            }
            
            out.println("<p><a href='index.jsp'>Torna alla home</a></p>");
            out.println("</body>");
            out.println("</html>");
        }
    }
    
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        
        String action = request.getParameter("action");
        
        try {
            if ("add".equals(action)) {
                String title = request.getParameter("title");
                String description = request.getParameter("description");
                
                TodoItem todoItem = new TodoItem(title, description);
                session.save(todoItem);
                
            } else if ("complete".equals(action)) {
                Long id = Long.parseLong(request.getParameter("id"));
                TodoItem todoItem = session.findById(id);
                if (todoItem != null) {
                    todoItem.setCompleted(true);
                    session.update(todoItem);
                }
                
            } else if ("delete".equals(action)) {
                Long id = Long.parseLong(request.getParameter("id"));
                session.delete(id);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        // Redirect alla GET per evitare resubmit
        response.sendRedirect("todos");
    }
    
    private String escapeHtml(String text) {
        if (text == null) return "";
        return text.replace("&", "&amp;")
                  .replace("<", "&lt;")
                  .replace(">", "&gt;")
                  .replace("\"", "&quot;")
                  .replace("'", "&#39;");
    }
}