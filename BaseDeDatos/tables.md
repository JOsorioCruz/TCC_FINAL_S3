---

# 📦 Base de Datos del Sistema de Venta de Bebidas

A continuación se describen las tablas que conforman la base de datos del sistema, con sus campos, restricciones y relaciones.

---

## 🧑‍💼 Tabla: `usuarios`

| Campo                 | Tipo         | Restricciones                                                       |
| --------------------- | ------------ | ------------------------------------------------------------------- |
| `id_usuario`          | SERIAL       | PRIMARY KEY                                                         |
| `nombre_completo`     | VARCHAR(100) | NOT NULL                                                            |
| `correo`              | VARCHAR(100) | UNIQUE, NOT NULL                                                    |
| `telefono`            | VARCHAR(20)  | Opcional                                                            |
| `direccion`           | TEXT         | Opcional                                                            |
| `tipo_identificacion` | VARCHAR(5)   | CHECK (IN 'CC', 'NIT')                                              |
| `identificacion`      | VARCHAR(20)  | UNIQUE, NOT NULL                                                    |
| `contrasena`          | VARCHAR(255) | NOT NULL                                                            |
| `fecha_registro`      | TIMESTAMP    | DEFAULT CURRENT\_TIMESTAMP                                          |

---

## Sentencias SQL Tabla: `usuarios`
```sql
CREATE TABLE usuarios (
  id_usuario SERIAL PRIMARY KEY,
  nombre_completo VARCHAR(100) NOT NULL,
  correo VARCHAR(100) UNIQUE NOT NULL,
  telefono VARCHAR(20),
  direccion TEXT,
  tipo_identificacion VARCHAR(5) CHECK (tipo_identificacion IN ('CC', 'NIT')),
  identificacion VARCHAR(20) UNIQUE NOT NULL,
  contrasena VARCHAR(255) NOT NULL,
  rol VARCHAR(20) CHECK (rol IN ('comprador', 'vendedor', 'admin')) DEFAULT 'comprador',
  fecha_registro TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);
```

## 🍾 Tabla: `productos`

| Campo                  | Tipo          | Restricciones                                          |
| ---------------------- | ------------- | ------------------------------------------------------ |
| `id_producto`          | SERIAL        | PRIMARY KEY                                            |
| `nombre`               | VARCHAR(100)  | NOT NULL                                               |
| `descripcion`          | TEXT          | Opcional                                               |
| `tipo_bebida`          | VARCHAR(50)   | Opcional                                               |
| `precio`               | DECIMAL(10,2) | NOT NULL                                               |
| `cantidad_stock`       | INT           | NOT NULL, CHECK (>= 0)                                 |
| `oferta_activa`        | BOOLEAN       | DEFAULT `FALSE`                                        |
| `porcentaje_descuento` | DECIMAL(5,2)  | Opcional                                               |
| `id_vendedor`          | INT           | FOREIGN KEY → `usuarios(id_usuario)` ON DELETE CASCADE |

---
## Sentencias SQL Tabla: `productos`
```sql
CREATE TABLE productos (
  id_producto SERIAL PRIMARY KEY,
  nombre VARCHAR(100) NOT NULL,
  descripcion TEXT,
  tipo_bebida VARCHAR(50),
  precio DECIMAL(10,2) NOT NULL,
  cantidad_stock INT NOT NULL CHECK (cantidad_stock >= 0),
  oferta_activa BOOLEAN DEFAULT FALSE,
  porcentaje_descuento DECIMAL(5,2),
  id_vendedor INT REFERENCES usuarios(id_usuario) ON DELETE CASCADE
);
```

## 🛒 Tabla: `carrito`

| Campo            | Tipo        | Restricciones                                            |
| ---------------- | ----------- | -------------------------------------------------------- |
| `id_carrito`     | SERIAL      | PRIMARY KEY                                              |
| `id_usuario`     | INT         | FOREIGN KEY → `usuarios(id_usuario)` ON DELETE CASCADE   |
| `estado`         | VARCHAR(20) | CHECK (IN 'pendiente', 'pagado') - DEFAULT `'pendiente'` |
| `fecha_creacion` | TIMESTAMP   | DEFAULT CURRENT\_TIMESTAMP                               |

---

## Sentencias SQL Tabla: `carrito`
```sql
CREATE TABLE carrito (
  id_carrito SERIAL PRIMARY KEY,
  id_usuario INT REFERENCES usuarios(id_usuario) ON DELETE CASCADE,
  estado VARCHAR(20) CHECK (estado IN ('pendiente', 'pagado')) DEFAULT 'pendiente',
  fecha_creacion TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);
```
## 💰 Tabla: `ventas`

| Campo               | Tipo          | Restricciones                                     |
| ------------------- | ------------- | ------------------------------------------------- |
| `id_venta`          | SERIAL        | PRIMARY KEY                                       |
| `id_usuario`        | INT           | FOREIGN KEY → `usuarios(id_usuario)`              |
| `total`             | DECIMAL(12,2) | NOT NULL                                          |
| `metodo_pago`       | VARCHAR(20)   | CHECK (IN 'pse', 'tarjeta', 'efectivo')           |
| `direccion_entrega` | TEXT          | Opcional                                          |
| `estado_entrega`    | VARCHAR(30)   | CHECK (IN 'preparando', 'en camino', 'entregado') |
| `fecha_venta`       | TIMESTAMP     | DEFAULT CURRENT\_TIMESTAMP                        |

---
## Sentencias SQL Tabla: `ventas`
```sql
CREATE TABLE ventas (
  id_venta SERIAL PRIMARY KEY,
  id_usuario INT REFERENCES usuarios(id_usuario),
  total DECIMAL(12,2) NOT NULL,
  metodo_pago VARCHAR(20) CHECK (metodo_pago IN ('pse', 'tarjeta', 'efectivo')),
  direccion_entrega TEXT,
  estado_entrega VARCHAR(30) CHECK (estado_entrega IN ('preparando', 'en camino', 'entregado')),
  fecha_venta TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);
```
