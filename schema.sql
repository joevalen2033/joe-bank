--Crear base de datos en postgres con el nombre "bancaonline" y luego ejecutar este script para crear las tablas necesarias.
CREATE TABLE IF NOT EXISTS bancos
(
    id BIGSERIAL PRIMARY KEY,
    name VARCHAR(20) NOT NULL,
    location VARCHAR(200) NOT NULL
);

CREATE TABLE IF NOT EXISTS cuentas (
    id BIGSERIAL PRIMARY KEY,
    account_number BIGINT NOT NULL UNIQUE,
    created_at TIMESTAMP NOT NULL DEFAULT NOW(),
    balance DOUBLE PRECISION NOT NULL DEFAULT 0.0,
    bank_id BIGINT NOT NULL
);


CREATE TABLE transacciones (
    id BIGSERIAL PRIMARY KEY, -- Identificador único autoincremental
    account_number BIGINT NOT NULL, -- Número de cuenta asociado
    type VARCHAR(20) NOT NULL, -- Tipo de transacción: INCOME u OUTCOME como texto
    amount NUMERIC(15, 2) NOT NULL DEFAULT 0.0, -- Monto de la transacción, por defecto 0.0
    is_interbank BOOLEAN NOT NULL DEFAULT FALSE, -- Indica si es interbancaria, por defecto false
    created_at VARCHAR(50) NOT NULL, -- Fecha de creación
    tax_amount NUMERIC(15, 2) -- Monto de impuestos, opcional (puede ser null)
);

CREATE TABLE usuarios
(
    id       SERIAL PRIMARY KEY,
    username VARCHAR(100) NOT NULL UNIQUE,
    password TEXT         NOT NULL,
    role     VARCHAR(50)  NOT NULL
);