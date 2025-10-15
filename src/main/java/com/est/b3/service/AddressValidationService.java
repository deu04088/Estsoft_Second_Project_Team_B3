package com.est.b3.service;

import java.net.URI;
import java.util.Map;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;


@Service
@RequiredArgsConstructor
public class AddressValidationService {

  private final String API_URL = "https://business.juso.go.kr/addrlink/addrLinkApi.do";

  @Value("${VALID_ADDRESS_API_KEY}")
  private String validAddressApiKey;


  public Map<String, Object> validateAddress(String keyword) {
    try {
      URI uri = UriComponentsBuilder.fromHttpUrl(API_URL)
          .queryParam("confmKey", validAddressApiKey)
          .queryParam("currentPage", 1)
          .queryParam("countPerPage", 1)
          .queryParam("keyword", keyword)
          .queryParam("resultType", "json")
          .build()
          .toUri();

      RestTemplate restTemplate = new RestTemplate();
      Map response = restTemplate.getForObject(uri, Map.class);

      return response;
    } catch (Exception e) {
      e.printStackTrace();
      return null;
    }
  }
}