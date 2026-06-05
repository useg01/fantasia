package com.fantasi.fantasia.service;

import com.fantasi.fantasia.entity.PedidoPersonalizado;
import java.util.List;

public interface PedidoService {
    List<PedidoPersonalizado> listarTodos();

    // CORRECCIÓN AQUÍ: Cambiar Pedido por PedidoPersonalizado
    PedidoPersonalizado guardar(PedidoPersonalizado pedido);

    void cambiarEstado(Long id, String nuevoEstado);
    void eliminar(Long id);
}