package com.fantasi.fantasia.repository;

import com.fantasi.fantasia.entity.PedidoPersonalizado;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface PedidoRepository extends JpaRepository<PedidoPersonalizado, Long> {

    long countByEstado(PedidoPersonalizado.Estado estado);

    // ✨ AÑADE ESTE MÉTODO TRÁEME-TODO-DE-GOLPE
    @Query("SELECT p FROM PedidoPersonalizado p JOIN FETCH p.cliente ORDER BY p.id DESC")
    List<PedidoPersonalizado> findAllConClientes();
}