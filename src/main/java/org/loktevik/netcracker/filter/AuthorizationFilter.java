package org.loktevik.netcracker.filter;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.loktevik.netcracker.security.utils.AuthorizationTokenProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.*;

import static org.springframework.http.HttpHeaders.AUTHORIZATION;
import static org.springframework.http.HttpStatus.FORBIDDEN;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

public class AuthorizationFilter extends OncePerRequestFilter {
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        if (request.getContextPath().equals("/login")){
            filterChain.doFilter(request, response);
        } else if (request.getContextPath().equals("/sign-in")){
            filterChain.doFilter(request, response);
        } else {
            System.out.println("REQUEST ATTEMPT");
            String authToken = request.getParameter("access_token");
            String authorizationHeader = request.getHeader(AUTHORIZATION);
            if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ") || (authToken != null)){
                try{
                    String token = null;
                    if (authorizationHeader != null){
                        token = authorizationHeader.substring("Bearer ".length());
                    } else {
                        token = authToken;
                    }
                    UsernamePasswordAuthenticationToken authenticationToken = AuthorizationTokenProvider.getToken(token);
                    SecurityContextHolder.getContext().setAuthentication(authenticationToken);
                    filterChain.doFilter(request, response);
                } catch (Exception e){
                    response.setStatus(FORBIDDEN.value());
                    Map<String, String> error = new HashMap<>();
                    error.put("error_message", e.getMessage());
                    response.setContentType(APPLICATION_JSON_VALUE);
                    new ObjectMapper().writeValue(response.getOutputStream(), error);
                }
            } else {
                filterChain.doFilter(request, response);
            }
        }
    }
}
