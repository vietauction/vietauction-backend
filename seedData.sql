USE DB_AUCTION;

-- This file must be imported as UTF-8 so Vietnamese account and catalogue text
-- is stored as utf8mb4. It is intentionally not executed by application startup.
SET NAMES utf8mb4 COLLATE utf8mb4_unicode_ci;

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

-- 3. Public catalogue categories and assets. These records are deterministic development data.
INSERT INTO product_category (id, name) VALUES
(1, 'Trang sức & Đá quý'),
(2, 'Nghệ thuật & Đồ cổ'),
(3, 'Bất động sản'),
(4, 'Phương tiện vận tải');

INSERT INTO product (id, name, buy_now_price, description, material, brand, weight, is_holding, received_date, create_date, state, user_id, category_id) VALUES
(1, 'Nhẫn kim cương Art Deco 3,2 carat', 980000000, 'Nhẫn kim cương kiểu Art Deco, hồ sơ kiểm định được lưu cùng tài sản.', 'DIAMOND', 'VietAuction Select', 3.2, TRUE, '2026-09-01 09:00:00', '2026-09-01 09:00:00', 'AUCTION', 3, 1),
(2, 'Dây chuyền vàng trắng đính kim cương', 520000000, 'Dây chuyền vàng trắng với điểm nhấn kim cương, có biên bản kiểm tra tình trạng.', 'GOLD', 'DOJI', 18.5, TRUE, '2026-09-02 09:00:00', '2026-09-02 09:00:00', 'AUCTION', 3, 1),
(3, 'Vòng ngọc cẩm thạch thiên nhiên', 340000000, 'Vòng ngọc cẩm thạch thiên nhiên được tiếp nhận kèm hồ sơ mô tả.', 'DIAMOND', 'VietAuction Select', 52.0, TRUE, '2026-09-03 09:00:00', '2026-09-03 09:00:00', 'AUCTION', 3, 1),
(4, 'Bình gốm men lam triều Nguyễn', 265000000, 'Bình gốm cổ với hồ sơ mô tả tình trạng và nguồn gốc tiếp nhận.', NULL, 'VietAuction Select', NULL, TRUE, '2026-09-04 09:00:00', '2026-09-04 09:00:00', 'AUCTION', 3, 2),
(5, 'Tranh sơn mài Phố cổ đầu thế kỷ XX', 185000000, 'Tác phẩm sơn mài được tiếp nhận cùng biên bản kiểm tra hiện trạng.', NULL, 'VietAuction Select', NULL, TRUE, '2026-09-05 09:00:00', '2026-09-05 09:00:00', 'AUCTION', 3, 2),
(6, 'Tượng Phật đồng thế kỷ XVIII', 410000000, 'Tượng đồng cổ, thông tin niên đại và tình trạng theo hồ sơ tài sản.', NULL, 'VietAuction Select', NULL, TRUE, '2026-09-06 09:00:00', '2026-09-06 09:00:00', 'AUCTION', 3, 2),
(7, 'Căn hộ ven sông tại Thảo Điền', 8700000000, 'Căn hộ ven sông với hồ sơ pháp lý và thông tin hiện trạng được công bố theo từng giai đoạn.', NULL, 'VietAuction Select', NULL, TRUE, '2026-09-07 09:00:00', '2026-09-07 09:00:00', 'AUCTION', 3, 3),
(8, 'Nhà phố thương mại trung tâm Quận 3', 23500000000, 'Nhà phố thương mại có hồ sơ pháp lý được ban tổ chức tiếp nhận.', NULL, 'VietAuction Select', NULL, TRUE, '2026-09-08 09:00:00', '2026-09-08 09:00:00', 'AUCTION', 3, 3),
(9, 'Quyền sử dụng đất tại Thủ Thiêm', 12500000000, 'Quyền sử dụng đất tại khu đô thị Thủ Thiêm, chi tiết công bố theo hồ sơ đấu giá.', NULL, 'VietAuction Select', NULL, TRUE, '2026-09-09 09:00:00', '2026-09-09 09:00:00', 'AUCTION', 3, 3),
(10, 'Mercedes-Benz S450 Luxury 2022', 3600000000, 'Xe Mercedes-Benz S450 Luxury sản xuất năm 2022, hồ sơ hiện trạng được tiếp nhận.', NULL, 'VietAuction Select', NULL, TRUE, '2026-09-10 09:00:00', '2026-09-10 09:00:00', 'AUCTION', 3, 4),
(11, 'Porsche Cayenne phiên bản 2021', 2800000000, 'Xe Porsche Cayenne phiên bản 2021, thông tin bảo dưỡng được cập nhật trong hồ sơ.', NULL, 'VietAuction Select', NULL, TRUE, '2026-09-11 09:00:00', '2026-09-11 09:00:00', 'AUCTION', 3, 4),
(12, 'Lô xe khách Universe Noble 47 chỗ', 4800000000, 'Lô xe khách 47 chỗ phục vụ vận tải, thông tin kỹ thuật công bố trong hồ sơ phiên.', NULL, 'VietAuction Select', NULL, TRUE, '2026-09-12 09:00:00', '2026-09-12 09:00:00', 'AUCTION', 3, 4);

INSERT INTO image (data, product_id) VALUES
('/assets/landing/products/1.jpg', 1),
('/assets/landing/products/4.jpg', 1),
('/assets/landing/products/5.jpg', 1),
('/assets/landing/products/4.jpg', 2),
('/assets/landing/products/5.jpg', 2),
('/assets/landing/products/1.jpg', 3),
('/assets/landing/products/5.jpg', 3),
('/assets/landing/products/5.jpg', 4),
('/assets/landing/products/1.jpg', 5),
('/assets/landing/products/4.jpg', 6),
('/assets/landing/products/5.jpg', 7),
('/assets/landing/products/1.jpg', 8),
('/assets/landing/products/4.jpg', 9),
('/assets/landing/products/5.jpg', 10),
('/assets/landing/products/1.jpg', 11),
('/assets/landing/products/4.jpg', 12);

INSERT INTO auction (id, name, start_date, end_date, start_price, last_price, deposit_price, step_price, state, product_id, staff_id) VALUES
(1, 'Phiên nhẫn kim cương Art Deco', '2026-10-10 09:00:00', '2026-10-20 17:00:00', 680000000, 920000000, 68000000, 10000000, 'ONGOING', 1, 2),
(2, 'Phiên dây chuyền vàng trắng', '2026-10-24 09:00:00', '2026-10-24 17:00:00', 420000000, NULL, 42000000, 10000000, 'WAITING', 2, 2),
(3, 'Phiên vòng ngọc cẩm thạch', '2026-10-28 09:00:00', '2026-10-28 17:00:00', 260000000, NULL, 26000000, 5000000, 'WAITING', 3, 2),
(4, 'Phiên bình gốm men lam', '2026-10-08 09:00:00', '2026-10-09 17:00:00', 190000000, 265000000, 19000000, 5000000, 'FINISHED', 4, 2),
(5, 'Phiên tranh sơn mài Phố cổ', '2026-10-11 09:00:00', '2026-10-21 17:00:00', 150000000, 185000000, 15000000, 5000000, 'ONGOING', 5, 2),
(6, 'Phiên tượng Phật đồng', '2026-10-30 09:00:00', '2026-10-30 17:00:00', 330000000, NULL, 33000000, 10000000, 'WAITING', 6, 2),
(7, 'Phiên căn hộ Thảo Điền', '2026-11-02 09:00:00', '2026-11-02 17:00:00', 6200000000, NULL, 620000000, 100000000, 'WAITING', 7, 2),
(8, 'Phiên nhà phố Quận 3', '2026-10-01 09:00:00', '2026-10-05 17:00:00', 21000000000, 23500000000, 2100000000, 100000000, 'FINISHED', 8, 2),
(9, 'Phiên đất Thủ Thiêm', '2026-11-05 09:00:00', '2026-11-05 17:00:00', 11000000000, NULL, 1100000000, 100000000, 'WAITING', 9, 2),
(10, 'Phiên Mercedes-Benz S450', '2026-10-03 09:00:00', '2026-10-07 17:00:00', 3000000000, 3600000000, 300000000, 50000000, 'FINISHED', 10, 2),
(11, 'Phiên Porsche Cayenne', '2026-11-08 09:00:00', '2026-11-08 17:00:00', 2400000000, NULL, 240000000, 50000000, 'WAITING', 11, 2),
(12, 'Phiên lô xe Universe Noble', '2026-10-12 09:00:00', '2026-10-22 17:00:00', 4200000000, NULL, 420000000, 100000000, 'PAUSED', 12, 2);

INSERT INTO auction_history (price_given, time, bid_code, state, auction_id, user_id) VALUES
(700000000, '2026-10-10 10:00:00', 'BID-0001', 'ACTIVE', 1, 4),
(810000000, '2026-10-10 11:00:00', 'BID-0002', 'ACTIVE', 1, 4),
(920000000, '2026-10-10 12:00:00', 'BID-0003', 'ACTIVE', 1, 4),
(165000000, '2026-10-11 10:00:00', 'BID-0004', 'ACTIVE', 5, 4),
(185000000, '2026-10-11 11:00:00', 'BID-0005', 'ACTIVE', 5, 4),
(220000000, '2026-10-08 10:00:00', 'BID-0006', 'ACTIVE', 4, 4),
(265000000, '2026-10-09 11:00:00', 'BID-0007', 'ACTIVE', 4, 4),
(600000000, '2026-10-03 10:00:00', 'BID-0008', 'ACTIVE', 8, 4),
(680000000, '2026-10-04 11:00:00', 'BID-0009', 'ACTIVE', 8, 4),
(135000000, '2026-10-03 10:00:00', 'BID-0010', 'ACTIVE', 10, 4),
(155000000, '2026-10-06 11:00:00', 'BID-0011', 'ACTIVE', 10, 4);
