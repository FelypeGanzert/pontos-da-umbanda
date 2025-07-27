package com.felypeganzert.backend.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.felypeganzert.backend.entity.YoutubeUrl;

@Repository
public interface YoutubeUrlRepository extends JpaRepository<YoutubeUrl, Long> {
    List<YoutubeUrl> findByPontoCantadoId(Long pontoCantadoId);
}
