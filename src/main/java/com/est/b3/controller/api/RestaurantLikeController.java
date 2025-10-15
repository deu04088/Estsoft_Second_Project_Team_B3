package com.est.b3.controller.api;

import com.est.b3.dto.SessionUserDTO;
import com.est.b3.service.LikeService;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/restaurants")
public class RestaurantLikeController {
    private final LikeService likeService;

    @PostMapping("/{id}/like")
    public Map<String, Object> toggleLike(@PathVariable("id") Long restaurantId, HttpSession session) {
        Long bossId = ((SessionUserDTO) session.getAttribute("loginBoss")).getId();
        int likeCount = likeService.toggleLike(restaurantId, bossId);

        Map<String, Object> response = new HashMap<>();
        response.put("likeCount", likeCount);
        return response;
    }
}
