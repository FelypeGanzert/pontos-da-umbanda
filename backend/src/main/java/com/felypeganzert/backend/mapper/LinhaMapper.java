package com.felypeganzert.backend.mapper;

import com.felypeganzert.backend.dto.LinhaDTO;
import com.felypeganzert.backend.entity.Linha;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class LinhaMapper {

    @Autowired
    private ModelMapper modelMapper;

    public LinhaDTO toDTO(Linha linha) {
        if (linha == null) {
            return null;
        }
        
        LinhaDTO linhaDTO = modelMapper.map(linha, LinhaDTO.class);
        
        // Mapeamento manual dos relacionamentos para IDs
        if (linha.getOrixaRegente() != null) {
            linhaDTO.setOrixaRegenteId(linha.getOrixaRegente().getId());
        }
        
        if (linha.getOrixaAdjunto() != null) {
            linhaDTO.setOrixaAdjuntoId(linha.getOrixaAdjunto().getId());
        }
        
        return linhaDTO;
    }

    public List<LinhaDTO> toDTOList(List<Linha> linhas) {
        if (linhas == null) {
            return null;
        }
        return linhas.stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }
}
