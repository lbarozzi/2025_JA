package it.eforhum.demo.filter;

import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Component
@Order(1)
public class RequestLoggingFilter implements Filter {

    private static final Logger logger = LoggerFactory.getLogger(RequestLoggingFilter.class);
    private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        
        HttpServletRequest httpRequest = (HttpServletRequest) request;
        HttpServletResponse httpResponse = (HttpServletResponse) response;

        long startTime = System.currentTimeMillis();
        String timestamp = LocalDateTime.now().format(formatter);

        // Log della richiesta in ingresso
        logger.info("=== Richiesta in ingresso ===");
        logger.info("Timestamp: {}", timestamp);
        logger.info("Metodo: {}", httpRequest.getMethod());
        logger.info("URI: {}", httpRequest.getRequestURI());
        logger.info("Query String: {}", httpRequest.getQueryString());
        logger.info("IP Client: {}", httpRequest.getRemoteAddr());
        logger.info("User-Agent: {}", httpRequest.getHeader("User-Agent"));

        try {
            chain.doFilter(request, response);
        } finally {
            // Log della risposta
            long duration = System.currentTimeMillis() - startTime;
            logger.info("=== Risposta ===");
            logger.info("Status Code: {}", httpResponse.getStatus());
            logger.info("Tempo di esecuzione: {} ms", duration);
            logger.info("============================");
        }
    }
}
