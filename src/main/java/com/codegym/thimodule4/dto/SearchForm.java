package com.codegym.thimodule4.dto;

import lombok.Data;
import java.math.BigDecimal;
import java.time.LocalDate;

/**
 * DTO class cho form tìm kiếm khuyến mãi
 */
@Data
public class SearchForm {
    
    /**
     * Mức giảm giá tối thiểu
     */
    private BigDecimal discountAmount;
    
    /**
     * Thời gian bắt đầu (từ ngày)
     */
    private LocalDate startTime;
    
    /**
     * Thời gian kết thúc (đến ngày)
     */
    private LocalDate endTime;
    
    /**
     * Constructor mặc định
     */
    public SearchForm() {}
    
    /**
     * Constructor với tham số
     */
    public SearchForm(BigDecimal discountAmount, LocalDate startTime, LocalDate endTime) {
        this.discountAmount = discountAmount;
        this.startTime = startTime;
        this.endTime = endTime;
    }
    
    /**
     * Kiểm tra form có được fill không
     */
    public boolean isEmpty() {
        return discountAmount == null && startTime == null && endTime == null;
    }
    
    /**
     * Reset form về trạng thái ban đầu
     */
    public void reset() {
        this.discountAmount = null;
        this.startTime = null;
        this.endTime = null;
    }
    
    /**
     * Kiểm tra có điều kiện tìm kiếm nào không
     */
    public boolean hasSearchCriteria() {
        return discountAmount != null || startTime != null || endTime != null;
    }
}
