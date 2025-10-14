package com.est.b3.controller.api;

import com.est.b3.domain.Restaurant;
import com.est.b3.dto.admin.AdminPostResponse;
import com.est.b3.dto.api.ApiResponse;
import com.est.b3.exception.CustomException;
import com.est.b3.exception.ErrorCode;
import com.est.b3.repository.RestaurantRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/admin/posts")
@RequiredArgsConstructor
public class AdminPostController {

    private final RestaurantRepository restaurantRepository;

    /**
     * 게시물 전체 조회
     */
    @GetMapping
    public ResponseEntity<ApiResponse<List<AdminPostResponse>>> getAllPosts() {
        List<Restaurant> restaurants = restaurantRepository.findAll();

        List<AdminPostResponse> responses = restaurants.stream()
                .map(AdminPostResponse::new)
                .toList();

        return ResponseEntity.ok(ApiResponse.success(200, "게시물 목록 조회 성공", responses));
    }

    /**
     * 게시물 공개/비공개 상태 토글
     */
    @PutMapping("/{id}/toggle")
    public ResponseEntity<ApiResponse<Void>> togglePostState(@PathVariable Long id) {
        Restaurant restaurant = restaurantRepository.findById(id)
                .orElseThrow(() -> new CustomException(ErrorCode.POST_NOT_FOUND));

        restaurant.toggleState();
        restaurantRepository.save(restaurant);

        String message = restaurant.getState() == 1
                ? "게시물이 공개 처리되었습니다."
                : "게시물이 비공개 처리되었습니다.";

        return ResponseEntity.ok(ApiResponse.success(200, message, null));
    }
}
