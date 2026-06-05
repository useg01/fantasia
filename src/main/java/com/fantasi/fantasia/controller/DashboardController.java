package com.fantasi.fantasia.controller;

import com.fantasi.fantasia.dto.DashboardDTO;
import com.fantasi.fantasia.service.ClienteService;
import com.fantasi.fantasia.service.DashboardService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@RequiredArgsConstructor
public class DashboardController {

    // Inyectamos el Service en lugar de los Repositorios
    private final DashboardService dashboardService;
    private final ClienteService clienteService;

    @GetMapping("/dashboard")
    public String dashboard(Model model) {
        // Ahora sí, esta variable existe y Spring la llenará automáticamente
        DashboardDTO dto = dashboardService.obtenerDashboard();

        model.addAttribute("totalVentas", dto.getTotalVentas());
        model.addAttribute("stockBajo", dto.getStockBajo());
        model.addAttribute("pedidosPendientes", dto.getPedidosPendientes());
        model.addAttribute("totalClientes", clienteService.contarActivos()); // Ya no debería dar error

        return "dashboard";
    }
}