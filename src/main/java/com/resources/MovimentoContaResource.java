package com.resources;

import com.domains.dtos.MovimentoContaDTO;
import com.services.MovimentoContaService;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@Validated
@RestController
@RequestMapping("/api/v1/movimentos-conta")
public class MovimentoContaResource {

    private final MovimentoContaService service;

    public MovimentoContaResource(MovimentoContaService service) {
        this.service = service;
    }

    @GetMapping
    public ResponseEntity<List<MovimentoContaDTO>> list(
            @RequestParam(required = false) Long contaId) {

        List<MovimentoContaDTO> body;
        if (contaId != null) {
            body = service.findByConta(contaId);
        } else {
            body = service.findAll();
        }
        return ResponseEntity.ok(body);
    }

    @GetMapping("/{id}")
    public ResponseEntity<MovimentoContaDTO> findById(@PathVariable Long id) {
        MovimentoContaDTO dto = service.findById(id);
        return ResponseEntity.ok(dto);
    }

    @PostMapping
    public ResponseEntity<MovimentoContaDTO> create(
            @RequestBody @Validated(MovimentoContaDTO.Create.class) MovimentoContaDTO dto) {

        MovimentoContaDTO created = service.create(dto);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(created.getIdMovimentoConta())
                .toUri();
        return ResponseEntity.created(location).body(created);
    }

    @PutMapping("/{id}")
    public ResponseEntity<MovimentoContaDTO> update(
            @PathVariable Long id,
            @RequestBody @Validated(MovimentoContaDTO.Update.class) MovimentoContaDTO dto) {

        dto.setIdMovimentoConta(id);
        return ResponseEntity.ok(service.update(id, dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}
