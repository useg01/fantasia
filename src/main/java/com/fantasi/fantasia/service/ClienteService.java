// ClienteService.java
package com.fantasi.fantasia.service;

import com.fantasi.fantasia.entity.Cliente;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface ClienteService {

    Page<Cliente> listar(String filtro, Pageable pageable);

    List<Cliente> listarTodos();

    Cliente guardar(Cliente cliente);

    Cliente buscar(Long id);

    void eliminar(Long id);

    Object listarAll();

    long contarActivos();
}