package com.est.b3.controller.api;

import com.est.b3.domain.Photo;
import com.est.b3.service.RestaurantEnrollService;
import com.est.b3.service.RestaurantFileService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RequiredArgsConstructor
@Controller
public class RestaurantEnrollController {
    private final RestaurantEnrollService restaurantEnrollService;
    private final RestaurantFileService restaurantFileService;

    @PostMapping("/api/restaurant-form")
    public String saveRestaurantInfo(@RequestParam(value="photo") MultipartFile photo,
                                     @RequestParam(value="name") String name,
                                     @RequestParam(value="menuName") String menuName,
                                     @RequestParam(value="price") Integer price,
                                     @RequestParam(value="description") String description,
                                     @RequestParam(value="address") String address) throws IOException {
//
        // 사진 파일 저장 후 URL 생성
        //String photoUrl = restaurantEnrollService.savePhoto(photo);

        Photo photoEntity = null;

        if (photo != null && !photo.isEmpty()) {
            photoEntity = restaurantFileService.savePhotoLocal(photo);
        }

//        Boss bossId = restaurantEnrollService.saveBossInfo();



        restaurantEnrollService.saveRestaurant(name, menuName, price, description, address, photoEntity); // bossId);

        return "redirect:/restaurants";
    }
}
