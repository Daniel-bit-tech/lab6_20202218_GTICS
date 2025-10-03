package com.example.lab06_20202218.Entity;


import jakarta.persistence.*;

import java.time.LocalDate;


@Entity
@Table(name="heroes_navales")
public class Heroes_navales {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String nombre;
    private String descripcion;
    private String pais;

    private String rango;

    @Column(name = "fecha_nacimiento")
    private java.sql.Date fechaNacimiento;

    // getters y setters
    public Integer getId() { return id; }
    public void setId(Integer id) { this.id = id; }

    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }

    public String getDescripcion() { return descripcion; }
    public void setDescripcion(String descripcion) { this.descripcion = descripcion; }

    public String getPais() { return pais; }
    public void setPais(String pais) { this.pais = pais; }

    public String getRango() { return rango; }
    public void setRango(String rango) { this.rango = rango; }

    public java.sql.Date getFechaNacimiento() { return fechaNacimiento; }
    public void setFechaNacimiento(java.sql.Date fechaNacimiento) { this.fechaNacimiento = fechaNacimiento; }
}
