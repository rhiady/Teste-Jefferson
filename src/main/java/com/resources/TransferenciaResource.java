package com.resources;

import com.domains.dtos.TransferenciaDTO;
import com.services.TransferenciaService;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@Validated
@RestController
@RequestMapping("/api/v1/transferencias")
public class TransferenciaResource {

    private final TransferenciaService service;

    public TransferenciaResource(TransferenciaService service) {
        this.service = service;
    }

    @GetMapping
    public ResponseEntity<List<TransferenciaDTO>> list(
            @RequestParam(required = false) Long usuarioId) {

        List<TransferenciaDTO> body;
        if (usuarioId != null) {
            body = service.findByUsuario(usuarioId);
        } else {
            body = service.findAll();
        }
        return ResponseEntity.ok(body);
    }

    @GetMapping("/{id}")
    public ResponseEntity<TransferenciaDTO> findById(@PathVariable Long id) {
        TransferenciaDTO dto = service.findById(id);
        return ResponseEntity.ok(dto);
    }

    @PostMapping
    public ResponseEntity<TransferenciaDTO> create(
            @RequestBody @Validated(TransferenciaDTO.Create.class) TransferenciaDTO dto) {

        TransferenciaDTO created = service.create(dto);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(created.getIdTransferencia())
                .toUri();
        return ResponseEntity.created(location).body(created);
    }

    @PutMapping("/{id}")
    public ResponseEntity<TransferenciaDTO> update(
            @PathVariable Long id,
            @RequestBody @Validated(TransferenciaDTO.Update.class) TransferenciaDTO dto) {

        dto.setIdTransferencia(id);
        return ResponseEntity.ok(service.update(id, dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}
