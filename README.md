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

![image](https://github.com/thdus12/Board/assets/97299700/3d3d921a-92c9-4845-9c50-2267915a6d2d)

![image](https://github.com/thdus12/Board/assets/97299700/a1337fa4-dd26-4706-aa34-3b14ccccb8b8)

### ✅ 게시글 리스트 확인 및 삭제, 페이징

게시글 리스트를 확인하고 체크박스로 다중 선택하여 게시글 삭제가 가능함

![image](https://github.com/thdus12/Board/assets/97299700/50716119-574a-4329-a164-fbb51106a306)

### ✅ 게시글 확인 및 수정

게시글 내용 및 댓글을 확인하고 수정 및 삭제가 가능함.

![image](https://github.com/thdus12/Board/assets/97299700/40b4bf70-313c-40f7-b342-be64fa0cfdb7)

![image](https://github.com/thdus12/Board/assets/97299700/654a5427-cc29-491b-949c-956c9fdbfa72)

### ✅ 게시글 댓글 추가, 수정, 삭제

게시글 내에 댓글, 대댓글을 추가, 수정, 삭제가 가능함.

![image](https://github.com/thdus12/Board/assets/97299700/300c1d6b-b685-4313-ac03-5b2f3ca48601)

![image](https://github.com/thdus12/Board/assets/97299700/8bb1faea-aa44-4312-ba6e-fc04c8186b51)

