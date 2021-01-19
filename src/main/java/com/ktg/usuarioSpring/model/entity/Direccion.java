package com.ktg.usuarioSpring.model.entity;

import lombok.*;

import javax.persistence.*;
import javax.validation.Valid;
import javax.validation.constraints.*;

//Indica que es una entidad
@Entity
@Table(name = "direcciones")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Direccion {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_direccion", updatable = false, nullable = false)
    private long id;

    //Validación en cascada
    @Valid
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "usuario_id", nullable = false, unique = true)
    private Usuario usuario;

    @NotBlank(message = "La calle no puede ser nula.")
    @Column(nullable = false, length = 200)
    private String calle;

    @NotBlank(message = "El No. exterior no puede ser nulo.")
    @Column(nullable = false, length = 15)
    private String no_exterior;

    @NotBlank(message = "El código postal no puede ser nulo.")
    @Column(name = "codigo_postal", nullable = false, length = 15)
    private String cp;

    @NotBlank(message = "El Estado no puede ser nulo.")
    @Column(nullable = false)
    private String estado;

    @NotBlank(message = "El municipio no puede ser nulo.")
    @Column(nullable = false, length = 50)
    private String municipio;

    @NotBlank(message = "La Referencia no pueder se nula.")
    @Column(nullable = false)
    private String referencia;

    @Column(nullable = false, length = 1)
    private String status;
}
