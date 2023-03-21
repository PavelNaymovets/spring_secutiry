create table categories (
    id              bigserial primary key,
    title           varchar(255) unique
);

insert into categories (title) values ('Еда'), ('Другое');

create table products
(
    id              bigserial primary key,
    title           varchar(255),
    cost            numeric(8, 2),
    category_id     bigint references categories (id)
);

insert into products (title, cost, category_id) values
('Сахар', 100.00, 1),
('Кирпич', 50.00, 1),
('Бетон', 90.00, 1),
('Цемент', 10.00, 1),
('Хлеб', 100.00, 1),
('Молоко', 80.00, 1),
('Яйца', 10.00, 2),
('Сыр', 20.00, 2),
('Мясо', 100.00, 2),
('Картофель', 60.00, 2),
('Морковь', 90.00, 2);