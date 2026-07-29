package com.bohemme.citas;

import com.bohemme.citas.dto.CitaRequest;
import com.bohemme.citas.exception.HorarioNoDisponibleException;
import com.bohemme.citas.model.Servicio;
import com.bohemme.citas.repository.CitaRepository;
import com.bohemme.citas.repository.ClienteRepository;
import com.bohemme.citas.repository.ServicioRepository;
import com.bohemme.citas.service.CitaService;
import com.bohemme.citas.service.NotificacionService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class CitaServiceTest {

    private CitaRepository citaRepository;
    private ClienteRepository clienteRepository;
    private ServicioRepository servicioRepository;
    private NotificacionService notificacionService;
    private CitaService citaService;

    @BeforeEach
    void setUp() {
        citaRepository = mock(CitaRepository.class);
        clienteRepository = mock(ClienteRepository.class);
        servicioRepository = mock(ServicioRepository.class);
        notificacionService = mock(NotificacionService.class);
        citaService = new CitaService(citaRepository, clienteRepository, servicioRepository, notificacionService);
    }

    @Test
    void debeLanzarExcepcionSiElHorarioYaEstaOcupado() {
        Servicio servicio = new Servicio("Asesoramiento", "desc", 30, BigDecimal.ZERO);
        servicio.setId(1L);

        when(servicioRepository.findById(1L)).thenReturn(Optional.of(servicio));
        when(citaRepository.existeSolapamiento(any(), any())).thenReturn(true);

        CitaRequest request = new CitaRequest();
        request.setServicioId(1L);
        request.setNombreCliente("Ana");
        request.setTelefonoCliente("600000000");
        request.setFechaHoraInicio(LocalDateTime.now().plusDays(1));

        assertThrows(HorarioNoDisponibleException.class, () -> citaService.crearCita(request));

        // Nunca debería llegar a guardar la cita si el horario está ocupado
        verify(citaRepository, never()).save(any());
    }
}
