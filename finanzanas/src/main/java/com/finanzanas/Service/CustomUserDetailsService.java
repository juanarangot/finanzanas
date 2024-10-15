package com.finanzanas.Service;

import com.finanzanas.Entity.User;
import com.finanzanas.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Primary;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@Primary
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository; // Repositorio de usuarios

    @Autowired
    @Qualifier("customPasswordEncoder")
    private PasswordEncoder passwordEncoder; // Encriptador de contraseñas

    @Override
    public UserDetails loadUserByUsername(String correo) throws UsernameNotFoundException {
        User user = userRepository.findByCorreo(correo)
                .orElseThrow(() -> new UsernameNotFoundException("Usuario no encontrado con el correo: " + correo));

        return org.springframework.security.core.userdetails.User.builder()
                .username(user.getCorreo())
                .password(user.getPassword())
                .roles("USER")  // Puedes ajustar roles según sea necesario
                .build();
    }

    public void registrarUsuario(User usuario) {
        usuario.setPassword(passwordEncoder.encode(usuario.getPassword()));  // Encriptar la contraseña
        userRepository.save(usuario);
    }
}
