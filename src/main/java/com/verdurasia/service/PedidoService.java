package com.verdurasia.service;

import com.verdurasia.dto.PedidoDto;
import com.verdurasia.entity.*;
import com.verdurasia.exception.ResourceNotFoundException;
import com.verdurasia.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class PedidoService {

    private final PedidoRepository pedidoRepo;
    private final ClienteRepository clienteRepo;
    private final ProductoRepository productoRepo;

    public Page<PedidoDto.Response> listar(Pageable pageable) {
        return pedidoRepo.findAll(pageable).map(this::toResponse);
    }

    public PedidoDto.Response obtener(Long id) {
        return toResponse(findOrThrow(id));
    }

    @Transactional
    public PedidoDto.Response crear(PedidoDto.CreateRequest req) {
        Cliente cliente = clienteRepo.findById(req.clienteId())
                .orElseThrow(() -> new ResourceNotFoundException("Cliente", req.clienteId()));

        Pedido pedido = Pedido.builder()
                .cliente(cliente)
                .notas(req.notas())
                .build();

        for (PedidoDto.ItemRequest ir : req.items()) {
            Producto producto = productoRepo.findById(ir.productoId())
                    .orElseThrow(() -> new ResourceNotFoundException("Producto", ir.productoId()));

            PedidoItem item = PedidoItem.builder()
                    .pedido(pedido)
                    .producto(producto)
                    .cantidad(ir.cantidad())
                    .precioUnit(producto.getPrecio())
                    .build();
            pedido.getItems().add(item);
        }

        pedido.recalcularTotal();
        return toResponse(pedidoRepo.save(pedido));
    }

    @Transactional
    public PedidoDto.Response cambiarEstado(Long id, PedidoDto.UpdateEstadoRequest req) {
        Pedido pedido = findOrThrow(id);
        pedido.setEstado(req.estado());
        return toResponse(pedidoRepo.save(pedido));
    }

    @Transactional
    public void eliminar(Long id) {
        pedidoRepo.delete(findOrThrow(id));
    }

    // ---- helpers ----

    private Pedido findOrThrow(Long id) {
        return pedidoRepo.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Pedido", id));
    }

    private PedidoDto.Response toResponse(Pedido p) {
        List<PedidoDto.ItemResponse> items = p.getItems().stream()
                .map(i -> new PedidoDto.ItemResponse(
                        i.getId(),
                        i.getProducto().getId(),
                        i.getProducto().getNombre(),
                        i.getCantidad(),
                        i.getPrecioUnit(),
                        i.getSubtotal()))
                .toList();

        return new PedidoDto.Response(
                p.getId(),
                p.getCliente().getId(),
                p.getCliente().getNombre(),
                p.getEstado(),
                p.getTotal(),
                p.getNotas(),
                items,
                p.getCreatedAt(),
                p.getUpdatedAt());
    }
}
