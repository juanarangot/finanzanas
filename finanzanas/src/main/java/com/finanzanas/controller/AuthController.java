package com.finanzanas.controller;

import com.finanzanas.Entity.User;
import com.finanzanas.Service.CustomUserDetailsService;
import com.finanzanas.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private CustomUserDetailsService userDetailsService; // Servicio de usuarios

    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    private UserService userService;

    // Registra un usuario
//    @PostMapping("/crear")
//    public ResponseEntity<String> registrarUsuario(@RequestBody User usuario) {
//        userDetailsService.registrarUsuario(usuario);
//        return ResponseEntity.ok("Usuario registrado correctamente");
//    }

    // Implementa el login y otros métodos de autenticación si es necesario

    @PostMapping("/registrar")
    public ResponseEntity<User> registrarUsuario(@RequestBody User user) {
        User nuevoUsuario = userService.registrarUsuario(user);
        return ResponseEntity.ok(nuevoUsuario);
    }


    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody User loginUser) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginUser.getCorreo(), loginUser.getPassword())
        );
        SecurityContextHolder.getContext().setAuthentication(authentication);
        return ResponseEntity.ok("Login exitoso");
    }
}
