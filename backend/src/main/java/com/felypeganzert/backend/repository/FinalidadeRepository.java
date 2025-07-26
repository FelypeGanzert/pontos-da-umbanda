package com.felypeganzert.backend.repository;

import com.felypeganzert.backend.entity.Finalidade;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface FinalidadeRepository extends JpaRepository<Finalidade, Long> {
    List<Finalidade> findByAtivoTrue();
}
