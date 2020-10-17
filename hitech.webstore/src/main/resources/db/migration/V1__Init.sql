create table items (id bigserial primary key, category varchar(255), title varchar(255), author varchar(255), price int);
insert into items (category, title, author, price) values
('Product', 'Хлеб', 'Московский хлебный комбинат', 50),
('Product', 'Молоко', '1-й Молочный комбинат', 80),
('Product', 'Сыр', '1-й Молочный комбинат', 217),
('Product', 'Мясо', 'ОАО "Останкинский мясокомбинат"', 300),
('Product', 'Рыба', 'ОАО "Дары Комчатки"', 450),
('Product', 'Вода', 'ОАО "Напитки из черноголовки"', 30),
('Product', 'Творог', '1-й Молочный комбинат', 76),
('Product', 'Йогурт', '1-й Молочный комбинат', 45),
('Product', 'Сигареты', 'Филип Моррис Интернэшнл', 218),
('Product', 'Водка', 'Московский завод ликёро-водочных изделий', 250),
('Book', 'Пепел и сталь', 'Брэндон Сандерсон', 600),
('Book', 'Задверье', 'Нил Гейман', 650),
('Book', 'Хроники Амбера', 'Роджер Желязны', 1500),
('Book', 'Гарри Поттер и философский камень', 'Джоан Роулинг', 400),
('Book', 'Кричащая лестница', 'Джонатан Страуд', 700),
('Book', 'Основание', 'Айзек Азимов', 1250),
('Book', 'Сага о живых кораблях','Робин Хобб',950),
('Book', 'Ночь в тоскливом октябре', 'Роджер Желязны', 780),
('Book', 'Код да Винчи', 'Дэн Браун', 800),
('Book', 'Властелин колец: Братство Кольца', 'Джон Рональд Руэл Толкин', 1400);

create table orders (id bigserial primary key, price int);

create table order_items (id bigserial primary key, item_id bigint references items(id), order_id bigint references orders(id), quantity int, price int, price_per_item int);
