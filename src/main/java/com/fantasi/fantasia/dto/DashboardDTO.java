package com.fantasi.fantasia.dto;

import com.fantasi.fantasia.entity.Producto;
import lombok.Builder;
import lombok.Data;
import java.math.BigDecimal;
import java.util.List;

@Data
@Builder
public class DashboardDTO {
    private BigDecimal totalVentas;
    private long totalClientes;
    private long totalProductos;
    private long pedidosPendientes;

    // CAMBIO: Ahora pasamos la lista completa para que el HTML la pueda recorrer
    private List<Producto> stockBajo;
}