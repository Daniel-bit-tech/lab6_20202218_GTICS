package com.example.lab06_20202218.Controller;


import com.example.lab06_20202218.Entity.Asignaciones_cancion;

import com.example.lab06_20202218.Services.JuegoCancionService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/admin")
public class AdminController {

    private final JuegoCancionService juegoService;

    public AdminController(JuegoCancionService juegoService) {
        this.juegoService = juegoService;
    }



    @GetMapping("/asignaciones")
    public String listarAsignaciones(Model model) {
        List<Asignaciones_cancion> solicitudes = juegoService.getSolicitudesPendientes();
        model.addAttribute("solicitudes", solicitudes);
        model.addAttribute("canciones", juegoService.getCancionesDisponibles());
        return "admin/asignaciones";
    }
    @PostMapping("/asignar")
    public String asignarCancion(@RequestParam("solicitudId") Long solicitudId,
                                 @RequestParam("cancionId") Long cancionId) {
        Optional<Asignaciones_cancion> solicitud = juegoService.getAsignacionById(solicitudId);
        if (solicitud.isPresent()) {
            juegoService.asignarCancion(solicitud.get(), cancionId);
        }
        return "redirect:/admin/asignaciones";
    }

    @GetMapping("/ranking")
    public String verRanking(Model model) {
        model.addAttribute("ranking", juegoService.top10());
        return "juego/cancion-ranking";
    }
}