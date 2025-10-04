package com.example.lab06_20202218.Services;
import com.example.lab06_20202218.Entity.Mesas;
import com.example.lab06_20202218.Entity.Reservas;
import com.example.lab06_20202218.Entity.Usuario;
import com.example.lab06_20202218.Repository.MesasRepository;
import com.example.lab06_20202218.Repository.ReservasRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class MesaService {

    private final MesasRepository mesasRepository;
    private final ReservasRepository reservasRepository;

    public MesaService(MesasRepository mesasRepository, ReservasRepository reservasRepository) {
        this.mesasRepository = mesasRepository;
        this.reservasRepository = reservasRepository;
    }

    public List<Mesas> getTodasLasMesas() {
        return mesasRepository.findAll();
    }

    public long getMesasOcupadasCount() {
        return mesasRepository.findByDisponibleFalse().size();
    }

    public long getMesasLibresCount() {
        return mesasRepository.findByDisponibleTrue().size();
    }

    public boolean puedeReservar(Usuario usuario) {
        return reservasRepository.findByUsuario(usuario).isEmpty();
    }

    public boolean validarCapacidad(Long mesaId, int numPersonas) {
        Optional<Mesas> mesaOpt = mesasRepository.findById(mesaId);
        return mesaOpt.filter(mesa -> numPersonas <= mesa.getCapacidad()).isPresent();
    }

    public void reservarMesa(Usuario usuario, Long mesaId) {
        Optional<Mesas> mesaOpt = mesasRepository.findById(mesaId);
        if (mesaOpt.isPresent()) {
            Mesas mesa = mesaOpt.get();
            if (mesa.getDisponible()) {
                mesa.setDisponible(false);
                mesasRepository.save(mesa);

                Reservas reserva = new Reservas();
                reserva.setUsuario(usuario);
                reserva.setMesa(mesa);
                reserva.setFecha(LocalDateTime.now());
                reservasRepository.save(reserva);
            }
        }
    }

    public void liberarMesa(Long mesaId) {
        reservasRepository.findByMesaId(mesaId).stream().forEach(reservasRepository::delete);
        Optional<Mesas> mesaOpt = mesasRepository.findById(mesaId);
        if (mesaOpt.isPresent()) {
            Mesas mesa = mesaOpt.get();
            mesa.setDisponible(true);
            mesasRepository.save(mesa);
        }
    }

    public void reasignarAsientos(Long mesaId, int nuevaCapacidad) {
        Optional<Mesas> mesaOpt = mesasRepository.findById(mesaId);
        if (mesaOpt.isPresent()) {
            Mesas mesa = mesaOpt.get();
            mesa.setCapacidad(nuevaCapacidad);
            mesasRepository.save(mesa);
        }
    }
}
