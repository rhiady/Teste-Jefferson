package com.services;

import com.domains.ContaBancaria;
import com.domains.Lancamento;
import com.domains.Recebimento;
import com.domains.dtos.RecebimentoDTO;
import com.mappers.RecebimentoMapper;
import com.repositories.ContaBancariaRepository;
import com.repositories.LancamentoRepository;
import com.repositories.RecebimentoRepository;
import com.services.exceptions.ObjectNotFoundException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
public class RecebimentoService {

    private final RecebimentoRepository recebimentoRepo;
    private final LancamentoRepository lancamentoRepo;
    private final ContaBancariaRepository contaRepo;

    public RecebimentoService(RecebimentoRepository recebimentoRepo,
                              LancamentoRepository lancamentoRepo,
                              ContaBancariaRepository contaRepo) {
        this.recebimentoRepo = recebimentoRepo;
        this.lancamentoRepo = lancamentoRepo;
        this.contaRepo = contaRepo;
    }


    private Lancamento resolveLancamento(Long id) {
        if (id == null) return null;

        return lancamentoRepo.findById(id)
                .orElseThrow(() ->
                        new ObjectNotFoundException("Lançamento não encontrado: id=" + id));
    }

    private ContaBancaria resolveConta(Long id) {
        if (id == null) return null;

        return contaRepo.findById(id)
                .orElseThrow(() ->
                        new ObjectNotFoundException("Conta bancária não encontrada: id=" + id));
    }


    @Transactional(readOnly = true)
    public List<RecebimentoDTO> findAll() {
        return RecebimentoMapper.toDtoList(recebimentoRepo.findAll());
    }

    @Transactional(readOnly = true)
    public RecebimentoDTO findById(Long id) {
        if (id == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Id do recebimento é obrigatório");
        }

        return recebimentoRepo.findById(id)
                .map(RecebimentoMapper::toDto)
                .orElseThrow(() ->
                        new ObjectNotFoundException("Recebimento não encontrado: id=" + id));
    }


    @Transactional(readOnly = true)
    public List<RecebimentoDTO> findByLancamento(Long lancamentoId) {
        if (lancamentoId == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Id do lançamento é obrigatório");
        }

        List<Recebimento> list = recebimentoRepo.findAll()
                .stream()
                .filter(r -> r.getLancamento() != null &&
                        lancamentoId.equals(r.getLancamento().getIdLancamento()))
                .toList();

        return RecebimentoMapper.toDtoList(list);
    }



    @Transactional
    public RecebimentoDTO create(RecebimentoDTO dto) {
        if (dto == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Dados do recebimento são obrigatórios");
        }

        dto.setIdRecebimento(null);

        Recebimento entity;
        try {
            entity = RecebimentoMapper.toEntity(
                    dto,
                    this::resolveLancamento,
                    this::resolveConta
            );
        } catch (IllegalArgumentException ex) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, ex.getMessage());
        }

        Recebimento saved = recebimentoRepo.save(entity);
        return RecebimentoMapper.toDto(saved);
    }


    @Transactional
    public RecebimentoDTO update(Long id, RecebimentoDTO dto) {
        if (id == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Id do recebimento é obrigatório");
        }
        if (dto == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Dados do recebimento são obrigatórios");
        }

        Recebimento entity = recebimentoRepo.findById(id)
                .orElseThrow(() ->
                        new ObjectNotFoundException("Recebimento não encontrado: id=" + id));

        RecebimentoMapper.copyToEntity(
                dto,
                entity,
                this::resolveLancamento,
                this::resolveConta
        );

        Recebimento updated = recebimentoRepo.save(entity);
        return RecebimentoMapper.toDto(updated);
    }


    @Transactional
    public void delete(Long id) {
        if (id == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Id do recebimento é obrigatório");
        }

        Recebimento entity = recebimentoRepo.findById(id)
                .orElseThrow(() ->
                        new ObjectNotFoundException("Recebimento não encontrado: id=" + id));

        try {
            recebimentoRepo.delete(entity);
        } catch (org.springframework.dao.DataIntegrityViolationException ex) {
            throw new DataIntegrityViolationException(
                    "Recebimento não pode ser removido pois está vinculado a outros registros: id=" + id,
                    ex
            );
        }
    }
}
