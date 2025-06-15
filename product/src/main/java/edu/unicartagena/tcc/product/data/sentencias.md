```bash
-H "Authorization: Bearer eyJhbGciOiJIUzI1NiJ9..."
```

---

### 1. Crear producto

**POST** `/api/productos/crear`

```bash
curl -X POST http://localhost:8083/api/productos/crear \
-H "Content-Type: application/json" \
-H "Authorization: Bearer eyJhbGciOiJIUzI1NiJ9..." \
-d '{
  "nombre": "Cerveza",
  "descripcion": "Refrescante",
  "precio": 12.5
}'
```

---

### 2. Obtener productos paginados

**GET** `/api/productos?page=0&size=5&sort=id,desc`

```bash
curl -X GET "http://localhost:8083/api/productos?page=0&size=5&sort=id,desc" \
-H "Authorization: Bearer eyJhbGciOiJIUzI1NiJ9..."
```

---

### 3. Obtener todos los productos (sin paginación)

**GET** `/api/productos/lista`

```bash
curl -X GET http://localhost:8083/api/productos/lista \
-H "Authorization: Bearer eyJhbGciOiJIUzI1NiJ9..."
```

---

### 4. Obtener producto por ID

**GET** `/api/productos/lista/{id}`

```bash
curl -X GET http://localhost:8083/api/productos/lista/1 \
-H "Authorization: Bearer eyJhbGciOiJIUzI1NiJ9..."
```

---

### 5. Actualizar producto

**PUT** `/api/productos/{id}`

```bash
curl -X PUT http://localhost:8083/api/productos/1 \
-H "Content-Type: application/json" \
-H "Authorization: Bearer eyJhbGciOiJIUzI1NiJ9..." \
-d '{
  "nombre": "Cerveza Actualizada",
  "descripcion": "Nueva descripción",
  "precio": 14.0
}'
```

---

### 6. Eliminar producto

**DELETE** `/api/productos/{id}`

```bash
curl -X DELETE http://localhost:8083/api/productos/1 \
-H "Authorization: Bearer eyJhbGciOiJIUzI1NiJ9..."
```

---

### 7. Buscar por nombre

**GET** `/api/productos/buscar?nombre=Cerveza`

```bash
curl -X GET "http://localhost:8083/api/productos/buscar?nombre=Cerveza" \
-H "Authorization: Bearer eyJhbGciOiJIUzI1NiJ9..."
```

---

### 8. Buscar por rango de precio

**GET** `/api/productos/precio-rango?precioMin=10&precioMax=20`

```bash
curl -X GET "http://localhost:8083/api/productos/precio-rango?precioMin=10&precioMax=20" \
-H "Authorization: Bearer eyJhbGciOiJIUzI1NiJ9..."
```

---
