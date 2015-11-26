package com.tech.configurations;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

import javax.sql.DataSource;

@Configuration
@EnableWebSecurity
public class WebSecurityConfiguration extends WebSecurityConfigurerAdapter {

    @Autowired
    DataSource dataSource;

    @Autowired
    public void configureGlobalSecurity(AuthenticationManagerBuilder auth) throws Exception {
        auth.jdbcAuthentication().dataSource(dataSource)
                .usersByUsernameQuery(
                        "select username,password,enabled from usersdata where username=?")
                .authoritiesByUsernameQuery(
                        "select username, role from user_roles where username=?");
    }
    
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                .antMatchers("/user/**","/user**").access("hasRole('USER')")
                .antMatchers("/chat/**","/chat**").access("hasRole('USER')")
                .antMatchers("/pictures/**","/pictures**").access("hasRole('USER')")
                .antMatchers("/images/**","/images**").access("hasRole('USER')")
                .antMatchers("/", "/home").permitAll()
                .and().logout().logoutUrl("/login?logout")
                .and().formLogin().loginPage("/login").failureUrl("/login?error")
                .and().exceptionHandling().accessDeniedPage("/Access_Denied");
    }
}
