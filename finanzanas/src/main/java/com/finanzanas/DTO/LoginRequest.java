package com.finanzanas.DTO;

public class LoginRequest {

    private String correo;
    private String password;

    // Constructor vacío
    public LoginRequest() {}

    // Constructor con parámetros
    public LoginRequest(String correo, String password) {
        this.correo = correo;
        this.password = password;
    }

    // Getters y setters
    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
