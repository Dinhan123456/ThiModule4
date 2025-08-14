package com.codegym.thimodule4.repository;

import com.codegym.thimodule4.model.Promotion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface PromotionRepository extends JpaRepository<Promotion, Long> {
    
    // Tìm kiếm theo mức giảm giá
    List<Promotion> findByDiscountAmountGreaterThanEqual(Integer discountAmount);
    
    // Tìm kiếm theo thời gian bắt đầu
    List<Promotion> findByStartTimeGreaterThanEqual(LocalDate startTime);
    
    // Tìm kiếm theo thời gian kết thúc
    List<Promotion> findByEndTimeLessThanEqual(LocalDate endTime);
    
    // Tìm kiếm dựa trên cả 3 điều kiện
    @Query("SELECT p FROM Promotion p WHERE " +
           "(:discountAmount IS NULL OR p.discountAmount >= :discountAmount) AND " +
           "(:startTime IS NULL OR p.startTime >= :startTime) AND " +
           "(:endTime IS NULL OR p.endTime <= :endTime)")
    List<Promotion> findByMultipleConditions(
            @Param("discountAmount") Integer discountAmount,
            @Param("startTime") LocalDate startTime,
            @Param("endTime") LocalDate endTime
    );
    
    // Tìm kiếm theo tiêu đề (tìm kiếm mờ)
    List<Promotion> findByTitleContainingIgnoreCase(String title);
}
