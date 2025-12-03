package com.mappers;

import com.domains.CartaoCredito;
import com.domains.Usuario;
import com.domains.dtos.CartaoCreditoDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;

import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.function.Function;
import java.util.stream.Collectors;

public final class CartaoCreditoMapper {

    private CartaoCreditoMapper() {}

    public static CartaoCreditoDTO toDto(CartaoCredito e) {
        if (e == null) return null;

        Long usuarioId = (e.getUsuario() == null) ? null : e.getUsuario().getIdUsuario();
        String usuarioNome = (e.getUsuario() == null) ? null : e.getUsuario().getNome();

        return new CartaoCreditoDTO(
                e.getIdCartaoCredito(),
                e.getBandeira(),
                e.getEmissor(),
                e.getApelido(),
                e.getFechamentoFaturaDia(),
                e.getVencimentoFaturaDia(),
                e.getAtivo(),
                e.getStatusFatura(),
                usuarioId,
                usuarioNome
        );
    }

    public static List<CartaoCreditoDTO> toDtoList(Collection<CartaoCredito> entities) {
        if (entities == null) return List.of();
        return entities.stream()
                .filter(Objects::nonNull)
                .map(CartaoCreditoMapper::toDto)
                .collect(Collectors.toList());
    }

    public static Page<CartaoCreditoDTO> toDtoPage(Page<CartaoCredito> page) {
        List<CartaoCreditoDTO> content = toDtoList(page.getContent());
        return new PageImpl<>(content, page.getPageable(), page.getTotalElements());
    }

    public static CartaoCredito toEntity(CartaoCreditoDTO dto, Usuario usuario) {
        if (dto == null) return null;

        CartaoCredito e = new CartaoCredito();
        e.setIdCartaoCredito(dto.getIdCartaoCredito());
        e.setBandeira(trim(dto.getBandeira()));
        e.setEmissor(trim(dto.getEmissor()));
        e.setApelido(trim(dto.getApelido()));
        e.setFechamentoFaturaDia(dto.getFechamentoFaturaDia());
        e.setVencimentoFaturaDia(dto.getVencimentoFaturaDia());
        e.setAtivo(dto.getAtivo());
        e.setStatusFatura(dto.getStatusFatura());
        e.setUsuario(usuario);
        return e;
    }

    public static CartaoCredito toEntity(CartaoCreditoDTO dto, Function<Long, Usuario> usuarioResolver) {
        if (dto == null) return null;
        Usuario usuario = (dto.getUsuarioId() == null) ? null : usuarioResolver.apply(dto.getUsuarioId());
        return toEntity(dto, usuario);
    }

    public static void copyToEntity(CartaoCreditoDTO dto, CartaoCredito target, Usuario usuario) {
        if (dto == null || target == null) return;

        target.setBandeira(trim(dto.getBandeira()));
        target.setEmissor(trim(dto.getEmissor()));
        target.setApelido(trim(dto.getApelido()));
        target.setFechamentoFaturaDia(dto.getFechamentoFaturaDia());
        target.setVencimentoFaturaDia(dto.getVencimentoFaturaDia());
        target.setAtivo(dto.getAtivo());
        target.setStatusFatura(dto.getStatusFatura());
        target.setUsuario(usuario);
    }

    public static void copyToEntity(CartaoCreditoDTO dto, CartaoCredito target, Function<Long, Usuario> usuarioResolver) {
        if (dto == null || target == null) return;
        Usuario usuario = (dto.getUsuarioId() == null) ? null : usuarioResolver.apply(dto.getUsuarioId());
        copyToEntity(dto, target, usuario);
    }

    private static String trim(String s) {
        return (s == null) ? null : s.trim();
    }
}
