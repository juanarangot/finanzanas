package com.finanzanas.Entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;


@Entity
@Getter
@Setter
@Table(name = "usuarios")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)  // Genera el ID automáticamente
    @Column(name = "id_usuario")
    private Long idUsuario;

    @Column(nullable = false, length = 255)
    private String nombre;

    @Column(nullable = false, unique = true, length = 255)
    private String correo;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false)
    private BigDecimal balance = BigDecimal.ZERO; // Balance inicial en 0.00

    @Column(name = "objetivo_financiero")
    private String objetivoFinanciero; // Objetivo financiero del usuario

//    @JsonManagedReference // Evitar referencias circulares al serializar a JSON
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Budget> presupuestos = new ArrayList<>(); // Lista de presupuestos/bolsillos del usuario


    //    Lista de transacciones financieras que ha hecho el usuario
//    Se asocia con la tabla de transacciones a través de la llave foránea "id_usuario"
//    se mapea con el atributo "user" de la clase Transaction
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Transaction> transactions;


    // Constructores
    public User() {}

    public User(String nombre, String correo) {
        this.nombre = nombre;
        this.correo = correo;
        this.password = new BCryptPasswordEncoder().encode(password);  // Encriptar la contraseña
    }

}
