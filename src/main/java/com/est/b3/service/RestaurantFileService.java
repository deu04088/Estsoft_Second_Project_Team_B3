package com.est.b3.service;

import com.est.b3.domain.Photo;
import com.est.b3.repository.PhotoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import software.amazon.awssdk.auth.credentials.AwsBasicCredentials;
import software.amazon.awssdk.auth.credentials.StaticCredentialsProvider;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.DeleteObjectRequest;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;

import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.UUID;

@Service
public class RestaurantFileService {

    private final PhotoRepository photoRepository;

    private final String bucketName;
    private final String region;
    private final S3Client s3Client;

    public RestaurantFileService(
            PhotoRepository photoRepository,
            @Value("${aws.access-key-id}") String accessKey,
            @Value("${aws.secret-access-key}") String secretKey,
            @Value("${aws.region}") String region,
            @Value("${aws.s3.bucket-name}") String bucketName
    ) {
        this.photoRepository = photoRepository;
        this.bucketName = bucketName;
        this.region = region;

        AwsBasicCredentials credentials = AwsBasicCredentials.create(accessKey, secretKey);

        this.s3Client = S3Client.builder()
                .region(Region.of(region))
                .credentialsProvider(StaticCredentialsProvider.create(credentials))
                .build();
    }

    /**
     * S3 업로드 방식
     */
    public Photo savePhotoS3(MultipartFile file) throws IOException {
        if (file == null || file.isEmpty()) {
            throw new IllegalArgumentException("파일이 비어있습니다.");
        }

        // 고유한 파일명 생성
        String originalFilename = file.getOriginalFilename();
        String ext = "";

        if (originalFilename != null && originalFilename.contains(".")) {
            ext = originalFilename.substring(originalFilename.lastIndexOf("."));
        }

        String uuid = UUID.randomUUID().toString();
        String newFileName = uuid + ext;

        // S3 업로드
        PutObjectRequest request = PutObjectRequest.builder()
                .bucket(bucketName)
                .key(newFileName)
                .contentType(file.getContentType())
                .build();

        s3Client.putObject(request, RequestBody.fromBytes(file.getBytes()));

        // S3 접근 가능한 URL 생성
        String s3Url = String.format("https://%s.s3.%s.amazonaws.com/%s", bucketName, region, newFileName);

        // DB 저장
        Photo photo = Photo.builder()
                .s3Key(newFileName)
                .s3Url(s3Url)
                .originalFilename(originalFilename)
                .uploadDate(LocalDateTime.now())
                .build();

        return photoRepository.save(photo);
    }

    /**
     * 로컬 업로드 방식 (로컬 개발용)
     */
    private static final String UPLOAD_DIR = System.getProperty("user.dir") + "/uploads/";

    public Photo savePhotoLocal(MultipartFile file) throws IOException {

        if (file == null || file.isEmpty()) {
            throw new IllegalArgumentException("파일이 비어있습니다.");
        }

        File dir = new File(UPLOAD_DIR);

        if (!dir.exists()) dir.mkdirs();

        String originalFilename = file.getOriginalFilename();
        String ext = "";

        if (originalFilename != null && originalFilename.contains(".")) {
            ext = originalFilename.substring(originalFilename.lastIndexOf("."));
        }

        String uuid = UUID.randomUUID().toString();
        String newFileName = uuid + ext;

        File dest = new File(dir, newFileName);
        file.transferTo(dest);

        Photo photo = Photo.builder()
                .s3Key(newFileName)
                .s3Url("/uploads/" + newFileName)
                .originalFilename(originalFilename)
                .uploadDate(LocalDateTime.now())
                .build();

        return photoRepository.save(photo);
    }

    /**
     * S3 파일 삭제
     */
    public void deletePhotoFromS3(String s3Key) {
        DeleteObjectRequest deleteRequest = DeleteObjectRequest.builder()
                .bucket(bucketName)
                .key(s3Key)
                .build();
        s3Client.deleteObject(deleteRequest);
    }
}
