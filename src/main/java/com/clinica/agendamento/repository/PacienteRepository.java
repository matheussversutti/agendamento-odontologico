package com.clinica.agendamento.repository;

import com.clinica.agendamento.model.Paciente;

import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;

public interface PacienteRepository extends MongoRepository<Paciente, String> {
	Optional<Paciente> findByEmail(String email);
  
}