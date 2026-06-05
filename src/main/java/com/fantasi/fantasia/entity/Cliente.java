package com.fantasi.fantasia.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;
import java.time.LocalDate;

@Entity
@Table(name = "clientes")
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class Cliente {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private boolean activo = true;
    
    @NotBlank @Column(nullable = false, length = 100)
    private String nombre;

    @NotBlank @Pattern(regexp = "\\d{6,15}", message = "Teléfono inválido")
    @Column(nullable = false, length = 15)
    private String telefono;

    @Email(message = "Correo inválido")
    @Column(length = 100)
    private String correo;

    @Column(length = 200)
    private String direccion;

    @Column(nullable = false)
    private LocalDate fechaRegistro;

    @PrePersist
    public void prePersist() {
        if (fechaRegistro == null) fechaRegistro = LocalDate.now();
    }
}