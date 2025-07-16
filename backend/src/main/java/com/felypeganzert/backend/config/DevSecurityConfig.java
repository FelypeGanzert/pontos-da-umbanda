package com.felypeganzert.backend.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
@Profile("dev")
public class DevSecurityConfig {

    @Bean
    public SecurityFilterChain devSecurityFilterChain(HttpSecurity http) throws Exception {
        http
            .authorizeHttpRequests(authz -> authz
                .anyRequest().permitAll() // Permite acesso total para desenvolvimento local
            )
            .csrf(csrf -> csrf.disable()) // CSRF desabilitado para facilitar testes
            .headers(headers -> headers
                .frameOptions(frameOptions -> frameOptions.disable()) // Permite H2 Console
                .contentTypeOptions(contentType -> contentType.disable()) // Facilita desenvolvimento
            )
            .httpBasic(httpBasic -> httpBasic.disable()) // Remove autenticação básica
            .formLogin(formLogin -> formLogin.disable()); // Remove formulário de login

        return http.build();
    }
}
