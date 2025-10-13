package com.est.b3.service;

import io.github.cdimascio.dotenv.Dotenv;
import java.net.URI;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;


@Service
@RequiredArgsConstructor
public class AddressValidationService {

  private final String API_URL = "https://business.juso.go.kr/addrlink/addrLinkApi.do";
  private final String ValidAddressApiKey = Dotenv.load().get("VALID_ADDRESS_API_KEY");

  public Map<String, Object> validateAddress(String keyword) {
    try {
      URI uri = UriComponentsBuilder.fromHttpUrl(API_URL)
          .queryParam("confmKey", ValidAddressApiKey)
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