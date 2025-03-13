package com.clinica.agendamento.repository;

import com.clinica.agendamento.model.Administrador;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface AdministradorRepository extends MongoRepository<Administrador, String> {
}
