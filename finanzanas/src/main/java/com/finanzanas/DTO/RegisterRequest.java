package com.finanzanas.DTO;

import lombok.Data;

@Data
public class RegisterRequest {
    private String nombre;
    private String correo;
    private String password;

}
