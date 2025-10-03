package com.example.lab06_20202218.Services;




import com.example.lab06_20202218.Entity.Usuario;
import com.example.lab06_20202218.Repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class CustomUserDetailsService implements UserDetailsService {


    @Autowired
    private UsuarioRepository usuarioRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Usuario usuario = usuarioRepository.findByCorreo(username).orElseThrow(() -> new UsernameNotFoundException("No encontrado"));
        return User.builder().username(usuario.getCorreo()).password(usuario.getPassword()).roles(usuario.getRol().getNombre()).disabled(usuario.getActivo()).build();
    }

}
