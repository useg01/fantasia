// ReporteDTO.java
package com.fantasi.fantasia.dto;

import lombok.*;

import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ReporteDTO {

    private String nombreProducto;

    private Integer cantidadVendida;

    private BigDecimal totalGenerado;
}