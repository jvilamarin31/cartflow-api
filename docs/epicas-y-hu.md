Export of Github issues for [jvilamarin31/cartflow-api](https://github.com/jvilamarin31/cartflow-api). Generated on 2026.09.11 at 11:08:00.

# [\#20 Issue](https://github.com/jvilamarin31/cartflow-api/issues/20) `open`: [HU-016] Visualizar historial de pedidos

#### <img src="https://avatars.githubusercontent.com/u/185236460?v=4" width="50">[jvilamarin31](https://github.com/jvilamarin31) opened issue at [2026-08-29 01:43](https://github.com/jvilamarin31/cartflow-api/issues/20):

### Estructura de la HU
Como cliente (o administrador), quiero consultar mi historial de pedidos, para revisar mis compras anteriores.

### Rol
Cliente y Administrador

### ¿Por qué existe?
Para dar trazabilidad al usuario sobre sus compras y permitir al administrador gestionar la operación.

### Prioridad MoSCoW
- [ ] Must
- [x] **Should**
- [ ] Could
- [ ] Won't

### Criterios de Aceptación
- Dado que un cliente está autenticado, entonces el sistema responde únicamente con sus propios pedidos.
- Dado que un administrador está autenticado, entonces el sistema responde con el listado completo de todos los pedidos realizados en la plataforma.




-------------------------------------------------------------------------------

# [\#19 Issue](https://github.com/jvilamarin31/cartflow-api/issues/19) `open`: [HU-015] Manejar errores y pagos fallidos o cancelados

#### <img src="https://avatars.githubusercontent.com/u/185236460?v=4" width="50">[jvilamarin31](https://github.com/jvilamarin31) opened issue at [2026-08-29 01:41](https://github.com/jvilamarin31/cartflow-api/issues/19):

### Estructura de la HU
Como cliente, quiero que el sistema maneje correctamente un pago fallido o cancelado, para poder reintentar la compra sin perder mi carrito.

### Rol
Cliente

### ¿Por qué existe?
Para brindar una buena experiencia de usuario en caso de errores externos.

### Prioridad MoSCoW
- [ ] Must
- [x] **Should**
- [ ] Could
- [ ] Won't

### Criterios de Aceptación
- Dado que el pago es cancelado o fallido, entonces el estado del pedido se marca como `FAILED` o `CANCELLED` y el carrito del usuario permanece intacto.




-------------------------------------------------------------------------------

# [\#18 Issue](https://github.com/jvilamarin31/cartflow-api/issues/18) `open`: [HU-014] Procesar Webhook de pago exitoso y crear pedido

#### <img src="https://avatars.githubusercontent.com/u/185236460?v=4" width="50">[jvilamarin31](https://github.com/jvilamarin31) opened issue at [2026-08-29 01:40](https://github.com/jvilamarin31/cartflow-api/issues/18):

### Estructura de la HU
Como sistema, quiero recibir la confirmación de pago de Stripe, para crear el pedido y vaciar el carrito automáticamente.

### Rol
Sistema (Backend)

### ¿Por qué existe?
Para garantizar la integridad de la transacción, actualizar el inventario y no perder el historial de compra.

### Prioridad MoSCoW
- [x] **Must**
- [ ] Should
- [ ] Could
- [ ] Won't

### Criterios de Aceptación
- Dado que el webhook de Stripe indica un pago exitoso, entonces el sistema crea el pedido (Order y OrderItem), descuenta el stock y vacía el carrito.
- Dado que el webhook es válido, entonces el sistema responde a Stripe con un estado 200.




-------------------------------------------------------------------------------

# [\#17 Issue](https://github.com/jvilamarin31/cartflow-api/issues/17) `open`: [HU-013] Iniciar pago y redirigir a Stripe

#### <img src="https://avatars.githubusercontent.com/u/185236460?v=4" width="50">[jvilamarin31](https://github.com/jvilamarin31) opened issue at [2026-08-29 01:39](https://github.com/jvilamarin31/cartflow-api/issues/17):

### Estructura de la HU
Como cliente, quiero iniciar el pago de mi carrito, para ser redirigido a la pasarela de pago segura de Stripe.

### Rol
Cliente

### ¿Por qué existe?
Para procesar el pago de forma segura sin manejar datos sensibles de tarjetas en nuestro servidor.

### Prioridad MoSCoW
- [x] **Must**
- [ ] Should
- [ ] Could
- [ ] Won't

### Criterios de Aceptación
- Dado que el carrito no está vacío, entonces el sistema crea una sesión de pago y responde con la URL de Stripe.
- Dado que el carrito está vacío, entonces el sistema bloquea la acción y responde con un error 400.




-------------------------------------------------------------------------------

# [\#16 Issue](https://github.com/jvilamarin31/cartflow-api/issues/16) `open`: [HU-012] Ver detalle y total del carrito

#### <img src="https://avatars.githubusercontent.com/u/185236460?v=4" width="50">[jvilamarin31](https://github.com/jvilamarin31) opened issue at [2026-08-29 01:36](https://github.com/jvilamarin31/cartflow-api/issues/16):

### Estructura de la HU
Como cliente, quiero ver el detalle de mi carrito (productos, cantidades y total a pagar), para confirmar mi compra.

### Rol
Cliente

### ¿Por qué existe?
Para que el usuario tenga visibilidad total de lo que va a pagar antes de continuar.

### Prioridad MoSCoW
- [x] **Must**
- [ ] Should
- [ ] Could
- [ ] Won't

### Criterios de Aceptación
- Dado que el carrito tiene productos, entonces el sistema responde con la lista de ítems y el total calculado correctamente.
- Dado que el carrito está vacío, entonces el sistema responde con una lista vacía y total en 0.




-------------------------------------------------------------------------------

# [\#15 Issue](https://github.com/jvilamarin31/cartflow-api/issues/15) `open`: [HU-011] Eliminar producto del carrito

#### <img src="https://avatars.githubusercontent.com/u/185236460?v=4" width="50">[jvilamarin31](https://github.com/jvilamarin31) opened issue at [2026-08-29 01:35](https://github.com/jvilamarin31/cartflow-api/issues/15):

### Estructura de la HU
Como cliente, quiero eliminar un producto de mi carrito, para quitarlo de mi compra.

### Rol
Cliente

### ¿Por qué existe?
Para que el usuario pueda corregir errores o cambiar de opinión sobre un producto.

### Prioridad MoSCoW
- [x] **Must**
- [ ] Should
- [ ] Could
- [ ] Won't

### Criterios de Aceptación
- Dado que el producto está en el carrito, entonces el sistema elimina el ítem y responde con un estado 200.




-------------------------------------------------------------------------------

# [\#14 Issue](https://github.com/jvilamarin31/cartflow-api/issues/14) `open`: [HU-010] Actualizar cantidad de un producto en el carrito

#### <img src="https://avatars.githubusercontent.com/u/185236460?v=4" width="50">[jvilamarin31](https://github.com/jvilamarin31) opened issue at [2026-08-29 01:34](https://github.com/jvilamarin31/cartflow-api/issues/14):

### Estructura de la HU
Como cliente, quiero cambiar la cantidad de un producto en mi carrito, para ajustar mi pedido a mis necesidades.

### Rol
Cliente

### ¿Por qué existe?
Para dar flexibilidad al usuario antes de finalizar la compra.

### Prioridad MoSCoW
- [x] **Must**
- [ ] Should
- [ ] Could
- [ ] Won't

### Criterios de Aceptación
- Dado que el producto está en el carrito y la nueva cantidad es mayor a 0, entonces el sistema actualiza la cantidad y responde con un estado 200.
- Dado que la nueva cantidad supera el stock disponible, entonces el sistema responde con un error 400.




-------------------------------------------------------------------------------

# [\#13 Issue](https://github.com/jvilamarin31/cartflow-api/issues/13) `open`: [HU-009] Agregar producto al carrito

#### <img src="https://avatars.githubusercontent.com/u/185236460?v=4" width="50">[jvilamarin31](https://github.com/jvilamarin31) opened issue at [2026-08-29 01:30](https://github.com/jvilamarin31/cartflow-api/issues/13):

### Estructura de la HU
Como cliente, quiero agregar un producto a mi carrito indicando la cantidad, para poder comprarlo.

### Rol
Cliente

### ¿Por qué existe?
Para iniciar el proceso de compra y guardar los productos seleccionados.

### Prioridad MoSCoW
- [x] **Must**
- [ ] Should
- [ ] Could
- [ ] Won't

### Criterios de Aceptación
- Dado que el producto existe y el stock es suficiente, entonces el sistema agrega el ítem al carrito y responde con un estado 200.
- Dado que el producto ya estaba en el carrito, entonces el sistema actualiza la cantidad.
- Dado que el stock es insuficiente, entonces el sistema responde con un error 400.




-------------------------------------------------------------------------------

# [\#12 Issue](https://github.com/jvilamarin31/cartflow-api/issues/12) `open`: [HU-008] Listar productos para gestión (Admin)

#### <img src="https://avatars.githubusercontent.com/u/185236460?v=4" width="50">[jvilamarin31](https://github.com/jvilamarin31) opened issue at [2026-08-29 01:27](https://github.com/jvilamarin31/cartflow-api/issues/12):

### Estructura de la HU
Como administrador, quiero ver la lista completa de productos (incluyendo los que están inactivos), para poder seleccionar cuál necesito editar o desactivar.

### Rol
Administrador

### ¿Por qué existe?
Para que el administrador tenga visibilidad total del inventario y pueda gestionar correctamente el catálogo, incluso los productos que ya no están visibles para los clientes.

### Prioridad MoSCoW
- [x] **Must**
- [ ] Should
- [ ] Could
- [ ] Won't

### Criterios de Aceptación
- Dado que el administrador solicita el listado de productos, entonces el sistema responde con un estado 200 y devuelve **todos** los productos, independientemente de su estado (`is_active` sea `true` o `false`).
- Dado que el administrador solicita el listado, entonces el sistema permite filtrar por estado (activos o inactivos) para facilitar la búsqueda.




-------------------------------------------------------------------------------

# [\#11 Issue](https://github.com/jvilamarin31/cartflow-api/issues/11) `open`: [HU-007] Desactivar producto (Admin)

#### <img src="https://avatars.githubusercontent.com/u/185236460?v=4" width="50">[jvilamarin31](https://github.com/jvilamarin31) opened issue at [2026-08-29 01:27](https://github.com/jvilamarin31/cartflow-api/issues/11):

### Estructura de la HU
Como administrador, quiero desactivar un producto, para que deje de estar disponible para la venta.

### Rol
Administrador

### ¿Por qué existe?
Para retirar del catálogo productos agotados o que ya no se quieren vender, sin borrarlos de la base de datos.

### Prioridad MoSCoW
- [x] **Must**
- [ ] Should
- [ ] Could
- [ ] Won't

### Criterios de Aceptación
- Dado que el administrador desactiva un producto, entonces el sistema responde con un estado 200 y `is_active = false`.
- Dado que un cliente consulta el catálogo, entonces ese producto ya no aparece en la lista.




-------------------------------------------------------------------------------

# [\#10 Issue](https://github.com/jvilamarin31/cartflow-api/issues/10) `open`: [HU-006] Listar productos activos (Cliente)

#### <img src="https://avatars.githubusercontent.com/u/185236460?v=4" width="50">[jvilamarin31](https://github.com/jvilamarin31) opened issue at [2026-08-29 01:21](https://github.com/jvilamarin31/cartflow-api/issues/10):

### Estructura de la HU
Como cliente, quiero ver la lista de productos activos con su precio y stock, para poder elegir qué comprar.

### Rol
Cliente

### ¿Por qué existe?
Para que el cliente pueda navegar por el catálogo y decidir sus compras.

### Prioridad MoSCoW
- [x] **Must**
- [ ] Should
- [ ] Could
- [ ] Won't

### Criterios de Aceptación
- Dado que el cliente consulta el catálogo, entonces el sistema responde con un estado 200 y solo los productos con `is_active = true`.




-------------------------------------------------------------------------------

# [\#9 Issue](https://github.com/jvilamarin31/cartflow-api/issues/9) `open`: [HU-005] Editar producto (Admin)

#### <img src="https://avatars.githubusercontent.com/u/185236460?v=4" width="50">[jvilamarin31](https://github.com/jvilamarin31) opened issue at [2026-08-29 01:20](https://github.com/jvilamarin31/cartflow-api/issues/9):

### Estructura de la HU
Como administrador, quiero editar los datos de un producto existente, para actualizar su información o corregir errores.

### Rol
Administrador

### ¿Por qué existe?
Para mantener el catálogo actualizado y corregir precios o descripciones.

### Prioridad MoSCoW
- [x] **Must**
- [ ] Should
- [ ] Could
- [ ] Won't

### Criterios de Aceptación
- Dado que el administrador envía datos válidos de un producto existente, entonces el sistema actualiza y responde con un estado 200.
- Dado que el producto no existe, entonces el sistema responde con un error 404 (Not Found).




-------------------------------------------------------------------------------

# [\#8 Issue](https://github.com/jvilamarin31/cartflow-api/issues/8) `open`: [HU-004] Crear producto (Admin)

#### <img src="https://avatars.githubusercontent.com/u/185236460?v=4" width="50">[jvilamarin31](https://github.com/jvilamarin31) opened issue at [2026-08-29 01:19](https://github.com/jvilamarin31/cartflow-api/issues/8):

### Estructura de la HU
Como administrador, quiero crear un nuevo producto con nombre, descripción, precio y stock, para agregarlo al catálogo.

### Rol
Administrador

### ¿Por qué existe?
Para que el administrador pueda ampliar la oferta de productos de la tienda.

### Prioridad MoSCoW
- [x] **Must**
- [ ] Should
- [ ] Could
- [ ] Won't

### Criterios de Aceptación
- Dado que el administrador envía datos válidos, entonces el sistema crea el producto y responde con un estado 201.
- Dado que el administrador envía un stock negativo, entonces el sistema responde con un error 400 (Bad Request).




-------------------------------------------------------------------------------

# [\#7 Issue](https://github.com/jvilamarin31/cartflow-api/issues/7) `open`: [HU-003] Obtener perfil propio

#### <img src="https://avatars.githubusercontent.com/u/185236460?v=4" width="50">[jvilamarin31](https://github.com/jvilamarin31) opened issue at [2026-08-29 01:13](https://github.com/jvilamarin31/cartflow-api/issues/7):

### Estructura de la HU
Como usuario autenticado, quiero consultar mi perfil (nombre, email y rol), para verificar mi información personal y mis permisos.

### Rol
Usuario autenticado

### ¿Por qué existe?
Para que el usuario pueda ver sus datos personales y sepa qué permisos tiene dentro del sistema.

### Prioridad MoSCoW
- [x] **Must**
- [ ] Should
- [ ] Could
- [ ] Won't

### Criterios de Aceptación
- Dado que el usuario envía un token válido, entonces el sistema responde con un estado 200 y los datos del usuario.
- Dado que el token es inválido o no existe, entonces el sistema responde con un error 401.




-------------------------------------------------------------------------------

# [\#6 Issue](https://github.com/jvilamarin31/cartflow-api/issues/6) `open`: [HU-002] Inicio de sesión

#### <img src="https://avatars.githubusercontent.com/u/185236460?v=4" width="50">[jvilamarin31](https://github.com/jvilamarin31) opened issue at [2026-08-29 01:11](https://github.com/jvilamarin31/cartflow-api/issues/6):

### Estructura de la HU
Como usuario registrado, quiero iniciar sesión con mi email y contraseña, para obtener un token JWT que me permita acceder a los endpoints protegidos.

### Rol
Usuario registrado

### ¿Por qué existe?
Para garantizar que solo los usuarios autenticados puedan realizar operaciones en la plataforma, manteniendo la sesión de forma stateless.

### Prioridad MoSCoW
- [x] **Must**
- [ ] Should
- [ ] Could
- [ ] Won't

### Criterios de Aceptación
- Dado que el usuario envía credenciales correctas, entonces el sistema responde con un estado 200 y un token JWT válido con expiración de 1 hora.
- Dado que el usuario envía email o contraseña incorrectos, entonces el sistema responde con un error 401 (Unauthorized).
- Dado que el token ha expirado, entonces el sistema responde con un error 401 al usarlo en una petición.




-------------------------------------------------------------------------------

# [\#5 Issue](https://github.com/jvilamarin31/cartflow-api/issues/5) `open`: [HU-001] Registro de usuario

#### <img src="https://avatars.githubusercontent.com/u/185236460?v=4" width="50">[jvilamarin31](https://github.com/jvilamarin31) opened issue at [2026-08-28 02:48](https://github.com/jvilamarin31/cartflow-api/issues/5):

### Estructura de la HU
Como usuario no registrado, quiero crear una cuenta con mi correo electrónico y una contraseña segura, para poder acceder a la plataforma y realizar compras.

### Rol
Usuario no registrado (futuro Cliente)

### ¿Por qué existe?
Para garantizar que solo los usuarios autenticados puedan acceder a las funcionalidades de compra y consultar su historial, estableciendo la base de la seguridad del sistema.

### Prioridad MoSCoW
- [x] **Must** (Debe tener)
- [ ] Should (Debería tener)
- [ ] Could (Podría tener)
- [ ] Won't (No tendrá)

### Criterios de Aceptación
- Dado que un usuario ingresa un email válido y una contraseña con al menos 8 caracteres, entonces el sistema crea la cuenta, encripta la contraseña con BCrypt y responde con un estado 201.
- Dado que un usuario intenta registrarse con un email que ya existe en la base de datos, entonces el sistema responde con un error 409 (Conflicto) y un mensaje claro.
- Dado que un usuario ingresa un email con formato inválido o una contraseña demasiado corta, entonces el sistema responde con un error 400 (Bad Request) y detalla el campo inválido.




-------------------------------------------------------------------------------

# [\#4 Issue](https://github.com/jvilamarin31/cartflow-api/issues/4) `open`: Épica 4: Procesamiento de Pagos con Stripe

#### <img src="https://avatars.githubusercontent.com/u/185236460?v=4" width="50">[jvilamarin31](https://github.com/jvilamarin31) opened issue at [2026-08-28 02:46](https://github.com/jvilamarin31/cartflow-api/issues/4):

### Usuario(s)
Cliente (USER) y Administrador (ADMIN)

### Descripción
Como cliente, deseo completar el pago de los productos de mi carrito a través de una pasarela de pago segura, para recibir la confirmación de mi compra y tener un registro de mi historial. Como administrador, necesito consultar los pedidos realizados por los clientes para gestionar la operación y el inventario.

### Objetivo
Implementar el flujo completo de compra, integrando una pasarela de pagos externa (Stripe), garantizando la persistencia permanente del pedido y la actualización automática del inventario tras una transacción exitosa.

### Alcance
- Iniciar el proceso de pago desde el carrito.
- Redirigir al cliente a la pasarela de pago segura.
- Registrar el pedido y sus productos asociados tras la confirmación del pago.
- Actualizar automáticamente el stock de los productos vendidos.
- Vaciar el carrito del cliente una vez completada la compra.
- Gestionar los estados del pedido (pendiente, pagado, fallido, cancelado).
- Permitir la visualización del historial de pedidos por parte del usuario y del administrador.

### Criterios de Épica (Para darla por completada)
- El sistema impide el pago si el carrito está vacío.
- Un pago exitoso genera un pedido persistente, descuenta el stock y vacía el carrito.
- Un pago fallido o cancelado no altera el stock ni el carrito.
- El cliente puede ver el estado y detalle de sus compras anteriores.
- El administrador puede consultar la lista total de pedidos realizados.

### Historias de Usuario Relacionadas
- [ ] [HU-013](https://github.com/jvilamarin31/cartflow-api/issues/17) Iniciar pago y redirigir a Stripe.
- [ ] [HU-014](https://github.com/jvilamarin31/cartflow-api/issues/18) Procesar Webhook de pago exitoso y crear pedido.
- [ ] [HU-015](https://github.com/jvilamarin31/cartflow-api/issues/19) Manejar errores y pagos fallidos o cancelados.
- [ ] [HU-016](https://github.com/jvilamarin31/cartflow-api/issues/20) Visualizar historial de pedidos (Admin/Cliente).




-------------------------------------------------------------------------------

# [\#3 Issue](https://github.com/jvilamarin31/cartflow-api/issues/3) `open`: Épica 3: Gestión del Carrito de Compras

#### <img src="https://avatars.githubusercontent.com/u/185236460?v=4" width="50">[jvilamarin31](https://github.com/jvilamarin31) opened issue at [2026-08-28 02:14](https://github.com/jvilamarin31/cartflow-api/issues/3):

### Usuario(s)
Cliente (USER)

### Descripción
Como cliente, quiero gestionar un carrito de compras personal, donde pueda agregar, actualizar y eliminar productos (cart_items), para tener el control de lo que voy a comprar antes de pagar.

### Objetivo
Implementar la lógica completa del carrito (tabla `cart` y `cart_item`), garantizando que cada usuario tenga un único carrito activo y que las cantidades se validen contra el stock real de los productos.

### Alcance
- Creación automática del carrito (tabla `cart`) asociado al usuario autenticado.
- Agregar productos al carrito (tabla `cart_item`).
- Actualizar la cantidad de un producto en el carrito.
- Eliminar productos del carrito.
- Validación de stock disponible (contra la tabla `product`).
- Cálculo del subtotal por ítem y del total del carrito.

### Dependencias
- Tablas `cart` y `cart_item` en MariaDB (con su llave única `cart_product_unique`).
- Módulo de productos (para consultar precio y stock).
- Seguridad JWT (para identificar al usuario dueño del carrito).

### Criterios de Épica (Para darla por completada)
- Cada usuario autenticado tiene un único carrito activo.
- No se puede agregar un producto duplicado al mismo carrito (se actualiza la cantidad o da error controlado).
- El sistema no permite agregar más unidades de las que hay en stock.
- El endpoint devuelve el total calculado correctamente.

### Historias de Usuario Relacionadas
- [ ] [HU-009](https://github.com/jvilamarin31/cartflow-api/issues/13)  Agregar producto al carrito
- [ ] [HU-010](https://github.com/jvilamarin31/cartflow-api/issues/14)  Actualizar cantidad de un producto en el carrito
- [ ] [HU-011](https://github.com/jvilamarin31/cartflow-api/issues/15)  Eliminar producto del carrito
- [ ] [HU-012](https://github.com/jvilamarin31/cartflow-api/issues/16)  Ver detalle y total del carrito




-------------------------------------------------------------------------------

# [\#2 Issue](https://github.com/jvilamarin31/cartflow-api/issues/2) `open`: Épica 2: Gestión de Productos

#### <img src="https://avatars.githubusercontent.com/u/185236460?v=4" width="50">[jvilamarin31](https://github.com/jvilamarin31) opened issue at [2026-08-28 02:12](https://github.com/jvilamarin31/cartflow-api/issues/2):

### Usuario(s)
Administrador (ADMIN) y Cliente (USER)

### Descripción
Como administrador, quiero crear, editar, listar y desactivar productos, para mantener el catálogo actualizado y controlar el inventario. Como cliente, quiero ver los productos activos con su precio y stock, para poder comprarlos.

### Objetivo
Implementar el CRUD de productos y la lógica de inventario (stock), asegurando que solo los administradores puedan modificar el catálogo, y que los clientes solo vean productos activos.

### Alcance
- CRUD de productos (crear, leer, actualizar, desactivar lógicamente).
- Control de stock (validaciones de cantidad).
- Auditoría (created_by, updated_by).
- Configuración de Spring Security para roles (ADMIN vs USER).

### Dependencias
- Tabla `product` en MariaDB.
- JPA Auditing (AuditorAware).

### Criterios de Épica (Para darla por completada)
- Un ADMIN puede crear, editar y desactivar productos.
- Un USER solo puede listar productos activos (is_active = 1).
- El sistema valida que el stock no sea negativo.
- Las rutas de administración responden 403 si el rol no es ADMIN.

### Historias de Usuario Relacionadas
- [ ] [HU-004](https://github.com/jvilamarin31/cartflow-api/issues/8) Crear producto (Admin)
-  [ ] [HU-005](https://github.com/jvilamarin31/cartflow-api/issues/9) Editar producto (Admin)
- [ ] [HU-006](https://github.com/jvilamarin31/cartflow-api/issues/10) Listar productos activos (Cliente)
- [ ] [HU-007](https://github.com/jvilamarin31/cartflow-api/issues/11) Desactivar producto (Admin)
- [ ] [HU-008](https://github.com/jvilamarin31/cartflow-api/issues/12)  Listar productos para gestión (Admin)




-------------------------------------------------------------------------------

# [\#1 Issue](https://github.com/jvilamarin31/cartflow-api/issues/1) `open`: Épica 1: Autenticación y Gestión de Usuarios

#### <img src="https://avatars.githubusercontent.com/u/185236460?v=4" width="50">[jvilamarin31](https://github.com/jvilamarin31) opened issue at [2026-08-27 04:17](https://github.com/jvilamarin31/cartflow-api/issues/1):

### Usuario(s)
Usuario final (Cliente y Administrador)

### Descripción
Como usuario, quiero registrarme en la plataforma, iniciar sesión de forma segura y consultar mi perfil, para acceder a mis funcionalidades según mi rol.

### Objetivo
Implementar la base de la seguridad del sistema, permitiendo el registro, login y gestión de roles (ADMIN y USER) mediante JWT.

### Alcance
- CRUD de usuarios.
- Registro e inicio de sesión.
- Implementación del filtro JWT (OncePerRequestFilter).
- Configuración de Spring Security y roles.

### Dependencias
- Base de datos `user` ya ejecutada en MariaDB.
- Librería JJWT.

### Criterios de Épica (Para darla por completada)
- Un usuario puede registrarse y loguearse.
- Un ADMIN puede ver y editar usuarios.
- Las rutas protegidas responden 401 sin token válido.

### Historias de Usuario Relacionadas
- [ ] [HU-001](https://github.com/jvilamarin31/cartflow-api/issues/5) Registro de usuario
- [ ] [HU-002](https://github.com/jvilamarin31/cartflow-api/issues/6) Inicio de sesión
- [ ] [HU-003](https://github.com/jvilamarin31/cartflow-api/issues/7) Obtener perfil propio




-------------------------------------------------------------------------------

