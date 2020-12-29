package com.ktg.usuarioSpring.model.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Entity
@Table(name = "usuarios")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
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
    @NotBlank(message = "La Contraseña es requerida")
    //Propiedad para que la contraseña solo se pueda escribir
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @Column(nullable = false)
    private String password;

}
