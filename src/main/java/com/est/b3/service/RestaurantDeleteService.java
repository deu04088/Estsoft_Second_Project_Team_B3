package com.est.b3.service;

import com.est.b3.domain.Restaurant;
import com.est.b3.repository.RestaurantRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RestaurantDeleteService {

    private final RestaurantRepository restaurantRepository;

    @Transactional
    public void deleteRestaurant(Long restaurantId, Long bossId) {
        Restaurant restaurant = restaurantRepository.findById(restaurantId)
                .orElseThrow(() -> new RuntimeException("삭제할 식당이 없습니다."));

        // 본인 소유인지 확인
        if (!restaurant.getBoss().getId().equals(bossId)) {
            throw new RuntimeException("권한이 없습니다.");
        }

        restaurantRepository.delete(restaurant);
    }
}