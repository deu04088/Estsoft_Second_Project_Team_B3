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
    validationService.updateAddress(userDetails.getUsername(), request.getAddress());
    return ResponseEntity.ok().build();
  }
}
