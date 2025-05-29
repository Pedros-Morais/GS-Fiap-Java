package com.fiap.blackoutservice.filter;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.fiap.blackoutservice.exception.ErrorResponse;
import com.fasterxml.jackson.databind.ObjectMapper;

import io.github.bucket4j.Bucket;
import io.github.bucket4j.ConsumptionProbe;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.util.Date;

@Component
public class RateLimitingFilter extends OncePerRequestFilter {

    private final Bucket bucket;
    private final ObjectMapper objectMapper;

    @Autowired
    public RateLimitingFilter(Bucket bucket, ObjectMapper objectMapper) {
        this.bucket = bucket;
        this.objectMapper = objectMapper;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        
        // Skip rate limiting for Swagger and H2 console
        String path = request.getRequestURI();
        if (path.contains("/swagger-ui") || path.contains("/api-docs") || path.contains("/h2-console")) {
            filterChain.doFilter(request, response);
            return;
        }
        
        ConsumptionProbe probe = bucket.tryConsumeAndReturnRemaining(1);
        
        if (probe.isConsumed()) {
            // Add rate limit headers
            response.addHeader("X-Rate-Limit-Remaining", String.valueOf(probe.getRemainingTokens()));
            response.addHeader("X-Rate-Limit-Retry-After-Seconds", String.valueOf(probe.getNanosToWaitForRefill() / 1_000_000_000));
            
            filterChain.doFilter(request, response);
        } else {
            // Too many requests
            response.setStatus(HttpStatus.TOO_MANY_REQUESTS.value());
            response.setContentType("application/json");
            
            // Create error response
            ErrorResponse errorResponse = new ErrorResponse(
                    new Date(),
                    HttpStatus.TOO_MANY_REQUESTS.value(),
                    "Too Many Requests",
                    "You have exceeded the API rate limit. Please try again later.",
                    request.getRequestURI()
            );
            
            response.getWriter().write(objectMapper.writeValueAsString(errorResponse));
            response.getWriter().flush();
        }
    }
}
