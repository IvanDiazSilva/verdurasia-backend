package com.verdurasia.controller;

import com.verdurasia.dto.OfertaDto;
import com.verdurasia.service.OfertaService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/ofertas")
@RequiredArgsConstructor
@Tag(name = "Ofertas", description = "Gestión de ofertas y descuentos")
public class OfertaController {

    private final OfertaService service;

    @GetMapping
    @Operation(summary = "Listar todas las ofertas")
    public Page<OfertaDto.Response> listar(@PageableDefault(size = 20) Pageable pageable) {
        return service.listar(pageable);
    }

    @GetMapping("/vigentes")
    @Operation(summary = "Listar ofertas vigentes (activas y dentro de fecha)")
    public List<OfertaDto.Response> vigentes() {
        return service.vigentes();
    }

    @GetMapping("/{id}")
    @Operation(summary = "Obtener una oferta por ID")
    public OfertaDto.Response obtener(@PathVariable Long id) {
        return service.obtener(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "Crear una nueva oferta")
    public OfertaDto.Response crear(@RequestBody @Valid OfertaDto.CreateRequest req) {
        return service.crear(req);
    }

    @PatchMapping("/{id}")
    @Operation(summary = "Actualizar parcialmente una oferta")
    public OfertaDto.Response actualizar(@PathVariable Long id,
                                         @RequestBody @Valid OfertaDto.UpdateRequest req) {
        return service.actualizar(id, req);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Eliminar una oferta")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        service.eliminar(id);
        return ResponseEntity.noContent().build();
    }
}
