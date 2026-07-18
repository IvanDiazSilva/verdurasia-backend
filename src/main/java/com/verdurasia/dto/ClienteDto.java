package com.verdurasia.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

import java.time.LocalDateTime;

public class ClienteDto {

    // ---- Request ----

    public record CreateRequest(
            @NotBlank @Size(max = 150) String nombre,
            @NotBlank @Email @Size(max = 255) String email,
            @Size(max = 30) String telefono,
            String direccion
    ) {}

    public record UpdateRequest(
            @Size(max = 150) String nombre,
            @Email @Size(max = 255) String email,
            @Size(max = 30) String telefono,
            String direccion,
            Boolean activo
    ) {}

    // ---- Response ----

    public record Response(
            Long id,
            String nombre,
            String email,
            String telefono,
            String direccion,
            Boolean activo,
            LocalDateTime createdAt,
            LocalDateTime updatedAt
    ) {}
}
