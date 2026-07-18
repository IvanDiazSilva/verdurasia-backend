-- V2 — Categorías semilla para desarrollo
INSERT INTO categorias (nombre, descripcion) VALUES
  ('Frutas',      'Frutas frescas de temporada'),
  ('Verduras',    'Verduras y hortalizas frescas'),
  ('Legumbres',   'Legumbres secas y en conserva'),
  ('Hierbas',     'Hierbas aromáticas y condimentos'),
  ('Ecológico',   'Productos con certificación ecológica')
ON CONFLICT (nombre) DO NOTHING;
