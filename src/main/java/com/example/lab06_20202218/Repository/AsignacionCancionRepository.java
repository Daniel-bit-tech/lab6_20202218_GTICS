package com.example.lab06_20202218.Repository;

import com.example.lab06_20202218.Entity.Asignaciones_cancion;
import com.example.lab06_20202218.Entity.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface AsignacionCancionRepository extends JpaRepository<Asignaciones_cancion, Long> {
    Optional<Asignaciones_cancion> findByUsuario(Usuario usuario);
    List<Asignaciones_cancion> findBySolicitud(Boolean solicitud);
    @Query(value = "SELECT * FROM asignaciones_cancion WHERE adivinada = 1 ORDER BY intentos ASC LIMIT 10", nativeQuery = true)
    List<Asignaciones_cancion> findTop10ByAdivinadaTrueOrderByIntentosAsc();

}