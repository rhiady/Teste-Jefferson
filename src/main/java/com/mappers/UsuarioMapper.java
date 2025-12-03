package com.mappers;

import com.domains.Usuario;
import com.domains.dtos.UsuarioDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;

import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public final class UsuarioMapper {

    private UsuarioMapper() {}

    public static UsuarioDTO toDto(Usuario e) {
        if (e == null) return null;

        return new UsuarioDTO(
                e.getIdUsuario(),
                e.getNome(),
                e.getEmail(),
                e.getCriadoEm()
        );
    }

    public static List<UsuarioDTO> toDtoList(Collection<Usuario> entities) {
        if (entities == null) return List.of();
        return entities.stream()
                .filter(Objects::nonNull)
                .map(UsuarioMapper::toDto)
                .collect(Collectors.toList());
    }

    public static Page<UsuarioDTO> toDtoPage(Page<Usuario> page) {
        List<UsuarioDTO> content = toDtoList(page.getContent());
        return new PageImpl<>(content, page.getPageable(), page.getTotalElements());
    }

    public static Usuario toEntity(UsuarioDTO dto) {
        if (dto == null) return null;

        Usuario e = new Usuario();
        e.setIdUsuario(dto.getIdUsuario());
        e.setNome(trim(dto.getNome()));
        e.setEmail(trim(dto.getEmail()));
        e.setCriadoEm(dto.getDataCadastro());
        return e;
    }

    public static void copyToEntity(UsuarioDTO dto, Usuario target) {
        if (dto == null || target == null) return;

        target.setNome(trim(dto.getNome()));
        target.setEmail(trim(dto.getEmail()));
        target.setCriadoEm(dto.getDataCadastro());
    }

    private static String trim(String s) {
        return (s == null) ? null : s.trim();
    }
}
