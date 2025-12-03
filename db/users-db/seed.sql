ALTER TABLE users AUTO_INCREMENT = 1;

-- Insert users
INSERT INTO users (email, password, provider, provider_id, username, role)
VALUES 
('admin@etf.unibl.org',  '$2a$10$1tpqZcSJ1jQDZ8KPa2W5X.3Toy6VzAUihhjU2BAYs2hpKL5MhOzA6', 'google', 'google-12345', 'adminUser','ADMIN'),
('moderator@etf.unibl.org', '$2a$10$hashedModeratorPass', NULL, NULL, 'modUser','MODERATOR'),
('user1@etf.unibl.org', '$2a$10$hashedUserPass', NULL, NULL, 'regularUser','USER'),
('student@etf.unibl.org', '$2a$10$hashedStudentPass', 'google', 'google-67890', 'studentUser','USER');

