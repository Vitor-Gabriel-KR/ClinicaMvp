package com.clinica.clinica_mvp.java.controller;

import com.clinica.clinica_mvp.controller.AgendaController;
import com.clinica.clinica_mvp.model.agenda;
import com.clinica.clinica_mvp.services.AgendaService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.boot.test.mock.mockito.MockBean;

import static org.mockito.Mockito.*;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class AgendaControllerTest {

    @Autowired
    private AgendaController agendaController;

    @MockBean
    private AgendaService agendaService;

    private agenda mockAgenda;

    @BeforeEach
    public void setUp() {
        mockAgenda = new agenda();
        mockAgenda.setId(1L);
        mockAgenda.setDiasSemana("Segunda-feira");
        mockAgenda.setHorariosDisponiveis("08:00 - 12:00");
        mockAgenda.setStatus(true);

        
        when(agendaService.listarTodos()).thenReturn(List.of(mockAgenda));
        when(agendaService.salvar(any(agenda.class))).thenReturn(mockAgenda);
        when(agendaService.buscarPorId(1L)).thenReturn(Optional.of(mockAgenda));
    }

    @Test
    public void testListarTodos() {
        var agendas = agendaController.listarTodos();
        assertNotNull(agendas);
        assertEquals(1, agendas.size());
    }

    @Test
    public void testSalvar() {
        agenda novaAgenda = new agenda();
        novaAgenda.setDiasSemana("Segunda-feira");
        novaAgenda.setHorariosDisponiveis("14:00 - 18:00");
        novaAgenda.setStatus(true);

        agenda savedAgenda = agendaController.salvar(novaAgenda);
        assertNotNull(savedAgenda);
        assertEquals("Segunda-feira", savedAgenda.getDiasSemana());
    }

    @Test
    public void testExcluir() {
        Long agendaId = 1L;
        doNothing().when(agendaService).excluir(agendaId);

        agendaController.excluir(agendaId);
        verify(agendaService, times(1)).excluir(agendaId);
    }

    @Test
    public void testAtualizar() {
        agenda novaAgenda = new agenda();
        novaAgenda.setDiasSemana("Quarta-feira");
        novaAgenda.setHorariosDisponiveis("10:00 - 14:00");
        novaAgenda.setStatus(true);

        ResponseEntity<agenda> response = agendaController.atualizar(1L, novaAgenda);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Quarta-feira", response.getBody().getDiasSemana());
    }

    @Test
    public void testAtualizarAgendaNaoEncontrada() {
        agenda novaAgenda = new agenda();
        novaAgenda.setDiasSemana("Quarta-feira");

        when(agendaService.buscarPorId(2L)).thenReturn(Optional.empty());

        ResponseEntity<agenda> response = agendaController.atualizar(2L, novaAgenda);
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }
}
