package com.est.b3.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class RestaurantResponseDto {
  private Long id;
  private String name;
  private String menuName;
  private int price;
  private String address;
  private String photoUrl;

  // JPQL 사용을 위해 어노테이션 대신 명시적 생성자 사용하겠습니다.
  public RestaurantResponseDto(Long id, String name, String menuName, int price, String address, String photoUrl) {
    this.id = id;
    this.name = name;
    this.menuName = menuName;
    this.price = price;
    this.address = address;
    this.photoUrl = photoUrl;
  }
}