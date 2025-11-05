package it.eforhum.filter;

import java.io.IOError;
import java.io.IOException;
import java.util.logging.Logger;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.annotation.WebFilter;
import java.util.logging.Logger;
import java.io.IOException;

@WebFilter("/*")
public class LoggingFilter implements Filter {
    private static final Logger logger= Logger.getLogger(LoggingFilter.class.getName());
    
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        logger.info("Logging filter init");

    }

    @Override
    public void destroy() {
        logger.info("Logging filter destroy");
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
    throws IOException, ServletException {
        HttpServletRequest httpRequest = (HttpServletRequest) request;
        HttpServletResponse httpResponse = (HttpServletResponse) response;
        long startTime = System.currentTimeMillis();
        
        logger.info(String.format("Request: %s %s from %s",
            httpRequest.getMethod(),
            httpRequest.getRequestURI(),
            httpRequest.getRemoteAddr()
        ));
        
        try {
            chain.doFilter(httpRequest, httpResponse);
        } finally{
            long duration = System.currentTimeMillis()- startTime;
            logger.info(String.format("Response: %d in %d ms",
                httpResponse.getStatus(),
                duration
            ));
        }
        
    }
}