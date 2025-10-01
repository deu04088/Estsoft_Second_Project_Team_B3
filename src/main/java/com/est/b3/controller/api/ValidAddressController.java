package com.est.b3.controller.api;

import com.est.b3.dto.AddressRequest;
import com.est.b3.service.AddressValidationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class ValidAddressController {

  private final AddressValidationService validationService;

  @GetMapping("/api/check-address")
  public String checkAddress(@RequestParam String address) {
    boolean isValid = validationService.isValidAddress(address);
    return isValid ? "VALID" : "INVALID";
  }

  @PostMapping("/api/update-address")
  public ResponseEntity<Void> updateAddress(
      @AuthenticationPrincipal UserDetails userDetails,
      @RequestBody AddressRequest request
  ) {
    String address = request.getAddress();
    // 패턴 검증
    String regex = "^[가-힣]+(시|도)\\s([가-힣]+(구|군|시)\\s)?[가-힣]+(동|읍|면)$";
    if (address == null || !address.matches(regex)) {
      return ResponseEntity.badRequest().build(); // 형식 오류 시 400 반환
    }

    validationService.updateAddress(userDetails.getUsername(), request.getAddress());
    return ResponseEntity.ok().build();
  }
}
