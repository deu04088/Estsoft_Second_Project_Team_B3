package com.est.b3.controller.page;

import com.est.b3.domain.Boss;
import com.est.b3.dto.BossDto;
import com.est.b3.dto.RestaurantInfoDto;
import com.est.b3.dto.RestaurantResponseDto;
import com.est.b3.dto.SessionUserDTO;
import com.est.b3.repository.RestaurantRepository;
import com.est.b3.service.RestaurantEnrollService;
import com.est.b3.service.RestaurantReviseService;
import com.est.b3.service.RestaurantService;
import com.est.b3.service.RestaurantViewcountService;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@Controller
@RequiredArgsConstructor
public class RestaurantPageController {

    private final RestaurantService restaurantService;
    private final RestaurantEnrollService restaurantEnrollService;
    private final RestaurantReviseService restaurantReviseService;
    private final RestaurantViewcountService restaurantViewcountService;

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
    public String getRestaurantsPage(
        HttpSession session,
        @PageableDefault(size = 16) Pageable pageable,
        Model model
    ) {
        SessionUserDTO sessionUser = (SessionUserDTO) session.getAttribute("loginBoss"); // 세션에 로그인된 Boss 꺼내기
        if (sessionUser == null) {
            return "redirect:/login"; // 로그인 안 되어 있으면 index.html로 이동
        }

        Long bossId = sessionUser.getId();
        Page<RestaurantResponseDto> page = restaurantService.getRestaurantsByBossAddress(bossId, pageable);

        model.addAttribute("restaurants", page.getContent());
        model.addAttribute("page", page);
        model.addAttribute("url", "/restaurants");
        return "restaurants";
    }



    // 메뉴 검색 페이지
    @GetMapping("/restaurants/search")
    public String searchRestaurantsPage(
        HttpSession session,
        @RequestParam String menu,
        @PageableDefault(size = 16) Pageable pageable,
        Model model
    ) {
        SessionUserDTO sessionUser = (SessionUserDTO) session.getAttribute("loginBoss");
        if (sessionUser == null) {
            return "redirect:/index";
        }

        Long bossId = sessionUser.getId();
        Page<RestaurantResponseDto> page = restaurantService.searchRestaurants(bossId, menu, pageable);

        model.addAttribute("restaurants", page.getContent());
        model.addAttribute("page", page);
        model.addAttribute("menu", menu);
        model.addAttribute("url", "/restaurants/search?menu=" + menu);
        return "restaurants-search"; // 검색 결과 템플릿
    }

    // 식당 등록 페이지
    @GetMapping("/restaurant-form")
    public String form(Model model, HttpSession session) {

        // 임시 유저 데이터 (추후 로그인 사용자 세션 연동)
        Map<String, Object> user = new HashMap<>();
        user.put("id", 1L);
        user.put("username", "tester");
        user.put("address", "서울 강서구 화곡동");

        model.addAttribute("user", user);
        SessionUserDTO loginBoss = (SessionUserDTO) session.getAttribute("loginBoss");
//        Long bossId = loginBoss.getId();
        BossDto boss = restaurantEnrollService.getBossById(loginBoss.getId());
        model.addAttribute("boss", boss);

        return "restaurant-form"; // templates/restaurant-form.html
    }

    // 식당 상세 페이지
    @GetMapping("/restaurant-detail/{id}")
    public String detail(@PathVariable Long id, Model model) {

        // 임시 데이터 [연결 확인용] : 추후 개발시 지우세요
        Map<String, Object> testRestaurant = createRestaurant(
                id, "OK Burger", "더블치즈 버거", 11000, 15,
                "/images/sample-burger.png", 12, 1L
        );

        testRestaurant.put("description", "스테이크 200g과 양상추, 토마토로 당일 생산합니다.");
        testRestaurant.put("address", "강서구 화곡동");
        model.addAttribute("testRestaurant", testRestaurant);

        RestaurantInfoDto restaurant = restaurantReviseService.getRestaurantById(id);
        restaurantViewcountService.getRestaurantViewcount(restaurant.getId());
        model.addAttribute("restaurant", restaurant);

        // 임시 로그인 유저 (세션 대신)
        Map<String, Object> sessionUser = new HashMap<>();
        sessionUser.put("id", 1L);   // 현재 로그인했다고 가정
        sessionUser.put("username", "tester");

        model.addAttribute("sessionUser", sessionUser);

        return "restaurant-detail"; // templates/restaurant-detail.html
    }


}
