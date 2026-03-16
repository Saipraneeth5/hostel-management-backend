-- USERS
INSERT INTO users (username, email, password, role, is_active, created_at, updated_at)
SELECT 'admin', 'admin@hostel.com',
'$2a$10$N9qo8uLOickgx2ZMRZoMyeIjZAgcfl7p92ldGxad68LJZdL17lhWy',
'ADMIN', true, NOW(), NOW()
WHERE NOT EXISTS (SELECT 1 FROM users WHERE username = 'admin');

INSERT INTO users (username, email, password, role, is_active, created_at, updated_at)
SELECT 'warden', 'warden@hostel.com',
'$2a$10$8K1p/a0dR1xqM8K3Z5Hs4e7w3YFGhM6D1vD0QHnBzPmVRaLbVrLe2',
'WARDEN', true, NOW(), NOW()
WHERE NOT EXISTS (SELECT 1 FROM users WHERE username = 'warden');

-- ROOMS
INSERT INTO rooms (room_number, room_type, capacity, occupied_count, floor, block, monthly_fee, status, has_ac, has_attached_bathroom, created_at, updated_at)
SELECT 'A101','SINGLE',1,0,1,'A',5000,'AVAILABLE',false,false,NOW(),NOW()
WHERE NOT EXISTS (SELECT 1 FROM rooms WHERE room_number = 'A101');

INSERT INTO rooms (room_number, room_type, capacity, occupied_count, floor, block, monthly_fee, status, has_ac, has_attached_bathroom, created_at, updated_at)
SELECT 'A102','DOUBLE',2,0,1,'A',3500,'AVAILABLE',false,false,NOW(),NOW()
WHERE NOT EXISTS (SELECT 1 FROM rooms WHERE room_number = 'A102');

INSERT INTO rooms (room_number, room_type, capacity, occupied_count, floor, block, monthly_fee, status, has_ac, has_attached_bathroom, created_at, updated_at)
SELECT 'A103','TRIPLE',3,0,1,'A',2500,'AVAILABLE',false,false,NOW(),NOW()
WHERE NOT EXISTS (SELECT 1 FROM rooms WHERE room_number = 'A103');

INSERT INTO rooms (room_number, room_type, capacity, occupied_count, floor, block, monthly_fee, status, has_ac, has_attached_bathroom, created_at, updated_at)
SELECT 'A104','DOUBLE',2,0,1,'A',3500,'AVAILABLE',false,false,NOW(),NOW()
WHERE NOT EXISTS (SELECT 1 FROM rooms WHERE room_number = 'A104');

INSERT INTO rooms (room_number, room_type, capacity, occupied_count, floor, block, monthly_fee, status, has_ac, has_attached_bathroom, created_at, updated_at)
SELECT 'A105','SINGLE',1,0,1,'A',5000,'AVAILABLE',false,false,NOW(),NOW()
WHERE NOT EXISTS (SELECT 1 FROM rooms WHERE room_number = 'A105');

INSERT INTO rooms (room_number, room_type, capacity, occupied_count, floor, block, monthly_fee, status, has_ac, has_attached_bathroom, created_at, updated_at)
SELECT 'B101','SINGLE',1,0,1,'B',5000,'AVAILABLE',false,false,NOW(),NOW()
WHERE NOT EXISTS (SELECT 1 FROM rooms WHERE room_number = 'B101');

INSERT INTO rooms (room_number, room_type, capacity, occupied_count, floor, block, monthly_fee, status, has_ac, has_attached_bathroom, created_at, updated_at)
SELECT 'B102','DOUBLE',2,0,1,'B',3500,'AVAILABLE',false,false,NOW(),NOW()
WHERE NOT EXISTS (SELECT 1 FROM rooms WHERE room_number = 'B102');

INSERT INTO rooms (room_number, room_type, capacity, occupied_count, floor, block, monthly_fee, status, has_ac, has_attached_bathroom, created_at, updated_at)
SELECT 'B103','TRIPLE',3,0,1,'B',2500,'AVAILABLE',false,false,NOW(),NOW()
WHERE NOT EXISTS (SELECT 1 FROM rooms WHERE room_number = 'B103');

INSERT INTO rooms (room_number, room_type, capacity, occupied_count, floor, block, monthly_fee, status, has_ac, has_attached_bathroom, created_at, updated_at)
SELECT 'B201','DOUBLE',2,0,2,'B',3500,'AVAILABLE',false,false,NOW(),NOW()
WHERE NOT EXISTS (SELECT 1 FROM rooms WHERE room_number = 'B201');

INSERT INTO rooms (room_number, room_type, capacity, occupied_count, floor, block, monthly_fee, status, has_ac, has_attached_bathroom, created_at, updated_at)
SELECT 'B202','TRIPLE',3,0,2,'B',2500,'AVAILABLE',false,false,NOW(),NOW()
WHERE NOT EXISTS (SELECT 1 FROM rooms WHERE room_number = 'B202');

INSERT INTO rooms (room_number, room_type, capacity, occupied_count, floor, block, monthly_fee, status, has_ac, has_attached_bathroom, created_at, updated_at)
SELECT 'C101','TRIPLE',3,0,1,'C',2500,'AVAILABLE',false,false,NOW(),NOW()
WHERE NOT EXISTS (SELECT 1 FROM rooms WHERE room_number = 'C101');

INSERT INTO rooms (room_number, room_type, capacity, occupied_count, floor, block, monthly_fee, status, has_ac, has_attached_bathroom, created_at, updated_at)
SELECT 'C102','DOUBLE',2,0,1,'C',3500,'AVAILABLE',false,false,NOW(),NOW()
WHERE NOT EXISTS (SELECT 1 FROM rooms WHERE room_number = 'C102');
