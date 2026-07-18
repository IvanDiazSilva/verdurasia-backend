package com.verdurasia.service;

import com.verdurasia.dto.ProductoDto;
import com.verdurasia.entity.Categoria;
import com.verdurasia.entity.Producto;
import com.verdurasia.exception.ResourceNotFoundException;
import com.verdurasia.repository.CategoriaRepository;
import com.verdurasia.repository.ProductoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ProductoService {

    private final ProductoRepository repo;
    private final CategoriaRepository categoriaRepo;

    public Page<ProductoDto.Response> listar(Pageable pageable) {
        return repo.findAll(pageable).map(this::toResponse);
    }

    public Page<ProductoDto.Response> buscar(String nombre, Long categoriaId, Pageable pageable) {
        return repo.buscar(nombre, categoriaId, pageable).map(this::toResponse);
    }

    public ProductoDto.Response obtener(Long id) {
        return toResponse(findOrThrow(id));
    }

    @Transactional
    public ProductoDto.Response crear(ProductoDto.CreateRequest req) {
        Categoria cat = req.categoriaId() != null
                ? categoriaRepo.findById(req.categoriaId())
                        .orElseThrow(() -> new ResourceNotFoundException("Categoria", req.categoriaId()))
                : null;

        Producto p = Producto.builder()
                .nombre(req.nombre())
                .descripcion(req.descripcion())
                .precio(req.precio())
                .stock(req.stock() != null ? req.stock() : 0)
                .unidad(req.unidad() != null ? req.unidad() : "kg")
                .categoria(cat)
                .build();
        return toResponse(repo.save(p));
    }

    @Transactional
    public ProductoDto.Response actualizar(Long id, ProductoDto.UpdateRequest req) {
        Producto p = findOrThrow(id);
        if (req.nombre()      != null) p.setNombre(req.nombre());
        if (req.descripcion() != null) p.setDescripcion(req.descripcion());
        if (req.precio()      != null) p.setPrecio(req.precio());
        if (req.stock()       != null) p.setStock(req.stock());
        if (req.unidad()      != null) p.setUnidad(req.unidad());
        if (req.activo()      != null) p.setActivo(req.activo());
        if (req.categoriaId() != null) {
            Categoria cat = categoriaRepo.findById(req.categoriaId())
                    .orElseThrow(() -> new ResourceNotFoundException("Categoria", req.categoriaId()));
            p.setCategoria(cat);
        }
        return toResponse(repo.save(p));
    }

    @Transactional
    public void eliminar(Long id) {
        repo.delete(findOrThrow(id));
    }

    // ---- helpers ----

    private Producto findOrThrow(Long id) {
        return repo.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Producto", id));
    }

    private ProductoDto.Response toResponse(Producto p) {
        return new ProductoDto.Response(
                p.getId(), p.getNombre(), p.getDescripcion(),
                p.getPrecio(), p.getStock(), p.getUnidad(), p.getActivo(),
                p.getCategoria() != null ? p.getCategoria().getId() : null,
                p.getCategoria() != null ? p.getCategoria().getNombre() : null,
                p.getCreatedAt(), p.getUpdatedAt());
    }
}
