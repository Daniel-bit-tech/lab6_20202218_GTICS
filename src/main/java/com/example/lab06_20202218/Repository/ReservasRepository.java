package com.example.lab06_20202218.Repository;

import com.example.lab06_20202218.Entity.Reservas;

import com.example.lab06_20202218.Entity.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;


public interface ReservasRepository extends JpaRepository<Reservas, Long> {
    Optional<Reservas> findByUsuario(Usuario usuario);
    List<Reservas> findByMesaId(Long mesaId);

}
