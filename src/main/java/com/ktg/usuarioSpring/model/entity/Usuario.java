package com.ktg.usuarioSpring.model.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Entity
@Table(name = "usuarios")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Usuario {

    @Id
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_usuario", updatable = false, nullable = false)
    private long id;

    @NotBlank(message = "El correo no puede ser nulo.")
    @Email(message = "Ingrese correo valido")
    @Column(nullable = false, unique = true, length = 100)
    private String correo;

    @NotBlank(message = "El nombre no puede ser nulo.")
    @Size(min = 2, max = 100, message = "El nombre debe tener entre {min} y {max}.")
    @Column(nullable = false, length = 100)
    private String nombre;

    @NotBlank(message = "El apellido no puede ser nulo.")
    @Size(min = 2, max = 100, message = "El Apellido debe tener entre {min} y {max}.")
    @Column(nullable = false, length = 100)
    private String apellido;

    //@JsonIgnore
    @NotBlank(message = "La contraseña no puede ser nula.")
    //Propiedad para que la contraseña solo se pueda escribir
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @Size(min = 5, message = "La contraseña debe tener minimo {min} caracteres.")
    @Column(nullable = false)
    private String password;

    @NotNull(message = "La edad no puede ser nula.")
    @Column(nullable = false, length = 3)
    private int edad;
}
