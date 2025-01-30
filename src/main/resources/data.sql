INSERT INTO user_entity (id, name, date_of_birth, password) VALUES (1, 'user1', '12.12.2000', '$2a$10$F5rzmMmNJcgwqTXmcro1eOeATecEUDsPM8WjKtF8Qx46RFDjlmCSW');
INSERT INTO account(id, user_id, version) VALUES (1, 1, 0);
INSERT INTO email_data (user_id, email) VALUES (1, '1@ya.ru');
INSERT INTO phone_data  (user_id, phone) VALUES (1, '89991232334');

INSERT INTO user_entity (id, name, date_of_birth, password) VALUES (2, 'user2', '12.12.2000', '$2a$10$F5rzmMmNJcgwqTXmcro1eOeATecEUDsPM8WjKtF8Qx46RFDjlmCSW');
INSERT INTO account(id, user_id) VALUES (2, 2);
INSERT INTO email_data (user_id, email) VALUES ( 2, '2@ya.ru');
INSERT INTO phone_data (user_id, phone) VALUES ( 2, '89991232335');