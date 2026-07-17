# Tienda on line de deportes usando JAVA

API REST en Spring Boot para gestionar TIENDA DE DEPORTES ONLINE 

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

