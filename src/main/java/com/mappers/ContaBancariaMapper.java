package com.mappers;

import com.domains.ContaBancaria;
import com.domains.Usuario;
import com.domains.dtos.ContaBancariaDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;

import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.function.Function;
import java.util.stream.Collectors;

public final class ContaBancariaMapper {

    private ContaBancariaMapper() {}

    public static ContaBancariaDTO toDto(ContaBancaria e) {
        if (e == null) return null;

        Long usuarioId = (e.getUsuario() == null) ? null : e.getUsuario().getIdUsuario();
        String usuarioNome = (e.getUsuario() == null) ? null : e.getUsuario().getNome();

        return new ContaBancariaDTO(
                e.getIdContaBancaria(),
                e.getInstituicao(),
                e.getAgencia(),
                e.getNumero(),
                e.getApelido(),
                e.getSaldoInicial(),
                e.getDataSaldoInicial(),
                e.getAtiva(),
                usuarioId,
                usuarioNome
        );
    }

    public static List<ContaBancariaDTO> toDtoList(Collection<ContaBancaria> entities) {
        if (entities == null) return List.of();
        return entities.stream()
                .filter(Objects::nonNull)
                .map(ContaBancariaMapper::toDto)
                .collect(Collectors.toList());
    }

    public static Page<ContaBancariaDTO> toDtoPage(Page<ContaBancaria> page) {
        List<ContaBancariaDTO> content = toDtoList(page.getContent());
        return new PageImpl<>(content, page.getPageable(), page.getTotalElements());
    }

    public static ContaBancaria toEntity(ContaBancariaDTO dto, Usuario usuario) {
        if (dto == null) return null;

        ContaBancaria e = new ContaBancaria();
        e.setIdContaBancaria(dto.getIdContaBancaria());
        e.setInstituicao(trim(dto.getInstituicao()));
        e.setAgencia(trim(dto.getAgencia()));
        e.setNumero(trim(dto.getNumero()));
        e.setApelido(trim(dto.getApelido()));
        e.setSaldoInicial(dto.getSaldoInicial());
        e.setDataSaldoInicial(dto.getDataSaldoInicial());
        e.setAtiva(dto.getAtivo());
        e.setUsuario(usuario);
        return e;
    }

    public static ContaBancaria toEntity(ContaBancariaDTO dto, Function<Long, Usuario> usuarioResolver) {
        if (dto == null) return null;
        Usuario usuario = (dto.getUsuarioId() == null) ? null : usuarioResolver.apply(dto.getUsuarioId());
        return toEntity(dto, usuario);
    }

    public static void copyToEntity(ContaBancariaDTO dto, ContaBancaria target, Usuario usuario) {
        if (dto == null || target == null) return;

        target.setInstituicao(trim(dto.getInstituicao()));
        target.setAgencia(trim(dto.getAgencia()));
        target.setNumero(trim(dto.getNumero()));
        target.setApelido(trim(dto.getApelido()));
        target.setSaldoInicial(dto.getSaldoInicial());
        target.setDataSaldoInicial(dto.getDataSaldoInicial());
        target.setAtiva(dto.getAtivo());
        target.setUsuario(usuario);
    }

    public static void copyToEntity(ContaBancariaDTO dto, ContaBancaria target, Function<Long, Usuario> usuarioResolver) {
        if (dto == null || target == null) return;
        Usuario usuario = (dto.getUsuarioId() == null) ? null : usuarioResolver.apply(dto.getUsuarioId());
        copyToEntity(dto, target, usuario);
    }

    private static String trim(String s) {
        return (s == null) ? null : s.trim();
    }
}
