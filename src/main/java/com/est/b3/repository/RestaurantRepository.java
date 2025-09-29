package com.est.b3.repository;

import com.est.b3.domain.Restaurant;
import com.est.b3.dto.RestaurantResponseDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface RestaurantRepository extends JpaRepository<Restaurant, Long> {
  // JPQL 방식으로 좋아요 순 이름 순 정렬
  // 조회 결과를 DTO 객체로 반환
  // Restaurant의 사진 , 좋아요 조인
  // 유저 주소가 포함된 식당만 조회
  // count 사용하기위해 그룹핑
  // 좋아요 (l) 수 기준 내림차순 같으면 이름 오름차순
  @Query("""
    SELECT new com.est.b3.dto.RestaurantResponseDto(
        r.id,
        r.name,
        r.menuName,
        r.price,
        r.address,
        p.s3Url,
        r.viewCount
    )
    FROM Restaurant r
    LEFT JOIN r.photo p
    LEFT JOIN Like l ON l.restaurant = r
    WHERE r.address LIKE %:address%
    GROUP BY r.id, r.name, r.menuName, r.price, r.address, p.s3Url, r.viewCount
    ORDER BY COUNT(l) DESC, r.name ASC
""")
  Page<RestaurantResponseDto> findByAddressSortedByLikes(
      @Param("address") String address, Pageable pageable);


  // 마찬가지로 JPQL 사용하겠습니다.
  // 위의 방식에서 메뉴명 검색어를 포함하는 조건이 추가되었습니다.
  @Query("""
  SELECT new com.est.b3.dto.RestaurantResponseDto(
      r.id,
      r.name,
      r.menuName,
      r.price,
      r.address,
      p.s3Url,
      r.viewCount
  )
  FROM Restaurant r
  LEFT JOIN r.photo p
  LEFT JOIN Like l ON l.restaurant = r
  WHERE r.address LIKE %:address%
    AND r.menuName LIKE %:menu%
  GROUP BY r.id, r.name, r.menuName, r.price, r.address, p.s3Url, r.viewCount
  ORDER BY COUNT(l) DESC, r.name ASC
""")
  Page<RestaurantResponseDto> searchRestaurantsByMenu(
      @Param("address") String address,
      @Param("menu") String menu,
      Pageable pageable);
}