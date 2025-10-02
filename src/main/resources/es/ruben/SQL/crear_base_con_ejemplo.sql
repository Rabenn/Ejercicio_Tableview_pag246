CREATE DATABASE IF NOT EXISTS prueba;
USE prueba;

CREATE TABLE IF NOT EXISTS persona (
    id INT AUTO_INCREMENT PRIMARY KEY,
    first_name VARCHAR(100) NOT NULL,
    last_name VARCHAR(100) NOT NULL,
    birth_date DATE NOT NULL
);

INSERT INTO persona (first_name, last_name, birth_date) VALUES
('Juan', 'Pérez', '1990-05-10'),
('María', 'López', '1985-11-23'),
('Carlos', 'Ruiz', '2000-01-15');
