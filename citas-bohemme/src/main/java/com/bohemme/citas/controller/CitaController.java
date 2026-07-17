package com.bohemme.citas.controller;

import com.bohemme.citas.dto.CitaRequest;
import com.bohemme.citas.dto.CitaResponse;
import com.bohemme.citas.service.CitaService;
import jakarta.validation.Valid;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/citas")
@CrossOrigin(origins = "*") // Ajustar en producción al dominio real de bohemme.com
public class CitaController {

    private final CitaService citaService;

    public CitaController(CitaService citaService) {
        this.citaService = citaService;
    }

    // Crear una nueva solicitud de cita (uso público, desde la web)
    @PostMapping
    public ResponseEntity<CitaResponse> crearCita(@Valid @RequestBody CitaRequest request) {
        CitaResponse creada = citaService.crearCita(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(creada);
    }

    // Confirmar una cita (uso interno, panel de admin)
    @PatchMapping("/{id}/confirmar")
    public ResponseEntity<CitaResponse> confirmarCita(@PathVariable Long id) {
        return ResponseEntity.ok(citaService.confirmarCita(id));
    }

    // Cancelar una cita (cliente o admin)
    @PatchMapping("/{id}/cancelar")
    public ResponseEntity<CitaResponse> cancelarCita(@PathVariable Long id) {
        return ResponseEntity.ok(citaService.cancelarCita(id));
    }

    // Consultar citas en un rango de fechas (para pintar el calendario)
    @GetMapping
    public ResponseEntity<List<CitaResponse>> obtenerCitas(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime desde,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime hasta) {
        return ResponseEntity.ok(citaService.obtenerCitasEntre(desde, hasta));
    }

    // Citas pendientes de confirmar (para el panel de admin)
    @GetMapping("/pendientes")
    public ResponseEntity<List<CitaResponse>> obtenerPendientes() {
        return ResponseEntity.ok(citaService.obtenerCitasPendientes());
    }
}
