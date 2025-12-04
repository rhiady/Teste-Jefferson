package com.services;



import com.domains.CentroCusto;
import com.domains.Usuario;
import com.domains.dtos.CentroCustoDTO;
import com.mappers.CentroCustoMapper;
import com.repositories.CentroCustoRepository;
import com.repositories.UsuarioRepository;
import com.services.exceptions.DataIntegrityViolationException;
import com.services.exceptions.ObjectNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
public class CentroCustoService {

    private final CentroCustoRepository centroRepo;
    private final UsuarioRepository usuarioRepo;

    public CentroCustoService(CentroCustoRepository centroRepo,
                              UsuarioRepository usuarioRepo) {
        this.centroRepo = centroRepo;
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
    public List<CentroCustoDTO> findAll() {
        return CentroCustoMapper.toDtoList(centroRepo.findAll());
    }

    @Transactional(readOnly = true)
    public CentroCustoDTO findById(Long id) {
        if (id == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Id do centro de custo é obrigatório");
        }

        return centroRepo.findById(id)
                .map(CentroCustoMapper::toDto)
                .orElseThrow(() ->
                        new ObjectNotFoundException("Centro de custo não encontrado: id=" + id));
    }


    @Transactional(readOnly = true)
    public List<CentroCustoDTO> findByUsuario(Long idUsuario) {
        if (idUsuario == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Id do usuário é obrigatório");
        }

        List<CentroCusto> centros = centroRepo.findAll()
                .stream()
                .filter(cc -> cc.getUsuario() != null
                        && idUsuario.equals(cc.getUsuario().getIdUsuario()))
                .toList();

        return CentroCustoMapper.toDtoList(centros);
    }



    @Transactional
    public CentroCustoDTO create(CentroCustoDTO dto) {
        if (dto == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Dados do centro de custo são obrigatórios");
        }


        dto.setIdCentroCusto(null);

        CentroCusto entity;
        try {
            entity = CentroCustoMapper.toEntity(dto, this::resolveUsuario);
        } catch (IllegalArgumentException ex) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, ex.getMessage());
        }

        CentroCusto saved = centroRepo.save(entity);
        return CentroCustoMapper.toDto(saved);
    }



    @Transactional
    public CentroCustoDTO update(Long id, CentroCustoDTO dto) {
        if (id == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Id do centro de custo é obrigatório");
        }
        if (dto == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Dados do centro de custo são obrigatórios");
        }

        CentroCusto entity = centroRepo.findById(id)
                .orElseThrow(() ->
                        new ObjectNotFoundException("Centro de custo não encontrado: id=" + id));

        CentroCustoMapper.copyToEntity(dto, entity, this::resolveUsuario);

        CentroCusto updated = centroRepo.save(entity);
        return CentroCustoMapper.toDto(updated);
    }



    @Transactional
    public void delete(Long id) {
        if (id == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Id do centro de custo é obrigatório");
        }

        CentroCusto entity = centroRepo.findById(id)
                .orElseThrow(() ->
                        new ObjectNotFoundException("Centro de custo não encontrado: id=" + id));

        try {
            centroRepo.delete(entity);
        } catch (org.springframework.dao.DataIntegrityViolationException ex) {
            throw new DataIntegrityViolationException(
                    "Centro de custo possui lançamentos vinculados e não pode ser removido: id=" + id,
                    ex
            );
        }
    }
}


