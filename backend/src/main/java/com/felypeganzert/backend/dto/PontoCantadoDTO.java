package com.felypeganzert.backend.dto;

import java.time.LocalDateTime;
import java.util.List;

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
public class PontoCantadoDTO {
    private Long id;

    @NotBlank(message = "O título é obrigatório")
    @Size(max = 200, message = "O título deve ter no máximo 200 caracteres")
    private String titulo;

    @NotBlank(message = "A letra completa é obrigatória")
    private String letraCompleta;

    private LinhaDTO linha;
    private OrixaDTO orixa;
    private TipoEntidadeDTO tipoEntidade;
    private EntidadeEspecificaDTO entidadeEspecifica;
    private FinalidadeDTO finalidade;

    private String origem;
    private String ritmo;
    private String tonalidade;
    private Integer duracaoEstimada;
    private List<YoutubeUrlDTO> youtubeUrls;
    private List<TagDTO> tags;
    private String observacoes;
    private String autorLetra;
    private String terreiroOrigem;
    private String regiaoOrigem;
    private LocalDateTime dataContribuicao;
    @NotNull(message = "O usuário contribuidor é obrigatório")
    private Long usuarioContribuidorId;
    private String statusModeracao;
    private Long moderadorId;
    private LocalDateTime dataModeracao;
    private String motivoRejeicao;
    @Builder.Default
    private Integer visualizacoes = 0;
    @Builder.Default
    private Boolean ativo = true;
}
