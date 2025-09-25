package com.est.b3.domain;

import jakarta.persistence.*;
import java.time.LocalDateTime;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "users")
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class User {

  @Id
  private Long id;

  @Column(name = "user_name", length = 20, nullable = false)
  private String userName;

  @Column(name = "nick_name", length = 30)
  private String nickName;

  @Column(name = "password", length = 60, nullable = false)
  private String password;

  @Column(name = "address", length = 50)
  private String address;

  @Column(name = "created_at")
  private LocalDateTime createdAt;

  @Column(name = "role", length = 10)
  private String role;

  @Column(name = "state")
  private Integer state;

}