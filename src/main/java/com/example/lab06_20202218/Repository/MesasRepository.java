package com.example.lab06_20202218.Repository;

import com.example.lab06_20202218.Entity.Mesas;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MesasRepository extends JpaRepository<Mesas, Long> {
    List<Mesas> findByDisponibleTrue();
    List<Mesas> findByDisponibleFalse();
}
