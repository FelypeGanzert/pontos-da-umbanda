package com.felypeganzert.backend.dto;

import java.time.LocalDateTime;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class LinhaDTO {

    private Long id;

    @NotBlank(message = "O nome da Linha é obrigatório")
    @Size(max = 80, message = "O nome deve ter no máximo 80 caracteres")
    private String nome;

    @NotNull(message = "O Orixá regente é obrigatório")
    private OrixaDTO orixaRegente;

    private OrixaDTO orixaAdjunto;

    @NotBlank(message = "A descrição da Linha é obrigatória")
    private String descricao;

    private String caracteristicas;

    @Size(max = 100, message = "As cores devem ter no máximo 100 caracteres")
    private String cores;

    @Size(max = 200, message = "Os elementos de trabalho devem ter no máximo 200 caracteres")
    private String elementosTrabalho;

    private Integer numeroOrdem;

    private LocalDateTime dataCriacao;

    @NotNull(message = "O status ativo é obrigatório")
    @Builder.Default
    private Boolean ativo = true;
}
