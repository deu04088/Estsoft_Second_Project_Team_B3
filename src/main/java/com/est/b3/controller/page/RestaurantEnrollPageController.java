package com.est.b3.controller.page;

import com.est.b3.service.RestaurantEnrollService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RequiredArgsConstructor
@Controller
public class RestaurantEnrollPageController {
    private final RestaurantEnrollService restaurantEnrollService;

    @PostMapping("/api/restaurant-form")
    public String saveRestaurantInfo(@RequestParam(value="photo") MultipartFile photo,
                                     @RequestParam(value="name") String name,
                                     @RequestParam(value="menuName") String menuName,
                                     @RequestParam(value="price") Integer price,
                                     @RequestParam(value="description") String description,
                                     @RequestParam(value="address") String address) throws IOException {
        // 사진 파일 저장 후 URL 생성
        String photoUrl = restaurantEnrollService.savePhoto(photo);

        this.restaurantEnrollService.saveRestaurant(name, menuName, price, description, address, photoUrl);
        return "redirect:/restaurants";
    }
}
