package it.eforhum.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import it.eforhum.dao.TodoSession;
import it.eforhum.entity.TodoItem;

/**
 * Servlet per gestire le azioni sui Todo (aggiungi, completa, elimina)
 * La visualizzazione è delegata alla JSP todos.jsp
 */
@WebServlet(name = "TodoActionServlet", urlPatterns = {"/todoAction"})
public class TodoActionServlet extends HttpServlet {
    
    private TodoSession session = new TodoSession();
    
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        
        // Imposta la codifica per i caratteri UTF-8
        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");
        
        String action = request.getParameter("action");
        
        try {
            switch (action) {
                case "add":
                    handleAddTodo(request);
                    break;
                case "complete":
                    handleCompleteTodo(request);
                    break;
                case "delete":
                    handleDeleteTodo(request);
                    break;
                default:
                    // Azione non riconosciuta, non fare nulla
                    break;
            }
        } catch (Exception e) {
            // Log dell'errore
            e.printStackTrace();
            
            // In un'applicazione reale, potresti voler passare il messaggio di errore
            // alla JSP attraverso una session attribute
            request.getSession().setAttribute("errorMessage", 
                "Errore durante l'operazione: " + e.getMessage());
        }
        
        // Redirect alla JSP per evitare il resubmit del form
        response.sendRedirect("todos.jsp");
    }
    
    /**
     * Gestisce l'aggiunta di un nuovo todo
     */
    private void handleAddTodo(HttpServletRequest request) {
        String title = request.getParameter("title");
        String description = request.getParameter("description");
        
        // Validazione base
        if (title == null || title.trim().isEmpty()) {
            throw new IllegalArgumentException("Il titolo è obbligatorio");
        }
        
        TodoItem todoItem = new TodoItem(title.trim(), 
                                       description != null ? description.trim() : "");
        session.save(todoItem);
    }
    
    /**
     * Gestisce il completamento di un todo
     */
    private void handleCompleteTodo(HttpServletRequest request) {
        String idParam = request.getParameter("id");
        if (idParam == null || idParam.trim().isEmpty()) {
            throw new IllegalArgumentException("ID todo non specificato");
        }
        
        Long id = Long.parseLong(idParam);
        TodoItem todoItem = session.findById(id);
        
        if (todoItem == null) {
            throw new IllegalArgumentException("Todo non trovato con ID: " + id);
        }
        
        if (!todoItem.getCompleted()) {
            todoItem.setCompleted(true);
            session.update(todoItem);
        }
    }
    
    /**
     * Gestisce l'eliminazione di un todo
     */
    private void handleDeleteTodo(HttpServletRequest request) {
        String idParam = request.getParameter("id");
        if (idParam == null || idParam.trim().isEmpty()) {
            throw new IllegalArgumentException("ID todo non specificato");
        }
        
        Long id = Long.parseLong(idParam);
        
        // Verifica che il todo esista prima di eliminarlo
        TodoItem todoItem = session.findById(id);
        if (todoItem == null) {
            throw new IllegalArgumentException("Todo non trovato con ID: " + id);
        }
        
        session.delete(id);
    }
    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        // Redirect alla JSP per le richieste GET
        response.sendRedirect("todos.jsp");
    }
}