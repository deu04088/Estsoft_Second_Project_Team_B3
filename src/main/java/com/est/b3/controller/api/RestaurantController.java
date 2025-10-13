package com.est.b3.controller.api;

import com.est.b3.dto.RestaurantResponseDto;
import com.est.b3.service.RestaurantService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

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
    return restaurantService.searchRestaurants(bossId, menu, pageable);
  }
}