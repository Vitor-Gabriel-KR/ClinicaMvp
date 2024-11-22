package com.clinica.clinica_mvp.services;

import com.clinica.clinica_mvp.model.consulta;
import com.clinica.clinica_mvp.repository.ConsultaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ConsultaService {
    @Autowired
    private ConsultaRepository consultaRepository;

    public List<consulta> listarTodos() {
        return consultaRepository.findAll();
    }

    public consulta salvar(consulta consulta) {
        return consultaRepository.save(consulta);
    }

    public void excluir(Long id) {
        consultaRepository.deleteById(id);
    }

    public Object buscarPorId(long l) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'buscarPorId'");
    }
}
