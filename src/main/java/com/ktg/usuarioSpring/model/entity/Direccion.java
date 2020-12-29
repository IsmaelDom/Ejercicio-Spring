package com.ktg.usuarioSpring.model.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

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
public class Direccion {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_direccion", updatable = false, nullable = false)
    private long id;

    @Valid
    @OneToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn(name = "usuario_id", nullable = false)
    private Usuario usuario;

    @NotBlank(message = "La Calle es requerida")
    @Size(min = 4, max = 100, message = "Ingrese una Calle valida, debe tener {min} minimo")
    @Column(nullable = false, length = 100)
    private String calle;

    @NotBlank(message = "El No. Exterior es requerido")
    @Size(min = 2, max = 15, message = "Ingrese un No. Exterior valido, debe tener {min} minimo")
    @Column(nullable = false, length = 15)
    private String no_exterior;

    @NotBlank(message = "El Código Postal es requerido")
    @Size(min = 2, max = 15, message = "Ingrese un Código Postal valido, debe tener {min} caracteres minimo")
    @Column(name = "codigo_postal", nullable = false, length = 15)
    private String cp;

    @NotBlank(message = "El Estado es requerido")
    @Size(min = 5, max = 100, message = "Ingrese un Estado valido, debe tener {min} caracteres minimo")
    @Column(nullable = false, length = 100)
    private String estado;

    @NotBlank(message = "La Referencia es requerida")
    @Column(nullable = false)
    private String referencia;
}
