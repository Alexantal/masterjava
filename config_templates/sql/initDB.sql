DROP TABLE IF EXISTS cities;
DROP TABLE IF EXISTS users;
DROP TABLE IF EXISTS project_groups;
DROP TABLE IF EXISTS projects;
DROP SEQUENCE IF EXISTS city_seq;
DROP SEQUENCE IF EXISTS user_seq;
DROP SEQUENCE IF EXISTS project_seq;
DROP SEQUENCE IF EXISTS project_group_seq;

DROP TYPE IF EXISTS user_flag;
DROP TYPE IF EXISTS group_type;

CREATE TYPE user_flag AS ENUM ('active', 'deleted', 'superuser');
CREATE TYPE group_type AS ENUM ('registering', 'current', 'finished');

CREATE SEQUENCE city_seq START 100000;
CREATE SEQUENCE user_seq START 100000;
CREATE SEQUENCE project_seq START 100000;
CREATE SEQUENCE project_group_seq START 100000;

CREATE TABLE cities (
    id          INTEGER PRIMARY KEY DEFAULT nextval(city_seq),
    city_code   TEXT NOT NULL,
    value       TEXT NOT NULL,
    CONSTRAINT city_code_value_idx UNIQUE (city_code, value)
);

CREATE TABLE users (
    id          INTEGER PRIMARY KEY DEFAULT nextval('user_seq'),
    city_id     INTEGER NOT NULL,
    full_name   TEXT NOT NULL,
    email       TEXT NOT NULL,
    flag        user_flag NOT NULL,
    FOREIGN KEY (city_id) REFERENCES cities(id)
);

CREATE UNIQUE INDEX email_idx ON users (email);

CREATE TABLE  projects (
    id              INTEGER PRIMARY KEY DEFAULT nextval(project_seq),
    project_name    TEXT NOT NULL,
    description     TEXT NOT NULL
);

CREATE UNIQUE INDEX project_name_idx ON projects (project_name);

CREATE TABLE project_groups (
    project_id      INTEGER NOT NULL,
    group_name      TEXT NOT NULL,
    type            group_type NOT NULL,
    FOREIGN KEY (project_id) REFERENCES projects(id)
);

CREATE UNIQUE INDEX group_name_idx ON project_groups (group_name);