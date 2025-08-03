package com.felypeganzert.backend.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class FinalidadeDTO {
    private Long id;

    @NotBlank(message = "O nome da finalidade é obrigatório")
    @Size(max = 80, message = "O nome deve ter no máximo 80 caracteres")
    private String nome;

    @NotBlank(message = "A descrição é obrigatória")
    private String descricao;

    private String categoria;
    private String usoRitual;
    private String momentoGira;

    @Builder.Default
    private Boolean ativo = true;
}
