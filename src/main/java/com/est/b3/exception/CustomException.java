package com.est.b3.exception;


public class CustomException extends RuntimeException {
  private final ErrorCode errorCode;


/*  CustomException 간단하에 하나의 케이스만 발생할경우 컨트롤러에서 설정해줘도 되지만
  하나의 동작에 2개 이상의 분기가 되어야 할 경우 서비스단에서 설정해주셔야합니다.
  예시

  if(request.getEmail() == null || request.getEmail().isEmpty()) {
    throw new CustomException(ErrorCode.EMAIL_REQUIRED<-Errorcode의 상수 데이터);
  }*/



  public CustomException(ErrorCode errorCode) {
    super(errorCode.getMessage());
    this.errorCode = errorCode;
  }

  public ErrorCode getErrorCode() {
    return errorCode;
  }
}