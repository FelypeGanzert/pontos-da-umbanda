package com.felypeganzert.backend.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.felypeganzert.backend.dto.LinhaDTO;
import com.felypeganzert.backend.entity.Linha;
import com.felypeganzert.backend.mapper.LinhaMapper;
import com.felypeganzert.backend.repository.LinhaRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class LinhaService {

    private final LinhaRepository repository;
    private final LinhaMapper mapper;


    @Transactional(readOnly = true)
    public List<LinhaDTO> findAllAtivos() {
        log.debug("Buscando todas as linhas ativas");
        List<Linha> linhas = repository.findByAtivoTrue();
        log.debug("Encontradas {} linhas ativas", linhas.size());
        return mapper.toDTOList(linhas);
    }
}
