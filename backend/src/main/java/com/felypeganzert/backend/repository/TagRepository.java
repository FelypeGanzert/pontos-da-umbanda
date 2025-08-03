package com.felypeganzert.backend.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.felypeganzert.backend.entity.Tag;

@Repository
public interface TagRepository extends JpaRepository<Tag, Long> {
    List<Tag> findByNomeIn(List<String> nomes);
}
