package com.felypeganzert.backend.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.felypeganzert.backend.dto.YoutubeUrlDTO;
import com.felypeganzert.backend.entity.YoutubeUrl;
import com.felypeganzert.backend.mapper.YoutubeUrlMapper;
import com.felypeganzert.backend.repository.YoutubeUrlRepository;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class YoutubeUrlService {
    private final YoutubeUrlRepository repository;
    private final YoutubeUrlMapper mapper;

    @Transactional(readOnly = true)
    public List<YoutubeUrlDTO> findByPontoCantado(Long pontoCantadoId) {
        return mapper.toDTOList(repository.findByPontoCantadoId(pontoCantadoId));
    }

    @Transactional
    public YoutubeUrlDTO save(YoutubeUrlDTO dto) {
        YoutubeUrl entity = mapper.toEntity(dto);
        YoutubeUrl saved = repository.save(entity);
        return mapper.toDTO(saved);
    }

    @Transactional
    public YoutubeUrlDTO update(Long id, YoutubeUrlDTO dto) {
        if (!repository.existsById(id)) {
            throw new EntityNotFoundException("YoutubeUrl não encontrada para o id: " + id);
        }
        dto.setId(id);
        return save(dto);
    }

    @Transactional
    public void delete(Long id) {
        repository.deleteById(id);
    }
}
