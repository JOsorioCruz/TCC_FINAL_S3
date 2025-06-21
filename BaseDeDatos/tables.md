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
| `rol`                 | VARCHAR(20)  | CHECK (IN 'comprador', 'vendedor', 'admin') - DEFAULT `'comprador'` |
| `fecha_registro`      | TIMESTAMP    | DEFAULT CURRENT\_TIMESTAMP                                          |

---

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

## ❤️ Tabla: `favoritos`

| Campo         | Tipo   | Restricciones                                                     |
| ------------- | ------ | ----------------------------------------------------------------- |
| `id_favorito` | SERIAL | PRIMARY KEY                                                       |
| `id_usuario`  | INT    | FOREIGN KEY → `usuarios(id_usuario)` ON DELETE CASCADE            |
| `id_producto` | INT    | FOREIGN KEY → `productos(id_producto)` ON DELETE CASCADE          |
| UNIQUE        |        | (`id_usuario`, `id_producto`) para evitar duplicidad en favoritos |

---

## 🛒 Tabla: `carrito`

| Campo            | Tipo        | Restricciones                                            |
| ---------------- | ----------- | -------------------------------------------------------- |
| `id_carrito`     | SERIAL      | PRIMARY KEY                                              |
| `id_usuario`     | INT         | FOREIGN KEY → `usuarios(id_usuario)` ON DELETE CASCADE   |
| `estado`         | VARCHAR(20) | CHECK (IN 'pendiente', 'pagado') - DEFAULT `'pendiente'` |
| `fecha_creacion` | TIMESTAMP   | DEFAULT CURRENT\_TIMESTAMP                               |

---

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

## 📦 Tabla: `detalle_venta`

| Campo             | Tipo          | Restricciones                                      |
| ----------------- | ------------- | -------------------------------------------------- |
| `id_detalle`      | SERIAL        | PRIMARY KEY                                        |
| `id_venta`        | INT           | FOREIGN KEY → `ventas(id_venta)` ON DELETE CASCADE |
| `id_producto`     | INT           | FOREIGN KEY → `productos(id_producto)`             |
| `cantidad`        | INT           | NOT NULL                                           |
| `precio_unitario` | DECIMAL(10,2) | NOT NULL                                           |

---

## 💬 Tabla: `comentarios`

| Campo           | Tipo      | Restricciones                          |
| --------------- | --------- | -------------------------------------- |
| `id_comentario` | SERIAL    | PRIMARY KEY                            |
| `id_usuario`    | INT       | FOREIGN KEY → `usuarios(id_usuario)`   |
| `id_producto`   | INT       | FOREIGN KEY → `productos(id_producto)` |
| `comentario`    | TEXT      | Opcional                               |
| `calificacion`  | INT       | CHECK (BETWEEN 1 AND 5)                |
| `fecha`         | TIMESTAMP | DEFAULT CURRENT\_TIMESTAMP             |

---

## ↩️ Tabla: `respuestas_comentario`

| Campo           | Tipo      | Restricciones                                                |
| --------------- | --------- | ------------------------------------------------------------ |
| `id_respuesta`  | SERIAL    | PRIMARY KEY                                                  |
| `id_comentario` | INT       | FOREIGN KEY → `comentarios(id_comentario)` ON DELETE CASCADE |
| `id_vendedor`   | INT       | FOREIGN KEY → `usuarios(id_usuario)`                         |
| `respuesta`     | TEXT      | Opcional                                                     |
| `fecha`         | TIMESTAMP | DEFAULT CURRENT\_TIMESTAMP                                   |

---

## 🏷️ Tabla: `inventario_ajustes`

| Campo               | Tipo         | Restricciones                          |
| ------------------- | ------------ | -------------------------------------- |
| `id_ajuste`         | SERIAL       | PRIMARY KEY                            |
| `id_producto`       | INT          | FOREIGN KEY → `productos(id_producto)` |
| `motivo`            | VARCHAR(100) | Opcional                               |
| `cantidad_ajustada` | INT          | Obligatorio                            |
| `tipo_ajuste`       | VARCHAR(20)  | CHECK (IN 'incremento', 'reduccion')   |
| `fecha_ajuste`      | TIMESTAMP    | DEFAULT CURRENT\_TIMESTAMP             |

---

## 📊 Tabla: `estadisticas`

| Campo                    | Tipo      | Restricciones                        |
| ------------------------ | --------- | ------------------------------------ |
| `id_estadistica`         | SERIAL    | PRIMARY KEY                          |
| `id_usuario`             | INT       | FOREIGN KEY → `usuarios(id_usuario)` |
| `total_ventas`           | INT       | DEFAULT 0                            |
| `total_compras`          | INT       | DEFAULT 0                            |
| `productos_mas_vendidos` | TEXT      | Opcional                             |
| `actualizado`            | TIMESTAMP | DEFAULT CURRENT\_TIMESTAMP           |

---

