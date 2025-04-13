package com.clinica.agendamento.controller;

import com.clinica.agendamento.model.Paciente;
import com.clinica.agendamento.model.PacienteDTO;
import com.clinica.agendamento.repository.PacienteRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.Period;
import java.util.List;

@Controller
@RequestMapping("/pacientes")
public class PacienteController {

    @Autowired
    private PacienteRepository pacienteRepository;

    private final BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

    // Exibe o formulário de cadastro
    @GetMapping("/novo")
    public String mostrarFormulario(Model model) {
        model.addAttribute("pacienteDTO", new PacienteDTO());
        return "pacientes/formulario-paciente";
    }

    // Salva um novo paciente
    @PostMapping
    public String salvarPaciente(@Valid @ModelAttribute("pacienteDTO") PacienteDTO pacienteDTO,
                                 BindingResult result, Model model) {

        // Validação de idade
        LocalDate hoje = LocalDate.now();
        Period idade = Period.between(pacienteDTO.getDataNascimento(), hoje);
        if (idade.getYears() < 14) {
            result.rejectValue("dataNascimento", "error.pacienteDTO", "O paciente deve ter pelo menos 14 anos.");
        }

        // Validação de senha
        if (!pacienteDTO.getSenha().equals(pacienteDTO.getConfirmarSenha())) {
            result.rejectValue("confirmarSenha", "error.pacienteDTO", "As senhas não coincidem.");
        }

        if (result.hasErrors()) {
            return "pacientes/formulario-paciente";
        }

        // Conversão DTO -> Entidade
        Paciente paciente = new Paciente();
        paciente.setNome(pacienteDTO.getNome());
        paciente.setEmail(pacienteDTO.getEmail());
        paciente.setTelefone(pacienteDTO.getTelefone());
        paciente.setDataNascimento(pacienteDTO.getDataNascimento());
        paciente.setSenha(encoder.encode(pacienteDTO.getSenha()));

        pacienteRepository.save(paciente);
        return "redirect:/pacientes";
    }

    // Lista todos os pacientes
    @GetMapping
    public String listarPacientes(Model model) {
        List<Paciente> pacientes = pacienteRepository.findAll();
        model.addAttribute("pacientes", pacientes);
        return "pacientes/lista-pacientes";
    }

    // Exibe o formulário de edição
 // Exibe o formulário de edição
    @GetMapping("/editar/{id}")
    public String mostrarFormularioEdicao(@PathVariable String id, Model model) {
        Paciente paciente = pacienteRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("ID de paciente inválido: " + id));

        PacienteDTO pacienteDTO = new PacienteDTO();
        pacienteDTO.setId(paciente.getId()); // Adicionado
        pacienteDTO.setNome(paciente.getNome());
        pacienteDTO.setEmail(paciente.getEmail());
        pacienteDTO.setTelefone(paciente.getTelefone());
        pacienteDTO.setDataNascimento(paciente.getDataNascimento());

        model.addAttribute("pacienteDTO", pacienteDTO);
        return "pacientes/formulario-paciente";
    }

    // Atualiza um paciente existente
    @PostMapping("/editar")
    public String atualizarPaciente(@Valid @ModelAttribute("pacienteDTO") PacienteDTO pacienteDTO,
                                    BindingResult result) {

        if (result.hasErrors()) {
            return "pacientes/formulario-paciente";
        }

        Paciente pacienteExistente = pacienteRepository.findById(pacienteDTO.getId())
                .orElseThrow(() -> new IllegalArgumentException("ID de paciente inválido: " + pacienteDTO.getId()));

        pacienteExistente.setNome(pacienteDTO.getNome());
        pacienteExistente.setEmail(pacienteDTO.getEmail());
        pacienteExistente.setTelefone(pacienteDTO.getTelefone());
        pacienteExistente.setDataNascimento(pacienteDTO.getDataNascimento());

        if (pacienteDTO.getSenha() != null && !pacienteDTO.getSenha().isEmpty()) {
            if (!pacienteDTO.getSenha().equals(pacienteDTO.getConfirmarSenha())) {
                result.rejectValue("confirmarSenha", "error.pacienteDTO", "As senhas não coincidem.");
                return "pacientes/formulario-paciente";
            }
            pacienteExistente.setSenha(encoder.encode(pacienteDTO.getSenha()));
        }

        pacienteRepository.save(pacienteExistente);
        return "redirect:/pacientes";
    }


    // Atualiza um paciente existente
    @PostMapping("/editar/{id}")
    public String atualizarPaciente(@PathVariable String id,
                                    @Valid @ModelAttribute("pacienteDTO") PacienteDTO pacienteDTO,
                                    BindingResult result) {

        if (result.hasErrors()) {
            return "pacientes/formulario-paciente";
        }

        Paciente pacienteExistente = pacienteRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("ID de paciente inválido: " + id));

        pacienteExistente.setNome(pacienteDTO.getNome());
        pacienteExistente.setEmail(pacienteDTO.getEmail());
        pacienteExistente.setTelefone(pacienteDTO.getTelefone());
        pacienteExistente.setDataNascimento(pacienteDTO.getDataNascimento());

        // Atualiza senha se campo foi preenchido
        if (pacienteDTO.getSenha() != null && !pacienteDTO.getSenha().isEmpty()) {
            if (!pacienteDTO.getSenha().equals(pacienteDTO.getConfirmarSenha())) {
                result.rejectValue("confirmarSenha", "error.pacienteDTO", "As senhas não coincidem.");
                return "pacientes/formulario-paciente";
            }
            pacienteExistente.setSenha(encoder.encode(pacienteDTO.getSenha()));
        }

        pacienteRepository.save(pacienteExistente);
        return "redirect:/pacientes";
    }

    // Exclui um paciente
    @GetMapping("/excluir/{id}")
    public String excluirPaciente(@PathVariable String id) {
        pacienteRepository.deleteById(id);
        return "redirect:/pacientes";
    }
}
