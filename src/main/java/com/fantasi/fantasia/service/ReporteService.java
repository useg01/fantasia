// ReporteService.java
package com.fantasi.fantasia.service;

import java.math.BigDecimal;
import java.util.List;

public interface ReporteService {

    BigDecimal totalVentas();

    List<Object[]> productosMasVendidos();
}