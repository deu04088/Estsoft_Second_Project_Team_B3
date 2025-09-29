package com.est.b3.service;

import com.est.b3.domain.Boss;
import com.est.b3.dto.RestaurantResponseDto;
import com.est.b3.repository.BossRepository;
import com.est.b3.repository.LikeRepository;
import com.est.b3.repository.RestaurantRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RestaurantService {

  private final BossRepository bossRepository;
  private final RestaurantRepository restaurantRepository;
  private final LikeRepository likeRepository;

  // 동네 식당 목록조회
  public Page<RestaurantResponseDto> getRestaurantsByUserAddress(Long userId, Pageable pageable) {
    Boss user = bossRepository.findById(userId)
        .orElseThrow(() -> new IllegalArgumentException("not found user"));

    String address = user.getAddress();

    return restaurantRepository.findByAddressSortedByLikes(address, pageable);
  }

  // 메뉴로 식당 검색
  public Page<RestaurantResponseDto> searchRestaurants(Long userId, String menu, Pageable pageable) {
    Boss user = bossRepository.findById(userId)
        .orElseThrow(() -> new IllegalArgumentException("not found user"));

    String address = user.getAddress();

    return restaurantRepository.searchRestaurantsByMenu(address, menu, pageable);
  }
}