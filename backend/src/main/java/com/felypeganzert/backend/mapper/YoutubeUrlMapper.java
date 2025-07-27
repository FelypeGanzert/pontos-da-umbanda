
package com.felypeganzert.backend.mapper;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.felypeganzert.backend.dto.YoutubeUrlDTO;
import com.felypeganzert.backend.entity.YoutubeUrl;

@Component
public class YoutubeUrlMapper {
    @Autowired
    private ModelMapper modelMapper;

    public YoutubeUrl toEntity(YoutubeUrlDTO dto) {
        if (dto == null) {
            return null;
        }
        return modelMapper.map(dto, YoutubeUrl.class);
    }

    public List<YoutubeUrl> toEntityList(List<YoutubeUrlDTO> dtos) {
        if (dtos == null) {
            return null;
        }
        return dtos.stream().map(this::toEntity).collect(Collectors.toList());
    }

    public YoutubeUrlDTO toDTO(YoutubeUrl url) {
        if (url == null) {
            return null;
        }
        return modelMapper.map(url, YoutubeUrlDTO.class);
    }

    public List<YoutubeUrlDTO> toDTOList(List<YoutubeUrl> urls) {
        if (urls == null) {
            return null;
        }
        return urls.stream().map(this::toDTO).collect(Collectors.toList());
    }
}
