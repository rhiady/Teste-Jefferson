package com.resources;

import com.domains.dtos.RecebimentoDTO;
import com.services.RecebimentoService;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@Validated
@RestController
@RequestMapping("/api/v1/recebimentos")
public class RecebimentoResource {

    private final RecebimentoService service;

    public RecebimentoResource(RecebimentoService service) {
        this.service = service;
    }

    @GetMapping
    public ResponseEntity<List<RecebimentoDTO>> list(
            @RequestParam(required = false) Long lancamentoId) {

        List<RecebimentoDTO> body;
        if (lancamentoId != null) {
            body = service.findByLancamento(lancamentoId);
        } else {
            body = service.findAll();
        }
        return ResponseEntity.ok(body);
    }

    @GetMapping("/{id}")
    public ResponseEntity<RecebimentoDTO> findById(@PathVariable Long id) {
        RecebimentoDTO dto = service.findById(id);
        return ResponseEntity.ok(dto);
    }

    @PostMapping
    public ResponseEntity<RecebimentoDTO> create(
            @RequestBody @Validated(RecebimentoDTO.Create.class) RecebimentoDTO dto) {

        RecebimentoDTO created = service.create(dto);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(created.getIdRecebimento())
                .toUri();
        return ResponseEntity.created(location).body(created);
    }

    @PutMapping("/{id}")
    public ResponseEntity<RecebimentoDTO> update(
            @PathVariable Long id,
            @RequestBody @Validated(RecebimentoDTO.Update.class) RecebimentoDTO dto) {

        dto.setIdRecebimento(id);
        return ResponseEntity.ok(service.update(id, dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}

