-- Note: This database schema was initially generated using Hibernate ORM.
-- It was then visualized in MySQL Workbench, and this SQL script was reverse-engineered from that visualization.

CREATE DATABASE TempsensorDB_database; -- Creating the database. The name "TempsensorDB_database" can be modified based on preference.

USE TempsensorDB_database;

CREATE TABLE `temp_category_table` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `lower_bound` varchar(255) DEFAULT NULL,
  `name` varchar(255) DEFAULT NULL,
  `upper_bound` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

CREATE TABLE `level_table` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `color` varchar(255) DEFAULT NULL,
  `category_id` bigint DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK146k750ed2lfwvea8xb4tkjee` (`category_id`),
  CONSTRAINT `FK146k750ed2lfwvea8xb4tkjee` FOREIGN KEY (`category_id`) REFERENCES `temp_category_table` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

CREATE TABLE `temp_table` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `temperatures` float DEFAULT NULL,
  `timestamp` datetime(6) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

CREATE TABLE `warning_table` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `timestamp_warning` datetime(6) DEFAULT NULL,
  `level_id` bigint DEFAULT NULL,
  `temp_id` bigint DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FKh93mqeqi4i6ywskicn3ev7r6b` (`level_id`),
  KEY `FKnrkdnio4e2umiv1ciyt5ihwpb` (`temp_id`),
  CONSTRAINT `FKh93mqeqi4i6ywskicn3ev7r6b` FOREIGN KEY (`level_id`) REFERENCES `level_table` (`id`),
  CONSTRAINT `FKnrkdnio4e2umiv1ciyt5ihwpb` FOREIGN KEY (`temp_id`) REFERENCES `temp_table` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
