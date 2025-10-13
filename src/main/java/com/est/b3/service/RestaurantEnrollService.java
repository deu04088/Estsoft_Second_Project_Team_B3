package com.est.b3.service;

import com.est.b3.domain.Photo;
import com.est.b3.domain.Restaurant;
import com.est.b3.repository.PhotoRepository;
import com.est.b3.repository.RestaurantRepository;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.*;

@RequiredArgsConstructor
@Service
public class RestaurantEnrollService {
    private final PhotoRepository photoRepository;
    private final RestaurantRepository restaurantRepository;

    // 로컬 저장소

    // ⚠️ 코드 (절대 경로 하드코딩 → 나중에 상대 경로로 대체)
    // private static final String UPLOAD_DIR = "C:/Coding/b3/sav";

    public Restaurant saveRestaurant(String name,
                                     String menuName,
                                     Integer price,
                                     String description,
                                     String address,
                                     Photo photo) {

        Restaurant restaurantInfo = Restaurant.builder()
                //.photoUrl(photoUrl)             // Restaurant.java에 해당 Column 추가 필요
                .name(name)
                .menuName(menuName)
                .price(price)
                .description(description)
                .address(address)
                .createdAt(LocalDateTime.now())
                .photo(photo)
                .build();

        // 로컬 저장 테스트용
//        ObjectMapper mapper = new ObjectMapper();
//        mapper.setSerializationInclusion(JsonInclude.Include.NON_NULL); // null 값 제거
//        mapper.enable(SerializationFeature.INDENT_OUTPUT);
//
//        try {
//            File dir = new File(UPLOAD_DIR);
//            if(!dir.exists()) dir.mkdirs(); // 폴더 없으면 자동 생성
//
//            // json 파일 생성
//            File file = new File(dir, "restaurant.json");
//            List<Map<String, Object>> restaurants = new ArrayList<>();
//
//            // 기존 JSON 읽어서 리스트에 추가
//            if (file.exists() && file.length() > 0) {
//                // 파일 내용 확인
//                JsonNode node = mapper.readTree(file);
//
//                if (node.isArray()) {
//                    // 배열이면 그대로 읽기
//                    restaurants = Arrays.asList(mapper.treeToValue(node, Map[].class));
//                    restaurants = new ArrayList<>(restaurants);
//                } else if (node.isObject()) {
//                    // 단일 객체이면 리스트로 변환
//                    Map<String, Object> single = mapper.treeToValue(node, Map.class);
//                    restaurants.add(single);
//                }
//            }
//
//            // Restaurant 객체에서 필요한 필드만 Map으로 추출
//            Map<String, Object> restaurantMap = new HashMap<>();
//            restaurantMap.put("name", restaurantInfo.getName());
//            restaurantMap.put("menuName", restaurantInfo.getMenuName());
//            restaurantMap.put("price", restaurantInfo.getPrice());
//            restaurantMap.put("description", restaurantInfo.getDescription());
//            restaurantMap.put("address", restaurantInfo.getAddress());
//            restaurantMap.put("photoUrl", photoUrl);
//
//            // 기존 내용 추가 (덮어씌워짐 방지)
//            restaurants.add(restaurantMap);
//            mapper.writeValue(file, restaurants);
//
//        } catch (IOException e) {
//            throw new RuntimeException("레스토랑 정보를 파일에 저장하는 중 오류 발생", e);
//        }

        return restaurantRepository.save(restaurantInfo);
    }

    public String savePhoto(MultipartFile photo) throws IOException {
        // 사진 검사
        if (photo == null || photo.isEmpty()) return null;

        // 경로 검사
//        File dir = new File(UPLOAD_DIR);
//        if (!dir.exists()) dir.mkdirs();
//
//        // 파일 이름 추출
        String filename = photo.getOriginalFilename();
//        // 파일 저장
//        File dest = new File(dir, filename);
//        photo.transferTo(dest);

        //photo 객체 생성(url만 저장)
        Photo photoEntity = Photo.builder()
                .s3Key(filename)            // 로컬 파일명 또는 S3 key
                .s3Url("https://s3.aws.com/" + filename) // 추후 실제 URL 추출 수정 필요
                .uploadDate(LocalDateTime.now())
                .build();

        // 객체 저장... 근데 지금 굳이....?
        photoRepository.save(photoEntity);

        // 로컬 URL 생성 (나중에 WebMvcConfigurer로 매핑 필요)
        return photoEntity.getS3Url();
    }

}




