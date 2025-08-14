package com.codegym.thimodule4.service.impl;

import com.codegym.thimodule4.model.Promotion;
import com.codegym.thimodule4.repository.PromotionRepository;
import com.codegym.thimodule4.service.PromotionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class PromotionServiceImpl implements PromotionService {
    
    @Autowired
    private PromotionRepository promotionRepository;
    
    @Override
    public List<Promotion> getAllPromotions() {
        return promotionRepository.findAll();
    }
    
    @Override
    public Promotion getPromotionById(Long id) {
        Optional<Promotion> promotion = promotionRepository.findById(id);
        return promotion.orElse(null);
    }
    
    @Override
    public Promotion savePromotion(Promotion promotion) {
        // Validate ngày kết thúc phải lớn hơn ngày bắt đầu ít nhất 1 ngày
        if (!isValidDateRange(promotion.getStartTime(), promotion.getEndTime())) {
            throw new IllegalArgumentException("Ngày kết thúc phải lớn hơn ngày bắt đầu ít nhất 1 ngày");
        }
        return promotionRepository.save(promotion);
    }
    
    @Override
    public Promotion updatePromotion(Long id, Promotion promotion) {
        Optional<Promotion> existingPromotion = promotionRepository.findById(id);
        if (existingPromotion.isPresent()) {
            Promotion updatedPromotion = existingPromotion.get();
            updatedPromotion.setTitle(promotion.getTitle());
            updatedPromotion.setStartTime(promotion.getStartTime());
            updatedPromotion.setEndTime(promotion.getEndTime());
            updatedPromotion.setDiscountAmount(promotion.getDiscountAmount());
            updatedPromotion.setDetails(promotion.getDetails());
            
            // Validate ngày kết thúc phải lớn hơn ngày bắt đầu ít nhất 1 ngày
            if (!isValidDateRange(updatedPromotion.getStartTime(), updatedPromotion.getEndTime())) {
                throw new IllegalArgumentException("Ngày kết thúc phải lớn hơn ngày bắt đầu ít nhất 1 ngày");
            }
            
            return promotionRepository.save(updatedPromotion);
        }
        return null;
    }
    
    @Override
    public void deletePromotion(Long id) {
        promotionRepository.deleteById(id);
    }
    
    @Override
    public List<Promotion> searchByDiscountAmount(Integer discountAmount) {
        if (discountAmount == null) {
            return getAllPromotions();
        }
        return promotionRepository.findByDiscountAmountGreaterThanEqual(discountAmount);
    }
    
    @Override
    public List<Promotion> searchByStartTime(LocalDate startTime) {
        if (startTime == null) {
            return getAllPromotions();
        }
        return promotionRepository.findByStartTimeGreaterThanEqual(startTime);
    }
    
    @Override
    public List<Promotion> searchByEndTime(LocalDate endTime) {
        if (endTime == null) {
            return getAllPromotions();
        }
        return promotionRepository.findByEndTimeLessThanEqual(endTime);
    }
    
    @Override
    public List<Promotion> searchByMultipleConditions(Integer discountAmount, LocalDate startTime, LocalDate endTime) {
        return promotionRepository.findByMultipleConditions(discountAmount, startTime, endTime);
    }
    
    @Override
    public List<Promotion> searchByTitle(String title) {
        if (title == null || title.trim().isEmpty()) {
            return getAllPromotions();
        }
        return promotionRepository.findByTitleContainingIgnoreCase(title.trim());
    }
    
    @Override
    public boolean isValidDateRange(LocalDate startTime, LocalDate endTime) {
        if (startTime == null || endTime == null) {
            return false;
        }
        // Ngày kết thúc phải lớn hơn ngày bắt đầu ít nhất 1 ngày
        return endTime.isAfter(startTime.plusDays(0));
    }
}
