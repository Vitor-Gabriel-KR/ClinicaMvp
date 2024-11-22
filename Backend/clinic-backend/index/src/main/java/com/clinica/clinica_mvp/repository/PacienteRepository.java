package com.clinica.clinica_mvp.repository;

import com.clinica.clinica_mvp.model.paciente;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PacienteRepository extends JpaRepository<paciente, Long> {
}
