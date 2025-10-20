package com.est.b3.controller.api;

import com.est.b3.domain.Photo;
import com.est.b3.domain.Restaurant;
import com.est.b3.repository.PhotoRepository;
import com.est.b3.repository.RestaurantRepository;
import com.est.b3.service.RestaurantFileService;
import com.est.b3.service.RestaurantReviseService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Map;

@RequiredArgsConstructor
@Controller
public class RestaurantReviseController {
    private final RestaurantReviseService restaurantReviseService;
    private final RestaurantFileService restaurantFileService;
    private final RestaurantRepository restaurantRepository;
    private final PhotoRepository photoRepository;

    @PostMapping("/api/update-form")
    public String updateRestaurantInfo(@RequestParam("id") Long id,
        @RequestParam(value = "photo", required = false) MultipartFile photo,
        @RequestParam(value = "existingPhotoId", required = false) Long existingPhotoId,
        @RequestParam("name") String name,
        @RequestParam("menuName") String menuName,
        @RequestParam("price") Integer price,
        @RequestParam("description") String description,
        @RequestParam("address") String address) throws IOException {

        Photo photoEntity;

        if (photo != null && !photo.isEmpty()) {
            photoEntity = restaurantFileService.savePhotoLocal(photo);
        } else {
            photoEntity = photoRepository.findById(existingPhotoId)
                .orElseThrow(() -> new IllegalArgumentException("기존 사진을 찾을 수 없습니다."));
        }

        Map<String, String> add = restaurantReviseService.separateAddress(address);
        String siDo = add.get("siDo");
        String guGun = add.get("guGun");
        String dongEupMyeon = add.get("dongeupMyeon");

        restaurantReviseService.updateRestaurant(id, name, menuName, price, description, address, photoEntity, siDo, guGun, dongEupMyeon);

        return "redirect:/restaurant-detail/" + id;
    }
}
