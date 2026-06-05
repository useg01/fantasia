// UsuarioService.java
package com.fantasi.fantasia.service;

import com.fantasi.fantasia.entity.Usuario;

import java.util.List;

public interface UsuarioService {

    List<Usuario> listar();

    Usuario guardar(Usuario usuario);

    Usuario buscar(Long id);

    void eliminar(Long id);
}