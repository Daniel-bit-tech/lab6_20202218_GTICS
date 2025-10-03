package com.example.lab06_20202218.Controller;

import com.example.lab06_20202218.Entity.Heroes_navales;
import com.example.lab06_20202218.Services.HeroeService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/heroes")
public class HeroeController {

    private final HeroeService heroeService;

    public HeroeController(HeroeService heroeService) {
        this.heroeService = heroeService;
    }


    @GetMapping
    public String listarHeroes(Model model) {
        model.addAttribute("heroes", heroeService.listarTodos());
        return "heroes/lista";
    }


    @GetMapping("/registrar")
    public String formularioRegistro(Model model) {
        model.addAttribute("heroe", new Heroes_navales());
        return "heroes/form";
    }

    @PostMapping("/save")
    public String guardarHeroe(@ModelAttribute("heroe") Heroes_navales heroe) {
        heroeService.guardar(heroe);
        return "redirect:/heroes";
    }
}