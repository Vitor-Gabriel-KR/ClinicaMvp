package com.clinica.clinica_mvp.model;

import jakarta.persistence.*;

import java.time.LocalTime;

@Entity
public class consulta {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "agenda_id", nullable = false)
    private agenda agenda;

    @ManyToOne
    @JoinColumn(name = "paciente_id", nullable = false)
    private paciente paciente;

    private String dia;

    private LocalTime  dataHora;
    private String status;

    // Getters / Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public agenda getAgenda() {
        return agenda;
    }

    public void setAgenda(agenda agenda) {
        this.agenda = agenda;
    }

    public paciente getPaciente() {
        return paciente;
    }

    public void setPaciente(paciente paciente) {
        this.paciente = paciente;
    }

    public LocalTime  getDataHora() {
        return dataHora;
    }

    public void setDataHora(LocalTime  dataHora) {
        this.dataHora = dataHora;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
    public String getDia() {
        return dia;
    }

    public void setDia(String dia) {
        this.dia = dia;
    }
}
