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
- 배포 하지 않았기 때문에 localhost에서 동작합니다.
- JWT 방식
- Spring Security
- 과제 1. 사용자 회원가입 엔드포인트((POST)/signup)
  - Pattern.compile를 사용하여 @를 포함한 이메일 형식 지정
  - password.length()를 사용하여 비밀번호 문자열 길이 비교
  - 비밀번호는 SHA256 알고리즘으로 해시 후 Base64 인코딩하여 DB에 저장
- 과제 2. 사용자 로그인 엔드포인트((POST)/members/login)
  - 회원가입과 동일한 이메일, 비밀번호 유효성 검증 진행
  - Spring Security를 통해 USER 권한, 요청에 대한 인증, JWT 인증 필터 설정
  - JwtAuthenticationFilter 클래스를 통해 JWT 인증
  - JwtProvider 클래스를 통해 Refresh Token, Access Token, JWT 정보 가져오기, JWT 정보 검증
- 과제 3. 새로운 게시글을 생성하는 엔드포인트((POST)/posts/create)
  - 게시글의 제목, 내용 입력
  - 작성자의 이메일, 게시글 제목, 내용, 번호가 DB에 저장
- 과제 4. 게시글 목록을 조회하는 엔드포인트((GET)/posts?page=0&size=10)
  - PageRequest를 사용해 DB에 저장된 Entity들을 페이지로 나눔
  - 게시글 Repository의 finalAll 메서드를 PageRequest에 저장
  - URI 주소에 page=게시글 페이지, size=한 페이지에 보여지는 게시글 수 입력
  - page는 default=0으로 설정
  - size는 default=10, max=50으로 설정
- 과제 5. 특정 게시글을 조회하는 엔드포인트((GET)/posts/1)
  - URI 주소에 찾고자하는 게시글의 ID 값 입력
  - 게시글 Repository.findById를 사용
- 과제 6. 특정 게시글을 수정하는 엔드포인트((PUT)/posts/1)
  - URI 주소에 수정하고자하는 게시글의 ID 값 입력
  - 게시글 ID, 작성자는 수정 불가
  - 수정할 제목, 내용 입력
  - @AuthenticationPrincipal를 통해 UserDetails에 접근하여 로그인 정보를 가져옴
  - 게시글 작성자와 로그인한 정보가 같다면 수정
- 과제 7. 특정 게시글을 삭제하는 엔드포인트((DELETE)/posts/1)
  - URI 주소에 삭제하고자하는 게시글의 ID 값 입력
  - @AuthenticationPrincipal를 통해 UserDetails에 접근하여 로그인 정보를 가져옴
  - 게시글 작성자와 로그인한 정보가 같다면 삭제
API 명세(request/response 포함)
- Request
- 
- Response

