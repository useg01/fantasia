package com.fantasi.fantasia.controller;

import com.fantasi.fantasia.entity.PedidoPersonalizado;
import com.fantasi.fantasia.service.ClienteService;
import com.fantasi.fantasia.service.PedidoService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/pedidos")
@RequiredArgsConstructor
public class PedidoController {

    private final PedidoService pedidoService;
    private final ClienteService clienteService;

    @GetMapping("/lista")
    public String listar(Model model) {
        // 1. Lista de pedidos personalizados para la tabla
        model.addAttribute("pedidos", pedidoService.listarTodos());

        // 2. Lista de clientes para el selector dropdown
        model.addAttribute("clientes", clienteService.listarTodos());

        // 3. Objeto limpio que usará el formulario modal (Ojo: PedidoPersonalizado)
        model.addAttribute("nuevoPedido", new PedidoPersonalizado());
        return "pedidos/lista";
    }

    @PostMapping("/guardar")
    public String guardar(
            @ModelAttribute("nuevoPedido") PedidoPersonalizado pedido,
            @RequestParam("clienteId") Long clienteId) {

        // CORREGIDO: Cambiado 'buscarPorId' por 'buscar' que es el método real de tu servicio
        com.fantasi.fantasia.entity.Cliente clienteAsignado = clienteService.buscar(clienteId);

        // Asignamos el cliente al pedido antes de guardarlo
        pedido.setCliente(clienteAsignado);

        // Guardamos el pedido personalizado
        pedidoService.guardar(pedido);

        return "redirect:/pedidos/lista";
    }

    @GetMapping("/entregar/{id}")
    public String entregar(@PathVariable Long id) {
        pedidoService.cambiarEstado(id, "ENTREGADO");
        return "redirect:/pedidos/lista";
    }

    @GetMapping("/eliminar/{id}")
    public String eliminar(@PathVariable Long id) {
        pedidoService.eliminar(id);
        return "redirect:/pedidos/lista";
    }

    @PostMapping("/cliente-rapido")
    public String guardarClienteRapido(
            @RequestParam("nombre") String nombre,
            @RequestParam("telefono") String telefono) {

        // 1. Instanciamos un objeto Cliente con los datos mínimos obligatorios de tu DB
        com.fantasi.fantasia.entity.Cliente nuevoCliente = new com.fantasi.fantasia.entity.Cliente();
        nuevoCliente.setNombre(nombre);
        nuevoCliente.setTelefono(telefono);
        // Nota: Tu prePersist() en Cliente.java se encargará de ponerle automáticamente la fecha_registro actual.

        // 2. Lo guardamos mediante el servicio existente
        clienteService.guardar(nuevoCliente);

        // 3. Refrescamos la pantalla de pedidos (ahora saldrá en el dropdown)
        return "redirect:/pedidos/lista";
    }
}