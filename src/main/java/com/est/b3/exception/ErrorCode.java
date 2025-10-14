package com.est.b3.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public enum ErrorCode {

  //오류에 해당하는 문구는 차후 수정 필요
  //커스텀에러에 필요한 상수 자유롭게 추가하여 사용하시면됩니다
  // ex)EMAIL_NULL(HttpStatus.BAD_REQUEST, "이메일은 필수입력입니다.")

  // 회원가입
  USERNAME_DUPLICATE(HttpStatus.CONFLICT, "이미 존재하는 아이디입니다."),
  NICKNAME_DUPLICATE(HttpStatus.CONFLICT, "이미 존재하는 닉네임입니다."),

  // 로그인
  USER_NOT_FOUND(HttpStatus.NOT_FOUND, "해당 사용자를 찾을 수 없습니다."),
  PASSWORD_MISMATCH(HttpStatus.CONFLICT, "비밀번호가 일치하지 않습니다."),
  INVALID_REQUEST(HttpStatus.BAD_REQUEST, "잘못된 요청입니다."),

  // 세션
  SESSION_NOT_FOUND(HttpStatus.UNAUTHORIZED, "로그인된 세션이 없습니다."),

  // 어드민
  UNAUTHORIZED(HttpStatus.FORBIDDEN, "권한이 없습니다."),
  ADMIN_SESSION_EXPIRED(HttpStatus.UNAUTHORIZED, "관리자 인증 세션이 만료되었습니다. 다시 인증해주세요."),


  // 게시물
  POST_NOT_FOUND(HttpStatus.NOT_FOUND, "해당 게시글을 찾을 수 없습니다."),

  INTERNAL_SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "서버 오류가 발생했습니다."),
  NULL_POINTER(HttpStatus.BAD_REQUEST, "값을 입력해주세요"),
  ILLEGAL_ARGUMENT(HttpStatus.BAD_REQUEST, "잘못된 인자가 전달되었습니다.");


  private final HttpStatus status;
  private final String message;

  ErrorCode(HttpStatus status, String message) {
    this.status = status;
    this.message = message;
  }
}
