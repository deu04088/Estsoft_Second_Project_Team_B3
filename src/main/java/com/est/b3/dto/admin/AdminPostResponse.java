package com.est.b3.dto.admin;

import com.est.b3.domain.Restaurant;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class AdminPostResponse {
    private Long id;
    private String restaurantName;
    private String menuName;
    private String bossNickName;
    private String address;
    private LocalDateTime createdAt;
    private Integer state; // 1=공개, 0=비공개

    public AdminPostResponse(Restaurant restaurant) {
        this.id = restaurant.getId();
        this.restaurantName = restaurant.getName();
        this.menuName = restaurant.getMenuName();
        this.bossNickName = restaurant.getBoss().getNickName();
        this.address = restaurant.getAddress();
        this.createdAt = restaurant.getCreatedAt();
        this.state = restaurant.getState();
    }
}
