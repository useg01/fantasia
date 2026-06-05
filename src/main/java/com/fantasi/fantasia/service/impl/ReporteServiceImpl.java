// ReporteServiceImpl.java
package com.fantasi.fantasia.service.impl;

import com.fantasi.fantasia.repository.VentaRepository;
import com.fantasi.fantasia.service.ReporteService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ReporteServiceImpl implements ReporteService {

    private final VentaRepository ventaRepo;

    @Override
    public BigDecimal totalVentas() {
        return ventaRepo.totalVentas();
    }

    @Override
    public List<Object[]> productosMasVendidos() {
        return ventaRepo.productosMasVendidos();
    }
}