# wanted-pre-onboarding-backend

# 1. 지원자의 성명
- 이름 : 박상환

# 2. 애플리케이션의 실행 방법 (엔드포인트 호출 방법 포함)
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


# 3. 데이터베이스 테이블 구조
- 테이블 : 3개(member, member_roles, post)
- 연관 관계
  - member-member_roles, 1대1 관계
  - member-post, 1대다 관계
  
    ![DB구조](https://github.com/sangbus/wanted-pre-onboarding-backend/assets/87519025/b374f371-4fdf-4f9f-a4a7-8ab69732d39d)
    
  - member
    - email(PK) : 이메일, 영문 최대 20자
    - password : 비밀번호, 영문 최소 8자리 이상, 최대 50자
  - member_roles
    - member_email : 이메일, 영문 최대 20자
    - roles : 사용자의 권한, 회원가입하면 USER 권한 얻음, 영문 최대 10자
  - post
    - id : 인덱스 값
    - title : 제목, 영문 최대 20자
    - content : 내용, 영문 최대 200자
    - email(fk) : 이메일, 영문 최대 20자

# 4. 구현한 API의 동작을 촬영한 데모 영상 링크
- https://www.youtube.com/watch?v=4oMGl8LDWeQ
# 5. 구현 방법 및 이유에 대한 간략한 설명
- 배포 하지 않았기 때문에 localhost에서 동작
- 스프링의 어려운 초기 설정을 간편하게 처리해주어 비즈니스 로직을 구현하는데에 집중할 수 있게 Spring Boot 사용
- 보안과 관련해서 체계적으로 많은 옵션을 제공해주기 때문에 Spring Security 사용
- Access Token 1시간. Refresh Token 1일로 설정
### 과제 1. 사용자 회원가입 엔드포인트((POST)/signup)
  - Pattern.compile를 사용하여 @를 포함한 이메일 형식 지정
  - password.length()를 사용하여 비밀번호 문자열 길이 비교
  - 비밀번호는 SHA256 알고리즘으로 해시 후 Base64 인코딩하여 DB에 저장
### 과제 2. 사용자 로그인 엔드포인트((POST)/members/login)
  - 회원가입과 동일한 이메일, 비밀번호 유효성 검증 진행
  - Spring Security를 통해 USER 권한, 요청에 대한 인증, JWT 인증 필터 설정
  - JwtAuthenticationFilter 클래스를 통해 JWT 인증
  - JwtProvider 클래스를 통해 Refresh Token, Access Token, JWT 정보 가져오기, JWT 정보 검증
### 과제 3. 새로운 게시글을 생성하는 엔드포인트((POST)/posts/create)
  - 게시글의 제목, 내용 입력
  - 작성자의 이메일, 게시글 번호, 제목, 내용이 DB에 저장
### 과제 4. 게시글 목록을 조회하는 엔드포인트((GET)/posts?page=0&size=10)
  - PageRequest를 사용해 DB에 저장된 Entity들을 페이지로 나눔
  - 게시글 Repository의 finalAll 메서드를 PageRequest에 저장
  - URI 주소에 page=게시글 페이지, size=한 페이지에 보여지는 게시글 수 입력
  - page는 default=0으로 설정
  - size는 default=10, max=50으로 설정
### 과제 5. 특정 게시글을 조회하는 엔드포인트((GET)/posts/1)
  - URI 주소에 찾고자하는 게시글의 ID 값 입력
  - 게시글 Repository.findById를 사용
### 과제 6. 특정 게시글을 수정하는 엔드포인트((PUT)/posts/1)
  - URI 주소에 수정하고자하는 게시글의 ID 값 입력
  - 게시글 ID, 작성자는 수정 불가
  - 수정할 제목, 내용 입력
  - @AuthenticationPrincipal를 통해 UserDetails에 접근하여 로그인 정보를 가져옴
  - 게시글 작성자와 로그인한 정보가 같다면 수정
### 과제 7. 특정 게시글을 삭제하는 엔드포인트((DELETE)/posts/1)
  - URI 주소에 삭제하고자하는 게시글의 ID 값 입력
  - @AuthenticationPrincipal를 통해 UserDetails에 접근하여 로그인 정보를 가져옴
  - 게시글 작성자와 로그인한 정보가 같다면 삭제

# 6. API 명세(request/response 포함)
### 6.1. 회원가입
   #### 6.1.1. Request
   - (POST) /signup
```JSON
     {
       "email" : "ggg12@naver.com",
       "password" : "1q2w3e4e"
     }
```
   #### 6.1.2. Response
   - 200 OK
     - "회원가입 성공"
   - 400 Bad Request(이메일 검증)
     - "이메일 형식에 적합하지 않습니다."
   - 400 Bad Request(비밀번호 검증)
     - "비밀번호 형식에 적합하지 않습니다. 8자리 이상 설정해주세요."
### 6.2. 로그인
  #### 6.2.1. Request
   - (POST) /members/login
```JSON
    {
      "email":"ggg12@naver.com", 
      "password" : "1q2w3e4e"
    }
```
  #### 6.2.2. Response
  - 200 OK
```JSON
{
    GrantType : Bearer
    RefreshToken : eyJhbGciOiJIUzI1NiJ9.eyJleHAiOjE2OTIxMTk0NzF9.TQhfkQRVhHM-bBsxs4vBpW4VMBBSHn_d4XLP7NWnLPs
    AccessToken : eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJnZ2cxMkBuYXZlci5jb20iLCJhdXRoIjoiUk9MRV9VU0VSIiwiZXhwIjoxNjkyMDM2NjcxfQ.mJdBUAx9juwTqdinIRr6-mExXmNdkx6wurKJAe_dc8M
}
```
  - 400 Bad Request(이메일 검증)
     - "이메일 형식에 적합하지 않습니다."
  - 400 Bad Request(비밀번호 검증)
     - "비밀번호 형식에 적합하지 않습니다. 8자리 이상 설정해주세요."
### 6.3. 게시물 등록
  #### 6.3.1. Request
  - (POST) /posts/create
  - Headers Authorization : Bearer eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJnZ2cxMkBuYXZlci5jb20iLCJhdXRoIjoiUk9MRV9VU0VSIiwiZXhwIjoxNjkyMDM2NjcxfQ.mJdBUAx9juwTqdinIRr6-mExXmNdkx6wurKJAe_dc8M
```JSON
    {
        "title":"wanted",
        "content":"universal"
    }
```
  #### 6.3.2. Response
  - 201 Created
```JSON
    {
        "id": 1,
        "title": "wanted",
        "content": "universal",
        "email": "ggg12@naver.com"
    }
```
  - 403 Forbidden (유효하지 않은 Access Token)
    
### 6.4. 게시글 목록 조회<br/>
  #### 6.4.1 Request  
  - (GET) /posts?page=0&size=10<br/>
  - Headers Authorization : Bearer eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJnZ2cxMkBuYXZlci5jb20iLCJhdXRoIjoiUk9MRV9VU0VSIiwiZXhwIjoxNjkyMDM2NjcxfQ.mJdBUAx9juwTqdinIRr6-mExXmNdkx6wurKJAe_dc8M<br/>
  
  #### 6.4.2 Response<br/>
  - 200 OK<br/>
```JSON
    {
        "content": [
            {
                "id": 1,
                "title": "wanted",
                "content": "universal",
                "email": "ggg12@naver.com"
            },
            {
                "id": 2,
                "title": "",
                "content": "universal",
                "email": "ggg12@naver.com"
            },
            {
                "id": 3,
                "title": "",
                "content": "",
                "email": "ggg12@naver.com"
            }
        ],
        "pageable": {
            "sort": {
                "empty": true,
                "sorted": false,
                "unsorted": true
            },
            "offset": 0,
            "pageSize": 10,
            "pageNumber": 0,
            "paged": true,
            "unpaged": false
        },
        "totalPages": 1,
        "totalElements": 3,
        "last": true,
        "size": 10,
        "number": 0,
        "sort": {
            "empty": true,
            "sorted": false,
            "unsorted": true
        },
        "numberOfElements": 3,
        "first": true,
        "empty": false
    } 
```
  - 403 Forbidden (유효하지 않은 Access Token)<br/>
### 6.5. 특정 게시글 조회<br/>
  #### 6.5.1. Request<br/>
  - (GET) /posts/1<br/>
  - Headers Authorization : Bearer eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJnZ2cxMkBuYXZlci5jb20iLCJhdXRoIjoiUk9MRV9VU0VSIiwiZXhwIjoxNjkyMDM2NjcxfQ.mJdBUAx9juwTqdinIRr6-mExXmNdkx6wurKJAe_dc8M<br/>
  #### 6.5.2 Response<br/>
  - 200 OK<br/>
```JSON
    {
      "id": 1,
      "title": "wanted",
      "content": "universal",
      "email": "ggg12@naver.com"
    }
```
  - 404 NOT FOUND (존재하지 않는 게시글)<br/>
  - 403 Forbidden (유효하지 않은 Access Token)<br/>
### 6.6. 게시글 수정<br/>
  #### 6.6.1. Request<br/>
  - (PUT) /posts/1<br/>
  - Headers Authorization : Bearer eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJnZ2cxMkBuYXZlci5jb20iLCJhdXRoIjoiUk9MRV9VU0VSIiwiZXhwIjoxNjkyMDM2NjcxfQ.mJdBUAx9juwTqdinIRr6-mExXmNdkx6wurKJAe_dc8M<br/>
```JSON
    {
      "title": "타이틀 수정",
      "content": "내용 수정"
    }
```
  #### 6.6.2 Response<br/>
  - 200 OK<br/>
```JSON
    {
      "id": 1,
      "title": "타이틀 수정",
      "content": "내용 수정",
      "email": "ggg12@naver.com"
    }
```
  - 404 NOT Found (존재하지 않는 게시글)<br/>
    - "게시글을 찾을 수 없습니다."<br/>
  - 403 Forbidden (유효하지 않은 Access Token)<br/>
  - 403 Forbidden (작성자와 로그인한 사용자가 다른 경우)<br/>
    - "작성자만 삭제할 수 있습니다."<br/>
### 6.7. 게시글 삭제<br/>
  #### 6.7.1. Request<br/>
  - (DELETE) /posts/1<br/>
  - Headers Authorization : Bearer eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJnZ2cxMkBuYXZlci5jb20iLCJhdXRoIjoiUk9MRV9VU0VSIiwiZXhwIjoxNjkyMDM2NjcxfQ.mJdBUAx9juwTqdinIRr6-mExXmNdkx6wurKJAe_dc8M<br/>
  #### 6.7.2 Response<br/>
  - 200 OK<br/>
    - "게시글 삭제 성공"<br/>
  - 404 NOT FOUND (존재하지 않는 게시글)
    - "게시글을 찾을 수 없습니다."
  - 403 Forbidden (유효하지 않은 Access Token)<br/>
  - 403 Forbidden (작성자와 로그인한 사용자가 다른 경우)<br/>
    - "작성자만 삭제할 수 있습니다."

