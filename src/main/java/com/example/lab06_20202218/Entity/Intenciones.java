package com.example.lab06_20202218.Entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.*;
import java.time.LocalDateTime;

import jakarta.persistence.PrePersist;




@Entity
@Table(name = "intenciones")
@Getter @Setter @NoArgsConstructor @AllArgsConstructor
public class Intenciones {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne @JoinColumn(name="usuario_id")
    private Usuario usuario;

    @NotBlank(message = "No admite vacío")
    @Size(min = 15, message = "Mínimo 15 caracteres")
    @Pattern(
            regexp = "^(?!.*\\b(odio|pelea|matar)\\b).*$",
            message = "No puede contener palabras prohibidas"
    )
    @Column(length = 1000)
    private String descripcion;

    @Column
    private LocalDateTime fecha;

    @PrePersist
    public void prePersist() {
        this.fecha = LocalDateTime.now();
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

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public LocalDateTime getFecha() {
        return fecha;
    }

    public void setFecha(LocalDateTime fecha) {
        this.fecha = fecha;
    }
}

