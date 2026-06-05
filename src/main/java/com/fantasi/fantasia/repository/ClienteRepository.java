// ClienteRepository.java
package com.fantasi.fantasia.repository;

import com.fantasi.fantasia.entity.Cliente;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface ClienteRepository extends JpaRepository<Cliente, Long> {
    // Solo busca entre los que están activos
    Page<Cliente> findByNombreContainingIgnoreCaseAndActivoTrue(String nombre, Pageable pageable);

    // Sobrescribimos el findAll para que solo traiga activos
    Page<Cliente> findByActivoTrue(Pageable pageable);
    List<Cliente> findByActivoTrue();
    long countByActivoTrue();
}