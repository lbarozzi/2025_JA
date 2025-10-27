<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.List" %>
<%@ page import="it.eforhum.dao.TodoSession" %>
<%@ page import="it.eforhum.entity.TodoItem" %>
<%!
    // Metodo per l'escape HTML
    private String escapeHtml(String text) {
        if (text == null) return "";
        return text.replace("&", "&amp;")
                  .replace("<", "&lt;")
                  .replace(">", "&gt;")
                  .replace("\"", "&quot;")
                  .replace("'", "&#39;");
    }
%>
<%
    // Inizializzazione del session TodoSession
    TodoSession todoSession = new TodoSession();
    List<TodoItem> todos = null;
    String errorMessage = null;
    
    try {
        todos = todoSession.findAll();
    } catch (Exception e) {
        errorMessage = "Errore nel caricamento dei todo: " + e.getMessage();
        e.printStackTrace();
    }
%>
<!DOCTYPE html>
<html>
<head>
    <title>Todo List</title>
    <meta charset="UTF-8">
    <style>
        body { 
            font-family: Arial, sans-serif; 
            margin: 40px; 
            background-color: #f5f5f5;
        }
        .container {
            max-width: 1000px;
            margin: 0 auto;
            background-color: white;
            padding: 20px;
            border-radius: 8px;
            box-shadow: 0 2px 10px rgba(0,0,0,0.1);
        }
        h1 {
            color: #333;
            text-align: center;
            border-bottom: 2px solid #007cba;
            padding-bottom: 10px;
        }
        table { 
            border-collapse: collapse; 
            width: 100%; 
            margin-top: 20px;
        }
        th, td { 
            border: 1px solid #ddd; 
            padding: 12px; 
            text-align: left; 
        }
        th { 
            background-color: #007cba; 
            color: white;
            font-weight: bold;
        }
        tr:nth-child(even) {
            background-color: #f9f9f9;
        }
        tr:hover {
            background-color: #f5f5f5;
        }
        .completed { 
            text-decoration: line-through; 
            color: #888; 
            background-color: #e8f5e8 !important;
        }
        .form-container { 
            margin: 20px 0; 
            padding: 20px; 
            border: 1px solid #ddd; 
            border-radius: 5px;
            background-color: #f9f9f9;
        }
        .form-container h3 {
            margin-top: 0;
            color: #007cba;
        }
        input[type=text], textarea { 
            width: 100%; 
            max-width: 400px;
            padding: 10px; 
            margin: 5px 0; 
            border: 1px solid #ddd;
            border-radius: 4px;
            font-size: 14px;
        }
        input[type=submit] { 
            padding: 10px 20px; 
            background-color: #007cba; 
            color: white; 
            border: none; 
            cursor: pointer; 
            border-radius: 4px;
            font-size: 14px;
            margin: 5px;
        }
        input[type=submit]:hover {
            background-color: #005a8b;
        }
        .btn-danger {
            background-color: #dc3545;
        }
        .btn-danger:hover {
            background-color: #c82333;
        }
        .btn-success {
            background-color: #28a745;
        }
        .btn-success:hover {
            background-color: #218838;
        }
        .actions {
            white-space: nowrap;
        }
        .error {
            color: #dc3545;
            background-color: #f8d7da;
            border: 1px solid #f5c6cb;
            padding: 10px;
            border-radius: 4px;
            margin: 10px 0;
        }
        .no-todos {
            text-align: center;
            color: #666;
            font-style: italic;
            padding: 40px;
        }
        .back-link {
            margin-top: 20px;
            text-align: center;
        }
        .back-link a {
            color: #007cba;
            text-decoration: none;
            font-weight: bold;
        }
        .back-link a:hover {
            text-decoration: underline;
        }
    </style>
</head>
<body>
    <div class="container">
        <h1>Lista Todo</h1>
        
        <!-- Form per aggiungere nuovo todo -->
        <div class="form-container">
            <h3>Aggiungi nuovo Todo</h3>
            <form method="post" action="todoAction">
                <input type="hidden" name="action" value="add">
                <div>
                    <label for="title">Titolo:</label><br>
                    <input type="text" id="title" name="title" required placeholder="Inserisci il titolo del todo...">
                </div>
                <div>
                    <label for="description">Descrizione:</label><br>
                    <textarea id="description" name="description" rows="3" placeholder="Inserisci una descrizione (opzionale)..."></textarea>
                </div>
                <input type="submit" value="Aggiungi Todo">
            </form>
        </div>
        
        <!-- Visualizzazione errori -->
        <% if (errorMessage != null) { %>
            <div class="error">
                <%= escapeHtml(errorMessage) %>
            </div>
        <% } %>
        
        <!-- Lista todos -->
        <% if (todos != null && !todos.isEmpty()) { %>
            <table>
                <thead>
                    <tr>
                        <th>ID</th>
                        <th>Titolo</th>
                        <th>Descrizione</th>
                        <th>Completato</th>
                        <th>Data Creazione</th>
                        <th>Azioni</th>
                    </tr>
                </thead>
                <tbody>
                    <% for (TodoItem todo : todos) { 
                        String cssClass = todo.getCompleted() ? "completed" : "";
                    %>
                        <tr class="<%= cssClass %>">
                            <td><%= todo.getId() %></td>
                            <td><%= escapeHtml(todo.getTitle()) %></td>
                            <td><%= escapeHtml(todo.getDescription()) %></td>
                            <td><%= todo.getCompleted() ? "Sì" : "No" %></td>
                            <td><%= todo.getCreatedAt() %></td>
                            <td class="actions">
                                <% if (!todo.getCompleted()) { %>
                                    <form style="display:inline" method="post" action="todoAction">
                                        <input type="hidden" name="action" value="complete">
                                        <input type="hidden" name="id" value="<%= todo.getId() %>">
                                        <input type="submit" value="Completa" class="btn-success">
                                    </form>
                                <% } %>
                                
                                <form style="display:inline; margin-left:5px" method="post" action="todoAction">
                                    <input type="hidden" name="action" value="delete">
                                    <input type="hidden" name="id" value="<%= todo.getId() %>">
                                    <input type="submit" value="Elimina" class="btn-danger" 
                                           onclick="return confirm('Sei sicuro di voler eliminare questo todo?')">
                                </form>
                            </td>
                        </tr>
                    <% } %>
                </tbody>
            </table>
        <% } else if (todos != null) { %>
            <div class="no-todos">
                <p>Nessun todo trovato. Aggiungine uno usando il modulo sopra!</p>
            </div>
        <% } %>
        
        <div class="back-link">
            <a href="index.jsp">← Torna alla home</a>
        </div>
    </div>
</body>
</html>