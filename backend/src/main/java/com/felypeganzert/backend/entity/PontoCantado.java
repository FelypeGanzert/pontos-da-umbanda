package com.felypeganzert.backend.entity;

import java.time.LocalDateTime;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "pontos_cantados")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PontoCantado {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 200, nullable = false)
    private String titulo;

    @Column(name = "letra_completa", columnDefinition = "TEXT", nullable = false)
    private String letraCompleta;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "linha_id")
    private Linha linha;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "orixa_id")
    private Orixa orixa;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "tipo_entidade_id")
    private TipoEntidade tipoEntidade;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "entidade_especifica_id")
    private EntidadeEspecifica entidadeEspecifica;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "finalidade_id")
    private Finalidade finalidade;

    @Column(length = 20)
    private String origem;

    @Column(length = 50)
    private String ritmo;

    @Column(length = 10)
    private String tonalidade;

    private Integer duracaoEstimada;

    @OneToMany(mappedBy = "pontoCantado", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<YoutubeUrl> youtubeUrls;

    @ManyToMany
    @JoinTable(
        name = "ponto_cantado_tags",
        joinColumns = @JoinColumn(name = "ponto_cantado_id"),
        inverseJoinColumns = @JoinColumn(name = "tag_id")
    )
    private List<Tag> tags;

    @Column(columnDefinition = "TEXT")
    private String observacoes;

    @Column(length = 150)
    private String autorLetra;

    @Column(length = 150)
    private String terreiroOrigem;

    @Column(length = 100)
    private String regiaoOrigem;

    private LocalDateTime dataContribuicao;

    @Column(name = "usuario_contribuidor_id", nullable = false)
    private Long usuarioContribuidorId;

    @Column(length = 20)
    private String statusModeracao;

    @Column(name = "moderador_id")
    private Long moderadorId;

    private LocalDateTime dataModeracao;
    private String motivoRejeicao;

    @Builder.Default
    private Integer visualizacoes = 0;

    @Builder.Default
    private Boolean ativo = true;
}
