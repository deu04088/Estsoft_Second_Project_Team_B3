package com.est.b3.controller.api;

import com.est.b3.domain.Boss;
import com.est.b3.dto.SessionUserDTO;
import com.est.b3.repository.BossRepository;
import com.est.b3.service.AddressValidationService;
import jakarta.servlet.http.HttpSession;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class ValidAddressController {

  private final AddressValidationService addressValidationService;
  private final BossRepository bossRepository;
  
  @PostMapping("/api/check-address")
  public ResponseEntity<?> checkAddress(@RequestBody Map<String, String> body) {
    String address = body.get("address");

    String regex = "^[가-힣]+(시|도)\\s([가-힣]+(구|군|시)\\s)?[가-힣]+(동|읍|면)$";
    if (address == null || !address.matches(regex)) {
      return ResponseEntity.ok(Map.of("status", "INVALID", "reason", "INVALID_FORMAT"));
    }

    Map<String, Object> response = addressValidationService.validateAddress(address);

    if (response == null) return ResponseEntity.ok(Map.of("status", "INVALID"));

    Map results = (Map) response.get("results");
    Map common = (Map) results.get("common");
    int totalCount = Integer.parseInt((String) common.get("totalCount"));

    if (totalCount > 0) {
      return ResponseEntity.ok(results);
    } else {
      return ResponseEntity.ok(Map.of("status", "INVALID"));
    }
  }

  @PostMapping("/api/update-address")
  public ResponseEntity<?> updateAddress(@RequestBody Map<String, String> body, HttpSession session) {
    //세션 체크
    SessionUserDTO loginUser = (SessionUserDTO) session.getAttribute("loginBoss");
    if (loginUser == null) {
      return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("로그인이 필요합니다.");
    }

    //fullAddress 분리
    String fullAddress = body.get("address");
    String[] parts = fullAddress.split(" ");
    String siDo = parts.length > 0 ? parts[0] : "";
    String guGun = parts.length > 2 ? parts[1] : "";
    String dongEupMyeon = parts.length == 2 ? parts[1] : parts.length > 2 ? parts[2] : "";

    //DB 업데이트
    Boss boss = bossRepository.findById(loginUser.getId())
        .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 사용자입니다."));
    boss.updateAddress(fullAddress, siDo, guGun, dongEupMyeon);
    bossRepository.save(boss);

    //세션 갱신
    SessionUserDTO updatedUser = new SessionUserDTO(
        boss.getId(),
        boss.getUserName(),
        boss.getNickName(),
        boss.getSiDo(),
        boss.getGuGun(),
        boss.getDongEupMyeon(),
        boss.getAddress(),
        boss.getRole()
    );
    session.setAttribute("loginBoss", updatedUser);

    return ResponseEntity.ok("주소 변경 완료");
  }
}
