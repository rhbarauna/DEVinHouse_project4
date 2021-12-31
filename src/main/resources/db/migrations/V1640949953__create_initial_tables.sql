DROP TABLE IF EXISTS users;
DROP TABLE IF EXISTS villagers;

CREATE TABLE villagers (
   id SERIAL PRIMARY KEY,
   name VARCHAR(255) NOT NULL,
   surname VARCHAR(255) NOT NULL,
   document VARCHAR(15) NOT NULL UNIQUE,
   birthday TIMESTAMP NOT NULL,
   wage NUMERIC(5,2)
);

CREATE TABLE users (
   id SERIAL PRIMARY KEY,
   villager_id BIGINT NOT NULL UNIQUE,
   email VARCHAR(255) NOT NULL UNIQUE,
   password VARCHAR(255) NOT NULL,
   roles VARCHAR[3] NOT NULL DEFAULT '{USER}',
   CONSTRAINT user_table_villager_fk
       FOREIGN KEY (villager_id)
           REFERENCES villagers (id)
);

INSERT INTO villagers (name, surname, document, birthday, wage) VALUES('Rhenato', 'Barauna ADMIN', '100.000.000-00', '1985-07-10', 100);
INSERT INTO villagers (name, surname, document, birthday, wage) VALUES('Rhenato', 'Barauna USER', '200.000.000-00', '1985-07-11', 200);

INSERT INTO users (villager_id, email, password, roles) VALUES(1, 'rhbarauna@powerkitchen.ca', '$2a$10$vdzLTE.f4hUuo1sseEJnzO8hJim0.2UnBnqpDhI.xTqVvFAD68FSm', '{ADMIN}');
INSERT INTO users (villager_id, email, password) VALUES(2, 'rhbarauna@gmail.com', '$2a$10$vdzLTE.f4hUuo1sseEJnzO8hJim0.2UnBnqpDhI.xTqVvFAD68FSm');