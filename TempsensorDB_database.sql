CREATE DATABASE TempsensorDB_database;

USE TempsensorDB_database;

CREATE TABLE TempTable (
    id INT AUTO_INCREMENT PRIMARY KEY,
    temperatures DECIMAL(5, 2),
    timestamp DATETIME NOT NULL
);

CREATE TABLE TempCategory (
    id INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(50) NOT NULL,
    lower_bound DECIMAL(5, 2),
    upper_bound DECIMAL(5, 2)
);

CREATE TABLE Level (
    id INT AUTO_INCREMENT PRIMARY KEY,
    color VARCHAR(50) NOT NULL,
    category_id INT,
    FOREIGN KEY (category_id) REFERENCES TempCategory(id)
);

CREATE TABLE Warning (
    id INT AUTO_INCREMENT PRIMARY KEY,
    temp_id INT,
    level_id INT,
    timestamp DATETIME NOT NULL,
    FOREIGN KEY (temp_id) REFERENCES TempTable(id),
    FOREIGN KEY (level_id) REFERENCES Level(id)
);