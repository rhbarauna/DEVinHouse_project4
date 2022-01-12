DROP TABLE IF EXISTS user_role;
DROP TABLE IF EXISTS "user";
DROP TABLE IF EXISTS villager;
DROP TABLE IF EXISTS "role";


CREATE TABLE villager (
  id SERIAL PRIMARY KEY,
  name VARCHAR(60) NOT NULL,
  surname VARCHAR(60) NOT NULL,
  document VARCHAR(15) NOT NULL UNIQUE,
  birthday TIMESTAMP NOT NULL,
  wage NUMERIC(5,2),
  user_id BIGINT NOT NULL UNIQUE,
  CONSTRAINT villager_table_user_fk
      FOREIGN KEY (user_id)
          REFERENCES user (id)
);

CREATE TABLE "user" (
    id SERIAL PRIMARY KEY,
    email VARCHAR(60) NOT NULL UNIQUE,
    password VARCHAR(60) NOT NULL
);

CREATE TABLE "role" (
    id SERIAL PRIMARY KEY,
    name VARCHAR(60) NOT NULL UNIQUE,
    description TEXT
);

CREATE TABLE user_role (
   user_id BIGINT NOT NULL,
   role_id BIGINT NOT NULL,
   CONSTRAINT user_role_table_user_fk
       FOREIGN KEY (user_id)
           REFERENCES "user" (id),
   CONSTRAINT user_role_table_role_fk
       FOREIGN KEY (role_id)
           REFERENCES role (id)
);

INSERT INTO villager (name, surname, document, birthday, wage) VALUES('Rhenato', 'Barauna ADMIN', '100.000.000-00', '1985-07-10', 100);
INSERT INTO villager (name, surname, document, birthday, wage) VALUES('Rhenato', 'Barauna USER', '200.000.000-00', '1985-07-11', 200);

INSERT INTO role (name) VALUES('ADMIN');
INSERT INTO role (name) VALUES('USER');

INSERT INTO "user" (villager_id, email, password) VALUES(1, 'rhbarauna@powerkitchen.ca', '$2a$10$Nxv2VDK6JIZPZrZmWwfgFOgaDXbwf0h4DN3tI3wWcLVPOLvTlLHSC');
INSERT INTO "user" (villager_id, email, password) VALUES(2, 'rhbarauna@gmail.com', '$2a$10$Nxv2VDK6JIZPZrZmWwfgFOgaDXbwf0h4DN3tI3wWcLVPOLvTlLHSC');

INSERT INTO user_role (user_id, role_id) VALUES(1, 1);
INSERT INTO user_role (user_id, role_id) VALUES(1, 2);
INSERT INTO user_role (user_id, role_id) VALUES(2, 2);