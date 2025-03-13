package com.clinica.agendamento.repository;

import com.clinica.agendamento.model.Dentista;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface DentistaRepository extends MongoRepository<Dentista, String> {
}
