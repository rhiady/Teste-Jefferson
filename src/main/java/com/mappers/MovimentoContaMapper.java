package com.mappers;

import com.domains.ContaBancaria;
import com.domains.MovimentoConta;
import com.domains.dtos.MovimentoContaDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;

import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.function.Function;
import java.util.stream.Collectors;

public final class MovimentoContaMapper {

    private MovimentoContaMapper() {
    }

    public static MovimentoContaDTO toDto(MovimentoConta e) {
        if (e == null) return null;

        Long idContaBancaria = (e.getConta() == null) ? null : e.getConta().getIdContaBancaria();
        String contaApelido = (e.getConta() == null) ? null : e.getConta().getApelido();

        return new MovimentoContaDTO(
                e.getIdMovimentoConta(),
                idContaBancaria,
                contaApelido,
                e.getDataMovimento(),
                e.getValor(),
                e.getHistorico(),
                e.getReferenciaId(),
                e.getReferenciaTipo()
        );
    }

    public static List<MovimentoContaDTO> toDtoList(Collection<MovimentoConta> entities) {
        if (entities == null) return List.of();
        return entities.stream()
                .filter(Objects::nonNull)
                .map(MovimentoContaMapper::toDto)
                .collect(Collectors.toList());
    }

    public static Page<MovimentoContaDTO> toDtoPage(Page<MovimentoConta> page) {
        List<MovimentoContaDTO> content = toDtoList(page.getContent());
        return new PageImpl<>(content, page.getPageable(), page.getTotalElements());
    }

    public static MovimentoConta toEntity(MovimentoContaDTO dto,
                                          ContaBancaria conta) {
        if (dto == null) return null;

        MovimentoConta e = new MovimentoConta();

        e.setIdMovimentoConta(dto.getIdMovimentoConta());
        e.setConta(conta);
        e.setDataMovimento(dto.getDataMovimento());
        e.setValor(dto.getValor());
        e.setHistorico(trim(dto.getHistorico()));
        e.setReferenciaId(dto.getReferenciaId());
        e.setReferenciaTipo(trim(dto.getReferenciaTipo()));

        return e;
    }

    public static MovimentoConta toEntity(MovimentoContaDTO dto,
                                          Function<Long, ContaBancaria> contaResolver) {
        if (dto == null) return null;

        ContaBancaria conta = (dto.getContaId() == null) ? null : contaResolver.apply(dto.getContaId());
        return toEntity(dto, conta);
    }

    public static void copyToEntity(MovimentoContaDTO dto,
                                    MovimentoConta target,
                                    ContaBancaria conta) {
        if (dto == null || target == null) return;

        target.setConta(conta);
        target.setDataMovimento(dto.getDataMovimento());
        target.setValor(dto.getValor());
        target.setHistorico(trim(dto.getHistorico()));
        target.setReferenciaId(dto.getReferenciaId());
        target.setReferenciaTipo(trim(dto.getReferenciaTipo()));
    }

    public static void copyToEntity(MovimentoContaDTO dto,
                                    MovimentoConta target,
                                    Function<Long, ContaBancaria> contaResolver) {
        if (dto == null || target == null) return;

        ContaBancaria conta = (dto.getContaId() == null) ? null : contaResolver.apply(dto.getContaId());
        copyToEntity(dto, target, conta);
    }

    private static String trim(String s) {
        return (s == null) ? null : s.trim();
    }
}

