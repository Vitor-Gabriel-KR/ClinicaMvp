package com.clinica.clinica_mvp.java.controller;

import com.clinica.clinica_mvp.controller.ConsultaController;
import com.clinica.clinica_mvp.model.consulta;
import com.clinica.clinica_mvp.services.ConsultaService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
public class ConsultaControllerTest {

    @InjectMocks
    private ConsultaController consultaController;

    @Mock
    private ConsultaService consultaService;

    private consulta mockConsulta;

    @BeforeEach
    public void setUp() {
        mockConsulta = new consulta();
        mockConsulta.setId(1L);
        mockConsulta.setDia("2024-11-23");
        mockConsulta.setDataHora(LocalTime.of(8, 0));
        mockConsulta.setStatus("Agendada");
    }

    @Test
    public void testListarTodos() {
        when(consultaService.listarTodos()).thenReturn(List.of(mockConsulta));

        var consultas = consultaController.listarTodos();
        assertNotNull(consultas);
        assertEquals(1, consultas.size());
        verify(consultaService, times(1)).listarTodos();
    }

    @Test
    public void testSalvar() {
        consulta novaConsulta = new consulta();
        novaConsulta.setDia("2024-11-23");
        novaConsulta.setDataHora(LocalTime.of(9, 0));
        novaConsulta.setStatus("Agendada");

        when(consultaService.salvar(any(consulta.class))).thenReturn(mockConsulta);

        consulta savedConsulta = consultaController.salvar(novaConsulta);
        assertNotNull(savedConsulta);
        assertEquals("2024-11-23", savedConsulta.getDia());
        verify(consultaService, times(1)).salvar(any(consulta.class));
    }

    @Test
    public void testExcluir() {
        Long consultaId = 1L;
        doNothing().when(consultaService).excluir(consultaId);

        consultaController.excluir(consultaId);
        verify(consultaService, times(1)).excluir(consultaId);
    }

    @Test
    public void testAtualizarConsultaNaoImplementada() {
        consulta novaConsulta = new consulta();
        novaConsulta.setDia("2024-11-25");

        UnsupportedOperationException exception = assertThrows(UnsupportedOperationException.class, () -> {
            consultaController.atualizar(1L, novaConsulta);
        });

        assertEquals("Unimplemented method 'atualizar'", exception.getMessage());
    }
}
