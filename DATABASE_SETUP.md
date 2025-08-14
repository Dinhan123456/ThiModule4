# Hướng dẫn thiết lập cơ sở dữ liệu - Hệ thống Quản lý Khuyến mãi Rạp Chiếu Phim

## 📋 Tổng quan

Tài liệu này hướng dẫn chi tiết cách thiết lập cơ sở dữ liệu MySQL cho hệ thống quản lý khuyến mãi rạp chiếu phim.

## 🗄️ Yêu cầu cơ sở dữ liệu

- **Loại cơ sở dữ liệu**: MySQL 8.0 trở lên
- **Bộ ký tự**: utf8mb4
- **Collation**: utf8mb4_unicode_ci
- **Cổng**: 3306 (mặc định)

## 🚀 Phương pháp thiết lập nhanh

### Phương pháp 1: Sử dụng file batch (Khuyến nghị cho người dùng Windows)

1. **Nhấp đúp chạy** `setup_database.bat`
2. Nhập tên người dùng và mật khẩu MySQL
3. Chờ quá trình thiết lập hoàn tất

### Phương pháp 2: Sử dụng script PowerShell

1. **Nhấp chuột phải** vào `setup_database.ps1`
2. Chọn "Chạy với PowerShell"
3. Nhập tên người dùng và mật khẩu MySQL
4. Chờ quá trình thiết lập hoàn tất

### Phương pháp 3: Thực thi script SQL thủ công

1. Mở dòng lệnh MySQL hoặc công cụ quản lý
2. Thực thi nội dung file `quick_setup.sql`

## 📁 Mô tả file

| Tên file | Mô tả | Mục đích |
|----------|-------|----------|
| `database_setup.sql` | Script thiết lập cơ sở dữ liệu đầy đủ | Bao gồm tất cả chức năng: view, stored procedure, trigger, index |
| `quick_setup.sql` | Script thiết lập đơn giản | Tạo nhanh cơ sở dữ liệu và bảng cơ bản |
| `setup_database.bat` | File batch Windows | Tự động thực thi thiết lập cơ sở dữ liệu |
| `setup_database.ps1` | Script PowerShell | Cách thiết lập cơ sở dữ liệu an toàn hơn |

## 🗂️ Cấu trúc cơ sở dữ liệu

### Bảng chính: `promotions`

| Tên trường | Kiểu dữ liệu | Mô tả | Ràng buộc |
|------------|--------------|-------|-----------|
| `id` | bigint | ID khóa chính | AUTO_INCREMENT, NOT NULL |
| `title` | varchar(255) | Tiêu đề khuyến mãi | NOT NULL |
| `start_time` | date | Thời gian bắt đầu | NOT NULL |
| `end_time` | date | Thời gian kết thúc | NOT NULL |
| `discount_amount` | int | Mức giảm giá (VNĐ) | NOT NULL |
| `details` | text | Chi tiết khuyến mãi | NOT NULL |
| `created_at` | datetime | Thời gian tạo | DEFAULT CURRENT_TIMESTAMP |
| `updated_at` | datetime | Thời gian cập nhật | ON UPDATE CURRENT_TIMESTAMP |

### Index

- `PRIMARY KEY` trên `id`
- `idx_start_time` trên `start_time`
- `idx_end_time` trên `end_time`
- `idx_discount_amount` trên `discount_amount`
- `idx_created_at` trên `created_at`

## 🔧 Các bước thiết lập thủ công

### Bước 1: Khởi động dịch vụ MySQL

```bash
# Windows
net start mysql

# Linux/Mac
sudo systemctl start mysql
# hoặc
sudo service mysql start
```

### Bước 2: Kết nối MySQL

```bash
mysql -u root -p
```

### Bước 3: Tạo cơ sở dữ liệu

```sql
CREATE DATABASE IF NOT EXISTS `movie_theater` 
CHARACTER SET utf8mb4 
COLLATE utf8mb4_unicode_ci;
```

### Bước 4: Sử dụng cơ sở dữ liệu

```sql
USE `movie_theater`;
```

### Bước 5: Tạo bảng

```sql
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
```

### Bước 6: Chèn dữ liệu mẫu

```sql
INSERT INTO `promotions` (`title`, `start_time`, `end_time`, `discount_amount`, `details`) VALUES
('Khuyến Mãi Năm Mới', '2025-03-04', '2025-03-10', 5000, 'Giảm giá chương trình'),
('Khuyến Mãi Valentine', '2025-04-04', '2025-04-10', 50000, 'Tết đến mọi người hãy đi xem phim đi'),
('Khuyến Mãi Sinh Nhật', '2025-04-16', '2025-04-23', 50000, 'Giảm giá vé');
```

## ⚠️ Các vấn đề thường gặp

### Vấn đề 1: Kết nối bị từ chối

**Giải pháp**:
- Kiểm tra dịch vụ MySQL có đang chạy không
- Xác nhận cổng 3306 có bị chiếm dụng không
- Kiểm tra cài đặt tường lửa

### Vấn đề 2: Truy cập bị từ chối

**Giải pháp**:
- Xác nhận tên người dùng và mật khẩu chính xác
- Kiểm tra quyền người dùng
- Thử sử dụng người dùng root

### Vấn đề 3: Lỗi bộ ký tự

**Giải pháp**:
- Đảm bảo sử dụng bộ ký tự utf8mb4
- Kiểm tra file cấu hình MySQL
- Tạo lại cơ sở dữ liệu

## 🔍 Xác minh thiết lập

Sau khi thiết lập hoàn tất, có thể chạy các lệnh sau để xác minh:

```sql
-- Hiển thị cơ sở dữ liệu
SHOW DATABASES;

-- Sử dụng cơ sở dữ liệu
USE movie_theater;

-- Hiển thị bảng
SHOW TABLES;

-- Hiển thị cấu trúc bảng
DESCRIBE promotions;

-- Truy vấn dữ liệu
SELECT * FROM promotions;
```

## 📱 Cấu hình ứng dụng

Đảm bảo cấu hình cơ sở dữ liệu trong `application.properties` chính xác:

```properties
spring.datasource.url=jdbc:mysql://localhost:3306/movie_theater?createDatabaseIfNotExist=true&useSSL=false&serverTimezone=UTC
spring.datasource.username=root
spring.datasource.password=mật_khẩu_của_bạn
```

## 🎯 Bước tiếp theo

Sau khi thiết lập cơ sở dữ liệu hoàn tất:

1. Chạy `start.bat` để khởi động ứng dụng
2. Truy cập `http://localhost:8080`
3. Bắt đầu sử dụng hệ thống quản lý khuyến mãi

## 📞 Hỗ trợ kỹ thuật

Nếu gặp vấn đề, hãy kiểm tra:

1. Tính tương thích phiên bản MySQL
2. Cài đặt kết nối mạng
3. Cấu hình quyền người dùng
4. Nhật ký ứng dụng

---

**Lưu ý**: Hãy đảm bảo sử dụng mật khẩu mạnh trong môi trường sản xuất và hạn chế quyền truy cập cơ sở dữ liệu.
