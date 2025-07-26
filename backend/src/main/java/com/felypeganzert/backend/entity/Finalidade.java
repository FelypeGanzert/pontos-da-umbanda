package com.felypeganzert.backend.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "finalidades")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Finalidade {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 80, unique = true, nullable = false)
    private String nome;

    @Column(columnDefinition = "TEXT", nullable = false)
    private String descricao;

    @Column(length = 50)
    private String categoria;

    @Column(columnDefinition = "TEXT")
    private String usoRitual;

    @Column(length = 100)
    private String momentoGira;

    @Column(nullable = false)
    @Builder.Default
    private Boolean ativo = true;
}
