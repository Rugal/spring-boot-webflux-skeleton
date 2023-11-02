DROP TABLE IF EXISTS tag;

CREATE TABLE tag
(
  id        INT AUTO_INCREMENT PRIMARY KEY,
  name      VARCHAR(255) NOT NULL,
  create_at BIGINT,
  update_at BIGINT
);

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
