# 검색 API
---
## 인기 키워드 조회(/v1/search/top-n-keywords)
### 개요
- 조회 시, 인증(Authorization)이 필요합니다.
- 가장 많이 검색한 TOP 10의 키워드를 조회 할 수 있습니다.


### 요청 경로
```json
HTTP/1.1 GET /v1/search/top-n-keywords
```
### 요청 헤더
| 키  | 벨류  |
|---|---|
| Content-Type  | application/json  |
| Authorization  | Bearer {회원의 JWT 토큰}  |

### 요청 쿼리스트링
| 키  | 타입  | 필수여부  | 기본값 | 설명 |
|---|---|---|---|---|
| limit  | Number  |   | 10 | N에 해당하는 키워드의 개수를 조절할 수 있습니다.(1 ~ 10개) |

### 요청 경로 예시
```json
HTTP/1.1 GET /v1/search/top-n-keywords?limit=10
```

### 응답
#### 성공
```json
HTTP/1.1 200 OK
{
    "updatedAt": "2019-07-24T01:44:29.265",
    "list": [
        {
            "keyword": "A Time to Kill",
            "count": 12
        },
	  ...
    ]
}
```
| 키  | 타입 | 설명 |
|---|---|---|
| updatedAt  | String(ISO Date String)  | 조회 갱신 일자  |
| list  | Object[]  | 키워드 목록 배열  |
| list[].keyword  | String  | 검색 키워드  |
| list[].count  | Number  | 키워드 조회 카운트  |

#### 실패
##### 최소 조회 가능 키워드 수(`limit`)는 1입니다.
```json
HTTP/1.1 400 Bad Request

{
    "message": " Invalid Input Value",
    "status": 400,
    "errors": [
        {
            "field": "limit",
            "value": "0",
            "reason": "최소 조회 가능 키워드 수(`limit`)는 1입니다."
        }
    ],
    "code": "INVALID_INPUT_VALUE"
}
```
##### 최대 조회 가능 키워드 수(`limit`)는 10입니다.
```json
HTTP/1.1 400 Bad Request

{
    "message": " Invalid Input Value",
    "status": 400,
    "errors": [
        {
            "field": "limit",
            "value": "11",
            "reason": "최대 조회 가능 키워드 수(`limit`)는 10입니다."
        }
    ],
    "code": "INVALID_INPUT_VALUE"
}
```




---
## 회원 검색 키워드 조회(/v1/search/user-search-keywords)
### 개요
- 조회 시, 인증(Authorization)이 필요합니다.
- 회원이 검색한 키워드 목록을 조회할 수 있습니다.

### 요청 경로
```
HTTP/1.1 GET /v1/search/user-search-keywords
```
### 요청 헤더
| 키  | 벨류  |
|---|---|
| Content-Type  | application/json  |
| Authorization  | Bearer {회원의 JWT 토큰}  |

### 요청 쿼리스트링
| 키  | 타입  | 필수여부  | 설명 |
|---|---|---|---|
| category  | String  | O  | 검색 카테고리(BOOK, USER) |
| keyword  | String  | O  | 검색 키워드 |

### 요청 경로 예시
```json
HTTP/1.1 GET /v1/search/user-search-keywords?category=BOOK&keyword=Paths of Glory
```

### 응답
#### 성공
```json
HTTP/1.1 200 OK
{
    "content": [
        {
            "id": 464,
            "category": "BOOK",
            "keyword": "Paths of Glory",
            "searchedAt": "2019-07-24T01:19:42.834"
        },
        {
            "id": 292,
            "category": "BOOK",
            "keyword": "Paths of Glory",
            "searchedAt": "2019-07-24T01:19:42.774"
        },
        {
            "id": 246,
            "category": "BOOK",
            "keyword": "Paths of Glory",
            "searchedAt": "2019-07-24T01:19:42.755"
        },
        {
            "id": 72,
            "category": "BOOK",
            "keyword": "Paths of Glory",
            "searchedAt": "2019-07-24T01:19:42.673"
        },
        {
            "id": 24,
            "category": "BOOK",
            "keyword": "Paths of Glory",
            "searchedAt": "2019-07-24T01:19:42.641"
        }
    ],
    "pageable": {
        "sort": {
            "sorted": true,
            "unsorted": false,
            "empty": false
        },
        "offset": 0,
        "pageSize": 10,
        "pageNumber": 0,
        "unpaged": false,
        "paged": true
    },
    "totalPages": 1,
    "totalElements": 5,
    "last": true,
    "size": 10,
    "number": 0,
    "first": true,
    "sort": {
        "sorted": true,
        "unsorted": false,
        "empty": false
    },
    "numberOfElements": 5,
    "empty": false
}
```