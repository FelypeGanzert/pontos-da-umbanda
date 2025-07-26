package com.felypeganzert.backend.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.felypeganzert.backend.dto.TipoEntidadeDTO;
import com.felypeganzert.backend.entity.TipoEntidade;
import com.felypeganzert.backend.mapper.TipoEntidadeMapper;
import com.felypeganzert.backend.repository.TipoEntidadeRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class TipoEntidadeService {

    private final TipoEntidadeRepository repository;
    private final TipoEntidadeMapper mapper;

    @Transactional(readOnly = true)
    public List<TipoEntidadeDTO> findAllAtivos() {
        List<TipoEntidade> tiposEntidade = repository.findByAtivoTrue();
        return mapper.toDTOList(tiposEntidade);
    }
}
