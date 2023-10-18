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

-- school database

DROP TABLE IF EXISTS registration;
DROP TABLE IF EXISTS course;
DROP TABLE IF EXISTS student;

CREATE TABLE student
(
  id        INT AUTO_INCREMENT PRIMARY KEY,
  name      VARCHAR(100) NOT NULL,
  create_at BIGINT,
  update_at BIGINT
);

CREATE TABLE course
(
  id        INT AUTO_INCREMENT PRIMARY KEY,
  name      VARCHAR(100) NOT NULL,
  create_at BIGINT,
  update_at BIGINT
);

CREATE TABLE registration
(
  id         INT AUTO_INCREMENT PRIMARY KEY,
  student_id INT NOT NULL REFERENCES student(id),
  course_id  INT NOT NULL REFERENCES course(id),
  score      INT,
  create_at  BIGINT,
  update_at  BIGINT
);


INSERT INTO student(name, create_at, update_at)
VALUES ('Orochi', 1697300106, 1697618173),
       ('Rugal', 1697300106, 1697618173),
       ('Bernstein', 1697300106, 1697618173),
       ('Tenjin', 1697300106, 1697618173),
       ('Descend', 1697300106, 1697618173);

INSERT INTO course(name, create_at, update_at)
VALUES ('Math', 1697300106, 1697618173),
       ('Database', 1697300106, 1697618173),
       ('Data Structure', 1697300106, 1697618173),
       ('Software Engineer', 1697300106, 1697618173),
       ('English', 1697300106, 1697618173);

INSERT INTO registration(student_id, course_id, score, create_at, update_at)
VALUES (1, 1, 100, 1697300106, 1697618173),
       (1, 2, 70, 1697300106, 1697618173),
       (1, 3, 95, 1697300106, 1697618173),
       (2, 1, 60, 1697300106, 1697618173),
       (2, 4, 80, 1697300106, 1697618173),
       (2, 5, 67, 1697302106, 1697618173),
       (3, 1, 60, 1697300106, 1697618173),
       (3, 2, 20, 1697300106, 1697618173),
       (3, 3, 30, 1697300106, 1697618173),
       (3, 4, 99, 1697300106, 1697620323),
       (4, 2, 13, 1697300106, 1697618173),
       (4, 3, 43, 1697300106, 1697618173),
       (4, 4, 87, 1697300106, 1697630173),
       (5, 1, 23, 1697300106, 1697618173),
       (5, 2, 77, 1697300106, 1697644173),
       (5, 4, 82, 1697300106, 1697618223),
       (5, 5, 97, 1697300106, 1697618173);
