# 8. Conceptos Transversales

## Descripción General

Los conceptos transversales definen los estándares técnicos, principios y soluciones aplicados en toda la arquitectura de CartFlow API. Estos principios afectan a múltiples componentes o capas, asegurando un enfoque unificado.

## Principales Conceptos Transversales

### 1. **Seguridad** ✅

El acceso a las rutas protegidas se controla con JWT. `JwtAuthenticationFilter` intercepta cada petición, valida el token, busca al usuario y construye sus authorities con prefijo `ROLE_` a partir del rol almacenado (no del claim). La configuración es stateless y las contraseñas se almacenan con hash BCrypt. Se aplican además cabeceras de seguridad (CSP, HSTS, `X-Frame-Options: DENY`, `X-Content-Type-Options`) y una política CORS explícita.

```mermaid
graph TD
    subgraph Sistemas_Afectados
        Auth["Módulo de Auth"]
        User["Módulo de Usuario"]
        Protected["Rutas protegidas (carrito, pedidos, admin)"]
    end

    Seguridad["Seguridad<br><i>JWT, BCrypt, CORS, cabeceras HTTP</i>"]

    Auth --> Seguridad
    User --> Seguridad
    Protected --> Seguridad
```

### 2. **Manejo de Errores y Validación** ✅

Las excepciones de negocio heredan de `CartflowException`, que transporta un `HttpStatus`. `GlobalExceptionHandler` (`@RestControllerAdvice`) las traduce a una respuesta uniforme `ErrorResponse` (con timestamp, status, error, mensaje, path y detalles). La validación de entrada con Jakarta Validation produce errores `400` con el detalle de cada campo inválido.

```mermaid
graph TD
    subgraph Sistemas_Afectados
        Auth["Módulo de Auth"]
        Cart["Módulo de Carrito (planificado)"]
        Orders["Módulo de Pedidos (planificado)"]
    end

    Errores["Manejo de Errores<br><i>CartflowException + GlobalExceptionHandler</i>"]

    Auth --> Errores
    Cart --> Errores
    Orders --> Errores
```

### 3. **Auditoría** ✅ (parcial)

Las entidades usan JPA Auditing (`@CreatedDate`, `@LastModifiedDate`) para registrar `created_at` y `updated_at`. La tabla `product` añade `created_by` y `updated_by` para trazabilidad de quién modifica el catálogo.

```mermaid
graph TD
    subgraph Sistemas_Afectados
        User["UserEntity (created_at, updated_at)"]
        Product["Product (created_by, updated_by) — planificado"]
        Order["Order / OrderItem (created_at) — planificado"]
    end

    Auditoria["Auditoría<br><i>JPA Auditing + columnas de trazabilidad</i>"]

    User --> Auditoria
    Product --> Auditoria
    Order --> Auditoria
```

### 4. **Consistencia Transaccional** 🔜

El flujo de compra debe ser atómico: al confirmarse el pago se crean `order` y `order_item`, se descuenta el stock y se vacía el carrito dentro de una misma transacción. El precio unitario se "congela" en `order_item.unit_price` para no depender del precio actual del producto.

```mermaid
graph TD
    subgraph Sistemas_Afectados
        Checkout["Checkout (Stripe webhook)"]
        Order["Order / OrderItem"]
        Product["Product (stock)"]
        Cart["Cart / CartItem"]
    end

    Transaccion["Consistencia Transaccional<br><i>@Transactional</i>"]

    Checkout --> Transaccion
    Order --> Transaccion
    Product --> Transaccion
    Cart --> Transaccion
```

### 5. **Configuración y Gestión de Secretos** ✅

La configuración sensible se externaliza en `backend/.env`, cargado mediante `springboot4-dotenv`. Ninguna credencial (`DB_PASSWORD`, `jwt.key`) se versiona. Las claves de configuración son `DB_URL`, `DB_USERNAME`, `DB_PASSWORD`, `jwt.key` (HMAC en Base64) y `jwt.expiration`.

```mermaid
graph TD
    subgraph Sistemas_Afectados
        DS["DataSource (MariaDB)"]
        Jwt["JwtService"]
        Env["backend/.env (no versionado)"]
    end

    Config["Configuración y Secretos<br><i>dotenv + Spring @Value</i>"]

    DS --> Config
    Jwt --> Config
    Env --> Config
```

## Vista Consolidada de Conceptos Transversales

El siguiente diagrama resume, a alto nivel, los sistemas afectados y los conceptos transversales definidos. Las relaciones específicas se documentan en detalle en cada subsección.

```mermaid
graph TB
    subgraph Conceptos_Transversales
        Seguridad
        Manejo_Errores
        Auditoria
        Transaccionalidad
        Configuracion
    end

    subgraph Sistemas_Afectados
        Auth
        User
        Product
        Cart
        Orders
        Common
    end
```

## Motivación

Estos conceptos establecen un enfoque coherente en estándares técnicos y prácticas de diseño, mejorando la seguridad, la trazabilidad y la integridad de datos de CartFlow API. Adoptarlos de forma transversal evita duplicar lógica y facilita la implementación consistente de las épicas pendientes.
