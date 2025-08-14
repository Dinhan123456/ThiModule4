# 🚀 Hướng dẫn khởi động nhanh - Hệ thống Quản lý Khuyến mãi Rạp Chiếu Phim

## 📋 Yêu cầu hệ thống

- **Hệ điều hành**: Windows 10/11, Linux, macOS
- **Java**: JDK 17 trở lên
- **Cơ sở dữ liệu**: MySQL 8.0 trở lên
- **Bộ nhớ**: Tối thiểu 2GB RAM
- **Dung lượng ổ đĩa**: Tối thiểu 500MB

## ⚡ Khởi động nhanh trong 5 phút

### Bước 1: Kiểm tra môi trường

```bash
# Kiểm tra phiên bản Java
java -version

# Kiểm tra phiên bản MySQL
mysql --version

# Kiểm tra phiên bản Gradle
./gradlew --version
```

### Bước 2: Thiết lập cơ sở dữ liệu

#### Phương pháp A: Thiết lập tự động (Khuyến nghị)
```bash
# Windows
Nhấp đúp chạy: setup_database.bat

# PowerShell
Nhấp chuột phải: setup_database.ps1 → "Chạy với PowerShell"
```

#### Phương pháp B: Thiết lập thủ công
```bash
# Kết nối MySQL
mysql -u root -p

# Thực thi script SQL
source quick_setup.sql
```

### Bước 3: Khởi động ứng dụng

```bash
# Windows
Nhấp đúp chạy: start.bat

# Dòng lệnh
./gradlew bootRun
```

### Bước 4: Truy cập hệ thống

Mở trình duyệt và truy cập: **http://localhost:8080**

## 🔧 Cấu hình chi tiết

### Cấu hình cơ sở dữ liệu

Chỉnh sửa `src/main/resources/application.properties`:

```properties
# Kết nối cơ sở dữ liệu
spring.datasource.url=jdbc:mysql://localhost:3306/movie_theater?createDatabaseIfNotExist=true&useSSL=false&serverTimezone=UTC
spring.datasource.username=root
spring.datasource.password=mật_khẩu_MySQL_của_bạn

# Cài đặt JPA
spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true
```

### Cấu hình cổng

```properties
# Thay đổi cổng (mặc định 8080)
server.port=8080
```

## 📁 Cấu trúc dự án

```
ThiModule4/
├── src/main/java/com/codegym/thimodule4/
│   ├── controller/          # Bộ điều khiển
│   ├── entity/             # Lớp thực thể
│   ├── repository/         # Lớp truy cập dữ liệu
│   ├── service/            # Lớp logic nghiệp vụ
│   └── config/             # Lớp cấu hình
├── src/main/resources/
│   ├── templates/          # Mẫu trang
│   └── application.properties
├── database_setup.sql      # Script cơ sở dữ liệu đầy đủ
├── quick_setup.sql         # Script thiết lập nhanh
├── setup_database.bat      # Thiết lập cơ sở dữ liệu Windows
├── setup_database.ps1      # Thiết lập cơ sở dữ liệu PowerShell
├── start.bat               # Script khởi động
└── README.md               # Hướng dẫn chi tiết
```

## 🎯 Tính năng

### ✅ Các chức năng đã thực hiện

1. **Quản lý danh sách khuyến mãi**
   - Hiển thị tất cả thông tin khuyến mãi
   - Thêm, chỉnh sửa, xóa khuyến mãi
   - Chức năng tìm kiếm và lọc

2. **Xác thực dữ liệu**
   - Xác thực các trường bắt buộc
   - Xác thực phạm vi ngày tháng
   - Xác thực mức giảm giá

3. **Giao diện người dùng**
   - Thiết kế đáp ứng
   - Kiểu dáng Bootstrap 5
   - Giao diện hoạt động trực quan

4. **Chức năng tìm kiếm**
   - Tìm kiếm theo mức giảm giá
   - Tìm kiếm theo phạm vi thời gian
   - Tìm kiếm theo điều kiện kết hợp

## 🚨 Xử lý sự cố

### Các vấn đề thường gặp

| Vấn đề | Giải pháp |
|--------|-----------|
| Cổng bị chiếm dụng | Thay đổi `server.port` trong `application.properties` |
| Kết nối cơ sở dữ liệu thất bại | Kiểm tra trạng thái dịch vụ MySQL và thông tin kết nối |
| Lỗi phiên bản Java | Đảm bảo sử dụng JDK 17 trở lên |
| Quyền không đủ | Chạy script với quyền quản trị viên |

### Xem nhật ký

```bash
# Xem nhật ký ứng dụng
tail -f logs/application.log

# Xem nhật ký lỗi
tail -f logs/error.log
```

## 📊 Dữ liệu kiểm tra

Sau khi khởi động, hệ thống sẽ tự động tạo dữ liệu mẫu:

- **Khuyến Mãi Năm Mới**: 5,000 VNĐ
- **Khuyến Mãi Valentine**: 50,000 VNĐ  
- **Khuyến Mãi Sinh Nhật**: 50,000 VNĐ

## 🔒 Lưu ý bảo mật

1. **Môi trường sản xuất**
   - Thay đổi mật khẩu mặc định
   - Hạn chế quyền truy cập cơ sở dữ liệu
   - Bật HTTPS

2. **Môi trường phát triển**
   - Sử dụng cơ sở dữ liệu kiểm tra
   - Không gửi thông tin nhạy cảm
   - Sao lưu dữ liệu định kỳ

## 📞 Nhận trợ giúp

### Tài nguyên tài liệu
- `README.md` - Hướng dẫn chi tiết dự án
- `DATABASE_SETUP.md` - Hướng dẫn thiết lập cơ sở dữ liệu
- `QUICK_START.md` - Hướng dẫn khởi động nhanh

### Hỗ trợ kỹ thuật
- Kiểm tra file nhật ký
- Xem đầu ra console
- Tham khảo tài liệu chính thức Spring Boot

## 🎉 Chúc mừng!

Bây giờ bạn đã khởi động thành công hệ thống quản lý khuyến mãi rạp chiếu phim!

### Gợi ý bước tiếp theo

1. **Làm quen với giao diện**: Duyệt qua các trang chức năng
2. **Thêm dữ liệu**: Tạo hoạt động khuyến mãi mới
3. **Kiểm tra chức năng**: Xác minh tất cả chức năng hoạt động bình thường
4. **Tùy chỉnh cấu hình**: Điều chỉnh cài đặt hệ thống theo nhu cầu

---

**Hãy tận hưởng hệ thống quản lý khuyến mãi của bạn!** 🎬✨
