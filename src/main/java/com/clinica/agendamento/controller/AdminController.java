package com.clinica.agendamento.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class AdminController {

    @GetMapping("/admin")
    @ResponseBody
    public String adminPage() {
        return "Bem-vindo à página de administração!";
    }
}