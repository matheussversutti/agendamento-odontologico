package com.clinica.agendamento;

import com.clinica.agendamento.model.Paciente;
import com.clinica.agendamento.repository.PacienteRepository;

import java.time.LocalDate;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class TesteMongoDB implements CommandLineRunner {

    private final PacienteRepository pacienteRepository;

    public TesteMongoDB(PacienteRepository pacienteRepository) {
        this.pacienteRepository = pacienteRepository;
    }

    @Override
    public void run(String... args) throws Exception {
        Paciente paciente = new Paciente();
        paciente.setNome("Maria Oliveira");
        paciente.setEmail("maria@example.com");
        paciente.setTelefone("123456789");
        paciente.setDataNascimento(LocalDate.parse("1990-05-15"));

        pacienteRepository.save(paciente);
        System.out.println("Paciente salvo com sucesso!");
    }
}
