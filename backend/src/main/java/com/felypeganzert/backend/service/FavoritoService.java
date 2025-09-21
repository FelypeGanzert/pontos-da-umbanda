package com.felypeganzert.backend.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.felypeganzert.backend.dto.FavoritoDTO;
import com.felypeganzert.backend.entity.Favorito;
import com.felypeganzert.backend.entity.PontoCantado;
import com.felypeganzert.backend.entity.Usuario;
import com.felypeganzert.backend.exception.NotFoundException;
import com.felypeganzert.backend.mapper.FavoritoMapper;
import com.felypeganzert.backend.repository.FavoritoRepository;
import com.felypeganzert.backend.repository.PontoCantadoRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class FavoritoService {
    
    private final FavoritoRepository repository;
    private final PontoCantadoRepository pontoCantadoRepository;
    private final UsuarioService usuarioService;
    private final FavoritoMapper mapper;

    @Transactional(readOnly = true)
    public List<FavoritoDTO> findMeusFavoritos() {
        log.debug("Buscando favoritos do usuário logado");
        Usuario usuario = usuarioService.getUsuarioLogado();
        List<Favorito> favoritos = repository.findByUsuario(usuario);
        return mapper.toDTOList(favoritos);
    }

    @Transactional
    public void adicionar(Long pontoId) {
        log.debug("Adicionando ponto cantado {} aos favoritos", pontoId);
        Usuario usuario = usuarioService.getUsuarioLogado();
        PontoCantado ponto = pontoCantadoRepository.findById(pontoId)
            .orElseThrow(() -> new NotFoundException("Ponto cantado não encontrado"));

        repository.findByUsuarioAndPontoCantadoId(usuario, pontoId)
            .ifPresent(f -> {
                throw new IllegalStateException("Ponto cantado já está nos favoritos");
            });

        Favorito favorito = Favorito.builder()
            .usuario(usuario)
            .pontoCantado(ponto)
            .build();

        repository.save(favorito);
    }

    @Transactional
    public void remover(Long pontoId) {
        log.debug("Removendo ponto cantado {} dos favoritos", pontoId);
        Usuario usuario = usuarioService.getUsuarioLogado();
        repository.deleteByUsuarioAndPontoCantadoId(usuario, pontoId);
    }

    @Transactional(readOnly = true)
    public long count() {
        log.debug("Contando favoritos do usuário logado");
        Usuario usuario = usuarioService.getUsuarioLogado();
        return repository.countByUsuario(usuario);
    }
}