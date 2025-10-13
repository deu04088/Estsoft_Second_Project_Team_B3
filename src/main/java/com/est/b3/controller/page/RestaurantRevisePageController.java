package com.est.b3.controller.page;

import com.est.b3.dto.RestaurantResponseDto;
import com.est.b3.service.RestaurantReviseService;
import com.est.b3.service.RestaurantService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@RequiredArgsConstructor
@Controller
public class RestaurantRevisePageController {
    private final RestaurantReviseService restaurantReviseService;

    @GetMapping("/update-form/{id}")
    public String updateForm(@PathVariable Long id, Model model) {
        RestaurantResponseDto restaurant = restaurantReviseService.getRestaurantById(id);
        model.addAttribute("restaurant", restaurant);

        return "update-form";
    }
}
