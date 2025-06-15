# 🧪 TCC_FINAL_S3

Proyecto desarrollado en Java como parte del Trabajo Colaborativo Contextualizado (TCC) del tercer semestre. El sistema implementa una arquitectura basada en microservicios para una tienda virtual enfocada en productos específicos en esta caso bebidas alcoholicas.

## 📌 Descripción del Proyecto

Este proyecto busca simular una plataforma de e-commerce con funcionalidades distribuidas por microservicios independientes. Entre las funcionalidades clave se encuentran:

- Registro y autenticación de usuarios (JWT)
- Gestión de productos
- Carrito de compras
- Comunicación entre microservicios (REST API)

## 🧱 Tecnologías Utilizadas

- Java 21
- Spring Boot 3
- Maven
- MySQL
- JWT para autenticación
- IntelliJ IDEA como IDE
- Postman para pruebas de endpoints

## 📁 Estructura del Proyecto

```

TCC\_FINAL\_S3/
├── signup/            # Microservicio de registro
├── login/             # Microservicio de autenticación
├── product/           # Microservicio para productos
├── carrito/           # Microservicio para el carrito de compras
└── README.md          # Documentación del proyecto

````

## 🚀 Instalación y Ejecución

1. Clona el repositorio:

```bash
git clone https://github.com/JOsorioCruz/TCC_FINAL_S3.git
````

2. Importa el proyecto en IntelliJ IDEA como un proyecto Maven.

3. Configura tu base de datos MySQL con el siguiente esquema:

```sql
CREATE DATABASE tcc_final;
```

4. Asegúrate de tener configurado tu archivo `application.properties` en cada microservicio con:

```properties
spring.datasource.url=jdbc:mysql://localhost:3306/tcc_final
spring.datasource.username=root
spring.datasource.password=tu_contraseña
spring.jpa.hibernate.ddl-auto=update
```

5. Ejecuta cada microservicio desde su clase principal con Spring Boot.

## 🔐 Autenticación

La autenticación se realiza mediante JWT. Luego de iniciar sesión exitosamente desde el microservicio `login`, el token debe incluirse en los headers para acceder a los demás servicios:

```http
Authorization: Bearer <tu_token>
```

## 📬 Endpoints Relevantes

* **POST /signup/register** → Registro de usuario
* **POST /login/authenticate** → Login y retorno de JWT
* **GET /product/all** → Listar productos
* **POST /carrito/agregar** → Agregar productos al carrito

## 🧪 Pruebas

Para probar los endpoints:

1. Usa Postman o cualquier cliente REST.
2. Autentícate primero para obtener el JWT.
3. Usa el JWT para probar los endpoints protegidos.

## 👥 Autores

* Jairo Osorio Cruz – QA Engineer | Backend Developer en formación

## 📜 Licencia

Este proyecto es de uso educativo y puede ser modificado libremente para fines académicos.

