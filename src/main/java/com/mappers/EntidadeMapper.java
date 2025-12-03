package com.mappers;

import com.domains.Entidade;
import com.domains.Usuario;
import com.domains.dtos.EntidadeDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;

import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.function.Function;
import java.util.stream.Collectors;

public final class EntidadeMapper {

    private EntidadeMapper() {}

    public static EntidadeDTO toDto(Entidade e) {
        if (e == null) return null;

        Long usuarioId = (e.getUsuario() == null) ? null : e.getUsuario().getIdUsuario();
        String usuarioNome = (e.getUsuario() == null) ? null : e.getUsuario().getNome();

        return new EntidadeDTO(
                e.getIdEntidade(),
                e.getNome(),
                e.getDocumento(),
                e.getTipo(),
                usuarioId,
                usuarioNome
        );
    }

    public static List<EntidadeDTO> toDtoList(Collection<Entidade> entities) {
        if (entities == null) return List.of();
        return entities.stream()
                .filter(Objects::nonNull)
                .map(EntidadeMapper::toDto)
                .collect(Collectors.toList());
    }

    public static Page<EntidadeDTO> toDtoPage(Page<Entidade> page) {
        List<EntidadeDTO> content = toDtoList(page.getContent());
        return new PageImpl<>(content, page.getPageable(), page.getTotalElements());
    }

    public static Entidade toEntity(EntidadeDTO dto, Usuario usuario) {
        if (dto == null) return null;

        Entidade e = new Entidade();
        e.setIdEntidade(dto.getIdEntidade());
        e.setNome(trim(dto.getNome()));
        e.setDocumento(trim(dto.getDocumento()));
        e.setTipo(trim(dto.getTipo()));
        e.setUsuario(usuario);
        return e;
    }

    public static Entidade toEntity(EntidadeDTO dto, Function<Long, Usuario> usuarioResolver) {
        if (dto == null) return null;
        Usuario usuario = (dto.getUsuarioId() == null) ? null : usuarioResolver.apply(dto.getUsuarioId());
        return toEntity(dto, usuario);
    }

    public static void copyToEntity(EntidadeDTO dto, Entidade target, Usuario usuario) {
        if (dto == null || target == null) return;

        target.setNome(trim(dto.getNome()));
        target.setDocumento(trim(dto.getDocumento()));
        target.setTipo(trim(dto.getTipo()));
        target.setUsuario(usuario);
    }

    public static void copyToEntity(EntidadeDTO dto, Entidade target, Function<Long, Usuario> usuarioResolver) {
        if (dto == null || target == null) return;
        Usuario usuario = (dto.getUsuarioId() == null) ? null : usuarioResolver.apply(dto.getUsuarioId());
        copyToEntity(dto, target, usuario);
    }

    private static String trim(String s) {
        return (s == null) ? null : s.trim();
    }
}
