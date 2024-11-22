package com.clinica.clinica_mvp.java.controller;

import com.clinica.clinica_mvp.controller.PacienteController;
import com.clinica.clinica_mvp.model.paciente;
import com.clinica.clinica_mvp.services.PacienteService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.List;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
public class PacienteControllerTest {

    @InjectMocks
    private PacienteController pacienteController;

    @Mock
    private PacienteService pacienteService;

    private paciente mockPaciente;

    @BeforeEach
    public void setUp() {
        mockPaciente = new paciente();
        mockPaciente.setId(1L);
        mockPaciente.setNome("Carlos Silva");
        mockPaciente.setDataNascimento(LocalDate.of(1990, 5, 15));
        mockPaciente.setTelefone("123456789");
        mockPaciente.setEndereco("Rua A, 123");
        mockPaciente.setEmail("carlos.silva@example.com");
        mockPaciente.setStatus(true);
    }

    @Test
    public void testListarTodos() {
        when(pacienteService.listarTodos()).thenReturn(List.of(mockPaciente));
        var pacientes = pacienteController.listarTodos();
        assertNotNull(pacientes);
        assertEquals(1, pacientes.size());
        verify(pacienteService, times(1)).listarTodos();
    }

    @Test
    public void testSalvar() {
        paciente novoPaciente = new paciente();
        novoPaciente.setNome("Maria Oliveira");
        novoPaciente.setDataNascimento(LocalDate.of(1985, 3, 20));
        novoPaciente.setTelefone("987654321");
        novoPaciente.setEndereco("Rua B, 456");
        novoPaciente.setEmail("maria.oliveira@example.com");
        novoPaciente.setStatus(true);
        when(pacienteService.salvar(any(paciente.class))).thenReturn(mockPaciente);
        paciente savedPaciente = pacienteController.salvar(novoPaciente);
        assertNotNull(savedPaciente);
        assertEquals("Carlos Silva", savedPaciente.getNome());
        verify(pacienteService, times(1)).salvar(any(paciente.class));
    }

    @Test
    public void testExcluir() {
        Long pacienteId = 1L;
        doNothing().when(pacienteService).excluir(pacienteId);
        pacienteController.excluir(pacienteId);
        verify(pacienteService, times(1)).excluir(pacienteId);
    }
}
