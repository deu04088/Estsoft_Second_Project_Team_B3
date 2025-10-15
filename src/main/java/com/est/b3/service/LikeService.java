package com.est.b3.service;

import com.est.b3.domain.Boss;
import com.est.b3.domain.Like;
import com.est.b3.domain.Restaurant;
import com.est.b3.repository.BossRepository;
import com.est.b3.repository.LikeRepository;
import com.est.b3.repository.RestaurantRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class LikeService {
    private final LikeRepository likeRepository;
    private final RestaurantRepository restaurantRepository;
    private final BossRepository bossRepository; // 로그인한 boss 정보

    @Transactional
    public int toggleLike(Long restaurantId, Long bossId) {
        Restaurant restaurant = restaurantRepository.findById(restaurantId)
                .orElseThrow(() -> new RuntimeException("식당 없음"));

        Optional<Like> existingLike = likeRepository.findByBossIdAndRestaurantId(bossId, restaurantId);

        if (existingLike.isPresent()) {
            // 이미 좋아요 눌렀으면 삭제
            likeRepository.delete(existingLike.get());
        } else {
            // 새 좋아요 추가
            Boss boss = bossRepository.findById(bossId)
                    .orElseThrow(() -> new RuntimeException("Boss 없음"));

            Like like = Like.builder()
                    .restaurant(restaurant)
                    .boss(boss)
                    .likedAt(LocalDateTime.now())
                    .build();
            likeRepository.save(like);
        }

        // 새 좋아요 수 반환
        return likeRepository.countByRestaurantId(restaurantId);
    }
}
