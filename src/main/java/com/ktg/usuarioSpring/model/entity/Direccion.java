package com.ktg.usuarioSpring.model.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.Valid;
import javax.validation.constraints.*;

//Indica que es una entidad
@Entity
@Table(name = "direcciones")
@Getter @Setter
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
    @Size(min = 4, max = 100, message = "Ingrese una Calle valida")
    @Column(nullable = false, length = 100)
    private String calle;

    @NotBlank(message = "El No. Exterior es requerido")
    @Size(min = 2, max = 15, message = "Ingrese un No. Exterior valido")
    @Column(nullable = false, length = 15)
    private String no_exterior;

    @NotBlank(message = "El Código Postal es requerido")
    @Size(min = 2, max = 15, message = "Ingrese un Código Postal valido")
    @Column(name = "codigo_postal", nullable = false, length = 15)
    private String cp;

    @NotBlank(message = "El Estado es requerido")
    @Size(min = 5, max = 100, message = "Ingrese un Estado valido")
    @Column(nullable = false, length = 100)
    private String estado;

    @NotBlank(message = "La Referencia es requerida")
    @Column(nullable = false)
    private String referencia;

    public Direccion() {
    }

    public Direccion(long id, Usuario usuario, String calle, String no_exterior, String cp, String estado, String referencia) {
        this.id = id;
        this.usuario = usuario;
        this.calle = calle;
        this.no_exterior = no_exterior;
        this.cp = cp;
        this.estado = estado;
        this.referencia = referencia;
    }
}
