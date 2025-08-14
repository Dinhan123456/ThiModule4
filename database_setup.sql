-- ========================================
-- Script thiết lập cơ sở dữ liệu - Hệ thống Quản lý Khuyến mãi Rạp Chiếu Phim
-- ========================================

-- Tạo cơ sở dữ liệu
CREATE DATABASE IF NOT EXISTS `movie_theater` 
CHARACTER SET utf8mb4 
COLLATE utf8mb4_unicode_ci;

-- Sử dụng cơ sở dữ liệu
USE `movie_theater`;

-- Xóa bảng (nếu tồn tại)
DROP TABLE IF EXISTS `promotions`;

-- Tạo bảng khuyến mãi
CREATE TABLE `promotions` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `title` varchar(255) NOT NULL COMMENT 'Tiêu đề khuyến mãi',
  `start_time` date NOT NULL COMMENT 'Thời gian bắt đầu',
  `end_time` date NOT NULL COMMENT 'Thời gian kết thúc',
  `discount_amount` int NOT NULL COMMENT 'Mức giảm giá (VNĐ)',
  `details` text NOT NULL COMMENT 'Chi tiết khuyến mãi',
  `created_at` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT 'Thời gian tạo',
  `updated_at` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT 'Thời gian cập nhật',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='Bảng thông tin khuyến mãi';

-- Chèn dữ liệu mẫu vào bảng khuyến mãi
INSERT INTO `promotions` (`title`, `start_time`, `end_time`, `discount_amount`, `details`) VALUES
('Khuyến Mãi Năm Mới', '2025-03-04', '2025-03-10', 50000, 'Giảm giá đặc biệt cho dịp Tết Nguyên Đán.'),
('Khuyến Mãi Valentine', '2025-04-04', '2025-04-10', 75000, 'Ưu đãi lãng mạn cho các cặp đôi.'),
('Khuyến Mãi Sinh Nhật', '2025-04-16', '2025-04-23', 100000, 'Mừng sinh nhật rạp, giảm giá cực sốc!');

-- Tạo Index để cải thiện hiệu suất tìm kiếm
CREATE INDEX idx_promotions_title ON `promotions` (`title`);
CREATE INDEX idx_promotions_start_time ON `promotions` (`start_time`);
CREATE INDEX idx_promotions_end_time ON `promotions` (`end_time`);
CREATE INDEX idx_promotions_discount_amount ON `promotions` (`discount_amount`);

-- ========================================
-- Các đối tượng cơ sở dữ liệu nâng cao (Tùy chọn)
-- ========================================

-- 1. View: active_promotions (Các khuyến mãi đang diễn ra)
DROP VIEW IF EXISTS `active_promotions`;
CREATE VIEW `active_promotions` AS
SELECT
    id,
    title,
    start_time,
    end_time,
    discount_amount,
    details,
    created_at,
    updated_at
FROM
    `promotions`
WHERE
    start_time <= CURDATE() AND end_time >= CURDATE();

-- 2. View: upcoming_promotions (Các khuyến mãi sắp tới)
DROP VIEW IF EXISTS `upcoming_promotions`;
CREATE VIEW `upcoming_promotions` AS
SELECT
    id,
    title,
    start_time,
    end_time,
    discount_amount,
    details,
    created_at,
    updated_at
FROM
    `promotions`
WHERE
    start_time > CURDATE();

-- 3. Stored Procedure: GetPromotionStats (Lấy thống kê khuyến mãi)
DELIMITER //
DROP PROCEDURE IF EXISTS `GetPromotionStats`//
CREATE PROCEDURE `GetPromotionStats`()
BEGIN
    SELECT
        COUNT(*) AS total_promotions,
        SUM(CASE WHEN start_time <= CURDATE() AND end_time >= CURDATE() THEN 1 ELSE 0 END) AS active_promotions,
        SUM(CASE WHEN start_time > CURDATE() THEN 1 ELSE 0 END) AS upcoming_promotions,
        SUM(CASE WHEN end_time < CURDATE() THEN 1 ELSE 0 END) AS past_promotions,
        AVG(discount_amount) AS average_discount
    FROM
        `promotions`;
END //
DELIMITER ;

-- 4. Stored Procedure: SearchPromotions (Tìm kiếm khuyến mãi theo tiêu chí)
DELIMITER //
DROP PROCEDURE IF EXISTS `SearchPromotions`//
CREATE PROCEDURE `SearchPromotions`(
    IN p_title VARCHAR(255),
    IN p_min_discount INT,
    IN p_start_date DATE,
    IN p_end_date DATE
)
BEGIN
    SELECT
        id,
        title,
        start_time,
        end_time,
        discount_amount,
        details,
        created_at,
        updated_at
    FROM
        `promotions`
    WHERE
        (p_title IS NULL OR title LIKE CONCAT('%', p_title, '%')) AND
        (p_min_discount IS NULL OR discount_amount >= p_min_discount) AND
        (p_start_date IS NULL OR start_time >= p_start_date) AND
        (p_end_date IS NULL OR end_time <= p_end_date);
END //
DELIMITER ;

-- 5. Trigger: before_promotion_insert (Kiểm tra ngày trước khi chèn)
DELIMITER //
DROP TRIGGER IF EXISTS `before_promotion_insert`//
CREATE TRIGGER `before_promotion_insert`
BEFORE INSERT ON `promotions`
FOR EACH ROW
BEGIN
    IF NEW.end_time <= NEW.start_time THEN
        SIGNAL SQLSTATE '45000' SET MESSAGE_TEXT = 'Ngày kết thúc phải lớn hơn ngày bắt đầu.';
    END IF;
    SET NEW.created_at = NOW();
    SET NEW.updated_at = NOW();
END //
DELIMITER ;

-- 6. Trigger: before_promotion_update (Kiểm tra ngày trước khi cập nhật)
DELIMITER //
DROP TRIGGER IF EXISTS `before_promotion_update`//
CREATE TRIGGER `before_promotion_update`
BEFORE UPDATE ON `promotions`
FOR EACH ROW
BEGIN
    IF NEW.end_time <= NEW.start_time THEN
        SIGNAL SQLSTATE '45000' SET MESSAGE_TEXT = 'Ngày kết thúc phải lớn hơn ngày bắt đầu.';
    END IF;
    SET NEW.updated_at = NOW();
END //
DELIMITER ;

-- Kiểm tra dữ liệu
SELECT * FROM `promotions`;
