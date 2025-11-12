package it.eforhum.servlet;

import it.eforhum.dao.UserDAO;
import it.eforhum.entity.User;

import javax.json.Json;
import javax.json.JsonArrayBuilder;
import javax.json.JsonObject;
import javax.json.JsonObjectBuilder;
import javax.json.JsonReader;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

@WebServlet(name = "UserApiServlet", urlPatterns = {"/api/user", "/api/user/*"})
public class UserApiServlet extends HttpServlet {
    
    private UserDAO userDAO = new UserDAO();
    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        
        response.setContentType("application/json;charset=UTF-8");
        
        String pathInfo = request.getPathInfo();
        
        try (PrintWriter out = response.getWriter()) {
            if (pathInfo == null || pathInfo.equals("/")) {
                // GET /api/user - Lista tutti gli utenti
                listAllUsers(out);
            } else {
                // GET /api/user/{id} - Trova utente per ID
                Long id = Long.parseLong(pathInfo.substring(1));
                findUserById(id, out, response);
            }
        } catch (NumberFormatException e) {
            sendError(response, 400, "Invalid user ID format");
        } catch (Exception e) {
            sendError(response, 500, "Internal server error: " + e.getMessage());
            e.printStackTrace();
        }
    }
    
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        
        response.setContentType("application/json;charset=UTF-8");
        
        String action = request.getParameter("action");
        
        try (PrintWriter out = response.getWriter()) {
            if ("login".equals(action)) {
                // POST /api/user?action=login
                performLogin(request, out, response);
            } else {
                // POST /api/user - Crea nuovo utente
                createUser(request, out, response);
            }
        } catch (Exception e) {
            sendError(response, 500, "Internal server error: " + e.getMessage());
            e.printStackTrace();
        }
    }
    
    @Override
    protected void doPut(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        
        response.setContentType("application/json;charset=UTF-8");
        
        String pathInfo = request.getPathInfo();
        
        if (pathInfo == null || pathInfo.equals("/")) {
            sendError(response, 400, "User ID is required");
            return;
        }
        
        try (PrintWriter out = response.getWriter()) {
            Long id = Long.parseLong(pathInfo.substring(1));
            updateUser(id, request, out, response);
        } catch (NumberFormatException e) {
            sendError(response, 400, "Invalid user ID format");
        } catch (Exception e) {
            sendError(response, 500, "Internal server error: " + e.getMessage());
            e.printStackTrace();
        }
    }
    
    @Override
    protected void doDelete(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        
        response.setContentType("application/json;charset=UTF-8");
        
        String pathInfo = request.getPathInfo();
        
        if (pathInfo == null || pathInfo.equals("/")) {
            sendError(response, 400, "User ID is required");
            return;
        }
        
        try (PrintWriter out = response.getWriter()) {
            Long id = Long.parseLong(pathInfo.substring(1));
            deleteUser(id, out, response);
        } catch (NumberFormatException e) {
            sendError(response, 400, "Invalid user ID format");
        } catch (Exception e) {
            sendError(response, 500, "Internal server error: " + e.getMessage());
            e.printStackTrace();
        }
    }
    
    private void listAllUsers(PrintWriter out) {
        List<User> users = userDAO.findAll();
        
        JsonArrayBuilder arrayBuilder = Json.createArrayBuilder();
        for (User user : users) {
            arrayBuilder.add(userToJson(user));
        }
        
        JsonObject response = Json.createObjectBuilder()
            .add("success", true)
            .add("count", users.size())
            .add("data", arrayBuilder)
            .build();
        
        out.print(response.toString());
    }
    
    private void findUserById(Long id, PrintWriter out, HttpServletResponse response) {
        User user = userDAO.findById(id);
        
        if (user != null) {
            JsonObject jsonResponse = Json.createObjectBuilder()
                .add("success", true)
                .add("data", userToJson(user))
                .build();
            out.print(jsonResponse.toString());
        } else {
            response.setStatus(HttpServletResponse.SC_NOT_FOUND);
            JsonObject jsonResponse = Json.createObjectBuilder()
                .add("success", false)
                .add("message", "User not found")
                .build();
            out.print(jsonResponse.toString());
        }
    }
    
    private void createUser(HttpServletRequest request, PrintWriter out, HttpServletResponse response) 
            throws IOException {
        
        JsonReader jsonReader = Json.createReader(request.getReader());
        JsonObject jsonObject = jsonReader.readObject();
        
        String username = jsonObject.getString("username");
        String email = jsonObject.getString("email");
        String password = jsonObject.getString("password");
        
        String passwordHash = UserDAO.hashPassword(password);
        User user = new User(username, email, passwordHash);
        
        userDAO.save(user);
        
        response.setStatus(HttpServletResponse.SC_CREATED);
        JsonObject jsonResponse = Json.createObjectBuilder()
            .add("success", true)
            .add("message", "User created successfully")
            .add("data", userToJson(user))
            .build();
        
        out.print(jsonResponse.toString());
    }
    
    private void updateUser(Long id, HttpServletRequest request, PrintWriter out, 
                           HttpServletResponse response) throws IOException {
        
        User user = userDAO.findById(id);
        
        if (user == null) {
            response.setStatus(HttpServletResponse.SC_NOT_FOUND);
            JsonObject jsonResponse = Json.createObjectBuilder()
                .add("success", false)
                .add("message", "User not found")
                .build();
            out.print(jsonResponse.toString());
            return;
        }
        
        JsonReader jsonReader = Json.createReader(request.getReader());
        JsonObject jsonObject = jsonReader.readObject();
        
        if (jsonObject.containsKey("username")) {
            user.setUsername(jsonObject.getString("username"));
        }
        if (jsonObject.containsKey("email")) {
            user.setEmail(jsonObject.getString("email"));
        }
        if (jsonObject.containsKey("password")) {
            String passwordHash = UserDAO.hashPassword(jsonObject.getString("password"));
            user.setPasswordHash(passwordHash);
        }
        if (jsonObject.containsKey("active")) {
            user.setActive(jsonObject.getBoolean("active"));
        }
        
        userDAO.update(user);
        
        JsonObject jsonResponse = Json.createObjectBuilder()
            .add("success", true)
            .add("message", "User updated successfully")
            .add("data", userToJson(user))
            .build();
        
        out.print(jsonResponse.toString());
    }
    
    private void deleteUser(Long id, PrintWriter out, HttpServletResponse response) {
        User user = userDAO.findById(id);
        
        if (user == null) {
            response.setStatus(HttpServletResponse.SC_NOT_FOUND);
            JsonObject jsonResponse = Json.createObjectBuilder()
                .add("success", false)
                .add("message", "User not found")
                .build();
            out.print(jsonResponse.toString());
            return;
        }
        
        userDAO.delete(id);
        
        JsonObject jsonResponse = Json.createObjectBuilder()
            .add("success", true)
            .add("message", "User deleted successfully")
            .build();
        
        out.print(jsonResponse.toString());
    }
    
    private void performLogin(HttpServletRequest request, PrintWriter out, 
                             HttpServletResponse response) throws IOException {
        
        JsonReader jsonReader = Json.createReader(request.getReader());
        JsonObject jsonObject = jsonReader.readObject();
        
        String username = jsonObject.getString("username");
        String password = jsonObject.getString("password");
        
        User user = userDAO.checkLogin(username, password);
        
        if (user != null) {
            request.getSession().setAttribute("user", user);
            JsonObject jsonResponse = Json.createObjectBuilder()
                .add("success", true)
                .add("message", "Login successful")
                .add("data", userToJson(user))
                .build();
            out.print(jsonResponse.toString());
        } else {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            JsonObject jsonResponse = Json.createObjectBuilder()
                .add("success", false)
                .add("message", "Invalid credentials")
                .build();
            out.print(jsonResponse.toString());
        }
    }
    
    private JsonObject userToJson(User user) {
        JsonObjectBuilder builder = Json.createObjectBuilder()
            .add("id", user.getId())
            .add("username", user.getUsername())
            .add("email", user.getEmail())
            .add("active", user.getActive())
            .add("createdAt", user.getCreatedAt().toString());
        
        return builder.build();
    }
    
    private void sendError(HttpServletResponse response, int status, String message) 
            throws IOException {
        response.setStatus(status);
        response.setContentType("application/json;charset=UTF-8");
        
        JsonObject jsonResponse = Json.createObjectBuilder()
            .add("success", false)
            .add("message", message)
            .build();
        
        try (PrintWriter out = response.getWriter()) {
            out.print(jsonResponse.toString());
        }
    }
}
