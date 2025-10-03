package com.example.lab06_20202218.Controller;

import com.example.lab06_20202218.Entity.Intenciones;
import com.example.lab06_20202218.Entity.Usuario;
import com.example.lab06_20202218.Repository.UsuarioRepository;
import com.example.lab06_20202218.Services.IntencionService;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/intenciones")
public class IntencionController {

    private final IntencionService intencionService;
    private final UsuarioRepository usuarioRepo;

    public IntencionController(IntencionService intencionService, UsuarioRepository usuarioRepo) {
        this.intencionService = intencionService;
        this.usuarioRepo = usuarioRepo;
    }

    @GetMapping
    public String index(Model model) {
        if (!model.containsAttribute("intencion")) {
            model.addAttribute("intencion", new Intenciones());
        }
        return "intenciones/form";
    }


    @GetMapping("/admin")
    public String listarIntencionesAdmin(Model model) {
        model.addAttribute("intenciones", intencionService.listarTodas());
        return "intenciones/admin";
    }

    @PostMapping("/save")
    public String save(@Valid @ModelAttribute("intencion") Intenciones intencion,
                       BindingResult result,
                       @AuthenticationPrincipal User user,
                       HttpSession session,
                       Model model) {

        if (session.getAttribute("intencion_registrada") != null) {
            model.addAttribute("error", "Ya registraste una intención");
            return "intenciones/form";
        }

        if (result.hasErrors()) {
            return "intenciones/form";
        }

        Usuario u = usuarioRepo.findByCorreo(user.getUsername()).orElseThrow();
        intencionService.crear(u, intencion.getDescripcion());
        session.setAttribute("intencion_registrada", true);
        model.addAttribute("exito", "Intención registrada");

        model.addAttribute("intencion", new Intenciones());

        return "intenciones/form";
    }
}