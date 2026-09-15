# 11. Riesgos y Deuda Técnica

## Descripción General

Esta sección lista los riesgos y la deuda técnica **realmente identificados** en CartFlow API a partir del código, del esquema SQL y de las épicas/HU. No se incluyen riesgos genéricos. Los riesgos se dividen en **actuales** (verificados en el código existente) y **potenciales** (dependen de código aún no implementado).

## Riesgos Actuales

Verificados directamente en el código o la configuración actual.

1. **Esquema sin migraciones versionadas** ✅ verificado
   - **Descripción**: se usa `spring.jpa.hibernate.ddl-auto=update` (`application.properties:10`) en lugar de Flyway/Liquibase.
   - **Impacto**: Medio-Alto — cambios de esquema no reproducibles ni auditables entre entornos.
   - **Mitigación**: adoptar migraciones versionadas y alinear `database/init.sql`.

2. **Sin tests aislados** ✅ verificado
   - **Descripción**: solo existe `BackendApplicationTests` con `@SpringBootTest`, que requiere MariaDB y el `.env` activos; no hay pruebas unitarias con mocks.
   - **Impacto**: Medio — mayor costo de verificación y riesgo de regresiones.
   - **Mitigación**: añadir tests unitarios (por ejemplo con Mockito) para servicios y filtros.

3. **CORS con orígenes hardcodeados** ✅ verificado
   - **Descripción**: `SecurityConfig` fija orígenes `localhost` (`SecurityConfig.java:65-70`).
   - **Impacto**: Medio — el frontend de producción no podrá consumir la API sin cambios de código.
   - **Mitigación**: externalizar los orígenes permitidos por configuración/entorno.

4. **Sin rate limiting** ✅ verificado
   - **Descripción**: la HU-018 exige responder `429` tras más de 3 reenvíos de verificación en 1 hora, pero no existe ninguna limitación de peticiones.
   - **Impacto**: Medio — criterio de HU incumplido y superficie de abuso (fuerza bruta / spam de correos).
   - **Mitigación**: implementar limitación de tasa en el endpoint de reenvío.

5. **Escenarios de seguridad no auditados** ⚠️ Pendiente por definir
   - **Descripción**: no se ha realizado una revisión de seguridad formal del manejo de JWT, cabeceras ni endpoints.
   - **Impacto**: `[Pendiente por definir]`.
   - **Mitigación**: auditoría de seguridad cuando el alcance funcional esté más completo.

## Riesgos Potenciales

Dependen de código futuro. Se revisarán cuando el módulo correspondiente se implemente.

6. **Condiciones de carrera de stock** 🔜 **[Hipotético]**
   - **Descripción**: el descuento de stock y la validación de disponibilidad en carrito/checkout aún no están implementados. Sin control de concurrencia (bloqueo optimista/pesimista o constraint), dos compras simultáneas podrían sobrevender.
   - **Impacto**: Alto (si no se aborda) — inconsistencia de inventario.
   - **Mitigación**: al implementar HU-009/HU-014, usar transacciones con bloqueo o versión en `product.stock`.

7. **Webhook de Stripe sin validación de firma** 🔜 **[Hipotético]**
   - **Descripción**: la HU-014 requiere recibir webhooks de Stripe para confirmar pagos. Si no se valida la firma (`Stripe-Signature`), un atacante podría falsificar eventos.
   - **Impacto**: Crítico (si no se aborda) — pedidos fraudulentos o cambios de estado no autorizados.
   - **Mitigación**: validar la firma usando la clave secreta del webhook (`STRIPE_WEBHOOK_SECRET`) antes de procesar cualquier evento.

8. **Tokens de verificación sin política de limpieza** 🔜 **[Hipotético]**
   - **Descripción**: al registrar usuarios y permitir reenvíos, la tabla `user` acumulará `verification_token` expirados que nunca se limpian.
   - **Impacto**: Bajo-Medio — degradación a largo plazo, ruido en la tabla.
   - **Mitigación**: job programado (`@Scheduled`) que ejecute `UPDATE user SET verification_token = NULL, verification_token_expires_at = NULL WHERE verification_token_expires_at < NOW()`.

## Tabla Resumen de Riesgos

| ID | Título del Riesgo | Categoría | Nivel de Impacto | Sistemas Afectados | Estrategia de Mitigación |
| -- | ----------------- | --------- | ---------------- | ------------------ | ------------------------ |
| R1 | Esquema sin migraciones | Actual | 🟠 Alto | Persistencia | Adoptar Flyway/Liquibase |
| R2 | Sin tests aislados | Actual | 🟡 Medio | Todo el backend | Tests unitarios con mocks |
| R3 | CORS hardcodeado | Actual | 🟡 Medio | common (Security) | Externalizar orígenes |
| R4 | Sin rate limiting | Actual | 🟡 Medio | Auth | Limitación de tasa |
| R5 | Auditoría de seguridad pendiente | Actual | ⚠️ TBD | Todo el backend | Revisión formal de seguridad |
| RP1 | Carrera de stock **[Hipotético]** | Potencial | 🟠 Alto | Carrito, Pedidos, Producto | Bloqueo/versión transaccional |
| RP2 | Webhook Stripe sin validar firma **[Hipotético]** | Potencial | 🔴 Crítico | Pedidos y Pagos | Validar `Stripe-Signature` |
| RP3 | Tokens de verificación sin limpieza **[Hipotético]** | Potencial | 🟡 Medio | Usuario | Job programado de limpieza |

## Deuda Técnica

| ID | Categoría | Descripción | Nivel de Impacto | Acción Sugerida |
| -- | --------- | ----------- | ---------------- | --------------- |
| TD1 | Esquema de BD | `ddl-auto=update` sin migraciones versionadas (`application.properties:10`) | 🟠 Alto | Introducir migraciones (Flyway/Liquibase) |
| TD2 | Pruebas | Sin tests unitarios aislados; los `@SpringBootTest` dependen de MariaDB + `.env` | 🟡 Medio | Añadir pruebas con mocks |
| TD3 | Configuración | `jwt.expiration` es en milisegundos pese al nombre `expiracionMinutos` (`JwtService.java:24,38`) | 🟡 Medio | Renombrar o documentar la unidad; validar el valor |
| TD4 | Configuración | Orígenes CORS hardcodeados a localhost (`SecurityConfig.java:65-70`) | 🟡 Medio | Externalizar por entorno |

## Mapa de Deuda Técnica

```mermaid
graph TD
    Deuda["Deuda Técnica"]

    Esquema["Esquema sin migraciones (TD1)"]
    Tests["Sin tests aislados (TD2)"]
    Config["Configuración: jwt.expiration / CORS (TD3, TD4)"]

    Deuda --> Esquema
    Deuda --> Tests
    Deuda --> Config