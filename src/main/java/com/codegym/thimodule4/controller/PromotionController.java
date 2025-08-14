package com.codegym.thimodule4.controller;

import com.codegym.thimodule4.dto.SearchForm;
import com.codegym.thimodule4.model.Promotion;
import com.codegym.thimodule4.service.PromotionService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

/**
 * Controller xử lý các request liên quan đến khuyến mãi
 */
@Controller
@RequestMapping("/promotions")
public class PromotionController {
    
    @Autowired
    private PromotionService promotionService;
    
    /**
     * Hiển thị danh sách khuyến mãi
     * GET /promotions
     */
    @GetMapping("")
    public String listPromotions(@ModelAttribute("searchForm") SearchForm searchForm, 
                                Model model) {
        // @ModelAttribute tự động bind request parameters vào searchForm
        // Nếu không có parameters thì tạo object mới
        
        List<Promotion> promotions;
        
        if (searchForm.hasSearchCriteria()) {
            // Nếu có điều kiện search thì tìm kiếm
            promotions = promotionService.searchByMultipleConditions(
                searchForm.getDiscountAmount() != null ? searchForm.getDiscountAmount().intValue() : null,
                searchForm.getStartTime(),
                searchForm.getEndTime()
            );
        } else {
            // Nếu không có điều kiện search thì lấy tất cả
            promotions = promotionService.getAllPromotions();
        }
        
        model.addAttribute("promotions", promotions);
        // searchForm đã được bind tự động, không cần add lại
        
        return "promotion/list";
    }
    
    /**
     * Hiển thị form thêm mới khuyến mãi
     * GET /promotions/add
     */
    @GetMapping("/add")
    public String showAddForm(Model model) {
        Promotion promotion = new Promotion();
        model.addAttribute("promotion", promotion);
        
        return "promotion/add";
    }
    
    /**
     * Xử lý form thêm mới khuyến mãi
     * POST /promotions/add
     */
    @PostMapping("/add")
    public String addPromotion(@Valid @ModelAttribute("promotion") Promotion promotion,
                              BindingResult result,
                              Model model,
                              RedirectAttributes redirectAttributes) {
        
        if (result.hasErrors()) {
            return "promotion/add";
        }
        
        try {
            // Validate ngày kết thúc phải lớn hơn ngày bắt đầu ít nhất 1 ngày
            if (!promotionService.isValidDateRange(promotion.getStartTime(), promotion.getEndTime())) {
                result.rejectValue("endTime", "error.endTime", "Ngày kết thúc phải lớn hơn ngày bắt đầu ít nhất 1 ngày");
                return "promotion/add";
            }
            
            promotionService.savePromotion(promotion);
            redirectAttributes.addFlashAttribute("successMessage", "Thêm khuyến mãi thành công!");
            
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorMessage", "Lỗi: " + e.getMessage());
        }
        
        return "redirect:/promotions";
    }
    
    /**
     * Hiển thị form chỉnh sửa khuyến mãi
     * GET /promotions/edit/{id}
     */
    @GetMapping("/edit/{id}")
    public String showEditForm(@PathVariable Long id, Model model) {
        
        Promotion promotion = promotionService.getPromotionById(id);
        
        if (promotion != null) {
            model.addAttribute("promotion", promotion);
            return "promotion/edit";
        } else {
            return "redirect:/promotions";
        }
    }
    
    /**
     * Xử lý form chỉnh sửa khuyến mãi
     * POST /promotions/edit/{id}
     */
    @PostMapping("/edit/{id}")
    public String editPromotion(@PathVariable Long id,
                               @Valid @ModelAttribute("promotion") Promotion promotion,
                               BindingResult result,
                               RedirectAttributes redirectAttributes) {
        
        if (result.hasErrors()) {
            return "promotion/edit";
        }
        
        try {
            // Validate ngày kết thúc phải lớn hơn ngày bắt đầu ít nhất 1 ngày
            if (!promotionService.isValidDateRange(promotion.getStartTime(), promotion.getEndTime())) {
                result.rejectValue("endTime", "error.endTime", "Ngày kết thúc phải lớn hơn ngày bắt đầu ít nhất 1 ngày");
                return "promotion/edit";
            }
            
            promotionService.updatePromotion(id, promotion);
            redirectAttributes.addFlashAttribute("successMessage", "Cập nhật khuyến mãi thành công!");
            
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorMessage", "Lỗi: " + e.getMessage());
        }
        
        return "redirect:/promotions";
    }
    
    /**
     * Xóa khuyến mãi
     * POST /promotions/delete/{id}
     */
    @PostMapping("/delete/{id}")
    public String deletePromotion(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        
        try {
            promotionService.deletePromotion(id);
            redirectAttributes.addFlashAttribute("successMessage", "Xóa khuyến mãi thành công!");
            
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorMessage", "Lỗi: " + e.getMessage());
        }
        
        return "redirect:/promotions";
    }
    
    /**
     * Tìm kiếm khuyến mãi
     * POST /promotions/search
     */
    @PostMapping("/search")
    public String searchPromotions(@ModelAttribute("searchForm") SearchForm searchForm,
                                  RedirectAttributes redirectAttributes) {
        
        // Redirect với search parameters để giữ được form state
        return "redirect:/promotions?" + buildSearchQueryString(searchForm);
    }
    
    /**
     * Xóa điều kiện tìm kiếm
     * GET /promotions/clear-search
     */
    @GetMapping("/clear-search")
    public String clearSearch(RedirectAttributes redirectAttributes) {
        redirectAttributes.addFlashAttribute("successMessage", "Đã xóa điều kiện tìm kiếm");
        return "redirect:/promotions";
    }
    
    /**
     * Helper method để build query string cho search
     */
    private String buildSearchQueryString(SearchForm searchForm) {
        StringBuilder query = new StringBuilder();
        
        if (searchForm.getDiscountAmount() != null) {
            query.append("discountAmount=").append(searchForm.getDiscountAmount());
        }
        
        if (searchForm.getStartTime() != null) {
            if (query.length() > 0) query.append("&");
            query.append("startTime=").append(searchForm.getStartTime());
        }
        
        if (searchForm.getEndTime() != null) {
            if (query.length() > 0) query.append("&");
            query.append("endTime=").append(searchForm.getEndTime());
        }
        
        return query.toString();
    }
}
