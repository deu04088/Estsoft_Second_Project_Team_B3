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
    public String saveRestaurantInfo(@RequestParam(value="photo", required = false) MultipartFile photo,
                                     @RequestParam(value="name") String name,
                                     @RequestParam(value="menuName") String menuName,
                                     @RequestParam(value="price") Integer price,
                                     @RequestParam(value="description") String description,
                                     @RequestParam(value="address") String address,
                                     HttpSession session) throws IOException {

        Photo photoEntity = null;

        if (photo != null && !photo.isEmpty()) {
            System.out.println("업로드된 파일명: " + photo.getOriginalFilename());
            System.out.println("================ activeProfile = " + activeProfile);

            if ("local".equals(activeProfile)) {
                photoEntity = restaurantFileService.savePhotoLocal(photo);

                System.out.println("[Local Mode] 로컬 uploads 폴더에 저장됨: " + photoEntity.getS3Url());
            } else {
                photoEntity = restaurantFileService.savePhotoS3(photo);

                System.out.println("[Production Mode] S3 버킷에 저장됨: " + photoEntity.getS3Url());
            }

        } else {

            System.out.println("사진이 업로드되지 않았습니다.");

            photoEntity = Photo.builder()
                    .s3Key("default-restaurant.png")
                    .s3Url("/images/default-restaurant.png")  // static 폴더 경로
                    .originalFilename("기본 이미지")
                    .uploadDate(java.time.LocalDateTime.now())
                    .build();
        }

        SessionUserDTO loginBoss = (SessionUserDTO) session.getAttribute("loginBoss");

        if (loginBoss == null) {
            System.out.println("세션이 없습니다. loginBoss == null");

            throw new IllegalStateException("로그인 세션이 만료되었습니다.");
        }

        Long bossId = loginBoss.getId();
        restaurantEnrollService.saveRestaurant(name, menuName, price, description, address, photoEntity, bossId);

        return "redirect:/restaurants";
    }

}
