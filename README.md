# wanted-pre-onboarding-backend
# 성명 
허예림
# AWS 환경
# 애플리케이션의 실행 방법
# 데이터 베이스 테이블 구조
![image](https://github.com/yl0937/wanted-pre-onboarding-backend/assets/60501504/ab7a23b1-12ef-4bae-a2c2-a6cd0713c28a)

# 구현 api 동작 동영상
https://youtu.be/1wl6pp7OVf0
# 구현 방법 및 이유에 대한 간력한 설명
- user
  - 회원가입 : @Email 어노테이션을 사용해 이메일 형식을 검증하고, @Notblank를 사용해 필수 입력 하도록 구성, 비밀번호도 마찬가지로 @Notblank와 @Size(min = 8) 어노테이션을 사용하여 비밀번호를 8자리 이상으로 필수 입력하도록 구성, SignUpRequest dto를 생성하여 회원 가입 진행시 @valid를 통해 유효성 검사, 중복 이메일 검증
  - 로그인 : 회원 가입과 마찬가지로 이메일, 패스워드를 필수로 입력하고 8자리 이상으로 구성하도록 유효성 검사 진행, 사용하여 SecurityConfig 에 PasswordEncorder 를 Bean등록 후 암호화 후 저장.
- board
  - 게시글 생성
  - 게시글 수정
  - 게시글 삭제
  - 게시글 전체 조회
  - 게시글 개별 조회
# API 명세
- 회원 가입
  - method : post
  - url : /users/auth/signup
  - request-body : {"email":"이메일","password":"비밀번호"}
  - response
    - 입력값 유효성 검사 실패
    
      {"ok" : "false", "code": 40001,"message":"입력값 유효성 검사에 실패하였습니다.", "detail": "password: 크기가 8에서 2147483647 사이여야 합니다, email: 공백일 수 없습니다"}
    - 이미 가입된 이메일
    
       {
    "ok": false,
    "code": 40002,
    "message": "이미 가입된 이메일 입니다.",
    "detail": ""
}
    - 성공
      
      {
    "ok": true,
    "data": null
}
- 로그인
  - method : post
  - url : /users/auth/signin
  - request-body : {"email":"이메일","password":"비밀번호"}
  - response
    - 입력값 유효성 검사 실패
    
      {"ok" : "false", "code": 40001,"message":"입력값 유효성 검사에 실패하였습니다.", "detail": "password: 크기가 8에서 2147483647 사이여야 합니다, email: 공백일 수 없습니다"}
    - 없는 사용자
      {
    "ok": false,
    "code": 40004,
    "message": "사용자를 찾을 수 없습니다.",
    "detail": ""
}
    - 틀린 비밀번호
      {
    "ok": false,
    "code": 40003,
    "message": "비밀번호가 일치하지 않습니다.",
    "detail": ""
}
    - 성공
      {
    "ok": true,
    "data": {
        "accessToken": 액세스 토큰 ,
        "accessTokenExpirationDateTime": 액세스 토큰 만료일
    }
}
   
- 게시글 생성
  - method : post
  - url : /boards
  - request-header : Authorization: Bearer 액세스토큰
  - request-body : {"title":"제목","content":"내용","image_url":"이미지 주소"}
  - response
    - 액세스 토큰 누락
      {
    "ok": false,
    "code": 40101,
    "message": "인증 정보(액세스 토큰)가 누락되었습니다."
}
    - 액세스 토큰 만료
            {
    "ok": false,
    "code": 40102,
    "message": "만료된 액세스 토큰입니다."
}
    - 입력값 유효성 검사 실패
      {
    "ok": false,
    "code": 40001,
    "message": "입력값 유효성 검사에 실패하였습니다.",
    "detail": "title: 공백일 수 없습니다, content: 공백일 수 없습니다"
} 
    - 성공
      {
    "ok": true,
    "data": {
        "id": 6,
        "userId": 12,
        "userName": "test1234@gmail.com",
        "title": "이것은 수정된 제목입니다.",
        "content": "글의 내용입니다.",
        "imageUrl": null,
        "createdAt": "2023-08-11T18:21:58.6810515",
        "updatedAt": "2023-08-11T18:21:58.6810515"
    }
}
- 게시글 수정
  - method : patch
  - url : /boards/{boardId}
  - request-header : Authorization: Bearer 액세스토큰
  - request-body : {"title":"제목","content":"내용","image_url":"이미지 주소"}
  - response
    - 액세스 토큰 누락
      {
    "ok": false,
    "code": 40101,
    "message": "인증 정보(액세스 토큰)가 누락되었습니다."
}
    - 액세스 토큰 만료
            {
    "ok": false,
    "code": 40102,
    "message": "만료된 액세스 토큰입니다."
}
    - 권한 없음
      {
    "ok": false,
    "code": 40300,
    "message": "해당 요청에 대한 권한이 없습니다.",
    "detail": "게시판 작성자가 아닙니다."
} 

    - 없는 게시글
      {
    "ok": false,
    "code": 40400,
    "message": "해당하는 게시글을 찾을 수 없습니다.",
    "detail": ""
} 
    - 성공
      {
    "ok": true,
    "data": {
        "id": 6,
        "userId": 12,
        "userName": "test1234@gmail.com",
        "title": "이것은 수정된 제목입니다.",
        "content": "글의 내용입니다.",
        "imageUrl": null,
        "createdAt": "2023-08-11T18:21:58.6810515",
        "updatedAt": "2023-08-11T18:21:58.6810515"
    }
}
- 게시글 삭제
  - method : delete
  - url : /boards
  - request-header : Authorization: Bearer 액세스토큰
  - response
    - 액세스 토큰 누락
      {
    "ok": false,
    "code": 40101,
    "message": "인증 정보(액세스 토큰)가 누락되었습니다."
}
    - 액세스 토큰 만료
            {
    "ok": false,
    "code": 40102,
    "message": "만료된 액세스 토큰입니다."
}
    - 권한 없음
      {
    "ok": false,
    "code": 40300,
    "message": "해당 요청에 대한 권한이 없습니다.",
    "detail": "게시판 작성자가 아닙니다."
} 

    - 없는 게시글
      {
    "ok": false,
    "code": 40400,
    "message": "해당하는 게시글을 찾을 수 없습니다.",
    "detail": ""
} 
    - 성공
      {
    "ok": true,
    "data": {
        "id": 6,
        "userId": 12,
        "userName": "test1234@gmail.com",
        "title": "이것은 수정된 제목입니다.",
        "content": "글의 내용입니다.",
        "imageUrl": null,
        "createdAt": "2023-08-11T18:21:58.6810515",
        "updatedAt": "2023-08-11T18:21:58.6810515"
    }
}
- 게시글 전체 조회
  - method : get
  - url : /boards/read?pageNum={pageNum}
  - response
    - 성공
{
    "ok": true,
    "data": {
        "content": [
            {
                "id": 1,
                "userId": 4,
                "userName": "efdafA@dfsd",
                "title": "sfgjhgfjd@gsfsf",
                "content": "가나다",
                "imageUrl": null,
                "createdAt": "2023-08-10T13:37:51",
                "updatedAt": "2023-08-10T13:37:51"
            },
            {
                "id": 6,
                "userId": 12,
                "userName": "test1234@gmail.com",
                "title": "이것은 수정된 제목입니다.",
                "content": "글의 내용입니다.",
                "imageUrl": null,
                "createdAt": "2023-08-11T18:21:59",
                "updatedAt": "2023-08-11T18:21:59"
            }
        ],
        "pageable": "INSTANCE",
        "last": true,
        "totalElements": 4,
        "totalPages": 1,
        "size": 4,
        "number": 0,
        "sort": {
            "empty": true,
            "sorted": false,
            "unsorted": true
        },
        "first": true,
        "numberOfElements": 4,
        "empty": false
    }
}
- 게시글 개별 조회
  - method : get
  - url : /boards/read/{boardId}
  - response
      - 없는 게시글
      {
    "ok": false,
    "code": 40400,
    "message": "해당하는 게시글을 찾을 수 없습니다.",
    "detail": ""
} 
    - 성공
      {
    "ok": true,
    "data": {
        "id": 6,
        "userId": 12,
        "userName": "test1234@gmail.com",
        "title": "이것은 수정된 제목입니다.",
        "content": "글의 내용입니다.",
        "imageUrl": null,
        "createdAt": "2023-08-11T18:21:58.6810515",
        "updatedAt": "2023-08-11T18:21:58.6810515"
    }
}


