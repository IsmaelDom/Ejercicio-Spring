package com.ktg.usuarioSpring.controllers;

import lombok.ToString;

import java.io.Serializable;

@ToString
public class DireccionUserDTO implements Serializable {

    private String direccion;
    private String usuario;

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }
}
