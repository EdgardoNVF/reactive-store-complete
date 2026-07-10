CREATE TABLE IF NOT EXISTS products (
    id          BIGSERIAL PRIMARY KEY,
    name        VARCHAR(255) NOT NULL,
    description TEXT,
    price       NUMERIC(10, 2) NOT NULL,
    stock       INTEGER NOT NULL DEFAULT 0,
    active      BOOLEAN NOT NULL DEFAULT TRUE,
    created_at  TIMESTAMP WITH TIME ZONE DEFAULT NOW()
);

INSERT INTO products (name, description, price, stock, active) VALUES
    ('Laptop Pro 15', 'Laptop de alto rendimiento con procesador i9', 1299.99, 50, true),
    ('Teclado Mecánico RGB', 'Teclado mecánico con switches Cherry MX', 89.99, 200, true),
    ('Monitor 4K 27', 'Monitor UHD con panel IPS y 144Hz', 449.99, 30, true),
    ('Mouse Ergonómico', 'Mouse inalámbrico con diseño ergonómico', 49.99, 150, true),
    ('Auriculares Noise-Cancelling', 'Auriculares Bluetooth con cancelación activa de ruido', 199.99, 75, true)
ON CONFLICT DO NOTHING;