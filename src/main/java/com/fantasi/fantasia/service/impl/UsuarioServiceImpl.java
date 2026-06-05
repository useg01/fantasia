// UsuarioServiceImpl.java
package com.fantasi.fantasia.service.impl;

import com.fantasi.fantasia.entity.Usuario;
import com.fantasi.fantasia.repository.UsuarioRepository;
import com.fantasi.fantasia.service.UsuarioService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UsuarioServiceImpl implements UsuarioService {

    private final UsuarioRepository repo;
    private final PasswordEncoder passwordEncoder;

    @Override
    public List<Usuario> listar() {
        return repo.findAll();
    }

    @Override
    public Usuario guardar(Usuario usuario) {

        usuario.setPassword(
                passwordEncoder.encode(usuario.getPassword())
        );

        return repo.save(usuario);
    }

    @Override
    public Usuario buscar(Long id) {
        return repo.findById(id)
                .orElseThrow(() ->
                        new RuntimeException("Usuario no encontrado"));
    }

    @Override
    public void eliminar(Long id) {
        repo.deleteById(id);
    }
}