// ClienteServiceImpl.java
package com.fantasi.fantasia.service.impl;

import com.fantasi.fantasia.entity.Cliente;
import com.fantasi.fantasia.repository.ClienteRepository;
import com.fantasi.fantasia.service.ClienteService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ClienteServiceImpl implements ClienteService {

    private final ClienteRepository repo;

    @Override
    public long contarActivos() {
        return repo.countByActivoTrue();
    }

    @Override
    public Page<Cliente> listar(String filtro, Pageable pageable) {
        if (filtro == null || filtro.isBlank()) {
            // Asegúrate de usar el método que filtra por activos
            return repo.findByActivoTrue(pageable);
        }
        return repo.findByNombreContainingIgnoreCaseAndActivoTrue(filtro, pageable);
    }

    // En ClienteServiceImpl.java
    @Override
    public List<Cliente> listarTodos() {
        // Cambiamos findAll() por un nuevo método que filtre por activos
        return repo.findByActivoTrue();
    }

    @Override
    public Cliente guardar(Cliente cliente) {
        if (cliente.getId() != null) {
            // Buscamos el cliente original en la base de datos para obtener su fecha
            Cliente clienteExistente = repo.findById(cliente.getId())
                    .orElseThrow(() -> new RuntimeException("Cliente no encontrado"));

            // Mantenemos la fecha original
            cliente.setFechaRegistro(clienteExistente.getFechaRegistro());
        } else {
            // Es un cliente nuevo, asignamos fecha actual
            cliente.setFechaRegistro(java.time.LocalDate.now());
        }
        return repo.save(cliente);
    }

    @Override
    public Cliente buscar(Long id) {
        return repo.findById(id)
                .orElseThrow(() ->
                        new RuntimeException("Cliente no encontrado"));
    }

    @Override
    public void eliminar(Long id) {
        Cliente cliente = buscar(id);
        cliente.setActivo(false); // Borrado lógico
        repo.save(cliente);
    }

    @Override
    public Object listarAll() {
        return null;
    }
}