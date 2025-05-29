-- Test data for integration tests

-- Users
INSERT INTO app_user (id, name, email, password, reward_points) 
VALUES (1, 'Test User 1', 'user1@test.com', 'password', 50);

INSERT INTO app_user (id, name, email, password, reward_points) 
VALUES (2, 'Test User 2', 'user2@test.com', 'password', 100);

-- Blackouts
INSERT INTO blackout (id, location, start_time, end_time, status, description, reported_by_id, latitude, longitude, verified) 
VALUES (1, 'São Paulo Downtown', '2025-05-29T15:00:00', null, 'ACTIVE', 'Major power outage in downtown area', 1, -23.5505, -46.6333, false);

INSERT INTO blackout (id, location, start_time, end_time, status, description, reported_by_id, latitude, longitude, verified) 
VALUES (2, 'Rio de Janeiro Beach', '2025-05-28T10:00:00', '2025-05-28T18:00:00', 'RESOLVED', 'Scheduled maintenance completed', 2, -22.9068, -43.1729, true);
