# 1. Introducción y Objetivos

## Propósito

CartFlow API es el backend de una tienda en línea. Expone una API REST que cubre autenticación de usuarios, gestión de productos, carrito de compras, procesamiento de pagos mediante Stripe y gestión de tickets de soporte. El alcance funcional está definido por 5 épicas y las historias de usuario HU-001 a HU-026 en [`epicas-y-hu.md`](../epicas-y-hu.md).

## Principales Partes Interesadas

| Parte Interesada | Rol | Expectativas |
|------------------|-----|--------------|
| Visitante | Usuario no autenticado | Explorar el catálogo y ver el detalle de productos sin crear cuenta |
| Cliente (USER) | Usuario final autenticado | Registrarse, iniciar sesión, gestionar su carrito y pagar de forma segura |
| Administrador (ADMIN) | Gestor del catálogo | Crear, editar, listar y desactivar productos; consultar pedidos |
| Sistema (Backend) | Procesos internos | Recibir webhooks de Stripe, crear pedidos, actualizar stock y vaciar el carrito |
| Stripe | Pasarela de pago externa | Procesar pagos y notificar el resultado vía webhook |
| Servicio de Email | Sistema externo (Mailtrap/Gmail SMTP) | Enviar correos transaccionales de verificación de cuenta |
| Equipo de desarrollo | Mantenedores | Convenciones claras, código mantenible y trazabilidad de las decisiones |

## Principales Objetivos de Calidad

1. **Seguridad**: garantizar que solo usuarios autenticados y verificados accedan a las operaciones protegidas, y que los datos sensibles (contraseñas, tarjetas) no queden expuestos; los pagos se delegan a Stripe.
2. **Mantenibilidad**: organización por features, convenciones de nombres y documentación arquitectónica que faciliten la evolución del backend.
3. **Integridad de datos**: consistencia entre el stock de productos, el carrito y los pedidos tras una compra, evitando sobreventas.
4. **Usabilidad**: permitir que un visitante explore el catálogo sin fricción y que el cliente complete la compra con retroalimentación clara ante errores.

Estos objetivos guían las decisiones de diseño detalladas en las secciones siguientes. La definición de métricas concretas para estos objetivos está pendiente (ver [Requisitos de Calidad](10_requisitos_de_calidad.md)).

## Alcance Funcional (Épicas)

| Épica | Descripción | Estado |
|-------|-------------|--------|
| Épica 1: Autenticación y Gestión de Usuarios | Registro, login, perfil, verificación y reenvío de correo (HU-001..003, HU-017..018) | 🔜 Planificado (registro parcialmente ✅) |
| Épica 2: Gestión de Productos | CRUD de productos, catálogo público y detalle (HU-004..008, HU-019) | 🔜 Planificado |
| Épica 3: Gestión del Carrito de Compras | Agregar, actualizar, eliminar ítems y ver total (HU-009..012) | 🔜 Planificado |
| Épica 4: Procesamiento de Pagos con Stripe | Iniciar pago, webhook, errores, historial de pedidos y correo de confirmación (HU-013..016, HU-020) | 🔜 Planificado |
| Épica 5: Gestión de Tickets de Soporte | Creación, listado, conversación cliente/admin, asignación y estados de tickets (HU-021..026) | 🔜 Planificado |
