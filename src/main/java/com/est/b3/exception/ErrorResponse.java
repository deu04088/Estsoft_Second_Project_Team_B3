package com.est.b3.exception;

import java.time.LocalDateTime;
import lombok.Getter;

@Getter
public class ErrorResponse {
  private final int status;
  private final String error;
  private final String code;
  private final String message;
  private final LocalDateTime timestamp;

  private ErrorResponse(ErrorCode errorCode) {
    this.status = errorCode.getStatus().value();
    this.error = errorCode.getStatus().getReasonPhrase();
    this.code = errorCode.name();
    this.message = errorCode.getMessage();
    this.timestamp = LocalDateTime.now();
  }

  public static ErrorResponse of(ErrorCode errorCode) {
    return new ErrorResponse(errorCode);
  }

  /* Response JSON 예시 프론트에서 필요한 값만 출력 제어해주시면 됩니다.
  {
    "status": 400,
      "error": "Bad Request",
      "code": "NULL_POINTER",
      "message": "Null 값이 존재합니다.",
      "timestamp": "2025-09-25T10:16:40.456"
  }
*/
}
