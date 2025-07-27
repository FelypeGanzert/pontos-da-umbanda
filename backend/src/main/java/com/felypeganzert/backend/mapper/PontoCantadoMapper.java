package com.felypeganzert.backend.mapper;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.felypeganzert.backend.dto.PontoCantadoDTO;
import com.felypeganzert.backend.entity.PontoCantado;

@Component
public class PontoCantadoMapper {
    @Autowired
    private ModelMapper modelMapper;


    public PontoCantado toEntity(PontoCantadoDTO dto) {
        if (dto == null) {
            return null;
        }
        return modelMapper.map(dto, PontoCantado.class);
    }

    public PontoCantadoDTO toDTO(PontoCantado ponto) {
        if (ponto == null) {
            return null;
        }
        return modelMapper.map(ponto, PontoCantadoDTO.class);
    }

    public List<PontoCantadoDTO> toDTOList(List<PontoCantado> pontos) {
        if (pontos == null) {
            return null;
        }
        return pontos.stream().map(this::toDTO).collect(Collectors.toList());
    }
}
