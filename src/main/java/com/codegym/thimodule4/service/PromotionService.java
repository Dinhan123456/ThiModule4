package com.codegym.thimodule4.service;

import com.codegym.thimodule4.model.Promotion;

import java.time.LocalDate;
import java.util.List;

public interface PromotionService {
    
    // Lấy tất cả khuyến mãi
    List<Promotion> getAllPromotions();
    
    // Lấy khuyến mãi theo ID
    Promotion getPromotionById(Long id);
    
    // Lưu khuyến mãi mới
    Promotion savePromotion(Promotion promotion);
    
    // Cập nhật khuyến mãi
    Promotion updatePromotion(Long id, Promotion promotion);
    
    // Xóa khuyến mãi
    void deletePromotion(Long id);
    
    // Tìm kiếm theo mức giảm giá
    List<Promotion> searchByDiscountAmount(Integer discountAmount);
    
    // Tìm kiếm theo thời gian bắt đầu
    List<Promotion> searchByStartTime(LocalDate startTime);
    
    // Tìm kiếm theo thời gian kết thúc
    List<Promotion> searchByEndTime(LocalDate endTime);
    
    // Tìm kiếm dựa trên cả 3 điều kiện
    List<Promotion> searchByMultipleConditions(Integer discountAmount, LocalDate startTime, LocalDate endTime);
    
    // Tìm kiếm theo tiêu đề
    List<Promotion> searchByTitle(String title);
    
    // Validate ngày kết thúc phải lớn hơn ngày bắt đầu ít nhất 1 ngày
    boolean isValidDateRange(LocalDate startTime, LocalDate endTime);
}
