DELETE
FROM PUBLIC.TACO_ORDER_TACOS;
DELETE
FROM PUBLIC.TACO_INGREDIENTS;
DELETE
FROM PUBLIC.TACO;
DELETE
FROM PUBLIC.TACO_ORDER;
DELETE
FROM PUBLIC.INGREDIENT;
INSERT INTO PUBLIC.INGREDIENT(ID, NAME, TYPE)
VALUES ('FLTO', 'Flour Tortilla', 0),
  ('COTO', 'Corn Tortilla', 0)
  ,
  ('GRBF', 'Ground Beef', 1)
  ,
  ('CARN', 'Carnitas', 1)
  ,
  ('TMTO', 'Diced Tomatoes', 2)
  ,
  ('LETC', 'Lettuce', 2)
  ,
  ('CHED', 'Cheddar', 3)
  ,
  ('JACK', 'Monterrey Jack', 3)
  ,
  ('SLSA', 'Salsa', 4)
  ,
  ('SRCR', 'Sour Cream', 4);