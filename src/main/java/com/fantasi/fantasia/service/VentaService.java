package com.fantasi.fantasia.service;

import com.fantasi.fantasia.dto.VentaRequestDTO;
import com.fantasi.fantasia.entity.Venta;

import java.time.LocalDate;
import java.util.List;

public interface VentaService {
    Venta registrarVenta(VentaRequestDTO request);
    List<Venta> listar(); // Este es el método que usaremos
    Venta buscar(Long id);
    void eliminar(Long id);
    Object buscarConFiltros(String comprador, LocalDate fechaInicio, LocalDate fechaFin);
}