package com.felypeganzert.backend.service;

import com.felypeganzert.backend.dto.UsuarioDTO;
import com.felypeganzert.backend.entity.Usuario;
import com.felypeganzert.backend.exception.EntityNotFoundException;
import com.felypeganzert.backend.mapper.UsuarioMapper;
import com.felypeganzert.backend.repository.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class UsuarioService {
    private final UsuarioRepository repository;
    private final UsuarioMapper mapper;
    private final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    @Transactional(readOnly = true)
    public UsuarioDTO getAuthenticatedUser() {
        var email = SecurityContextHolder.getContext().getAuthentication().getName();
        return repository.findByEmail(email)
            .map(mapper::toDTO)
            .orElseThrow(() -> new EntityNotFoundException("Usuário não encontrado"));
    }

    @Transactional
    public UsuarioDTO updateAuthenticatedUser(UsuarioDTO dto) {
        var email = SecurityContextHolder.getContext().getAuthentication().getName();
        var usuario = repository.findByEmail(email)
            .orElseThrow(() -> new EntityNotFoundException("Usuário não encontrado"));

        // Verifica se o email novo já existe
        if (dto.getEmail() != null && !dto.getEmail().equals(email)) {
            if (repository.existsByEmail(dto.getEmail())) {
                throw new IllegalStateException("Email já está em uso: " + dto.getEmail());
            }
            usuario.setEmail(dto.getEmail());
        }

        // Atualiza apenas os campos permitidos
        usuario.setNome(dto.getNome());
        if (dto.getSenha() != null && !dto.getSenha().trim().isEmpty()) {
            usuario.setSenhaHash(passwordEncoder.encode(dto.getSenha()));
        }

        usuario.setDataAtualizacao(LocalDateTime.now());
        usuario = repository.save(usuario);
        return mapper.toDTO(usuario);
    }

    @Transactional
    public void deleteAuthenticatedUser() {
        var email = SecurityContextHolder.getContext().getAuthentication().getName();
        var usuario = repository.findByEmail(email)
            .orElseThrow(() -> new EntityNotFoundException("Usuário não encontrado"));
        
        repository.delete(usuario);
    }

    @Transactional
    public UsuarioDTO save(UsuarioDTO dto) {
        if (dto.getEmail() != null && repository.existsByEmail(dto.getEmail())) {
            throw new IllegalStateException("Email já está em uso: " + dto.getEmail());
        }
        
        Usuario entity = mapper.toEntity(dto);
        
        // Criptografar senha
        if (dto.getSenha() != null) {
            entity.setSenhaHash(passwordEncoder.encode(dto.getSenha()));
        }
        
        // Definir valores padrão se não fornecidos
        if (entity.getRole() == null) {
            entity.setRole("USER");
        }
        if (entity.getStatus() == null) {
            entity.setStatus("ATIVO");
        }
        if (entity.getEmailVerificado() == null) {
            entity.setEmailVerificado(false);
        }
        
        entity.setDataCriacao(LocalDateTime.now());
        entity.setDataAtualizacao(LocalDateTime.now());
        
        Usuario saved = repository.save(entity);
        return mapper.toDTO(saved);
    }

    @Transactional
    public UsuarioDTO update(Long id, UsuarioDTO dto) {
        if (!repository.existsById(id)) {
            throw new EntityNotFoundException("Usuario não encontrado para o id: " + id);
        }
        
        // Verificar se email já existe em outro usuário
        if (dto.getEmail() != null) {
            repository.findByEmail(dto.getEmail())
                    .filter(user -> !user.getId().equals(id))
                    .ifPresent(user -> {
                        throw new IllegalStateException("Email já está em uso: " + dto.getEmail());
                    });
        }
        
        dto.setId(id);
        
        Usuario entity = mapper.toEntity(dto);
        
        // Criptografar nova senha se fornecida
        if (dto.getSenha() != null && !dto.getSenha().trim().isEmpty()) {
            entity.setSenhaHash(passwordEncoder.encode(dto.getSenha()));
        } else {
            // Manter senha existente
            Usuario existing = repository.findById(id).orElseThrow();
            entity.setSenhaHash(existing.getSenhaHash());
        }
        
        entity.setDataAtualizacao(LocalDateTime.now());
        
        Usuario saved = repository.save(entity);
        return mapper.toDTO(saved);
    }

    @Transactional(readOnly = true)
    public Usuario getUsuarioLogado() {
        var email = SecurityContextHolder.getContext().getAuthentication().getName();
        return repository.findByEmail(email)
            .orElseThrow(() -> new EntityNotFoundException("Usuário não encontrado"));
    }

    @Transactional
    public void delete(Long id) {
        if (!repository.existsById(id)) {
            throw new EntityNotFoundException("Usuario não encontrado para o id: " + id);
        }
        repository.deleteById(id);
    }

    @Transactional
    public void inativar(Long id) {
        Usuario usuario = repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Usuario não encontrado para o id: " + id));
        usuario.setStatus("INATIVO");
        usuario.setDataAtualizacao(LocalDateTime.now());
        repository.save(usuario);
    }

    @Transactional
    public void ativar(Long id) {
        Usuario usuario = repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Usuario não encontrado para o id: " + id));
        usuario.setStatus("ATIVO");
        usuario.setDataAtualizacao(LocalDateTime.now());
        repository.save(usuario);
    }
}
