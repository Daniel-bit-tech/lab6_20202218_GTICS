package com.example.lab06_20202218.Entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Table(name = "asignaciones_cancion")
@Getter
@Setter
public class Asignaciones_cancion {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "usuario_id")
    private Usuario usuario;

    @ManyToOne
    @JoinColumn(name = "cancion_id")
    private Canciones_criollas cancion;

    private Integer intentos = 0;
    private Boolean adivinada = false;
    private Boolean solicitud = false;
    private LocalDateTime fecha_adivinada;

    public LocalDateTime getFecha_adivinada() {
        return fecha_adivinada;
    }

    public void setFecha_adivinada(LocalDateTime fecha_adivinada) {
        this.fecha_adivinada = fecha_adivinada;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public Canciones_criollas getCancion() {
        return cancion;
    }

    public void setCancion(Canciones_criollas cancion) {
        this.cancion = cancion;
    }

    public Integer getIntentos() {
        return intentos;
    }

    public void setIntentos(Integer intentos) {
        this.intentos = intentos;
    }

    public Boolean getAdivinada() {
        return adivinada;
    }

    public void setAdivinada(Boolean adivinada) {
        this.adivinada = adivinada;
    }

    public Boolean getSolicitud() {
        return solicitud;
    }

    public void setSolicitud(Boolean solicitud) {
        this.solicitud = solicitud;
    }
}

