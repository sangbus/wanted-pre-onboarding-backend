# wanted-pre-onboarding-backend

지원자의 성명
- 이름 : 박상환

애플리케이션의 실행 방법 (엔드포인트 호출 방법 포함)
- 회원가입 : (POST)/signup
- 로그인 : (POST)/members/login
- 게시글 작성 : (POST)/posts/create
- 게시글 목록 조회 : (GET)/posts?page=0&size=10
  - page : 0페이지부터 시작
  - size : 한 페이지에 보여줄 수 있는 게시글 수, Default = 10, Max = 50
- 특정 게시글 조회 : (GET)/posts/1
- 게시글 수정 : (PUT)/posts/1
- 게시글 삭제 : (DELETE)/posts/1

데이터베이스 테이블 구조
- 테이블 : 3개(member, member_roles, post)
- 연관 관계
  - member-member_roles, 1대1 관계
  - member-post, 1대다 관계
  ![DB구조](https://github.com/sangbus/wanted-pre-onboarding-backend/assets/87519025/6c6d8d20-2a9e-4e8c-800d-03b720289c9c)


구현한 API의 동작을 촬영한 데모 영상 링크

구현 방법 및 이유에 대한 간략한 설명

API 명세(request/response 포함)

