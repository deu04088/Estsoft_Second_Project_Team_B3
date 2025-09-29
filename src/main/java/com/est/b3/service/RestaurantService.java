package com.est.b3.service;

import com.est.b3.domain.Boss;
import com.est.b3.dto.RestaurantResponseDto;

import com.est.b3.repository.RestaurantRepository;
import com.est.b3.repository.BossRepository;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RestaurantService {

  private final BossRepository bossRepository;
  private final RestaurantRepository restaurantRepository;

  // 동네 식당 목록조회
  public Page<RestaurantResponseDto> getRestaurantsByBossAddress(Long bossId, Pageable pageable) {
    Boss boss = bossRepository.findById(bossId)
        .orElseThrow(() -> new IllegalArgumentException("not found boss"));


    String address = boss.getAddress();

    return restaurantRepository.findByAddressSortedByLikes(address, pageable);
  }

  // 메뉴로 식당 검색
  public Page<RestaurantResponseDto> searchRestaurants(Long bossId, String menu, Pageable pageable) {
    Boss boss = bossRepository.findById(bossId)
        .orElseThrow(() -> new IllegalArgumentException("not found boss"));


    String address = boss.getAddress();

    return restaurantRepository.searchRestaurantsByMenu(address, menu, pageable);
  }
}