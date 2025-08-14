package com.codegym.thimodule4.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "promotions")
public class Promotion {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @NotBlank(message = "Tiêu đề không được để trống")
    @Size(min = 1, max = 255, message = "Tiêu đề phải từ 1-255 ký tự")
    @Column(name = "title", nullable = false)
    private String title;
    
    @NotNull(message = "Thời gian bắt đầu không được để trống")
    @Future(message = "Thời gian bắt đầu phải lớn hơn thời gian hiện tại")
    @DateTimeFormat(pattern = "dd/MM/yyyy")
    @Column(name = "start_time", nullable = false)
    private LocalDate startTime;
    
    @NotNull(message = "Thời gian kết thúc không được để trống")
    @Future(message = "Thời gian kết thúc phải lớn hơn thời gian hiện tại")
    @DateTimeFormat(pattern = "dd/MM/yyyy")
    @Column(name = "end_time", nullable = false)
    private LocalDate endTime;
    
    @NotNull(message = "Mức giảm giá không được để trống")
    @Min(value = 10000, message = "Mức giảm giá phải lớn hơn 10,000 VNĐ")
    @Column(name = "discount_amount", nullable = false)
    private Integer discountAmount;
    
    @NotBlank(message = "Chi tiết không được để trống")
    @Size(min = 1, max = 1000, message = "Chi tiết phải từ 1-1000 ký tự")
    @Column(name = "details", nullable = false, columnDefinition = "TEXT")
    private String details;
    
    @Column(name = "created_at")
    private LocalDateTime createdAt;
    
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;



    // Constructors
    public Promotion() {
        this.createdAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
    }
    
    public Promotion(String title, LocalDate startTime, LocalDate endTime, Integer discountAmount, String details) {
        this();
        this.title = title;
        this.startTime = startTime;
        this.endTime = endTime;
        this.discountAmount = discountAmount;
        this.details = details;
    }
    
    // Getters and Setters
    public Long getId() {
        return id;
    }
    
    public void setId(Long id) {
        this.id = id;
    }
    
    public String getTitle() {
        return title;
    }
    
    public void setTitle(String title) {
        this.title = title;
    }
    
    public LocalDate getStartTime() {
        return startTime;
    }
    
    public void setStartTime(LocalDate startTime) {
        this.startTime = startTime;
    }
    
    public LocalDate getEndTime() {
        return endTime;
    }
    
    public void setEndTime(LocalDate endTime) {
        this.endTime = endTime;
    }
    
    public Integer getDiscountAmount() {
        return discountAmount;
    }
    
    public void setDiscountAmount(Integer discountAmount) {
        this.discountAmount = discountAmount;
    }
    
    public String getDetails() {
        return details;
    }
    
    public void setDetails(String details) {
        this.details = details;
    }
    
    public LocalDateTime getCreatedAt() {
        return createdAt;
    }
    
    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }
    
    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }
    
    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }
    
    // Business logic methods
    public boolean isValidDateRange() {
        return startTime != null && endTime != null && endTime.isAfter(startTime.plusDays(0));
    }
    
    @PreUpdate
    public void preUpdate() {
        this.updatedAt = LocalDateTime.now();
    }
}
