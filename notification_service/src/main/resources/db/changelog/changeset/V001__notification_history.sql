CREATE TABLE IF NOT EXISTS notification_history (
    id BIGINT PRIMARY KEY,
    order_number VARCHAR(100) NOT NULL,
    customer_id BIGINT NOT NULL,
    status VARCHAR(50) NOT NULL,
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP
);
