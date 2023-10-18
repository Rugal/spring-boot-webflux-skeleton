DROP TABLE IF EXISTS tag;

CREATE TABLE tag
(
  id        INT AUTO_INCREMENT PRIMARY KEY,
  name      VARCHAR(255) NOT NULL,
  create_at BIGINT,
  update_at BIGINT
);

INSERT INTO tag(name, create_at, update_at)
VALUES ('BlueRay', 1697300106, 1697618173),
       ('Rugal', 1697300106, 1697618173),
       ('Bernstein', 1697300106, 1697618173),
       ('Tenjin', 1697300106, 1697618173),
       ('Descend', 1697300106, 1697618173);
