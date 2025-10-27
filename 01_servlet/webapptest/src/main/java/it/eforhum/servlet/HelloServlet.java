package it.eforhum.servlet;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet(name = "HelloServlet", urlPatterns = {"/hello"})
public class HelloServlet extends HttpServlet {
    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        
        response.setContentType("text/html;charset=UTF-8");

        String nome= request.getParameter("nome");
         
        
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