// ProductoServiceImpl.java
package com.fantasi.fantasia.service.impl;

import com.fantasi.fantasia.entity.Producto;
import com.fantasi.fantasia.repository.ProductoRepository;
import com.fantasi.fantasia.service.ProductoService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductoServiceImpl implements ProductoService {

    private final ProductoRepository repo;

    @Override
    public Page<Producto> listar(String filtro, Pageable pageable) {
        if (filtro == null || filtro.isBlank()) {
            // Ahora esto funcionará porque lo definimos arriba
            return repo.findByEstadoTrue(pageable);
        }
        return repo.findByNombreContainingIgnoreCaseAndEstadoTrue(filtro, pageable);
    }

    @Override
    public Producto guardar(Producto producto) {
        return repo.save(producto);
    }

    @Override
    public Producto buscar(Long id) {
        return repo.findById(id)
                .orElseThrow(() ->
                        new RuntimeException("Producto no encontrado"));
    }

    @Override
    public void eliminar(Long id) {

        Producto producto = buscar(id);

        producto.setEstado(false);

        repo.save(producto);
    }

    @Override
    public List<Producto> stockBajo() {
        return repo.findStockBajo();
    }

    @Override
    public void descontarStock(Long productoId, int cantidad) {

        Producto producto = buscar(productoId);

        if (producto.getStock() < cantidad) {
            throw new RuntimeException(
                    "Stock insuficiente para " + producto.getNombre()
            );
        }

        producto.setStock(producto.getStock() - cantidad);

        repo.save(producto);
    }

    @Override
    public List<Producto> listarTodos() {
        // ESTA ES LA CORRECCIÓN:
        // Usamos el repositorio para traer todos los productos reales
        return repo.findAll();
    }
    @Override
    public List<Producto> listarActivos() {
        return repo.findByEstadoTrue();
    }

    @Override
    public Object listarAll() {
        return null;
    }
}