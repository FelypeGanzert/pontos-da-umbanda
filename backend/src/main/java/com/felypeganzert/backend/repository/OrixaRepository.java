package com.felypeganzert.backend.repository;

import com.felypeganzert.backend.entity.Orixa;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrixaRepository extends JpaRepository<Orixa, Long> {

    List<Orixa> findByAtivoTrue();
}
