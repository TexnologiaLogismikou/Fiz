package com.tech.configurations.security;

import com.tech.configurations.tools.Host;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class CorsFilter extends OncePerRequestFilter {

    private static final String ORIGIN = "Origin";


    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

        if (request.getHeader(ORIGIN) == null || request.getHeader(ORIGIN).equals("null")) {
            response.addHeader("Access-Control-Allow-Origin", Host.apache);
            response.setHeader("Access-Control-Allow-Credentials", "true");
            response.setHeader("Access-Control-Allow-Headers", "x-requested-with");
            response.setHeader("Access-Control-Expose-Headers", "x-requested-with"); filterChain.doFilter(request, response);

            String reqHead = request.getHeader("Access-Control-Request-Headers");

            if (!StringUtils.isEmpty(reqHead)) {
                response.addHeader("Access-Control-Allow-Headers", reqHead);
            }
        }
        if (request.getMethod().equals("OPTIONS")) {
            try {
                response.getWriter().print("OK");
                response.getWriter().flush();
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            filterChain.doFilter(request, response);
        }

//        HttpServletResponse res = (HttpServletResponse) response;
//        HttpServletRequest req= (HttpServletRequest) request;
//        res.setHeader("Access-Control-Allow-Origin", Host.apache);
//        res.setHeader("Access-Control-Allow-Methods", "POST, GET, OPTIONS, DELETE");
//        res.setHeader("Access-Control-Allow-Headers", "x-requested-with");
//        res.setHeader("Access-Control-Expose-Headers", "x-requested-with"); filterChain.doFilter(request, response);

    }
}