package com.example.lab06_20202218.Repository;

import com.example.lab06_20202218.Entity.Heroes_navales;
import com.example.lab06_20202218.Entity.Rol;
import com.example.lab06_20202218.Entity.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface HeroeNavalRepository extends JpaRepository<Heroes_navales, Long> {




}
