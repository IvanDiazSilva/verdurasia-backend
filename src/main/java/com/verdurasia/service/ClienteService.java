package com.verdurasia.service;

import com.verdurasia.dto.ClienteDto;
import com.verdurasia.entity.Cliente;
import com.verdurasia.exception.ResourceNotFoundException;
import com.verdurasia.repository.ClienteRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ClienteService {

    private final ClienteRepository repo;

    public Page<ClienteDto.Response> listar(Pageable pageable) {
        return repo.findAll(pageable).map(this::toResponse);
    }

    public ClienteDto.Response obtener(Long id) {
        return toResponse(findOrThrow(id));
    }

    @Transactional
    public ClienteDto.Response crear(ClienteDto.CreateRequest req) {
        if (repo.existsByEmail(req.email())) {
            throw new IllegalArgumentException("Ya existe un cliente con el email: " + req.email());
        }
        Cliente c = Cliente.builder()
                .nombre(req.nombre())
                .email(req.email())
                .telefono(req.telefono())
                .direccion(req.direccion())
                .build();
        return toResponse(repo.save(c));
    }

    @Transactional
    public ClienteDto.Response actualizar(Long id, ClienteDto.UpdateRequest req) {
        Cliente c = findOrThrow(id);
        if (req.nombre()    != null) c.setNombre(req.nombre());
        if (req.email()     != null) c.setEmail(req.email());
        if (req.telefono()  != null) c.setTelefono(req.telefono());
        if (req.direccion() != null) c.setDireccion(req.direccion());
        if (req.activo()    != null) c.setActivo(req.activo());
        return toResponse(repo.save(c));
    }

    @Transactional
    public void eliminar(Long id) {
        repo.delete(findOrThrow(id));
    }

    // ---- helpers ----

    private Cliente findOrThrow(Long id) {
        return repo.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Cliente", id));
    }

    private ClienteDto.Response toResponse(Cliente c) {
        return new ClienteDto.Response(
                c.getId(), c.getNombre(), c.getEmail(),
                c.getTelefono(), c.getDireccion(), c.getActivo(),
                c.getCreatedAt(), c.getUpdatedAt());
    }
}
