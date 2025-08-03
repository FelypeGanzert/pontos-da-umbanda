package com.felypeganzert.backend.mapper;

import com.felypeganzert.backend.dto.UsuarioDTO;
import com.felypeganzert.backend.entity.Usuario;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class UsuarioMapper {
    @Autowired
    private ModelMapper modelMapper;

    public UsuarioDTO toDTO(Usuario entity) {
        if (entity == null) return null;
        UsuarioDTO dto = modelMapper.map(entity, UsuarioDTO.class);
        // Não mapear senha para DTO por segurança
        dto.setSenha(null);
        return dto;
    }

    public Usuario toEntity(UsuarioDTO dto) {
        if (dto == null) return null;
        Usuario entity = modelMapper.map(dto, Usuario.class);
        // Mapear senha para senhaHash
        if (dto.getSenha() != null) {
            entity.setSenhaHash(dto.getSenha());
        }
        return entity;
    }

    public List<UsuarioDTO> toDTOList(List<Usuario> entities) {
        return entities.stream().map(this::toDTO).toList();
    }
}
