package com.example.lab06_20202218.Controller;

import com.example.lab06_20202218.Entity.Asignaciones_cancion;
import com.example.lab06_20202218.Entity.Usuario;
import com.example.lab06_20202218.Repository.UsuarioRepository;
import com.example.lab06_20202218.Services.JuegoCancionService;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import java.util.Map;
import java.util.Optional;

@Controller
@RequestMapping("/juego/cancion")
public class JuegoCancionController {

    private final JuegoCancionService juegoService;
    private final UsuarioRepository usuarioRepo;

    public JuegoCancionController(JuegoCancionService juegoService, UsuarioRepository usuarioRepo) {
        this.juegoService = juegoService;
        this.usuarioRepo = usuarioRepo;
    }

    @GetMapping
    public String index(Model model, @AuthenticationPrincipal User user) {
        Usuario usuario = usuarioRepo.findByCorreo(user.getUsername()).orElseThrow();
        Optional<Asignaciones_cancion> asignacionOpt = juegoService.getAsignacion(usuario);

        if (asignacionOpt.isPresent()) {
            Asignaciones_cancion asignacion = asignacionOpt.get();
            if (asignacion.getCancion() != null) {
                model.addAttribute("asignacion", asignacion);
                model.addAttribute("patron", juegoService.generarPatron(asignacion.getCancion().getTitulo()));
                return "juego/cancion";
            } else {
                return "juego/cancion-disabled";
            }
        } else {

            return "juego/cancion-disabled";
        }
    }


    @PostMapping("/solicitar")
    public String solicitar(@AuthenticationPrincipal User user) {
        Usuario usuario = usuarioRepo.findByCorreo(user.getUsername()).orElseThrow();
        juegoService.solicitarAsignacion(usuario);
        return "redirect:/juego/cancion";
    }

    @PostMapping("/intentar")
    public String intentar(@RequestParam("intento") String intento, @AuthenticationPrincipal User user, Model model) {
        Usuario usuario = usuarioRepo.findByCorreo(user.getUsername()).orElseThrow();
        Asignaciones_cancion asignacion = juegoService.getAsignacion(usuario).orElseThrow();

        Map<String, Object> resultado = juegoService.intentar(asignacion, intento);

        model.addAttribute("asignacion", asignacion);
        model.addAttribute("resultado", resultado);


        if (Boolean.TRUE.equals(resultado.get("acerto"))) {
            model.addAttribute("mensaje", "Felicidades");
        } else {
            model.addAttribute("mensaje", "Intento incorrecto");
        }

        return "juego/cancion";
    }

    @GetMapping("/ranking")
    public String ranking(Model model) {
        model.addAttribute("ranking", juegoService.top10());
        return "juego/cancion-ranking";
    }
}