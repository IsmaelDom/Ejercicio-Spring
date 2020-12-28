package com.ktg.usuarioSpring.model.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Entity
@Table(name = "usuarios")
public class Usuario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_usuario", updatable = false, nullable = false)
    private long id;

    @NotBlank(message = "El Usuario es requerido")
    @Size(min = 2, max = 100, message = "Ingrese un Usuario valido")
    @Column(nullable = false, unique = true, length = 100)
    private String usuario;

    @NotBlank(message = "El Nombre de usuario es requerido")
    @Size(min = 2, max = 100, message = "Ingrese un Nombre valido")
    @Column(nullable = false, length = 100)
    private String nombre;

    @NotBlank(message = "El Apellido es requerido")
    @Size(min = 2, max = 100, message = "Ingrese un Apellido valido")
    @Column(nullable = false, length = 100)
    private String apellido;

    //@JsonIgnore
    @NotBlank(message = "La Contraseña es requerido")
    //Propiedad para que la contraseña solo se pueda escribir
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @Column(nullable = false)
    private String password;

    public Usuario() {
    }

    public Usuario(long id, String usuario, String nombre, String apellido, String password) {
        this.id = id;
        this.usuario = usuario;
        this.nombre = nombre;
        this.apellido = apellido;
        this.password = password;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellido() {
        return apellido;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
