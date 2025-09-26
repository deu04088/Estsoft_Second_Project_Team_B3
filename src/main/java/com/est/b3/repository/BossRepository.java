package com.est.b3.repository;

import com.est.b3.domain.Boss;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface BossRepository extends JpaRepository<Boss, Long> {

    // username으로 조회 (로그인 시 사용)
    Optional<Boss> findByUserName(String userName);

    // username 중복 여부 확인
    boolean existsByUserName(String userName);

    // nickname 중복 여부 확인
    boolean existsByNickName(String nickName);
}
