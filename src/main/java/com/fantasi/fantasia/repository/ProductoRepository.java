package com.fantasi.fantasia.repository;

import com.fantasi.fantasia.entity.Producto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ProductoRepository extends JpaRepository<Producto, Long> {

    Page<Producto> findByEstadoTrue(Pageable pageable);

    // Método para paginación y búsqueda
    Page<Producto> findByNombreContainingIgnoreCaseAndEstadoTrue(
            String nombre,
            Pageable pageable
    );

    // Búsqueda de stock bajo con @Query explícito
    @Query("SELECT p FROM Producto p WHERE p.stock <= p.stockMinimo AND p.estado = true")
    List<Producto> findStockBajo();

    // Búsqueda de todos los productos activos con @Query explícito
    @Query("SELECT p FROM Producto p WHERE p.estado = true")
    List<Producto> findByEstadoTrue();

    // Cuenta productos activos
    long countByEstadoTrue();
}