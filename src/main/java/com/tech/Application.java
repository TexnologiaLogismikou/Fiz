package com.tech;

import com.tech.configurations.InitializeValidators;
import com.tech.configurations.tools.MessageDeleter;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.context.web.SpringBootServletInitializer;

@SpringBootApplication
public class Application extends SpringBootServletInitializer {

    /**
     * Initialize Spring Application
     * @param args
     * @throws Exception 
     */
    public static void main(String[] args) throws Exception {
        InitializeValidators.InitializeCustomValidators();
        
        Thread tmp = new Thread(new MessageDeleter());
        tmp.start();
        
        SpringApplication.run(Application.class, args);
    }

    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
        return application.sources(Application.class);
    }
}
