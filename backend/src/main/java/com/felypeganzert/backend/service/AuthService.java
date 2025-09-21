package com.felypeganzert.backend.service;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;

import com.felypeganzert.backend.dto.auth.LoginRequest;
import com.felypeganzert.backend.dto.auth.LoginResponse;
import com.felypeganzert.backend.repository.UsuarioRepository;
import com.felypeganzert.backend.security.JwtService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AuthService {
    
    private final UsuarioRepository usuarioRepository;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    public LoginResponse login(LoginRequest request) {
        authenticationManager.authenticate(
            new UsernamePasswordAuthenticationToken(
                request.getEmail(),
                request.getSenha()
            )
        );
        
        var usuario = usuarioRepository.findByEmail(request.getEmail())
                .orElseThrow();
        
        var jwtToken = jwtService.generateToken(usuario);
        
        return LoginResponse.builder()
                .token(jwtToken)
                .nome(usuario.getNome())
                .email(usuario.getEmail())
                .build();
    }
}