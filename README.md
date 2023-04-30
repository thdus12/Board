# 게시판

구성환경 : SpringBoot, Gradle, Thymeleaf, Jpa(JPQL), Jar, MariaDB

## 개요

SpringBoot를 이용한 게시판 프로젝트 작성

### ✅ 로그인, 회원가입

스프링 시큐리티(Spring Security)를 이용한 로그인 처리, 로그아웃, 중복 로그인 처리, 로그인 정보 유지, 예외처리 등을 적용함

![image](https://user-images.githubusercontent.com/97299700/235307359-cacc1ee6-3c83-4a0a-800a-2fd720e65549.png)

![image](https://user-images.githubusercontent.com/97299700/235307389-66044c9e-a304-4fd9-885d-ca6050ee0fcf.png)

## 게시판 기능 구현

(BootStrap(Guide link) + Thymeleaf)를 사용하여 화면을 구성하고 등록, 상세, 목록, 페이징 처리

### ✅ 게시글 등록

게시글 등록하는 화면. 유효성 검사를 하여 빈값일 경우 게시글 등록이 불가함

![image](https://user-images.githubusercontent.com/97299700/235337555-ce0d2cc5-c98c-4bb9-afa9-320f9569e666.png)

![image](https://user-images.githubusercontent.com/97299700/235337425-6d3191d3-514f-458f-a7cd-f6394993e6cd.png)


### ✅ 게시글 리스트 확인 및 삭제, 페이징

게시글 리스트를 확인하고 체크박스로 다중 선택하여 게시글 삭제가 가능함

![image](https://user-images.githubusercontent.com/97299700/235337462-ac1d3e82-afb9-4974-9727-66509097afd3.png)

### ✅ 게시글 확인 및 수정



