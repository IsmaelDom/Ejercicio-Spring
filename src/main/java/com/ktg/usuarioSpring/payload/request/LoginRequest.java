package com.ktg.usuarioSpring.payload.request;

import lombok.ToString;

import javax.validation.constraints.NotBlank;

@ToString
public class LoginRequest {
    @NotBlank
    private String correo;

    @NotBlank
    private String password;

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
