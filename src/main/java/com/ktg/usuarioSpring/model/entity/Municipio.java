package com.ktg.usuarioSpring.model.entity;

import lombok.*;

import javax.persistence.*;

@Entity
@Table(name = "municipios")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Municipio {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_municipio", updatable = false, nullable = false)
    private int id;

    @Column(nullable = false, length = 100)
    private String municipio;

    @ManyToOne
    @JoinColumn(name = "estado_id", nullable = false)
    private Estado estado;
}
