package com.JAVI.citas.service;

import com.JAVI.citas.dto.CitaRequest;
import com.JAVI.citas.dto.CitaResponse;
import com.JAVI.citas.exception.HorarioNoDisponibleException;
import com.JAVI.citas.exception.RecursoNoEncontradoException;
import com.JAVI.citas.model.Cita;
import com.JAVI.citas.model.Cliente;
import com.JAVI.citas.model.EstadoCita;
import com.JAVI.citas.model.Servicio;
import com.JAVI.citas.repository.CitaRepository;
import com.JAVI.citas.repository.ClienteRepository;
import com.JAVI.citas.repository.ServicioRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class CitaService {

    private final CitaRepository citaRepository;
    private final ClienteRepository clienteRepository;
    private final ServicioRepository servicioRepository;
    private final NotificacionService notificacionService;

    public CitaService(CitaRepository citaRepository,
                        ClienteRepository clienteRepository,
                        ServicioRepository servicioRepository,
                        NotificacionService notificacionService) {
        this.citaRepository = citaRepository;
        this.clienteRepository = clienteRepository;
        this.servicioRepository = servicioRepository;
        this.notificacionService = notificacionService;
    }

    @Transactional
    public CitaResponse crearCita(CitaRequest request) {
        Servicio servicio = servicioRepository.findById(request.getServicioId())
                .orElseThrow(() -> new RecursoNoEncontradoException(
                        "Servicio no encontrado con id " + request.getServicioId()));

        LocalDateTime inicio = request.getFechaHoraInicio();
        LocalDateTime fin = inicio.plusMinutes(servicio.getDuracionMinutos());

        // Validación clave: que no se solape con otra cita ya reservada
        if (citaRepository.existeSolapamiento(inicio, fin)) {
            throw new HorarioNoDisponibleException(
                    "El horario seleccionado ya no está disponible. Elige otra franja.");
        }

        // Busca el cliente por teléfono, o lo crea si es nuevo
        Cliente cliente = clienteRepository.findByTelefono(request.getTelefonoCliente())
                .orElseGet(() -> {
                    Cliente nuevo = new Cliente(
                            request.getNombreCliente(),
                            request.getEmailCliente(),
                            request.getTelefonoCliente());
                    return clienteRepository.save(nuevo);
                });

        Cita cita = new Cita(cliente, servicio, inicio);
        cita.setNotas(request.getNotas());
        cita = citaRepository.save(cita);

        notificacionService.enviarConfirmacionPendiente(cita);

        return CitaResponse.desde(cita);
    }

    @Transactional
    public CitaResponse confirmarCita(Long citaId) {
        Cita cita = obtenerOLanzar(citaId);
        cita.setEstado(EstadoCita.CONFIRMADA);
        Cita guardada = citaRepository.save(cita);
        notificacionService.enviarConfirmacionDefinitiva(guardada);
        return CitaResponse.desde(guardada);
    }

    @Transactional
    public CitaResponse cancelarCita(Long citaId) {
        Cita cita = obtenerOLanzar(citaId);
        cita.setEstado(EstadoCita.CANCELADA);
        Cita guardada = citaRepository.save(cita);
        notificacionService.enviarCancelacion(guardada);
        return CitaResponse.desde(guardada);
    }

    public List<CitaResponse> obtenerCitasEntre(LocalDateTime desde, LocalDateTime hasta) {
        return citaRepository.findByFechaHoraInicioBetween(desde, hasta)
                .stream()
                .map(CitaResponse::desde)
                .collect(Collectors.toList());
    }

    public List<CitaResponse> obtenerCitasPendientes() {
        return citaRepository.findByEstado(EstadoCita.PENDIENTE)
                .stream()
                .map(CitaResponse::desde)
                .collect(Collectors.toList());
    }

    private Cita obtenerOLanzar(Long citaId) {
        return citaRepository.findById(citaId)
                .orElseThrow(() -> new RecursoNoEncontradoException("Cita no encontrada con id " + citaId));
    }
}
