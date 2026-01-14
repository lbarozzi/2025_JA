package it.eforhum.demo.filter;

import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class ApiKeyFilter implements Filter {

    @Value("${api.key:default-secret-key}")
    private String validApiKey;

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        
        HttpServletRequest httpRequest = (HttpServletRequest) request;
        HttpServletResponse httpResponse = (HttpServletResponse) response;

        String requestPath = httpRequest.getRequestURI();

        // Applica il filtro solo agli endpoint /api
        if (requestPath.startsWith("/api")) {
            String apiKey = httpRequest.getHeader("X-API-KEY");

            if (apiKey == null || !apiKey.equals(validApiKey)) {
                httpResponse.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                httpResponse.setContentType("application/json");
                httpResponse.getWriter().write("{\"error\": \"Invalid or missing API key\"}");
                return;
            }
        }

        chain.doFilter(request, response);
    }
}
