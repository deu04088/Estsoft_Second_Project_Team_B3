package com.est.b3.exception;


public class CustomException extends RuntimeException {
  private final ErrorCode errorCode;

  /* 단순 사용자 입력값 검증이 필요한 경우 ex)null 체크, 문자열 길이, 형식 확인
  컨트롤단에서 설정해주시면되고
  컨트롤단에서 사용예시
  @PostMapping
    public ResponseEntity<UserResponse> createUser(@RequestBody UserRequest request) {
        if(request.getEmail() == null || request.getEmail().isEmpty()) {
            throw new CustomException(ErrorCode.EMAIL_REQUIRED<-ErrorCode에 있는 상수 데이터);
        }
        이후 서비스 호출 부분

  DB 조회, 중복 체크, 권한 확인, 상태 검증등이 필요한 문제에선
  서비스단에 설정해주시면됩니다.
  서비스단에서 사용예시
  public UserResponse createUser(UserRequest request) {
    if(request.getEmail() == null || request.getEmail().isEmpty()) {
        throw new CustomException(ErrorCode.EMAIL_REQUIRED<-ErrorCode에 있는 상수 데이터);
    }
    if...다른 조건 및 문구 설정
    로직 시작 전 예외처리 검사를 먼저
    로직...
  */

  public CustomException(ErrorCode errorCode) {
    super(errorCode.getMessage());
    this.errorCode = errorCode;
  }

  public ErrorCode getErrorCode() {
    return errorCode;
  }
}