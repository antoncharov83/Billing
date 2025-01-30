CREATE TABLE user_entity (
                             id        SERIAL PRIMARY KEY,
                             name      VARCHAR(500),
                             date_of_birth DATE,
                             password  VARCHAR(500) NOT NULL CHECK (LENGTH(password) >= 8 AND LENGTH(password) <= 500)
);

-- Таблица аккаунтов
CREATE TABLE account (
                         id            SERIAL PRIMARY KEY,
                         user_id       BIGINT REFERENCES user_entity(id),
                         balance       DECIMAL(8,2) DEFAULT 100.00,
                         version       BIGINT DEFAULT 0
);

-- Таблица электронных адресов
CREATE TABLE email_data (
                            id            SERIAL PRIMARY KEY,
                            user_id       BIGINT REFERENCES user_entity(id),
                            email         VARCHAR(200)
);

-- Таблица телефонных номеров
CREATE TABLE phone_data (
                            id            SERIAL PRIMARY KEY,
                            user_id       BIGINT REFERENCES user_entity(id),
                            phone         VARCHAR(13)
);