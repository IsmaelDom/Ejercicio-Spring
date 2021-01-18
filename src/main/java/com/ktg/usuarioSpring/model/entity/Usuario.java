package com.ktg.usuarioSpring.model.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.*;

@Entity
@Table(name = "usuarios")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Usuario {

    @Id
    //@JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_usuario", updatable = false, nullable = false)
    private long id;

    @NotBlank(message = "El correo no puede ser nulo.")
    @Email(message = "Ingrese correo valido")
    @Column(nullable = false, unique = true, length = 100)
    private String correo;

    @NotBlank(message = "El nombre no puede ser nulo.")
    @Column(nullable = false, length = 100)
    private String nombre;

    @NotBlank(message = "El apellido no puede ser nulo.")
    @Column(nullable = false, length = 100)
    private String apellido;

    //@JsonIgnore
    @NotBlank(message = "La contraseña no puede ser nula.")
    //Propiedad para que la contraseña solo se pueda escribir
    //@JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @Size(min = 5, message = "La contraseña debe tener minimo {min} caracteres.")
    @Column(nullable = false)
    private String password;

    @NotNull(message = "La edad no puede ser nula.")
    @Min(value = 1, message = "Ingrese una edad valida.")
    @Max(value = 120, message = "Ingrese una edad valida.")
    @Column(nullable = false, length = 3)
    private int edad;

    @Column(nullable = false, length = 20)
    private String curp;
}
