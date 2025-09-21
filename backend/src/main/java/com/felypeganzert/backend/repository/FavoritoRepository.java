package com.felypeganzert.backend.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.felypeganzert.backend.entity.Favorito;
import com.felypeganzert.backend.entity.Usuario;

@Repository
public interface FavoritoRepository extends JpaRepository<Favorito, Long> {
    
    List<Favorito> findByUsuario(Usuario usuario);
    
    Optional<Favorito> findByUsuarioAndPontoCantadoId(Usuario usuario, Long pontoCantadoId);
    
    void deleteByUsuarioAndPontoCantadoId(Usuario usuario, Long pontoCantadoId);
    
    long countByUsuario(Usuario usuario);
}