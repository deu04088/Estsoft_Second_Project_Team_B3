package com.est.b3.controller.api;

import com.est.b3.domain.Photo;
import com.est.b3.domain.Restaurant;
import com.est.b3.repository.RestaurantRepository;
import com.est.b3.service.RestaurantFileService;
import com.est.b3.service.RestaurantReviseService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RequiredArgsConstructor
@Controller
public class RestaurantReviseController {
    private final RestaurantReviseService restaurantReviseService;
    private final RestaurantFileService restaurantFileService;
    private final RestaurantRepository restaurantRepository;

    @PostMapping("/api/update-form")
    public String updateRestaurantInfo(@RequestParam(value="id") Long id,
                                       @RequestParam(value="photo") MultipartFile photo,
                                       @RequestParam(value="name") String name,
                                       @RequestParam(value="menuName") String menuName,
                                       @RequestParam(value="price") Integer price,
                                       @RequestParam(value="description") String description,
                                       @RequestParam(value="address") String address) throws IOException {

        Photo photoEntity = null;
        photoEntity = restaurantFileService.savePhotoLocal(photo);

        restaurantReviseService.updateRestaurant(id, name, menuName, price, description, address, photoEntity);

        return "redirect:/restaurant-detail/" + id;
    }
}
