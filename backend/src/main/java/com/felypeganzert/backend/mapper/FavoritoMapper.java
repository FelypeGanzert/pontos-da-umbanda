package com.felypeganzert.backend.mapper;

import java.util.List;

import org.springframework.stereotype.Component;
import org.modelmapper.ModelMapper;

import com.felypeganzert.backend.dto.FavoritoDTO;
import com.felypeganzert.backend.entity.Favorito;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class FavoritoMapper {

    private final ModelMapper mapper;

    public FavoritoDTO toDTO(Favorito favorito) {
        FavoritoDTO dto = mapper.map(favorito, FavoritoDTO.class);
        dto.setPontoCantadoId(favorito.getPontoCantado().getId());
        dto.setPontoCantadoTitulo(favorito.getPontoCantado().getTitulo());
        return dto;
    }

    public List<FavoritoDTO> toDTOList(List<Favorito> favoritos) {
        return favoritos.stream()
            .map(this::toDTO)
            .toList();
    }
}