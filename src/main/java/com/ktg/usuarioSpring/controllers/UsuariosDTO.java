package com.ktg.usuarioSpring.controllers;

import lombok.*;

import java.io.Serializable;
import java.util.List;

@ToString
@Getter
@Setter
public class UsuariosDTO implements Serializable {

    private long id;
    private String fullDireccion;
    private String estado;
    private String municipio;
    private String referencia;
    private String fullName;
    private String correo;
    private int edad;

}
