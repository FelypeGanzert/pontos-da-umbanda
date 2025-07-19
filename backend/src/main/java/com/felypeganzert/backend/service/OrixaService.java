package com.felypeganzert.backend.service;

import com.felypeganzert.backend.dto.OrixaDTO;
import com.felypeganzert.backend.entity.Orixa;
import com.felypeganzert.backend.mapper.OrixaMapper;
import com.felypeganzert.backend.repository.OrixaRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class OrixaService {

    private final OrixaRepository orixaRepository;

    @Transactional(readOnly = true)
    public List<OrixaDTO> findAllAtivos() {
        List<Orixa> orixas = orixaRepository.findByAtivoTrue();
        return OrixaMapper.INSTANCE.toDTOList(orixas);
    }
}
