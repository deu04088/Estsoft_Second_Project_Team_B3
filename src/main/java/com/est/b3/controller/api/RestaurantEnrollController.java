package com.est.b3.controller.api;

import com.est.b3.domain.Photo;
import com.est.b3.dto.SessionUserDTO;
import com.est.b3.service.RestaurantEnrollService;
import com.est.b3.service.RestaurantFileService;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
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

    @Value("${spring.profiles.active:local}")
    private String activeProfile;

    @PostMapping("/api/restaurant-form")
    public String saveRestaurantInfo(@RequestParam(value="photo") MultipartFile photo,
                                     @RequestParam(value="name") String name,
                                     @RequestParam(value="menuName") String menuName,
                                     @RequestParam(value="price") Integer price,
                                     @RequestParam(value="description") String description,
                                     @RequestParam(value="address") String address,
                                     HttpSession session) throws IOException {
//
        // 사진 파일 저장 후 URL 생성
        //String photoUrl = restaurantEnrollService.savePhoto(photo);


        Photo photoEntity = null;

        if (photo != null && !photo.isEmpty()) {
            System.out.println("================ activeProfile = " + activeProfile);

            if ("local".equals(activeProfile)) {
                photoEntity = restaurantFileService.savePhotoLocal(photo);

                System.out.println("[Local Mode] 로컬 uploads 폴더에 저장됨: " + photoEntity.getS3Url());
            } else {
                photoEntity = restaurantFileService.savePhotoS3(photo);

                System.out.println("[Production Mode] S3 버킷에 저장됨: " + photoEntity.getS3Url());
            }
        }


        SessionUserDTO loginBoss = (SessionUserDTO) session.getAttribute("loginBoss");
        Long bossId = loginBoss.getId();

//        System.out.println(loginBoss.getId());
        restaurantEnrollService.saveRestaurant(name, menuName, price, description, address, photoEntity, bossId);

        return "redirect:/restaurants";
    }
}
