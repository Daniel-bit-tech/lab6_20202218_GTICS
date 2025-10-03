package com.example.lab06_20202218.Services;

import com.example.lab06_20202218.Entity.Asignaciones_cancion;
import com.example.lab06_20202218.Entity.Canciones_criollas;
import com.example.lab06_20202218.Entity.Usuario;
import com.example.lab06_20202218.Repository.AsignacionCancionRepository;
import com.example.lab06_20202218.Repository.CancionRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.*;


@Service
public class JuegoCancionService {

    private final AsignacionCancionRepository asignacionRepo;
    private final CancionRepository cancionRepo;

    public JuegoCancionService(AsignacionCancionRepository asignacionRepo, CancionRepository cancionRepo) {
        this.asignacionRepo = asignacionRepo;
        this.cancionRepo = cancionRepo;
    }

    public Optional<Asignaciones_cancion> getAsignacion(Usuario usuario) {
        return asignacionRepo.findByUsuario(usuario);
    }

    public void solicitarAsignacion(Usuario usuario) {

        Optional<Asignaciones_cancion> asignacionExistente = asignacionRepo.findByUsuario(usuario);
        if (asignacionExistente.isPresent()) {
            return;
        }


        Asignaciones_cancion nuevaAsignacion = new Asignaciones_cancion();
        nuevaAsignacion.setUsuario(usuario);
        nuevaAsignacion.setIntentos(0);
        nuevaAsignacion.setAdivinada(false);
        nuevaAsignacion.setSolicitud(true);

        asignacionRepo.save(nuevaAsignacion);
    }


    public Asignaciones_cancion asignarCancion(Asignaciones_cancion solicitud, Long cancionId) {
        Canciones_criollas cancion = cancionRepo.findById(cancionId).orElseThrow();
        solicitud.setCancion(cancion);
        solicitud.setSolicitud(false);
        solicitud.setIntentos(0);
        solicitud.setAdivinada(false);
        return asignacionRepo.save(solicitud);
    }

    public List<Asignaciones_cancion> getSolicitudesPendientes() {
        return asignacionRepo.findBySolicitud(true);
    }

    public Optional<Asignaciones_cancion> getAsignacionById(Long id) {
        return asignacionRepo.findById(id);
    }

    public String generarPatron(String titulo) {
        StringBuilder patron = new StringBuilder();
        for (char c : titulo.toCharArray()) {
            if (Character.isLetter(c)) {
                patron.append('_').append(' ');
            } else if (c == ' ') {
                patron.append(' ').append(' ');
            }
        }
        return patron.toString().trim();
    }

    public Map<String, Object> intentar(Asignaciones_cancion asignacion, String intento) {
        Map<String, Object> resultado = new HashMap<>();
        String titulo = asignacion.getCancion().getTitulo();
        String guess = intento;


        int matchedLetters = 0;
        Map<Character, Integer> freq = new HashMap<>();
        for (char c : titulo.toLowerCase().replaceAll("\\s+", "").toCharArray())
            freq.put(c, freq.getOrDefault(c, 0) + 1);
        for (char c : guess.toLowerCase().replaceAll("\\s+", "").toCharArray()) {
            if (freq.getOrDefault(c, 0) > 0) {
                matchedLetters++;
                freq.put(c, freq.get(c) - 1);
            }
        }


        StringBuilder match = new StringBuilder();
        int correct = 0;
        int minLength = Math.min(titulo.length(), guess.length());
        for (int i = 0; i < minLength; i++) {
            char titleChar = titulo.charAt(i);
            char guessChar = guess.charAt(i);
            if (titleChar == ' ') {
                match.append(' ');
            } else if (Character.toLowerCase(titleChar) == Character.toLowerCase(guessChar)) {
                match.append('o');
                correct++;
            } else {
                match.append('x');
            }
        }

        for (int i = minLength; i < guess.length(); i++) {
            if (guess.charAt(i) == ' ') {
                match.append(' ');
            } else {
                match.append('x');
            }
        }

        asignacion.setIntentos(asignacion.getIntentos() + 1);
        boolean adivinada = titulo.equalsIgnoreCase(guess);
        if (adivinada) {
            asignacion.setAdivinada(true);
            asignacion.setFecha_adivinada(LocalDateTime.now());
        }
        asignacionRepo.save(asignacion);

        resultado.put("acerto", adivinada);
        resultado.put("correct", correct);
        resultado.put("matched", matchedLetters);
        resultado.put("matchString", match.toString());
        resultado.put("intento", intento);

        return resultado;
    }

    public List<Canciones_criollas> getCancionesDisponibles() {
        return cancionRepo.findAll();
    }

    public List<Asignaciones_cancion> top10() {
        return asignacionRepo.findTop10ByAdivinadaTrueOrderByIntentosAsc();
    }
}
