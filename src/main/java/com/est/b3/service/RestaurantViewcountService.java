package com.est.b3.service;

import com.est.b3.domain.Restaurant;
import com.est.b3.repository.RestaurantRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RestaurantViewcountService {
    private final RestaurantRepository restaurantRepository;

    @Transactional
    public Restaurant getRestaurantViewcount(Long id) {
        Restaurant restaurant = restaurantRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("해당 식당이 없습니다."));

        restaurant.incrementView();

        return restaurant;
    }
}
