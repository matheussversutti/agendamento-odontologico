package com.clinica.agendamento.service;

import com.clinica.agendamento.model.Paciente;
import com.clinica.agendamento.repository.PacienteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class PacienteService {

    @Autowired
    private PacienteRepository pacienteRepository;

    public String buscarTipoPorEmail(String email) {
        Optional<Paciente> pacienteOpt = pacienteRepository.findByEmail(email);

        if (pacienteOpt.isPresent()) {
            return "CLIENTE"; // Como falamos, se é paciente, é CLIENTE
        }

        return null; // Pode retornar "ANÔNIMO", "DESCONHECIDO", etc., se preferir
    }
}