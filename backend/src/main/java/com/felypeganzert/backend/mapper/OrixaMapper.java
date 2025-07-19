package com.felypeganzert.backend.mapper;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import com.felypeganzert.backend.dto.OrixaDTO;
import com.felypeganzert.backend.entity.Orixa;

@Mapper
public interface OrixaMapper {

    OrixaMapper INSTANCE = Mappers.getMapper(OrixaMapper.class);

    OrixaDTO toDTO(Orixa orixa);
    List<OrixaDTO> toDTOList(List<Orixa> orixas);
}
