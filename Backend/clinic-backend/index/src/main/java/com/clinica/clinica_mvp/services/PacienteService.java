package com.clinica.clinica_mvp.services;

import com.clinica.clinica_mvp.model.paciente;
import com.clinica.clinica_mvp.repository.PacienteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PacienteService {
    @Autowired
    private PacienteRepository pacienteRepository;

    public List<paciente> listarTodos() {
        return pacienteRepository.findAll();
    }

    public paciente salvar(paciente paciente) {
        return pacienteRepository.save(paciente);
    }

    public void excluir(Long id) {
        pacienteRepository.deleteById(id);
    }
}
