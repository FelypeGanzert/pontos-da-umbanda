package com.felypeganzert.backend.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class EntidadeEspecificaDTO {
    private Long id;

    @NotBlank(message = "O nome da entidade é obrigatório")
    @Size(max = 100, message = "O nome deve ter no máximo 100 caracteres")
    private String nome;

    @NotNull(message = "O tipo de entidade é obrigatório")
    private TipoEntidadeDTO tipoEntidade;

    private LinhaDTO linha;

    private String descricao;
    private String historia;
    private String caracteristicasEspecificas;
    private LocalDateTime dataCriacao;

    @Builder.Default
    private Boolean ativo = true;
}
