package com.mappers;

import com.domains.CartaoCredito;
import com.domains.FaturaCartao;
import com.domains.dtos.FaturaCartaoDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;

import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.function.Function;
import java.util.stream.Collectors;

public final class FaturaCartaoMapper {

    private FaturaCartaoMapper() {}

    public static FaturaCartaoDTO toDto(FaturaCartao e) {
        if (e == null) return null;

        Long cartaoId = (e.getCartao() == null) ? null : e.getCartao().getIdCartaoCredito();
        String cartaoApelido = (e.getCartao() == null) ? null : e.getCartao().getApelido();

        return new FaturaCartaoDTO(
                e.getIdFaturaCartao(),
                e.getCompetencia(),
                e.getDataFechamento(),
                e.getDataVencimento(),
                e.getValorTotal(),
                e.getStatus(),
                cartaoId,
                cartaoApelido
        );
    }

    public static List<FaturaCartaoDTO> toDtoList(Collection<FaturaCartao> entities) {
        if (entities == null) return List.of();
        return entities.stream()
                .filter(Objects::nonNull)
                .map(FaturaCartaoMapper::toDto)
                .collect(Collectors.toList());
    }

    public static Page<FaturaCartaoDTO> toDtoPage(Page<FaturaCartao> page) {
        List<FaturaCartaoDTO> content = toDtoList(page.getContent());
        return new PageImpl<>(content, page.getPageable(), page.getTotalElements());
    }

    public static FaturaCartao toEntity(FaturaCartaoDTO dto, CartaoCredito cartao) {
        if (dto == null) return null;

        FaturaCartao e = new FaturaCartao();
        e.setIdFaturaCartao(dto.getIdFaturaCartao());
        e.setCompetencia(trim(dto.getCompetencia()));
        e.setDataFechamento(dto.getDataFechamento());
        e.setDataVencimento(dto.getDataVencimento());
        e.setValorTotal(dto.getValorTotal());
        e.setStatus(dto.getStatus());
        e.setCartao(cartao);
        return e;
    }

    public static FaturaCartao toEntity(FaturaCartaoDTO dto, Function<Long, CartaoCredito> cartaoResolver) {
        if (dto == null) return null;
        CartaoCredito cartao = (dto.getCartaoId() == null) ? null : cartaoResolver.apply(dto.getCartaoId());
        return toEntity(dto, cartao);
    }

    public static void copyToEntity(FaturaCartaoDTO dto, FaturaCartao target, CartaoCredito cartao) {
        if (dto == null || target == null) return;

        target.setCompetencia(trim(dto.getCompetencia()));
        target.setDataFechamento(dto.getDataFechamento());
        target.setDataVencimento(dto.getDataVencimento());
        target.setValorTotal(dto.getValorTotal());
        target.setStatus(dto.getStatus());
        target.setCartao(cartao);
    }

    public static void copyToEntity(FaturaCartaoDTO dto, FaturaCartao target, Function<Long, CartaoCredito> cartaoResolver) {
        if (dto == null || target == null) return;
        CartaoCredito cartao = (dto.getCartaoId() == null) ? null : cartaoResolver.apply(dto.getCartaoId());
        copyToEntity(dto, target, cartao);
    }

    private static String trim(String s) {
        return (s == null) ? null : s.trim();
    }
}
