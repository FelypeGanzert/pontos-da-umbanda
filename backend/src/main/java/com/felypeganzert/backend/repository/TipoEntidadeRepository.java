package com.felypeganzert.backend.repository;

import com.felypeganzert.backend.entity.TipoEntidade;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TipoEntidadeRepository extends JpaRepository<TipoEntidade, Long> {

    List<TipoEntidade> findByAtivoTrue();
}
