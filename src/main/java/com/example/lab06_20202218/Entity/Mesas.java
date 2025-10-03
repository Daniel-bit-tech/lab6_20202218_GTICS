package com.example.lab06_20202218.Entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "mesas")
@Getter
@Setter
public class Mesas {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    private Integer numero;

    private Integer capacidad = 4;

    private Boolean disponible = true;
}
