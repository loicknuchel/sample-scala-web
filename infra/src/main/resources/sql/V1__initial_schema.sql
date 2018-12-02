CREATE TABLE users (
  id         VARCHAR(36)  NOT NULL PRIMARY KEY,
  first_name VARCHAR(30)  NOT NULL,
  last_name  VARCHAR(30)  NOT NULL,
  email      VARCHAR(100) NOT NULL,
  created    TIMESTAMP    NOT NULL
);
