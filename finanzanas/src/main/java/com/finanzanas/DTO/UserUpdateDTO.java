package com.finanzanas.DTO;

import lombok.Data;

import java.math.BigDecimal;

/*
* Clase que representa un objeto de transferencia de datos (DTO) para actualizar un usuario
* y sus atributos en la base de datos.
* */
@Data
public class UserUpdateDTO {
    private Long id;
    private String nombre;
    private String correo;
    private String password;
    private BigDecimal balance;
    private String objetivoFinanciero;
    // Añadir getters y setters
}
