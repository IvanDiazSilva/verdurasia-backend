# VerdurasIA — Backend

API REST para la gestión de productos, categorías, clientes, pedidos y ofertas de una verdulería con asistencia de IA.

---

## Descripción

Backend desarrollado con **Spring Boot 3.3.2** y **Java 21**. Expone una API REST con documentación automática via Swagger UI. Utiliza **PostgreSQL** como base de datos y **Flyway** para las migraciones.

---

## Requisitos

| Herramienta | Versión mínima |
|-------------|----------------|
| Java JDK    | 21             |
| Maven       | 3.9+ (o usar `mvnw`) |
| PostgreSQL  | 14+            |
| Docker (opcional) | 24+     |

---

## Configuración de base de datos

El perfil `dev` (activo por defecto) espera una instancia de PostgreSQL en:

| Parámetro | Valor |
|-----------|-------|
| Host      | `localhost` |
| Puerto    | `5433` |
| Base de datos | `verdurasia_db` |
| Usuario   | `verdurasia` |
| Contraseña | `verdurasia` |

Puedes levantar la base de datos con Docker usando el `docker-compose.yml` en la raíz del repositorio:

```bash
docker-compose up -d
```

---

## Cómo arrancar el backend

### Con Maven Wrapper (recomendado)

```bash
# Desde la carpeta backend/
./mvnw spring-boot:run          # Linux / macOS
mvnw.cmd spring-boot:run        # Windows
```

### Con Maven instalado

```bash
mvn spring-boot:run
```

### Construir el JAR y ejecutarlo

```bash
./mvnw clean package -DskipTests
java -jar target/verdurasia-backend-0.0.1-SNAPSHOT.jar
```

---

## URL local del backend

| Recurso | URL |
|---------|-----|
| API base | `http://localhost:8080` |
| Swagger UI | `http://localhost:8080/api/swagger-ui.html` |
| OpenAPI JSON | `http://localhost:8080/api/v3/api-docs` |

---

## Endpoints principales

### Categorias — `/api/categorias`

| Método | Ruta | Descripción |
|--------|------|-------------|
| `GET` | `/api/categorias` | Listar categorías (paginado) |
| `GET` | `/api/categorias/{id}` | Obtener categoría por ID |
| `POST` | `/api/categorias` | Crear categoría |
| `PUT` | `/api/categorias/{id}` | Actualizar categoría |
| `DELETE` | `/api/categorias/{id}` | Eliminar categoría |

### Productos — `/api/productos`

| Método | Ruta | Descripción |
|--------|------|-------------|
| `GET` | `/api/productos` | Listar/buscar productos (`?nombre=`, `?categoriaId=`) |
| `GET` | `/api/productos/{id}` | Obtener producto por ID |
| `POST` | `/api/productos` | Crear producto |
| `PATCH` | `/api/productos/{id}` | Actualizar producto parcialmente |
| `DELETE` | `/api/productos/{id}` | Eliminar producto |

### Clientes — `/api/clientes`

| Método | Ruta | Descripción |
|--------|------|-------------|
| `GET` | `/api/clientes` | Listar clientes (paginado) |
| `GET` | `/api/clientes/{id}` | Obtener cliente por ID |
| `POST` | `/api/clientes` | Crear cliente |
| `PATCH` | `/api/clientes/{id}` | Actualizar cliente parcialmente |
| `DELETE` | `/api/clientes/{id}` | Eliminar cliente |

### Pedidos — `/api/pedidos`

| Método | Ruta | Descripción |
|--------|------|-------------|
| `GET` | `/api/pedidos` | Listar pedidos (paginado) |
| `GET` | `/api/pedidos/{id}` | Obtener pedido por ID |
| `POST` | `/api/pedidos` | Crear pedido |
| `PATCH` | `/api/pedidos/{id}/estado` | Cambiar estado del pedido |
| `DELETE` | `/api/pedidos/{id}` | Eliminar pedido |

### Ofertas — `/api/ofertas`

| Método | Ruta | Descripción |
|--------|------|-------------|
| `GET` | `/api/ofertas` | Listar ofertas (paginado) |
| `GET` | `/api/ofertas/vigentes` | Listar ofertas activas |
| `GET` | `/api/ofertas/{id}` | Obtener oferta por ID |
| `POST` | `/api/ofertas` | Crear oferta |
| `PATCH` | `/api/ofertas/{id}` | Actualizar oferta parcialmente |
| `DELETE` | `/api/ofertas/{id}` | Eliminar oferta |

---

## Estructura del proyecto

```
backend/
├── src/main/java/com/verdurasia/
│   ├── config/          # Configuración CORS y otros beans
│   ├── controller/      # Controladores REST
│   ├── dto/             # Data Transfer Objects
│   ├── entity/          # Entidades JPA
│   ├── exception/       # Manejo global de excepciones
│   ├── repository/      # Repositorios Spring Data
│   └── service/         # Lógica de negocio
├── src/main/resources/
│   ├── application.yml          # Configuración base
│   ├── application-dev.yml      # Configuración perfil dev
│   └── db/migration/            # Scripts Flyway
├── pom.xml
└── mvnw / mvnw.cmd
```

---

## Tecnologías

- Spring Boot 3.3.2
- Spring Data JPA / Hibernate
- PostgreSQL + Flyway
- Lombok
- SpringDoc OpenAPI (Swagger UI)
- Jakarta Bean Validation
