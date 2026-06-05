package com.fantasi.fantasia.service;

import com.fantasi.fantasia.dto.DashboardDTO;
import java.math.BigDecimal;

public interface DashboardService {
    DashboardDTO obtenerDashboard();
    BigDecimal obtenerTotalVentas();
}