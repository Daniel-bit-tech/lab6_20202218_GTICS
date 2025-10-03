package com.example.lab06_20202218.Repository;

import com.example.lab06_20202218.Entity.Reservas;

import org.springframework.data.jpa.repository.JpaRepository;


public interface ReservaRepository extends JpaRepository<Reservas, Long> {

}
