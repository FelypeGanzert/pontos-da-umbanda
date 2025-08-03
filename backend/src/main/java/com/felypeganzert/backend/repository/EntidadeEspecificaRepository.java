package com.felypeganzert.backend.repository;

import com.felypeganzert.backend.entity.EntidadeEspecifica;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EntidadeEspecificaRepository extends JpaRepository<EntidadeEspecifica, Long> {
    List<EntidadeEspecifica> findByAtivoTrue();
}
