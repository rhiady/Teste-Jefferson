package com.services;

import com.domains.*;
import com.domains.dtos.LancamentoDTO;
import com.mappers.LancamentoMapper;
import com.repositories.*;
import com.services.exceptions.ObjectNotFoundException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
public class LancamentoService {

    private final LancamentoRepository lancamentoRepo;
    private final UsuarioRepository usuarioRepo;
    private final EntidadeRepository entidadeRepo;
    private final CentroCustoRepository centroCustoRepo;
    private final ContaBancariaRepository contaRepo;
    private final CartaoCreditoRepository cartaoRepo;

    public LancamentoService(LancamentoRepository lancamentoRepo,
                             UsuarioRepository usuarioRepo,
                             EntidadeRepository entidadeRepo,
                             CentroCustoRepository centroCustoRepo,
                             ContaBancariaRepository contaRepo,
                             CartaoCreditoRepository cartaoRepo) {
        this.lancamentoRepo = lancamentoRepo;
        this.usuarioRepo = usuarioRepo;
        this.entidadeRepo = entidadeRepo;
        this.centroCustoRepo = centroCustoRepo;
        this.contaRepo = contaRepo;
        this.cartaoRepo = cartaoRepo;
    }


    private Usuario resolveUsuario(Long id) {
        if (id == null) return null;
        return usuarioRepo.findById(id)
                .orElseThrow(() ->
                        new ObjectNotFoundException("Usuário não encontrado: id=" + id));
    }

    private Entidade resolveEntidade(Long id) {
        if (id == null) return null;
        return entidadeRepo.findById(id)
                .orElseThrow(() ->
                        new ObjectNotFoundException("Entidade não encontrada: id=" + id));
    }

    private CentroCusto resolveCentro(Long id) {
        if (id == null) return null;
        return centroCustoRepo.findById(id)
                .orElseThrow(() ->
                        new ObjectNotFoundException("Centro de custo não encontrado: id=" + id));
    }

    private ContaBancaria resolveConta(Long id) {
        if (id == null) return null;
        return contaRepo.findById(id)
                .orElseThrow(() ->
                        new ObjectNotFoundException("Conta bancária não encontrada: id=" + id));
    }

    private CartaoCredito resolveCartao(Long id) {
        if (id == null) return null;
        return cartaoRepo.findById(id)
                .orElseThrow(() ->
                        new ObjectNotFoundException("Cartão de crédito não encontrado: id=" + id));
    }


    @Transactional(readOnly = true)
    public List<LancamentoDTO> findAll() {
        return LancamentoMapper.toDtoList(lancamentoRepo.findAll());
    }

    @Transactional(readOnly = true)
    public LancamentoDTO findById(Long id) {
        if (id == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Id do lançamento é obrigatório");
        }

        return lancamentoRepo.findById(id)
                .map(LancamentoMapper::toDto)
                .orElseThrow(() ->
                        new ObjectNotFoundException("Lançamento não encontrado: id=" + id));
    }


    @Transactional
    public LancamentoDTO create(LancamentoDTO dto) {
        if (dto == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Dados do lançamento são obrigatórios");
        }

        dto.setIdLancamento(null);

        Lancamento entity;
        try {
            entity = LancamentoMapper.toEntity(
                    dto,
                    this::resolveUsuario,
                    this::resolveEntidade,
                    this::resolveCentro,
                    this::resolveConta,
                    this::resolveCartao
            );
        } catch (IllegalArgumentException ex) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, ex.getMessage());
        }

        Lancamento saved = lancamentoRepo.save(entity);
        return LancamentoMapper.toDto(saved);
    }


    @Transactional
    public LancamentoDTO update(Long id, LancamentoDTO dto) {
        if (id == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Id do lançamento é obrigatório");
        }
        if (dto == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Dados do lançamento são obrigatórios");
        }

        Lancamento entity = lancamentoRepo.findById(id)
                .orElseThrow(() ->
                        new ObjectNotFoundException("Lançamento não encontrado: id=" + id));

        LancamentoMapper.copyToEntity(
                dto,
                entity,
                this::resolveUsuario,
                this::resolveEntidade,
                this::resolveCentro,
                this::resolveConta,
                this::resolveCartao
        );

        Lancamento updated = lancamentoRepo.save(entity);
        return LancamentoMapper.toDto(updated);
    }


    @Transactional
    public void delete(Long id) {
        if (id == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Id do lançamento é obrigatório");
        }

        Lancamento entity = lancamentoRepo.findById(id)
                .orElseThrow(() ->
                        new ObjectNotFoundException("Lançamento não encontrado: id=" + id));

        try {
            lancamentoRepo.delete(entity);
        } catch (org.springframework.dao.DataIntegrityViolationException ex) {
            throw new DataIntegrityViolationException(
                    "Lançamento está vinculado a outros registros e não pode ser removido: id=" + id,
                    ex
            );
        }
    }
}
