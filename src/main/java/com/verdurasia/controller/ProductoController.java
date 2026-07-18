package com.verdurasia.controller;

import com.verdurasia.dto.ProductoDto;
import com.verdurasia.service.ProductoService;
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

@RestController
@RequestMapping("/api/productos")
@RequiredArgsConstructor
@Tag(name = "Productos", description = "Gestión de productos")
public class ProductoController {

    private final ProductoService service;

    @GetMapping
    @Operation(summary = "Listar / buscar productos")
    public Page<ProductoDto.Response> listar(
            @RequestParam(required = false) String nombre,
            @RequestParam(required = false) Long categoriaId,
            @PageableDefault(size = 20) Pageable pageable) {

        if (nombre != null || categoriaId != null) {
            return service.buscar(nombre, categoriaId, pageable);
        }
        return service.listar(pageable);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Obtener un producto por ID")
    public ProductoDto.Response obtener(@PathVariable Long id) {
        return service.obtener(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "Crear un nuevo producto")
    public ProductoDto.Response crear(@RequestBody @Valid ProductoDto.CreateRequest req) {
        return service.crear(req);
    }

    @PatchMapping("/{id}")
    @Operation(summary = "Actualizar parcialmente un producto")
    public ProductoDto.Response actualizar(@PathVariable Long id,
                                           @RequestBody @Valid ProductoDto.UpdateRequest req) {
        return service.actualizar(id, req);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Eliminar un producto")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        service.eliminar(id);
        return ResponseEntity.noContent().build();
    }
}
