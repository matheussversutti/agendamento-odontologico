package com.clinica.agendamento.repository;

import com.clinica.agendamento.model.Consulta;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.time.Instant;
import java.util.List;

public interface ConsultaRepository extends MongoRepository<Consulta, String> {

    // Método para contar consultas entre duas datas
    @Query(value = "{ 'dataHora' : { $gte: ?0, $lt: ?1 } }", count = true)
    long countByDataHoraBetween(Instant inicio, Instant fim);

    // Método para buscar consultas por status
    List<Consulta> findByStatus(String status);

    // Método para buscar consultas entre duas datas
    @Query("{ 'dataHora' : { $gte: ?0, $lt: ?1 } }")
    List<Consulta> findByDataHoraBetween(Instant inicio, Instant fim);
}