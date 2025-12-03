package com.mappers;

import com.domains.ContaBancaria;
import com.domains.Lancamento;
import com.domains.Pagamento;
import com.domains.dtos.PagamentoDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;

import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.function.Function;
import java.util.stream.Collectors;

public final class PagamentoMapper {

    private PagamentoMapper() {
    }

    public static PagamentoDTO toDto(Pagamento e) {
        if (e == null) return null;

        Long idLancamento = (e.getLancamento() == null) ? null : e.getLancamento().getIdLancamento();
        String descricaoLancamento = (e.getLancamento() == null) ? null : e.getLancamento().getDescricao();

        Long idContaOrigem = (e.getContaOrigem() == null) ? null : e.getContaOrigem().getIdContaBancaria();
        String contaOrigemApelido = (e.getContaOrigem() == null) ? null : e.getContaOrigem().getApelido();

        return new PagamentoDTO(
                e.getIdPagamento(),
                idLancamento,
                descricaoLancamento,
                e.getDataPagamento(),
                e.getValorPago(),
                idContaOrigem,
                contaOrigemApelido,
                e.getObservacao()
        );
    }

    public static List<PagamentoDTO> toDtoList(Collection<Pagamento> entities) {
        if (entities == null) return List.of();
        return entities.stream()
                .filter(Objects::nonNull)
                .map(PagamentoMapper::toDto)
                .collect(Collectors.toList());
    }

    public static Page<PagamentoDTO> toDtoPage(Page<Pagamento> page) {
        List<PagamentoDTO> content = toDtoList(page.getContent());
        return new PageImpl<>(content, page.getPageable(), page.getTotalElements());
    }

    public static Pagamento toEntity(PagamentoDTO dto,
                                     Lancamento lancamento,
                                     ContaBancaria contaOrigem) {
        if (dto == null) return null;

        Pagamento e = new Pagamento();

        e.setIdPagamento(dto.getIdPagamento());
        e.setLancamento(lancamento);
        e.setDataPagamento(dto.getDataPagamento());
        e.setValorPago(dto.getValorPago());
        e.setContaOrigem(contaOrigem);
        e.setObservacao(trim(dto.getObservacao()));

        return e;
    }

    public static Pagamento toEntity(PagamentoDTO dto,
                                     Function<Long, Lancamento> lancamentoResolver,
                                     Function<Long, ContaBancaria> contaResolver) {
        if (dto == null) return null;

        Lancamento lancamento = (dto.getLancamentoId() == null) ? null : lancamentoResolver.apply(dto.getLancamentoId());
        ContaBancaria contaOrigem = (dto.getContaOrigemId() == null) ? null : contaResolver.apply(dto.getContaOrigemId());

        return toEntity(dto, lancamento, contaOrigem);
    }

    public static void copyToEntity(PagamentoDTO dto,
                                    Pagamento target,
                                    Lancamento lancamento,
                                    ContaBancaria contaOrigem) {
        if (dto == null || target == null) return;

        target.setLancamento(lancamento);
        target.setDataPagamento(dto.getDataPagamento());
        target.setValorPago(dto.getValorPago());
        target.setContaOrigem(contaOrigem);
        target.setObservacao(trim(dto.getObservacao()));
    }

    public static void copyToEntity(PagamentoDTO dto, Pagamento target, Function<Long, Lancamento> lancamentoResolver, Function<Long, ContaBancaria> contaResolver) {
        if (dto == null || target == null) return;

        Lancamento lancamento = (dto.getLancamentoId() == null) ? null : lancamentoResolver.apply(dto.getIdPagamento());
        ContaBancaria contaOrigem = (dto.getContaOrigemId() == null) ? null : contaResolver.apply(dto.getIdPagamento());

        copyToEntity(dto, target, lancamento, contaOrigem);
    }

    private static String trim(String s) {
        return (s == null) ? null : s.trim();
    }
}
