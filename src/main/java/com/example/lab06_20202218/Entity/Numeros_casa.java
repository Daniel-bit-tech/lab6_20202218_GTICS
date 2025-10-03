package com.example.lab06_20202218.Entity;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "numeros_casa")
@Getter
@Setter
public class Numeros_casa {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "usuario_id")
    private Usuario usuario;

    private Integer numeroObjetivo;

    private Integer intentos = 0;

    private Boolean adivinado = false;

    private Boolean solicitud = false;

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

    public Integer getNumeroObjetivo() {
        return numeroObjetivo;
    }

    public void setNumeroObjetivo(Integer numeroObjetivo) {
        this.numeroObjetivo = numeroObjetivo;
    }

    public Integer getIntentos() {
        return intentos;
    }

    public void setIntentos(Integer intentos) {
        this.intentos = intentos;
    }

    public Boolean getAdivinado() {
        return adivinado;
    }

    public void setAdivinado(Boolean adivinado) {
        this.adivinado = adivinado;
    }

    public Boolean getSolicitud() {
        return solicitud;
    }

    public void setSolicitud(Boolean solicitud) {
        this.solicitud = solicitud;
    }
}