package com.clinica.clinica_mvp.controller;

import com.clinica.clinica_mvp.model.consulta;
import com.clinica.clinica_mvp.services.ConsultaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/consultas")
public class ConsultaController {

    @Autowired
    private ConsultaService consultaService;

    @GetMapping
    public List<consulta> listarTodos() {
        return consultaService.listarTodos();
    }

    @PostMapping
    public consulta salvar(@RequestBody consulta consulta) {
        return consultaService.salvar(consulta);
    }

    @DeleteMapping("/{id}")
    public void excluir(@PathVariable Long id) {
        consultaService.excluir(id);
    }

    public ResponseEntity<consulta> atualizar(long l, consulta novaConsulta) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'atualizar'");
    }
}
