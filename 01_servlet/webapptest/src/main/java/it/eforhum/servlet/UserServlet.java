package it.eforhum.servlet;

import it.eforhum.dao.UserDAO;
import it.eforhum.entity.User;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

@WebServlet(name = "UserServlet", urlPatterns = {"/users"})
public class UserServlet extends HttpServlet {
    
    private UserDAO userDAO = new UserDAO();
    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        
        String action = request.getParameter("action");
        
        if ("login".equals(action)) {
            showLoginForm(request, response);
        } else {
            listUsers(request, response);
        }
    }
    
    private void listUsers(HttpServletRequest request, HttpServletResponse response) 
            throws IOException {
        response.setContentType("text/html;charset=UTF-8");
        
        try (PrintWriter out = response.getWriter()) {
            out.println("<!DOCTYPE html><html><head><title>Users</title>");
            out.println("<style>body{font-family:Arial;margin:40px;}");
            out.println("table{border-collapse:collapse;width:100%;}");
            out.println("th,td{border:1px solid #ddd;padding:12px;text-align:left;}");
            out.println("th{background-color:#f2f2f2;}</style></head><body>");
            out.println("<h1>Users</h1>");
            
            out.println("<h3>Add User</h3>");
            out.println("<form method='post'>");
            out.println("<input type='hidden' name='action' value='add'>");
            out.println("Username: <input type='text' name='username' required><br>");
            out.println("Email: <input type='email' name='email' required><br>");
            out.println("Password: <input type='password' name='password' required><br>");
            out.println("<input type='submit' value='Add'></form><br>");
            
            List<User> users = userDAO.findAll();
            out.println("<table><tr><th>ID</th><th>Username</th><th>Email</th>");
            out.println("<th>Active</th><th>Created</th><th>Actions</th></tr>");
            
            for (User user : users) {
                out.println("<tr><td>" + user.getId() + "</td>");
                out.println("<td>" + user.getUsername() + "</td>");
                out.println("<td>" + user.getEmail() + "</td>");
                out.println("<td>" + (user.getActive() ? "Yes" : "No") + "</td>");
                out.println("<td>" + user.getCreatedAt() + "</td>");
                out.println("<td>");
                out.println("<form style='display:inline' method='post'>");
                out.println("<input type='hidden' name='action' value='toggleActive'>");
                out.println("<input type='hidden' name='id' value='" + user.getId() + "'>");
                out.println("<input type='submit' value='" + (user.getActive() ? "Deactivate" : "Activate") + "'>");
                out.println("</form> ");
                out.println("<form style='display:inline' method='post'>");
                out.println("<input type='hidden' name='action' value='delete'>");
                out.println("<input type='hidden' name='id' value='" + user.getId() + "'>");
                out.println("<input type='submit' value='Delete' onclick='return confirm(\"Sure?\")'>");
                out.println("</form></td></tr>");
            }
            out.println("</table></body></html>");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    private void showLoginForm(HttpServletRequest request, HttpServletResponse response) 
            throws IOException {
        response.setContentType("text/html;charset=UTF-8");
        
        try (PrintWriter out = response.getWriter()) {
            out.println("<!DOCTYPE html><html><head><title>Login</title></head><body>");
            out.println("<h2>Login</h2>");
            out.println("<form method='post'>");
            out.println("<input type='hidden' name='action' value='login'>");
            out.println("Username: <input type='text' name='username' required><br>");
            out.println("Password: <input type='password' name='password' required><br>");
            out.println("<input type='submit' value='Login'></form></body></html>");
        }
    }
    
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        
        String action = request.getParameter("action");
        
        try {
            switch (action) {
                case "add":
                    addUser(request, response);
                    break;
                case "toggleActive":
                    toggleActive(request, response);
                    break;
                case "delete":
                    deleteUser(request, response);
                    break;
                case "login":
                    performLogin(request, response);
                    return;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        response.sendRedirect("users");
    }
    
    private void addUser(HttpServletRequest request, HttpServletResponse response) {
        String username = request.getParameter("username");
        String email = request.getParameter("email");
        String password = request.getParameter("password");
        
        String passwordHash = UserDAO.hashPassword(password);
        User user = new User(username, email, passwordHash);
        userDAO.save(user);
    }
    
    private void toggleActive(HttpServletRequest request, HttpServletResponse response) {
        Long id = Long.parseLong(request.getParameter("id"));
        User user = userDAO.findById(id);
        if (user != null) {
            user.setActive(!user.getActive());
            userDAO.update(user);
        }
    }
    
    private void deleteUser(HttpServletRequest request, HttpServletResponse response) {
        Long id = Long.parseLong(request.getParameter("id"));
        userDAO.delete(id);
    }
    
    private void performLogin(HttpServletRequest request, HttpServletResponse response) 
            throws IOException {
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        
        User user = userDAO.checkLogin(username, password);
        
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            if (user != null) {
                request.getSession().setAttribute("user", user);
                out.println("<h2>Login successful! Welcome " + user.getUsername() + "</h2>");
                out.println("<a href='users'>View users</a>");
            } else {
                out.println("<h2>Login failed!</h2><a href='users?action=login'>Try again</a>");
            }
        }
    }
}
