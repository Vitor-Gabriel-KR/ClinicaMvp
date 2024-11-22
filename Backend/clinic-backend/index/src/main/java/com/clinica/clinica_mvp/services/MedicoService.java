package com.clinica.clinica_mvp.services;

import com.clinica.clinica_mvp.model.medico;
import com.clinica.clinica_mvp.repository.MedicoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MedicoService {
    @Autowired
    private MedicoRepository medicoRepository;

    public List<medico> listarTodos() {
        return medicoRepository.findAll();
    }

    public medico salvar(medico medico) {
        return medicoRepository.save(medico);
    }

    public void excluir(Long id) {
        medicoRepository.deleteById(id);
    }
}
