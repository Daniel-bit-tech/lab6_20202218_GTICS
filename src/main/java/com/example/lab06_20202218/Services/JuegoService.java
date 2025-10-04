package com.example.lab06_20202218.Services;

import com.example.lab06_20202218.Entity.Numeros_casa;
import com.example.lab06_20202218.Entity.Usuario;
import com.example.lab06_20202218.Repository.Numeros_casaRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class JuegoService {

    private final Numeros_casaRepository numerosCasaRepository;

    public JuegoService(Numeros_casaRepository numerosCasaRepository) {
        this.numerosCasaRepository = numerosCasaRepository;
    }

    public Numeros_casa obtenerNumeroCasaPorUsuario(Usuario usuario) {
        Optional<Numeros_casa> numeroCasa = numerosCasaRepository.findByUsuario(usuario);
        return numeroCasa.orElse(null);
    }

    public void solicitarAsignacion(Usuario usuario) {
        if (numerosCasaRepository.findByUsuario(usuario).isEmpty()) {
            Numeros_casa numeroCasa = new Numeros_casa();
            numeroCasa.setUsuario(usuario);
            numeroCasa.setSolicitud(true);
            numerosCasaRepository.save(numeroCasa);
        }
    }

    public void asignarNumeroCasa(Usuario usuario, int numero) {
        numerosCasaRepository.findByUsuario(usuario).ifPresent(numerosCasa -> {
            if (numero > 64) {
                numerosCasa.setNumeroObjetivo(numero);
                numerosCasa.setSolicitud(false);
                numerosCasaRepository.save(numerosCasa);
            }
        });
    }

    public int calcularPasosMinimos(int numeroObjetivo) {
        return (int) Math.ceil((double) numeroObjetivo / 6);
    }

    public boolean adivinar(Usuario usuario, int pasosUsuario) {
        Optional<Numeros_casa> numeroCasaOpt = numerosCasaRepository.findByUsuario(usuario);
        if (numeroCasaOpt.isPresent()) {
            Numeros_casa numeroCasa = numeroCasaOpt.get();
            int intentosActuales = numeroCasa.getIntentos();
            numeroCasa.setIntentos(intentosActuales + 1);

            int pasosMinimos = calcularPasosMinimos(numeroCasa.getNumeroObjetivo());
            if (pasosUsuario == pasosMinimos) {
                numeroCasa.setAdivinado(true);
                numerosCasaRepository.save(numeroCasa);
                return true;
            } else {
                numerosCasaRepository.save(numeroCasa);
                return false;
            }
        }
        return false;
    }

    public List<Numeros_casa> obtenerRanking() {
        return numerosCasaRepository.findTop10ByAdivinadoTrueOrderByIntentosAsc();
    }
}