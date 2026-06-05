// DetalleVentaRepository.java
package com.fantasi.fantasia.repository;

import com.fantasi.fantasia.entity.DetalleVenta;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DetalleVentaRepository
        extends JpaRepository<DetalleVenta, Long> {
}