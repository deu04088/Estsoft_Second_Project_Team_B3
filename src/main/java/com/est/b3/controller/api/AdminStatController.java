package com.est.b3.controller.api;

import com.est.b3.dto.admin.RegionStatResponse;
import com.est.b3.dto.api.ApiResponse;
import com.est.b3.repository.RestaurantRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/admin/stats")
@RequiredArgsConstructor
public class AdminStatController {

    private final RestaurantRepository restaurantRepository;

    /**
     * 지역별 식당 수 통계 조회
     */
    @GetMapping("/regions")
    public ResponseEntity<ApiResponse<List<RegionStatResponse>>> getRegionStats() {

        List<Object[]> results = restaurantRepository.countRestaurantsByRegion();

        List<RegionStatResponse> stats = results.stream()
                .map(r -> new RegionStatResponse(
                        (String) r[0],
                        (String) r[1],
                        (String) r[2],
                        (Long) r[3]
                ))
                .toList();

        return ResponseEntity.ok(ApiResponse.success(200, "지역별 식당 통계 조회 성공", stats));
    }
}
