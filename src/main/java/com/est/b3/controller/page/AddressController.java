package com.est.b3.controller.page;

import com.est.b3.domain.Boss;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class AddressController {
/* 시큐리티 유져 디테일 부분과 연결대비용
  @GetMapping("/address-certify")
  public String addressCertifyPage(@AuthenticationPrincipal CustomUserDetails userDetails, Model model) {
    Boss boss = userDetails.getBoss(); // Boss 엔티티 가져오기
    model.addAttribute("user", boss);  // Thymeleaf에서 ${user.address} 에 값을 넣어주는용도
    return "address-certify";
  }*/
}