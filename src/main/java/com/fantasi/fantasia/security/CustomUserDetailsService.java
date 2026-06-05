package com.fantasi.fantasia.security;

import com.fantasi.fantasia.entity.Usuario;
import com.fantasi.fantasia.repository.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.*;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

    private final UsuarioRepository repo;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Usuario u = repo.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("Usuario no encontrado"));

        return new User(
                u.getUsername(),
                u.getPassword(),
                u.getActivo(), true, true, true,
                List.of(new SimpleGrantedAuthority("ROLE_" + u.getRol().name()))
        );
    }
}