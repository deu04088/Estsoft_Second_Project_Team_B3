## 🖥️ 프로젝트 소개
- 실제 주소 인증을 통한 지역 기반 소상공인(사장님) 커뮤니티 플랫폼 구현
  
<p align="center">
  <img src="https://github.com/user-attachments/assets/cfbdd168-e97e-4e5b-a00c-58575fa2c961" width="49%" />
  <img src="https://github.com/user-attachments/assets/d0d89e39-1008-4720-b004-fb778efba7a4" width="49%" />
</p>


---
## 👨‍👩‍👧‍👦 팀 소개
- 최보운
  - 팀장(프로젝트 총괄), 초기 기초 틀 작성
  - 메인페이지, 로그인, 로그아웃, 시큐리티 구현
- 김승호
  - 문서 관리(노션 팀페이지, 회의록)
  - 채팅 구현
- 김재영
  - 요구사항 명세서 작성, 전역 예제처리 구현
  - 주소 인증 구현, AWS 셋팅 및 배포
- 박주영
  - Git 관리(커밋 컨벤션 정리)
  - 식당 등록, 식당 상세 구현
- 장중원
  - DB 관리(JPA Entity, Repository)
  - 식당 목록, 식당 검색 구현
---
## 🕰️ 개발 기간
- 9월 23일 ~ 10월 20일
---
## 📍 요구사항
- spring 기반 프로젝트 구성
- view 의 경우에는 html+타임리프를 권장
- 기본 구성 외 추가적인 기능, 스타일링, 반응형은 자유롭게 진행
- 작업 진행 사항은 매일 업데이트

## ➕ 추가 요구사항
- 소셜 로그인
  - 구글 소셜 로그인을 기본으로 사용
  - 이외 소셜 로그인은 자유롭게 구성
- 관리자 페이지
  - 유저(사장님) 관리
    - 유저 정보 조회 (유저아이디, 상호명, 가입일, UserAgent, 회원 상태 등)
    - 유저 탈퇴 처리 기능
  - 게시물 관리
    - 게시물 정보 조회 (가게명, 대표 메뉴, 작성자, 지역, 등록일, 게시글 상태 등)
    - 게시물 상태 공개/비공개 처리 기능
    - 통계 (지역별로 등록된 식당 수 통계)
---
## 🎯 주요 기능
### 회원가입
- 비밀번호 복잡도 설정
- 비밀번호 안전도 판별
- 구글 소셜 로그인 구현
### 로그인/로그아웃
- 세션(Session) 생성
- 페이지 우측 상단 상시 로그아웃 가능
### 주소 인증
- 행정안전부 API를 통한 주소 검증
- 주소 정확도를 위한 정규식 제한
### 식당 목록
- 좋아요가 많은 순서로 등록된 식당 나열
- 계정 주소와 식당 주소 비교 후 인접 식당 표출
- 페이지네이션을 통한 식당 목록 정리
### 식당 검색
- 음식 메뉴 검색 통해 가게 검색
### 식당 정보 CRUD
-  업장 사진, 상호명, 대표 메뉴, 가격, 대표 메뉴 설명, 가게 위치 정보 등록
-  식당을 등록했던 계정으로 식당 정보 수정 및 삭제 가능
### 식당 상세
- 세부적인 식당 정보 표출
- 식당 등록 계정 판별에 따른 UI 분기 처리(채팅/수정,삭제)
- 조회수 및 좋아요 표출
### 채팅
- 사용자간 실시간 메시지 송수신 지원
- 읽은 채팅과 안읽은 채팅 구분
- 1:1 및 1:N 다중 사용자 채팅 지원
### 관리자 페이지 
- 관리자 전용 대시보드 제공
- 세션 기반 접근 시간 제한 설정(연장 가능)
- 등록된 사용자 계정 및 게시물에 대한 조회, 수정, 삭제 권한 제공
- 지역별 식당 등록 현황 통계 시각화
---
## ⚙ 기술 스택
| **영역** | **기술** |
| --- | --- |
| **Backend** | <img src="https://img.shields.io/badge/springboot-6DB33F?style=for-the-badge&logo=SpringBoot&logoColor=white"> |
| **Frontend** | <img src="https://img.shields.io/badge/thymeleaf-005F0F?style=for-the-badge&logo=Thymeleaf&logoColor=white"> <img src="https://img.shields.io/badge/bootstrap-7952B3?style=for-the-badge&logo=bootstrap&logoColor=white"> <img src="https://img.shields.io/badge/javascript-F7DF1E?style=for-the-badge&logo=javascript&logoColor=black">|
| **Database** | <img src="https://img.shields.io/badge/h2database-09476B?style=for-the-badge&logo=h2database&logoColor=white"> <img src="https://img.shields.io/badge/postgresql-4169E1?style=for-the-badge&logo=postgresql&logoColor=white"> |
| **API** | <img src="https://img.shields.io/badge/googlemaps-4285F4?style=for-the-badge&logo=googlemaps&logoColor=white"> |
| **배포** | <img src="https://img.shields.io/badge/amazonaws-232F3E?style=for-the-badge&logo=amazonaws&logoColor=white"> |
| **협업툴** | <img src="https://img.shields.io/badge/Notion-000000.svg?style=flat&logo=notion&logoColor=white"> <img src="https://img.shields.io/badge/github-181717?style=for-the-badge&logo=github&logoColor=white"> |
---
## 프로젝트 구조
```plaintext
B3 Project
 ├─ java
 │    ├─ config               # 환경 설정 관련 클래스 (시큐리티, CORS 등)
 │    ├─ controller           # 클라이언트 요청을 처리하는 컨트롤러
 │    ├─ domain               # 엔티티(Entity) 클래스
 │    ├─ dto                  # 데이터 전송 객체 (Data Transfer Object)
 │    ├─ exception            # 예외 처리 관련 클래스
 │    ├─ repository           # JPA 리포지토리 인터페이스
 │    ├─ service              # 비즈니스 로직을 담당하는 서비스 클래스
 │    └─ B3Application.java   # 스프링 부트 메인 실행 클래스
 │
 ├─ resources
 │    ├─ static                  # 정적 리소스 (CSS, JS, 이미지 등)
 │    ├─ templates               # Thymeleaf 템플릿 파일 (HTML)
 │    ├─ application.yml         # 공통 설정 파일
 │    ├─ application-local.yml   # 로컬 환경 설정 파일
 │    ├─ application-prod.yml    # 운영 환경 설정 파일
 │    └─ data.sql                # 초기 데이터 삽입용 SQL 스크립트
 │
 └─ .env
```
---
## 🖋 커밋 컨벤션
| **type** | **설명** |
| --- | --- |
| Feat | 새로운 기능 추가 |
| Fix | 버그 수정 |
| Refactor | 코드 리펙터링(구조 변경, 네이밍 변경, 삭제, 주석 정리 등) |
| Docs | 문서 수정(README, 위키) |
| Assets | 이미지, 폰트 등 리소스 파일 변경 |
