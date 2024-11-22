package com.clinica.clinica_mvp.controller;

import com.clinica.clinica_mvp.model.agenda;
import com.clinica.clinica_mvp.services.AgendaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/agendas")
public class AgendaController {

    @Autowired
    private AgendaService agendaService;

    @GetMapping
    public List<agenda> listarTodos() {
        return agendaService.listarTodos();
    }

    @PostMapping
    public agenda salvar(@RequestBody agenda agenda) {
        return agendaService.salvar(agenda);
    }

    @DeleteMapping("/{id}")
    public void excluir(@PathVariable Long id) {
        agendaService.excluir(id);
    }

    @PutMapping("/{id}")
    public ResponseEntity<agenda> atualizar(@PathVariable Long id, @RequestBody agenda novaAgenda) {
        return agendaService.buscarPorId(id)
            .map(agendaExistente -> {
                agendaExistente.setMedico(novaAgenda.getMedico());
                agendaExistente.setDiasSemana(novaAgenda.getDiasSemana());
                agendaExistente.setHorariosDisponiveis(novaAgenda.getHorariosDisponiveis());
                agendaExistente.setStatus(novaAgenda.getStatus());
                agendaService.salvar(agendaExistente);
                return ResponseEntity.ok(agendaExistente);
            })
            .orElseGet(() -> ResponseEntity.notFound().build());
    }
}
