package com.finanzanas.Service;

import com.finanzanas.Entity.User;
import com.finanzanas.Repository.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Service
public class UserService implements UserDetailsService {

    @Autowired
    private CustomUserDetailsService customUserDetailsService; // Si eliges este como principal


    @Autowired
    private UserRepository userRepository;

    @Autowired
    @Qualifier("customPasswordEncoder")
    private PasswordEncoder passwordEncoder;

//    @Transactional
//    public User crearUsuario(User usuario) {
////        !!! TODO: NO SE SI SE GENERE ALGUN ERROR
//        usuario.setPassword(passwordEncoder.encode(usuario.getPassword()));
//        return userRepository.save(usuario);
//    }

    @Transactional
    public User registrarUsuario(User usuario) {
//        !!! TODO: NO SE SI SE GENERE ALGUN ERROR
        usuario.setPassword(passwordEncoder.encode(usuario.getPassword()));
        return userRepository.save(usuario);
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        // Buscar el usuario por su email
        User user = userRepository.findByCorreo(email)
                .orElseThrow(() -> new UsernameNotFoundException("Usuario no encontrado con correo: " + email));
//        if (user == null) {
//            throw new UsernameNotFoundException("Usuario no encontrado con correo: " + email);
//        }

        // Retornar un UserDetails (puedes usar la clase User de Spring Security)
        return org.springframework.security.core.userdetails.User.withUsername(user.getCorreo())
                .password(user.getPassword())
                .roles("USER") // Puedes personalizar los roles según sea necesario
                .build();
    }

    public List<User> obtenerTodosLosUsuarios() {
        return userRepository.findAll();
    }

    public Optional<User> obtenerUsuarioPorId(Long id) {
        return userRepository.findById(id);
    }

    public void eliminarUsuario(Long id) {
        userRepository.deleteById(id);
    }


    public void actualizarBalance(Long idUsuario, BigDecimal nuevoBalance) {
        User user = obtenerUsuarioPorId(idUsuario).orElseThrow(() -> new RuntimeException("Usuario no encontrado"));
        user.setBalance(nuevoBalance);
        registrarUsuario(user);
    }

}
