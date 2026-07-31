package com.JAVI.citas.model;

public enum EstadoCita {
    PENDIENTE,      // Recién creada, esperando confirmación del admin
    CONFIRMADA,     // Aceptada por el negocio
    CANCELADA,      // Cancelada por el cliente o el negocio
    COMPLETADA      // La cita ya tuvo lugar
}
