package com.services;

import com.domains.Entidade;
import com.domains.Usuario;
import com.domains.dtos.EntidadeDTO;
import com.mappers.EntidadeMapper;
import com.repositories.EntidadeRepository;
import com.repositories.UsuarioRepository;
import com.services.exceptions.DataIntegrityViolationException;
import com.services.exceptions.ObjectNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
public class EntidadeService {

    private final EntidadeRepository entidadeRepo;
    private final UsuarioRepository usuarioRepo;

    public EntidadeService(EntidadeRepository entidadeRepo,
                           UsuarioRepository usuarioRepo) {
        this.entidadeRepo = entidadeRepo;
        this.usuarioRepo = usuarioRepo;
    }



    private Usuario resolveUsuario(Long usuarioId) {
        if (usuarioId == null) {
            return null;
        }

        return usuarioRepo.findById(usuarioId)
                .orElseThrow(() ->
                        new ObjectNotFoundException("Usuário não encontrado: id=" + usuarioId));
    }


    @Transactional(readOnly = true)
    public List<EntidadeDTO> findAll() {
        return EntidadeMapper.toDtoList(entidadeRepo.findAll());
    }

    @Transactional(readOnly = true)
    public EntidadeDTO findById(Long id) {
        if (id == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Id da entidade é obrigatório");
        }

        return entidadeRepo.findById(id)
                .map(EntidadeMapper::toDto)
                .orElseThrow(() ->
                        new ObjectNotFoundException("Entidade não encontrada: id=" + id));
    }


    @Transactional(readOnly = true)
    public List<EntidadeDTO> findByUsuario(Long idUsuario) {
        if (idUsuario == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Id do usuário é obrigatório");
        }


        List<Entidade> entidades = entidadeRepo.findAll()
                .stream()
                .filter(e -> e.getUsuario() != null
                        && idUsuario.equals(e.getUsuario().getIdUsuario()))
                .toList();

        return EntidadeMapper.toDtoList(entidades);
    }



    @Transactional
    public EntidadeDTO create(EntidadeDTO dto) {
        if (dto == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Dados da entidade são obrigatórios");
        }

        dto.setIdEntidade(null);

        Entidade entity;
        try {
            entity = EntidadeMapper.toEntity(dto, this::resolveUsuario);
        } catch (IllegalArgumentException ex) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, ex.getMessage());
        }

        Entidade saved = entidadeRepo.save(entity);
        return EntidadeMapper.toDto(saved);
    }


    @Transactional
    public EntidadeDTO update(Long id, EntidadeDTO dto) {
        if (id == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Id da entidade é obrigatório");
        }
        if (dto == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Dados da entidade são obrigatórios");
        }

        Entidade entity = entidadeRepo.findById(id)
                .orElseThrow(() ->
                        new ObjectNotFoundException("Entidade não encontrada: id=" + id));

        EntidadeMapper.copyToEntity(dto, entity, this::resolveUsuario);

        Entidade updated = entidadeRepo.save(entity);
        return EntidadeMapper.toDto(updated);
    }


    @Transactional
    public void delete(Long id) {
        if (id == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Id da entidade é obrigatório");
        }

        Entidade entity = entidadeRepo.findById(id)
                .orElseThrow(() ->
                        new ObjectNotFoundException("Entidade não encontrada: id=" + id));

        try {
            entidadeRepo.delete(entity);
        } catch (org.springframework.dao.DataIntegrityViolationException ex) {
            throw new DataIntegrityViolationException(
                    "Entidade possui lançamentos vinculados e não pode ser removida: id=" + id,
                    ex
            );
        }
    }
}
