package com.felypeganzert.backend.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.felypeganzert.backend.dto.PontoCantadoDTO;
import com.felypeganzert.backend.entity.PontoCantado;
import com.felypeganzert.backend.entity.Tag;
import com.felypeganzert.backend.exception.EntityNotFoundException;
import com.felypeganzert.backend.mapper.PontoCantadoMapper;
import com.felypeganzert.backend.repository.PontoCantadoRepository;
import com.felypeganzert.backend.repository.TagRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class PontoCantadoService {

    // TODO: melhorar método de criação (ter DTO específico para criar, definir valores padrões)
    // TODO: implementar validações que estão apenas no banco

    private final PontoCantadoRepository repository;
    private final TagRepository tagRepository;
    private final PontoCantadoMapper mapper;

    @Transactional(readOnly = true)
    public List<PontoCantadoDTO> findAllAtivos() {
        return mapper.toDTOList(repository.findByAtivoTrue());
    }

    @Transactional(readOnly = true)
    public PontoCantadoDTO findById(Long id) {
        return repository.findById(id)
                .map(mapper::toDTO)
                .orElseThrow(() -> new EntityNotFoundException("PontoCantado não encontrado para o id: " + id));
    }

    @Transactional
    public PontoCantadoDTO save(PontoCantadoDTO dto) {
        List<Tag> tags = new java.util.ArrayList<>();
        if (dto.getTags() != null && !dto.getTags().isEmpty()) {
            List<String> upperNomes = dto.getTags().stream()
                    .map(t -> t.getNome().trim().toUpperCase())
                    .distinct()
                    .collect(Collectors.toList());

            List<Tag> tagsExistentes = tagRepository.findByNomeIn(upperNomes);
            List<String> nomesExistentes = tagsExistentes.stream().map(Tag::getNome).toList();

            List<Tag> novasTags = upperNomes.stream()
                    .filter(nome -> !nomesExistentes.contains(nome))
                    .map(nome -> Tag.builder().nome(nome).build())
                    .toList();

            if (!novasTags.isEmpty()) {
                tagsExistentes = new java.util.ArrayList<>(tagsExistentes);
                tagsExistentes.addAll(tagRepository.saveAll(novasTags));
            }
            tags = tagsExistentes;
        }
        PontoCantado entity = mapper.toEntity(dto);
        entity.setTags(tags);
        PontoCantado saved = repository.save(entity);
        return mapper.toDTO(saved);
    }

    @Transactional
    public PontoCantadoDTO update(Long id, PontoCantadoDTO dto) {
        if (!repository.existsById(id)) {
            throw new EntityNotFoundException("PontoCantado não encontrado para o id: " + id);
        }
        dto.setId(id);
        return save(dto);
    }

    @Transactional
    public void delete(Long id) {
        repository.deleteById(id);
    }
}
