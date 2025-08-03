package com.felypeganzert.backend.dto;

import java.time.LocalDateTime;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class YoutubeUrlDTO {
    private Long id;

    @NotBlank(message = "A URL é obrigatória")
    @Size(max = 500, message = "A URL deve ter no máximo 500 caracteres")
    private String url;

    private String statusModeracao;
    private Long moderadorId;
    private LocalDateTime dataModeracao;
    private String motivoRejeicao;
    @Builder.Default
    private Boolean ativo = true;
}
