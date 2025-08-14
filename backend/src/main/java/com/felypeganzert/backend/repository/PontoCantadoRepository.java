
package com.felypeganzert.backend.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.felypeganzert.backend.entity.PontoCantado;

@Repository
public interface PontoCantadoRepository extends JpaRepository<PontoCantado, Long> {
    List<PontoCantado> findByAtivoTrue();

    List<PontoCantado> findByTagsId(Long tagId);

    boolean existsByTagsId(Long tagId);
}
