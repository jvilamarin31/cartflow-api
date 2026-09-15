# CartFlow API — Épicas e Historias de Usuario

> Documento sincronizado desde el tablero de GitHub Projects. Las épicas y HU se editan en el tablero; este archivo es su espejo en el repositorio.

## Índice

### Épicas
- [Épica 1: Autenticación y Gestión de Usuarios](#épica-1-autenticación-y-gestión-de-usuarios)
- [Épica 2: Gestión de Productos](#épica-2-gestión-de-productos)
- [Épica 3: Gestión del Carrito de Compras](#épica-3-gestión-del-carrito-de-compras)
- [Épica 4: Procesamiento de Pagos con Stripe](#épica-4-procesamiento-de-pagos-con-stripe)

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
- Envío de correos transaccionales (verificación de cuenta).
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
