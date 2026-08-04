package com.JAVI.citas.repository;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.JAVI.citas.model.Cita;
import com.JAVI.citas.model.EstadoCita;

public interface CitaRepository extends JpaRepository<Cita, Long> {

    // Todas las citas dentro de un rango (para pintar el calendario)
    List<Cita> findByFechaHoraInicioBetween(LocalDateTime desde, LocalDateTime hasta);

    // Citas de un cliente concreto
    List<Cita> findByClienteId(Long clienteId);

    /**
     * Comprueba si existe alguna cita que se solape con el rango [inicio, fin).
     * Dos rangos se solapan si: inicioExistente < finNuevo AND finExistente > inicioNuevo.
     * Solo se consideran citas PENDIENTE o CONFIRMADA (las CANCELADAS no bloquean el hueco).
     */
    @Query("""
        SELECT COUNT(c) > 0 FROM Cita c
        WHERE c.estado IN (com.JAVI.citas.model.EstadoCita.PENDIENTE, com.JAVI.citas.model.EstadoCita.CONFIRMADA)
        AND c.fechaHoraInicio < :fin
        AND c.fechaHoraFin > :inicio
        """)
    boolean existeSolapamiento(@Param("inicio") LocalDateTime inicio, @Param("fin") LocalDateTime fin);

    List<Cita> findByEstado(EstadoCita estado);
}
