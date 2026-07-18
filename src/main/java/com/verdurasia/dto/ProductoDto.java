package com.verdurasia.dto;

import jakarta.validation.constraints.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class ProductoDto {

    public record CreateRequest(
            @NotBlank @Size(max = 150) String nombre,
            String descripcion,
            @NotNull @DecimalMin("0.00") BigDecimal precio,
            @Min(0) Integer stock,
            @Size(max = 30) String unidad,
            Long categoriaId
    ) {}

    public record UpdateRequest(
            @Size(max = 150) String nombre,
            String descripcion,
            @DecimalMin("0.00") BigDecimal precio,
            @Min(0) Integer stock,
            @Size(max = 30) String unidad,
            Boolean activo,
            Long categoriaId
    ) {}

    public record Response(
            Long id,
            String nombre,
            String descripcion,
            BigDecimal precio,
            Integer stock,
            String unidad,
            Boolean activo,
            Long categoriaId,
            String categoriaNombre,
            LocalDateTime createdAt,
            LocalDateTime updatedAt
    ) {}
}
