package com.est.b3.service;


import io.github.cdimascio.dotenv.Dotenv;
import java.net.URI;
import java.util.Map;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

@Service
public class AddressValidationService {

  private final String API_URL = "https://business.juso.go.kr/addrlink/addrLinkApi.do";
  // 발급받은 행안부 API 키 env에 들어있음
  private final String ValidAddressApiKey = Dotenv.load().get("VALID_ADDRESS_API_KEY");

  public boolean isValidAddress(String keyword) {
    try {
      URI uri = UriComponentsBuilder.fromHttpUrl(API_URL)
          .queryParam("confmKey", ValidAddressApiKey)
          .queryParam("currentPage", 1)
          .queryParam("countPerPage", 1)
          .queryParam("keyword", keyword)
          .queryParam("resultType", "json")
          .encode()
          .build()
          .toUri();

      RestTemplate restTemplate = new RestTemplate();
      Map response = restTemplate.getForObject(uri, Map.class);

      if (response == null) return false;

      Map results = (Map) response.get("results");
      Map common = (Map) results.get("common");

      int totalCount = Integer.parseInt((String)common.get("totalCount"));
      return totalCount > 0;
    } catch (Exception e) {
      e.printStackTrace();
      return false;
    }
  }
}
