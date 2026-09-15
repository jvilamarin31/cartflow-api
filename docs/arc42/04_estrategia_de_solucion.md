# 4. Estrategia de Solución

## Descripción General

Esta sección describe las estrategias y decisiones fundamentales que dan forma a la arquitectura de CartFlow API. El detalle de cada decisión (contexto, alternativas consideradas y justificación) se documenta en la [Sección 9: Decisiones Arquitectónicas](./09_decisiones_arquitectonicas.md).

## Estrategias Principales

1. **Arquitectura por dominios (Package by Feature)** ✅
   El backend se organiza por feature (`auth`, `user`, `product`, `cart`, `order`, `notification`, `common`), cada uno con sus capas internas. Maximiza la cohesión y minimiza el acoplamiento entre dominios.

2. **API REST stateless con JWT** ✅
   Autenticación con tokens JWT firmados con HMAC-SHA256, sin sesión de servidor. Autorización por rol (`USER`, `ADMIN`).

3. **Persistencia con Spring Data JPA sobre MariaDB** ✅
   Entidades JPA mapeadas a un esquema relacional único. Repositorios por feature.

4. **Contrato de API desacoplado con DTOs** ✅
   Peticiones y respuestas con `record` de Java + Bean Validation. Las entidades JPA nunca se exponen.

5. **Manejo centralizado de errores** ✅
   Excepciones de negocio heredan de `CartflowException`. Respuestas HTTP uniformes vía `@RestControllerAdvice`.

6. **Integraciones externas desacopladas** 🔜
   Stripe (pagos) y Email Service (correos) se integran mediante servicios dedicados y configuración por variables de entorno.

## Motivación

Estas estrategias fijan los lineamientos de alto nivel sobre tecnología, patrones de diseño y seguridad. El razonamiento detrás de cada elección, las alternativas consideradas y los tradeoffs se documentan en la [Sección 9](./09_decisiones_arquitectonicas.md). La [Sección 5](./05_vista_de_componentes.md) descompone estas estrategias en contenedores y componentes concretos.
