# Ứng dụng Quản lý Khuyến mãi Rạp Chiếu Phim

## Mô tả
Ứng dụng web quản lý thông tin khuyến mãi cho rạp chiếu phim, được phát triển bằng Spring Boot và Thymeleaf.

## Tính năng chính

### 1. Quản lý danh sách khuyến mãi
- Hiển thị tất cả chương trình khuyến mãi từ cơ sở dữ liệu
- Nút "Thêm khuyến mãi" để mở màn hình thêm mới
- Nút "Sửa" và "Xóa" cho mỗi khuyến mãi

### 2. Thêm mới khuyến mãi
- Form nhập liệu với các trường bắt buộc
- Validate dữ liệu đầu vào:
  - Tất cả trường đều bắt buộc
  - Thời gian bắt đầu phải lớn hơn thời gian hiện tại
  - Thời gian kết thúc phải lớn hơn thời gian bắt đầu ít nhất 1 ngày
  - Mức giảm giá phải lớn hơn 10,000 VNĐ

### 3. Chỉnh sửa khuyến mãi
- Form chỉnh sửa với dữ liệu hiện tại
- Validate tương tự như thêm mới

### 4. Xóa khuyến mãi
- Hiển thị popup xác nhận với tên khuyến mãi
- Xóa khuyến mãi sau khi xác nhận

### 5. Tìm kiếm khuyến mãi
- Tìm kiếm theo mức giảm giá
- Tìm kiếm theo thời gian bắt đầu
- Tìm kiếm theo thời gian kết thúc
- Tìm kiếm dựa trên cả 3 điều kiện

## Công nghệ sử dụng

- **Backend**: Spring Boot 3.5.4, Spring Data JPA, Spring Validation
- **Cơ sở dữ liệu**: MySQL 8.0
- **Frontend**: Thymeleaf, Bootstrap 5, Font Awesome
- **Build Tool**: Gradle
- **Phiên bản Java**: 17

## Cài đặt và chạy

### Yêu cầu hệ thống
- Java 17 trở lên
- MySQL 8.0 trở lên
- Gradle 7.0 trở lên

### Bước 1: Cài đặt cơ sở dữ liệu
1. Tạo cơ sở dữ liệu MySQL tên `movie_theater`
2. Cập nhật thông tin kết nối trong `application.properties`

### Bước 2: Chạy ứng dụng
```bash
# Clone repository
git clone <repository-url>
cd ThiModule4

# Chạy ứng dụng
./gradlew bootRun
```

### Bước 3: Truy cập ứng dụng
Mở trình duyệt và truy cập: `http://localhost:8080`

## Cấu trúc dự án

```
src/
├── main/
│   ├── java/
│   │   └── com/codegym/thimodule4/
│   │       ├── controller/
│   │       │   ├── HomeController.java
│   │       │   └── PromotionController.java
│   │       ├── entity/
│   │       │   └── Promotion.java
│   │       ├── repository/
│   │       │   └── PromotionRepository.java
│   │       ├── service/
│   │       │   ├── PromotionService.java
│   │       │   └── impl/
│   │       │       └── PromotionServiceImpl.java
│   │       ├── config/
│   │       │   └── DataInitializer.java
│   │       └── ThiModule4Application.java
│   └── resources/
│       ├── templates/
│       │   ├── layout/
│       │   │   └── base.html
│       │   └── promotion/
│       │       ├── list.html
│       │       ├── add.html
│       │       └── edit.html
│       └── application.properties
```

## API Endpoints

- `GET /` - Chuyển hướng đến danh sách khuyến mãi
- `GET /promotions` - Hiển thị danh sách khuyến mãi
- `GET /promotions/add` - Hiển thị form thêm mới
- `POST /promotions/add` - Xử lý thêm mới khuyến mãi
- `GET /promotions/edit/{id}` - Hiển thị form chỉnh sửa
- `POST /promotions/edit/{id}` - Xử lý chỉnh sửa khuyến mãi
- `POST /promotions/delete/{id}` - Xóa khuyến mãi
- `POST /promotions/search` - Tìm kiếm khuyến mãi

## Dữ liệu mẫu

Ứng dụng sẽ tự động tạo 3 khuyến mãi mẫu khi khởi động:
1. Khuyến Mãi Năm Mới (5,000 VNĐ)
2. Khuyến Mãi Valentine (50,000 VNĐ)
3. Khuyến Mãi Sinh Nhật (50,000 VNĐ)

## Tính năng bảo mật

- Validate dữ liệu đầu vào ở cả frontend và backend
- Kiểm tra quyền truy cập và xác thực người dùng
- Bảo vệ chống SQL injection

## Hỗ trợ

Nếu có vấn đề hoặc câu hỏi, vui lòng liên hệ:
- Email: support@codegym.vn
- Website: www.codegym.vn

## License

Dự án này được phát triển cho mục đích học tập và nghiên cứu.
