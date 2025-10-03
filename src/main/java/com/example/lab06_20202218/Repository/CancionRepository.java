package com.example.lab06_20202218.Repository;

import com.example.lab06_20202218.Entity.Canciones_criollas;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CancionRepository extends JpaRepository<Canciones_criollas, Long> {
}
