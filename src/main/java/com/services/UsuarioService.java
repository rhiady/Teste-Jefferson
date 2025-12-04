package com.services;

import com.domains.Usuario;
import com.domains.dtos.UsuarioDTO;
import com.mappers.UsuarioMapper;
import com.repositories.UsuarioRepository;
import com.services.exceptions.ObjectNotFoundException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
public class UsuarioService {

    private final UsuarioRepository usuarioRepo;

    public UsuarioService(UsuarioRepository usuarioRepo) {
        this.usuarioRepo = usuarioRepo;
    }


    @Transactional(readOnly = true)
    public List<UsuarioDTO> findAll() {
        return UsuarioMapper.toDtoList(usuarioRepo.findAll());
    }

    @Transactional(readOnly = true)
    public UsuarioDTO findById(Long id) {
        if (id == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Id é obrigatório");
        }

        return usuarioRepo.findById(id)
                .map(UsuarioMapper::toDto)
                .orElseThrow(() ->
                        new ObjectNotFoundException("Usuário não encontrado: id=" + id));
    }

    @Transactional(readOnly = true)
    public UsuarioDTO findByEmail(String email) {
        if (email == null || email.isBlank()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "E-mail do usuário é obrigatório");
        }

        String normalizedEmail = email.trim().toLowerCase();

        return usuarioRepo.findByEmail(normalizedEmail)
                .map(UsuarioMapper::toDto)
                .orElseThrow(() ->
                        new ObjectNotFoundException("Usuário não encontrado: email=" + normalizedEmail));
    }


    @Transactional
    public UsuarioDTO create(UsuarioDTO dto) {
        if (dto == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Dados do usuário são obrigatórios");
        }

        dto.setIdUsuario(null);

        Usuario usuario;
        try {
            usuario = UsuarioMapper.toEntity(dto);
        } catch (IllegalArgumentException ex) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, ex.getMessage());
        }


        return UsuarioMapper.toDto(usuarioRepo.save(usuario));
    }

    @Transactional
    public UsuarioDTO update(Long id, UsuarioDTO dto) {
        if (id == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Id é obrigatório");
        }
        if (dto == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Dados do usuário são obrigatórios");
        }

        Usuario usuario = usuarioRepo.findById(id)
                .orElseThrow(() ->
                        new ObjectNotFoundException("Usuário não encontrado: id=" + id));


        if (dto.getEmail() != null) {
            String novoEmail = dto.getEmail().trim().toLowerCase();
            usuarioRepo.findByEmail(novoEmail)
                    .filter(outro -> !outro.getIdUsuario().equals(id))
                    .ifPresent(outro -> {
                        throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "E-mail já cadastrado para outro usuário");
                    });
        }

        UsuarioMapper.copyToEntity(dto, usuario);

        return UsuarioMapper.toDto(usuarioRepo.save(usuario));
    }


    @Transactional
    public void delete(Long id) {
        if (id == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Id é obrigatório");
        }

        Usuario usuario = usuarioRepo.findById(id)
                .orElseThrow(() ->
                        new ObjectNotFoundException("Usuário não encontrado: id=" + id));

        try {
            usuarioRepo.delete(usuario);
        } catch (DataIntegrityViolationException ex) {
            throw new DataIntegrityViolationException(
                    "Usuário possui vínculos financeiros e não pode ser removido: id=" + id
            );
        }
    }
}
