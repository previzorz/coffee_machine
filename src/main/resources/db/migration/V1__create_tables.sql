CREATE TABLE recipes
(
    id BIGSERIAL PRIMARY KEY
);

CREATE TABLE coffees
(
    id        BIGSERIAL PRIMARY KEY,
    name      VARCHAR(255) NOT NULL UNIQUE,
    recipe_id BIGINT       NOT NULL,
    CONSTRAINT fk_recipe FOREIGN KEY (recipe_id) REFERENCES recipes (id) ON DELETE CASCADE
);

CREATE TABLE coffee_order_statistics
(
    id               BIGSERIAL PRIMARY KEY,
    name             VARCHAR(255) NOT NULL UNIQUE,
    total_orders     INT          NOT NULL CHECK (total_orders >= 0),
    first_order_date TIMESTAMP    NOT NULL,
    CONSTRAINT uq_name UNIQUE (name)
);

CREATE TABLE ingredients
(
    id   BIGSERIAL PRIMARY KEY,
    name VARCHAR(255) NOT NULL UNIQUE
);

CREATE TABLE ingredient_proportions
(
    id            BIGSERIAL PRIMARY KEY,
    proportion    INT    NOT NULL,
    recipe_id     BIGINT NOT NULL,
    ingredient_id BIGINT NOT NULL,
    FOREIGN KEY (recipe_id) REFERENCES recipes (id) ON DELETE CASCADE,
    FOREIGN KEY (ingredient_id) REFERENCES ingredients (id) ON DELETE CASCADE,
    CONSTRAINT unique_recipe_ingredient UNIQUE (recipe_id, ingredient_id)
);

CREATE TABLE ingredient_quantities
(
    id            BIGSERIAL PRIMARY KEY,
    ingredient_id BIGINT NOT NULL,
    quantity      INT    NOT NULL,
    FOREIGN KEY (ingredient_id) REFERENCES ingredients (id) ON DELETE CASCADE
);