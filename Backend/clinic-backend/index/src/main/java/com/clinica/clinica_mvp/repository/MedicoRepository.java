package com.clinica.clinica_mvp.repository;

import com.clinica.clinica_mvp.model.medico;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MedicoRepository extends JpaRepository<medico, Long> {
}
