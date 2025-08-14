package com.codegym.thimodule4.config;

import com.codegym.thimodule4.model.Promotion;
import com.codegym.thimodule4.repository.PromotionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Component
public class DataInitializer implements CommandLineRunner {

    @Autowired
    private PromotionRepository promotionRepository;

    @Override
    public void run(String... args) throws Exception {
        if (promotionRepository.count() == 0) {
            initializeSampleData();
        }
    }

    private void initializeSampleData() {
        Promotion promotion1 = new Promotion();
        promotion1.setTitle("Khuyến Mãi Năm Mới");
        promotion1.setStartTime(LocalDate.of(2025, 3, 4));
        promotion1.setEndTime(LocalDate.of(2025, 3, 10));
        promotion1.setDiscountAmount(50000);
        promotion1.setDetails("Giảm giá đặc biệt cho dịp Tết Nguyên Đán.");
        promotion1.setCreatedAt(LocalDateTime.now());
        promotion1.setUpdatedAt(LocalDateTime.now());
        promotionRepository.save(promotion1);

        Promotion promotion2 = new Promotion();
        promotion2.setTitle("Khuyến Mãi Valentine");
        promotion2.setStartTime(LocalDate.of(2025, 4, 4));
        promotion2.setEndTime(LocalDate.of(2025, 4, 10));
        promotion2.setDiscountAmount(75000);
        promotion2.setDetails("Ưu đãi lãng mạn cho các cặp đôi.");
        promotion2.setCreatedAt(LocalDateTime.now());
        promotion2.setUpdatedAt(LocalDateTime.now());
        promotionRepository.save(promotion2);

        Promotion promotion3 = new Promotion();
        promotion3.setTitle("Khuyến Mãi Sinh Nhật");
        promotion3.setStartTime(LocalDate.of(2025, 4, 16));
        promotion3.setEndTime(LocalDate.of(2025, 4, 23));
        promotion3.setDiscountAmount(100000);
        promotion3.setDetails("Mừng sinh nhật rạp, giảm giá cực sốc!");
        promotion3.setCreatedAt(LocalDateTime.now());
        promotion3.setUpdatedAt(LocalDateTime.now());
        promotionRepository.save(promotion3);
    }
}
