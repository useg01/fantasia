package com.fantasi.fantasia.entity;

import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDate;

@Entity
@Table(name = "pedidos")
@Data
public class Pedido {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 500)
    private String descripcion;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "cliente_id", nullable = false)
    private Cliente cliente;

    @Column(nullable = false)
    private LocalDate fechaEntrega;

    @Column(nullable = false)
    private String viabilidad; // Ejemplo: "VIABLE", "EN EVALUACIÓN", "NO VIABLE"

    @Column(nullable = false)
    private String estado; // Ejemplo: "PENDIENTE", "ENTREGADO", "CANCELADO"
}