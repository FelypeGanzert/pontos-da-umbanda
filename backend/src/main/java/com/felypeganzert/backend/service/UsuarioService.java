package com.felypeganzert.backend.service;

import com.felypeganzert.backend.dto.UsuarioDTO;
import com.felypeganzert.backend.entity.Usuario;
import com.felypeganzert.backend.exception.EntityNotFoundException;
import com.felypeganzert.backend.mapper.UsuarioMapper;
import com.felypeganzert.backend.repository.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class UsuarioService {
    private final UsuarioRepository repository;
    private final UsuarioMapper mapper;
    private final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    @Transactional(readOnly = true)
    public List<UsuarioDTO> findAllAtivos() {
        return mapper.toDTOList(repository.findByStatus("ATIVO"));
    }

    @Transactional(readOnly = true)
    public UsuarioDTO findById(Long id) {
        return repository.findById(id)
                .map(mapper::toDTO)
                .orElseThrow(() -> new EntityNotFoundException("Usuario não encontrado para o id: " + id));
    }

    @Transactional(readOnly = true)
    public UsuarioDTO findByEmail(String email) {
        return repository.findByEmail(email)
                .map(mapper::toDTO)
                .orElseThrow(() -> new EntityNotFoundException("Usuario não encontrado para o email: " + email));
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
        if (entity.getRole() == null) entity.setRole("USER");
        if (entity.getStatus() == null) entity.setStatus("ATIVO");
        if (entity.getEmailVerificado() == null) entity.setEmailVerificado(false);
        
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
