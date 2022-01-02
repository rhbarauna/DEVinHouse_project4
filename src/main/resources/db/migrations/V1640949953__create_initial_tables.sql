DROP TABLE IF EXISTS "user";
DROP TABLE IF EXISTS villager;

CREATE TABLE villager (
                          id SERIAL PRIMARY KEY,
                          name VARCHAR(60) NOT NULL,
                          surname VARCHAR(60) NOT NULL,
                          document VARCHAR(15) NOT NULL UNIQUE,
                          birthday TIMESTAMP NOT NULL,
                          wage NUMERIC(5,2)
);

CREATE TABLE "user" (
                        id SERIAL PRIMARY KEY,
                        villager_id BIGINT NOT NULL UNIQUE,
                        email VARCHAR(60) NOT NULL UNIQUE,
                        password VARCHAR(60) NOT NULL,
                        roles VARCHAR[3] NOT NULL DEFAULT '{USER}',
                        CONSTRAINT user_table_villager_fk
                            FOREIGN KEY (villager_id)
                                REFERENCES villager (id)
);

INSERT INTO villager (name, surname, document, birthday, wage) VALUES('Rhenato', 'Barauna ADMIN', '100.000.000-00', '1985-07-10', 100);
INSERT INTO villager (name, surname, document, birthday, wage) VALUES('Rhenato', 'Barauna USER', '200.000.000-00', '1985-07-11', 200);

INSERT INTO "user" (villager_id, email, password, roles) VALUES(1, 'rhbarauna@powerkitchen.ca', '$2a$10$vdzLTE.f4hUuo1sseEJnzO8hJim0.2UnBnqpDhI.xTqVvFAD68FSm', '{ADMIN}');
INSERT INTO "user" (villager_id, email, password) VALUES(2, 'rhbarauna@gmail.com', '$2a$10$vdzLTE.f4hUuo1sseEJnzO8hJim0.2UnBnqpDhI.xTqVvFAD68FSm');