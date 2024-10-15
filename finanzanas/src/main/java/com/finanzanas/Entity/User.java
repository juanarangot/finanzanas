package com.finanzanas.Entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;


@Entity
@Getter
@Setter
@Table(name = "usuarios")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)  // Genera el ID automáticamente
    @Column(name = "id_usuario")
    private Long idUsuario;

    @Column(nullable = false)
    private String nombre;

    @Column(nullable = false, unique = true)
    private String correo;

    @Column(nullable = false)
    private String password;


    // Constructores
    public User() {}

    public User(String nombre, String correo) {
        this.nombre = nombre;
        this.correo = correo;
        this.password = new BCryptPasswordEncoder().encode(password);  // Encriptar la contraseña
    }

}
