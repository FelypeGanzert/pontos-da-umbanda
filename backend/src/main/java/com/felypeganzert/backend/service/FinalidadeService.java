package com.felypeganzert.backend.service;

import com.felypeganzert.backend.dto.FinalidadeDTO;
import com.felypeganzert.backend.entity.Finalidade;
import com.felypeganzert.backend.mapper.FinalidadeMapper;
import com.felypeganzert.backend.repository.FinalidadeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class FinalidadeService {
    private final FinalidadeRepository repository;
    private final FinalidadeMapper mapper;

    @Transactional(readOnly = true)
    public List<FinalidadeDTO> findAllAtivos() {
        List<Finalidade> finalidades = repository.findByAtivoTrue();
        return mapper.toDTOList(finalidades);
    }
}
