package com.fantasi.fantasia.controller;

import com.fantasi.fantasia.dto.VentaRequestDTO;
import com.fantasi.fantasia.entity.Venta;
import com.fantasi.fantasia.service.VentaService;
import com.fantasi.fantasia.service.ClienteService;
import com.fantasi.fantasia.service.ProductoService;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.Map;

@Controller
@RequestMapping("/ventas")
@RequiredArgsConstructor
public class VentaController {

    private final VentaService ventaService;
    private final ClienteService clienteService;
    private final ProductoService productoService;

    /**
     * Muestra el formulario para registrar una nueva venta
     * Ruta: GET /ventas/nueva
     */
    @GetMapping("/nueva")
    public String nuevaVenta(Model model) {

        // 1. Objeto para el formulario
        model.addAttribute("venta", new Venta());

        // 2. Clientes (estos no cambian)
        model.addAttribute("clientes", clienteService.listarTodos());

        // --- CORRECCIÓN AQUÍ ---
        // Cambiamos listarTodos() por listarActivos()
        model.addAttribute("productos", productoService.listarActivos());

        return "ventas/nueva";
    }


    /**
     * Muestra el historial con el listado de todas las ventas
     * Ruta: GET /ventas/lista
     */
    // Cambia esto en tu VentaController para que coincida con el enlace del Dashboard
    @GetMapping("/lista")
    public String listarVentas(
            @RequestParam(required = false) String comprador,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fechaInicio,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fechaFin,
            Model model) {

        if ((comprador != null && !comprador.isEmpty()) || fechaInicio != null || fechaFin != null) {
            model.addAttribute("listaVentas", ventaService.buscarConFiltros(comprador, fechaInicio, fechaFin));
        } else {
            model.addAttribute("listaVentas", ventaService.listar());
        }
        return "ventas/historial";
    }

    /**
     * Procesa el envío del formulario mediante fetch (JSON)
     * Ruta: POST /ventas/guardar
     */
    @PostMapping("/guardar")
    @ResponseBody
    public ResponseEntity<?> guardarVenta(@RequestBody VentaRequestDTO request) {
        try {
            Venta venta = ventaService.registrarVenta(request);
return ResponseEntity.ok(Map.of("id", venta.getId(), "mensaje", "Venta registrada con éxito"));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(Map.of("error", e.getMessage()));
        }
    }

    @PostMapping("/eliminar/{id}")
    public String eliminarVenta(@PathVariable Long id) {
        ventaService.eliminar(id);
        return "redirect:/ventas/lista";
    }
}