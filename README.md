# 게시판

구성환경 : SpringBoot, Gradle, Thymeleaf, Jpa(JPQL), Jar, MariaDB

## 개요

SpringBoot를 이용한 게시판 프로젝트 작성

### ✅ 로그인, 회원가입

스프링 시큐리티(Spring Security)를 이용한 로그인 처리, 로그아웃, 중복 로그인 처리, 로그인 정보 유지, 예외처리 등을 적용함

![image](https://user-images.githubusercontent.com/97299700/235307359-cacc1ee6-3c83-4a0a-800a-2fd720e65549.png)

![image](https://github.com/thdus12/Board/assets/97299700/124f3d45-7842-4457-9fef-f833fc32f588)

![image](https://user-images.githubusercontent.com/97299700/235307389-66044c9e-a304-4fd9-885d-ca6050ee0fcf.png)

![image](https://github.com/thdus12/Board/assets/97299700/13657422-000b-4a22-834b-ff01ca543702)


## 게시판 기능 구현

(BootStrap(Guide link) + Thymeleaf)를 사용하여 화면을 구성하고 등록, 상세, 목록, 페이징 처리

### ✅ 게시글 등록

게시글 등록하는 화면. 유효성 검사를 하여 빈값일 경우 게시글 등록이 불가함

![image](https://github.com/thdus12/Board/assets/97299700/6cc12ee1-85fc-4eec-a1bd-44e7f182250f)

![image](https://github.com/thdus12/Board/assets/97299700/37ebf3e2-03e7-4f0d-a509-d904f0d43e38)

### ✅ 게시글 리스트 확인 및 삭제, 페이징

게시글 리스트를 확인하고 체크박스로 다중 선택하여 게시글 삭제가 가능함

![image](https://github.com/thdus12/Board/assets/97299700/50716119-574a-4329-a164-fbb51106a306)

### ✅ 게시글 확인 및 수정

게시글 내용 및 댓글을 확인하고 수정 및 삭제가 가능함.

![image](https://github.com/thdus12/Board/assets/97299700/40b4bf70-313c-40f7-b342-be64fa0cfdb7)

![image](https://github.com/thdus12/Board/assets/97299700/654a5427-cc29-491b-949c-956c9fdbfa72)

### ✅ 게시글 댓글 추가, 수정, 삭제

게시글 내에 댓글, 대댓글을 추가, 수정, 삭제가 가능함.

![image](https://github.com/thdus12/Board/assets/97299700/ebe0c63e-9fd1-479c-8780-8d1fadd7c9e0)

![image](https://github.com/thdus12/Board/assets/97299700/763a49f0-2064-4ca6-9618-eb2f946acc70)

