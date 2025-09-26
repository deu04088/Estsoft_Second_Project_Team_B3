package com.est.b3.exception;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

  // 사용자 정의 예외 처리 - 예외처리 문구 추가 필요시 ErrorCode에 상수 추가 후 사용
  // CustomException 파일에 사용예시 주석달려있음
  @ExceptionHandler(CustomException.class)
  public ResponseEntity<ErrorResponse> handleCustomException(CustomException e) {
    ErrorCode errorCode = e.getErrorCode();
    return ResponseEntity
        .status(errorCode.getStatus())
        .body(ErrorResponse.of(errorCode));
  }

  // NullPointerException 커스텀에서 처리되지 않은 에러 처리용
  @ExceptionHandler(NullPointerException.class)
  public ResponseEntity<ErrorResponse> handleNullPointer(NullPointerException e) {
    e.printStackTrace();
    return ResponseEntity
        .status(ErrorCode.NULL_POINTER.getStatus())
        .body(ErrorResponse.of(ErrorCode.NULL_POINTER));
  }

  // IllegalArgumentException 커스텀에서 처리되지 않은 에러 처리용
  @ExceptionHandler(IllegalArgumentException.class)
  public ResponseEntity<ErrorResponse> handleIllegalArgument(IllegalArgumentException e) {
    e.printStackTrace();
    return ResponseEntity
        .status(ErrorCode.ILLEGAL_ARGUMENT.getStatus())
        .body(ErrorResponse.of(ErrorCode.ILLEGAL_ARGUMENT));
  }

  //위에서 걸러지지 않은 모든예외 처리용  INTERNAL_SERVER_ERROR -> 서버 에러 문구로 표시
  @ExceptionHandler(Exception.class)
  public ResponseEntity<ErrorResponse> handleException(Exception e) {
    e.printStackTrace();
    return ResponseEntity
        .status(ErrorCode.INTERNAL_SERVER_ERROR.getStatus())
        .body(ErrorResponse.of(ErrorCode.INTERNAL_SERVER_ERROR));
  }
}
