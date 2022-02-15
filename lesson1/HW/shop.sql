-- Для изображений создал два столбца, чтобы можно было загрузить изображение прямо в БД или просто указать ссылку в интернете

-- В идеале характеристики должны храниться в отдельной таблице со связью 1 к 1
-- Но непонятно, как хранить характеристики. Должна быть отдельная таблица для каждой категории товаров?
-- Или сделать одну универсальную таблицу на сотню столбцов, в которую можно будет внести характеристики любого товара?
CREATE TABLE IF NOT EXISTS product
(
    id bigserial,
    title varchar(128) NOT NULL,
    article varchar(32),
    price numeric(19, 2) NOT NULL,
    brand varchar(128),
    color varchar(64),
    category bigint references category (id),
    image bytea,
    image_url varchar(2048),
    description varchar(2048),
    PRIMARY KEY (id)
);

CREATE TABLE IF NOT EXISTS "user"
(
    login varchar(64) NOT NULL,
    name varchar(64),
    lastname varchar(64),
    patronymic varchar(64),
    password varchar(60) NOT NULL,
    email varchar(254) NOT NULL,
    enabled boolean NOT NULL DEFAULT false,
    id bigserial,
    PRIMARY KEY (id)
);

-- Сначала хотел хранить здесь просто пользователя, но потом понял, что заказ может сделать и не залогиненый пользователь,
-- поэтому продублировал здесь поля пользователя
CREATE TABLE IF NOT EXISTS "order"
(
    id bigserial,
    "user" bigint,
    name varchar(64),
    lastname varchar(64),
    patronymic varchar(64),
    email varchar(254) NOT NULL,
    price numeric(19, 2) NOT NULL,
    date timestamp(0) without time zone NOT NULL,
    comment varchar(2048),
    PRIMARY KEY (id)
);

-- Наверняка можно использовать какой-то API,
-- который можно подключить к сайту, чтобы можно было выбрать адрес из выпадающего списка.
-- Мне кажется, эта таблица явно не годится.
CREATE TABLE IF NOT EXISTS address
(
    id bigserial,
    country varchar(128) NOT NULL,
    region varchar(128),
    city varchar(256) NOT NULL,
    street varchar(256) NOT NULL,
    house_number varchar(32) NOT NULL,
    apartment_number varchar(32) NOT NULL,
    PRIMARY KEY (id)
);

CREATE TABLE IF NOT EXISTS cart_item
(
    id bigint,
    title varchar(128) NOT NULL,
    price numeric(19, 2) NOT NULL,
    cart_id bigint NOT NULL references cart (id)
);

-- Это подсмотрел в документации к Spring
-- https://docs.spring.io/spring-session/docs/2.4.5/reference/html5/
CREATE TABLE IF NOT EXISTS spring_session
(
    primary_id character(36),
    session_id character(36) NOT NULL,
    creation_time bigint NOT NULL,
    last_access_time bigint NOT NULL,
    max_inactive_interval integer NOT NULL,
    expiry_time bigint NOT NULL,
    principal_name varchar(100),
    PRIMARY KEY (primary_id)
);

CREATE TABLE IF NOT EXISTS spring_session_attributes
(
    session_primary_id character(36) NOT NULL references spring_session (primary_id),
    attribute_name varchar(200) NOT NULL,
    attribute_bytes bytea NOT NULL,
    PRIMARY KEY (session_primary_id, attribute_name)
);

-- Вспомнил, что корзину хотели на Redis переносить, но всё же не стал удалять
CREATE TABLE IF NOT EXISTS cart
(
    id bigserial,
    price numeric(19, 2) NOT NULL,
    spring_session bigint NOT NULL references spring_session (primary_id),
    PRIMARY KEY (id)
);

-- Список адресов, которые будут сохраняться для пользователя. При оформлении заказа можно будет выбрать один сохраненных адресов в выпадающем списке.
-- Я на самом деле не знаю, как сделать лучше. По идее эти адреса можно получить самому, запросив все заказы пользователя и из них получить уникальные адреса.
-- Но можно сэкономить ресурсы, просто сохранив эти данные в таблицу, обратиться к ней гораздо быстрее, чем выполнять запрос.
-- Стоит ли так делать или это bad practice и так никто не делает?
CREATE TABLE IF NOT EXISTS user_address
(
    "user" bigint references "user" (id),
    address bigint references address (id),
    PRIMARY KEY ("user", address)
);

-- Нужно придумать, что делать с подкатегориями
-- Прочитал про 3 способа (Adjacency List, Closure table, Nested sets) https://gist.github.com/codedokode/10539720
-- Решил пока обойтись без подкатегорий)
CREATE TABLE IF NOT EXISTS category
(
    id bigserial,
    name varchar(128),
    PRIMARY KEY (id)
);

CREATE TABLE IF NOT EXISTS status
(
    id bigserial,
    status_id bigint references status_enum (id),
    PRIMARY KEY (id)
);

CREATE TABLE IF NOT EXISTS status_enum
(
    id bigserial,
    status_name varchar(64) NOT NULL,
    PRIMARY KEY (id)
);

CREATE TABLE IF NOT EXISTS order_status
(
    order_id bigint references "order" (id),
    status_id bigint references status (id),
    start_date timestamp(0) without time zone NOT NULL,
    end_date timestamp(0) without time zone
);

CREATE TABLE IF NOT EXISTS authority
(
    id bigserial,
    name varchar(255) NOT NULL,
    PRIMARY KEY (id)
);

CREATE TABLE IF NOT EXISTS user_authority
(
    user_id bigint references "user" (id),
    authority_id bigint references authority (name),
    PRIMARY KEY (user_id, authority_id)
);