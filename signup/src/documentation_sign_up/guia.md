## Estructura General del Módulo

El módulo de signup sigue una arquitectura en capas, característica de aplicaciones Spring Boot:

```
Controller → Service → Repository → Entity
    ↓           ↓
   DTO       Exception
```

## Componentes Principales

### 1. Controller

**`SignupController.java`**
- **Propósito**: Recibe las peticiones HTTP para el registro de usuarios
- **Interacción**: Recibe un `SignupRequest` y delega la lógica de negocio al `UsuarioService`
- **Endpoint**: `/api/auth/signup` (POST)

### 2. DTOs (Data Transfer Objects)

**`SignupRequest.java`**
- **Propósito**: Transporta los datos del cliente al servidor para el registro
- **Contenido**: Datos del usuario (nombre, correo, teléfono, dirección, identificación, contraseña)
- **Validaciones**: Anotaciones Jakarta para validar campos (@NotBlank, @Email, @Size)

### 3. Service

**`UsuarioService.java` (interfaz) & `UsuarioServiceImpl.java` (implementación)**
- **Propósito**: Contiene la lógica de negocio para el registro
- **Funciones**:
    - Validar que correo, identificación y teléfono sean únicos
    - Encriptar la contraseña
    - Crear y guardar el usuario en la base de datos
- **Interacción**: Utiliza el `UsuarioRepository` y `BCryptPasswordEncoder`

### 4. Repository

**`UsuarioRepository.java`**
- **Propósito**: Interfaz para acceder a la base de datos
- **Métodos principales**:
    - `existsByCorreo`: Verifica si existe un usuario con ese correo
    - `existsByIdentificacion`: Verifica si existe un usuario con esa identificación
    - `existsByTelefono`: Verifica si existe un usuario con ese teléfono
    - `save`: Guarda el usuario en la base de datos
- **Tecnología**: Extiende JpaRepository para operaciones CRUD

### 5. Entity

**`Usuario.java`**
- **Propósito**: Representa la tabla de usuarios en la base de datos
- **Campos**: id, nombreCompleto, correo, teléfono, dirección, identificación, contraseña
- **Anotaciones**: JPA para mapeo objeto-relacional (@Entity, @Column, etc.)
- **Restricciones**: Campos únicos (correo, identificación, teléfono)

### 6. Configuration

**`edu.unicartagena.tcc.product.security.SecurityConfig.java`**
- **Propósito**: Configura la seguridad de la aplicación
- **Funciones**:
    - Define rutas públicas y protegidas
    - Configura el codificador de contraseñas (BCryptPasswordEncoder)
    - Deshabilita CSRF para APIs REST

### 7. Exception Handling

**`RestExceptionHandler.java`**
- **Propósito**: Maneja excepciones y proporciona respuestas HTTP adecuadas
- **Tipos de excepciones manejadas**:
    - Errores de validación (campos inválidos)
    - Errores de registro (datos duplicados)

**`RegistroException.java`**
- **Propósito**: Excepción personalizada para errores específicos de registro
- **Uso**: Lanzado cuando se violan restricciones de unicidad

## Flujo de Trabajo

1. **Cliente envía solicitud** → `SignupController` recibe los datos en formato `SignupRequest`
2. **Validación inicial** → Las anotaciones Jakarta validan el formato de los campos
3. **Lógica de negocio** → `UsuarioServiceImpl` verifica datos únicos y procesa el registro
4. **Persistencia** → `UsuarioRepository` guarda el usuario en la base de datos
5. **Respuesta** → El controlador devuelve una respuesta exitosa o `RestExceptionHandler` maneja errores

## Diagrama de Secuencia Simplificado

```
Cliente → SignupController → UsuarioServiceImpl → UsuarioRepository → Base de datos
   ↑                ↓               ↓
   └────────────────┴───────────────┘
         Respuesta/Excepción
```
