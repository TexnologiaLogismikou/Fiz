package com.tech;

import com.tech.configurations.InitializeValidators;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.context.web.SpringBootServletInitializer;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.ImportResource;
import org.springframework.context.annotation.PropertySource;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
@ComponentScan("com.auth0")
@ImportResource("classpath:auth0-security-context.xml")
@PropertySource("classpath:auth0.properties")
public class Application extends SpringBootServletInitializer {

    /**
     * Initialize Spring Application
     * @param args
     * @throws Exception 
     */
    public static void main(String[] args) throws Exception {
        InitializeValidators.InitializeCustomValidators();
 
        SpringApplication.run(Application.class, args);
    }

    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
        return application.sources(Application.class);
    }
}
