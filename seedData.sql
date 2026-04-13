-- seed_data.sql - Dữ liệu mẫu hoàn chỉnh, đã sửa syntax lỗi 1064 (update 2026)
-- Chạy theo thứ tự để tránh lỗi foreign key
-- Sử dụng MySQL Workbench hoặc terminal: mysql -u root -p DB_AUCTION < seed_data.sql

USE DB_AUCTION;

-- Optional: Xóa dữ liệu cũ nếu muốn reset hoàn toàn (chạy theo thứ tự ngược FK)
-- TRUNCATE TABLE token;
-- TRUNCATE TABLE auction_history;
-- TRUNCATE TABLE auction_registration;
-- TRUNCATE TABLE transaction;
-- TRUNCATE TABLE auction;
-- TRUNCATE TABLE request_approval;
-- TRUNCATE TABLE image;
-- TRUNCATE TABLE jewelry;
-- TRUNCATE TABLE user;
-- TRUNCATE TABLE jewelry_category;
-- TRUNCATE TABLE bank;

-- 1. Bank
INSERT INTO bank (bank_name, trading_name, logo) VALUES
                                                     ('Vietcombank', 'Ngân hàng TMCP Ngoại thương Việt Nam', 'https://example.com/vcb-logo.png'),
                                                     ('Techcombank', 'Ngân hàng TMCP Kỹ Thương Việt Nam', 'https://example.com/tcb-logo.png'),
                                                     ('BIDV', 'Ngân hàng Đầu tư và Phát triển Việt Nam', 'https://example.com/bidv-logo.png');

-- 2. Jewelry Category
INSERT INTO jewelry_category (name) VALUES
                                        ('Nhẫn cưới'),
                                        ('Dây chuyền vàng'),
                                        ('Lắc tay kim cương'),
                                        ('Bông tai ngọc trai'),
                                        ('Đồng hồ cao cấp');

-- 3. User (password hash chung cho tất cả để test login dễ dàng)
-- Hash: $2a$12$E4g4vqFSo/UmwyLPC4m.e.PtuBmD7I99lavzYAR3TK0XYXGt7klbS
-- Có thể decode thử với tool online nếu cần biết plain text gốc (thường là password mạnh)
INSERT INTO user (username, password, first_name, last_name, email, phone, address, city, district, ward, year_of_birth, avatar, cccd, cccd_first, cccd_last, cccd_from, state, bank_id, bank_account_number, bank_account_name, role) VALUES
                                                                                                                                                                                                                                 ('admin', '$2a$12$E4g4vqFSo/UmwyLPC4m.e.PtuBmD7I99lavzYAR3TK0XYXGt7klbS', 'Admin', 'System', 'admin@auction.vn', '0909123456', '123 Đường Láng', 'Hà Nội', 'Đống Đa', 'Láng Thượng', '1990', NULL, '012345678901', NULL, NULL, 'CA Hà Nội', 'VERIFIED', 1, '1234567890', 'Nguyễn Văn Admin', 'ADMIN'),
                                                                                                                                                                                                                                 ('staff1', '$2a$12$E4g4vqFSo/UmwyLPC4m.e.PtuBmD7I99lavzYAR3TK0XYXGt7klbS', 'Nguyễn', 'Thị Staff', 'staff1@auction.vn', '0912345678', '456 Lê Lợi', 'HCM', 'Quận 1', 'Bến Nghé', '1988', NULL, '098765432109', NULL, NULL, 'CA HCM', 'VERIFIED', 2, '9876543210', 'Nguyễn Thị Staff', 'STAFF'),
                                                                                                                                                                                                                                 ('manager1', '$2a$12$E4g4vqFSo/UmwyLPC4m.e.PtuBmD7I99lavzYAR3TK0XYXGt7klbS', 'Trần', 'Văn QL', 'seller1@gmail.com', '0987654321', '789 Nguyễn Huệ', 'HCM', 'Quận 1', 'Bến Thành', '1992', NULL, '112233445566', NULL, NULL, 'CA Bình Dương', 'ACTIVE', 1, '1112223334', 'Trần Văn Quản Lý', 'MANAGER'),
                                                                                                                                                                                                                                 ('bidder1', '$2a$12$E4g4vqFSo/UmwyLPC4m.e.PtuBmD7I99lavzYAR3TK0XYXGt7klbS', 'Lê', 'Thị Bidder', 'bidder1@gmail.com', '0935123456', '101 Pasteur', 'HCM', 'Quận 3', 'Phường 6', '1995', NULL, '223344556677', NULL, NULL, 'CA Đồng Nai', 'ACTIVE', 3, '4445556667', 'Lê Thị Bidder', 'MEMBER');

-- 4. Jewelry (trang sức của seller1)
INSERT INTO jewelry (name, buy_now_price, description, material, brand, weight, is_holding, received_date, delivery_date, create_date, state, user_id, category_id) VALUES
                                                                                                                                                                        ('Nhẫn cưới vàng 18K đính kim cương solitaire', 45000000.00, 'Nhẫn cưới thiết kế tinh tế, vàng 18K, kim cương 0.5ct solitaire, PNJ style', 'SILVER', 'PNJ', 5.2, FALSE, '2026-02-15 10:00:00', NULL, '2026-02-10 09:00:00', 'ACTIVE', 3, 1),
                                                                                                                                                                        ('Dây chuyền vàng trắng đính ngọc trai tự nhiên', 32000000.00, 'Dây chuyền vàng trắng 14K với ngọc trai baroque cao cấp, DOJI style', 'SILVER', 'DOJI', 18.5, TRUE, '2026-03-01 14:30:00', NULL, '2026-02-20 11:00:00', 'ACTIVE', 3, 2);

-- 5. Image (ảnh thực tế từ Unsplash/Pexels, syntax sạch)
DELETE FROM image WHERE jewelry_id IN (1, 2);  -- Xóa cũ nếu có

INSERT INTO image (icon, link, data, jewelry_id) VALUES
-- Jewelry 1: Nhẫn cưới vàng kim cương
(TRUE, 'https://unsplash.com/photos/two-gold-wedding-rings-with-diamonds-on-petals-afmciNtfRkA/download?force=true', NULL, 1),
(FALSE, 'https://unsplash.com/photos/a-gold-ring-with-diamonds-on-blue-silk-qpB9-iY7hkk/download?force=true', NULL, 1),
(FALSE, 'https://images.pexels.com/photos/265870/pexels-photo-265870.jpeg?auto=compress&cs=tinysrgb&w=1260&h=750&dpr=2', NULL, 1)

-- Jewelry 2: Dây chuyền ngọc trai vàng trắng
(TRUE, 'https://unsplash.com/photos/gold-colored-white-pearl-pendant-necklace-jSw10p2-L3I/download?force=true', NULL, 2),
(FALSE, 'https://unsplash.com/photos/woman-in-white-pearl-necklace-E4hJoFywAtU/download?force=true', NULL, 2),
(FALSE, 'https://images.pexels.com/photos/1454178/pexels-photo-1454178.jpeg?auto=compress&cs=tinysrgb&w=1260&h=750&dpr=2', NULL, 2);

-- 6. Request Approval
INSERT INTO request_approval (valuation, desired_price, request_time, response_time, state, is_confirm, note, staff_id, sender_id, responder_id, jewelry_id) VALUES
                                                                                                                                                                 (42000000.00, 45000000.00, '2026-02-12 15:00:00', '2026-02-14 09:30:00', 'APPROVED', TRUE, 'Đánh giá tốt, kim cương đạt chuẩn', 2, 3, 2, 1),
                                                                                                                                                                 (30000000.00, 32000000.00, '2026-03-02 10:00:00', NULL, 'PENDING', FALSE, NULL, NULL, 3, NULL, 2);

-- 7. Auction
INSERT INTO auction (name, description, first_price, last_price, participation_fee, deposit, price_step, create_date, start_date, end_date, end_date_stored, staff_id, jewelry_id, state) VALUES
    ('Phiên đấu giá nhẫn cưới kim cương PNJ', 'Nhẫn cưới vàng 18K đính kim cương 0.5ct - Giá khởi điểm hấp dẫn', 30000000.00, NULL, 500000.00, 5000000.00, 1000000.00, '2026-02-20 10:00:00', '2026-03-15 20:00:00', '2026-03-16 22:00:00', '2026-03-16 22:00:00', 2, 1, 'WAITING');

-- 8. Transaction
INSERT INTO transaction (create_date, payment_time, total_price, fees_incurred, state, type, transaction_code, bank_code, payment_method, auction_id, user_id) VALUES
                                                                                                                                                                   ('2026-03-10 12:00:00', '2026-03-10 12:05:00', 500000.00, 0.00, 'COMPLETED', 'REGISTRATION_FEE', 'TXN001', 'VCB', 'BANK_TRANSFER', 1, 4),
                                                                                                                                                                   ('2026-03-10 14:30:00', NULL, 10000000.00, 500000.00, 'PENDING', 'DEPOSIT', 'TXN002', 'TCB', 'VNPAY', 1, 4);

-- 9. Auction Registration
INSERT INTO auction_registration (registration_fee, registration_date, auction_registration_state, kick_reason, user_id, auction_id, transaction_id) VALUES
    (500000.00, '2026-03-10 12:10:00', 'APPROVED', NULL, 4, 1, 1);

-- 10. Auction History
INSERT INTO auction_history (price_given, time, bid_code, state, user_id, auction_id) VALUES
                                                                                          (31000000.00, '2026-03-15 20:05:00', 'BID001', 'ACTIVE', 4, 1),
                                                                                          (32500000.00, '2026-03-15 20:10:00', 'BID002', 'ACTIVE', 3, 1);

-- 11. Token (mẫu)
INSERT INTO token (token, refresh_token, token_type, revoked, expired, created_time, ip_address, device_info, user_id) VALUES
    ('eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.sample-token-for-testing', 'refresh-token-sample-123456789', 'BEARER', FALSE, FALSE, '2026-03-10 18:00:00', '127.0.0.1', 'Chrome on Mac - Test', 1);