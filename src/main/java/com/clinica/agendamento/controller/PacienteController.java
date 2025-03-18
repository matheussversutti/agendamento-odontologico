package com.clinica.agendamento.controller;

import com.clinica.agendamento.model.Paciente;
import com.clinica.agendamento.repository.PacienteRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/pacientes")
public class PacienteController {

    @Autowired
    private PacienteRepository pacienteRepository;

    // Exibe o formulário de cadastro
    @GetMapping("/novo")
    public String mostrarFormulario(Model model) {
        model.addAttribute("paciente", new Paciente());
        return "pacientes/formulario-paciente";
    }

    // Salva um novo paciente
    @PostMapping
    public String salvarPaciente(@Valid @ModelAttribute("paciente") Paciente paciente, BindingResult result, Model model) {
        if (result.hasErrors()) {
            return "pacientes/formulario-paciente"; // Retorna ao formulário em caso de erro
        }
        pacienteRepository.save(paciente); // Salva no banco de dados
        return "redirect:/pacientes"; // Redireciona para a lista de pacientes
    }

    // Lista todos os pacientes
    @GetMapping
    public String listarPacientes(Model model) {
        List<Paciente> pacientes = pacienteRepository.findAll();
        model.addAttribute("pacientes", pacientes);
        return "pacientes/lista-pacientes";
    }

    // Exibe o formulário de edição
    @GetMapping("/editar/{id}")
    public String mostrarFormularioEdicao(@PathVariable String id, Model model) {
        Paciente paciente = pacienteRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("ID de paciente inválido: " + id));
        model.addAttribute("paciente", paciente);
        return "pacientes/formulario-paciente";
    }

    // Atualiza um paciente existente
    @PostMapping("/editar/{id}")
    public String atualizarPaciente(@PathVariable String id, @Valid @ModelAttribute("paciente") Paciente paciente, BindingResult result) {
        if (result.hasErrors()) {
            return "pacientes/formulario-paciente"; // Retorna ao formulário em caso de erro
        }
        paciente.setId(id); // Garante que o ID seja mantido
        pacienteRepository.save(paciente); // Atualiza no banco de dados
        return "redirect:/pacientes"; // Redireciona para a lista de pacientes
    }

    // Exclui um paciente
    @GetMapping("/excluir/{id}")
    public String excluirPaciente(@PathVariable String id) {
        pacienteRepository.deleteById(id); // Exclui do banco de dados
        return "redirect:/pacientes"; // Redireciona para a lista de pacientes
    }
    
    
}