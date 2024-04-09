package com.Premate.config;



import java.io.IOException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.FilterConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
@WebFilter("/api/admin/updateAdmin/1")
public class RequestLoggingFilter implements Filter {
    private static final Logger logger = LoggerFactory.getLogger(RequestLoggingFilter.class);

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        // Initialization code here
    }

   
    @Override
    public void destroy() {
        // Cleanup code here
    }

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		// TODO Auto-generated method stub
		try {
            // Log request details before passing the request to the servlet
            logger.info("Request URI: {}", ((HttpServletRequest) request).getRequestURI());
            // Add more logging statements for request parameters, headers, etc.

            // Pass the request down the filter chain
            chain.doFilter(request, response);
        } catch (Exception e) {
            // Log any errors that occur during request processing
            logger.error("Error processing request: {}", e.getMessage(), e);
            // Optionally, you can handle the error and return an error response
            // Example: ((HttpServletResponse) response).sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        }
		
	}
}

