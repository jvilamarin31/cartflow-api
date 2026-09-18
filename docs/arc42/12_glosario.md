# 12. Glosario

## Descripción General

Este glosario incluye los términos esenciales utilizados en la documentación arquitectónica de CartFlow API, ordenados alfabéticamente. Cada término incluye un enlace a la sección relevante cuando aplica.

---

- **ADR (Architectural Decision Record)**: formato para registrar decisiones de arquitectura con su contexto y justificación.  
  [Ver más](09_decisiones_arquitectonicas.md)

- **arc42**: plantilla estandarizada para documentar y comunicar arquitecturas de software.  
  [Ver más](01_introduccion_y_objetivos.md)

- **BCrypt**: algoritmo de hashing de contraseñas con salt, usado por `PasswordEncoder` en CartFlow.  
  [Ver más](08_conceptos_transversales.md)

- **Bean Validation / Jakarta Validation**: conjunto de anotaciones (`@NotBlank`, `@Email`, `@Size`, `@Pattern`) para validar los DTOs de entrada.  
  [Ver más](04_estrategia_de_solucion.md)

- **C4**: modelo de diagramas de arquitectura en niveles (contexto, contenedores, componentes).  
  [Ver más](05_vista_de_componentes.md)

- **cart**: tabla que representa el carrito de compras de un usuario (uno por usuario).  
  [Ver más](05_vista_de_componentes.md)

- **cart_item**: tabla con los productos y cantidades de un carrito; única por (`cart_id`, `product_id`).  
  [Ver más](05_vista_de_componentes.md)

- **CartflowException**: excepción base de negocio que transporta un `HttpStatus`.  
  [Ver más](08_conceptos_transversales.md)

- **DDL / `ddl-auto`**: generación automática del esquema por Hibernate (`update` en CartFlow).  
  [Ver más](02_restricciones.md)

- **DTO (Data Transfer Object)**: objeto de transporte de datos entre capas; en CartFlow se implementa con `record`.  
  [Ver más](04_estrategia_de_solucion.md)

- **Email Service**: sistema externo encargado del envío de correos transaccionales (Mailtrap en desarrollo, Gmail SMTP en producción).  
  [Ver más](03_contexto_y_alcance.md)

- **Épica**: agrupación de historias de usuario con un objetivo común.  
  [Ver más](../epicas-y-hu.md)

- **GlobalExceptionHandler**: componente `@RestControllerAdvice` que centraliza el manejo de errores.  
  [Ver más](08_conceptos_transversales.md)

- **HU (Historia de Usuario)**: unidad de requisito funcional desde la perspectiva del usuario (HU-001..HU-026).  
  [Ver más](../epicas-y-hu.md)

- **JPA / Hibernate**: estándar y framework de persistencia usados para mapear entidades a MariaDB.  
  [Ver más](04_estrategia_de_solucion.md)

- **JWT (JSON Web Token)**: token firmado usado para autenticación stateless; en CartFlow con HMAC-SHA256 y expiración configurable.  
  [Ver más](08_conceptos_transversales.md)

- **MariaDB**: motor de base de datos relacional único soportado por CartFlow.  
  [Ver más](02_restricciones.md)

- **MoSCoW**: técnica de priorización de requisitos (Must, Should, Could, Won't).  
  [Ver más](../epicas-y-hu.md)

- **order / order_item**: tablas que registran un pedido y sus productos, con precio congelado en `unit_price`.  
  [Ver más](05_vista_de_componentes.md)

- **product**: tabla del catálogo, con `is_active`, `stock` y auditoría (`created_by`/`updated_by`).  
  [Ver más](05_vista_de_componentes.md)

- **SMTP**: protocolo usado para enviar correos transaccionales a través del Email Service.  
  [Ver más](03_contexto_y_alcance.md)

- **Stripe Checkout**: solución hospedada de Stripe usada para procesar pagos sin manejar datos de tarjeta.  
  [Ver más](09_decisiones_arquitectonicas.md)

- **user**: tabla de usuarios, con `email` único, `password` (hash BCrypt), `role` (`ADMIN`/`USER`) y campos de verificación de correo (`email_verified`, `verification_token`, `verification_token_expires_at`).  
  [Ver más](05_vista_de_componentes.md)

- **Verification Token**: token aleatorio de un solo uso, con expiración de 8 horas, generado al registrar un usuario para confirmar la propiedad del correo electrónico.  
  [Ver más](09_decisiones_arquitectonicas.md)

- **Visitante**: actor del sistema no autenticado que explora el catálogo público y el detalle de productos sin requerir cuenta.  
  [Ver más](03_contexto_y_alcance.md)

---

## Motivación

El glosario respalda una comprensión común de los términos entre todas las partes interesadas, reduciendo interpretaciones erróneas y mejorando la comunicación en el proyecto.