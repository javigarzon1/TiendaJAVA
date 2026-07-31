package com.JAVI.citas.dto;

import com.JAVI.citas.model.Cita;
import com.JAVI.citas.model.EstadoCita;

import java.time.LocalDateTime;

public class CitaResponse {

    private Long id;
    private String nombreCliente;
    private String telefonoCliente;
    private String nombreServicio;
    private LocalDateTime fechaHoraInicio;
    private LocalDateTime fechaHoraFin;
    private EstadoCita estado;

    public static CitaResponse desde(Cita cita) {
        CitaResponse r = new CitaResponse();
        r.id = cita.getId();
        r.nombreCliente = cita.getCliente().getNombre();
        r.telefonoCliente = cita.getCliente().getTelefono();
        r.nombreServicio = cita.getServicio().getNombre();
        r.fechaHoraInicio = cita.getFechaHoraInicio();
        r.fechaHoraFin = cita.getFechaHoraFin();
        r.estado = cita.getEstado();
        return r;
    }

    // Getters
    public Long getId() { return id; }
    public String getNombreCliente() { return nombreCliente; }
    public String getTelefonoCliente() { return telefonoCliente; }
    public String getNombreServicio() { return nombreServicio; }
    public LocalDateTime getFechaHoraInicio() { return fechaHoraInicio; }
    public LocalDateTime getFechaHoraFin() { return fechaHoraFin; }
    public EstadoCita getEstado() { return estado; }
}
