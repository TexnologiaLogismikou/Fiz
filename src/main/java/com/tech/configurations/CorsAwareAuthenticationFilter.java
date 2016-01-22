package com.tech.configurations;

import com.tech.configurations.tools.Host;
import org.springframework.context.annotation.Bean;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class CorsAwareAuthenticationFilter extends UsernamePasswordAuthenticationFilter {
    static final String ORIGIN = Host.apache;

    @Bean
    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response){

            response.addHeader("Access-Control-Allow-Origin", Host.apache);
            response.setHeader("Access-Control-Allow-Methods", "POST, GET, OPTIONS, DELETE");
            response.setHeader("Access-Control-Allow-Credentials", "true");
            response.setHeader("Access-Control-Allow-Headers", "X-Ajax-call");
            response.setHeader("Access-Control-Allow-Headers", "X-requested-with");
            response.setHeader("Access-Control-Expose-Headers", "x-requested-with");


        return super.attemptAuthentication(request, response);
    }
}