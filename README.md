# 게시판
## 👉 구성환경
> SpringBoot, Gradle, Thymeleaf, Jpa(JPQL), Jar, MariaDB

## 👉 개요
> SpringBoot를 이용한 게시판 프로젝트 작성

## 👉 로그인, 회원가입 기능 구현
> Swagger API를 이용한 로그인/회원가입 처리, 로그아웃, 중복 로그인 처리, 로그인 정보 유지, 예외처리 등을 적용함.

#### ✨ 로그인, 회원가입 화면
* 사용자는 회원가입 화면을 통해 회원 가입이 가능함.
* 가입한 아이디 및 비밀번호로 로그인이 가능함.
* 유효성 검사를 통해 유효값 체크함.


![image](https://github.com/thdus12/Board/assets/97299700/9ac61436-f2c1-4c7d-a4ef-4ce7c0f86201)

![image](https://github.com/thdus12/Board/assets/97299700/2e0b6352-834f-41dd-8769-b36f65dd702e)

![image](https://github.com/thdus12/Board/assets/97299700/0f19ad60-aeba-474c-a714-757eea3bf4f1)

![image](https://github.com/thdus12/Board/assets/97299700/8468f39b-471d-4550-a039-fec335161b92)


## 👉 게시판 기능 구현

> (BootStrap(Guide link) + Thymeleaf)를 사용하여 화면을 구성하고 등록, 상세, 목록, 페이징 처리

#### ✨ 게시글 등록 화면

* 게시글의 제목, 내용을 작성하여 게시글 등록이 가능함.
* 유효성 검사를 하여 빈값일 경우 게시글 등록이 불가함.
* File Upload 영역의 Browse 버튼을 통해 사진 첨부가 가능함.
* +,- 버튼으로 파일 개수 조절 가능함.

![image](https://github.com/thdus12/Board/assets/97299700/c7ad9fba-0dc5-4488-bb29-63fe0e94ef7d)

![image](https://github.com/thdus12/Board/assets/97299700/886e9332-d1b9-44e6-a866-adf2a48b4a86)

#### ✨ 게시글 목록 화면

* 페이징 처리된 게시글 리스트를 확인하고, 게시글의 제목, 작성자, 조회수, 추천수, 등록시간을 확인 할 수 있음.
* 관리자 권한을 가진 사용자는 체크박스로 다중 선택하여 게시글 삭제가 가능함.

**<일반 사용자일 경우 화면>**
![image](https://github.com/thdus12/Board/assets/97299700/be2fb47b-8ad7-4cf0-ae6d-fccba655dc0f)

**<관리자 권한을 가진 사용자 경우 화면>**
![image](https://github.com/thdus12/Board/assets/97299700/51d14d04-d8d9-4dfe-a85a-2012a32ba58c)

#### ✨ 게시글 상세 화면

* 게시글 내용 및 댓글을 확인하고 게시글 추천/비추천이 가능함.
* 본인이 작성한 게시글만 수정 및 삭제가 가능함.

![image](https://github.com/thdus12/Board/assets/97299700/b8cc0264-61b7-412f-a763-7db3cb9a6a06)

![image](https://github.com/thdus12/Board/assets/97299700/5703c4ae-6c12-4031-a9e4-61ff13c53aa6)

#### ✨ 게시글 댓글 화면

* 게시글 내에 댓글, 대댓글을 추가 가능함.
* 본인이 작성한 댓글, 대댓글만 수정, 삭제가 가능함.
* 관리자 권한을 가진 사용자는 모든 댓글 삭제 가능함.

![image](https://github.com/thdus12/Board/assets/97299700/5ec00afe-3465-40aa-9f26-da044a9ed325)

![image](https://github.com/thdus12/Board/assets/97299700/763a49f0-2064-4ca6-9618-eb2f946acc70)

