CREATE DATABASE IF NOT EXISTS java_app_sec;

USE java_app_sec;

CREATE TABLE person (
	id INT UNSIGNED AUTO_INCREMENT PRIMARY KEY,
	username VARCHAR(15) NOT NULL,
	email VARCHAR(30) NOT NULL,
	password VARCHAR(32) NOT NULL,
	name VARCHAR(80),
	is_admin BOOLEAN DEFAULT FALSE,
	funds FLOAT DEFAULT 0.0,
	CONSTRAINT UC_Person UNIQUE (username, email)
);

INSERT INTO person (username, email, password, name, is_admin, funds) VALUES (
	'admin', 'admin@appsec.com', md5('Admin@123'), 'Administrator', true, 100.0
);

INSERT INTO person (username, email, password, name, funds) VALUES (
	'john_doe', 'john.doe@appsec.com', md5('Password1!'), 'John Doe', 50.0
);

INSERT INTO person (username, email, password, name, funds) VALUES (
	'john_smith', 'john.smith@appsec.com', md5('Winter2021!'), 'John Smith', 750.0
);

INSERT INTO person (username, email, password, name, funds) VALUES (
	'megan_hill', 'megan.hill@appsec.com', md5('ILoveMyCat2021!'), 'Megan Hill', 1200.0
);

INSERT INTO person (username, email, password, name, funds) VALUES (
	'cindy_williams', 'cindy.williams@appsec.com', md5('Test@789'), 'Cindy Williams', 330.0
);