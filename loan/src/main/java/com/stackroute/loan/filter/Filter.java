package com.stackroute.loan.filter;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class Filter extends OncePerRequestFilter {

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        if (request.getMethod().equals("OPTIONAL") || request.getMethod().equals("optional")){
            filterChain.doFilter(request,response);
        } else if (request.getHeader("authorization") != null && request.getHeader("authorization").startsWith("Bearer ")) {
            String token = request.getHeader("authorization").substring(7);
            Claims claims = Jwts.parser().setSigningKey("My-Secret-Key").parseClaimsJws(token).getBody();
            request.setAttribute("email",claims.get("email"));
            filterChain.doFilter(request,response);
        }
        else{
            throw new RuntimeException("User not authorized, please login");
        }
    }
}
