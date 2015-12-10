package com.tech.configurations.security;

import org.apache.tomcat.jdbc.pool.DataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
@EnableWebSecurity
public class WebSecurityConfiguration extends WebSecurityConfigurerAdapter {

    @Autowired
    DataSource dataSource;

    @Autowired
    AuthenticationSuccessHandler authenticationSuccessHandler;

    @Autowired
    AuthenticationEntryPoint restAuthenticationEntryPoint;

    @Autowired
    CorsFilter corsFilter;

    @Autowired
    public void configureGlobalSecurity(AuthenticationManagerBuilder auth) throws Exception {
        auth.jdbcAuthentication().dataSource(dataSource)
                .usersByUsernameQuery(
                        "select username,password,enabled from usersdata where username=?")
                .authoritiesByUsernameQuery("SELECT usersdata.username, user_roles.user_role_role\n" +
                        "FROM usersdata\n" +
                        "INNER JOIN user_roles\n" +
                        "ON usersdata.id = user_roles.user_role_userid\n" +
                        "WHERE usersdata.username = ?");
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .addFilterBefore(corsFilter, UsernamePasswordAuthenticationFilter.class)
                .authorizeRequests()
                    .antMatchers("/user**", "/user/**").authenticated()
                    .anyRequest().authenticated()
                .and()
                    .formLogin()
                        .permitAll()
                        .usernameParameter("j_username")
                        .passwordParameter("j_password")
                        .defaultSuccessUrl("/", true)
                        .successHandler(authenticationSuccessHandler)
                        .loginProcessingUrl("/login")
                        .failureHandler(new SimpleUrlAuthenticationFailureHandler())
                .and()
                    .logout()
//                TODO fix logout
                        .permitAll()
                        .logoutUrl("/logout")
                        .invalidateHttpSession(true)
                        .deleteCookies("JSESSIONID")
                        .logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
                .and()
                    .csrf().disable()
                .exceptionHandling()
                    .authenticationEntryPoint(restAuthenticationEntryPoint);
    }
}


