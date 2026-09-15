# 9. Decisiones Arquitectónicas

## Descripción General

Esta sección documenta las decisiones arquitectónicas críticas que dan forma a CartFlow API. Cada decisión incluye su contexto, las opciones consideradas, la elección final y la justificación. Se indica el estado (`✅ Implementado` / `🔜 Planificado`).

## Principales Decisiones Arquitectónicas

1. **Seguridad stateless con JWT** ✅
   - **Contexto**: la API es consumida por un cliente desacoplado y necesita autenticación sin estado de servidor.
   - **Opciones**: sesiones de servidor con cookies vs. tokens JWT.
   - **Decisión**: usar **JWT** firmado con HMAC-SHA256 (JJWT) y `SessionCreationPolicy.STATELESS`, validado por `JwtAuthenticationFilter`.
   - **Razonamiento**: no requiere almacenar sesiones, escala horizontalmente bien y encaja con un frontend separado. Las authorities se derivan del `UserEntity` para reflejar cambios de rol sin esperar a que expire el token.

2. **Hash de contraseñas con BCrypt** ✅
   - **Contexto**: las contraseñas no deben almacenarse ni poder revertirse.
   - **Opciones**: hashing con BCrypt vs. otros algoritmos.
   - **Decisión**: usar `BCryptPasswordEncoder` expuesto como bean en `ApplicationConfig`.
   - **Razonamiento**: BCrypt es un estándar robusto para hashing de contraseñas, con salt incorporado.

3. **Persistencia con JPA/Hibernate y `ddl-auto=update` sobre MariaDB** ✅
   - **Contexto**: el proyecto está en etapa temprana y el esquema evoluciona rápido.
   - **Opciones**: migraciones versionadas (Flyway/Liquibase) vs. `ddl-auto=update`.
   - **Decisión**: usar `spring.jpa.hibernate.ddl-auto=update` y mantener las entidades alineadas con `database/init.sql`.
   - **Razonamiento**: agiliza el desarrollo temprano. **Introduce deuda técnica**: no hay control de versiones del esquema (ver [Riesgos y Deuda Técnica](11_riesgos_y_deuda_tecnica.md)).

4. **Organización por features** ✅
   - **Contexto**: el sistema crecerá por dominios (auth, producto, carrito, pedidos).
   - **Opciones**: capas técnicas globales vs. paquetes por feature.
   - **Decisión**: empaquetar en `com.cartflow.backend.<feature>` con subcapas internas.
   - **Razonamiento**: mejora la cohesión, aísla cambios y facilita la incorporación de nuevos desarrolladores al proyecto.

5. **DTOs como records + Bean Validation** ✅
   - **Contexto**: se necesita un contrato estable y validación de entrada.
   - **Opciones**: exponer entidades JPA vs. DTOs dedicados.
   - **Decisión**: usar `record` de Java para request/response y validar con Jakarta Validation.
   - **Razonamiento**: evita acoplar la API al modelo de persistencia y centraliza las reglas de validación con mensajes claros.

6. **Manejo centralizado de excepciones** ✅
   - **Contexto**: se requiere un formato de error uniforme.
   - **Opciones**: manejar errores por controlador vs. un `@RestControllerAdvice` global.
   - **Decisión**: `CartflowException` + `GlobalExceptionHandler` que produce `ErrorResponse`.
   - **Razonamiento**: homogeneiza las respuestas de error y evita duplicar lógica de manejo en cada endpoint.

7. **Pago con Stripe Checkout hospedado** 🔜
   - **Contexto**: no se deben manejar datos sensibles de tarjetas.
   - **Opciones**: Stripe Elements incrustado vs. Stripe Checkout hospedado.
   - **Decisión**: crear una sesión de pago en Stripe y redirigir al cliente; confirmar por webhook.
   - **Razonamiento**: minimiza el alcance PCI y simplifica la integración. Requiere validar la autenticidad del webhook (ver riesgos).

8. **Un carrito activo por usuario** 🔜
   - **Contexto**: el carrito debe ser único por usuario y sin productos duplicados.
   - **Opciones**: permitir varios carritos y consolidar vs. uno solo con ítem único.
   - **Decisión**: clave única `cart_user_unique` en `cart` y `cart_product_unique` en (`cart_id`, `product_id`).
   - **Razonamiento**: garantiza consistencia a nivel de base de datos y simplifica la lógica de negocio.

## Tabla Resumen de Decisiones

| ID | Título de la Decisión | Estado | Nivel de Impacto | Sistemas Afectados |
| -- | --------------------- | ------ | ---------------- | ------------------ |
| 1  | Seguridad stateless con JWT | ✅ Activa | Crítico | Auth, common, rutas protegidas |
| 2  | Hash de contraseñas con BCrypt | ✅ Activa | Alto | Auth, Usuario |
| 3  | JPA/Hibernate + `ddl-auto=update` sobre MariaDB | ✅ Activa | Alto | Persistencia |
| 4  | Organización por features | ✅ Activa | Medio | Todo el backend |
| 5  | DTOs como records + Bean Validation | ✅ Activa | Medio | Auth y módulos futuros |
| 6  | Manejo centralizado de excepciones | ✅ Activa | Medio | Todo el backend |
| 7  | Stripe Checkout hospedado | 🔜 Planificada | Alto | Pedidos y Pagos |
| 8  | Un carrito activo por usuario | 🔜 Planificada | Medio | Carrito |

## Motivación

Documentar estas decisiones proporciona una comprensión clara de las elecciones que guían la estructura y funcionalidad de CartFlow API, y sirve de referencia para las implementaciones futuras.

## NOTA SOBRE ADR

Las decisiones documentadas aquí siguen el formato **Architectural Decision Record (ADR)**, una práctica formalizada para registrar decisiones de arquitectura.

Para más información sobre ADR:

- [Michael Nygard – Documenting Architecture Decisions](https://cognitect.com/blog/2011/11/15/documenting-architecture-decisions.html)
- [Repositorio de Plantillas ADR en GitHub](https://github.com/joelparkerhenderson/architecture-decision-record)
