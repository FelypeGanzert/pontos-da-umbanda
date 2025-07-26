package com.felypeganzert.backend.mapper;

import com.felypeganzert.backend.dto.FinalidadeDTO;
import com.felypeganzert.backend.entity.Finalidade;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class FinalidadeMapper {
    @Autowired
    private ModelMapper modelMapper;

    public FinalidadeDTO toDTO(Finalidade finalidade) {
        if (finalidade == null) {
            return null;
        }
        return modelMapper.map(finalidade, FinalidadeDTO.class);
    }

    public List<FinalidadeDTO> toDTOList(List<Finalidade> finalidades) {
        if (finalidades == null) {
            return null;
        }
        return finalidades.stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }
}
