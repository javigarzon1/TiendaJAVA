package com.bohemme.citas.service;

import com.bohemme.citas.model.Cita;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.time.format.DateTimeFormatter;

/**
 * Centraliza el envío de notificaciones sobre citas.
 * Hoy usa email (JavaMailSender). Mañana se puede añadir un método
 * enviarWhatsApp(...) usando la API de WhatsApp Business sin tocar el resto del código.
 */
@Service
public class NotificacionService {

    private static final Logger log = LoggerFactory.getLogger(NotificacionService.class);
    private static final DateTimeFormatter FORMATO = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");

    private final JavaMailSender mailSender;

    public NotificacionService(JavaMailSender mailSender) {
        this.mailSender = mailSender;
    }

    public void enviarConfirmacionPendiente(Cita cita) {
        String asunto = "Hemos recibido tu solicitud de cita - Bohemme";
        String cuerpo = String.format(
                "Hola %s,%n%nHemos recibido tu solicitud para \"%s\" el %s.%n" +
                "Te confirmaremos en breve por este mismo medio.%n%nUn saludo,%nBohemme",
                cita.getCliente().getNombre(),
                cita.getServicio().getNombre(),
                cita.getFechaHoraInicio().format(FORMATO));

        enviar(cita.getCliente().getEmail(), asunto, cuerpo);
    }

    public void enviarConfirmacionDefinitiva(Cita cita) {
        String asunto = "¡Tu cita está confirmada! - Bohemme";
        String cuerpo = String.format(
                "Hola %s,%n%nTu cita para \"%s\" el %s ha sido confirmada.%n" +
                "Te esperamos.%n%nUn saludo,%nBohemme",
                cita.getCliente().getNombre(),
                cita.getServicio().getNombre(),
                cita.getFechaHoraInicio().format(FORMATO));

        enviar(cita.getCliente().getEmail(), asunto, cuerpo);
    }

    public void enviarCancelacion(Cita cita) {
        String asunto = "Tu cita ha sido cancelada - Bohemme";
        String cuerpo = String.format(
                "Hola %s,%n%nTu cita para \"%s\" el %s ha sido cancelada.%n" +
                "Si quieres, puedes reservar otra fecha desde nuestra web.%n%nUn saludo,%nBohemme",
                cita.getCliente().getNombre(),
                cita.getServicio().getNombre(),
                cita.getFechaHoraInicio().format(FORMATO));

        enviar(cita.getCliente().getEmail(), asunto, cuerpo);
    }

    private void enviar(String destinatario, String asunto, String cuerpo) {
        if (destinatario == null || destinatario.isBlank()) {
            log.warn("No se pudo enviar notificación: el cliente no tiene email registrado");
            return;
        }
        try {
            SimpleMailMessage mensaje = new SimpleMailMessage();
            mensaje.setTo(destinatario);
            mensaje.setSubject(asunto);
            mensaje.setText(cuerpo);
            mailSender.send(mensaje);
        } catch (Exception e) {
            // No queremos que un fallo de email tumbe la reserva de la cita
            log.error("Error enviando email a {}: {}", destinatario, e.getMessage());
        }
    }
}
