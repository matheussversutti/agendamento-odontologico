package com.clinica.agendamento.repository;

import com.clinica.agendamento.model.Consulta;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface ConsultaRepository extends MongoRepository<Consulta, String> {
}
