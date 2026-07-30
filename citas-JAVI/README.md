# Sistema de Reservas de Citas — Javi

API REST en Spring Boot para gestionar reservas de citas (asesoramiento, pruebas de anillo,
personalización de piezas, reparaciones...), pensada como alternativa/complemento a la reserva
manual por WhatsApp.

## Requisitos

- Java 17+
- Maven 3.8+
- (Opcional para producción) PostgreSQL

> Nota: este proyecto se generó en un entorno sin acceso a Maven Central, así que no se ha podido
> compilar aquí. El código sigue las convenciones estándar de Spring Boot 3 y debería compilar sin
> problemas en tu máquina. Si algo falla al compilar, dímelo y lo revisamos.

## Cómo arrancarlo

```bash
cd citas-bohemme
mvn spring-boot:run
```

La API arrancará en `http://localhost:8080`. Por defecto usa una base de datos H2 en memoria
(se resetea al reiniciar), ideal para probar rápido. La consola de H2 está en
`http://localhost:8080/h2-console` (JDBC URL: `jdbc:h2:mem:citasdb`, usuario `sa`, sin contraseña).

Al arrancar se cargan automáticamente 4 servicios de ejemplo (ver `data.sql`).

## Endpoints principales

| Método | Endpoint                          | Descripción                              |
|--------|-----------------------------------|-------------------------------------------|
| GET    | `/api/servicios`                  | Lista de servicios activos                |
| POST   | `/api/citas`                      | Crear una solicitud de cita               |
| GET    | `/api/citas?desde=...&hasta=...`  | Citas en un rango (para el calendario)    |
| GET    | `/api/citas/pendientes`           | Citas pendientes de confirmar (admin)     |
| PATCH  | `/api/citas/{id}/confirmar`       | Confirmar una cita (admin)                |
| PATCH  | `/api/citas/{id}/cancelar`        | Cancelar una cita                         |

### Ejemplo: crear una cita

```bash
curl -X POST http://localhost:8080/api/citas \
  -H "Content-Type: application/json" \
  -d '{
    "nombreCliente": "María López",
    "emailCliente": "maria@example.com",
    "telefonoCliente": "600123456",
    "servicioId": 1,
    "fechaHoraInicio": "2026-07-15T11:00:00"
  }'
```

Si alguien más intenta reservar un horario que se solapa con esta cita, la API responde con
`409 Conflict` y un mensaje claro, sin necesidad de comprobarlo a mano.

### Ejemplo: consultar citas de una semana

```bash
curl "http://localhost:8080/api/citas?desde=2026-07-14T00:00:00&hasta=2026-07-20T23:59:59"
```

## Estructura del proyecto

```
src/main/java/com/JAVI/citas/
├── CitasApplication.java       # Punto de entrada
├── model/                      # Entidades JPA (Cliente, Servicio, Cita, EstadoCita)
├── repository/                 # Acceso a datos (incluye la consulta de solapamiento)
├── service/                    # Lógica de negocio + notificaciones por email
├── controller/                 # Endpoints REST
├── dto/                        # Objetos de entrada/salida de la API
└── exception/                  # Excepciones personalizadas + manejador global
```

## Configurar el envío de emails

Edita `src/main/resources/application.properties`:

```properties
spring.mail.username=tu_email@gmail.com
spring.mail.password=tu_password_de_aplicacion
```

Si usas Gmail, necesitas generar una "contraseña de aplicación" desde la configuración de
seguridad de tu cuenta de Google (no tu contraseña normal).

## Pasar a producción con PostgreSQL

En `application.properties`, comenta el bloque de H2 y descomenta el bloque de PostgreSQL,
rellenando tus credenciales reales.

## Próximos pasos sugeridos

1. **Frontend**: una página sencilla con un formulario de reserva y un calendario visual
   (puede ir en el propio WordPress/WooCommerce de Bohemme, consumiendo esta API vía fetch/JS).
2. **Panel de administración**: vista simple para ver citas pendientes y confirmarlas con un clic.
3. **WhatsApp Business API**: sustituir/complementar `NotificacionService` para enviar los
   mensajes de confirmación directamente por WhatsApp en lugar de (o además de) email.
4. **Autenticación**: proteger los endpoints de admin (`/pendientes`, `/confirmar`, `/cancelar`)
   con Spring Security para que no sean públicos.
5. **Horario del negocio**: añadir una entidad de disponibilidad para bloquear días festivos
   y definir el horario de apertura, en vez de aceptar cualquier fecha futura.

## Tests

```bash
mvn test
```

Incluye un test que verifica que no se puede crear una cita si el horario ya está ocupado.
