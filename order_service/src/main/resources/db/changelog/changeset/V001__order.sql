CREATE TABLE IF NOT EXISTS orders (
    id BIGINT PRIMARY KEY,
    order_number VARCHAR(100) NOT NULL,
    customer_id BIGINT,
    order_date TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    status VARCHAR(50) NOT NULL,
    FOREIGN KEY (customer_id) REFERENCES Customer(id)
);
