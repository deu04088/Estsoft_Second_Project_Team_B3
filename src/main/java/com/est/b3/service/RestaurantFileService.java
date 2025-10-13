package com.est.b3.service;

import com.est.b3.domain.Photo;
import com.est.b3.dto.PhotoResponseDto;
import com.est.b3.repository.PhotoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.UUID;

@RequiredArgsConstructor
@Service
public class RestaurantFileService {

    private final PhotoRepository photoRepository;

    // 공용 업로드 폴더 (프로젝트 루트 기준, 항상 절대 경로)
    private static final String UPLOAD_DIR = System.getProperty("user.dir") + "/uploads/";


    public Photo savePhotoLocal(MultipartFile file) throws IOException {
        if (file == null || file.isEmpty()) {
            throw new IllegalArgumentException("파일이 비어있습니다.");
        }

        // 업로드 폴더 확인 및 생성
        File dir = new File(UPLOAD_DIR);

        if (!dir.exists()) {
            dir.mkdirs();
        }

        // UUID 기반 새 파일명 생성
        String originalFilename = file.getOriginalFilename();
        String ext = "";

        if (originalFilename != null && originalFilename.contains(".")) {
            ext = originalFilename.substring(originalFilename.lastIndexOf("."));
        }

        String uuid = UUID.randomUUID().toString();
        String newFileName = uuid + ext;

        // 실제 파일 저장
        File dest = new File(dir, newFileName);
        file.transferTo(dest);

        // DB에 Photo 엔티티 저장
        Photo photo = Photo.builder()
                .s3Key(newFileName)
                .s3Url("/uploads/" + newFileName) // 로컬 접근 URL (WebMvcConfigurer로 매핑)
                .originalFilename(originalFilename)
                .uploadDate(LocalDateTime.now())
                .build();

        return photoRepository.save(photo);
    }
}
