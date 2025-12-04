package com.services;

import com.domains.CartaoCredito;
import com.domains.FaturaCartao;
import com.domains.dtos.FaturaCartaoDTO;
import com.mappers.FaturaCartaoMapper;
import com.repositories.CartaoCreditoRepository;
import com.repositories.FaturaCartaoRepository;
import com.services.exceptions.ObjectNotFoundException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
public class FaturaCartaoService {

    private final FaturaCartaoRepository faturaRepo;
    private final CartaoCreditoRepository cartaoRepo;

    public FaturaCartaoService(FaturaCartaoRepository faturaRepo,
                               CartaoCreditoRepository cartaoRepo) {
        this.faturaRepo = faturaRepo;
        this.cartaoRepo = cartaoRepo;
    }


    private CartaoCredito resolveCartao(Long cartaoId) {
        if (cartaoId == null) {
            return null;
        }

        return cartaoRepo.findById(cartaoId)
                .orElseThrow(() ->
                        new ObjectNotFoundException("Cartão de crédito não encontrado: id=" + cartaoId));
    }


    @Transactional(readOnly = true)
    public List<FaturaCartaoDTO> findAll() {
        return FaturaCartaoMapper.toDtoList(faturaRepo.findAll());
    }

    @Transactional(readOnly = true)
    public FaturaCartaoDTO findById(Long id) {
        if (id == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Id da fatura é obrigatório");
        }

        return faturaRepo.findById(id)
                .map(FaturaCartaoMapper::toDto)
                .orElseThrow(() ->
                        new ObjectNotFoundException("Fatura de cartão não encontrada: id=" + id));
    }


    @Transactional(readOnly = true)
    public List<FaturaCartaoDTO> findByCartao(Long idCartao) {
        if (idCartao == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Id do cartão é obrigatório");
        }

        List<FaturaCartao> faturas = faturaRepo.findAll()
                .stream()
                .filter(f -> f.getCartao() != null
                        && idCartao.equals(f.getCartao().getIdCartaoCredito()))
                .toList();

        return FaturaCartaoMapper.toDtoList(faturas);
    }


    @Transactional
    public FaturaCartaoDTO create(FaturaCartaoDTO dto) {
        if (dto == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Dados da fatura são obrigatórios");
        }

        dto.setIdFaturaCartao(null);

        FaturaCartao entity;
        try {
            entity = FaturaCartaoMapper.toEntity(dto, this::resolveCartao);
        } catch (IllegalArgumentException ex) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, ex.getMessage());
        }

        FaturaCartao saved = faturaRepo.save(entity);
        return FaturaCartaoMapper.toDto(saved);
    }


    @Transactional
    public FaturaCartaoDTO update(Long id, FaturaCartaoDTO dto) {
        if (id == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Id da fatura é obrigatório");
        }
        if (dto == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Dados da fatura são obrigatórios");
        }

        FaturaCartao entity = faturaRepo.findById(id)
                .orElseThrow(() ->
                        new ObjectNotFoundException("Fatura de cartão não encontrada: id=" + id));

        FaturaCartaoMapper.copyToEntity(dto, entity, this::resolveCartao);

        FaturaCartao updated = faturaRepo.save(entity);
        return FaturaCartaoMapper.toDto(updated);
    }



    @Transactional
    public void delete(Long id) {
        if (id == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Id da fatura é obrigatório");
        }

        FaturaCartao entity = faturaRepo.findById(id)
                .orElseThrow(() ->
                        new ObjectNotFoundException("Fatura de cartão não encontrada: id=" + id));

        try {
            faturaRepo.delete(entity);
        } catch (org.springframework.dao.DataIntegrityViolationException ex) {
            throw new DataIntegrityViolationException(
                    "Fatura possui lançamentos vinculados e não pode ser removida: id=" + id,
                    ex
            );
        }
    }
}
