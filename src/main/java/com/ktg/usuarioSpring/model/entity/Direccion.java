package com.ktg.usuarioSpring.model.entity;

import javax.persistence.*;
import javax.validation.Valid;
import javax.validation.constraints.*;

//Indica que es una entidad
@Entity
@Table(name = "direcciones")
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

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getCalle() {
        return calle;
    }

    public void setCalle(String calle) {
        this.calle = calle;
    }

    public String getNo_exterior() {
        return no_exterior;
    }

    public void setNo_exterior(String no_exterior) {
        this.no_exterior = no_exterior;
    }

    public String getCp() {
        return cp;
    }

    public void setCp(String cp) {
        this.cp = cp;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public String getReferencia() {
        return referencia;
    }

    public void setReferencia(String referencia) {
        this.referencia = referencia;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }
}
