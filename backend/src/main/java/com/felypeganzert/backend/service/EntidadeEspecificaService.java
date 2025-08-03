package com.felypeganzert.backend.service;

import com.felypeganzert.backend.dto.EntidadeEspecificaDTO;
import com.felypeganzert.backend.entity.EntidadeEspecifica;
import com.felypeganzert.backend.mapper.EntidadeEspecificaMapper;
import com.felypeganzert.backend.repository.EntidadeEspecificaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class EntidadeEspecificaService {

    private final EntidadeEspecificaRepository repository;
    private final EntidadeEspecificaMapper mapper;

    @Transactional(readOnly = true)
    public List<EntidadeEspecificaDTO> findAllAtivos() {
        List<EntidadeEspecifica> entidades = repository.findByAtivoTrue();
        return mapper.toDTOList(entidades);
    }
}
