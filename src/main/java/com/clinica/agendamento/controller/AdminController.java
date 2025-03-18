package com.clinica.agendamento.controller;

import com.clinica.agendamento.repository.ConsultaRepository;
import com.clinica.agendamento.repository.PacienteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;

@Controller
public class AdminController {

    @Autowired
    private PacienteRepository pacienteRepository; // Repositório de pacientes

    @Autowired
    private ConsultaRepository consultaRepository; // Repositório de consultas

    @GetMapping("/admin/dashboard")
    public String dashboard(Model model) {
        // Obtém o total de pacientes cadastrados
        long totalPacientes = pacienteRepository.count();

        // Obtém o total de consultas agendadas para hoje
        LocalDate hoje = LocalDate.now();
        Instant inicioDoDia = hoje.atStartOfDay(ZoneId.systemDefault()).toInstant();
        Instant fimDoDia = hoje.plusDays(1).atStartOfDay(ZoneId.systemDefault()).toInstant();

        long consultasHoje = consultaRepository.countByDataHoraBetween(inicioDoDia, fimDoDia);

        // Passa os valores para o template Thymeleaf
        model.addAttribute("totalPacientes", totalPacientes);
        model.addAttribute("consultasHoje", consultasHoje);

        return "admin/dashboard"; // Retorna a página dashboard.html
    }
}