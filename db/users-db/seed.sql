ALTER TABLE users AUTO_INCREMENT = 1;

-- Insert users
INSERT INTO users (email, password, provider, provider_id, username)
VALUES 
('admin@etf.unibl.org',  '$2a$10$hashedAdminPass', 'google', 'google-12345', 'adminUser'),
('moderator@etf.unibl.org', '$2a$10$hashedModeratorPass', NULL, NULL, 'modUser'),
('user1@etf.unibl.org', '$2a$10$hashedUserPass', NULL, NULL, 'regularUser'),
('student@etf.unibl.org', '$2a$10$hashedStudentPass', 'google', 'google-67890', 'studentUser');

-- Assign roles
INSERT INTO user_roles (user_id, role) VALUES
(1, 'ADMIN'),
(2, 'MODERATOR'),
(3, 'USER'),
(4, 'USER'),
(1, 'MODERATOR');
