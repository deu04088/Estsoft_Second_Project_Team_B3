## 🖥️ 프로젝트 소개
- 배달의 민족 이용하는 가게 사장님들을 위한 배달 시스템 관리 플랫폼 구현 
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
- 페이징 기능을 통한 식당 목록 정리
### 식당 검색
- 음식 메뉴 검색 통해 가게 검색
### 식당 정보 CRUD
-  업장 사진, 상호명, 대표 메뉴, 가격, 대표 메뉴 설명, 가게 위치 정보 등록
-  식당을 등록했던 계정으로 식당 정보 수정 및 삭제 가능
### 식당 상세
- 기본적인 식당 정보 표출
- 계정에 따른 기능 버튼(채팅/수정,삭제)
- 조회수 및 좋아요 표출
### 채팅
- 읽은 채팅과 안읽은 채팅 구분
- 일대일, 일대다 채팅 가능
### 관리자 페이지 
- 관리자 페이지 사용 시간 제한(연장 가능)
- DB에 등록된 계정과 게시물 관리
- 지역별 등록 식당 수 통계
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
## 🖋 커밋 컨벤션
| **type** | **설명** |
| --- | --- |
| Feat | 새로운 기능 추가 |
| Fix | 버그 수정 |
| Refactor | 코드 리펙터링(구조 변경, 네이밍 변경, 삭제, 주석 정리 등) |
| Docs | 문서 수정(README, 위키) |
| Assets | 이미지, 폰트 등 리소스 파일 변경 |
