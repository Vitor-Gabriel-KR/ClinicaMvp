package com.clinica.clinica_mvp.controller;

import com.clinica.clinica_mvp.model.medico;
import com.clinica.clinica_mvp.services.MedicoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/medicos")
public class MedicoController {
    
    @Autowired
    private MedicoService medicoService;

    @GetMapping
    public List<medico> listarTodos() {
        return medicoService.listarTodos();
    }

    @PostMapping
    public medico salvar(@RequestBody medico medico) {
        return medicoService.salvar(medico);
    }

    @DeleteMapping("/{id}")
    public void excluir(@PathVariable Long id) {
        medicoService.excluir(id);
    }
}
