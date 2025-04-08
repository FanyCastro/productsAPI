-- src/main/resources/schema.sql
DROP TABLE IF EXISTS products;

CREATE TABLE products (
                          sku VARCHAR(20) PRIMARY KEY,
                          price DECIMAL(19, 2) NOT NULL,
                          description VARCHAR(255) NOT NULL,
                          category VARCHAR(50) NOT NULL
);

-- Índices para mejorar el rendimiento en búsquedas frecuentes
CREATE INDEX idx_products_category ON products(category);
CREATE INDEX idx_products_price ON products(price);
