package com.clinica.clinica_mvp.java.Service;

import com.clinica.clinica_mvp.model.consulta;
import com.clinica.clinica_mvp.repository.ConsultaRepository;
import com.clinica.clinica_mvp.services.ConsultaService;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalTime;
import java.util.List;

@ExtendWith(MockitoExtension.class)
public class ConsultaServiceTest {

    @InjectMocks
    private ConsultaService consultaService;

    @Mock
    private ConsultaRepository consultaRepository;

    private consulta mockConsulta;

    @BeforeEach
    public void setUp() {
        mockConsulta = new consulta();
        mockConsulta.setId(1L);
        mockConsulta.setDia("Segunda-feira");
        mockConsulta.setDataHora(LocalTime.of(9, 0));
        mockConsulta.setStatus("Agendada");
    }

    @Test
    public void testListarTodos() {
        when(consultaRepository.findAll()).thenReturn(List.of(mockConsulta));

        List<consulta> consultas = consultaService.listarTodos();

        assertNotNull(consultas);
        assertEquals(1, consultas.size());
        assertEquals("Segunda-feira", consultas.get(0).getDia());

        verify(consultaRepository, times(1)).findAll();
    }

    @Test
    public void testSalvar() {
        consulta novaConsulta = new consulta();
        novaConsulta.setDia("Quarta-feira");
        novaConsulta.setDataHora(LocalTime.of(14, 0));
        novaConsulta.setStatus("Confirmada");

        when(consultaRepository.save(any(consulta.class))).thenReturn(mockConsulta);

        consulta savedConsulta = consultaService.salvar(novaConsulta);

        assertNotNull(savedConsulta);
        assertEquals("Segunda-feira", savedConsulta.getDia());

        verify(consultaRepository, times(1)).save(any(consulta.class));
    }

    @Test
    public void testExcluir() {
        Long consultaId = 1L;

        doNothing().when(consultaRepository).deleteById(consultaId);

        consultaService.excluir(consultaId);

        verify(consultaRepository, times(1)).deleteById(consultaId);
    }
}
