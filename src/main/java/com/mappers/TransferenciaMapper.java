package com.mappers;

import com.domains.ContaBancaria;
import com.domains.Transferencia;
import com.domains.Usuario;
import com.domains.dtos.TransferenciaDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.function.Function;
import java.util.stream.Collectors;

public final class TransferenciaMapper {

    private TransferenciaMapper() {}



    public static TransferenciaDTO toDto(Transferencia e) {
        if (e == null) return null;

        Long idDto = e.getIdTransferencia();

        Usuario u = e.getUsuario();
        ContaBancaria origem = e.getContaOrigem();
        ContaBancaria destino = e.getContaDestino();

        Long usuarioId = (u == null) ? null : u.getIdUsuario();
        String usuarioNome = (u == null) ? null : u.getNome();

        Long contaOrigemId = (origem == null) ? null : origem.getIdContaBancaria();
        String contaOrigemApelido = (origem == null) ? null : origem.getApelido();

        Long contaDestinoId = (destino == null) ? null : destino.getIdContaBancaria();
        String contaDestinoApelido = (destino == null) ? null : destino.getApelido();



        return new TransferenciaDTO(
                idDto,
                usuarioId,
                usuarioNome,
                contaOrigemId,
                contaOrigemApelido,
                contaDestinoId,
                contaDestinoApelido,
                e.getData(),
                e.getValor(),
                e.getObservacao()
        );
    }

    public static List<TransferenciaDTO> toDtoList(Collection<Transferencia> entities) {
        if (entities == null) return List.of();
        return entities.stream()
                .filter(Objects::nonNull)
                .map(TransferenciaMapper::toDto)
                .collect(Collectors.toList());
    }

    public static Page<TransferenciaDTO> toDtoPage(Page<Transferencia> page) {
        List<TransferenciaDTO> content = toDtoList(page.getContent());
        return new PageImpl<>(content, page.getPageable(), page.getTotalElements());
    }



    public static Transferencia toEntity(TransferenciaDTO dto,
                                         Usuario usuario,
                                         ContaBancaria contaOrigem,
                                         ContaBancaria contaDestino) {
        if (dto == null) return null;

        Transferencia e = new Transferencia();
        e.setIdTransferencia(dto.getIdTransferencia());
        e.setUsuario(usuario);
        e.setContaOrigem(contaOrigem);
        e.setContaDestino(contaDestino);



        e.setValor(dto.getValor());
        e.setObservacao(trim(dto.getObservacao()));

        return e;
    }

    public static Transferencia toEntity(TransferenciaDTO dto,
                                         Function<Long, Usuario> usuarioResolver,
                                         Function<Long, ContaBancaria> contaResolver) {
        if (dto == null) return null;

        Usuario usuario = (dto.getUsuarioId() == null)
                ? null
                : usuarioResolver.apply(dto.getUsuarioId());

        ContaBancaria origem = (dto.getContaOrigemId() == null)
                ? null
                : contaResolver.apply(dto.getContaOrigemId());

        ContaBancaria destino = (dto.getContaDestinoId() == null)
                ? null
                : contaResolver.apply(dto.getContaDestinoId());

        return toEntity(dto, usuario, origem, destino);
    }

    public static void copyToEntity(TransferenciaDTO dto,
                                    Transferencia target,
                                    Usuario usuario,
                                    ContaBancaria contaOrigem,
                                    ContaBancaria contaDestino) {
        if (dto == null || target == null) return;

        target.setUsuario(usuario);
        target.setContaOrigem(contaOrigem);
        target.setContaDestino(contaDestino);


        target.setValor(dto.getValor());
        target.setObservacao(trim(dto.getObservacao()));
    }

    public static void copyToEntity(TransferenciaDTO dto,
                                    Transferencia target,
                                    Function<Long, Usuario> usuarioResolver,
                                    Function<Long, ContaBancaria> contaResolver) {
        if (dto == null || target == null) return;

        Usuario usuario = (dto.getUsuarioId() == null)
                ? null
                : usuarioResolver.apply(dto.getUsuarioId());

        ContaBancaria origem = (dto.getContaOrigemId() == null)
                ? null
                : contaResolver.apply(dto.getContaOrigemId());

        ContaBancaria destino = (dto.getContaDestinoId() == null)
                ? null
                : contaResolver.apply(dto.getContaDestinoId());

        copyToEntity(dto, target, usuario, origem, destino);
    }


    private static String trim(String s) {
        return (s == null) ? null : s.trim();
    }
}
