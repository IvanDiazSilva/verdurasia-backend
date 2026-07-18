package com.verdurasia.service;

import com.verdurasia.dto.OfertaDto;
import com.verdurasia.entity.Oferta;
import com.verdurasia.entity.Producto;
import com.verdurasia.exception.ResourceNotFoundException;
import com.verdurasia.repository.OfertaRepository;
import com.verdurasia.repository.ProductoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class OfertaService {

    private final OfertaRepository repo;
    private final ProductoRepository productoRepo;

    public Page<OfertaDto.Response> listar(Pageable pageable) {
        return repo.findAll(pageable).map(this::toResponse);
    }

    public List<OfertaDto.Response> vigentes() {
        return repo.findVigentes(LocalDate.now()).stream().map(this::toResponse).toList();
    }

    public OfertaDto.Response obtener(Long id) {
        return toResponse(findOrThrow(id));
    }

    @Transactional
    public OfertaDto.Response crear(OfertaDto.CreateRequest req) {
        Producto producto = req.productoId() != null
                ? productoRepo.findById(req.productoId())
                        .orElseThrow(() -> new ResourceNotFoundException("Producto", req.productoId()))
                : null;

        Oferta o = Oferta.builder()
                .nombre(req.nombre())
                .descripcion(req.descripcion())
                .descuento(req.descuento())
                .tipo(req.tipo() != null ? req.tipo() : Oferta.Tipo.PORCENTAJE)
                .fechaInicio(req.fechaInicio())
                .fechaFin(req.fechaFin())
                .producto(producto)
                .build();
        return toResponse(repo.save(o));
    }

    @Transactional
    public OfertaDto.Response actualizar(Long id, OfertaDto.UpdateRequest req) {
        Oferta o = findOrThrow(id);
        if (req.nombre()      != null) o.setNombre(req.nombre());
        if (req.descripcion() != null) o.setDescripcion(req.descripcion());
        if (req.descuento()   != null) o.setDescuento(req.descuento());
        if (req.tipo()        != null) o.setTipo(req.tipo());
        if (req.fechaInicio() != null) o.setFechaInicio(req.fechaInicio());
        if (req.fechaFin()    != null) o.setFechaFin(req.fechaFin());
        if (req.activa()      != null) o.setActiva(req.activa());
        if (req.productoId()  != null) {
            Producto p = productoRepo.findById(req.productoId())
                    .orElseThrow(() -> new ResourceNotFoundException("Producto", req.productoId()));
            o.setProducto(p);
        }
        return toResponse(repo.save(o));
    }

    @Transactional
    public void eliminar(Long id) {
        repo.delete(findOrThrow(id));
    }

    // ---- helpers ----

    private Oferta findOrThrow(Long id) {
        return repo.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Oferta", id));
    }

    private OfertaDto.Response toResponse(Oferta o) {
        return new OfertaDto.Response(
                o.getId(), o.getNombre(), o.getDescripcion(),
                o.getDescuento(), o.getTipo(),
                o.getFechaInicio(), o.getFechaFin(), o.getActiva(),
                o.getProducto() != null ? o.getProducto().getId() : null,
                o.getProducto() != null ? o.getProducto().getNombre() : null,
                o.getCreatedAt(), o.getUpdatedAt());
    }
}
