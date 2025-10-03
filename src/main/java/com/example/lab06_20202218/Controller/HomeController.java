package com.example.lab06_20202218.Controller;


import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

    @GetMapping("/")
    public String index(@AuthenticationPrincipal User user) {
        if (user != null) {
            return "redirect:/heroes";
        }
        return "index";
    }
}