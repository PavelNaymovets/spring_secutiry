CREATE TABLE categories (
    id              bigserial primary key,
    title           varchar(255) unique
);

INSERT INTO categories (title) values ('Food'), ('Others');

CREATE TABLE products
(
    id              bigserial primary key,
    title           varchar(255),
    cost            int,
    category_id     bigint references categories (id)
);

INSERT INTO products (title, cost, category_id) VALUES
('Сахар', 100, 1),
('Кирпич', 50, 1),
('Бетон', 90, 1),
('Цемент', 10, 1),
('Хлеб', 100, 1),
('Молоко', 80, 1),
('Яйца', 10, 2),
('Сыр', 20, 2),
('Мясо', 100, 2),
('Картофель', 60, 2),
('Морковь', 90, 2);