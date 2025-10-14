package com.est.b3.controller.api;

import com.est.b3.domain.Restaurant;
import com.est.b3.dto.RestaurantResponseDto;
import com.est.b3.service.RestaurantService;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.sql.ResultSet;

// RestController는 RestAPI의 역할을 함!
// 그래서 페이지로 안보냄


@RestController
@RequestMapping("/api/restaurants")
@RequiredArgsConstructor
public class RestaurantController {

  private final RestaurantService restaurantService;

  @GetMapping("/{bossId}") // 엔드포인트 -/api/restaurants/1?page=0
  public Page<RestaurantResponseDto> getRestaurantsByBoss(
      @PathVariable Long bossId,
      @PageableDefault(size = 16) Pageable pageable
  ) {
    return restaurantService.getRestaurantsByBossAddress(bossId, pageable);
  }

  @GetMapping("/{bossId}/search") // 엔드포인트 -/api/restaurants/1/search?menu=메뉴&page=0
  public Page<RestaurantResponseDto> searchRestaurantsByBoss(
      @PathVariable Long bossId,
      @RequestParam String menu,
      @PageableDefault(size = 16) Pageable pageable
  ) {
    return restaurantService.searchRestaurants(bossId, menu, pageable); // 리턴값이 "a" 라고 치면, 얘도 a.html로 가야되는 거아님??
  }

//  @GetMapping("/{id}")
//    public RestaurantResponse getRestaurantDetail(@PathVariable Long restaurantId) {
//      return restaurantService.getRestaurants(restaurantId);
//  }
}