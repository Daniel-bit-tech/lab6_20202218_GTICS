package com.example.lab06_20202218.Services;


import com.example.lab06_20202218.Entity.Intenciones;
import com.example.lab06_20202218.Entity.Usuario;
import com.example.lab06_20202218.Repository.IntencionRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;


@Service
public class IntencionService {

    private final IntencionRepository intencionRepo;

    public IntencionService(IntencionRepository intencionRepo) {
        this.intencionRepo = intencionRepo;
    }



    public Intenciones crear(Usuario usuario, String descripcion) {
        Intenciones i = new Intenciones();
        i.setUsuario(usuario);
        i.setDescripcion(descripcion);
        i.setFecha(LocalDateTime.now());
        return intencionRepo.save(i);
    }

    public List<Intenciones> listarTodas() {
        return intencionRepo.findAll();
    }
}
