package com.est.b3.service;

import com.est.b3.domain.Photo;
import com.est.b3.domain.Restaurant;
import com.est.b3.dto.RestaurantResponseDto;
import com.est.b3.repository.RestaurantRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.NoSuchElementException;

@Service
@RequiredArgsConstructor
public class RestaurantReviseService {
    private final RestaurantRepository restaurantRepository;

    // restaurantId로 가게 정보를 가져오는 메서드
    public RestaurantResponseDto getRestaurantById(Long restaurantId) {
        Restaurant restaurant = restaurantRepository.findById(restaurantId)
                .orElseThrow(() -> new NoSuchElementException("해당 식당이 없습니다. id=" + restaurantId));

        // 엔티티 → DTO 변환
        return new RestaurantResponseDto(
                restaurant.getId(),
                restaurant.getName(),
                restaurant.getMenuName(),
                restaurant.getPrice(),
                restaurant.getAddress(),
                restaurant.getPhoto().getS3Url(),
                restaurant.getViewCount()
        );
    }

    @Transactional
    public void updateRestaurant(Long id,
                                 String name,
                                 String menuName,
                                 Integer price,
                                 String description,
                                 String address,
                                 Photo photo) {

        Restaurant restaurant = restaurantRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("해당 주소의 식당이 없습니다."));
        restaurant.update(name, menuName, price, description, address, photo);
    }
}
