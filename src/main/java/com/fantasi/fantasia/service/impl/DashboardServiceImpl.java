package com.fantasi.fantasia.service.impl;

import com.fantasi.fantasia.dto.DashboardDTO;
import com.fantasi.fantasia.entity.PedidoPersonalizado;
import com.fantasi.fantasia.repository.*;
import com.fantasi.fantasia.service.DashboardService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.math.BigDecimal;

@Service
@RequiredArgsConstructor
public class DashboardServiceImpl implements DashboardService {

    private final VentaRepository ventaRepo;
    private final ProductoRepository productoRepo;
    private final ClienteRepository clienteRepo;
    private final PedidoRepository pedidoRepo;

    @Override
    public DashboardDTO obtenerDashboard() {
        return DashboardDTO.builder()
                // Usamos el nuevo total de ventas activas
                .totalVentas(obtenerTotalVentas())
                .totalClientes(clienteRepo.count())
                .totalProductos(productoRepo.count())
                .pedidosPendientes(pedidoRepo.countByEstado(PedidoPersonalizado.Estado.PENDIENTE))
                .stockBajo(productoRepo.findStockBajo())
                .build();
    }

    @Override
    public BigDecimal obtenerTotalVentas() {
        // Llama al método que filtra por estado = true en el repositorio
        return ventaRepo.totalVentasActivas();
    }
}