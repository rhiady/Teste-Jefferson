package com.mappers;

import com.domains.ContaBancaria;
import com.domains.Lancamento;
import com.domains.Recebimento;
import com.domains.dtos.RecebimentoDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;

import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.function.Function;
import java.util.stream.Collectors;

public final class RecebimentoMapper {

    private RecebimentoMapper() {}


    public static RecebimentoDTO toDto(Recebimento e) {
        if (e == null) return null;

        Long lancamentoId = (e.getLancamento() == null) ? null : e.getLancamento().getIdLancamento();
        String lancamentoDescricao = (e.getLancamento() == null) ? null : e.getLancamento().getDescricao();

        Long contaDestinoId = (e.getContaDestino() == null) ? null : e.getContaDestino().getIdContaBancaria();
        String contaDestinoApelido = (e.getContaDestino() == null) ? null : e.getContaDestino().getApelido();

        return new RecebimentoDTO(
                e.getIdRecebimento(),
                lancamentoId,
                lancamentoDescricao,
                e.getDataRecebimento(),
                e.getValorRecebido(),
                contaDestinoId,
                contaDestinoApelido,
                e.getObservacao()
        );
    }

    public static List<RecebimentoDTO> toDtoList(Collection<Recebimento> entities) {
        if (entities == null) return List.of();
        return entities.stream()
                .filter(Objects::nonNull)
                .map(RecebimentoMapper::toDto)
                .collect(Collectors.toList());
    }

    public static Page<RecebimentoDTO> toDtoPage(Page<Recebimento> page) {
        List<RecebimentoDTO> content = toDtoList(page.getContent());
        return new PageImpl<>(content, page.getPageable(), page.getTotalElements());
    }


    public static Recebimento toEntity(RecebimentoDTO dto,
                                       Lancamento lancamento,
                                       ContaBancaria contaDestino) {
        if (dto == null) return null;

        Recebimento e = new Recebimento();
        e.setIdRecebimento(dto.getIdRecebimento());
        e.setLancamento(lancamento);
        e.setDataRecebimento(dto.getDataRecebimento());
        e.setValorRecebido(dto.getValorRecebido());
        e.setContaDestino(contaDestino);
        e.setObservacao(trim(dto.getObservacao()));

        return e;
    }

    /* =============== ENTITY (resolver functions) =============== */

    public static Recebimento toEntity(RecebimentoDTO dto,
                                       Function<Long, Lancamento> lancamentoResolver,
                                       Function<Long, ContaBancaria> contaResolver) {

        if (dto == null) return null;

        Lancamento lancamento = (dto.getLancamentoId() == null)
                ? null
                : lancamentoResolver.apply(dto.getLancamentoId());

        ContaBancaria contaDestino = (dto.getContaDestinoId() == null)
                ? null
                : contaResolver.apply(dto.getContaDestinoId());

        return toEntity(dto, lancamento, contaDestino);
    }

    /* =============== COPY =============== */

    public static void copyToEntity(RecebimentoDTO dto,
                                    Recebimento target,
                                    Lancamento lancamento,
                                    ContaBancaria contaDestino) {

        if (dto == null || target == null) return;

        target.setLancamento(lancamento);
        target.setDataRecebimento(dto.getDataRecebimento());
        target.setValorRecebido(dto.getValorRecebido());
        target.setContaDestino(contaDestino);
        target.setObservacao(trim(dto.getObservacao()));
    }

    public static void copyToEntity(RecebimentoDTO dto,
                                    Recebimento target,
                                    Function<Long, Lancamento> lancamentoResolver,
                                    Function<Long, ContaBancaria> contaResolver) {

        if (dto == null || target == null) return;

        Lancamento lancamento = (dto.getLancamentoId() == null)
                ? null
                : lancamentoResolver.apply(dto.getLancamentoId());

        ContaBancaria contaDestino = (dto.getContaDestinoId() == null)
                ? null
                : contaResolver.apply(dto.getContaDestinoId());

        copyToEntity(dto, target, lancamento, contaDestino);
    }


    private static String trim(String s) {
        return (s == null) ? null : s.trim();
    }
}
