package com.felypeganzert.backend.mapper;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.felypeganzert.backend.dto.TagDTO;
import com.felypeganzert.backend.entity.Tag;

@Component
public class TagMapper {
    @Autowired
    private ModelMapper modelMapper;

    public Tag toEntity(TagDTO dto) {
        if (dto == null) {
            return null;
        }
        return modelMapper.map(dto, Tag.class);
    }

    public TagDTO toDTO(Tag tag) {
        if (tag == null) {
            return null;
        }
        return modelMapper.map(tag, TagDTO.class);
    }

    public List<TagDTO> toDTOList(List<Tag> tags) {
        if (tags == null) {
            return null;
        }
        return tags.stream().map(this::toDTO).collect(Collectors.toList());
    }
}
