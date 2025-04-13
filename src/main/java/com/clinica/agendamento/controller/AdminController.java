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
    private PacienteRepository pacienteRepository;

    @Autowired
    private ConsultaRepository consultaRepository;

    @GetMapping("/painel-admin")
    public String dashboard(Model model) {
        // Calculando o total de pacientes
        long totalPacientes = pacienteRepository.count();

        // Calculando o total de consultas no dia
        LocalDate hoje = LocalDate.now();
        Instant inicioDoDia = hoje.atStartOfDay(ZoneId.systemDefault()).toInstant();
        Instant fimDoDia = hoje.plusDays(1).atStartOfDay(ZoneId.systemDefault()).toInstant();

        long consultasHoje = consultaRepository.countByDataHoraBetween(inicioDoDia, fimDoDia);

        // Passando os dados para a view
        model.addAttribute("totalPacientes", totalPacientes);
        model.addAttribute("consultasHoje", consultasHoje);

        // Retorna a view do painel
        return "admin/painel-admin";
    }
}
