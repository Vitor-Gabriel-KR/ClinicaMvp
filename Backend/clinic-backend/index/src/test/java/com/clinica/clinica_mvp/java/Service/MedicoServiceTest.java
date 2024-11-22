package com.clinica.clinica_mvp.java.Service;

import com.clinica.clinica_mvp.model.medico;
import com.clinica.clinica_mvp.repository.MedicoRepository;
import com.clinica.clinica_mvp.services.MedicoService;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class MedicoServiceTest {

    @InjectMocks
    private MedicoService medicoService;

    @Mock
    private MedicoRepository medicoRepository;

    private medico mockMedico;

    @BeforeEach
    public void setUp() {
        mockMedico = new medico();
        mockMedico.setId(1L);
        mockMedico.setNome("Dr. João Silva");
        mockMedico.setConselho("CRM12345");
        mockMedico.setEstado("SP");
        mockMedico.setDataCriacao(LocalDate.now());
        mockMedico.setStatus(true);
    }

    @Test
    public void testListarTodos() {
        when(medicoRepository.findAll()).thenReturn(List.of(mockMedico));

        List<medico> medicos = medicoService.listarTodos();

        assertNotNull(medicos);
        assertEquals(1, medicos.size());
        assertEquals("Dr. João Silva", medicos.get(0).getNome());
        verify(medicoRepository, times(1)).findAll();
    }

    @Test
    public void testSalvar() {
        when(medicoRepository.save(any(medico.class))).thenReturn(mockMedico);

        medico novoMedico = new medico();
        novoMedico.setNome("Dra. Maria Oliveira");
        novoMedico.setConselho("CRM67890");
        novoMedico.setEstado("RJ");
        novoMedico.setDataCriacao(LocalDate.now());
        novoMedico.setStatus(true);

        medico savedMedico = medicoService.salvar(novoMedico);

        assertNotNull(savedMedico);
        assertEquals("Dr. João Silva", savedMedico.getNome());
        verify(medicoRepository, times(1)).save(any(medico.class));
    }

    @Test
    public void testExcluir() {
        Long medicoId = 1L;

        doNothing().when(medicoRepository).deleteById(medicoId);

        medicoService.excluir(medicoId);

        verify(medicoRepository, times(1)).deleteById(medicoId);
    }
}
