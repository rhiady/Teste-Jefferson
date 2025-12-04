package com.resources;

import com.domains.dtos.ContaBancariaDTO;
import com.services.ContaBancariaService;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@Validated
@RestController
@RequestMapping("/api/v1/contas-bancarias")
public class ContaBancariaResource {

    private final ContaBancariaService service;

    public ContaBancariaResource(ContaBancariaService service) {
        this.service = service;
    }

    @GetMapping
    public ResponseEntity<List<ContaBancariaDTO>> list(
            @RequestParam(required = false) Long usuarioId) {

        List<ContaBancariaDTO> body;

        if (usuarioId != null) {
            body = service.findByUsuario(usuarioId);
        } else {
            body = service.findAll();
        }

        return ResponseEntity.ok(body);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ContaBancariaDTO> findById(@PathVariable Long id) {
        ContaBancariaDTO dto = service.findById(id);
        return ResponseEntity.ok(dto);
    }

    @PostMapping
    public ResponseEntity<ContaBancariaDTO> create(
            @RequestBody @Validated(ContaBancariaDTO.Create.class) ContaBancariaDTO dto) {

        ContaBancariaDTO created = service.create(dto);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(created.getIdContaBancaria())
                .toUri();
        return ResponseEntity.created(location).body(created);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ContaBancariaDTO> update(
            @PathVariable Long id,
            @RequestBody @Validated(ContaBancariaDTO.Update.class) ContaBancariaDTO dto) {

        dto.setIdContaBancaria(id);
        return ResponseEntity.ok(service.update(id, dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}
