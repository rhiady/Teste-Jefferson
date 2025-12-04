package com.services;

import com.domains.ContaBancaria;
import com.domains.MovimentoConta;
import com.domains.dtos.MovimentoContaDTO;
import com.mappers.MovimentoContaMapper;
import com.repositories.ContaBancariaRepository;
import com.repositories.MovimentoContaRepository;
import com.services.exceptions.ObjectNotFoundException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
public class MovimentoContaService {

    private final MovimentoContaRepository movimentoRepo;
    private final ContaBancariaRepository contaRepo;

    public MovimentoContaService(MovimentoContaRepository movimentoRepo,
                                 ContaBancariaRepository contaRepo) {
        this.movimentoRepo = movimentoRepo;
        this.contaRepo = contaRepo;
    }


    private ContaBancaria resolveConta(Long contaId) {
        if (contaId == null) {
            return null;
        }

        return contaRepo.findById(contaId)
                .orElseThrow(() ->
                        new ObjectNotFoundException("Conta bancária não encontrada: id=" + contaId));
    }



    @Transactional(readOnly = true)
    public List<MovimentoContaDTO> findAll() {
        return MovimentoContaMapper.toDtoList(movimentoRepo.findAll());
    }

    @Transactional(readOnly = true)
    public MovimentoContaDTO findById(Long id) {
        if (id == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Id do movimento é obrigatório");
        }

        return movimentoRepo.findById(id)
                .map(MovimentoContaMapper::toDto)
                .orElseThrow(() ->
                        new ObjectNotFoundException("Movimento de conta não encontrado: id=" + id));
    }


    @Transactional(readOnly = true)
    public List<MovimentoContaDTO> findByConta(Long contaId) {
        if (contaId == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Id da conta é obrigatório");
        }


        List<MovimentoConta> movimentos = movimentoRepo.findAll()
                .stream()
                .filter(m -> m.getConta() != null
                        && contaId.equals(m.getConta().getIdContaBancaria()))
                .toList();

        return MovimentoContaMapper.toDtoList(movimentos);
    }


    @Transactional
    public MovimentoContaDTO create(MovimentoContaDTO dto) {
        if (dto == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Dados do movimento são obrigatórios");
        }

        dto.setIdMovimentoConta(null);

        MovimentoConta entity;
        try {
            entity = MovimentoContaMapper.toEntity(dto, this::resolveConta);
        } catch (IllegalArgumentException ex) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, ex.getMessage());
        }

        MovimentoConta saved = movimentoRepo.save(entity);
        return MovimentoContaMapper.toDto(saved);
    }



    @Transactional
    public MovimentoContaDTO update(Long id, MovimentoContaDTO dto) {
        if (id == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Id do movimento é obrigatório");
        }
        if (dto == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Dados do movimento são obrigatórios");
        }

        MovimentoConta entity = movimentoRepo.findById(id)
                .orElseThrow(() ->
                        new ObjectNotFoundException("Movimento de conta não encontrado: id=" + id));

        MovimentoContaMapper.copyToEntity(dto, entity, this::resolveConta);

        MovimentoConta updated = movimentoRepo.save(entity);
        return MovimentoContaMapper.toDto(updated);
    }


    @Transactional
    public void delete(Long id) {
        if (id == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Id do movimento é obrigatório");
        }

        MovimentoConta entity = movimentoRepo.findById(id)
                .orElseThrow(() ->
                        new ObjectNotFoundException("Movimento de conta não encontrado: id=" + id));

        try {
            movimentoRepo.delete(entity);
        } catch (org.springframework.dao.DataIntegrityViolationException ex) {
            throw new DataIntegrityViolationException(
                    "Movimento está vinculado a outros registros e não pode ser removido: id=" + id,
                    ex
            );
        }
    }
}
