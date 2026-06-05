package com.fantasi.fantasia.controller;

import com.fantasi.fantasia.entity.Cliente;
import com.fantasi.fantasia.service.ClienteService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/clientes")
@RequiredArgsConstructor
public class ClienteController {

    private final ClienteService service;

    @GetMapping
    public String listar(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(required = false) String filtro,
            Model model
    ) {

        var clientes = service.listar(filtro, PageRequest.of(page, 8));

        model.addAttribute("clientes", clientes);
        model.addAttribute("filtro", filtro);

        return "clientes/lista";
    }

    @GetMapping("/nuevo")
    public String nuevo(Model model) {

        model.addAttribute("cliente", new Cliente());

        return "clientes/form";
    }

    @PostMapping("/guardar")
    public String guardar(
            @Valid @ModelAttribute Cliente cliente,
            BindingResult result
    ) {

        if (result.hasErrors()) {
            return "clientes/form";
        }

        service.guardar(cliente);

        return "redirect:/clientes";
    }

    @GetMapping("/editar/{id}")
    public String editar(@PathVariable Long id, Model model) {

        model.addAttribute("cliente", service.buscar(id));

        return "clientes/form";
    }

    @GetMapping("/eliminar/{id}")
    public String eliminar(@PathVariable Long id) {
        Cliente cliente = service.buscar(id);
        cliente.setActivo(false); // Solo lo desactivamos
        service.guardar(cliente);
        return "redirect:/clientes";
    }
}