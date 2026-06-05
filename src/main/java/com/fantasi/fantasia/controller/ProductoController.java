package com.fantasi.fantasia.controller;

import com.fantasi.fantasia.entity.Producto;
import com.fantasi.fantasia.service.ProductoService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/productos")
@RequiredArgsConstructor
public class ProductoController {

    private final ProductoService service;

    // =========================
    // LISTAR PRODUCTOS
    // =========================
    @GetMapping
    public String listar(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(required = false) String filtro,
            Model model
    ) {

        var productos = service.listar(filtro, PageRequest.of(page, 8));

        model.addAttribute("productos", productos);
        model.addAttribute("filtro", filtro);

        return "productos/lista";
    }

    // =========================
    // FORMULARIO NUEVO
    // =========================
    @GetMapping("/nuevo")
    public String nuevo(Model model) {

        model.addAttribute("producto", new Producto());

        return "productos/form";
    }

    // =========================
    // GUARDAR PRODUCTO
    // =========================
    @PostMapping("/guardar")
    public String guardar(
            @Valid @ModelAttribute("producto") Producto producto,
            BindingResult result,
            Model model
    ) {

        // Validaciones
        if (result.hasErrors()) {

            model.addAttribute("producto", producto);

            return "productos/form";
        }

        service.guardar(producto);

        return "redirect:/productos";
    }

    // =========================
    // EDITAR PRODUCTO
    // =========================
    @GetMapping("/editar/{id}")
    public String editar(
            @PathVariable Long id,
            Model model
    ) {

        Producto producto = service.buscar(id);

        model.addAttribute("producto", producto);

        return "productos/form";
    }

    // =========================
// ELIMINAR PRODUCTO
// =========================
    @PostMapping("/eliminar/{id}")
    public String eliminarProducto(@PathVariable Long id) {
        System.out.println("DEBUG: Eliminando ID: " + id); // Mira esto en tu consola
        service.eliminar(id);
        return "redirect:/productos"; // Esto obliga a recargar la lista
    }
}