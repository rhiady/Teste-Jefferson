package com.resources;

import com.domains.dtos.CentroCustoDTO;
import com.services.CentroCustoService;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@Validated
@RestController
@RequestMapping("/api/v1/centros-custo")
public class CentroCustoResource {

    private final CentroCustoService service;

    public CentroCustoResource(CentroCustoService service) {
        this.service = service;
    }

    @GetMapping
    public ResponseEntity<List<CentroCustoDTO>> list(
            @RequestParam(required = false) Long usuarioId) {

        List<CentroCustoDTO> body;
        if (usuarioId != null) {
            body = service.findByUsuario(usuarioId);
        } else {
            body = service.findAll();
        }
        return ResponseEntity.ok(body);
    }

    @GetMapping("/{id}")
    public ResponseEntity<CentroCustoDTO> findById(@PathVariable Long id) {
        CentroCustoDTO dto = service.findById(id);
        return ResponseEntity.ok(dto);
    }

    @PostMapping
    public ResponseEntity<CentroCustoDTO> create(
            @RequestBody @Validated(CentroCustoDTO.Create.class) CentroCustoDTO dto) {

        CentroCustoDTO created = service.create(dto);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(created.getIdCentroCusto())
                .toUri();
        return ResponseEntity.created(location).body(created);
    }

    @PutMapping("/{id}")
    public ResponseEntity<CentroCustoDTO> update(
            @PathVariable Long id,
            @RequestBody @Validated(CentroCustoDTO.Update.class) CentroCustoDTO dto) {

        dto.setIdCentroCusto(id);
        return ResponseEntity.ok(service.update(id, dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}
