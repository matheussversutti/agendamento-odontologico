package com.clinica.agendamento.repository;

import com.clinica.agendamento.model.Paciente;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface PacienteRepository extends MongoRepository<Paciente, String> {
  
}