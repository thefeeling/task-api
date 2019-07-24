# 도서 API
---
## 도서 목록 조회(/v1/search/top-n-keywords)
### 개요
- 조회 시, 인증(Authorization)이 필요합니다.
- 도서 목록을 조회 할 수 있습니다.

### 요청 경로
```
HTTP/1.1 GET /v1/books
```
### 요청 헤더
| 키  | 벨류  |
|---|---|
| Content-Type  | application/json  |
| Authorization  | Bearer {회원의 JWT 토큰}  |

### 요청 쿼리스트링
| 키  | 타입  | 필수여부  | 기본값  | 설명 |
|---|---|---|---|---|
| query  | String  | O  | O  | 가입하고자 하는 이메일 주소 |
| page  | Number  |   | O  | 페이지(첫 페이지가 0부터 시작합니다.) |
| size  | Number  |   | 10  | 페이징 사이즈 |

### 요청 경로 예시
```json
HTTP/1.1 GET /v1/books?query=당신&page=3&size=10
```

### 응답
#### 성공
```json
HTTP/1.1 200 OK
{
    "content": [
        {
            "isbn": "1195446578 9791195446575",
            "title": "당신에겐 집이 필요하다",
            "price": 16000,
            "salePrice": 14400,
            "description": "부동산은 시간과의 싸움이다. 상승 타이밍에 맞춰 집을 사려면 당신은 정말 뛰어난 투자자여야만 한다. 그럴 필요 없다. 내 집을 조금 일찍 마련해서 지키고만 있어도 여러 번의 기회를 만날 수 있다. 이런 연유로 부동산 투자자들의 내 집 마련은 일반인들보다 빠르다. 내 집 마련이 재테크의 시작이라는 것을 알기 때문이다.  전세보증금 정도의 목돈이 마련됐다면 무엇보다 내 집 마련을 고민하길 바란다. 전세금에 조금만 더 보태면 내 집을 마련할 수 있다. 거주",
            "publishedAt": "2017-01-16T00:00:00.000+09:00",
            "publisher": "베리북",
            "author": [
                "렘군"
            ],
            "link": "https://search.daum.net/search?w=bookpage&bookId=1655172&q=%EB%8B%B9%EC%8B%A0%EC%97%90%EA%B2%90+%EC%A7%91%EC%9D%B4+%ED%95%84%EC%9A%94%ED%95%98%EB%8B%A4",
            "image": "https://search1.kakaocdn.net/thumb/R120x174.q85/?fname=http%3A%2F%2Ft1.daumcdn.net%2Flbook%2Fimage%2F1655172%3Ftimestamp%3D20190131135031",
            "provider": "KAKAO"
        },
        {
            "isbn": "8994857214 9788994857213",
            "title": "이젠 책쓰기다",
            "price": 13000,
            "salePrice": -1,
            "description": "'책쓰기'가 인생의 돌파구가 된다고 믿는 '책쓰기 꿈쟁이' 조영석의 『이젠 책쓰기다』. '당신이 쓴 책 한 권, 인생을 바꾼다.'는 슬로건으로 '성공 책쓰기 코칭 센터'를 만들어서 '쉽게 책을 쓰고 출판까지 진행하는 방법'을 널리 전해온 저자가, 인생의 돌파구가 필요한 우리를 위해 저술한 것이다. 책쓰기를 통해 인생을 바꾸는 방법을 알려준다. 책쓰기의 처음부터 끝까지 알기 쉽게 설명하고 있다. 책쓰기를 결심하고 실행하기까지 인도한다.",
            "publishedAt": "2011-11-11T00:00:00.000+09:00",
            "publisher": "라온북",
            "author": [
                "조영석"
            ],
            "link": "https://search.daum.net/search?w=bookpage&bookId=1424040&q=%EC%9D%B4%EC%A0%A0+%EC%B1%85%EC%93%B0%EA%B8%B0%EB%8B%A4",
            "image": "https://search1.kakaocdn.net/thumb/R120x174.q85/?fname=http%3A%2F%2Ft1.daumcdn.net%2Flbook%2Fimage%2F1424040%3Ftimestamp%3D20190129234620",
            "provider": "KAKAO"
        },
        {
            "isbn": "8996047651 9788996047650",
            "title": "당신이 희망입니다(반양장)",
            "price": 13500,
            "salePrice": 12150,
            "description": "",
            "publishedAt": "2008-11-30T00:00:00.000+09:00",
            "publisher": "오픈하우스",
            "author": [
                "고도원"
            ],
            "link": "https://search.daum.net/search?w=bookpage&bookId=1465284&q=%EB%8B%B9%EC%8B%A0%EC%9D%B4+%ED%9D%AC%EB%A7%9D%EC%9E%85%EB%8B%88%EB%8B%A4%28%EB%B0%98%EC%96%91%EC%9E%A5%29",
            "image": "https://search1.kakaocdn.net/thumb/R120x174.q85/?fname=http%3A%2F%2Ft1.daumcdn.net%2Flbook%2Fimage%2F1465284%3Ftimestamp%3D20190130052423",
            "provider": "KAKAO"
        },
        {
            "isbn": "8954606903 9788954606905",
            "title": "당신의 조각들",
            "price": 12000,
            "salePrice": 10800,
            "description": "",
            "publishedAt": "2008-11-07T00:00:00.000+09:00",
            "publisher": "달",
            "author": [
                "타블로"
            ],
            "link": "https://search.daum.net/search?w=bookpage&bookId=689966&q=%EB%8B%B9%EC%8B%A0%EC%9D%98+%EC%A1%B0%EA%B0%81%EB%93%A4",
            "image": "https://search1.kakaocdn.net/thumb/R120x174.q85/?fname=http%3A%2F%2Ft1.daumcdn.net%2Flbook%2Fimage%2F689966%3Ftimestamp%3D20190124095143",
            "provider": "KAKAO"
        },
        {
            "isbn": "8993461198 9788993461190",
            "title": "맛있는 커피 레시피(당신의 카페를 빛낼)",
            "price": 20000,
            "salePrice": 18000,
            "description": "당신의 카페를 빛낼『맛있는 커피 레시피』. 이 책은 93개의 카페음료 레시피를 한권으로 엮은 책이다. 한국 커피산업진흥연구원의 노하우를 바탕으로 최신 카페 트렌드를 반영하여 카페에 꼭 필요한 메뉴들을 엄선하고, 수차례의 테스트를 거쳐 믿을 수 있는 레시피로 완성도를 보완하였다. 기본메뉴부터 주스, 에이드, 프라페, 티에이르기까지 카페음료에 관한 A부터 Z까지 모든 것을 만나볼 수 있다.",
            "publishedAt": "2018-05-23T00:00:00.000+09:00",
            "publisher": "아이비라인",
            "author": [
                "한국커피산업진흥연구원"
            ],
            "link": "https://search.daum.net/search?w=bookpage&bookId=1407967&q=%EB%A7%9B%EC%9E%88%EB%8A%94+%EC%BB%A4%ED%94%BC+%EB%A0%88%EC%8B%9C%ED%94%BC%28%EB%8B%B9%EC%8B%A0%EC%9D%98+%EC%B9%B4%ED%8E%98%EB%A5%BC+%EB%B9%9B%EB%82%BC%29",
            "image": "https://search1.kakaocdn.net/thumb/R120x174.q85/?fname=http%3A%2F%2Ft1.daumcdn.net%2Flbook%2Fimage%2F1407967%3Ftimestamp%3D20190129210826",
            "provider": "KAKAO"
        },
        {
            "isbn": "118543979X 9791185439792",
            "title": "월급으로 당신의 부동산을 가져라",
            "price": 15000,
            "salePrice": 13500,
            "description": "『월급으로 당신의 부동산을 가져라』에서 저자는 종잣돈이라는 거대한 벽에 부딪혀 투자할 엄두도 못 내고 있거나 몇 년째 재테크 강의만 들으며 종잣돈을 모으고 있는 사람들에게 ‘소액으로 곳곳에 씨 뿌리기’라고 하는 저자만의 소액투자 방법을 소개한다. 어떻게 소액으로 매월 투자할 수 있을까? 저축 대신 경매를 이용해 토지에 투자하는 것이다.  저자는 소액 토지를 매달 낙찰 받으면 빠르면 3개월 늦어도 18개월 안에 투자 원금과 수익금이 회수된다고 이야기하며",
            "publishedAt": "2017-07-01T00:00:00.000+09:00",
            "publisher": "다온북스",
            "author": [
                "시루"
            ],
            "link": "https://search.daum.net/search?w=bookpage&bookId=1618744&q=%EC%9B%94%EA%B8%89%EC%9C%BC%EB%A1%9C+%EB%8B%B9%EC%8B%A0%EC%9D%98+%EB%B6%80%EB%8F%99%EC%82%B0%EC%9D%84+%EA%B0%80%EC%A0%B8%EB%9D%BC",
            "image": "https://search1.kakaocdn.net/thumb/R120x174.q85/?fname=http%3A%2F%2Ft1.daumcdn.net%2Flbook%2Fimage%2F1618744%3Ftimestamp%3D20190131074537",
            "provider": "KAKAO"
        },
        {
            "isbn": "8965960517 9788965960515",
            "title": "당신은 이미 읽혔다",
            "price": 14000,
            "salePrice": 12600,
            "description": "상대의 속마음을 간파하는 기술『당신은 이미 읽혔다』. 이 책은 세계적인 인간 행동 전문가 앨런 피즈와 바바라 피즈의 30년 연구를 집대성한 몸짓 언어 바이블로, 심리학, 생물학, 뇌과학, 역사 등 다양한 분야의 연구를 바탕으로 한 몸짓 언어의 모든 것을 담은 책이다. 실제로 어떻게 몸짓을 활용하여 원하는 결과를 얻을 수 있는지 풍부한 이미지와 사진자료를 통해 쉽게 풀어냈다.  존 F. 케네디, 마릴린 먼로, 아돌프 히틀러 등 다양한 사례를 통해 실제",
            "publishedAt": "2012-11-30T00:00:00.000+09:00",
            "publisher": "흐름출판",
            "author": [
                "앨런 피즈",
                "바바라 피즈"
            ],
            "link": "https://search.daum.net/search?w=bookpage&bookId=920978&q=%EB%8B%B9%EC%8B%A0%EC%9D%80+%EC%9D%B4%EB%AF%B8+%EC%9D%BD%ED%98%94%EB%8B%A4",
            "image": "https://search1.kakaocdn.net/thumb/R120x174.q85/?fname=http%3A%2F%2Ft1.daumcdn.net%2Flbook%2Fimage%2F920978%3Ftimestamp%3D20190126000416",
            "provider": "KAKAO"
        },
        {
            "isbn": "1157760457 9791157760459",
            "title": "당신",
            "price": 10000,
            "salePrice": 9000,
            "description": "고물상 시인의 첫 번째 시집 『당신』. 사랑에 빠지는 순간부터 이별을 겪은 그 후의 이야기까지 작가의 손 글씨로 한자 한자 써 내려간 100편의 사랑 시가 담겨 있다.",
            "publishedAt": "2015-06-01T00:00:00.000+09:00",
            "publisher": "책과나무",
            "author": [
                "고물상"
            ],
            "link": "https://search.daum.net/search?w=bookpage&bookId=1578517&q=%EB%8B%B9%EC%8B%A0",
            "image": "https://search1.kakaocdn.net/thumb/R120x174.q85/?fname=http%3A%2F%2Ft1.daumcdn.net%2Flbook%2Fimage%2F1578517%3Ftimestamp%3D20190131013150",
            "provider": "KAKAO"
        },
        {
            "isbn": "8946418435 9788946418431",
            "title": "당신에게",
            "price": 13000,
            "salePrice": 11700,
            "description": "모리사와 아키오의 장편소설 『당신에게』. 사별한 아내가 띄운 마지막 편지, 그 유서가 보관된 아내의 고향 우체국으로 향하는 남편의 여행을 그린 작품이다. 삶과 사랑, 죽음과 이별이라는 소재를 저자 특유의 현실적이면서도 따뜻한 시선으로 펼쳐 보인다. 자신이나 타인의 죽음을 어떻게 받아들여야 하는지를 이야기하며 우리네 인생을, 삶과 죽음을, 좀 더 새로운 시각으로 바라볼 수 있는 기회를 전한다.  교도소에서 직업훈련 교사로 일하는 구라시마 에지. 아내의",
            "publishedAt": "2013-06-30T00:00:00.000+09:00",
            "publisher": "샘터(샘터사)",
            "author": [
                "모리사와 아키오"
            ],
            "link": "https://search.daum.net/search?w=bookpage&bookId=605475&q=%EB%8B%B9%EC%8B%A0%EC%97%90%EA%B2%8C",
            "image": "https://search1.kakaocdn.net/thumb/R120x174.q85/?fname=http%3A%2F%2Ft1.daumcdn.net%2Flbook%2Fimage%2F605475%3Ftimestamp%3D20190124002650",
            "provider": "KAKAO"
        },
        {
            "isbn": "8960974749 9788960974746",
            "title": "당신은 하나님을 오해하고 있습니다",
            "price": 14000,
            "salePrice": 12600,
            "description": "졸업을 한 학기 앞두고 귀국하여 인턴 전도사로 일을 시작한 그 주에 직장암 말기 판정을 받았지만 그녀는 그날 수술 동의서에 사인하지 않았다. 하나님께 지혜를 구하고 기도할 시간을 가진 후, 의사들과 주위 사람들의 압박에도 불구하고 수술, 항암, 방사선으로 이어지는 치료 대신 한 명의 영혼에게라도 더 복음을 전하는 쪽을 택했다. 이 책은 죽음도 두려워하지 않았던 진정한 복음의 증인, 故 유석경 전도사가 전하는 하나님의 본심에 대해 말한다.",
            "publishedAt": "2016-10-31T00:00:00.000+09:00",
            "publisher": "규장",
            "author": [
                "유석경"
            ],
            "link": "https://search.daum.net/search?w=bookpage&bookId=842566&q=%EB%8B%B9%EC%8B%A0%EC%9D%80+%ED%95%98%EB%82%98%EB%8B%98%EC%9D%84+%EC%98%A4%ED%95%B4%ED%95%98%EA%B3%A0+%EC%9E%88%EC%8A%B5%EB%8B%88%EB%8B%A4",
            "image": "https://search1.kakaocdn.net/thumb/R120x174.q85/?fname=http%3A%2F%2Ft1.daumcdn.net%2Flbook%2Fimage%2F842566%3Ftimestamp%3D20190125093507",
            "provider": "KAKAO"
        }
    ],
    "pageable": {
        "sort": {
            "sorted": false,
            "unsorted": true,
            "empty": true
        },
        "offset": 30,
        "pageSize": 10,
        "pageNumber": 3,
        "paged": true,
        "unpaged": false
    },
    "last": false,
    "totalPages": 859,
    "totalElements": 8588,
    "size": 10,
    "number": 3,
    "sort": {
        "sorted": false,
        "unsorted": true,
        "empty": true
    },
    "numberOfElements": 10,
    "first": false,
    "empty": false
}

```
#### 실패
##### 검색 키워드(`query`)가 없을 경우
```json
{
    "message": " Invalid Input Value",
    "status": 400,
    "errors": [
        {
            "field": "query",
            "value": "",
            "reason": "검색어(`query`)는 필수 값이며 빈 문자열을 허용하지 않습니다."
        }
    ],
    "code": "INVALID_INPUT_VALUE"
}
```