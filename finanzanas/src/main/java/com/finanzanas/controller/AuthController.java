package com.finanzanas.controller;

import com.finanzanas.DTO.LoginRequest;
import com.finanzanas.DTO.RegisterRequest;
import com.finanzanas.Entity.User;
import com.finanzanas.Repository.UserRepository;
import com.finanzanas.Service.CustomUserDetailsService;
import com.finanzanas.Service.UserService;
import com.finanzanas.util.JwtUtil;
import jdk.swing.interop.SwingInterOpUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private CustomUserDetailsService userDetailsService; // Servicio de usuarios

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    private UserService userService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;  // Inyecta el PasswordEncoder


    // Registra un usuario
//    @PostMapping("/crear")
//    public ResponseEntity<String> registrarUsuario(@RequestBody User usuario) {
//        userDetailsService.registrarUsuario(usuario);
//        return ResponseEntity.ok("Usuario registrado correctamente");
//    }

    // Implementa el login y otros métodos de autenticación si es necesario

//    @PostMapping("/registrar")
//    public ResponseEntity<User> registrarUsuario(@RequestBody User user) {
//        User nuevoUsuario = userService.registrarUsuario(user);
//        return ResponseEntity.ok(nuevoUsuario);
//    }

    @PostMapping("/registrar")
    public ResponseEntity<?> registerUser(@RequestBody RegisterRequest registerRequest) {
        // Crear el usuario y guardarlo en la base de datos
        User newUser = new User();
        newUser.setNombre(registerRequest.getNombre());
        newUser.setCorreo(registerRequest.getCorreo());
        newUser.setPassword(passwordEncoder.encode(registerRequest.getPassword())); // Encriptar la contraseña
        userRepository.save(newUser);

        // Autenticar al usuario recién registrado
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        registerRequest.getCorreo(),
                        registerRequest.getPassword()
                )
        );

        SecurityContextHolder.getContext().setAuthentication(authentication);

        // Generación del token JWT
        String jwt = jwtUtil.generateToken(registerRequest.getCorreo());

        // Crear la respuesta con el token y el id del usuario
        Map<String, Object> response = new HashMap<>();
        response.put("token", jwt);
        response.put("id", newUser.getIdUsuario()); // Ajusta según el nombre del campo de id en tu entidad User

        return ResponseEntity.ok(response);
    }




//    @PostMapping("/login")
//    public ResponseEntity<String> login(@RequestBody User loginUser) {
//        Authentication authentication = authenticationManager.authenticate(
//                new UsernamePasswordAuthenticationToken(loginUser.getCorreo(), loginUser.getPassword())
//        );
//        SecurityContextHolder.getContext().setAuthentication(authentication);
//        return ResponseEntity.ok("Login exitoso");
//    }

//    @PostMapping("/login")
//    public ResponseEntity<String> createAuthToken(@RequestBody User loginUser) throws Exception {
//        System.out.println("PAPAYA :)");
////        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginUser.getCorreo(), loginUser.getPassword()));
//
//        Authentication authentication = authenticationManager.authenticate(
//                new UsernamePasswordAuthenticationToken(loginUser.getCorreo(), loginUser.getPassword())
//        );
//
//        final UserDetails userDetails = userDetailsService.loadUserByUsername(loginUser.getCorreo());
//        final String jwt = jwtUtil.generateToken(userDetails.getUsername());
//
//        SecurityContextHolder.getContext().setAuthentication(authentication);
//
//        return ResponseEntity.ok(jwt);
//    }


    @PostMapping("/login")
    public ResponseEntity<?> authenticateUser(@RequestBody LoginRequest loginRequest) {

        // Autenticación del usuario
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginRequest.getCorreo(),
                        loginRequest.getPassword()
                )
        );

        SecurityContextHolder.getContext().setAuthentication(authentication);

        // Generación del token JWT
        String jwt = jwtUtil.generateToken(String.valueOf(authentication));

        // Obtener el usuario autenticado
        User user = userService.obtenerUsuarioPorCorreo(loginRequest.getCorreo())
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        // Crear la respuesta con el token y el id del usuario
        Map<String, Object> response = new HashMap<>();
        response.put("token", jwt);
        response.put("id", user.getIdUsuario()); // Ajusta según el nombre del campo de id de tu entidad User

        return ResponseEntity.ok(response);
    }


//    @PostMapping("/api/auth/login")
//    public ResponseEntity<?> createAuthenticationToken(@RequestBody AuthRequest authRequest) throws Exception {
//        try {
//            authenticationManager.authenticate(
//                    new UsernamePasswordAuthenticationToken(authRequest.getUsername(), authRequest.getPassword())
//            );
//        } catch (BadCredentialsException e) {
//            throw new Exception("Incorrect username or password", e);
//        }
//
//        final UserDetails userDetails = userDetailsService.loadUserByUsername(authRequest.getUsername());
//        final String jwt = jwtUtil.generateToken(userDetails.getUsername());
//
//        return ResponseEntity.ok(new AuthResponse(jwt));
//    }
}
