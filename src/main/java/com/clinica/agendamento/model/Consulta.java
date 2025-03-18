package com.clinica.agendamento.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.Instant;

@Document(collection = "consultas") // Nome da coleção no MongoDB
public class Consulta {

    @Id
    private String id; // Identificador único

    private Instant dataHora; // Data e hora da consulta
    private String status; // Status da consulta (agendada, cancelada, concluída)

    @DBRef
    private Paciente paciente; // Referência ao documento Paciente

    @DBRef
    private Dentista dentista; // Referência ao documento Dentista

    // Construtor padrão
    public Consulta() {
    }

    // Construtor com parâmetros
    public Consulta(Instant dataHora, String status, Paciente paciente, Dentista dentista) {
        this.dataHora = dataHora;
        this.status = status;
        this.paciente = paciente;
        this.dentista = dentista;
    }

    // Getters e Setters
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Instant getDataHora() {
        return dataHora;
    }

    public void setDataHora(Instant dataHora) {
        this.dataHora = dataHora;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Paciente getPaciente() {
        return paciente;
    }

    public void setPaciente(Paciente paciente) {
        this.paciente = paciente;
    }

    public Dentista getDentista() {
        return dentista;
    }

    public void setDentista(Dentista dentista) {
        this.dentista = dentista;
    }

    // Método toString para facilitar a visualização
    @Override
    public String toString() {
        return "Consulta{" +
                "id='" + id + '\'' +
                ", dataHora=" + dataHora +
                ", status='" + status + '\'' +
                ", paciente=" + paciente +
                ", dentista=" + dentista +
                '}';
    }
}