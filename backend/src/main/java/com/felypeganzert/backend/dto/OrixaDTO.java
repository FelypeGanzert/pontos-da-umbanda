package com.felypeganzert.backend.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OrixaDTO {

    private Long id;

    @NotBlank(message = "O nome do Orixá é obrigatório")
    @Size(max = 50, message = "O nome deve ter no máximo 50 caracteres")
    private String nome;

    @Size(max = 50, message = "O nome africano deve ter no máximo 50 caracteres")
    private String nomeAfricano;

    @NotBlank(message = "A descrição do Orixá é obrigatória")
    private String descricao;

    @Size(max = 7, message = "A cor primária deve ser um código hexadecimal válido (#FFFFFF)")
    private String corPrimaria;

    @Size(max = 7, message = "A cor secundária deve ser um código hexadecimal válido (#FFFFFF)")
    private String corSecundaria;

    @Size(max = 20, message = "O dia da semana deve ter no máximo 20 caracteres")
    private String diaSemana;

    @Size(max = 30, message = "O elemento deve ter no máximo 30 caracteres")
    private String elemento;

    @Size(max = 100, message = "O sincretismo deve ter no máximo 100 caracteres")
    private String sincretismo;

    @Size(max = 50, message = "O ícone de representação deve ter no máximo 50 caracteres")
    private String iconeRepresentacao;

    private LocalDateTime dataCriacao;

    @NotNull(message = "O status ativo é obrigatório")
    private Boolean ativo;
}
