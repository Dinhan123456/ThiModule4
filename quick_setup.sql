-- ========================================
-- Script thiết lập nhanh cơ sở dữ liệu - Hệ thống Quản lý Khuyến mãi Rạp Chiếu Phim
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
('Khuyến Mãi Năm Mới', '2025-03-04', '2025-03-10', 5000, 'Giảm giá chương trình'),
('Khuyến Mãi Valentine', '2025-04-04', '2025-04-10', 50000, 'Tết đến mọi người hãy đi xem phim đi'),
('Khuyến Mãi Sinh Nhật', '2025-04-16', '2025-04-23', 50000, 'Giảm giá vé');

DESCRIBE `promotions`;
SELECT * FROM `promotions` ORDER BY start_time;
