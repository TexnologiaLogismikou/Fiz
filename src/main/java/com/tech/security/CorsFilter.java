package com.tech.security;

import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.util.StringUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class CorsFilter extends UsernamePasswordAuthenticationFilter {
    static final String ORIGIN = "Origin";

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response){

        if (request.getHeader(ORIGIN) == null || request.getHeader(ORIGIN).equals("null")) {
            response.addHeader("Access-Control-Allow-Origin", "*");
            response.setHeader("Access-Control-Allow-Credentials", "true");
            response.addHeader("Access-Control-Max-Age", "10");
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
        }

        return super.attemptAuthentication(request, response);
    }
}