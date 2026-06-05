package com.fantasi.fantasia.service.impl;

import com.fantasi.fantasia.dto.DetalleVentaRequestDTO;
import com.fantasi.fantasia.dto.VentaRequestDTO;
import com.fantasi.fantasia.entity.*;
import com.fantasi.fantasia.repository.*;
import com.fantasi.fantasia.service.VentaService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class VentaServiceImpl implements VentaService {

    private final VentaRepository ventaRepo;
    private final ProductoRepository productoRepo;
    private final ClienteRepository clienteRepo;

    @Override
    @Transactional
    public Venta registrarVenta(VentaRequestDTO request) {
        Cliente cliente = null;
if (request.getIdCliente() != null) {
    cliente = clienteRepo.findById(request.getIdCliente())
            .orElseThrow(() -> new RuntimeException("Cliente no encontrado"));
}

Venta venta = new Venta();
venta.setCliente(cliente);
        venta.setFechaVenta(LocalDateTime.now());
        venta.setEstado(true); // Aseguramos que inicie activa
        venta.setDetalles(new ArrayList<>());

        BigDecimal totalVenta = BigDecimal.ZERO;

        for (DetalleVentaRequestDTO detDTO : request.getDetalles()) {
            Producto producto = productoRepo.findById(detDTO.getIdProducto())
                    .orElseThrow(() -> new RuntimeException("Producto no encontrado"));

            if (producto.getStock() < detDTO.getCantidad()) {
                throw new RuntimeException("Stock insuficiente para: " + producto.getNombre());
            }

            // Restar stock al vender
            producto.setStock(producto.getStock() - detDTO.getCantidad());
            productoRepo.save(producto);

            DetalleVenta detalle = new DetalleVenta();
            detalle.setProducto(producto);
            detalle.setCantidad(detDTO.getCantidad());
            detalle.setPrecioUnitario(producto.getPrecio());
            BigDecimal subtotal = producto.getPrecio().multiply(BigDecimal.valueOf(detDTO.getCantidad()));
            detalle.setSubtotal(subtotal);
            detalle.setVenta(venta);

            venta.getDetalles().add(detalle);
            totalVenta = totalVenta.add(subtotal);
        }

        venta.setTotal(totalVenta);
        return ventaRepo.save(venta);
    }

    @Override
    public List<Venta> listar() {
        // Filtramos solo las ventas que siguen activas
        return ventaRepo.findByEstadoTrue();
    }

    @Override
    public Venta buscar(Long id) {
        return ventaRepo.findById(id).orElse(null);
    }

    @Override
    public List<Venta> buscarConFiltros(String nombre, LocalDate fechaInicio, LocalDate fechaFin) {
        LocalDateTime inicio = (fechaInicio != null) ? fechaInicio.atStartOfDay() : LocalDateTime.of(2000, 1, 1, 0, 0);
        LocalDateTime fin = (fechaFin != null) ? fechaFin.atTime(LocalTime.MAX) : LocalDateTime.now().plusDays(1);

        if (nombre != null && !nombre.isEmpty()) {
            return ventaRepo.findByClienteNombreContainingIgnoreCaseAndFechaVentaBetweenAndEstadoTrue(nombre, inicio, fin);
        } else {
            return ventaRepo.findByFechaVentaBetweenAndEstadoTrue(inicio, fin);
        }
    }

    @Override
    @Transactional
    public void eliminar(Long id) {
        Venta venta = ventaRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("Venta no encontrada"));

        // 1. Devolver stock al inventario
        for (DetalleVenta detalle : venta.getDetalles()) {
            Producto producto = detalle.getProducto();
            producto.setStock(producto.getStock() + detalle.getCantidad());
            productoRepo.save(producto);
        }

        // 2. Borrado lógico
        venta.setEstado(false);
        ventaRepo.save(venta);
    }
}