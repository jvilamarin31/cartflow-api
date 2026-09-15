# Arquitectura de CartFlow API - Resumen de arc42

## Introducción

Esta documentación describe la arquitectura de **CartFlow API**, un backend REST para una tienda en línea construido con **Java 21** y **Spring Boot 4.1.1**, aplicando el modelo **arc42**. El modelo arc42 estructura la documentación arquitectónica en secciones clave, facilitando la comunicación de las decisiones de diseño, la estructura y los requisitos del sistema.

CartFlow API cubre autenticación de usuarios (con verificación de correo electrónico), catálogo de productos, carrito de compras y procesamiento de pagos mediante Stripe. Su arquitectura distingue tres roles de usuario: **Visitante** (no autenticado), **Cliente (USER)** y **Administrador (ADMIN)**.

## Propósito de Esta Documentación

A través de este documento se busca:

- Documentar de forma estructurada la arquitectura real y planificada de CartFlow API.
- Servir como referencia para el equipo de desarrollo y para la incorporación de nuevos colaboradores.
- Distinguir claramente entre lo que ya está implementado y lo que está definido pero aún no se ha construido.

> **Convención de estado:** cada bloque de esta documentación se marca como:
> - **✅ Implementado**: ya existe en el código del backend.
> - **🔜 Planificado**: definido en los C4 o en las épicas/HU (`docs/epicas-y-hu.md`), pendiente de implementación.
> - **⚠️ Pendiente por definir**: todavía no hay una decisión tomada; se deja constancia de qué falta documentar.

## Contenidos

1. [**Introducción y Objetivos**](01_introduccion_y_objetivos.md) - Propósito, partes interesadas y objetivos de calidad.
2. [**Restricciones**](02_restricciones.md) - Restricciones externas e internas que moldean la arquitectura.
3. [**Contexto y Alcance**](03_contexto_y_alcance.md) - Límites del sistema e interacciones con sistemas externos (Stripe, Email Service).
4. [**Estrategia de Solución**](04_estrategia_de_solucion.md) - Estrategias centrales y decisiones de alto nivel.
5. [**Vista de Componentes**](05_vista_de_componentes.md) - Bloques de construcción y su organización.
6. [**Vista de Tiempo de Ejecución**](06_vista_de_tiempo_de_ejecucion.md) - Escenarios clave e interacciones entre componentes.
7. [**Vista de Despliegue**](07_vista_de_despliegue.md) - Infraestructura y entornos (pendiente por definir).
8. [**Conceptos Transversales**](08_conceptos_transversales.md) - Conceptos técnicos aplicables a todo el sistema.
9. [**Decisiones Arquitectónicas**](09_decisiones_arquitectonicas.md) - Decisiones clave y sus motivaciones.
10. [**Requisitos de Calidad**](10_requisitos_de_calidad.md) - Objetivos de calidad y escenarios.
11. [**Riesgos y Deuda Técnica**](11_riesgos_y_deuda_tecnica.md) - Riesgos y deuda técnica identificados.
12. [**Glosario**](12_glosario.md) - Definiciones de términos importantes.

## Recursos

- **Diagramas C4**: diagramas de contexto, contenedores y componentes en [`docs/diagrams/`](../diagrams).
- **Épicas e Historias de Usuario**: [`docs/epicas-y-hu.md`](../epicas-y-hu.md).
- **Esquema de base de datos**: [`database/init.sql`](../../database/init.sql).

## Sobre arc42

arc42 es una plantilla estandarizada para la documentación de arquitecturas de software. Desarrollada por arquitectos de software para una documentación práctica y estructurada, arc42 es ampliamente utilizada en sistemas complejos. Esta documentación adapta sus 12 secciones al caso de CartFlow API.