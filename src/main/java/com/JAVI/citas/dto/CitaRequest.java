package com.JAVI.citas.dto;

import java.time.LocalDateTime;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class CitaRequest {

    @NotBlank(message = "El nombre es obligatorio")
    private String nombreCliente;

    @Email(message = "Email no válido")
    private String emailCliente;

    @NotBlank(message = "El teléfono es obligatorio")
    private String telefonoCliente;

    @NotNull(message = "Debes indicar el servicio")
    private Long servicioId;

    @NotNull(message = "Debes indicar fecha y hora")
    @Future(message = "La fecha debe ser futura")
    private LocalDateTime fechaHoraInicio;

    private String notas;

    // Getters y setters
    public String getNombreCliente() { return nombreCliente; }
    public void setNombreCliente(String nombreCliente) { this.nombreCliente = nombreCliente; }

    public String getEmailCliente() { return emailCliente; }
    public void setEmailCliente(String emailCliente) { this.emailCliente = emailCliente; }

    public String getTelefonoCliente() { return telefonoCliente; }
    public void setTelefonoCliente(String telefonoCliente) { this.telefonoCliente = telefonoCliente; }

    public Long getServicioId() { return servicioId; }
    public void setServicioId(Long servicioId) { this.servicioId = servicioId; }

    public LocalDateTime getFechaHoraInicio() { return fechaHoraInicio; }
    public void setFechaHoraInicio(LocalDateTime fechaHoraInicio) { this.fechaHoraInicio = fechaHoraInicio; }

    public String getNotas() { return notas; }
    public void setNotas(String notas) { this.notas = notas; }
}
