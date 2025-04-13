package com.clinica.agendamento.controller;

import com.clinica.agendamento.service.PacienteService;

import jakarta.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class PaginaPrincipalController {


    @GetMapping("/")
    public String home() {
        return "index";
    }
}
