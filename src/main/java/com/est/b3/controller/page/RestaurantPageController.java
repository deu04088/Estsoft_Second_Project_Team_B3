package com.est.b3.controller.page;

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
public class RestaurantPageController {

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
    @GetMapping("/restaurants")
    public String list(Model model) {

        // 임시 데이터 [연결 확인용] : 추후 개발시 지우세요
        List<Map<String, Object>> dummyRestaurants = new ArrayList<>();
        dummyRestaurants.add(
                createRestaurant(1L, "OK Burger", "더블치즈 버거", 11000, 15,
                        "/images/sample-burger.png", 1, 1L)
        );
        dummyRestaurants.add(
                createRestaurant(2L, "김밥천국", "참치김밥", 3500, 42,
                        "/images/sample-kimbap.png", 2, 2L)
        );

        model.addAttribute("restaurants", dummyRestaurants);
        return "restaurants";
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

    @GetMapping("/search")
    public String search(
            @RequestParam String menu,
            @RequestParam(defaultValue = "0") int page,
            Model model) {

        // 임시 데이터 (추후 DB에서 조회)
        // http://localhost:8080/search?menu=치킨&page=2
        // 카드리스트 안뜨는게 정상
        List<Map<String, Object>> searchResults = new ArrayList<>();
        searchResults.add(createRestaurant(1L, "OK Burger", "더블치즈 버거", 11000, 15,
                "/images/sample-burger.png", 10, 1L));
        searchResults.add(createRestaurant(2L, "김밥천국", "참치김밥", 3500, 42,
                "/images/sample-kimbap.png", 5, 2L));

        model.addAttribute("menuKeyword", menu);
        model.addAttribute("results", searchResults);

        return "search"; // templates/search.html
    }
}
