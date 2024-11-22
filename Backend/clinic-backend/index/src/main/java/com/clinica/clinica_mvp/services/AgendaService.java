package com.clinica.clinica_mvp.services;

import com.clinica.clinica_mvp.model.agenda;
import com.clinica.clinica_mvp.repository.AgendaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AgendaService {
    @Autowired
    private AgendaRepository agendaRepository;

    public List<agenda> listarTodos() {
        return agendaRepository.findAll();
    }

    public agenda salvar(agenda agenda) {
        return agendaRepository.save(agenda);
    }

    public void excluir(Long id) {
        agendaRepository.deleteById(id);
    }

    public Optional<agenda> buscarPorId(Long id) {
        return agendaRepository.findById(id);
    }
}
