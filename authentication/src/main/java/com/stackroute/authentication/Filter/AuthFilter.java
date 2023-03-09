package com.stackroute.authentication.Filter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

public class AuthFilter extends OncePerRequestFilter {
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        if(request.getMethod().equals("OPTIONAL") || request.getMethod().equals("optional"))
            filterChain.doFilter(request,response);
        else if (request.getHeader("authorization")!=null && request.getHeader("authorization").startsWith("Bearer ")){

        }
        else{
            filterChain.doFilter(request,response);
        }
    }
}
