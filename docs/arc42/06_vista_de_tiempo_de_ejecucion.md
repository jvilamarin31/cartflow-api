# 6. Vista de Tiempo de Ejecución

## Descripción General

La Vista de Tiempo de Ejecución describe el comportamiento dinámico y las interacciones entre los bloques de construcción de CartFlow API durante escenarios clave. Los escenarios siguientes están definidos en las historias de usuario ([`epicas-y-hu.md`](../epicas-y-hu.md)); los que aún no están implementados se marcan como **🔜 Planificado (diseño propuesto)** y sirven como especificación de referencia.

## Escenario 1: Registro y Verificación de Cuenta (HU-001, HU-017)

- **Estado**: ✅ Registro implementado (sin envío de correo) · 🔜 Verificación planificada.
- **Desencadenante**: un usuario no registrado envía sus datos de registro.
- **Secuencia**: el backend valida los datos, verifica que el email no exista, crea la cuenta **inactiva**, genera un token de verificación con expiración de 8 horas, envía el correo y responde `201`. Al hacer clic en el enlace, la cuenta se activa y el token se invalida (uso único).
- **Gestión de Errores**: email ya registrado → `409`; email inválido o contraseña < 8 caracteres → `400`.

```mermaid
sequenceDiagram
    participant Usuario as Usuario no registrado
    participant API as API REST (Auth)
    participant AuthService as AuthService
    participant UserRepo as IUserRepository
    participant Email as Email Service (SMTP)
    participant DB as MariaDB

    Usuario ->> API: POST /api/auth/register (email, password, name)
    API ->> AuthService: register(request)
    AuthService ->> UserRepo: findByEmail(email)
    UserRepo ->> DB: SELECT user WHERE email = ?
    DB -->> UserRepo: resultado
    alt Email ya existe
        AuthService -->> API: EmailAlreadyExistsException
        API -->> Usuario: 409 Conflict
    else Datos válidos
        AuthService ->> AuthService: encode(password) con BCrypt
        AuthService ->> UserRepo: save(user inactivo)
        UserRepo ->> DB: INSERT user
        AuthService ->> Email: enviar correo de verificación (token 8h)
        Email -->> Usuario: Correo con enlace de verificación
        AuthService -->> API: RegisterResponse (id, email, role, token)
        API -->> Usuario: 201 Created
        Usuario ->> API: GET /api/auth/verify?token=...
        API ->> UserRepo: activar cuenta e invalidar token
        API -->> Usuario: 200 OK
    end
```

## Escenario 2: Inicio de Sesión y Acceso a Endpoint Protegido (HU-002, HU-003)

- **Estado**: 🔜 Planificado (diseño propuesto).
- **Desencadenante**: un usuario verificado envía sus credenciales.
- **Secuencia**: el backend valida las credenciales y que el correo esté verificado, y devuelve un JWT con expiración de 1 hora. En peticiones posteriores, `JwtAuthenticationFilter` valida el token y establece la autenticación.
- **Gestión de Errores**: correo no verificado → `403`; credenciales incorrectas → `401`; token expirado o ausente → `401`.

```mermaid
sequenceDiagram
    participant Cliente as Cliente
    participant API as API REST (Auth)
    participant AuthService as AuthService
    participant UserRepo as IUserRepository
    participant Jwt as JwtService
    participant Filter as JwtAuthenticationFilter

    Cliente ->> API: POST /api/auth/login (email, password)
    API ->> AuthService: login(email, password)
    AuthService ->> UserRepo: findByEmail(email)
    UserRepo -->> AuthService: UserEntity
    alt Credenciales correctas y correo verificado
        AuthService ->> Jwt: getToken(user)
        Jwt -->> AuthService: JWT (exp 1h)
        AuthService -->> API: token
        API -->> Cliente: 200 OK (token)
    else Correo no verificado
        API -->> Cliente: 403 Forbidden
    else Credenciales incorrectas
        API -->> Cliente: 401 Unauthorized
    end

    Cliente ->> API: GET /api/user/profile (Authorization: Bearer JWT)
    API ->> Filter: validar token
    Filter ->> Jwt: isTokenValid(token, user)
    alt Token válido
        Filter ->> Filter: setAuthentication(ROLE_USER)
        API -->> Cliente: 200 OK (perfil)
    else Token inválido o expirado
        API -->> Cliente: 401 Unauthorized
    end
```

## Escenario 3: Gestión del Carrito con Validación de Stock (HU-009, HU-010, HU-012)

- **Estado**: 🔜 Planificado (diseño propuesto).
- **Desencadenante**: un cliente autenticado agrega un producto a su carrito.
- **Secuencia**: el backend identifica al usuario por el JWT, recupera o crea su carrito activo, valida el stock disponible y guarda el `cart_item`; si el producto ya estaba, actualiza la cantidad. Al consultar el carrito, calcula el total.
- **Gestión de Errores**: usuario no autenticado → `401`; stock insuficiente o cantidad > stock → `400`.

```mermaid
sequenceDiagram
    participant Cliente as Cliente (USER)
    participant API as API REST (Cart)
    participant CartService as CartService
    participant ProductRepo as IProductRepository
    participant CartRepo as ICartRepository
    participant DB as MariaDB

    Cliente ->> API: POST /api/cart/items (productId, quantity) + Bearer JWT
    API ->> CartService: addItem(userId, productId, quantity)
    CartService ->> CartRepo: findOrCreateByUser(userId)
    CartRepo ->> DB: SELECT/INSERT cart
    CartService ->> ProductRepo: findById(productId)
    ProductRepo -->> CartService: ProductEntity (stock, price)
    alt Stock insuficiente
        CartService -->> API: error de negocio
        API -->> Cliente: 400 Bad Request
    else Stock suficiente
        CartService ->> CartRepo: guardar/actualizar cart_item
        CartRepo ->> DB: INSERT/UPDATE cart_item
        API -->> Cliente: 200 OK
    end

    Cliente ->> API: GET /api/cart + Bearer JWT
    API ->> CartService: getCart(userId)
    CartService ->> CartRepo: obtener ítems
    CartService ->> CartService: calcular total
    API -->> Cliente: 200 OK (ítems + total)
```

## Escenario 4: Checkout en Stripe y Webhook de Pago (HU-013, HU-014, HU-015)

- **Estado**: 🔜 Planificado (diseño propuesto).
- **Desencadenante**: el cliente inicia el pago de su carrito.
- **Secuencia**: si el carrito no está vacío, el backend crea una sesión de pago en Stripe y devuelve la URL. Cuando Stripe confirma el pago por webhook, el sistema crea el pedido (`order` y `order_item`), descuenta el stock y vacía el carrito.
- **Gestión de Errores**: carrito vacío → `400`; pago fallido o cancelado → pedido en estado `FAILED`/`CANCELLED` y carrito intacto.

```mermaid
sequenceDiagram
    participant Cliente as Cliente
    participant API as API REST (Order/Payment)
    participant PaymentService as PaymentService
    participant Stripe as Stripe
    participant CartRepo as ICartRepository
    participant OrderRepo as IOrderRepository
    participant ProductRepo as IProductRepository

    Cliente ->> API: POST /api/checkout + Bearer JWT
    API ->> PaymentService: iniciarPago(userId)
    PaymentService ->> CartRepo: obtener carrito
    alt Carrito vacío
        API -->> Cliente: 400 Bad Request
    else Carrito con productos
        PaymentService ->> Stripe: crear sesión de pago
        Stripe -->> PaymentService: sessionId + URL
        PaymentService -->> API: URL de pago
        API -->> Cliente: 200 OK (URL de Stripe)
        Cliente ->> Stripe: completar pago
        Stripe -->> API: webhook pago exitoso
        API ->> OrderRepo: crear order y order_items
        API ->> ProductRepo: descontar stock
        API ->> CartRepo: vaciar carrito
        API -->> Stripe: 200 OK
    end

    alt Pago fallido o cancelado
        Stripe -->> API: webhook de fallo/cancelación
        API ->> OrderRepo: order.status = FAILED / CANCELLED
        Note over CartRepo: El carrito permanece intacto
    end
```

## Motivación

Esta vista es esencial para comprender las interacciones dinámicas de CartFlow API durante operaciones críticas como el registro, la autenticación, la gestión del carrito y el pago. Al documentar estos escenarios a partir de las historias de usuario, el equipo cuenta con una especificación de referencia antes de implementar cada épica.
