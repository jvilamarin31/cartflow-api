# CartFlow API — Épicas e Historias de Usuario

> Documento sincronizado desde el tablero de GitHub Projects. Las épicas y HU se editan en el tablero; este archivo es su espejo en el repositorio.

## Índice

### Épicas
- [Épica 1: Autenticación y Gestión de Usuarios](#épica-1-autenticación-y-gestión-de-usuarios)
- [Épica 2: Gestión de Productos](#épica-2-gestión-de-productos)
- [Épica 3: Gestión del Carrito de Compras](#épica-3-gestión-del-carrito-de-compras)
- [Épica 4: Procesamiento de Pagos con Stripe](#épica-4-procesamiento-de-pagos-con-stripe)
- [Épica 5: Gestión de Tickets de Soporte](#épica-5-gestión-de-tickets-de-soporte)

### Historias de Usuario
- [HU-001: Registro de usuario](#hu-001-registro-de-usuario)
- [HU-002: Inicio de sesión](#hu-002-inicio-de-sesión)
- [HU-003: Obtener perfil propio](#hu-003-obtener-perfil-propio)
- [HU-004: Crear producto](#hu-004-crear-producto)
- [HU-005: Editar producto](#hu-005-editar-producto)
- [HU-006: Listar productos activos](#hu-006-listar-productos-activos)
- [HU-007: Desactivar producto](#hu-007-desactivar-producto)
- [HU-008: Listar productos para gestión](#hu-008-listar-productos-para-gestión)
- [HU-009: Agregar producto al carrito](#hu-009-agregar-producto-al-carrito)
- [HU-010: Actualizar cantidad de un producto en el carrito](#hu-010-actualizar-cantidad-de-un-producto-en-el-carrito)
- [HU-011: Eliminar producto del carrito](#hu-011-eliminar-producto-del-carrito)
- [HU-012: Ver detalle y total del carrito](#hu-012-ver-detalle-y-total-del-carrito)
- [HU-013: Iniciar pago y redirigir a Stripe](#hu-013-iniciar-pago-y-redirigir-a-stripe)
- [HU-014: Procesar Webhook de pago exitoso y crear pedido](#hu-014-procesar-webhook-de-pago-exitoso-y-crear-pedido)
- [HU-015: Manejar errores y pagos fallidos o cancelados](#hu-015-manejar-errores-y-pagos-fallidos-o-cancelados)
- [HU-016: Visualizar historial de pedidos](#hu-016-visualizar-historial-de-pedidos)
- [HU-017: Verificar cuenta mediante enlace de correo](#hu-017-verificar-cuenta-mediante-enlace-de-correo)
- [HU-018: Reenviar correo de verificación](#hu-018-reenviar-correo-de-verificación)
- [HU-019: Ver detalle de producto](#hu-019-ver-detalle-de-producto)
- [HU-020: Enviar email de confirmación de compra](#hu-020-enviar-email-de-confirmación-de-compra)
- [HU-021: Crear ticket de soporte](#hu-021-crear-ticket-de-soporte)
- [HU-022: Ver mis tickets y su detalle](#hu-022-ver-mis-tickets-y-su-detalle)
- [HU-023: Responder en un ticket (Cliente)](#hu-023-responder-en-un-ticket-cliente)
- [HU-024: Listar todos los tickets](#hu-024-listar-todos-los-tickets)
- [HU-025: Tomar y cambiar estado de un ticket](#hu-025-tomar-y-cambiar-estado-de-un-ticket)
- [HU-026: Responder en un ticket (Admin)](#hu-026-responder-en-un-ticket-admin)

---

## Épicas

### Épica 1: Autenticación y Gestión de Usuarios

**Usuario(s):** Usuario final (Cliente y Administrador)

**Descripción**
Como usuario, quiero registrarme en la plataforma, iniciar sesión de forma segura y consultar mi perfil, para acceder a mis funcionalidades según mi rol.

**Objetivo**
Implementar la base de la seguridad del sistema, permitiendo el registro, login y gestión de roles (ADMIN y USER) mediante JWT.

**Alcance**
- CRUD de usuarios.
- Registro e inicio de sesión.
- Envío de correos de verificación y reenvío.
- Implementación del filtro JWT (OncePerRequestFilter).
- Configuración de Spring Security y roles.

**Dependencias**
- Base de datos `user` ya ejecutada en MariaDB.
- Librería JJWT.

**Criterios de Épica (Para darla por completada)**
- Un usuario puede registrarse y loguearse.
- Un ADMIN puede ver y editar usuarios.
- Las rutas protegidas responden 401 sin token válido.

**Historias de Usuario Relacionadas**
- [HU-001: Registro de usuario](#hu-001-registro-de-usuario)
- [HU-002: Inicio de sesión](#hu-002-inicio-de-sesión)
- [HU-003: Obtener perfil propio](#hu-003-obtener-perfil-propio)
- [HU-017: Verificar cuenta mediante enlace de correo](#hu-017-verificar-cuenta-mediante-enlace-de-correo)
- [HU-018: Reenviar correo de verificación](#hu-018-reenviar-correo-de-verificación)

### Épica 2: Gestión de Productos

**Usuario(s):** Administrador (ADMIN) y Cliente (USER)

**Descripción**
Como administrador, quiero crear, editar, listar y desactivar productos, para mantener el catálogo actualizado y controlar el inventario. Como cliente, quiero ver los productos activos con su precio y stock, para poder comprarlos.

**Objetivo**
Implementar el CRUD de productos y la lógica de inventario (stock), asegurando que solo los administradores puedan modificar el catálogo, y que los clientes solo vean productos activos.

**Alcance**
- CRUD de productos (crear, leer, actualizar, desactivar lógicamente).
- Catálogo público de productos activos (accesible sin autenticación).
- Detalle público de producto individual.
- Control de stock (validaciones de cantidad).
- Auditoría (created_by, updated_by).
- Configuración de Spring Security para roles (ADMIN vs USER).

**Dependencias**
- Tabla `product` en MariaDB.
- JPA Auditing (AuditorAware).

**Criterios de Épica (Para darla por completada)**
- Un ADMIN puede crear, editar y desactivar productos.
- Un USER solo puede listar productos activos (is_active = 1).
- El sistema valida que el stock no sea negativo.
- Las rutas de administración responden 403 si el rol no es ADMIN.

**Historias de Usuario Relacionadas**
- [HU-004: Crear producto](#hu-004-crear-producto)
- [HU-005: Editar producto](#hu-005-editar-producto)
- [HU-006: Listar productos activos](#hu-006-listar-productos-activos)
- [HU-007: Desactivar producto](#hu-007-desactivar-producto)
- [HU-008: Listar productos para gestión](#hu-008-listar-productos-para-gestión)
- [HU-019: Ver detalle de producto](#hu-019-ver-detalle-de-producto)

### Épica 3: Gestión del Carrito de Compras

**Usuario(s):** Cliente autenticado (USER)

**Descripción**
Como cliente, quiero gestionar un carrito de compras personal, donde pueda agregar, actualizar y eliminar productos (cart_items), para tener el control de lo que voy a comprar antes de pagar.

**Objetivo**
Implementar la lógica completa del carrito, garantizando que cada usuario tenga un único carrito activo y que las cantidades se validen contra el stock real de los productos.

**Alcance**
- Creación automática del carrito asociado al usuario autenticado.
- Agregar productos al carrito. **Requiere autenticación.**
- Actualizar la cantidad de un producto en el carrito.
- Eliminar productos del carrito.
- Validación de stock disponible.
- Cálculo del subtotal por ítem y del total del carrito.

**Dependencias**
- Tablas `cart` y `cart_item` en MariaDB (con su llave única `cart_product_unique`).
- Módulo de productos (para consultar precio y stock).
- Seguridad JWT (para identificar al usuario dueño del carrito).

**Criterios de Épica (Para darla por completada)**
- Cada usuario autenticado tiene un único carrito activo.
- No se puede agregar un producto duplicado al mismo carrito (se actualiza la cantidad o da error controlado).
- El sistema no permite agregar más unidades de las que hay en stock.
- El endpoint devuelve el total calculado correctamente.

**Historias de Usuario Relacionadas**
- [HU-009: Agregar producto al carrito](#hu-009-agregar-producto-al-carrito)
- [HU-010: Actualizar cantidad de un producto en el carrito](#hu-010-actualizar-cantidad-de-un-producto-en-el-carrito)
- [HU-011: Eliminar producto del carrito](#hu-011-eliminar-producto-del-carrito)
- [HU-012: Ver detalle y total del carrito](#hu-012-ver-detalle-y-total-del-carrito)

### Épica 4: Procesamiento de Pagos con Stripe

**Usuario(s):** Cliente (USER) y Administrador (ADMIN)

**Descripción**
Como cliente, deseo completar el pago de los productos de mi carrito a través de una pasarela de pago segura, para recibir la confirmación de mi compra y tener un registro de mi historial. Como administrador, necesito consultar los pedidos realizados por los clientes para gestionar la operación y el inventario.

**Objetivo**
Implementar el flujo completo de compra, integrando una pasarela de pagos externa (Stripe), garantizando la persistencia permanente del pedido y la actualización automática del inventario tras una transacción exitosa.

**Alcance**
- Iniciar el proceso de pago desde el carrito.
- Redirigir al cliente a la pasarela de pago segura.
- Registrar el pedido y sus productos asociados tras la confirmación del pago.
- Actualizar automáticamente el stock de los productos vendidos.
- Vaciar el carrito del cliente una vez completada la compra.
- Gestionar los estados del pedido (pendiente, pagado, fallido, cancelado).
- Permitir la visualización del historial de pedidos por parte del usuario y del administrador.
- Enviar correo de confirmación al cliente tras un pago exitoso.

**Criterios de Épica (Para darla por completada)**
- El sistema impide el pago si el carrito está vacío.
- Un pago exitoso genera un pedido persistente, descuenta el stock y vacía el carrito.
- Un pago fallido o cancelado no altera el stock ni el carrito.
- El cliente puede ver el estado y detalle de sus compras anteriores.
- El administrador puede consultar la lista total de pedidos realizados.

**Historias de Usuario Relacionadas**
- [HU-013: Iniciar pago y redirigir a Stripe](#hu-013-iniciar-pago-y-redirigir-a-stripe)
- [HU-014: Procesar Webhook de pago exitoso y crear pedido](#hu-014-procesar-webhook-de-pago-exitoso-y-crear-pedido)
- [HU-015: Manejar errores y pagos fallidos o cancelados](#hu-015-manejar-errores-y-pagos-fallidos-o-cancelados)
- [HU-016: Visualizar historial de pedidos](#hu-016-visualizar-historial-de-pedidos)
- [HU-020: Enviar email de confirmación de compra](#hu-020-enviar-email-de-confirmación-de-compra)

### Épica 5: Gestión de Tickets de Soporte

**Usuario(s):** Cliente (USER) y Administrador (ADMIN)

**Descripción**
Como cliente, quiero reportar problemas sobre mis pedidos o mi cuenta y hacer seguimiento a las respuestas del equipo de soporte. Como administrador, quiero gestionar los tickets asignándolos, respondiéndolos y cambiando su estado, para coordinar el trabajo del equipo.

**Objetivo**
Implementar un sistema de tickets con persistencia en base de datos, estados, asignación a admins y conversación bidireccional entre cliente y admin.

**Alcance**
- Creación de tickets por parte del cliente (con categoría y pedido opcional).
- Listado y detalle de tickets (cliente ve los suyos; admin ve todos).
- Conversación bidireccional con notificaciones por email.
- Asignación de tickets a un admin.
- Cambio de estados: `OPEN`, `IN_PROGRESS`, `RESOLVED`, `CLOSED`.
- Filtros y paginación para el listado de admin.

**Dependencias**
- Módulo de Auth (autenticación de cliente y admin).
- Módulo de Pedidos y Pagos (para validar `order_id`).
- Servicio de email (Mailtrap/Gmail) para las notificaciones.

**Criterios de Épica (Para darla por completada)**
- Un cliente puede crear un ticket y verlo en su historial.
- Un admin puede listar, asignar, responder y cerrar tickets.
- El cliente recibe notificación por email cuando el admin responde.
- El admin asignado recibe notificación por email cuando el cliente responde.
- Al crear un ticket, se notifica por email al equipo de soporte.
- Los tickets cerrados no admiten nuevas respuestas.
- Un cliente solo puede ver y responder sus propios tickets.

**Historias de Usuario Relacionadas**
- [HU-021: Crear ticket de soporte](#hu-021-crear-ticket-de-soporte)
- [HU-022: Ver mis tickets y su detalle](#hu-022-ver-mis-tickets-y-su-detalle)
- [HU-023: Responder en un ticket (Cliente)](#hu-023-responder-en-un-ticket-cliente)
- [HU-024: Listar todos los tickets](#hu-024-listar-todos-los-tickets)
- [HU-025: Tomar y cambiar estado de un ticket](#hu-025-tomar-y-cambiar-estado-de-un-ticket)
- [HU-026: Responder en un ticket (Admin)](#hu-026-responder-en-un-ticket-admin)

---

## Historias de Usuario

### HU-001: Registro de usuario

| Épica | Rol |
| ----- | --- |
| [Épica 1: Autenticación y Gestión de Usuarios](#épica-1-autenticación-y-gestión-de-usuarios) | Usuario no registrado (futuro Cliente) |

**Historia**
Como usuario no registrado, quiero crear una cuenta con mi correo electrónico, para recibir un correo de verificación y activar mi cuenta antes de poder iniciar sesión.

**¿Por qué existe?**
Para garantizar que solo los usuarios autenticados puedan acceder a las funcionalidades de compra y consultar su historial, estableciendo la base de la seguridad del sistema.

**Prioridad MoSCoW**
- [x] **Must** (Debe tener)
- [ ] Should (Debería tener)
- [ ] Could (Podría tener)
- [ ] Won't (No tendrá)

**Criterios de Aceptación**
- Dado que un usuario ingresa un email válido y una contraseña con al menos 8 caracteres, entonces el sistema crea la cuenta del usuario inactiva, genera un token único con expiración de 8 horas, envía un correo de verificación y responde con un estado 201.
- Dado que un usuario intenta registrarse con un email que ya existe en la base de datos, entonces el sistema responde con un error 409 (Conflicto) y un mensaje claro.
- Dado que un usuario ingresa un email con formato inválido o una contraseña demasiado corta, entonces el sistema responde con un error 400 (Bad Request) y detalla el campo inválido.

### HU-002: Inicio de sesión

| Épica | Rol |
| ----- | --- |
| [Épica 1: Autenticación y Gestión de Usuarios](#épica-1-autenticación-y-gestión-de-usuarios) | Cliente autenticado |

**Historia**
Como usuario registrado y verificado, quiero iniciar sesión con mi email y contraseña, para obtener un token JWT que me permita acceder a los endpoints protegidos.

**¿Por qué existe?**
Para garantizar que solo los usuarios autenticados y con correo verificado puedan realizar operaciones protegidas, manteniendo la sesión de forma stateless.

**Prioridad MoSCoW**
- [x] **Must**
- [ ] Should
- [ ] Could
- [ ] Won't

**Criterios de Aceptación**
- Dado que el usuario envía credenciales correctas Y su correo está verificado, entonces el sistema responde con un estado 200 y un token JWT válido con expiración de 1 hora.
- Dado que el usuario envía credenciales correctas pero su correo NO está verificado, entonces el sistema responde con un error 403 (Forbidden) indicando que debe verificar su correo.
- Dado que el usuario envía email o contraseña incorrectos, entonces el sistema responde con un error 401 (Unauthorized).
- Dado que el token ha expirado, entonces el sistema responde con un error 401 al usarlo en una petición.

### HU-003: Obtener perfil propio

| Épica | Rol |
| ----- | --- |
| [Épica 1: Autenticación y Gestión de Usuarios](#épica-1-autenticación-y-gestión-de-usuarios) | Usuario autenticado |

**Historia**
Como usuario autenticado, quiero consultar mi perfil (nombre, email y rol), para verificar mi información personal y mis permisos.

**¿Por qué existe?**
Para que el usuario pueda ver sus datos personales y sepa qué permisos tiene dentro del sistema.

**Prioridad MoSCoW**
- [x] **Must**
- [ ] Should
- [ ] Could
- [ ] Won't

**Criterios de Aceptación**
- Dado que el usuario envía un token válido, entonces el sistema responde con un estado 200 y los datos del usuario.
- Dado que el token es inválido o no existe, entonces el sistema responde con un error 401.

### HU-004: Crear producto

| Épica | Rol |
| ----- | --- |
| [Épica 2: Gestión de Productos](#épica-2-gestión-de-productos) | Administrador |

**Historia**
Como administrador, quiero crear un nuevo producto con nombre, descripción, precio y stock, para agregarlo al catálogo.

**¿Por qué existe?**
Para que el administrador pueda ampliar la oferta de productos de la tienda.

**Prioridad MoSCoW**
- [x] **Must**
- [ ] Should
- [ ] Could
- [ ] Won't

**Criterios de Aceptación**
- Dado que el administrador envía datos válidos, entonces el sistema crea el producto y responde con un estado 201.
- Dado que el administrador envía un stock negativo, entonces el sistema responde con un error 400 (Bad Request).

### HU-005: Editar producto

| Épica | Rol |
| ----- | --- |
| [Épica 2: Gestión de Productos](#épica-2-gestión-de-productos) | Administrador |

**Historia**
Como administrador, quiero editar los datos de un producto existente, para actualizar su información o corregir errores.

**¿Por qué existe?**
Para mantener el catálogo actualizado y corregir precios o descripciones.

**Prioridad MoSCoW**
- [x] **Must**
- [ ] Should
- [ ] Could
- [ ] Won't

**Criterios de Aceptación**
- Dado que el administrador envía datos válidos de un producto existente, entonces el sistema actualiza y responde con un estado 200.
- Dado que el producto no existe, entonces el sistema responde con un error 404 (Not Found).

### HU-006: Listar productos activos

| Épica | Rol |
| ----- | --- |
| [Épica 2: Gestión de Productos](#épica-2-gestión-de-productos) | Visitante (usuario no autenticado) |

**Historia**
Como visitante, quiero ver la lista de productos activos con su precio y stock, para poder explorar el catálogo antes de decidir comprar.

**¿Por qué existe?**
Para permitir que cualquier persona explore el catálogo sin necesidad de crear una cuenta, facilitando la conversión de visitantes a clientes.

**Prioridad MoSCoW**
- [x] **Must**
- [ ] Should
- [ ] Could
- [ ] Won't

**Criterios de Aceptación**
- Dado que cualquier usuario (autenticado o no) consulta el catálogo, entonces el sistema responde con un estado 200 y solo los productos activos, sin requerir autenticación.
- Dado que el catálogo está vacío, entonces el sistema responde con una lista vacía.
- Dado que se aplican filtros (categoría, precio, búsqueda), entonces el sistema responde con los productos que coincidan.

### HU-007: Desactivar producto

| Épica | Rol |
| ----- | --- |
| [Épica 2: Gestión de Productos](#épica-2-gestión-de-productos) | Administrador |

**Historia**
Como administrador, quiero desactivar un producto, para que deje de estar disponible para la venta.

**¿Por qué existe?**
Para retirar del catálogo productos agotados o que ya no se quieren vender, sin borrarlos de la base de datos.

**Prioridad MoSCoW**
- [x] **Must**
- [ ] Should
- [ ] Could
- [ ] Won't

**Criterios de Aceptación**
- Dado que el administrador desactiva un producto, entonces el sistema responde con un estado 200 y `is_active = false`.
- Dado que un cliente consulta el catálogo, entonces ese producto ya no aparece en la lista.

### HU-008: Listar productos para gestión

| Épica | Rol |
| ----- | --- |
| [Épica 2: Gestión de Productos](#épica-2-gestión-de-productos) | Administrador |

**Historia**
Como administrador, quiero ver la lista completa de productos (incluyendo los que están inactivos), para poder seleccionar cuál necesito editar o desactivar.

**¿Por qué existe?**
Para que el administrador tenga visibilidad total del inventario y pueda gestionar correctamente el catálogo, incluso los productos que ya no están visibles para los clientes.

**Prioridad MoSCoW**
- [x] **Must**
- [ ] Should
- [ ] Could
- [ ] Won't

**Criterios de Aceptación**
- Dado que el administrador solicita el listado de productos, entonces el sistema responde con un estado 200 y devuelve **todos** los productos, independientemente de su estado (`is_active` sea `true` o `false`).
- Dado que el administrador solicita el listado, entonces el sistema permite filtrar por estado (activos o inactivos) para facilitar la búsqueda.

### HU-009: Agregar producto al carrito

| Épica | Rol |
| ----- | --- |
| [Épica 3: Gestión del Carrito de Compras](#épica-3-gestión-del-carrito-de-compras) | Cliente |

**Historia**
Como cliente, quiero agregar un producto a mi carrito indicando la cantidad, para poder comprarlo.

**¿Por qué existe?**
Para iniciar el proceso de compra y guardar los productos seleccionados.

**Prioridad MoSCoW**
- [x] **Must**
- [ ] Should
- [ ] Could
- [ ] Won't

**Criterios de Aceptación**
- Dado que un visitante no autenticado intenta agregar un producto al carrito, entonces el sistema responde con un error 401 (Unauthorized).
- Dado que el producto existe y el stock es suficiente, entonces el sistema agrega el ítem al carrito y responde con un estado 200.
- Dado que el producto ya estaba en el carrito, entonces el sistema actualiza la cantidad.
- Dado que el stock es insuficiente, entonces el sistema responde con un error 400.

### HU-010: Actualizar cantidad de un producto en el carrito

| Épica | Rol |
| ----- | --- |
| [Épica 3: Gestión del Carrito de Compras](#épica-3-gestión-del-carrito-de-compras) | Cliente |

**Historia**
Como cliente, quiero cambiar la cantidad de un producto en mi carrito, para ajustar mi pedido a mis necesidades.

**¿Por qué existe?**
Para dar flexibilidad al usuario antes de finalizar la compra.

**Prioridad MoSCoW**
- [x] **Must**
- [ ] Should
- [ ] Could
- [ ] Won't

**Criterios de Aceptación**
- Dado que el producto está en el carrito y la nueva cantidad es mayor a 0, entonces el sistema actualiza la cantidad y responde con un estado 200.
- Dado que la nueva cantidad supera el stock disponible, entonces el sistema responde con un error 400.

### HU-011: Eliminar producto del carrito

| Épica | Rol |
| ----- | --- |
| [Épica 3: Gestión del Carrito de Compras](#épica-3-gestión-del-carrito-de-compras) | Cliente |

**Historia**
Como cliente, quiero eliminar un producto de mi carrito, para quitarlo de mi compra.

**¿Por qué existe?**
Para que el usuario pueda corregir errores o cambiar de opinión sobre un producto.

**Prioridad MoSCoW**
- [x] **Must**
- [ ] Should
- [ ] Could
- [ ] Won't

**Criterios de Aceptación**
- Dado que el producto está en el carrito, entonces el sistema elimina el ítem y responde con un estado 200.

### HU-012: Ver detalle y total del carrito

| Épica | Rol |
| ----- | --- |
| [Épica 3: Gestión del Carrito de Compras](#épica-3-gestión-del-carrito-de-compras) | Cliente |

**Historia**
Como cliente, quiero ver el detalle de mi carrito (productos, cantidades y total a pagar), para confirmar mi compra.

**¿Por qué existe?**
Para que el usuario tenga visibilidad total de lo que va a pagar antes de continuar.

**Prioridad MoSCoW**
- [x] **Must**
- [ ] Should
- [ ] Could
- [ ] Won't

**Criterios de Aceptación**
- Dado que un visitante no autenticado intenta ver un carrito, entonces el sistema responde con un error 401.
- Dado que el carrito tiene productos, entonces el sistema responde con la lista de ítems y el total calculado correctamente.
- Dado que el carrito está vacío, entonces el sistema responde con una lista vacía y total en 0.

### HU-013: Iniciar pago y redirigir a Stripe

| Épica | Rol |
| ----- | --- |
| [Épica 4: Procesamiento de Pagos con Stripe](#épica-4-procesamiento-de-pagos-con-stripe) | Cliente |

**Historia**
Como cliente, quiero iniciar el pago de mi carrito, para ser redirigido a la pasarela de pago segura de Stripe.

**¿Por qué existe?**
Para procesar el pago de forma segura sin manejar datos sensibles de tarjetas en nuestro servidor.

**Prioridad MoSCoW**
- [x] **Must**
- [ ] Should
- [ ] Could
- [ ] Won't

**Criterios de Aceptación**
- Dado que el carrito no está vacío, entonces el sistema crea una sesión de pago y responde con la URL de Stripe.
- Dado que el carrito está vacío, entonces el sistema bloquea la acción y responde con un error 400.

### HU-014: Procesar Webhook de pago exitoso y crear pedido

| Épica | Rol |
| ----- | --- |
| [Épica 4: Procesamiento de Pagos con Stripe](#épica-4-procesamiento-de-pagos-con-stripe) | Sistema (Backend) |

**Historia**
Como sistema, quiero recibir la confirmación de pago de Stripe, para crear el pedido y vaciar el carrito automáticamente.

**¿Por qué existe?**
Para garantizar la integridad de la transacción, actualizar el inventario y no perder el historial de compra.

**Prioridad MoSCoW**
- [x] **Must**
- [ ] Should
- [ ] Could
- [ ] Won't

**Criterios de Aceptación**
- Dado que el webhook de Stripe indica un pago exitoso, entonces el sistema crea el pedido (Order y OrderItem), descuenta el stock y vacía el carrito.
- Dado que el webhook es válido, entonces el sistema responde a Stripe con un estado 200.

### HU-015: Manejar errores y pagos fallidos o cancelados

| Épica | Rol |
| ----- | --- |
| [Épica 4: Procesamiento de Pagos con Stripe](#épica-4-procesamiento-de-pagos-con-stripe) | Cliente |

**Historia**
Como cliente, quiero que el sistema maneje correctamente un pago fallido o cancelado, para poder reintentar la compra sin perder mi carrito.

**¿Por qué existe?**
Para brindar una buena experiencia de usuario en caso de errores externos.

**Prioridad MoSCoW**
- [ ] Must
- [x] **Should**
- [ ] Could
- [ ] Won't

**Criterios de Aceptación**
- Dado que el pago es cancelado o fallido, entonces el estado del pedido se marca como `FAILED` o `CANCELLED` y el carrito del usuario permanece intacto.

### HU-016: Visualizar historial de pedidos

| Épica | Rol |
| ----- | --- |
| [Épica 4: Procesamiento de Pagos con Stripe](#épica-4-procesamiento-de-pagos-con-stripe) | Cliente y Administrador |

**Historia**
Como cliente (o administrador), quiero consultar mi historial de pedidos, para revisar mis compras anteriores.

**¿Por qué existe?**
Para dar trazabilidad al usuario sobre sus compras y permitir al administrador gestionar la operación.

**Prioridad MoSCoW**
- [ ] Must
- [x] **Should**
- [ ] Could
- [ ] Won't

**Criterios de Aceptación**
- Dado que un cliente está autenticado, entonces el sistema responde únicamente con sus propios pedidos.
- Dado que un administrador está autenticado, entonces el sistema responde con el listado completo de todos los pedidos realizados en la plataforma.

### HU-017: Verificar cuenta mediante enlace de correo

| Épica | Rol |
| ----- | --- |
| [Épica 1: Autenticación y Gestión de Usuarios](#épica-1-autenticación-y-gestión-de-usuarios) | Usuario registrado (no verificado) |

**Historia**
Como usuario registrado (no verificado), quiero hacer clic en el enlace de verificación que recibí en mi correo, para activar mi cuenta y poder iniciar sesión.

**¿Por qué existe?**
Para confirmar que el correo electrónico es real y pertenece al usuario que se registró, evitando cuentas falsas.

**Prioridad MoSCoW**
- [x] **Must**
- [ ] Should
- [ ] Could
- [ ] Won't

**Criterios de Aceptación**
- Dado que el usuario hace clic en un enlace con un token válido (no expirado y no usado), entonces el sistema activa el usuario, invalida el token (uso único) y responde con estado 200.
- Dado que el token ha expirado (más de 8 horas), entonces el sistema responde con un error 400 indicando que el enlace expiró.
- Dado que el token ya fue usado anteriormente, entonces el sistema responde con un error 400 indicando que ya fue utilizado.
- Dado que el token no existe, entonces el sistema responde con un error 404.

### HU-018: Reenviar correo de verificación

| Épica | Rol |
| ----- | --- |
| [Épica 1: Autenticación y Gestión de Usuarios](#épica-1-autenticación-y-gestión-de-usuarios) | Usuario registrado (no verificado) |

**Historia**
Como usuario registrado que no ha verificado su correo, quiero solicitar un nuevo enlace de verificación, para poder activar mi cuenta si el anterior expiró o no llegó.

**¿Por qué existe?**
Para brindar una segunda oportunidad al usuario en caso de que el correo original no haya llegado o haya expirado.

**Prioridad MoSCoW**
- [ ] Must
- [x] **Should**
- [ ] Could
- [ ] Won't

**Criterios de Aceptación**
- Dado que el usuario solicita reenvío con un email de una cuenta no verificada, entonces el sistema invalida el token anterior, genera uno nuevo con expiración de 8 horas, envía el correo y responde con estado 200.
- Dado que el usuario ya tiene su correo verificado, entonces el sistema responde con un error 400 indicando que su cuenta ya está activa.
- Dado que el email no existe en la base de datos, entonces el sistema responde con un error 404.
- Dado que el usuario solicita más de 3 reenvíos en 1 hora, entonces el sistema responde con un error 429 (Too Many Requests).

### HU-019: Ver detalle de producto

| Épica | Rol |
| ----- | --- |
| [Épica 2: Gestión de Productos](#épica-2-gestión-de-productos) | Visitante (usuario no autenticado) |

**Historia**
Como visitante, quiero ver el detalle completo de un producto (nombre, descripción, precio, stock), para decidir si lo compro.

**¿Por qué existe?**
Para que el visitante pueda evaluar el producto antes de decidir agregarlo al carrito, sin necesidad de crear una cuenta.

**Prioridad MoSCoW**
- [x] **Must**
- [ ] Should
- [ ] Could
- [ ] Won't

**Criterios de Aceptación**
- Dado que cualquier usuario (autenticado o no) consulta el detalle de un producto activo, entonces el sistema responde con estado 200 y los datos completos, sin requerir autenticación.
- Dado que el producto no existe, entonces el sistema responde con un error 404.
- Dado que el producto existe pero está inactivo, entonces el sistema responde con un error 404 (no se muestra al público).

### HU-020: Enviar email de confirmación de compra

| Épica | Rol |
| ----- | --- |
| [Épica 4: Procesamiento de Pagos con Stripe](#épica-4-procesamiento-de-pagos-con-stripe) | Cliente autenticado (USER) |

**Historia**
Como cliente, quiero recibir un correo electrónico con el resumen de mi compra tras un pago exitoso, para tener constancia del pedido y poder consultar su estado.

**¿Por qué existe?**
Para brindar una confirmación inmediata al cliente y mejorar la percepción de la transacción. Es un diferenciador de experiencia de usuario respecto a la simple redirección desde Stripe.

**Prioridad MoSCoW**
- [ ] Must
- [x] **Should**
- [ ] Could
- [ ] Won't

**Criterios de Aceptación**
- Dado que un pago se confirma exitosamente (webhook de Stripe), entonces el sistema envía un correo al cliente con el resumen del pedido (productos, cantidades, total y número de pedido).
- Dado que el correo se envía, entonces el cliente puede hacer clic en un enlace para consultar el detalle del pedido en su historial.
- Dado que el envío del correo falla, entonces el sistema registra el error pero NO revierte la creación del pedido (el pedido ya está confirmado por Stripe).

### HU-021: Crear ticket de soporte

| Épica | Rol |
| ----- | --- |
| [Épica 5: Gestión de Tickets de Soporte](#épica-5-gestión-de-tickets-de-soporte) | Cliente autenticado (USER) |

**Historia**
Como cliente autenticado, quiero reportar un problema relacionado con un pedido o con mi cuenta, describiendo el caso y adjuntando la referencia del pedido afectado, para que el equipo de soporte pueda atender mi solicitud.

**¿Por qué existe?**
Para ofrecer un canal formal de soporte a los clientes, con trazabilidad de cada caso y posibilidad de asignación a múltiples admins.

**Prioridad MoSCoW**
- [x] **Must**
- [ ] Should
- [ ] Could
- [ ] Won't

**Criterios de Aceptación**
- Dado que un cliente autenticado envía un ticket con categoría, asunto y mensaje válidos, entonces el sistema lo persiste con estado `OPEN` y responde con estado 201 (Created).
- Dado que el ticket se crea exitosamente, entonces el sistema envía un correo de notificación a la dirección compartida de soporte.
- Dado que la categoría es `ORDER_ISSUE` o `PAYMENT_ISSUE`, entonces el campo `orderId` es obligatorio.
- Dado que la categoría es `ACCOUNT_ISSUE`, entonces el campo `orderId` es opcional.
- Dado que el `orderId` no existe o no pertenece al usuario autenticado, entonces el sistema responde con error 403 (Forbidden).
- Dado que el asunto supera los 200 caracteres o el mensaje supera los 2000 caracteres, entonces el sistema responde con error 400.
- Dado que el usuario envía más de 3 tickets en 1 hora, entonces el sistema responde con error 429 (Too Many Requests).

### HU-022: Ver mis tickets y su detalle

| Épica | Rol |
| ----- | --- |
| [Épica 5: Gestión de Tickets de Soporte](#épica-5-gestión-de-tickets-de-soporte) | Cliente autenticado (USER) |

**Historia**
Como cliente autenticado, quiero ver la lista de mis tickets y el detalle de cada uno (incluyendo la conversación), para hacer seguimiento al estado de mis reportes.

**¿Por qué existe?**
Para dar visibilidad al cliente sobre el estado y progreso de sus solicitudes de soporte.

**Prioridad MoSCoW**
- [x] **Must**
- [ ] Should
- [ ] Could
- [ ] Won't

**Criterios de Aceptación**
- Dado que un cliente autenticado consulta su lista de tickets, entonces el sistema responde con estado 200 y únicamente los tickets cuyo `user_id` coincide con el usuario del token.
- Dado que el cliente consulta el detalle de un ticket propio, entonces el sistema responde con estado 200 y todos los mensajes de la conversación ordenados cronológicamente.
- Dado que el cliente intenta ver un ticket que no le pertenece, entonces el sistema responde con error 403 (Forbidden).
- Dado que el cliente no tiene tickets, entonces el sistema responde con estado 200 y una lista vacía.

### HU-023: Responder en un ticket (Cliente)

| Épica | Rol |
| ----- | --- |
| [Épica 5: Gestión de Tickets de Soporte](#épica-5-gestión-de-tickets-de-soporte) | Cliente autenticado (USER) |

**Historia**
Como cliente autenticado, quiero responder en un ticket de soporte abierto, para aportar información adicional o continuar la conversación con el admin.

**¿Por qué existe?**
Para que el cliente pueda continuar la conversación sin perder el contexto del caso, manteniendo toda la comunicación en un solo hilo.

**Prioridad MoSCoW**
- [x] **Must**
- [ ] Should
- [ ] Could
- [ ] Won't

**Criterios de Aceptación**
- Dado que un cliente responde en un ticket propio cuyo estado NO es `CLOSED`, entonces el sistema persiste la respuesta con `is_admin_reply = false` y responde con estado 201 (Created).
- Dado que un cliente responde en un ticket propio en estado `RESOLVED`, entonces el sistema cambia automáticamente el estado a `IN_PROGRESS` y notifica al admin asignado.
- Dado que un cliente responde en un ticket en estado `CLOSED`, entonces el sistema responde con error 409 (Conflict).
- Dado que un cliente intenta responder en un ticket que no le pertenece, entonces el sistema responde con error 403 (Forbidden).
- Dado que la respuesta se persiste, entonces el sistema envía un correo al admin asignado (o a la dirección compartida si no está asignado).

### HU-024: Listar todos los tickets

| Épica | Rol |
| ----- | --- |
| [Épica 5: Gestión de Tickets de Soporte](#épica-5-gestión-de-tickets-de-soporte) | Administrador (ADMIN) |

**Historia**
Como administrador, quiero ver la lista completa de tickets con filtros por estado, para priorizar y coordinar la atención del equipo de soporte.

**¿Por qué existe?**
Para que el equipo de soporte tenga visibilidad total de los casos pendientes y pueda priorizar según estado y antigüedad.

**Prioridad MoSCoW**
- [x] **Must**
- [ ] Should
- [ ] Could
- [ ] Won't

**Criterios de Aceptación**
- Dado que un admin consulta la lista de tickets, entonces el sistema responde con estado 200 y todos los tickets del sistema.
- Dado que el admin aplica filtro por estado (`OPEN`, `IN_PROGRESS`, `RESOLVED`, `CLOSED`), entonces el sistema responde únicamente con los tickets que coinciden.
- Dado que el admin aplica filtro por categoría, entonces el sistema responde únicamente con los tickets de esa categoría.
- Dado que el admin consulta la lista, entonces el sistema devuelve los tickets ordenados por fecha de creación descendente y con paginación.
- Dado que un usuario con rol USER intenta acceder a este endpoint, entonces el sistema responde con error 403.

### HU-025: Tomar y cambiar estado de un ticket

| Épica | Rol |
| ----- | --- |
| [Épica 5: Gestión de Tickets de Soporte](#épica-5-gestión-de-tickets-de-soporte) | Administrador (ADMIN) |

**Historia**
Como administrador, quiero asignarme un ticket y cambiar su estado, para indicar al resto del equipo que estoy trabajando en él y reflejar su progreso.

**¿Por qué existe?**
Para coordinar el trabajo entre múltiples admins, evitando que dos personas atiendan el mismo caso y dando visibilidad al avance.

**Prioridad MoSCoW**
- [x] **Must**
- [ ] Should
- [ ] Could
- [ ] Won't

**Criterios de Aceptación**
- Dado que un admin se asigna un ticket en estado `OPEN`, entonces el sistema actualiza `assigned_to` y cambia el estado a `IN_PROGRESS`, y responde con estado 200.
- Dado que un admin intenta asignarse un ticket ya asignado a otro admin, entonces el sistema responde con error 409 (Conflict).
- Dado que un admin cambia el estado de un ticket a `RESOLVED` o `CLOSED`, entonces el sistema actualiza el estado y envía un correo al cliente notificando el cambio.
- Dado que un admin cambia el estado de un ticket a `CLOSED`, entonces el ticket ya no admite nuevas respuestas.
- Dado que un usuario con rol USER intenta cambiar el estado, entonces el sistema responde con error 403.

### HU-026: Responder en un ticket (Admin)

| Épica | Rol |
| ----- | --- |
| [Épica 5: Gestión de Tickets de Soporte](#épica-5-gestión-de-tickets-de-soporte) | Administrador (ADMIN) |

**Historia**
Como administrador, quiero responder en un ticket asignado o sin asignar, para brindar solución al cliente y dar seguimiento al caso.

**¿Por qué existe?**
Para que el equipo de soporte pueda comunicar la solución al cliente dentro del mismo hilo del ticket, manteniendo la trazabilidad.

**Prioridad MoSCoW**
- [x] **Must**
- [ ] Should
- [ ] Could
- [ ] Won't

**Criterios de Aceptación**
- Dado que un admin responde en un ticket cuyo estado NO es `CLOSED`, entonces el sistema persiste la respuesta con `is_admin_reply = true` y responde con estado 201 (Created).
- Dado que el admin responde, entonces el sistema envía un correo al cliente notificándole la respuesta con un enlace al ticket.
- Dado que el admin responde en un ticket sin asignar, entonces el sistema asigna automáticamente el ticket a ese admin y cambia el estado a `IN_PROGRESS`.
- Dado que el admin responde en un ticket en estado `CLOSED`, entonces el sistema responde con error 409 (Conflict).
- Dado que un usuario con rol USER intenta usar este endpoint, entonces el sistema responde con error 403.
