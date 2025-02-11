INSERT INTO ingredients (name)
VALUES ('кофе'),
       ('вода'),
       ('молоко'),
       ('сахар')
ON CONFLICT (name) DO NOTHING;

INSERT INTO recipes (id)
VALUES (DEFAULT);

INSERT INTO recipes (id)
VALUES (DEFAULT);

INSERT INTO recipes (id)
VALUES (DEFAULT);

INSERT INTO ingredient_proportions (proportion, recipe_id, ingredient_id)
VALUES (1, (SELECT id FROM recipes WHERE id = 1), (SELECT id FROM ingredients WHERE name = 'кофе'));

INSERT INTO ingredient_proportions (proportion, recipe_id, ingredient_id)
VALUES (1, (SELECT id FROM recipes WHERE id = 2), (SELECT id FROM ingredients WHERE name = 'кофе')),
       (2, (SELECT id FROM recipes WHERE id = 2), (SELECT id FROM ingredients WHERE name = 'вода'));

INSERT INTO ingredient_proportions (proportion, recipe_id, ingredient_id)
VALUES (1, (SELECT id FROM recipes WHERE id = 3), (SELECT id FROM ingredients WHERE name = 'кофе')),
       (1, (SELECT id FROM recipes WHERE id = 3), (SELECT id FROM ingredients WHERE name = 'молоко')),
       (1, (SELECT id FROM recipes WHERE id = 3), (SELECT id FROM ingredients WHERE name = 'сахар'));

INSERT INTO coffees (name, recipe_id)
VALUES ('эспрессо', (SELECT id FROM recipes WHERE id = 1)),
       ('американо', (SELECT id FROM recipes WHERE id = 2)),
       ('капучино', (SELECT id FROM recipes WHERE id = 3));