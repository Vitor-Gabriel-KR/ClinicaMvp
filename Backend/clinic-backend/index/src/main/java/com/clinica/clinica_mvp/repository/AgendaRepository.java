package com.clinica.clinica_mvp.repository;

import com.clinica.clinica_mvp.model.agenda;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AgendaRepository extends JpaRepository<agenda, Long> {
}
