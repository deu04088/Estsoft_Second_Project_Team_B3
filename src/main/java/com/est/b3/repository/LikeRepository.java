package com.est.b3.repository;

import com.est.b3.domain.Like;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface LikeRepository extends JpaRepository<Like, Long> {
  int countByRestaurantId(Long restaurantId); // 식당의 좋아요 수 카운트
    Optional<Like> findByBossIdAndRestaurantId(Long bossId, Long restaurantId);
}