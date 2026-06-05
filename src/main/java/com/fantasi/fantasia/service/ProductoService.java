package com.fantasi.fantasia.service;

import com.fantasi.fantasia.entity.Producto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface ProductoService {

    Page<Producto> listar(String filtro, Pageable pageable);

    Producto guardar(Producto producto);

    Producto buscar(Long id);

    void eliminar(Long id);

    List<Producto> stockBajo();

    void descontarStock(Long productoId, int cantidad);

    List<Producto> listarTodos();

    List<Producto> listarActivos();
    
    Object listarAll();
}