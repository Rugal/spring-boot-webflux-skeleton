DROP TABLE IF EXISTS tag;

CREATE TABLE tag
(
  id   INT AUTO_INCREMENT PRIMARY KEY,
  name VARCHAR(255) NOT NULL
);

INSERT INTO tag(name)
VALUES ('BlueRay'),
       ('Rugal'),
       ('Bernstein'),
       ('Tenjin'),
       ('Descend');
