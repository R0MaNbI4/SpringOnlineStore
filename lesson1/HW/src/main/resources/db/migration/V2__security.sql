CREATE TABLE IF NOT EXISTS user
(
    login character varying(64) NOT NULL UNIQUE,
    name character varying(64),
    lastname character varying(64),
    patronymic character varying(64),
    password character varying(60) NOT NULL,
    email character varying(254) NOT NULL,
    enabled boolean NOT NULL DEFAULT false,
    id bigserial,
    PRIMARY KEY (id)
);

CREATE TABLE IF NOT EXISTS authority
(
    id bigserial,
    name character varying(255) NOT NULL,
    PRIMARY KEY (id)
);

CREATE TABLE IF NOT EXISTS user_authority
(
    user_id bigint REFERENCES user (id),
    authority_id bigint REFERENCES authority (id),
    PRIMARY KEY (user_id, authority_id)
);

INSERT INTO user (login, password, email, enabled)
VALUES
    ('admin', '$2a$12$VrDh.a3IPIc0SBujQ.f9iOqKEwaQV18RROnXn0IKvUvIM4J3Bagp6', 'admin@admin.ru', true),
    ('manager', '$2a$12$EFl4r/IPSmFcyHv1LKiYw.KgonH0xI.sTW22CDj8zZ22IMZbzr1Dm', 'manager@manager.ru', true),
    ('user', '$2a$12$BK1..9.ZCggRacKoBafiAulwN0ujIefHud8P0v0nsj9u4KqRDlDpi', 'user@user.ru', true);

INSERT INTO authority (name)
VALUES
    ('ROLE_ADMIN'),
    ('ROLE_MANAGER'),
    ('ROLE_USER');

INSERT INTO user_authority (user_id, authority_id)
VALUES
    (1, 1),
    (2, 2),
    (3, 3);