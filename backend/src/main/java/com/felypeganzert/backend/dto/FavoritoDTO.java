package com.felypeganzert.backend.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class FavoritoDTO {
    private Long id;
    private Long pontoCantadoId;
    private String pontoCantadoTitulo;
}