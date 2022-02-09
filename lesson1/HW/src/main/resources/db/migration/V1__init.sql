CREATE TABLE IF NOT EXISTS category
(
    id bigserial,
    title character varying(128),
    PRIMARY KEY (id)
);

CREATE TABLE IF NOT EXISTS product
(
    id bigserial,
    title character varying(128) NOT NULL,
    article character varying(32),
    price numeric(19, 2) NOT NULL,
    brand character varying(128),
    model character varying(256),
    color character varying(64),
    category_id bigint REFERENCES category (id),
--     image bytea,
    image_url character varying(2048),
    description character varying(2048),
    PRIMARY KEY (id)
);

insert into category (title)
values ('Электрочайники');

insert into product (article, title, price, category_id, brand, model, color, image_url)
values  ('1000166', 'Электрочайник Kromax KR-342', 712.8, 1,  'Kromax', 'KR-342',  '', 'http://magazilla.ru/jpg_zoom1/1000166.jpg'),
        ('1000166', 'Электрочайник Kromax KR-342', 712.8, 1, 'Kromax', 'KR-342', 'белый', 'http://magazilla.ru/jpg_zoom1/1000166.jpg'),
        ('1000170', 'Электрочайник Kromax KR-344', 579.7, 1, 'Kromax', 'KR-344', '', 'http://magazilla.ru/jpg_zoom1/1000170.jpg'),
        ('1000170', 'Электрочайник Kromax KR-344', 579.7, 1, 'Kromax', 'KR-344', 'белый', 'http://magazilla.ru/jpg_zoom1/1000170.jpg'),
        ('1000174', 'Электрочайник Kromax KR-347', 579.7, 1, 'Kromax', 'KR-347', '', 'http://magazilla.ru/jpg_zoom1/1000174.jpg'),
        ('1000174', 'Электрочайник Kromax KR-347', 579.7, 1, 'Kromax', 'KR-347', 'бирюзовый', 'http://magazilla.ru/jpg_zoom1/1000174.jpg'),
        ('1001379', 'Электрочайник Supra KES-2008', 814.0, 1, 'Supra', 'KES-2008', '', 'http://magazilla.ru/jpg_zoom1/1001379.jpg'),
        ('1001379', 'Электрочайник Supra KES-2008', 814.0, 1, 'Supra', 'KES-2008', 'черный', 'http://magazilla.ru/jpg_zoom1/1001379.jpg'),
        ('1004635', 'Электрочайник Kromax KR-223', 779.9, 1, 'Kromax', 'KR-223', '', 'http://magazilla.ru/jpg_zoom1/1004635.jpg'),
        ('1004635', 'Электрочайник Kromax KR-223', 779.9, 1, 'Kromax', 'KR-223', 'черный', 'http://magazilla.ru/jpg_zoom1/1004635.jpg'),
        ('1004640', 'Электрочайник Zigmund&Shtain KE 619', 933.9, 1, 'Zigmund&Shtain', 'KE 619', '', 'http://magazilla.ru/jpg/1004640.jpg'),
        ('1004640', 'Электрочайник Zigmund&Shtain KE 619', 933.9, 1, 'Zigmund&Shtain', 'KE 619', 'черный', 'http://magazilla.ru/jpg/1004640.jpg'),
        ('1004640', 'Электрочайник Zigmund&Shtain KE 619', 933.9, 1, 'Zigmund&Shtain', 'KE 619', 'белый', 'http://magazilla.ru/jpg/1004640.jpg'),
        ('1004676', 'Электрочайник Kromax KR-239', 867.9, 1, 'Kromax', 'KR-239', '', 'http://magazilla.ru/jpg_zoom1/1004676.jpg'),
        ('1004676', 'Электрочайник Kromax KR-239', 867.9, 1, 'Kromax', 'KR-239', 'бежевый', 'http://magazilla.ru/jpg_zoom1/1004676.jpg'),
        ('1004683', 'Электрочайник Kromax KR-241', 862.4, 1, 'Kromax', 'KR-241', '', 'http://magazilla.ru/jpg_zoom1/1004683.jpg'),
        ('1004683', 'Электрочайник Kromax KR-241', 862.4, 1, 'Kromax', 'KR-241', 'белый', 'http://magazilla.ru/jpg_zoom1/1004683.jpg'),
        ('1006111', 'Электрочайник Breville K360', 3839.0, 1, 'Breville', 'K360', '', 'http://magazilla.ru/jpg_zoom1/1006111.jpg'),
        ('1006111', 'Электрочайник Breville K360', 3839.0, 1, 'Breville', 'K360', 'бежевый', 'http://magazilla.ru/jpg_zoom1/1006111.jpg'),
        ('1008786', 'Электрочайник Alessi MG32', 20955.0, 1, 'Alessi', 'MG32', '', 'http://magazilla.ru/jpg_zoom1/1008786.jpg');