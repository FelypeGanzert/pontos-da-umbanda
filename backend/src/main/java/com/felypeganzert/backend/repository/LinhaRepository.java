package com.felypeganzert.backend.repository;

import com.felypeganzert.backend.entity.Linha;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LinhaRepository extends JpaRepository<Linha, Long> {

    List<Linha> findByAtivoTrue();
}
