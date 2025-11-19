# API de Gestión de Fábrica de Vehículos

API REST completa para gestionar una fábrica de vehículos con MongoDB Atlas. Desarrollada con Java 21 y Spring Boot 3.3.

## Características

- ✅ CRUD completo para Fábricas, Plantas y Vehículos
- ✅ Validaciones de integridad referencial
- ✅ Manejo centralizado de excepciones
- ✅ Conexión segura a MongoDB Atlas
- ✅ DTOs para transferencia de datos
- ✅ Logging detallado
- ✅ Respuestas HTTP estándar

## Estructura del Proyecto

```
src/
├── main/
│   ├── java/com/vehiculos/
│   │   ├── VehiculosApplication.java
│   │   ├── config/
│   │   ├── controller/
│   │   │   ├── FabricaController.java
│   │   │   ├── PlantaController.java
│   │   │   └── VehiculoController.java
│   │   ├── service/
│   │   │   ├── FabricaService.java
│   │   │   ├── PlantaService.java
│   │   │   └── VehiculoService.java
│   │   ├── repository/
│   │   │   ├── FabricaRepository.java
│   │   │   ├── PlantaRepository.java
│   │   │   └── VehiculoRepository.java
│   │   ├── model/
│   │   │   ├── Fabrica.java
│   │   │   ├── Planta.java
│   │   │   └── Vehiculo.java
│   │   ├── dto/
│   │   │   ├── FabricaDTO.java
│   │   │   ├── PlantaDTO.java
│   │   │   └── VehiculoDTO.java
│   │   └── exception/
│   │       ├── GlobalExceptionHandler.java
│   │       ├── ResourceNotFoundException.java
│   │       ├── InvalidOperationException.java
│   │       └── ErrorResponse.java
│   └── resources/
│       └── application.yml
└── pom.xml
```

## Requisitos Previos

- Java 21 o superior
- Maven 3.6+
- Conexión a Internet (para MongoDB Atlas)

## Configuración

La conexión a MongoDB Atlas ya está configurada en `application.yml`:

```yaml
spring:
  data:
    mongodb:
      uri: mongodb+srv://edben1407_db_user:eJH3EXezayUUlrdH@vehiculos.oehm4tc.mongodb.net/?appName=Vehiculos
      database: vehiculos
```

## Instalación y Ejecución

### 1. Compilar el proyecto

```bash
mvn clean install
```

### 2. Ejecutar la aplicación

```bash
mvn spring-boot:run
```

O:

```bash
java -jar target/vehiculos-api-1.0.0.jar
```

La aplicación estará disponible en: `http://localhost:8080/api/v1`

## Entidades

### Fabrica
```json
{
  "id": "ObjectId generado automáticamente",
  "nombre": "string (requerido)",
  "pais": "string (requerido)",
  "plantasIds": ["array de IDs de plantas"]
}
```

### Planta
```json
{
  "id": "ObjectId generado automáticamente",
  "nombre": "string (requerido)",
  "ubicacion": "string (requerido)",
  "fabricaId": "string (requerido - ID de la fábrica)"
}
```

### Vehiculo
```json
{
  "id": "ObjectId generado automáticamente",
  "marca": "string (requerido)",
  "modelo": "string (requerido)",
  "tipoLlantas": "string (requerido)",
  "numeroPuertas": "integer (requerido, > 0)",
  "plantaId": "string (requerido - ID de la planta)"
}
```

## Ejemplos de Requests para Postman

### 1. CREAR FÁBRICA

**Endpoint:** `POST /api/v1/fabricas`

**Headers:**
```
Content-Type: application/json
```

**Body:**
```json
{
  "nombre": "Fábrica Toyota México",
  "pais": "México"
}
```

**Response (201 Created):**
```json
{
  "id": "673a5c2e1f2a3b4c5d6e7f8g",
  "nombre": "Fábrica Toyota México",
  "pais": "México",
  "plantasIds": []
}
```

---

### 2. OBTENER TODAS LAS FÁBRICAS

**Endpoint:** `GET /api/v1/fabricas`

**Response (200 OK):**
```json
[
  {
    "id": "673a5c2e1f2a3b4c5d6e7f8g",
    "nombre": "Fábrica Toyota México",
    "pais": "México",
    "plantasIds": ["673a5c2e1f2a3b4c5d6e7f9h"]
  }
]
```

---

### 3. OBTENER FÁBRICA POR ID

**Endpoint:** `GET /api/v1/fabricas/{id}`

**Example:** `GET /api/v1/fabricas/673a5c2e1f2a3b4c5d6e7f8g`

**Response (200 OK):**
```json
{
  "id": "673a5c2e1f2a3b4c5d6e7f8g",
  "nombre": "Fábrica Toyota México",
  "pais": "México",
  "plantasIds": ["673a5c2e1f2a3b4c5d6e7f9h"]
}
```

---

### 4. ACTUALIZAR FÁBRICA

**Endpoint:** `PUT /api/v1/fabricas/{id}`

**Example:** `PUT /api/v1/fabricas/673a5c2e1f2a3b4c5d6e7f8g`

**Headers:**
```
Content-Type: application/json
```

**Body:**
```json
{
  "nombre": "Fábrica Toyota México Actualizada",
  "pais": "México"
}
```

**Response (200 OK):**
```json
{
  "id": "673a5c2e1f2a3b4c5d6e7f8g",
  "nombre": "Fábrica Toyota México Actualizada",
  "pais": "México",
  "plantasIds": ["673a5c2e1f2a3b4c5d6e7f9h"]
}
```

---

### 5. ELIMINAR FÁBRICA

**Endpoint:** `DELETE /api/v1/fabricas/{id}`

**Example:** `DELETE /api/v1/fabricas/673a5c2e1f2a3b4c5d6e7f8g`

**Note:** No se puede eliminar si tiene plantas asociadas.

**Response (204 No Content)**

---

## PLANTAS

### 6. CREAR PLANTA

**Endpoint:** `POST /api/v1/plantas`

**Prerequisites:** La fábrica debe existir

**Headers:**
```
Content-Type: application/json
```

**Body:**
```json
{
  "nombre": "Planta Monterrey",
  "ubicacion": "Monterrey, NL",
  "fabricaId": "673a5c2e1f2a3b4c5d6e7f8g"
}
```

**Response (201 Created):**
```json
{
  "id": "673a5c2e1f2a3b4c5d6e7f9h",
  "nombre": "Planta Monterrey",
  "ubicacion": "Monterrey, NL",
  "fabricaId": "673a5c2e1f2a3b4c5d6e7f8g"
}
```

---

### 7. OBTENER TODAS LAS PLANTAS

**Endpoint:** `GET /api/v1/plantas`

**Response (200 OK):**
```json
[
  {
    "id": "673a5c2e1f2a3b4c5d6e7f9h",
    "nombre": "Planta Monterrey",
    "ubicacion": "Monterrey, NL",
    "fabricaId": "673a5c2e1f2a3b4c5d6e7f8g"
  }
]
```

---

### 8. OBTENER PLANTA POR ID

**Endpoint:** `GET /api/v1/plantas/{id}`

**Example:** `GET /api/v1/plantas/673a5c2e1f2a3b4c5d6e7f9h`

**Response (200 OK):**
```json
{
  "id": "673a5c2e1f2a3b4c5d6e7f9h",
  "nombre": "Planta Monterrey",
  "ubicacion": "Monterrey, NL",
  "fabricaId": "673a5c2e1f2a3b4c5d6e7f8g"
}
```

---

### 9. OBTENER PLANTAS DE UNA FÁBRICA

**Endpoint:** `GET /api/v1/plantas/fabrica/{fabricaId}`

**Example:** `GET /api/v1/plantas/fabrica/673a5c2e1f2a3b4c5d6e7f8g`

**Response (200 OK):**
```json
[
  {
    "id": "673a5c2e1f2a3b4c5d6e7f9h",
    "nombre": "Planta Monterrey",
    "ubicacion": "Monterrey, NL",
    "fabricaId": "673a5c2e1f2a3b4c5d6e7f8g"
  }
]
```

---

### 10. ACTUALIZAR PLANTA

**Endpoint:** `PUT /api/v1/plantas/{id}`

**Example:** `PUT /api/v1/plantas/673a5c2e1f2a3b4c5d6e7f9h`

**Headers:**
```
Content-Type: application/json
```

**Body:**
```json
{
  "nombre": "Planta Monterrey Actualizada",
  "ubicacion": "Monterrey, NL"
}
```

**Response (200 OK):**
```json
{
  "id": "673a5c2e1f2a3b4c5d6e7f9h",
  "nombre": "Planta Monterrey Actualizada",
  "ubicacion": "Monterrey, NL",
  "fabricaId": "673a5c2e1f2a3b4c5d6e7f8g"
}
```

---

### 11. ELIMINAR PLANTA

**Endpoint:** `DELETE /api/v1/plantas/{id}`

**Example:** `DELETE /api/v1/plantas/673a5c2e1f2a3b4c5d6e7f9h`

**Note:** No se puede eliminar si tiene vehículos asociados.

**Response (204 No Content)**

---

## VEHÍCULOS

### 12. CREAR VEHÍCULO

**Endpoint:** `POST /api/v1/vehiculos`

**Prerequisites:** La planta debe existir

**Headers:**
```
Content-Type: application/json
```

**Body:**
```json
{
  "marca": "Toyota",
  "modelo": "Corolla",
  "tipoLlantas": "Michelin Defender",
  "numeroPuertas": 4,
  "plantaId": "673a5c2e1f2a3b4c5d6e7f9h"
}
```

**Response (201 Created):**
```json
{
  "id": "673a5c2e1f2a3b4c5d6e7fai",
  "marca": "Toyota",
  "modelo": "Corolla",
  "tipoLlantas": "Michelin Defender",
  "numeroPuertas": 4,
  "plantaId": "673a5c2e1f2a3b4c5d6e7f9h"
}
```

---

### 13. OBTENER TODOS LOS VEHÍCULOS

**Endpoint:** `GET /api/v1/vehiculos`

**Response (200 OK):**
```json
[
  {
    "id": "673a5c2e1f2a3b4c5d6e7fai",
    "marca": "Toyota",
    "modelo": "Corolla",
    "tipoLlantas": "Michelin Defender",
    "numeroPuertas": 4,
    "plantaId": "673a5c2e1f2a3b4c5d6e7f9h"
  }
]
```

---

### 14. OBTENER VEHÍCULO POR ID

**Endpoint:** `GET /api/v1/vehiculos/{id}`

**Example:** `GET /api/v1/vehiculos/673a5c2e1f2a3b4c5d6e7fai`

**Response (200 OK):**
```json
{
  "id": "673a5c2e1f2a3b4c5d6e7fai",
  "marca": "Toyota",
  "modelo": "Corolla",
  "tipoLlantas": "Michelin Defender",
  "numeroPuertas": 4,
  "plantaId": "673a5c2e1f2a3b4c5d6e7f9h"
}
```

---

### 15. OBTENER VEHÍCULOS DE UNA PLANTA

**Endpoint:** `GET /api/v1/vehiculos/planta/{plantaId}`

**Example:** `GET /api/v1/vehiculos/planta/673a5c2e1f2a3b4c5d6e7f9h`

**Response (200 OK):**
```json
[
  {
    "id": "673a5c2e1f2a3b4c5d6e7fai",
    "marca": "Toyota",
    "modelo": "Corolla",
    "tipoLlantas": "Michelin Defender",
    "numeroPuertas": 4,
    "plantaId": "673a5c2e1f2a3b4c5d6e7f9h"
  }
]
```

---

### 16. OBTENER VEHÍCULOS POR MARCA

**Endpoint:** `GET /api/v1/vehiculos/marca/{marca}`

**Example:** `GET /api/v1/vehiculos/marca/Toyota`

**Response (200 OK):**
```json
[
  {
    "id": "673a5c2e1f2a3b4c5d6e7fai",
    "marca": "Toyota",
    "modelo": "Corolla",
    "tipoLlantas": "Michelin Defender",
    "numeroPuertas": 4,
    "plantaId": "673a5c2e1f2a3b4c5d6e7f9h"
  }
]
```

---

### 17. ACTUALIZAR VEHÍCULO

**Endpoint:** `PUT /api/v1/vehiculos/{id}`

**Example:** `PUT /api/v1/vehiculos/673a5c2e1f2a3b4c5d6e7fai`

**Headers:**
```
Content-Type: application/json
```

**Body:**
```json
{
  "marca": "Toyota",
  "modelo": "Corolla XLE",
  "tipoLlantas": "Goodyear Assurance",
  "numeroPuertas": 4
}
```

**Response (200 OK):**
```json
{
  "id": "673a5c2e1f2a3b4c5d6e7fai",
  "marca": "Toyota",
  "modelo": "Corolla XLE",
  "tipoLlantas": "Goodyear Assurance",
  "numeroPuertas": 4,
  "plantaId": "673a5c2e1f2a3b4c5d6e7f9h"
}
```

---

### 18. ELIMINAR VEHÍCULO

**Endpoint:** `DELETE /api/v1/vehiculos/{id}`

**Example:** `DELETE /api/v1/vehiculos/673a5c2e1f2a3b4c5d6e7fai`

**Response (204 No Content)**

---

## Códigos de Estado HTTP

- **200 OK**: Operación exitosa
- **201 Created**: Recurso creado exitosamente
- **204 No Content**: Eliminación exitosa (sin contenido)
- **400 Bad Request**: Validación fallida
- **404 Not Found**: Recurso no encontrado
- **500 Internal Server Error**: Error del servidor

## Manejo de Errores

Todas las excepciones se devuelven en formato JSON:

```json
{
  "status": 400,
  "message": "Error descriptivo",
  "timestamp": "2024-11-18T10:30:45.123456",
  "path": "/api/v1/plantas"
}
```

## Validaciones de Integridad

1. **Crear Planta**: La fábrica asociada debe existir
2. **Crear Vehículo**: La planta asociada debe existir
3. **Eliminar Fábrica**: No puede tener plantas asociadas
4. **Eliminar Planta**: No puede tener vehículos asociados
5. **Número de Puertas**: Debe ser mayor a 0

## Dependencias Principales

- Spring Boot 3.3.0
- Spring Data MongoDB
- Java 21
- Lombok
- Jakarta Validation

## Notas

- Los IDs se generan automáticamente por MongoDB
- Las fechas se serializan en formato ISO 8601
- La conexión a MongoDB Atlas utiliza la cadena de conexión proporcionada
- El logging se configura a nivel DEBUG para el paquete `com.vehiculos`

## Solución de Problemas

### Error de conexión a MongoDB

Verifica que:
- La URL de conexión sea correcta en `application.yml`
- Las credenciales sean válidas
- Tu dirección IP esté en la lista de acceso de MongoDB Atlas

### Puerto 8080 ocupado

Cambia el puerto en `application.yml`:
```yaml
server:
  port: 8081
```

## Licencia

MIT

## Autor

Desarrollado para la gestión de fábricas de vehículos con MongoDB Atlas.
