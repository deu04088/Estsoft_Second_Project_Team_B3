package com.est.b3.controller.api;

import com.est.b3.dto.SessionUserDTO;
import com.est.b3.service.RestaurantDeleteService;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/restaurants")
public class RestaurantDeleteController {
    private final RestaurantDeleteService restaurantDeleteService;

    @DeleteMapping("/{id}/delete")
    public String deleteRestaurant(
            @PathVariable("id") Long restaurantId,
            HttpSession session
    ) {
        Long bossId = ((SessionUserDTO) session.getAttribute("loginBoss")).getId();
        restaurantDeleteService.deleteRestaurant(restaurantId, bossId);

        return "redirect:/restaurants";
//        return ResponseEntity.ok().body(Map.of("message", "삭제 완료"));
    }
}
