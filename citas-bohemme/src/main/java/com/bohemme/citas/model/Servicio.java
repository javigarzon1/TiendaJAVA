package com.bohemme.citas.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Entity
@Table(name = "servicios")
@Getter
@Setter
@NoArgsConstructor
public class Servicio {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "El nombre del servicio es obligatorio")
    private String nombre; // Ej: "Asesoramiento personalizado", "Prueba de anillo"

    private String descripcion;

    @Positive(message = "La duración debe ser positiva")
    private Integer duracionMinutos; // Ej: 30, 45, 60

    private BigDecimal precio;

    private boolean activo = true;

    public Servicio(String nombre, String descripcion, Integer duracionMinutos, BigDecimal precio) {
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.duracionMinutos = duracionMinutos;
        this.precio = precio;
    }
}
