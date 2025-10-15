package com.est.b3.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class RestaurantInfoDto {

    private Long id;
    private String name;
    private String menuName;
    private int price;
    private String address;
    private String s3Url;
    private Integer viewCount = 0;
    private String description;
    private String siDo;
    private String guGun;
    private String dongEupMyeon;
    private Integer likeCount = 0;


    public RestaurantInfoDto(Long id, String name, String menuName, int price,
        String address, String photoUrl, Integer viewCount, String description,
        String siDo, String guGun, String dongEupMyeon, Integer likeCount) {
        this.id = id;
        this.name = name;
        this.menuName = menuName;
        this.price = price;
        this.address = address;
        this.s3Url = photoUrl;
        this.viewCount = viewCount;
        this.description = description;
        this.siDo = siDo;
        this.guGun = guGun;
        this.dongEupMyeon = dongEupMyeon;
        this.likeCount = likeCount;
    }

}
