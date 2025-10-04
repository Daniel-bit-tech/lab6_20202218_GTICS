package com.example.lab06_20202218.Repository;

import com.example.lab06_20202218.Entity.Numeros_casa;

import com.example.lab06_20202218.Entity.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;


public interface Numeros_casaRepository extends JpaRepository<Numeros_casa, Long> {
    Optional<Numeros_casa> findByUsuario(Usuario usuario);
    List<Numeros_casa> findTop10ByAdivinadoTrueOrderByIntentosAsc();

}