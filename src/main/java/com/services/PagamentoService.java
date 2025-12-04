package com.services;

import com.domains.ContaBancaria;
import com.domains.Lancamento;
import com.domains.Pagamento;
import com.domains.dtos.PagamentoDTO;
import com.mappers.PagamentoMapper;
import com.repositories.ContaBancariaRepository;
import com.repositories.LancamentoRepository;
import com.repositories.PagamentoRepository;
import com.services.exceptions.ObjectNotFoundException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
public class PagamentoService {

    private final PagamentoRepository pagamentoRepo;
    private final LancamentoRepository lancamentoRepo;
    private final ContaBancariaRepository contaRepo;

    public PagamentoService(PagamentoRepository pagamentoRepo,
                            LancamentoRepository lancamentoRepo,
                            ContaBancariaRepository contaRepo) {
        this.pagamentoRepo = pagamentoRepo;
        this.lancamentoRepo = lancamentoRepo;
        this.contaRepo = contaRepo;
    }


    private Lancamento resolveLancamento(Long lancamentoId) {
        if (lancamentoId == null) {
            return null;
        }

        return lancamentoRepo.findById(lancamentoId)
                .orElseThrow(() ->
                        new ObjectNotFoundException("Lançamento não encontrado: id=" + lancamentoId));
    }

    private ContaBancaria resolveConta(Long contaId) {
        if (contaId == null) {
            return null;
        }

        return contaRepo.findById(contaId)
                .orElseThrow(() ->
                        new ObjectNotFoundException("Conta bancária não encontrada: id=" + contaId));
    }

    /* =============== READ =============== */

    @Transactional(readOnly = true)
    public List<PagamentoDTO> findAll() {
        return PagamentoMapper.toDtoList(pagamentoRepo.findAll());
    }

    @Transactional(readOnly = true)
    public PagamentoDTO findById(Long id) {
        if (id == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Id do pagamento é obrigatório");
        }

        return pagamentoRepo.findById(id)
                .map(PagamentoMapper::toDto)
                .orElseThrow(() ->
                        new ObjectNotFoundException("Pagamento não encontrado: id=" + id));
    }


    @Transactional(readOnly = true)
    public List<PagamentoDTO> findByLancamento(Long lancamentoId) {
        if (lancamentoId == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Id do lançamento é obrigatório");
        }

        List<Pagamento> pagamentos = pagamentoRepo.findAll()
                .stream()
                .filter(p -> p.getLancamento() != null
                        && lancamentoId.equals(p.getLancamento().getIdLancamento()))
                .toList();

        return PagamentoMapper.toDtoList(pagamentos);
    }


    @Transactional
    public PagamentoDTO create(PagamentoDTO dto) {
        if (dto == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Dados do pagamento são obrigatórios");
        }

        dto.setIdPagamento(null);

        Pagamento entity;
        try {
            entity = PagamentoMapper.toEntity(
                    dto,
                    this::resolveLancamento,
                    this::resolveConta
            );
        } catch (IllegalArgumentException ex) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, ex.getMessage());
        }

        Pagamento saved = pagamentoRepo.save(entity);
        return PagamentoMapper.toDto(saved);
    }



    @Transactional
    public PagamentoDTO update(Long id, PagamentoDTO dto) {
        if (id == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Id do pagamento é obrigatório");
        }
        if (dto == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Dados do pagamento são obrigatórios");
        }

        Pagamento entity = pagamentoRepo.findById(id)
                .orElseThrow(() ->
                        new ObjectNotFoundException("Pagamento não encontrado: id=" + id));

        PagamentoMapper.copyToEntity(
                dto,
                entity,
                this::resolveLancamento,
                this::resolveConta
        );

        Pagamento updated = pagamentoRepo.save(entity);
        return PagamentoMapper.toDto(updated);
    }


    @Transactional
    public void delete(Long id) {
        if (id == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Id do pagamento é obrigatório");
        }

        Pagamento entity = pagamentoRepo.findById(id)
                .orElseThrow(() ->
                        new ObjectNotFoundException("Pagamento não encontrado: id=" + id));

        try {
            pagamentoRepo.delete(entity);
        } catch (org.springframework.dao.DataIntegrityViolationException ex) {
            throw new DataIntegrityViolationException(
                    "Pagamento está vinculado a outros registros e não pode ser removido: id=" + id,
                    ex
            );
        }
    }
}
