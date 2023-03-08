create table categories (
    id              bigserial primary key,
    title           varchar(255) unique
);

insert into categories (title) values ('Food'), ('Others');

create table products
(
    id              bigserial primary key,
    title           varchar(255),
    cost            int,
    category_id     bigint references categories (id)
);

insert into products (title, cost, category_id) values
('Сахар', 100, 1),
('Кирпич', 50, 1);