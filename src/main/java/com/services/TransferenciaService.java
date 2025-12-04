package com.services;

import com.domains.ContaBancaria;
import com.domains.Transferencia;
import com.domains.Usuario;
import com.domains.dtos.TransferenciaDTO;
import com.mappers.TransferenciaMapper;
import com.repositories.ContaBancariaRepository;
import com.repositories.TransferenciaRepository;
import com.repositories.UsuarioRepository;
import com.services.exceptions.ObjectNotFoundException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDate;
import java.util.List;

@Service
public class TransferenciaService {

    private final TransferenciaRepository transferenciaRepo;
    private final UsuarioRepository usuarioRepo;
    private final ContaBancariaRepository contaRepo;

    public TransferenciaService(TransferenciaRepository transferenciaRepo,
                                UsuarioRepository usuarioRepo,
                                ContaBancariaRepository contaRepo) {
        this.transferenciaRepo = transferenciaRepo;
        this.usuarioRepo = usuarioRepo;
        this.contaRepo = contaRepo;
    }


    private Usuario resolveUsuario(Long usuarioId) {
        if (usuarioId == null) return null;

        return usuarioRepo.findById(usuarioId)
                .orElseThrow(() ->
                        new ObjectNotFoundException("Usuário não encontrado: id=" + usuarioId));
    }

    private ContaBancaria resolveConta(Long contaId) {
        if (contaId == null) return null;

        return contaRepo.findById(contaId)
                .orElseThrow(() ->
                        new ObjectNotFoundException("Conta bancária não encontrada: id=" + contaId));
    }

    /* =============== READ =============== */

    @Transactional(readOnly = true)
    public List<TransferenciaDTO> findAll() {
        return TransferenciaMapper.toDtoList(transferenciaRepo.findAll());
    }

    @Transactional(readOnly = true)
    public TransferenciaDTO findById(Long id) {
        if (id == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Id da transferência é obrigatório");
        }

        return transferenciaRepo.findById(id)
                .map(TransferenciaMapper::toDto)
                .orElseThrow(() ->
                        new ObjectNotFoundException("Transferência não encontrada: id=" + id));
    }


    @Transactional(readOnly = true)
    public List<TransferenciaDTO> findByUsuario(Long usuarioId) {
        if (usuarioId == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Id do usuário é obrigatório");
        }

        List<Transferencia> list = transferenciaRepo.findAll()
                .stream()
                .filter(t -> t.getUsuario() != null
                        && usuarioId.equals(t.getUsuario().getIdUsuario()))
                .toList();

        return TransferenciaMapper.toDtoList(list);
    }



    @Transactional
    public TransferenciaDTO create(TransferenciaDTO dto) {
        if (dto == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Dados da transferência são obrigatórios");
        }

        dto.setIdTransferencia(null); // inclusão

        Transferencia entity;
        try {
            entity = TransferenciaMapper.toEntity(
                    dto,
                    this::resolveUsuario,
                    this::resolveConta
            );
        } catch (IllegalArgumentException ex) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, ex.getMessage());
        }


        if (entity.getData() == null) {
            entity.setData(LocalDate.now());
        }

        Transferencia saved = transferenciaRepo.save(entity);
        return TransferenciaMapper.toDto(saved);
    }



    @Transactional
    public TransferenciaDTO update(Long id, TransferenciaDTO dto) {
        if (id == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Id da transferência é obrigatório");
        }
        if (dto == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Dados da transferência são obrigatórios");
        }

        Transferencia entity = transferenciaRepo.findById(id)
                .orElseThrow(() ->
                        new ObjectNotFoundException("Transferência não encontrada: id=" + id));

        TransferenciaMapper.copyToEntity(
                dto,
                entity,
                this::resolveUsuario,
                this::resolveConta
        );


        Transferencia updated = transferenciaRepo.save(entity);
        return TransferenciaMapper.toDto(updated);
    }


    @Transactional
    public void delete(Long id) {
        if (id == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Id da transferência é obrigatório");
        }

        Transferencia entity = transferenciaRepo.findById(id)
                .orElseThrow(() ->
                        new ObjectNotFoundException("Transferência não encontrada: id=" + id));

        try {
            transferenciaRepo.delete(entity);
        } catch (org.springframework.dao.DataIntegrityViolationException ex) {
            throw new DataIntegrityViolationException(
                    "Transferência está vinculada a outros registros e não pode ser removida: id=" + id,
                    ex
            );
        }
    }
}
