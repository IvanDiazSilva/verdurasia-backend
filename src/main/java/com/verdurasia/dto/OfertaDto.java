package com.verdurasia.dto;

import com.verdurasia.entity.Oferta;
import jakarta.validation.constraints.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

public class OfertaDto {

    public record CreateRequest(
            @NotBlank @Size(max = 150) String nombre,
            String descripcion,
            @NotNull @DecimalMin("0.01") @DecimalMax("100.00") BigDecimal descuento,
            Oferta.Tipo tipo,
            @NotNull LocalDate fechaInicio,
            @NotNull LocalDate fechaFin,
            Long productoId
    ) {}

    public record UpdateRequest(
            @Size(max = 150) String nombre,
            String descripcion,
            @DecimalMin("0.01") @DecimalMax("100.00") BigDecimal descuento,
            Oferta.Tipo tipo,
            LocalDate fechaInicio,
            LocalDate fechaFin,
            Boolean activa,
            Long productoId
    ) {}

    public record Response(
            Long id,
            String nombre,
            String descripcion,
            BigDecimal descuento,
            Oferta.Tipo tipo,
            LocalDate fechaInicio,
            LocalDate fechaFin,
            Boolean activa,
            Long productoId,
            String productoNombre,
            LocalDateTime createdAt,
            LocalDateTime updatedAt
    ) {}
}
