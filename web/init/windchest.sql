CREATE DATABASE windchest CHARACTER SET utf8mb4;
USE windchest;

CREATE TABLE IF NOT EXISTS user (
	uid      INT PRIMARY KEY AUTO_INCREMENT,
	username VARCHAR(32) NOT NULL,
	nickname VARCHAR(32) DEFAULT NULL,
	password VARCHAR(32) NOT NULL,
	email    VARCHAR(64) DEFAULT NULL,
	avatar   VARCHAR(512) DEFAULT NULL
)CHARACTER SET utf8mb4;

CREATE TABLE IF NOT EXISTS wind (
	wid  INT PRIMARY KEY AUTO_INCREMENT,
	uid  INT NOT NULL REFERENCES user(uid),
	chest VARCHAR(64) NOT NULL,
	type CHAR(16) DEFAULT 'text', -- ENUM('text', 'link', 'image', 'audio', 'video', 'file')
	text TEXT DEFAULT NULL,
	path VARCHAR(512) DEFAULT NULL,
	time TIMESTAMP DEFAULT CURRENT_TIMESTAMP
)CHARACTER SET utf8mb4;

CREATE TABLE IF NOT EXISTS chest (
	fid INT PRIMARY KEY AUTO_INCREMENT,
	uid INT NOT NULL REFERENCES user(uid),
	name VARCHAR(64) NOT NULL
)CHARACTER SET utf8mb4;
