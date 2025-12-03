CREATE TABLE `incidents` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `created_at` datetime(6) DEFAULT NULL,
  `description` varchar(1000) DEFAULT NULL,
  `image_url` varchar(255) DEFAULT NULL,
  `latitude` double NOT NULL,
  `longitude` double NOT NULL,
  `status` enum('APPROVED','PENDING','REJECTED') DEFAULT NULL,
  `type_id` BIGINT NOT NULL,
  `subtype_id` BIGINT,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;


CREATE TABLE incident_types (
  id BIGINT NOT NULL AUTO_INCREMENT,
  name VARCHAR(255) NOT NULL UNIQUE,
  PRIMARY KEY (id)
);

CREATE TABLE incident_subtypes (
  id BIGINT NOT NULL AUTO_INCREMENT,
  type_id BIGINT NOT NULL,
  name VARCHAR(255) NOT NULL,
  PRIMARY KEY (id),
  FOREIGN KEY (type_id) REFERENCES incident_types(id) ON DELETE CASCADE
);
