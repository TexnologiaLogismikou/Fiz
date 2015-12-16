package com.tech.configurations;

import com.tech.configurations.tools.Host;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

/**
 * Created by Andreas on 1/12/2015.
 */
@Configuration
@EnableWebMvc
public class WebConfig extends WebMvcConfigurerAdapter {

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedOrigins(Host.apache)
                .allowedMethods("POST", "GET", "PUT", "OPTIONS", "DELETE");
//                .allowedHeaders("Origin, X-Requested-With, Content-Type, Accept, X-Ajax-Call");
    }
}
