package com.ktg.usuarioSpring.model.entity;

import lombok.*;

import javax.persistence.*;

@Entity
@Table(name = "estados")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Estado {

        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        @Column(name = "id_estado", updatable = false, nullable = false)
        private int id;

        @Column(nullable = false, length = 40)
        private String estado;
}
