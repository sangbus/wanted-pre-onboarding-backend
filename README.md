# wanted-pre-onboarding-backend

지원자의 성명
- 이름 : 박상환

애플리케이션의 실행 방법 (엔드포인트 호출 방법 포함)
- Version
  - Java 17
  - Spring Boot 3.1.2, Gradle-Groovy
  - MYSQL 8.0.32

|HTTP Method|URI|Description|
|------|---|---|
|POST|/signup|회원가입|
|POST|/members/login|로그인|
|POST|/posts/create|게시글 작성|
|GET|/posts?page=0&size=10|게시글 목록 조회|
|GET|/posts/1|특정 게시글 조회|
|PUT|/posts/1|게시글 수정|
|DELETE|/posts/1|게시글 삭제|


데이터베이스 테이블 구조
- 테이블 : 3개(member, member_roles, post)
- 연관 관계
  - member-member_roles, 1대1 관계
  - member-post, 1대다 관계
    
 ![DB구조](https://github.com/sangbus/wanted-pre-onboarding-backend/assets/87519025/6c6d8d20-2a9e-4e8c-800d-03b720289c9c)
  - member
    - email(PK) : 이메일, 영문 최대 20자
    - password : 비밀번호, 영문 최대 50자
  - member_roles
    - member_email(fk) : 이메일, 영문 최대 20자
    - roles : 사용자의 권한, 회원가입하면 USER 권한 얻음, 영문 최대 10자
  - post
    - id : 인덱스 값
    - title : 제목, 영문 최대 20자
    - content : 내용, 영문 최대 200자
    - email(fk) : 이메일, 영문 최대 20자

구현한 API의 동작을 촬영한 데모 영상 링크
- 

구현 방법 및 이유에 대한 간략한 설명
- 배포는 하지 않았기 때문에 localhost에서 동작합니다.
- JWT 방식
- Spring Security

API 명세(request/response 포함)
- Request
- 
- Response

