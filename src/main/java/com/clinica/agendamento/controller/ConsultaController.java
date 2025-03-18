package com.clinica.agendamento.controller;

import com.clinica.agendamento.model.Consulta;
import com.clinica.agendamento.repository.ConsultaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/consultas") // Endpoint base para consultas
public class ConsultaController {

    @Autowired
    private ConsultaRepository consultaRepository;

    // 1. Listar todas as consultas
    @GetMapping
    public ResponseEntity<List<Consulta>> listarConsultas() {
        List<Consulta> consultas = consultaRepository.findAll();
        return ResponseEntity.ok(consultas);
    }

    // 2. Buscar consulta por ID
    @GetMapping("/{id}")
    public ResponseEntity<Consulta> buscarConsultaPorId(@PathVariable String id) {
        Optional<Consulta> consulta = consultaRepository.findById(id);
        return consulta.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    // 3. Criar uma nova consulta
    @PostMapping
    public ResponseEntity<Consulta> criarConsulta(@RequestBody Consulta consulta) {
        consulta.setId(null); // Garante que o ID seja gerado automaticamente
        Consulta novaConsulta = consultaRepository.save(consulta);
        return ResponseEntity.status(HttpStatus.CREATED).body(novaConsulta);
    }

    // 4. Atualizar uma consulta existente
    @PutMapping("/{id}")
    public ResponseEntity<Consulta> atualizarConsulta(@PathVariable String id, @RequestBody Consulta consultaAtualizada) {
        return consultaRepository.findById(id)
                .map(consulta -> {
                    consulta.setDataHora(consultaAtualizada.getDataHora());
                    consulta.setStatus(consultaAtualizada.getStatus());
                    consulta.setPaciente(consultaAtualizada.getPaciente());
                    consulta.setDentista(consultaAtualizada.getDentista());
                    Consulta consultaSalva = consultaRepository.save(consulta);
                    return ResponseEntity.ok(consultaSalva);
                })
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    // 5. Excluir uma consulta
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> excluirConsulta(@PathVariable String id) {
        if (consultaRepository.existsById(id)) {
            consultaRepository.deleteById(id);
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    // 6. Listar consultas por status
    @GetMapping("/status/{status}")
    public ResponseEntity<List<Consulta>> listarConsultasPorStatus(@PathVariable String status) {
        List<Consulta> consultas = consultaRepository.findByStatus(status);
        return ResponseEntity.ok(consultas);
    }

    // 7. Listar consultas entre duas datas
    @GetMapping("/periodo")
    public ResponseEntity<List<Consulta>> listarConsultasPorPeriodo(
            @RequestParam Instant inicio,
            @RequestParam Instant fim) {
        List<Consulta> consultas = consultaRepository.findByDataHoraBetween(inicio, fim);
        return ResponseEntity.ok(consultas);
    }
}