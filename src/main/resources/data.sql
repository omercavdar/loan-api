CREATE TABLE customers (
                           id BIGINT AUTO_INCREMENT PRIMARY KEY,
                           name VARCHAR(255),
                           surname VARCHAR(255),
                           credit_limit DECIMAL(19,2),
                           used_credit_limit DECIMAL(19,2)
);
INSERT INTO customers (name, surname, credit_limit, used_credit_limit) VALUES ('John', 'Doe', 10000, 0);
INSERT INTO customers (name, surname, credit_limit, used_credit_limit) VALUES ('Jane', 'Doe', 15000, 5000);
INSERT INTO customers (name, surname, credit_limit, used_credit_limit) VALUES ('Omer', 'Cavdar', 20000, 10000);