# Guía paso a paso para crear el microservicio `signup` - BeerDistribution

Esta guía te explica cómo crear completamente un microservicio de registro de usuarios (`signup`) en un proyecto Spring Boot con estructura multi-módulo.

---

## 🧱 Paso 1: Crear el módulo `signup`

### 1.1. Generar proyecto desde Spring Initializr

Accede a: [https://start.spring.io](https://start.spring.io)  
Configura lo siguiente:

- **Group**: `edu.unicartagena.tcc`
- **Artifact**: `signup`
- **Name**: `signup`
- **Java**: 17 o 21
- **Packaging**: JAR
- **Versión de Spring Boot**: 3.2.x

### 1.2. Selecciona dependencias

- Spring Web
- Spring Data JPA
- Spring Boot DevTools
- MySQL Driver
- Spring Boot Validation
- Spring Security
- Lombok

Descarga y descomprime el proyecto. Mueve la carpeta `signup/` al interior del proyecto padre `tcc`.

---

## ⚙️ Paso 2: Modificar `pom.xml` del módulo `signup`

Haz que herede del pom padre (`tcc/pom.xml`):

```xml
<parent>
    <groupId>edu.unicartagena.tcc</groupId>
    <artifactId>BeerDistribution</artifactId>
    <version>0.0.1-SNAPSHOT</version>
    <relativePath>../pom.xml</relativePath>
</parent>
```

---

## 📁 Paso 3: Estructura de paquetes

```text
signup/
├── controller
├── dto
├── entity
├── repository
├── service
│   └── impl
├── config
├── exception
```

---

## 🛠️ Paso 4: Configurar `application.properties`

```properties
server.port=8081
spring.application.name=signup

spring.datasource.url=jdbc:mysql://localhost:3306/beer_signup_db?createDatabaseIfNotExist=true
spring.datasource.username=root
spring.datasource.password=tu_contraseña
spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true
```

---

## ✅ Paso 5: Implementar el endpoint `/api/auth/signup`

### 5.1 DTO - `SignupRequest.java`
```java
@Data
public class SignupRequest {
    @NotBlank private String nombreCompleto;
    @Email @NotBlank private String correo;
    @NotBlank private String telefono;
    @NotBlank private String direccion;
    @NotBlank private String identificacion;
    @Size(min = 6) private String contrasena;
}
```

### 5.2 Entidad - `Usuario.java`
```java
@Entity
@Table(name = "usuarios")
@Data @Builder @NoArgsConstructor @AllArgsConstructor
public class Usuario {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nombreCompleto;
    @Column(unique = true) private String correo;
    private String telefono;
    private String direccion;
    private String identificacion;
    private String contrasena;
}
```

### 5.3 Repositorio - `UsuarioRepository.java`
```java
public interface UsuarioRepository extends JpaRepository<Usuario, Long> {
    boolean existsByCorreo(String correo);
}
```

### 5.4 Servicio - `UsuarioService.java` y `UsuarioServiceImpl.java`
```java
public interface UsuarioService {
    void registrarUsuario(SignupRequest request);
}
```

```java
@Service
@RequiredArgsConstructor
public class UsuarioServiceImpl implements UsuarioService {
    private final UsuarioRepository repo;
    private final BCryptPasswordEncoder encoder;

    public void registrarUsuario(SignupRequest req) {
        if (repo.existsByCorreo(req.getCorreo())) {
            throw new IllegalArgumentException("El correo ya existe.");
        }

        Usuario usuario = Usuario.builder()
            .nombreCompleto(req.getNombreCompleto())
            .correo(req.getCorreo())
            .telefono(req.getTelefono())
            .direccion(req.getDireccion())
            .identificacion(req.getIdentificacion())
            .contrasena(encoder.encode(req.getContrasena()))
            .build();

        repo.save(usuario);
    }
}
```

### 5.5 Controlador - `SignupController.java`
```java
@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class SignupController {
    private final UsuarioService usuarioService;

    @PostMapping("/signup")
    public ResponseEntity<String> registrar(@RequestBody @Valid SignupRequest request) {
        usuarioService.registrarUsuario(request);
        return ResponseEntity.ok("Usuario registrado correctamente.");
    }
}
```

---

## 🔐 Paso 6: Configurar seguridad - `edu.unicartagena.tcc.product.security.SecurityConfig.java`

```java
@Configuration
@EnableWebSecurity
public class edu.unicartagena.tcc.product.security.SecurityConfig {
    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        return http
            .csrf(AbstractHttpConfigurer::disable)
            .authorizeHttpRequests(auth -> auth
                .requestMatchers("/api/auth/**", "/swagger-ui/**", "/v3/api-docs/**").permitAll()
                .anyRequest().authenticated()
            )
            .build();
    }
}
```

---

## ⚠️ Paso 7: Manejo global de errores

### Clase `RestExceptionHandler.java` para capturar errores de validación

```java
@RestControllerAdvice
public class RestExceptionHandler {
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<?> handleValidationErrors(MethodArgumentNotValidException ex) {
        Map<String, String> errores = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach(error -> {
            String campo = ((FieldError) error).getField();
            errores.put(campo, error.getDefaultMessage());
        });

        return ResponseEntity.badRequest().body(Map.of(
            "mensaje", "Error de validación",
            "errores", errores
        ));
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<?> handleCustom(IllegalArgumentException ex) {
        return ResponseEntity.badRequest().body(Map.of("mensaje", ex.getMessage()));
    }
}
```

---

## 📘 Paso 8: Habilitar Swagger

### Dependencia en `pom.xml`:
```xml
<dependency>
  <groupId>org.springdoc</groupId>
  <artifactId>springdoc-openapi-starter-webmvc-ui</artifactId>
  <version>2.2.0</version>
</dependency>
```

### URL de acceso:

```
http://localhost:8081/swagger-ui/index.html
```

---

Con esta guía has creado el microservicio `signup` completo, funcional, seguro y documentado.

