package com.alura.forohub.infra.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Autowired
    private SecurityFilter securityFilter;

    @SuppressWarnings({"deprecation", "removal"})
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception{
        return httpSecurity.csrf().disable().sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and().authorizeHttpRequests()
                .requestMatchers(HttpMethod.POST, "/login", "/login/**").permitAll()
                .requestMatchers("/swagger-iu.html","/v3/api-docs/**", "swagger-ui/**").permitAll()
                .requestMatchers(HttpMethod.PUT, "/course", "/answer","/topic","/course/**","/**/answer", "/topic/**")
                .hasAnyAuthority("ROLE_ADMIN")
                .requestMatchers(HttpMethod.DELETE, "/course", "/answer", "/topic", "/course/**","/**/answer")
                .hasAnyAuthority("ROLE_ADMIN")
                .requestMatchers(HttpMethod.POST, "/course", "/topic", "/course/**", "/topic/**")
                .hasAnyAuthority("ROLE_INSTRUCTOR", "ROLE_ADMIN")
                .requestMatchers(HttpMethod.DELETE, "/answer", "/**/answer")
                .hasAnyAuthority("ROLE_INSTRUCTOR","ROLE_MODERATOR")
                .anyRequest()
                .authenticated()
                .and()
                .addFilterBefore(securityFilter, UsernamePasswordAuthenticationFilter.class)
                .build();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception{
        return authenticationConfiguration.getAuthenticationManager();
    }

    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

}
