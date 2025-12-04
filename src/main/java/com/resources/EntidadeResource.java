package com.resources;

import com.domains.dtos.EntidadeDTO;
import com.services.EntidadeService;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@Validated
@RestController
@RequestMapping("/api/v1/entidades")
public class EntidadeResource {

    private final EntidadeService service;

    public EntidadeResource(EntidadeService service) {
        this.service = service;
    }

    @GetMapping
    public ResponseEntity<List<EntidadeDTO>> list(
            @RequestParam(required = false) Long usuarioId) {

        List<EntidadeDTO> body;
        if (usuarioId != null) {
            body = service.findByUsuario(usuarioId);
        } else {
            body = service.findAll();
        }
        return ResponseEntity.ok(body);
    }

    @GetMapping("/{id}")
    public ResponseEntity<EntidadeDTO> findById(@PathVariable Long id) {
        EntidadeDTO dto = service.findById(id);
        return ResponseEntity.ok(dto);
    }

    @PostMapping
    public ResponseEntity<EntidadeDTO> create(
            @RequestBody @Validated(EntidadeDTO.Create.class) EntidadeDTO dto) {

        EntidadeDTO created = service.create(dto);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(created.getIdEntidade())
                .toUri();
        return ResponseEntity.created(location).body(created);
    }

    @PutMapping("/{id}")
    public ResponseEntity<EntidadeDTO> update(
            @PathVariable Long id,
            @RequestBody @Validated(EntidadeDTO.Update.class) EntidadeDTO dto) {

        dto.setIdEntidade(id);
        return ResponseEntity.ok(service.update(id, dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}
