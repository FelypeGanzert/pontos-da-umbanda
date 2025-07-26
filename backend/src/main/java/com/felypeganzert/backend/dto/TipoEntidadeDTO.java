package com.felypeganzert.backend.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
// ...existing code...
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TipoEntidadeDTO {

    private Long id;

    @NotBlank(message = "O nome do Tipo de Entidade é obrigatório")
    @Size(max = 80, message = "O nome deve ter no máximo 80 caracteres")
    private String nome;

    private LinhaDTO linha;

    @NotBlank(message = "A descrição do Tipo de Entidade é obrigatória")
    private String descricao;

    private String caracteristicas;

    @Size(max = 200, message = "A área de atuação deve ter no máximo 200 caracteres")
    private String areaAtuacao;

    @Size(max = 50, message = "O ícone de representação deve ter no máximo 50 caracteres")
    private String iconeRepresentacao;

    private LocalDateTime dataCriacao;

    @Builder.Default
    private Boolean ativo = true;
}
