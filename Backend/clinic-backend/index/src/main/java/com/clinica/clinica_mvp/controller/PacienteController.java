package com.clinica.clinica_mvp.controller;

import com.clinica.clinica_mvp.model.paciente;
import com.clinica.clinica_mvp.services.PacienteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/pacientes")
public class PacienteController {

    @Autowired
    private PacienteService pacienteService;

    @GetMapping
    public List<paciente> listarTodos() {
        return pacienteService.listarTodos();
    }

    @PostMapping
    public paciente salvar(@RequestBody paciente paciente) {
        System.out.println(paciente.getDataNascimento() + "data");
        return pacienteService.salvar(paciente);
    }

    @DeleteMapping("/{id}")
    public void excluir(@PathVariable Long id) {
        pacienteService.excluir(id);
    }
}
