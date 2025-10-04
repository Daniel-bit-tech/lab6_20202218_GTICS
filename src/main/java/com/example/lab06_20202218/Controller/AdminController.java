package com.example.lab06_20202218.Controller;

import com.example.lab06_20202218.Entity.Asignaciones_cancion;
import com.example.lab06_20202218.Entity.Usuario;
import com.example.lab06_20202218.Repository.UsuarioRepository;
import com.example.lab06_20202218.Services.JuegoCancionService;
import com.example.lab06_20202218.Services.JuegoService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/admin")
public class AdminController {


    private final JuegoCancionService juegoCancionService;
    private final JuegoService juegoMesaService;
    private final UsuarioRepository usuarioRepository;

    public AdminController(JuegoCancionService juegoCancionService, JuegoService juegoMesaService, UsuarioRepository usuarioRepository) {
        this.juegoCancionService = juegoCancionService;
        this.juegoMesaService = juegoMesaService;
        this.usuarioRepository = usuarioRepository;
    }


    @GetMapping("/asignaciones-canciones")
    public String listarAsignaciones(Model model) {
        List<Asignaciones_cancion> solicitudes = juegoCancionService.getSolicitudesPendientes();
        model.addAttribute("solicitudes", solicitudes);
        model.addAttribute("canciones", juegoCancionService.getCancionesDisponibles());
        return "admin/asignaciones";
    }

    @PostMapping("/asignar-cancion")
    public String asignarCancion(@RequestParam("solicitudId") Long solicitudId,
                                 @RequestParam("cancionId") Long cancionId) {
        Optional<Asignaciones_cancion> solicitud = juegoCancionService.getAsignacionById(solicitudId);
        if (solicitud.isPresent()) {
            juegoCancionService.asignarCancion(solicitud.get(), cancionId);
        }
        return "redirect:/admin/asignaciones-canciones";
    }

    @GetMapping("/ranking-canciones")
    public String verRankingCanciones(Model model) {
        model.addAttribute("ranking", juegoCancionService.top10());
        return "juego/cancion-ranking";
    }

    @GetMapping("/asignaciones-dulces")
    public String verSolicitudesDulces(Model model) {
        List<Usuario> usuarios = usuarioRepository.findAll();
        model.addAttribute("usuarios", usuarios);
        return "admin/asignaciones-dulces";
    }

    @PostMapping("/asignar-numero")
    public String asignarNumero(@RequestParam("usuarioId") Long usuarioId, @RequestParam("numero") int numero, RedirectAttributes redirectAttributes) {
        Usuario usuario = usuarioRepository.findById(usuarioId)
                .orElseThrow(() -> new IllegalArgumentException("Usuario no encontrado"));
        juegoMesaService.asignarNumeroCasa(usuario, numero);
        redirectAttributes.addFlashAttribute("mensaje", "Número de casa asignado exitosamente.");
        return "redirect:/admin/asignaciones-dulces";
    }

    @GetMapping("/ranking-dulces")
    public String verRankingDulces(Model model) {
        model.addAttribute("ranking", juegoMesaService.obtenerRanking());
        return "admin/ranking-admin";
    }
}