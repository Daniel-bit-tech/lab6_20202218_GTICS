package com.example.lab06_20202218.Controller;

import com.example.lab06_20202218.Entity.Numeros_casa;
import com.example.lab06_20202218.Entity.Usuario;
import com.example.lab06_20202218.Services.JuegoService;
import com.example.lab06_20202218.Repository.UsuarioRepository;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/juego")
public class JuegoController {

    private final JuegoService juegoService;
    private final UsuarioRepository usuarioRepository;

    public JuegoController(JuegoService juegoService, UsuarioRepository usuarioRepository) {
        this.juegoService = juegoService;
        this.usuarioRepository = usuarioRepository;
    }

    @GetMapping("/camino-dulces")
    public String verJuego(Model model, @AuthenticationPrincipal UserDetails userDetails) {
        String username = userDetails.getUsername();
        Usuario usuario = usuarioRepository.findByCorreo(username).get();

        Numeros_casa numeroCasa = juegoService.obtenerNumeroCasaPorUsuario(usuario);
        model.addAttribute("numeroCasa", numeroCasa);
        return "juego/camino-dulces";
    }

    @PostMapping("/solicitar")
    public String solicitarAsignacion(@AuthenticationPrincipal UserDetails userDetails) {
        String username = userDetails.getUsername();
        Usuario usuario = usuarioRepository.findByCorreo(username).get();
        juegoService.solicitarAsignacion(usuario);
        return "redirect:/juego/camino-dulces";
    }

    @PostMapping("/adivinar")
    public String adivinarPasos(@RequestParam("pasos") int pasos, @AuthenticationPrincipal UserDetails userDetails, RedirectAttributes redirectAttributes) {
        String username = userDetails.getUsername();
        Usuario usuario = usuarioRepository.findByCorreo(username).get();

        boolean adivinado = juegoService.adivinar(usuario, pasos);
        if (adivinado) {
            redirectAttributes.addFlashAttribute("mensaje", "¡Felicidades, has adivinado el número mínimo de pasos!");
        } else {
            redirectAttributes.addFlashAttribute("error", "Intento fallido. Sigue intentando.");
        }
        return "redirect:/juego/camino-dulces";
    }

    @GetMapping("/ranking")
    public String verRanking(Model model) {
        model.addAttribute("ranking", juegoService.obtenerRanking());
        return "juego/ranking-juego";
    }
}