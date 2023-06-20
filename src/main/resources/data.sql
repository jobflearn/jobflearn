INSERT INTO locations (latitude, longitude, province, city, street, country_code)
VALUES (37.5665, 126.9780, 'Seoul', 'Jung-gu', '110 Sejong-daero', 'KR'),
       (37.7749, -122.4194, 'California', 'San Francisco', 'Mountain View', 'US'),
       (51.5074, -0.1278, 'England', 'London', 'Canary Wharf', 'GB'),
       (35.6895, 139.6917, 'Tokyo', 'Chiyoda', 'Marunouchi', 'JP');

INSERT INTO companies (name, is_verified, description, location_id)
VALUES ('OpenAI', true, 'OpenAI is an artificial intelligence research lab.', 1),
       ('Google', true, 'Google LLC is an American multinational technology company.', 2),
       ('HSBC', false, 'HSBC Holdings plc is a British multinational investment bank and financial services holding company.', 3),
       ('Sony', true, 'Sony Group Corporation is a Japanese multinational conglomerate corporation.', 4);
