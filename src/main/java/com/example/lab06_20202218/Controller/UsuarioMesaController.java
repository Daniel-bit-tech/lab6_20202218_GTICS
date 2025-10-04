package com.example.lab06_20202218.Controller;

import com.example.lab06_20202218.Entity.Usuario;
import com.example.lab06_20202218.Repository.UsuarioRepository;
import com.example.lab06_20202218.Services.MesaService;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.bind.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/usuario/mesas")
public class UsuarioMesaController {

    private final MesaService mesaService;
    private final UsuarioRepository usuarioRepository;

    public UsuarioMesaController(MesaService mesaService, UsuarioRepository usuarioRepository) {
        this.mesaService = mesaService;
        this.usuarioRepository = usuarioRepository;
    }

    @GetMapping
    public String verMesas(Model model) {
        model.addAttribute("mesas", mesaService.getTodasLasMesas());
        model.addAttribute("mesasOcupadas", mesaService.getMesasOcupadasCount());
        model.addAttribute("mesasLibres", mesaService.getMesasLibresCount());
        return "usuario/mesas";
    }

    @PostMapping("/reservar")
    public String reservarMesa(@RequestParam("mesaId") Long mesaId,
                               @RequestParam("numPersonas") int numPersonas,
                               @AuthenticationPrincipal UserDetails userDetails,
                               RedirectAttributes redirectAttributes) {

        String username = userDetails.getUsername();

        Usuario usuario = usuarioRepository.findByCorreo(username)
                .orElseThrow(() -> new IllegalArgumentException("Usuario no encontrado"));

        if (!mesaService.puedeReservar(usuario)) {
            redirectAttributes.addFlashAttribute("error", "Ya tienes una mesa reservada");
        } else if (!mesaService.validarCapacidad(mesaId, numPersonas)) {
            redirectAttributes.addFlashAttribute("error", "La mesa no tiene capacidad suficiente");
        } else {
            mesaService.reservarMesa(usuario, mesaId);
        }

        return "redirect:/usuario/mesas";
    }
}
