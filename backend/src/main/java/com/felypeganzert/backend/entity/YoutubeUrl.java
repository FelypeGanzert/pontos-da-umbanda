package com.felypeganzert.backend.entity;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "youtube_urls")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class YoutubeUrl {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "ponto_cantado_id", nullable = false)
    private PontoCantado pontoCantado;

    @Column(length = 500, nullable = false)
    private String url;

    @Column(name = "status_moderacao", length = 20)
    private String statusModeracao;

    @Column(name = "moderador_id")
    private Long moderadorId;

    private LocalDateTime dataModeracao;
    private String motivoRejeicao;

    @Builder.Default
    private Boolean ativo = true;
}
