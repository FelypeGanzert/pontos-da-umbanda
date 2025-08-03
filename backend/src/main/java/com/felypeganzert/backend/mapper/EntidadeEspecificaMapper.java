package com.felypeganzert.backend.mapper;

import com.felypeganzert.backend.dto.EntidadeEspecificaDTO;
import com.felypeganzert.backend.entity.EntidadeEspecifica;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class EntidadeEspecificaMapper {

    @Autowired
    private ModelMapper modelMapper;

    public EntidadeEspecificaDTO toDTO(EntidadeEspecifica entidade) {
        if (entidade == null) {
            return null;
        }
        return modelMapper.map(entidade, EntidadeEspecificaDTO.class);
    }

    public List<EntidadeEspecificaDTO> toDTOList(List<EntidadeEspecifica> entidades) {
        if (entidades == null) {
            return null;
        }
        return entidades.stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }
}
