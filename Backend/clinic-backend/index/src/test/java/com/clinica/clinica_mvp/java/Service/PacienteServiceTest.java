package com.clinica.clinica_mvp.java.Service;

import com.clinica.clinica_mvp.model.paciente;
import com.clinica.clinica_mvp.repository.PacienteRepository;
import com.clinica.clinica_mvp.services.PacienteService;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class PacienteServiceTest {

    @InjectMocks
    private PacienteService pacienteService;

    @Mock
    private PacienteRepository pacienteRepository;

    private paciente mockPaciente;

    @BeforeEach
    public void setUp() {
        mockPaciente = new paciente();
        mockPaciente.setId(1L);
        mockPaciente.setNome("Maria Silva");
        mockPaciente.setDataNascimento(LocalDate.of(1985, 5, 20));
        mockPaciente.setTelefone("123456789");
        mockPaciente.setEndereco("Rua Exemplo, 123");
        mockPaciente.setEmail("maria.silva@example.com");
        mockPaciente.setStatus(true);
    }

    @Test
    public void testListarTodos() {
        when(pacienteRepository.findAll()).thenReturn(List.of(mockPaciente));

        List<paciente> pacientes = pacienteService.listarTodos();

        assertNotNull(pacientes);
        assertEquals(1, pacientes.size());
        assertEquals("Maria Silva", pacientes.get(0).getNome());
        verify(pacienteRepository, times(1)).findAll();
    }

    @Test
    public void testSalvar() {
        when(pacienteRepository.save(any(paciente.class))).thenReturn(mockPaciente);

        paciente novoPaciente = new paciente();
        novoPaciente.setNome("João Oliveira");
        novoPaciente.setDataNascimento(LocalDate.of(1990, 10, 15));
        novoPaciente.setTelefone("987654321");
        novoPaciente.setEndereco("Rua Nova, 456");
        novoPaciente.setEmail("joao.oliveira@example.com");
        novoPaciente.setStatus(true);

        paciente savedPaciente = pacienteService.salvar(novoPaciente);

        assertNotNull(savedPaciente);
        assertEquals("Maria Silva", savedPaciente.getNome());
        verify(pacienteRepository, times(1)).save(any(paciente.class));
    }

    @Test
    public void testExcluir() {
        Long pacienteId = 1L;

        doNothing().when(pacienteRepository).deleteById(pacienteId);

        pacienteService.excluir(pacienteId);

        verify(pacienteRepository, times(1)).deleteById(pacienteId);
    }
}
