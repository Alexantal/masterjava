--liquibase formatted sql

--changeset gkislin:1
CREATE SEQUENCE common_seq START 100000;

CREATE TABLE city (
  ref  TEXT PRIMARY KEY,
  name TEXT NOT NULL
);

ALTER TABLE users
  ADD COLUMN city_ref TEXT REFERENCES city (ref) ON UPDATE CASCADE;

--changeset gkislin:2
CREATE TABLE project (
  id          INTEGER PRIMARY KEY DEFAULT nextval('common_seq'),
  name        TEXT UNIQUE NOT NULL,
  description TEXT
);

CREATE TYPE GROUP_TYPE AS ENUM ('REGISTERING', 'CURRENT', 'FINISHED');

CREATE TABLE groups (
  id         INTEGER PRIMARY KEY DEFAULT nextval('common_seq'),
  name       TEXT UNIQUE NOT NULL,
  type       GROUP_TYPE  NOT NULL,
  project_id INTEGER     NOT NULL REFERENCES project (id)
);

CREATE TABLE user_group (
  user_id  INTEGER NOT NULL REFERENCES users (id) ON DELETE CASCADE,
  group_id INTEGER NOT NULL REFERENCES groups (id),
  CONSTRAINT users_group_idx UNIQUE (user_id, group_id)
);

--changeset avmelnikov:3
CREATE TABLE email_sending_results (
    id                  INTEGER PRIMARY KEY DEFAULT nextval('common_seq'),
    date_time           TIMESTAMP NOT NULL,
    from_email          TEXT NOT NULL,
    email_subject       TEXT NOT NULL DEFAULT 'without subject',
    is_sending_success  BOOLEAN NOT NULL DEFAULT FALSE,
    CONSTRAINT date_time_from_email_idx UNIQUE (date_time, from_email)
)