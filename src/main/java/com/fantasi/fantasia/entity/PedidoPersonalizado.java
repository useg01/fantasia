package com.fantasi.fantasia.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;
import java.time.LocalDate;

@Entity
@Table(name = "pedidos_personalizados")
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class PedidoPersonalizado {

    public enum Estado { PENDIENTE, EN_PROCESO, LISTO, ENTREGADO }

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "cliente_id", nullable = false)
    private Cliente cliente;

    @NotBlank
    @Column(nullable = false, length = 500)
    private String descripcion;

    @Column(nullable = false)
    private LocalDate fechaRegistro;

    @NotNull
    @Column(nullable = false)
    private LocalDate fechaEntrega;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Estado estado;

    @Column(length = 500)
    private String observaciones;

    @PrePersist
    public void prePersist() {
        if (fechaRegistro == null) fechaRegistro = LocalDate.now();
        if (estado == null) estado = Estado.PENDIENTE;
    }
}