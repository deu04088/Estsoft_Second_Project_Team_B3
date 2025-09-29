package com.est.b3.controller.page;

import com.est.b3.dto.RestaurantResponseDto;
import com.est.b3.service.RestaurantService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequiredArgsConstructor
public class RestaurantPageController {

    private final RestaurantService restaurantService;

    // 공통으로 더미 식당 생성하는 헬퍼 메서드 [추후 삭제]
    private Map<String, Object> createRestaurant(Long id, String name, String menuName,
                                                 int price, int viewCount, String photoUrl,
                                                 int likeCount, Long userId) {
        Map<String, Object> restaurant = new HashMap<>();
        restaurant.put("id", id);
        restaurant.put("name", name);
        restaurant.put("menuName", menuName);
        restaurant.put("price", price);
        restaurant.put("viewCount", viewCount);
        restaurant.put("photoUrl", photoUrl);
        restaurant.put("likeCount", likeCount);
        restaurant.put("userId", userId); // 추가
        return restaurant;
    }

    // 식당 리스트 페이지 (우리 동네 식당 소개)
    @GetMapping("/restaurants/{bossId}") //로그인 세션 완성 후 /restaurants 로 변경 가능
    public String getRestaurantsPage(
        @PathVariable Long bossId,
        @PageableDefault(size = 16) Pageable pageable,
        Model model
    ) {
        Page<RestaurantResponseDto> page = restaurantService.getRestaurantsByBossAddress(bossId, pageable);
        model.addAttribute("restaurants", page.getContent());
        model.addAttribute("page", page);
        model.addAttribute("url", "/restaurants/" + bossId);
        return "restaurants"; // Thymeleaf 템플릿 이름
    }

    // 메뉴 검색 페이지
    @GetMapping("/restaurants/{bossId}/search")
    public String searchRestaurantsPage(
        @PathVariable Long bossId,
        @RequestParam String menu,
        @PageableDefault(size = 16) Pageable pageable,
        Model model
    ) {
        Page<RestaurantResponseDto> page = restaurantService.searchRestaurants(bossId, menu, pageable);
        model.addAttribute("restaurants", page.getContent());
        model.addAttribute("page", page);
        model.addAttribute("menu", menu);
        model.addAttribute("url", "/restaurants/" + bossId + "/search?menu=" + menu);
        return "restaurant-search"; // 검색 결과 템플릿
    }

    // 식당 등록 페이지
    @GetMapping("/restaurant-form")
    public String form(Model model) {

        // 임시 유저 데이터 (추후 로그인 사용자 세션 연동)
        Map<String, Object> user = new HashMap<>();
        user.put("id", 1L);
        user.put("username", "tester");
        user.put("address", "서울 강서구 화곡동");

        model.addAttribute("user", user);

        return "restaurant-form"; // templates/restaurant-form.html
    }

    // 식당 상세 페이지
    @GetMapping("/restaurant-detail/{id}")
    public String detail(@PathVariable Long id, Model model) {

        // 임시 데이터 [연결 확인용] : 추후 개발시 지우세요
        Map<String, Object> restaurant = createRestaurant(
                id, "OK Burger", "더블치즈 버거", 11000, 15,
                "/images/sample-burger.png", 12, 1L
        );

        restaurant.put("description", "스테이크 200g과 양상추, 토마토로 당일 생산합니다.");
        restaurant.put("address", "강서구 화곡동");

        model.addAttribute("restaurant", restaurant);

        // 임시 로그인 유저 (세션 대신)
        Map<String, Object> sessionUser = new HashMap<>();
        sessionUser.put("id", 1L);   // 현재 로그인했다고 가정
        sessionUser.put("username", "tester");

        model.addAttribute("sessionUser", sessionUser);

        return "restaurant-detail"; // templates/restaurant-detail.html
    }


}
