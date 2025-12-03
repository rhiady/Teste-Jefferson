package com.mappers;

import com.domains.*;
import com.domains.dtos.LancamentoDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;

import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.function.Function;
import java.util.stream.Collectors;

public final class LancamentoMapper {

    private LancamentoMapper() {}

    public static LancamentoDTO toDto(Lancamento e) {
        if (e == null) return null;

        Long usuarioId = (e.getUsuario() == null) ? null : e.getUsuario().getIdUsuario();
        String usuarioNome = (e.getUsuario() == null) ? null : e.getUsuario().getNome();

        Long entidadeId = (e.getEntidade() == null) ? null : e.getEntidade().getIdEntidade();
        String entidadeNome = (e.getEntidade() == null) ? null : e.getEntidade().getNome();

        Long centroId = (e.getCentroCusto() == null) ? null : e.getCentroCusto().getIdCentroCusto();
        String centroNome = (e.getCentroCusto() == null) ? null : e.getCentroCusto().getNome();

        Long contaId = (e.getContaBancaria() == null) ? null : e.getContaBancaria().getIdContaBancaria();
        String contaApelido = (e.getContaBancaria() == null) ? null : e.getContaBancaria().getApelido();

        Long cartaoId = (e.getCartaoCredito() == null) ? null : e.getCartaoCredito().getIdCartaoCredito();
        String cartaoApelido = (e.getCartaoCredito() == null) ? null : e.getCartaoCredito().getApelido();

        return new LancamentoDTO(
                e.getIdLancamento(),
                e.getDescricao(),
                e.getValor(),
                usuarioId,
                usuarioNome,
                e.getTipo(),
                entidadeId,
                entidadeNome,
                centroId,
                centroNome,
                e.getDataCompetencia(),
                e.getDataVencimento(),
                e.getMeioPagamento(),
                contaId,
                contaApelido,
                cartaoId,
                cartaoApelido,
                e.getStatus(),
                e.getValorBaixado()
        );
    }

    public static List<LancamentoDTO> toDtoList(Collection<Lancamento> entities) {
        if (entities == null) return List.of();
        return entities.stream()
                .filter(Objects::nonNull)
                .map(LancamentoMapper::toDto)
                .collect(Collectors.toList());
    }

    public static Page<LancamentoDTO> toDtoPage(Page<Lancamento> page) {
        List<LancamentoDTO> content = toDtoList(page.getContent());
        return new PageImpl<>(content, page.getPageable(), page.getTotalElements());
    }

    public static Lancamento toEntity(
            LancamentoDTO dto,
            Usuario usuario,
            Entidade entidade,
            CentroCusto centroCusto,
            ContaBancaria conta,
            CartaoCredito cartao
    ) {
        if (dto == null) return null;

        Lancamento e = new Lancamento();
        e.setIdLancamento(dto.getIdLancamento());
        e.setDescricao(trim(dto.getDescricao()));
        e.setValor(dto.getValor());
        e.setUsuario(usuario);
        e.setTipo(dto.getTipo());
        e.setEntidade(entidade);
        e.setCentroCusto(centroCusto);
        e.setDataCompetencia(dto.getDataCompetencia());
        e.setDataVencimento(dto.getDataVencimento());
        e.setMeioPagamento(dto.getMeioPagamento());
        e.setContaBancaria(conta);
        e.setCartaoCredito(cartao);
        e.setStatus(dto.getStatus());
        e.setValorBaixado(dto.getValorBaixado());
        return e;
    }

    public static Lancamento toEntity(
            LancamentoDTO dto,
            Function<Long, Usuario> usuarioResolver,
            Function<Long, Entidade> entidadeResolver,
            Function<Long, CentroCusto> centroResolver,
            Function<Long, ContaBancaria> contaResolver,
            Function<Long, CartaoCredito> cartaoResolver
    ) {
        if (dto == null) return null;

        Usuario usuario = (dto.getUsuarioId() == null) ? null : usuarioResolver.apply(dto.getUsuarioId());
        Entidade entidade = (dto.getEntidadeId() == null) ? null : entidadeResolver.apply(dto.getEntidadeId());
        CentroCusto centro = (dto.getCentroCustoId() == null) ? null : centroResolver.apply(dto.getCentroCustoId());
        ContaBancaria conta = (dto.getContaBancariaId() == null) ? null : contaResolver.apply(dto.getContaBancariaId());
        CartaoCredito cartao = (dto.getCartaoCreditoId() == null) ? null : cartaoResolver.apply(dto.getCartaoCreditoId());

        return toEntity(dto, usuario, entidade, centro, conta, cartao);
    }

    public static void copyToEntity(
            LancamentoDTO dto,
            Lancamento target,
            Usuario usuario,
            Entidade entidade,
            CentroCusto centroCusto,
            ContaBancaria conta,
            CartaoCredito cartao
    ) {
        if (dto == null || target == null) return;

        target.setDescricao(trim(dto.getDescricao()));
        target.setValor(dto.getValor());
        target.setUsuario(usuario);
        target.setTipo(dto.getTipo());
        target.setEntidade(entidade);
        target.setCentroCusto(centroCusto);
        target.setDataCompetencia(dto.getDataCompetencia());
        target.setDataVencimento(dto.getDataVencimento());
        target.setMeioPagamento(dto.getMeioPagamento());
        target.setContaBancaria(conta);
        target.setCartaoCredito(cartao);
        target.setStatus(dto.getStatus());
        target.setValorBaixado(dto.getValorBaixado());
    }

    public static void copyToEntity(
            LancamentoDTO dto,
            Lancamento target,
            Function<Long, Usuario> usuarioResolver,
            Function<Long, Entidade> entidadeResolver,
            Function<Long, CentroCusto> centroResolver,
            Function<Long, ContaBancaria> contaResolver,
            Function<Long, CartaoCredito> cartaoResolver
    ) {
        if (dto == null || target == null) return;

        Usuario usuario = (dto.getUsuarioId() == null) ? null : usuarioResolver.apply(dto.getUsuarioId());
        Entidade entidade = (dto.getEntidadeId() == null) ? null : entidadeResolver.apply(dto.getEntidadeId());
        CentroCusto centro = (dto.getCentroCustoId() == null) ? null : centroResolver.apply(dto.getCentroCustoId());
        ContaBancaria conta = (dto.getContaBancariaId() == null) ? null : contaResolver.apply(dto.getContaBancariaId());
        CartaoCredito cartao = (dto.getCartaoCreditoId() == null) ? null : cartaoResolver.apply(dto.getCartaoCreditoId());

        copyToEntity(dto, target, usuario, entidade, centro, conta, cartao);
    }

    private static String trim(String s) {
        return (s == null) ? null : s.trim();
    }
}
