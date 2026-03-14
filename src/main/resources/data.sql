------------------------------------------------
-- USERS
------------------------------------------------

INSERT IGNORE INTO users (username, email, password, role, is_active, created_at)
VALUES
('admin','admin@hostel.com',
'$2a$10$N9qo8uLOickgx2ZMRZoMyeIjZAgcfl7p92ldGxad68LJZdL17lhWy',
'ADMIN',1,NOW());

INSERT IGNORE INTO users (username, email, password, role, is_active, created_at)
VALUES
('warden','warden@hostel.com',
'$2a$10$8K1p/a0dR1xqM8K3Z5Hs4e7w3YFGhM6D1vD0QHnBzPmVRaLbVrLe2',
'WARDEN',1,NOW());

------------------------------------------------
-- ROOMS
------------------------------------------------

INSERT IGNORE INTO rooms
(room_number, room_type, capacity, occupied_count, floor, block, monthly_fee, status)
VALUES
('A101','SINGLE',1,0,1,'A',5000,'AVAILABLE'),
('A102','DOUBLE',2,0,1,'A',3500,'AVAILABLE'),
('A103','TRIPLE',3,0,1,'A',2500,'AVAILABLE'),
('A104','DOUBLE',2,0,1,'A',3500,'AVAILABLE'),
('A105','SINGLE',1,0,1,'A',5000,'AVAILABLE'),

('B101','SINGLE',1,0,1,'B',5000,'AVAILABLE'),
('B102','DOUBLE',2,0,1,'B',3500,'AVAILABLE'),
('B103','TRIPLE',3,0,1,'B',2500,'AVAILABLE'),

('B201','DOUBLE',2,0,2,'B',3500,'AVAILABLE'),
('B202','TRIPLE',3,0,2,'B',2500,'AVAILABLE'),

('C101','TRIPLE',3,0,1,'C',2500,'AVAILABLE'),
('C102','DOUBLE',2,0,1,'C',3500,'AVAILABLE');

------------------------------------------------
-- MESS MENU
------------------------------------------------


------------------------------------------------
-- SAMPLE NOTIFICATIONS
------------------------------------------------


------------------------------------------------
-- SAMPLE PAYMENTS (requires student_id later)
------------------------------------------------
-- These will work once students exist

-- INSERT INTO payments (amount,payment_type,status,student_id)
-- VALUES (5000,'ROOM_RENT','PENDING',1);

------------------------------------------------
-- SAMPLE COMPLAINTS (requires student_id)
------------------------------------------------
-- INSERT INTO complaints (title,description,category,status,student_id)
-- VALUES ('Water leakage','Tap leaking in bathroom','MAINTENANCE','PENDING',1);