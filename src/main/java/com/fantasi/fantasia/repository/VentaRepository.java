// VentaRepository.java
package com.fantasi.fantasia.repository;

import com.fantasi.fantasia.entity.Venta;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

public interface VentaRepository extends JpaRepository<Venta, Long> {

    List<Venta> findByFechaVentaBetweenOrderByFechaVentaDesc(
            LocalDateTime inicio,
            LocalDateTime fin
    );

    @Query("""
        SELECT COALESCE(SUM(v.total), 0)
        FROM Venta v
    """)
    BigDecimal totalVentas();

    @Query("""
        SELECT d.producto.nombre, SUM(d.cantidad)
        FROM DetalleVenta d
        GROUP BY d.producto.nombre
        ORDER BY SUM(d.cantidad) DESC
    """)
    List<Object[]> productosMasVendidos();

    // Búsqueda combinada: Cliente + Rango de Fechas
    List<Venta> findByClienteNombreContainingIgnoreCaseAndFechaVentaBetween(String nombre, LocalDateTime inicio, LocalDateTime fin);

    // Búsqueda solo por fechas
    List<Venta> findByFechaVentaBetween(LocalDateTime inicio, LocalDateTime fin);

    List<Venta> findByEstadoTrue();
    // Y actualiza tus otros métodos de búsqueda para que incluyan "AndEstadoTrue":
    List<Venta> findByFechaVentaBetweenAndEstadoTrue(LocalDateTime inicio, LocalDateTime fin);

    List<Venta> findByClienteNombreContainingIgnoreCaseAndFechaVentaBetweenAndEstadoTrue(String nombre, LocalDateTime inicio, LocalDateTime fin);

    // En VentaRepository.java
    @Query("SELECT COALESCE(SUM(v.total), 0) FROM Venta v WHERE v.estado = true")
    BigDecimal totalVentasActivas();
}