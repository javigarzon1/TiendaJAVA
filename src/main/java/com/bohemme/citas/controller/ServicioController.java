package com.bohemme.citas.controller;

import com.bohemme.citas.model.Servicio;
import com.bohemme.citas.repository.ServicioRepository;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/servicios")
@CrossOrigin(origins = "*")
public class ServicioController {

    private final ServicioRepository servicioRepository;

    public ServicioController(ServicioRepository servicioRepository) {
        this.servicioRepository = servicioRepository;
    }

    // Lista de servicios activos, para mostrar en el formulario de reserva
    @GetMapping
    public List<Servicio> obtenerActivos() {
        return servicioRepository.findByActivoTrue();
    }

    // Alta de un nuevo servicio (uso interno/admin)
    @PostMapping
    public ResponseEntity<Servicio> crear(@Valid @RequestBody Servicio servicio) {
        Servicio guardado = servicioRepository.save(servicio);
        return ResponseEntity.status(HttpStatus.CREATED).body(guardado);
    }
}
