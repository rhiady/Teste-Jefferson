package com.resources;

import com.domains.dtos.FaturaCartaoDTO;
import com.services.FaturaCartaoService;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@Validated
@RestController
@RequestMapping("/api/v1/faturas-cartao")
public class FaturaCartaoResource {

    private final FaturaCartaoService service;

    public FaturaCartaoResource(FaturaCartaoService service) {
        this.service = service;
    }

    @GetMapping
    public ResponseEntity<List<FaturaCartaoDTO>> list(
            @RequestParam(required = false) Long cartaoId) {

        List<FaturaCartaoDTO> body;
        if (cartaoId != null) {
            body = service.findByCartao(cartaoId);
        } else {
            body = service.findAll();
        }
        return ResponseEntity.ok(body);
    }

    @GetMapping("/{id}")
    public ResponseEntity<FaturaCartaoDTO> findById(@PathVariable Long id) {
        FaturaCartaoDTO dto = service.findById(id);
        return ResponseEntity.ok(dto);
    }

    @PostMapping
    public ResponseEntity<FaturaCartaoDTO> create(
            @RequestBody @Validated(FaturaCartaoDTO.Create.class) FaturaCartaoDTO dto) {

        FaturaCartaoDTO created = service.create(dto);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(created.getIdFaturaCartao())
                .toUri();
        return ResponseEntity.created(location).body(created);
    }

    @PutMapping("/{id}")
    public ResponseEntity<FaturaCartaoDTO> update(
            @PathVariable Long id,
            @RequestBody @Validated(FaturaCartaoDTO.Update.class) FaturaCartaoDTO dto) {

        dto.setIdFaturaCartao(id);
        return ResponseEntity.ok(service.update(id, dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}
