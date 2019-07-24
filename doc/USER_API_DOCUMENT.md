# 유저 API
---
## 회원가입(/v1/users/join)
### 개요
- 회원가입을 할 수 있습니다.
- 계정의 ID의 경우, 이메일을 사용합니다.

### 요청 경로
```
HTTP/1.1 POST /v1/users/join
```
### 요청 헤더
| 키  | 벨류  |
|---|---|
| Content-Type  | application/json  |

### 요청 본문
| 키  | 타입  | 필수여부  | 설명 |
|---|---|---|---|
| email  | String  | O  | 가입하고자 하는 이메일 주소 |
| password  | String  | O  | 회원 Password |

### 요청 본문 예시
```json
{
	"email": "dev@dev.io",
	"password": "devio"
}	
```

### 응답
#### 성공
```
HTTP/1.1 204 NO CONTENT /v1/users/join
```
#### 실패
##### 이메일이 없을 경우
```json
{
	"message": " Invalid Input Value",
	"status": 400,
	"errors": [
		{
			"field": "email",
			"value": "",
			"reason": "이메일(`email`)은 빈 값을 허용하지 않으며 필수입니다."
		}
	],
	"code": "INVALID_INPUT_VALUE"
}
```
##### 이메일의 형식이 올바르지 않은 경우
```json
{
    "message": " Invalid Input Value",
    "status": 400,
    "errors": [
        {
            "field": "email",
            "value": "kscho111i1",
            "reason": "이메일(`email`) 형식이 올바르지 않습니다."
        }
    ],
    "code": "INVALID_INPUT_VALUE"
}
```
##### 패스워드가 없는 경우
```json
{
	"email": "kscho111i1@dev.io",
	"password": ""
}
	
```


---
## 로그인(/v1/users/login)
### 개요
- 가입한 계정으로 로그인을 할 수 있습니다.
- 로그인의 응답 값은 JWT Token입니다.

### 요청 경로
```
HTTP/1.1 POST /v1/users/login
```
### 요청 헤더
| 키  | 벨류  |
|---|---|
| Content-Type  | application/json  |

### 요청 본문
| 키  | 타입  | 필수여부  | 설명 |
|---|---|---|---|
| email  | String  | O  | 이메일 주소 |
| password  | String  | O  | 회원 Password |

### 요청 본문 예시
```json
{
	"email": "dev@dev.io",
	"password": "devio"
}	
```

### 응답
#### 성공
```json
HTTP/1.1 200 OK
{
    "accessToken": "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJpc3MiOiJrc2Nob2kiLCJpZCI6MSwiYXV0aFR5cGUiOiJMT0NBTCIsImV4cCI6MTU2Mzk4NjI3MSwiaWF0IjoxNTYzODk5ODcxfQ.8EfnkJq6dmwzSPyEwKpK3MJyq1_9NgkkYVqf4gKGLrA",
    "expiredAt": "2019-07-25T01:37:51.973"
}
```
#### 실패
##### 이메일이 없을 경우
```json
{
	"message": " Invalid Input Value",
	"status": 400,
	"errors": [
		{
			"field": "email",
			"value": "",
			"reason": "이메일(`email`)은 빈 값을 허용하지 않으며 필수입니다."
		}
	],
	"code": "INVALID_INPUT_VALUE"
}
```
##### 이메일의 형식이 올바르지 않은 경우
```json
{
    "message": " Invalid Input Value",
    "status": 400,
    "errors": [
        {
            "field": "email",
            "value": "kscho111i1",
            "reason": "이메일(`email`) 형식이 올바르지 않습니다."
        }
    ],
    "code": "INVALID_INPUT_VALUE"
}
```
##### 패스워드가 없는 경우
```json
{
	"email": "kscho111i1@dev.io",
	"password": ""
}
	
```

---
## 프로필 조회(/v1/users/me)
### 개요
- **로그인**을 통하여 발급받은 토큰을 사용하여 회원의 프로필을 조회할 수 있습니다.

### 요청 경로
```
HTTP/1.1 GET /v1/users/me
```
### 요청 헤더
| 키  | 필수유무  | 벨류  |
|---|---|---|
| Content-Type  |   | application/json  |
| Authorization  | O | Bearer {회원 인증 토큰} |

### 응답
#### 성공
```json
HTTP/1.1 200 OK
{
    "id": 1,
    "email": "kschoi@dev.io",
    "loginedAt": "2019-07-24T12:55:13.061"
}
```
| 키  | 타입  | 설명  |
|---|---|---|
| id  | Number  | 회원ID  |
| email  | String | 회원 이메일 주소 |
| loginedAt  | String(ISO Date String) | 마지막 로그인 일자 |

#### 실패
##### 만료된 인증 토큰입니다.
```json
HTTP/1.1 400 Bad Request

{
    "message": "Invalid Client Request",
    "status": 400,
    "errors": [
        {
            "field": "EXPIRED_TOKEN",
            "value": "",
            "reason": "만료된 인증 토큰입니다."
        }
    ],
    "code": "BAD_REQUEST_BY_CLIENT"
}
```
##### 유효하지 않은 토큰입니다.
```json
HTTP/1.1 400 Bad Request

{
    "message": "Invalid Client Request",
    "status": 400,
    "errors": [
        {
            "field": "INVALID_TOKEN",
            "value": "",
            "reason": "유효하지 않은 토큰입니다."
        }
    ],
    "code": "BAD_REQUEST_BY_CLIENT"
}
```
##### 토큰 타입은 `Bearer` 입니다.
```json
HTTP/1.1 400 Bad Request

{
    "message": "Invalid Client Request",
    "status": 400,
    "errors": [
        {
            "field": "INVALID_TOKEN_TYPE",
            "value": "",
            "reason": "토큰 타입은 `Bearer` 입니다."
        }
    ],
    "code": "BAD_REQUEST_BY_CLIENT"
}	
```


