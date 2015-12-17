package com.tech.configurations.security;

import com.tech.configurations.tools.Host;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class CorsFilter extends OncePerRequestFilter {

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

        response.setHeader("Access-Control-Allow-Origin", Host.apache);
        response.setHeader("Access-Control-Allow-Headers", "X-Ajax-call");
        response.setHeader("Access-Control-Allow-Headers", "X-requested-with");
        response.setHeader("Access-Control-Expose-Headers", "x-requested-with");
        filterChain.doFilter(request, response);
    }
}