package com.clinica.agendamento.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "dentistas") // Nome da coleção no MongoDB
public class Dentista {

    @Id
    private String id; // Identificador único

    private String nome; // Nome do dentista
    private String especialidade; // Especialidade do dentista
    private String email; // Email do dentista
    private String telefone; // Telefone do dentista

    // Construtor padrão
    public Dentista() {
    }

    // Construtor com parâmetros
    public Dentista(String nome, String especialidade, String email, String telefone) {
        this.nome = nome;
        this.especialidade = especialidade;
        this.email = email;
        this.telefone = telefone;
    }

    // Getters e Setters
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getEspecialidade() {
        return especialidade;
    }

    public void setEspecialidade(String especialidade) {
        this.especialidade = especialidade;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

  
}