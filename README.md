# 게시판

구성환경 : SpringBoot, Gradle, Thymeleaf, Jpa(JPQL), Jar, MariaDB

## 개요

SpringBoot를 이용한 게시판 프로젝트 작성

### ✅ 로그인, 회원가입

스프링 시큐리티(Spring Security)를 이용한 로그인 처리, 로그아웃, 중복 로그인 처리, 로그인 정보 유지, 예외처리 등을 적용함

![image](https://github.com/thdus12/Board/assets/97299700/9ac61436-f2c1-4c7d-a4ef-4ce7c0f86201)

![image](https://github.com/thdus12/Board/assets/97299700/2e0b6352-834f-41dd-8769-b36f65dd702e)

![image](https://github.com/thdus12/Board/assets/97299700/0f19ad60-aeba-474c-a714-757eea3bf4f1)

![image](https://github.com/thdus12/Board/assets/97299700/8468f39b-471d-4550-a039-fec335161b92)


## 게시판 기능 구현

(BootStrap(Guide link) + Thymeleaf)를 사용하여 화면을 구성하고 등록, 상세, 목록, 페이징 처리

### ✅ 게시글 등록

게시글 등록하는 화면. 유효성 검사를 하여 빈값일 경우 게시글 등록이 불가함

![image](https://github.com/thdus12/Board/assets/97299700/ba8a5e98-fbe6-4bbd-a866-432c91f95c33)

![image](https://github.com/thdus12/Board/assets/97299700/01ae2e58-050a-4ccd-bed2-77916e73cbdd)

### ✅ 게시글 리스트 확인 및 삭제, 페이징

게시글 리스트를 확인하고 체크박스로 다중 선택하여 게시글 삭제가 가능함

![image](https://github.com/thdus12/Board/assets/97299700/97f69c9e-dc72-41e7-b77a-7dbed0eb0b35)

### ✅ 게시글 확인 및 수정

게시글 내용 및 댓글을 확인하고 수정 및 삭제가 가능함.

![image](https://github.com/thdus12/Board/assets/97299700/b8cc0264-61b7-412f-a763-7db3cb9a6a06)

![image](https://github.com/thdus12/Board/assets/97299700/5703c4ae-6c12-4031-a9e4-61ff13c53aa6)

### ✅ 게시글 댓글 추가, 수정, 삭제

게시글 내에 댓글, 대댓글을 추가, 수정, 삭제가 가능함.

![image](https://github.com/thdus12/Board/assets/97299700/5ec00afe-3465-40aa-9f26-da044a9ed325)

![image](https://github.com/thdus12/Board/assets/97299700/763a49f0-2064-4ca6-9618-eb2f946acc70)

