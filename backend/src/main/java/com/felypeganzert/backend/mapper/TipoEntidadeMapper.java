package com.felypeganzert.backend.mapper;

import com.felypeganzert.backend.dto.TipoEntidadeDTO;
import com.felypeganzert.backend.entity.TipoEntidade;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class TipoEntidadeMapper {

    @Autowired
    private ModelMapper modelMapper;

    public TipoEntidadeDTO toDTO(TipoEntidade tipoEntidade) {
        if (tipoEntidade == null) {
            return null;
        }
        
        TipoEntidadeDTO tipoEntidadeDTO = modelMapper.map(tipoEntidade, TipoEntidadeDTO.class);
        
        // Mapeamento manual do relacionamento para ID
        if (tipoEntidade.getLinha() != null) {
            tipoEntidadeDTO.setLinhaId(tipoEntidade.getLinha().getId());
        }
        
        return tipoEntidadeDTO;
    }

    public List<TipoEntidadeDTO> toDTOList(List<TipoEntidade> tiposEntidade) {
        if (tiposEntidade == null) {
            return null;
        }
        return tiposEntidade.stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }
}
