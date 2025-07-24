package com.felypeganzert.backend.mapper;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.felypeganzert.backend.dto.OrixaDTO;
import com.felypeganzert.backend.entity.Orixa;

@Component
public class OrixaMapper {

    @Autowired
    private ModelMapper modelMapper;

    public OrixaDTO toDTO(Orixa orixa) {
        if (orixa == null) {
            return null;
        }
        return modelMapper.map(orixa, OrixaDTO.class);
    }

    public List<OrixaDTO> toDTOList(List<Orixa> orixas) {
        if (orixas == null) {
            return null;
        }
        return orixas.stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    public Orixa toEntity(OrixaDTO orixaDTO) {
        if (orixaDTO == null) {
            return null;
        }
        return modelMapper.map(orixaDTO, Orixa.class);
    }
}
