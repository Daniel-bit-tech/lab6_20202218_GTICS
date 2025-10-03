package com.example.lab06_20202218.Services;


import com.example.lab06_20202218.Entity.Heroes_navales;
import com.example.lab06_20202218.Repository.HeroeNavalRepository;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class HeroeService {

    private final HeroeNavalRepository heroeNavalRepository;

    public HeroeService(HeroeNavalRepository heroeNavalRepository) {
        this.heroeNavalRepository = heroeNavalRepository;
    }

    public List<Heroes_navales> listarTodos() {
        return heroeNavalRepository.findAll();
    }

    public Heroes_navales guardar(Heroes_navales heroe) {
        return heroeNavalRepository.save(heroe);
    }




}
