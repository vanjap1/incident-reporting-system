-- Incident Types
INSERT INTO incident_types (name) VALUES
 ('Fire'),
 ('Flood'),
 ('Traffic Accident');

-- Incident Subtypes
INSERT INTO incident_subtypes (type_id, name) VALUES
( 1, 'House Fire'),
( 1, 'Forest Fire'),
(2, 'River Flood'),
( 2, 'Urban Flood'),
( 3, 'Minor Collision'),
( 3, 'Fatal Accident');

-- Sample Incidents
INSERT INTO incidents (created_at, description, image_url, latitude, longitude, status, type_id, subtype_id)
VALUES
    (NOW(), 'Small house fire reported in city center', NULL, 44.78, 17.19, 'PENDING', 1, 1),
    (NOW(), 'Forest fire spreading near outskirts', NULL, 44.80, 17.25, 'APPROVED', 1, 2),
    (NOW(), 'River overflow causing local flooding', NULL, 44.76, 17.18, 'PENDING', 2, 3),
    (NOW(), 'Urban flood blocking main street', NULL, 44.77, 17.20, 'REJECTED', 2, 4),
    (NOW(), 'Two cars collided, no injuries', NULL, 44.79, 17.21, 'APPROVED', 3, 5),
    (NOW(), 'Severe accident with casualties', NULL, 44.75, 17.22, 'PENDING', 3, 6);
