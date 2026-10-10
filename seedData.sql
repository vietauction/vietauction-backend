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
INSERT INTO bank (id, name, logo_url) VALUES
(1, 'Vietcombank', 'https://example.com/vcb-logo.png'),
(2, 'Techcombank', 'https://example.com/tcb-logo.png'),
(3, 'BIDV', 'https://example.com/bidv-logo.png');

-- 2. User
-- Hash: $2a$12$E4g4vqFSo/UmwyLPC4m.e.PtuBmD7I99lavzYAR3TK0XYXGt7klbS
INSERT INTO `user` (id, username, password, first_name, last_name, email, phone, address, city, district, ward, year_of_birth, cccd, cccd_from, state, role, bank_account_number, bank_account_name, bank_id, register_date) VALUES
(1, 'admin', '$2a$12$E4g4vqFSo/UmwyLPC4m.e.PtuBmD7I99lavzYAR3TK0XYXGt7klbS', 'Admin', 'System', 'admin@auction.vn', '0909123456', '123 Đường Láng', 'Hà Nội', 'Đống Đa', 'Láng Thượng', '1990', '012345678901', 'CA Hà Nội', 'VERIFIED', 'ADMIN', '1234567890', 'Nguyễn Văn Admin', 1, '2026-01-01 10:00:00'),
(2, 'staff1', '$2a$12$E4g4vqFSo/UmwyLPC4m.e.PtuBmD7I99lavzYAR3TK0XYXGt7klbS', 'Nguyễn', 'Thị Staff', 'staff1@auction.vn', '0912345678', '456 Lê Lợi', 'HCM', 'Quận 1', 'Bến Nghé', '1988', '098765432109', 'CA HCM', 'VERIFIED', 'STAFF', '9876543210', 'Nguyễn Thị Staff', 2, '2026-01-02 10:00:00'),
(3, 'manager1', '$2a$12$E4g4vqFSo/UmwyLPC4m.e.PtuBmD7I99lavzYAR3TK0XYXGt7klbS', 'Trần', 'Văn QL', 'seller1@gmail.com', '0987654321', '789 Nguyễn Huệ', 'HCM', 'Quận 1', 'Bến Thành', '1992', '112233445566', 'CA Bình Dương', 'ACTIVE', 'MANAGER', '1112223334', 'Trần Văn Quản Lý', 1, '2026-01-03 10:00:00'),
(4, 'bidder1', '$2a$12$E4g4vqFSo/UmwyLPC4m.e.PtuBmD7I99lavzYAR3TK0XYXGt7klbS', 'Lê', 'Thị Bidder', 'bidder1@gmail.com', '0935123456', '101 Pasteur', 'HCM', 'Quận 3', 'Phường 6', '1995', '223344556677', 'CA Đồng Nai', 'ACTIVE', 'MEMBER', '4445556667', 'Lê Thị Bidder', 3, '2026-01-04 10:00:00');