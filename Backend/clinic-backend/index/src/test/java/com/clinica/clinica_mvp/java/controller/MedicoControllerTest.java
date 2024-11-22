package com.clinica.clinica_mvp.java.controller;

import com.clinica.clinica_mvp.controller.MedicoController;
import com.clinica.clinica_mvp.model.medico;
import com.clinica.clinica_mvp.services.MedicoService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
public class MedicoControllerTest {

    @InjectMocks
    private MedicoController medicoController;

    @Mock
    private MedicoService medicoService;

    private medico mockMedico;

    @BeforeEach
    public void setUp() {
        mockMedico = new medico();
        mockMedico.setId(1L);
        mockMedico.setNome("Dr. João Silva");
        mockMedico.setConselho("CRM12345");
        mockMedico.setEstado("SP");
        mockMedico.setStatus(true);
    }

    @Test
    public void testListarTodos() {
        when(medicoService.listarTodos()).thenReturn(List.of(mockMedico));
        var medicos = medicoController.listarTodos();
        assertNotNull(medicos);
        assertEquals(1, medicos.size());
        verify(medicoService, times(1)).listarTodos();
    }

    @Test
    public void testSalvar() {
        medico novoMedico = new medico();
        novoMedico.setNome("Dr. Maria Oliveira");
        novoMedico.setConselho("CRM67890");
        novoMedico.setEstado("RJ");
        novoMedico.setStatus(true);
        when(medicoService.salvar(any(medico.class))).thenReturn(mockMedico);
        medico savedMedico = medicoController.salvar(novoMedico);
        assertNotNull(savedMedico);
        assertEquals("Dr. João Silva", savedMedico.getNome());
        verify(medicoService, times(1)).salvar(any(medico.class));
    }

    @Test
    public void testExcluir() {
        Long medicoId = 1L;
        doNothing().when(medicoService).excluir(medicoId);
        medicoController.excluir(medicoId);
        verify(medicoService, times(1)).excluir(medicoId);
    }
}
