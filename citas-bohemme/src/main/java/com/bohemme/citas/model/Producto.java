package com.bohemme.citas.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "productos")
@Getter
@Setter
@NoArgsConstructor
public class Producto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "El nombre es obligatorio")
    private String nombre;

    @NotBlank(message = "El deporte es obligatorio")
    private String deporte; // Running, Fútbol, Gimnasio, Ciclismo, Natación...

    @NotBlank(message = "El género es obligatorio")
    private String genero; // Hombre, Mujer, Niños, Unisex

    @NotBlank(message = "La categoría de prenda es obligatoria")
    private String prenda; // Camisetas, Pantalones, Calzado, Chaquetas, Accesorios

    @Positive(message = "El precio debe ser positivo")
    private BigDecimal precio;

    @ElementCollection
    @CollectionTable(name = "producto_tallas", joinColumns = @JoinColumn(name = "producto_id"))
    @Column(name = "talla")
    private List<String> tallas = new ArrayList<>();

    private Integer stock = 0;

    private boolean activo = true;
}
