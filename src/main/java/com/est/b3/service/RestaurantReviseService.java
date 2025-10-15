package com.est.b3.service;

import com.est.b3.domain.Photo;
import com.est.b3.domain.Restaurant;
import com.est.b3.dto.RestaurantInfoDto;
import com.est.b3.dto.RestaurantResponseDto;
import com.est.b3.repository.RestaurantRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.NoSuchElementException;

@Service
@RequiredArgsConstructor
public class RestaurantReviseService {
    private final RestaurantRepository restaurantRepository;

    // restaurantId로 가게 정보를 가져오는 메서드
    public RestaurantInfoDto getRestaurantById(Long restaurantId) {
        Restaurant restaurant = restaurantRepository.findById(restaurantId)
                .orElseThrow(() -> new NoSuchElementException("해당 식당이 없습니다. id=" + restaurantId));

        // 엔티티 → DTO 변환
        return new RestaurantInfoDto(
                restaurant.getId(),
                restaurant.getName(),
                restaurant.getMenuName(),
                restaurant.getPrice(),
                restaurant.getAddress(),
                restaurant.getPhoto().getS3Url(),
                restaurant.getViewCount(),
                restaurant.getDescription(),
                restaurant.getSiDo(),
                restaurant.getGuGun(),
                restaurant.getDongEupMyeon(),
                restaurant.getLikes() != null ? restaurant.getLikes().size() : 0
        );
    }

    @Transactional
    public void  updateRestaurant(Long id,
                                 String name,
                                 String menuName,
                                 Integer price,
                                 String description,
                                 String address,
                                 Photo photo,
                                 String siDo,
                                 String guGun,
                                 String dongEupMyeon) {

        Restaurant restaurant = restaurantRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("해당 주소의 식당이 없습니다."));
        restaurant.update(name, menuName, price, description, address, photo, siDo, guGun, dongEupMyeon);
    }

    public Map<String, String> separateAddress(String address) {
        Map<String, String> separateAdd = new HashMap<>();
        String[] parts = address.split(" ");
        String siDo = parts.length > 0 ? parts[0] : "";
        String guGun = parts.length > 2 ? parts[1] : "";
        String dongEupMyeon = parts.length >= 3 ? parts[2] : "";

        // 동,읍,면 뒤 주소
        if (parts.length > 3) {
            dongEupMyeon = parts[2] + " " + String.join(" ", Arrays.copyOfRange(parts, 3, parts.length));
        }
        separateAdd.put("siDo", siDo);
        separateAdd.put("guGun", guGun);
        separateAdd.put("dongEupMyeon", dongEupMyeon);
        return separateAdd;
    }
}
