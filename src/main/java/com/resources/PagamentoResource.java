package com.resources;

import com.domains.dtos.PagamentoDTO;
import com.services.PagamentoService;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@Validated
@RestController
@RequestMapping("/api/v1/pagamentos")
public class PagamentoResource {

    private final PagamentoService service;

    public PagamentoResource(PagamentoService service) {
        this.service = service;
    }

    @GetMapping
    public ResponseEntity<List<PagamentoDTO>> list(
            @RequestParam(required = false) Long lancamentoId) {

        List<PagamentoDTO> body;
        if (lancamentoId != null) {
            body = service.findByLancamento(lancamentoId);
        } else {
            body = service.findAll();
        }
        return ResponseEntity.ok(body);
    }

    @GetMapping("/{id}")
    public ResponseEntity<PagamentoDTO> findById(@PathVariable Long id) {
        PagamentoDTO dto = service.findById(id);
        return ResponseEntity.ok(dto);
    }

    @PostMapping
    public ResponseEntity<PagamentoDTO> create(
            @RequestBody @Validated(PagamentoDTO.Create.class) PagamentoDTO dto) {

        PagamentoDTO created = service.create(dto);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(created.getIdPagamento())
                .toUri();
        return ResponseEntity.created(location).body(created);
    }

    @PutMapping("/{id}")
    public ResponseEntity<PagamentoDTO> update(
            @PathVariable Long id,
            @RequestBody @Validated(PagamentoDTO.Update.class) PagamentoDTO dto) {

        dto.setIdPagamento(id);
        return ResponseEntity.ok(service.update(id, dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}
