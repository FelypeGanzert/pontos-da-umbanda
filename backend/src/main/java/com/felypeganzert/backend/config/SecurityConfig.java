package com.felypeganzert.backend.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
@Profile("!dev") // Ativa para todos os perfis exceto 'dev'
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            .authorizeHttpRequests(authz -> authz
                .anyRequest().permitAll() // Permite acesso a todas as rotas sem autenticação
            )
            .csrf(csrf -> csrf.disable()) // Desabilita CSRF para APIs REST
            .headers(headers -> headers.frameOptions(frameOptions -> frameOptions.disable())); // Permite H2 Console se estiver usando

        return http.build();
    }
}
