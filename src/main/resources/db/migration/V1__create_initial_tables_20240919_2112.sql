CREATE TABLE drink (
    id         SERIAL PRIMARY KEY,
    name       VARCHAR(50) UNIQUE,
    created_at TIMESTAMP,
    updated_at TIMESTAMP
);

CREATE TABLE ingredient (
    id                 SERIAL PRIMARY KEY,
    name               VARCHAR(50) UNIQUE,
    remaining_quantity INT,
    created_at         TIMESTAMP,
    updated_at         TIMESTAMP
);

CREATE TABLE drink_ingredient (
    drink_id      INT,
    ingredient_id INT,
    quantity      INT,
    created_at    TIMESTAMP,
    updated_at    TIMESTAMP,
    FOREIGN KEY (drink_id) REFERENCES drink (id),
    FOREIGN KEY (ingredient_id) REFERENCES ingredient (id),
    UNIQUE (drink_id, ingredient_id)
);

CREATE TABLE drink_statistics (
    id          SERIAL PRIMARY KEY,
    drink_id    INT,
    order_count INT,
    created_at  TIMESTAMP,
    updated_at  TIMESTAMP,
    FOREIGN KEY (drink_id) REFERENCES drink (id)
);
