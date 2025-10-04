package com.example.lab06_20202218.Controller;

import com.example.lab06_20202218.Services.MesaService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/admin/mesas")
public class AdminMesaController {

    private final MesaService mesaService;

    public AdminMesaController(MesaService mesaService) {
        this.mesaService = mesaService;
    }

    @GetMapping
    public String administrarMesas(Model model) {
        model.addAttribute("mesas", mesaService.getTodasLasMesas());
        model.addAttribute("mesasOcupadas", mesaService.getMesasOcupadasCount());
        model.addAttribute("mesasLibres", mesaService.getMesasLibresCount());
        return "admin/mesas";
    }

    @PostMapping("/liberar")
    public String liberarMesa(@RequestParam("mesaId") Long mesaId) {
        mesaService.liberarMesa(mesaId);
        return "redirect:/admin/mesas";
    }

    @PostMapping("/reasignar")
    public String reasignarAsientos(@RequestParam("mesaId") Long mesaId, @RequestParam("capacidad") int capacidad) {
        mesaService.reasignarAsientos(mesaId, capacidad);
        return "redirect:/admin/mesas";
    }
}