-- Seed data for incidents table

INSERT INTO incidents (created_at, description, image_url, latitude, longitude, status, subtype, type)
VALUES
  (NOW(), 'Broken streetlight on Main St.', 'http://example.com/img1.jpg', 44.775, 17.185, 'PENDING', 'Lighting', 'Infrastructure'),

  (NOW(), 'Pothole near the bus stop', 'http://example.com/img2.jpg', 44.776, 17.186, 'APPROVED', 'Road', 'Infrastructure'),

  (NOW(), 'Graffiti on public wall', 'http://example.com/img3.jpg', 44.777, 17.187, 'REJECTED', 'Vandalism', 'Public Safety'),

  (NOW(), 'Overflowing trash bin in park', 'http://example.com/img4.jpg', 44.778, 17.188, 'PENDING', 'Waste', 'Sanitation'),

  (NOW(), 'Traffic light malfunction at intersection', 'http://example.com/img5.jpg', 44.779, 17.189, 'APPROVED', 'Traffic', 'Infrastructure');
