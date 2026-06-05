package com.fantasi.fantasia.dto;

import lombok.Data;
import java.util.List;

@Data
public class VentaRequestDTO {
    private Long idCliente;
    private List<DetalleVentaRequestDTO> detalles;
}