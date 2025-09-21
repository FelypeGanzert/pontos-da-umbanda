package com.felypeganzert.backend.dto.auth;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class RegisterResponse {
    private Long id;
    private String nome;
    private String email;
}