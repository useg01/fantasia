package com.fantasi.fantasia.service.impl;

import com.fantasi.fantasia.entity.PedidoPersonalizado;
import com.fantasi.fantasia.repository.PedidoRepository;
import com.fantasi.fantasia.service.PedidoService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
@RequiredArgsConstructor
public class PedidoServiceImpl implements PedidoService {

    private final PedidoRepository repo;

    @Override
    public List<PedidoPersonalizado> listarTodos() {
        // CORREGIDO: Cambiamos repo.findAll() por nuestro nuevo método con JOIN FETCH
        return repo.findAllConClientes();
    }

    @Override
    public PedidoPersonalizado guardar(PedidoPersonalizado pedido) {
        // CORREGIDO: Convertimos el String "PENDIENTE" al tipo de Enum que requiere tu entidad
        if (pedido.getEstado() == null) {
            pedido.setEstado(PedidoPersonalizado.Estado.PENDIENTE);
        }
        return repo.save(pedido);
    }

    @Override
    public void cambiarEstado(Long id, String nuevoEstado) {
        PedidoPersonalizado p = repo.findById(id)
                .orElseThrow(() -> new RuntimeException("Pedido no encontrado"));

        // CORREGIDO: Convertimos el String dinámico recibido al Enum correspondiente
        p.setEstado(PedidoPersonalizado.Estado.valueOf(nuevoEstado.toUpperCase()));
        repo.save(p);
    }

    @Override
    public void eliminar(Long id) {
        repo.deleteById(id);
    }
}