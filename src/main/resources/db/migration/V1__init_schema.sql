-- ============================================================
-- V1 — Schema inicial de VerdurasIA
-- ============================================================

-- Clientes
CREATE TABLE IF NOT EXISTS clientes (
    id          BIGSERIAL PRIMARY KEY,
    nombre      VARCHAR(150) NOT NULL,
    email       VARCHAR(255) NOT NULL UNIQUE,
    telefono    VARCHAR(30),
    direccion   TEXT,
    activo      BOOLEAN      NOT NULL DEFAULT TRUE,
    created_at  TIMESTAMP    NOT NULL DEFAULT NOW(),
    updated_at  TIMESTAMP    NOT NULL DEFAULT NOW()
);

-- Categorías de producto
CREATE TABLE IF NOT EXISTS categorias (
    id          BIGSERIAL PRIMARY KEY,
    nombre      VARCHAR(100) NOT NULL UNIQUE,
    descripcion TEXT
);

-- Productos
CREATE TABLE IF NOT EXISTS productos (
    id           BIGSERIAL PRIMARY KEY,
    nombre       VARCHAR(150)   NOT NULL,
    descripcion  TEXT,
    precio       NUMERIC(10, 2) NOT NULL CHECK (precio >= 0),
    stock        INTEGER        NOT NULL DEFAULT 0 CHECK (stock >= 0),
    unidad       VARCHAR(30)    NOT NULL DEFAULT 'kg',
    activo       BOOLEAN        NOT NULL DEFAULT TRUE,
    categoria_id BIGINT         REFERENCES categorias(id) ON DELETE SET NULL,
    created_at   TIMESTAMP      NOT NULL DEFAULT NOW(),
    updated_at   TIMESTAMP      NOT NULL DEFAULT NOW()
);

-- Pedidos
CREATE TABLE IF NOT EXISTS pedidos (
    id          BIGSERIAL PRIMARY KEY,
    cliente_id  BIGINT         NOT NULL REFERENCES clientes(id) ON DELETE RESTRICT,
    estado      VARCHAR(30)    NOT NULL DEFAULT 'PENDIENTE',
    total       NUMERIC(12, 2) NOT NULL DEFAULT 0,
    notas       TEXT,
    created_at  TIMESTAMP      NOT NULL DEFAULT NOW(),
    updated_at  TIMESTAMP      NOT NULL DEFAULT NOW()
);

-- Líneas de pedido
CREATE TABLE IF NOT EXISTS pedido_items (
    id          BIGSERIAL PRIMARY KEY,
    pedido_id   BIGINT         NOT NULL REFERENCES pedidos(id) ON DELETE CASCADE,
    producto_id BIGINT         NOT NULL REFERENCES productos(id) ON DELETE RESTRICT,
    cantidad    INTEGER        NOT NULL CHECK (cantidad > 0),
    precio_unit NUMERIC(10, 2) NOT NULL,
    subtotal    NUMERIC(12, 2) GENERATED ALWAYS AS (cantidad * precio_unit) STORED
);

-- Ofertas
CREATE TABLE IF NOT EXISTS ofertas (
    id           BIGSERIAL PRIMARY KEY,
    nombre       VARCHAR(150)   NOT NULL,
    descripcion  TEXT,
    descuento    NUMERIC(5, 2)  NOT NULL CHECK (descuento > 0 AND descuento <= 100),
    tipo         VARCHAR(30)    NOT NULL DEFAULT 'PORCENTAJE',
    fecha_inicio DATE           NOT NULL,
    fecha_fin    DATE           NOT NULL,
    activa       BOOLEAN        NOT NULL DEFAULT TRUE,
    producto_id  BIGINT         REFERENCES productos(id) ON DELETE CASCADE,
    created_at   TIMESTAMP      NOT NULL DEFAULT NOW(),
    updated_at   TIMESTAMP      NOT NULL DEFAULT NOW(),
    CONSTRAINT chk_fechas CHECK (fecha_fin >= fecha_inicio)
);

-- Índices útiles
CREATE INDEX IF NOT EXISTS idx_pedidos_cliente  ON pedidos(cliente_id);
CREATE INDEX IF NOT EXISTS idx_pedidos_estado   ON pedidos(estado);
CREATE INDEX IF NOT EXISTS idx_productos_cat    ON productos(categoria_id);
CREATE INDEX IF NOT EXISTS idx_ofertas_producto ON ofertas(producto_id);
CREATE INDEX IF NOT EXISTS idx_ofertas_activa   ON ofertas(activa, fecha_inicio, fecha_fin);
