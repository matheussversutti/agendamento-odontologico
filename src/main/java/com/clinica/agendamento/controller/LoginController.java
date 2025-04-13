package com.clinica.agendamento.controller;

import com.clinica.agendamento.model.Paciente;
import com.clinica.agendamento.repository.PacienteRepository;
import jakarta.servlet.http.HttpSession;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class LoginController {

    @Autowired
    private PacienteRepository pacienteRepository;

    // Exibe a tela de login do admin
    @GetMapping("/login-admin")
    public String loginAdmin() {
        return "login-admin";
    }

    // Exibe a tela de login do funcionário
    @GetMapping("/login-funcionario")
    public String loginFuncionario() {
        return "login-funcionario";
    }

    // Exibe a tela de login do paciente
    @GetMapping("/login-paciente")
    public String loginPaciente() {
        return "login-paciente";
    }

    // Login de paciente via banco de dados
    

    // Logout
    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate();
        return "redirect:/";
    }
}
