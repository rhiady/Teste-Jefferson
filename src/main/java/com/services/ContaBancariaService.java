package com.services;

import com.domains.ContaBancaria;
import com.domains.Usuario;
import com.domains.dtos.ContaBancariaDTO;
import com.mappers.ContaBancariaMapper;
import com.repositories.ContaBancariaRepository;
import com.repositories.UsuarioRepository;
import com.services.exceptions.ObjectNotFoundException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
public class ContaBancariaService {

    private final ContaBancariaRepository contaRepo;
    private final UsuarioRepository usuarioRepo;

    public ContaBancariaService(ContaBancariaRepository contaRepo,
                                UsuarioRepository usuarioRepo) {
        this.contaRepo = contaRepo;
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
    public List<ContaBancariaDTO> findAll() {
        return ContaBancariaMapper.toDtoList(contaRepo.findAll());
    }

    @Transactional(readOnly = true)
    public ContaBancariaDTO findById(Long id) {
        if (id == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Id da conta é obrigatório");
        }

        return contaRepo.findById(id)
                .map(ContaBancariaMapper::toDto)
                .orElseThrow(() ->
                        new ObjectNotFoundException("Conta bancária não encontrada: id=" + id));
    }


    @Transactional(readOnly = true)
    public List<ContaBancariaDTO> findByUsuario(Long idUsuario) {
        if (idUsuario == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Id do usuário é obrigatório");
        }


        List<ContaBancaria> contas = contaRepo.findAll()
                .stream()
                .filter(c -> c.getUsuario() != null
                        && idUsuario.equals(c.getUsuario().getIdUsuario()))
                .toList();

        return ContaBancariaMapper.toDtoList(contas);
    }


    @Transactional
    public ContaBancariaDTO create(ContaBancariaDTO dto) {
        if (dto == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Dados da conta bancária são obrigatórios");
        }


        dto.setIdContaBancaria(null);

        ContaBancaria entity;
        try {
            entity = ContaBancariaMapper.toEntity(dto, this::resolveUsuario);
        } catch (IllegalArgumentException ex) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, ex.getMessage());
        }

        ContaBancaria saved = contaRepo.save(entity);
        return ContaBancariaMapper.toDto(saved);
    }


    @Transactional
    public ContaBancariaDTO update(Long id, ContaBancariaDTO dto) {
        if (id == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Id da conta é obrigatório");
        }
        if (dto == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Dados da conta bancária são obrigatórios");
        }

        ContaBancaria entity = contaRepo.findById(id)
                .orElseThrow(() ->
                        new ObjectNotFoundException("Conta bancária não encontrada: id=" + id));

        ContaBancariaMapper.copyToEntity(dto, entity, this::resolveUsuario);

        ContaBancaria updated = contaRepo.save(entity);
        return ContaBancariaMapper.toDto(updated);
    }


    @Transactional
    public void delete(Long id) {
        if (id == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Id da conta é obrigatório");
        }

        ContaBancaria entity = contaRepo.findById(id)
                .orElseThrow(() ->
                        new ObjectNotFoundException("Conta bancária não encontrada: id=" + id));

        try {
            contaRepo.delete(entity);
        } catch (org.springframework.dao.DataIntegrityViolationException ex) {
            throw new DataIntegrityViolationException(
                    "Conta bancária possui lançamentos vinculados e não pode ser removida: id=" + id,
                    ex
            );
        }
    }
}
