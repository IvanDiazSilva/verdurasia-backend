package com.verdurasia.dto;

import com.verdurasia.entity.Pedido;
import jakarta.validation.constraints.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

public class PedidoDto {

    public record ItemRequest(
            @NotNull Long productoId,
            @NotNull @Min(1) Integer cantidad
    ) {}

    public record CreateRequest(
            @NotNull Long clienteId,
            String notas,
            @NotEmpty List<ItemRequest> items
    ) {}

    public record UpdateEstadoRequest(
            @NotNull Pedido.Estado estado
    ) {}

    public record ItemResponse(
            Long id,
            Long productoId,
            String productoNombre,
            Integer cantidad,
            BigDecimal precioUnit,
            BigDecimal subtotal
    ) {}

    public record Response(
            Long id,
            Long clienteId,
            String clienteNombre,
            Pedido.Estado estado,
            BigDecimal total,
            String notas,
            List<ItemResponse> items,
            LocalDateTime createdAt,
            LocalDateTime updatedAt
    ) {}
}
