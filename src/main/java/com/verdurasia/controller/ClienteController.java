package com.verdurasia.controller;

import com.verdurasia.dto.ClienteDto;
import com.verdurasia.service.ClienteService;
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
@RequestMapping("/api/clientes")
@RequiredArgsConstructor
@Tag(name = "Clientes", description = "Gestión de clientes")
public class ClienteController {

    private final ClienteService service;

    @GetMapping
    @Operation(summary = "Listar todos los clientes")
    public Page<ClienteDto.Response> listar(@PageableDefault(size = 20) Pageable pageable) {
        return service.listar(pageable);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Obtener un cliente por ID")
    public ClienteDto.Response obtener(@PathVariable Long id) {
        return service.obtener(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "Crear un nuevo cliente")
    public ClienteDto.Response crear(@RequestBody @Valid ClienteDto.CreateRequest req) {
        return service.crear(req);
    }

    @PatchMapping("/{id}")
    @Operation(summary = "Actualizar parcialmente un cliente")
    public ClienteDto.Response actualizar(@PathVariable Long id,
                                          @RequestBody @Valid ClienteDto.UpdateRequest req) {
        return service.actualizar(id, req);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Eliminar un cliente")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        service.eliminar(id);
        return ResponseEntity.noContent().build();
    }
}
