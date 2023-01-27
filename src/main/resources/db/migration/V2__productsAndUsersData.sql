CREATE TABLE IF NOT EXISTS products (id bigserial primary key, title varchar(255), cost int);

INSERT INTO products (title, cost) VALUES
('Сахар', 100),
('Кирпич', 50),
('Бетон', 90),
('Цемент', 10),
('Хлеб', 100),
('Молоко', 80),
('Яйца', 10),
('Сыр', 20),
('Мясо', 100),
('Картофель', 60),
('Морковь', 90);