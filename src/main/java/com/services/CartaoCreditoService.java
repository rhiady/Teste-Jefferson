package com.services;

import com.domains.CartaoCredito;
import com.domains.Usuario;
import com.domains.dtos.CartaoCreditoDTO;
import com.mappers.CartaoCreditoMapper;
import com.repositories.CartaoCreditoRepository;
import com.repositories.UsuarioRepository;
import com.services.exceptions.ObjectNotFoundException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
public class CartaoCreditoService {

    private final CartaoCreditoRepository cartaoRepo;
    private final UsuarioRepository usuarioRepo;

    public CartaoCreditoService(CartaoCreditoRepository cartaoRepo,
                                UsuarioRepository usuarioRepo) {
        this.cartaoRepo = cartaoRepo;
        this.usuarioRepo = usuarioRepo;
    }



    private Usuario resolveUsuario(Long usuarioId) {
        if (usuarioId == null) {
            return null;
        }

        return usuarioRepo.findById(usuarioId)
                .orElseThrow(() ->
                        new ObjectNotFoundException("Usuário não encontrado: id=" + usuarioId));
    }


    @Transactional(readOnly = true)
    public List<CartaoCreditoDTO> findAll() {
        return CartaoCreditoMapper.toDtoList(cartaoRepo.findAll());
    }

    @Transactional(readOnly = true)
    public CartaoCreditoDTO findById(Long id) {
        if (id == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Id do cartão é obrigatório");
        }

        return cartaoRepo.findById(id)
                .map(CartaoCreditoMapper::toDto)
                .orElseThrow(() ->
                        new ObjectNotFoundException("Cartão de crédito não encontrado: id=" + id));
    }


    @Transactional(readOnly = true)
    public List<CartaoCreditoDTO> findByUsuario(Long idUsuario) {
        if (idUsuario == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Id do usuário é obrigatório");
        }

        List<CartaoCredito> cartoes = cartaoRepo.findByUsuario_IdUsuario(idUsuario);
        return CartaoCreditoMapper.toDtoList(cartoes);
    }



    @Transactional
    public CartaoCreditoDTO create(CartaoCreditoDTO dto) {
        if (dto == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Dados do cartão são obrigatórios");
        }

        dto.setIdCartaoCredito(null);

        CartaoCredito entity;
        try {
            entity = CartaoCreditoMapper.toEntity(dto, this::resolveUsuario);
        } catch (IllegalArgumentException ex) {

            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, ex.getMessage());
        }

        CartaoCredito saved = cartaoRepo.save(entity);
        return CartaoCreditoMapper.toDto(saved);
    }


    @Transactional
    public CartaoCreditoDTO update(Long id, CartaoCreditoDTO dto) {
        if (id == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Id do cartão é obrigatório");
        }
        if (dto == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Dados do cartão são obrigatórios");
        }

        CartaoCredito entity = cartaoRepo.findById(id)
                .orElseThrow(() ->
                        new ObjectNotFoundException("Cartão de crédito não encontrado: id=" + id));


        CartaoCreditoMapper.copyToEntity(dto, entity, this::resolveUsuario);

        CartaoCredito updated = cartaoRepo.save(entity);
        return CartaoCreditoMapper.toDto(updated);
    }



    @Transactional
    public void delete(Long id) {
        if (id == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Id do cartão é obrigatório");
        }

        CartaoCredito entity = cartaoRepo.findById(id)
                .orElseThrow(() ->
                        new ObjectNotFoundException("Cartão de crédito não encontrado: id=" + id));

        try {
            cartaoRepo.delete(entity);
        } catch (org.springframework.dao.DataIntegrityViolationException ex) {
            throw new DataIntegrityViolationException(
                    "Cartão de crédito possui vínculos (faturas/lançamentos) e não pode ser removido: id=" + id,
                    ex
            );
        }
    }
}
