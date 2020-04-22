INSERT INTO category (description) VALUES ('American');
INSERT INTO category (description) VALUES ('Italian');
INSERT INTO category (description) VALUES ('Mexican');
INSERT INTO category (description) VALUES ('Fast Food');
INSERT INTO unit_of_measure (description) VALUES ('Teaspoon');
INSERT INTO unit_of_measure (description) VALUES ('Tablespoon');
INSERT INTO unit_of_measure (description) VALUES ('Cup');
INSERT INTO unit_of_measure (description) VALUES ('Pinch');
INSERT INTO unit_of_measure (description) VALUES ('Ounce');

-- Recipes
INSERT INTO recipe (description, difficulty, cook_time, prep_time, servings)
VALUES
('Perfect Guacamole', 'EASY', 10, 15, 8),
('Grilled Chicken Tacos', 'MODERATE', 25, 45, 12);