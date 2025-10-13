package com.est.b3.dto;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@Builder
public class PhotoResponseDto {
    private Long id;
    private String originalFilename;  // 업로드한 원본 파일명
    private String s3Key;             // UUID가 붙은 내부 저장 key
    private String s3Url;             // 접근 가능한 URL (/uploads/** or S3 URL)
    private LocalDateTime uploadDate;
}
