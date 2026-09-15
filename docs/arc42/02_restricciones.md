# 2. Restricciones

## Descripción General

Esta sección describe las restricciones que afectan el diseño y desarrollo de la arquitectura de CartFlow API. Las restricciones incluyen factores externos, como el stack tecnológico exigido y los sistemas de terceros, así como factores internos derivados de las convenciones y del estado actual del proyecto.

## Restricciones Externas

- **Stack tecnológico**: el backend debe construirse con **Java 21** y **Spring Boot 4.1.1**. Se usan los nombres de starter de Boot 4 (`spring-boot-starter-webmvc`, `spring-boot-starter-webmvc-test`) y no se migran a los nombres `-web`.
- **Base de datos**: solo se admite **MariaDB** (driver `org.mariadb.jdbc.Driver`, dialecto `MariaDBDialect`). No hay soporte multi-motor ni bases NoSQL.
- **Pasarela de pago**: el procesamiento de pagos se delega por completo a **Stripe**. No se manejan datos sensibles de tarjetas en el servidor.
- **Correo transaccional**: el envío de correos (verificación de cuenta) se delega a un servicio SMTP externo (**Mailtrap / Gmail**).
- **Autenticación**: el estándar es **JWT** firmado con HMAC-SHA256 mediante la librería **JJWT** (0.12.6).
- **Alcance académico**: el proyecto se limita a las 4 épicas y las HU-001..HU-019 definidas en [`epicas-y-hu.md`](../epicas-y-hu.md).

## Restricciones Internas

- **Base de datos única**: toda la persistencia vive en la base de datos `cartflow`; el esquema de referencia es [`database/init.sql`](../../database/init.sql).
- **Gestión del esquema**: `spring.jpa.hibernate.ddl-auto=update`, por lo que Hibernate altera el esquema automáticamente. Las entidades deben mantenerse alineadas con `init.sql`. Aún no se usan migraciones versionadas (Flyway/Liquibase).
- **Arquitectura stateless**: no se usan sesiones de servidor; `SessionCreationPolicy.STATELESS`.
- **Convenciones de código**: paquetes por feature en `com.cartflow.backend.<feature>`; repositorios con prefijo `I`; entidades con sufijo `Entity`. Lombok está habilitado en el compilador.
- **Configuración y secretos**: la configuración sensible (`DB_URL`, `DB_USERNAME`, `DB_PASSWORD`, `jwt.key`, `jwt.expiration`) proviene de `backend/.env`, cargado con `me.paulschwarz:springboot4-dotenv`. El archivo `.env` está ignorado por git y nunca debe commitearse.
- **Semántica de la expiración JWT**: `jwt.expiration` se suma a `currentTimeMillis()` como **milisegundos**, pese al nombre de la variable `expiracionMinutos` (`JwtService.java:24,38`). Una hora equivale a `3600000`.
- **Limitación del script de esquema**: `database/init.sql` no es ejecutable tal cual (tiene `CREATE DATABASE` duplicado, no incluye `USE cartflow` y define claves foráneas que referencian tablas antes de crearlas). Debe corregirse o aplicarse manualmente.

Estas restricciones definen el marco dentro del cual se toman todas las decisiones arquitectónicas de CartFlow API.
