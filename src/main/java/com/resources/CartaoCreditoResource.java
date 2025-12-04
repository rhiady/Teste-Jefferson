package com.resources;

import com.domains.dtos.CartaoCreditoDTO;
import com.services.CartaoCreditoService;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@Validated
@RestController
@RequestMapping("/api/v1/cartoes-credito")
public class CartaoCreditoResource {

    private final CartaoCreditoService service;

    public CartaoCreditoResource(CartaoCreditoService service) {
        this.service = service;
    }

    @GetMapping
    public ResponseEntity<List<CartaoCreditoDTO>> list(
            @RequestParam(required = false) Long usuarioId) {

        List<CartaoCreditoDTO> body;
        if (usuarioId != null) {
            body = service.findByUsuario(usuarioId);
        } else {
            body = service.findAll();
        }
        return ResponseEntity.ok(body);
    }

    @GetMapping("/{id}")
    public ResponseEntity<CartaoCreditoDTO> findById(@PathVariable Long id) {
        CartaoCreditoDTO dto = service.findById(id);
        return ResponseEntity.ok(dto);
    }

    @PostMapping
    public ResponseEntity<CartaoCreditoDTO> create(
            @RequestBody @Validated(CartaoCreditoDTO.Create.class) CartaoCreditoDTO dto) {

        CartaoCreditoDTO created = service.create(dto);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(created.getIdCartaoCredito())
                .toUri();
        return ResponseEntity.created(location).body(created);
    }

    @PutMapping("/{id}")
    public ResponseEntity<CartaoCreditoDTO> update(
            @PathVariable Long id,
            @RequestBody @Validated(CartaoCreditoDTO.Update.class) CartaoCreditoDTO dto) {

        dto.setIdCartaoCredito(id);
        return ResponseEntity.ok(service.update(id, dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}
