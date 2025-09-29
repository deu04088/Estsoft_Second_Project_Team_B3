package com.est.b3.controller.api;

import com.est.b3.service.AddressValidationService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ValidAddressController {

  private final AddressValidationService validationService;

  public ValidAddressController(AddressValidationService validationService) {
    this.validationService = validationService;
  }

  @GetMapping("/api/check-address")
  public String checkAddress(@RequestParam String address) {
    boolean isValid = validationService.isValidAddress(address);
    return isValid ? "VALID" : "INVALID";
  }
}
