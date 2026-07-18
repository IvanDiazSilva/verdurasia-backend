package com.verdurasia.service;

import com.verdurasia.entity.Categoria;
import com.verdurasia.exception.ResourceNotFoundException;
import com.verdurasia.repository.CategoriaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CategoriaService {

    private final CategoriaRepository repo;

    public Page<Categoria> listar(Pageable pageable) {
        return repo.findAll(pageable);
    }

    public Categoria obtener(Long id) {
        return repo.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Categoria", id));
    }

    @Transactional
    public Categoria crear(Categoria categoria) {
        if (repo.existsByNombreIgnoreCase(categoria.getNombre())) {
            throw new IllegalArgumentException(
                    "Ya existe una categoría con el nombre: " + categoria.getNombre());
        }
        return repo.save(categoria);
    }

    @Transactional
    public Categoria actualizar(Long id, Categoria datos) {
        Categoria existente = obtener(id);
        existente.setNombre(datos.getNombre());
        existente.setDescripcion(datos.getDescripcion());
        return repo.save(existente);
    }

    @Transactional
    public void eliminar(Long id) {
        repo.delete(obtener(id));
    }
}
