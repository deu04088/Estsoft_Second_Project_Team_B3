package com.est.b3.service;

import com.est.b3.domain.Boss;
import com.est.b3.domain.Photo;
import com.est.b3.domain.Restaurant;
import com.est.b3.dto.BossDto;
import com.est.b3.dto.SessionUserDTO;
import com.est.b3.repository.BossRepository;
import com.est.b3.repository.PhotoRepository;
import com.est.b3.repository.RestaurantRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.*;

@RequiredArgsConstructor
@Service
public class RestaurantEnrollService {
    private final PhotoRepository photoRepository;
    private final RestaurantRepository restaurantRepository;
    private final BossRepository bossRepository;

    // 로컬 저장소

    // ⚠️ 코드 (절대 경로 하드코딩 → 나중에 상대 경로로 대체)
    // private static final String UPLOAD_DIR = "C:/Coding/b3/sav";

    public Restaurant saveRestaurant(String name,
                                     String menuName,
                                     Integer price,
                                     String description,
                                     String address,
                                     Photo photo,
                                     Long bossId) {

//        Boss bossEntity = getBossById(boss.getId());
        Boss boss = bossRepository.findById(bossId)
                .orElseThrow(() -> new NoSuchElementException("해당 사용자이 없습니다. id=" + bossId));

        Map<String, String> add = separateAddress(address);

        Restaurant restaurantInfo = Restaurant.builder()
                //.photoUrl(photoUrl)             // Restaurant.java에 해당 Column 추가 필요
                .name(name)
                .menuName(menuName)
                .price(price)
                .description(description)
                .address(address)
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .boss(boss)
                .state(boss.getState())
                .photo(photo)
                .siDo(add.get("siDo"))
                .guGun(add.get("guGun"))
                .dongEupMyeon(add.get("dongEupMyeon"))
                .build();

        return restaurantRepository.save(restaurantInfo);
    }

    // boss 정보 가져오기
//    public Boss getBossInfo(String userName) {
//        Boss bossInfo = bossRepository.findByUserName(userName)
//                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 사장님입니다."));
//        return bossInfo;
//    }

    public BossDto getBossById(Long bossId) {
        Boss boss = bossRepository.findById(bossId)
                .orElseThrow(() -> new NoSuchElementException("해당 사용자이 없습니다. id=" + bossId));

        // 엔티티 → DTO 변환
        return new BossDto(
                boss.getId(),
                boss.getUserName(),
                boss.getNickName(),
                boss.getPassword(),
                boss.getAddress(),
                boss.getSiDo(),
                boss.getGuGun(),
                boss.getDongEupMyeon(),
                boss.getCreatedAt(),
                boss.getRole(),
                boss.getState()
      );
    }

    public Map<String, String> separateAddress(String address) {
        Map<String, String> separateAdd = new HashMap<>();
        String[] parts = address.split(" ");
        String siDo = parts.length > 0 ? parts[0] : "";
        String guGun = parts.length > 2 ? parts[1] : "";
//        String dongEupMyeon = parts.length == 2 ? parts[1] : parts.length > 2 ? parts[2] : "";
        String dongEupMyeon = parts.length >= 3 ? parts[2] : "";

        // 동,읍,면 뒤 주소
        if (parts.length > 3) {
            dongEupMyeon = parts[2] + " " + String.join(" ", Arrays.copyOfRange(parts, 3, parts.length));
        }

        separateAdd.put("siDo", siDo);
        separateAdd.put("guGun", guGun);
        separateAdd.put("dongEupMyeon", dongEupMyeon);
        return separateAdd;
    }
}




