package com.fantasi.fantasia.controller;

import com.fantasi.fantasia.repository.ProductoRepository;
import com.fantasi.fantasia.repository.VentaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/reportes")
@RequiredArgsConstructor
public class ReporteController {

    private final VentaRepository ventaRepository;
    private final ProductoRepository productoRepository;

    @GetMapping
    public String index(Model model) {

        model.addAttribute("totalVentas",
                ventaRepository.totalVentas());

        model.addAttribute("productosMasVendidos",
                ventaRepository.productosMasVendidos());

        model.addAttribute("stockBajo",
                productoRepository.findStockBajo());

        return "reportes/index";
    }
}