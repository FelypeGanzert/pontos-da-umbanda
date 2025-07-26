package com.felypeganzert.backend.mapper;

import com.felypeganzert.backend.dto.LinhaDTO;
import com.felypeganzert.backend.entity.Linha;
import org.modelmapper.ModelMapper;
// ...existing code...
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class LinhaMapper {

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private OrixaMapper orixaMapper;

    public LinhaDTO toDTO(Linha linha) {
        if (linha == null) {
            return null;
        }

        LinhaDTO linhaDTO = modelMapper.map(linha, LinhaDTO.class);

        // Mapeamento manual dos relacionamentos para DTOs
        if (linha.getOrixaRegente() != null) {
            linhaDTO.setOrixaRegente(orixaMapper.toDTO(linha.getOrixaRegente()));
        }

        if (linha.getOrixaAdjunto() != null) {
            linhaDTO.setOrixaAdjunto(orixaMapper.toDTO(linha.getOrixaAdjunto()));
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
