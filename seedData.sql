USE DB_AUCTION;

SET FOREIGN_KEY_CHECKS = 0;
TRUNCATE TABLE token;
TRUNCATE TABLE auction_history;
TRUNCATE TABLE auction_registration;
TRUNCATE TABLE `transaction`;
TRUNCATE TABLE auction;
TRUNCATE TABLE request_approval;
TRUNCATE TABLE image;
TRUNCATE TABLE product;
TRUNCATE TABLE `user`;
TRUNCATE TABLE product_category;
TRUNCATE TABLE bank;
SET FOREIGN_KEY_CHECKS = 1;

-- 1. Bank
INSERT INTO bank (name, logo_url) VALUES
('Vietcombank', 'https://example.com/vcb-logo.png'),
('Techcombank', 'https://example.com/tcb-logo.png'),
('BIDV', 'https://example.com/bidv-logo.png');

-- 2. User
-- Hash: $2a$12$E4g4vqFSo/UmwyLPC4m.e.PtuBmD7I99lavzYAR3TK0XYXGt7klbS
INSERT INTO `user` (username, password, first_name, last_name, email, phone, address, city, district, ward, year_of_birth, cccd, cccd_from, state, role, bank_account_number, bank_account_name, bank_id, register_date) VALUES
('admin', '$2a$12$E4g4vqFSo/UmwyLPC4m.e.PtuBmD7I99lavzYAR3TK0XYXGt7klbS', 'Admin', 'System', 'admin@auction.vn', '0909123456', '123 Đường Láng', 'Hà Nội', 'Đống Đa', 'Láng Thượng', '1990', '012345678901', 'CA Hà Nội', 'VERIFIED', 'ADMIN', '1234567890', 'Nguyễn Văn Admin', 1, '2026-01-01 10:00:00'),
('staff1', '$2a$12$E4g4vqFSo/UmwyLPC4m.e.PtuBmD7I99lavzYAR3TK0XYXGt7klbS', 'Nguyễn', 'Thị Staff', 'staff1@auction.vn', '0912345678', '456 Lê Lợi', 'HCM', 'Quận 1', 'Bến Nghé', '1988', '098765432109', 'CA HCM', 'VERIFIED', 'STAFF', '9876543210', 'Nguyễn Thị Staff', 2, '2026-01-02 10:00:00'),
('manager1', '$2a$12$E4g4vqFSo/UmwyLPC4m.e.PtuBmD7I99lavzYAR3TK0XYXGt7klbS', 'Trần', 'Văn QL', 'seller1@gmail.com', '0987654321', '789 Nguyễn Huệ', 'HCM', 'Quận 1', 'Bến Thành', '1992', '112233445566', 'CA Bình Dương', 'ACTIVE', 'MANAGER', '1112223334', 'Trần Văn Quản Lý', 1, '2026-01-03 10:00:00'),
('bidder1', '$2a$12$E4g4vqFSo/UmwyLPC4m.e.PtuBmD7I99lavzYAR3TK0XYXGt7klbS', 'Lê', 'Thị Bidder', 'bidder1@gmail.com', '0935123456', '101 Pasteur', 'HCM', 'Quận 3', 'Phường 6', '1995', '223344556677', 'CA Đồng Nai', 'ACTIVE', 'MEMBER', '4445556667', 'Lê Thị Bidder', 3, '2026-01-04 10:00:00');

-- 3. Product Category
INSERT INTO product_category (name) VALUES
('Nhẫn cưới'), ('Dây chuyền vàng'), ('Lắc tay kim cương');

-- 4. Product
INSERT INTO product (name, buy_now_price, description, material, brand, weight, is_holding, received_date, create_date, state, user_id, category_id) VALUES
('Nhẫn cưới vàng 18K đính kim cương', 45000000.00, 'Nhẫn cưới thiết kế tinh tế, vàng 18K', 'SILVER', 'PNJ', 5.2, 0, '2026-02-15 10:00:00', '2026-02-10 09:00:00', 'ACTIVE', 3, 1),
('Dây chuyền vàng trắng', 32000000.00, 'Dây chuyền vàng trắng 14K ngọc trai', 'GOLD', 'DOJI', 18.5, 1, '2026-03-01 14:30:00', '2026-02-20 11:00:00', 'ACTIVE', 3, 2);

-- 5. Image
INSERT INTO image (data, product_id) VALUES
('https://images.unsplash.com/photo-1', 1),
('https://images.unsplash.com/photo-2', 1),
('https://images.unsplash.com/photo-3', 2);

-- 6. Request Approval
INSERT INTO request_approval (valuation, desired_price, request_time, response_time, state, is_confirm, note, staff_id, user_id_send, user_id_respond, product_id) VALUES
(42000000.00, 45000000.00, '2026-02-12 15:00:00', '2026-02-14 09:30:00', 'ACTIVE', 1, 'Đánh giá tốt', 2, 3, 2, 1),
(30000000.00, 32000000.00, '2026-03-02 10:00:00', NULL, 'HIDDEN', 0, NULL, NULL, 3, NULL, 2);

-- 7. Auction
INSERT INTO auction (name, start_price, last_price, deposit_price, step_price, start_date, end_date, end_date_stored, staff_id, product_id, state) VALUES
('Phiên đấu giá nhẫn cưới kim cương', 30000000.00, NULL, 5000000.00, 1000000.00, '2026-03-15 20:00:00', '2026-03-16 22:00:00', '2026-03-16 22:00:00', 2, 1, 'WAITING');

-- 8. Transaction
INSERT INTO `transaction` (create_date, payment_time, total_price, fees_incurred, transaction_state, transaction_type, transaction_code, bank_code, payment_method, auction_id, user_id) VALUES
('2026-03-10 12:00:00', '2026-03-10 12:05:00', 500000.00, 0.00, 'SUCCEED', 'REGISTRATION', 'TXN001', 'VCB', 'BANKING', 1, 4),
('2026-03-10 14:30:00', NULL, 10000000.00, 500000.00, 'PENDING', 'PAYMENT_TO_SELLER', 'TXN002', 'TCB', 'BANKING', 1, 4);

-- 9. Auction Registration
INSERT INTO auction_registration (registration_fee, state, user_id, auction_id) VALUES
(500000.00, 'VALID', 4, 1);

-- 10. Auction History
INSERT INTO auction_history (price_given, time, bid_code, state, user_id, auction_id) VALUES
(31000000.00, '2026-03-15 20:05:00', 'BID001', 'ACTIVE', 4, 1),
(32500000.00, '2026-03-15 20:10:00', 'BID002', 'ACTIVE', 3, 1);