package com.felypeganzert.backend.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.felypeganzert.backend.dto.TagDTO;
import com.felypeganzert.backend.entity.Tag;
import com.felypeganzert.backend.exception.EntityNotFoundException;
import com.felypeganzert.backend.mapper.TagMapper;
import com.felypeganzert.backend.repository.PontoCantadoRepository;
import com.felypeganzert.backend.repository.TagRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class TagService {
    private final TagRepository repository;
    private final PontoCantadoRepository pontoCantadoRepository;
    private final TagMapper mapper;

    @Transactional(readOnly = true)
    public List<TagDTO> findAll() {
        return mapper.toDTOList(repository.findAll());
    }

    @Transactional(readOnly = true)
    public TagDTO findById(Long id) {
        return repository.findById(id)
                .map(mapper::toDTO)
                .orElseThrow(() -> new EntityNotFoundException("Tag não encontrada para o id: " + id));
    }

    @Transactional
    public TagDTO save(TagDTO dto) {
        Tag tag = mapper.toEntity(dto);
        Tag saved = repository.save(tag);
        return mapper.toDTO(saved);
    }

    @Transactional
    public TagDTO update(Long id, TagDTO dto) {
        if (!repository.existsById(id)) {
            throw new EntityNotFoundException("Tag não encontrada para o id: " + id);
        }
        dto.setId(id);
        return save(dto);
    }


    @Transactional
    public void delete(Long id) {
        if (pontoCantadoRepository.existsByTagsId(id)) {
            throw new IllegalStateException("Não é possível excluir a tag pois ela está sendo utilizada por um ou mais pontos cantados.");
        }
        repository.deleteById(id);
    }

}