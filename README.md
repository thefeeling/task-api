## 개요
- 도서 조회 어플리케이션으로 FRONT-END와 BACK-END를 포함하고 있음
- 로컬 실행 환경 시, 페이지의 경우 index.html에 구현되어 있음.

### 어플리케이션 실행 시 참고사항
- FRONT-END 페이지의 경우, http://localhost:8080 으로 접속하셔서 확인하실 수 있습니다.
- `application.properties`에 외부에 공개되면 안되는 시크릿 값을 포함하고 있습니다.
  ```bash
  ###########################################################
  # 하단의 api 관련 설정 값은
  # 어플리케이션 실행 시 반드시 내용을 기입해주셔야 합니다.
  app.api.kakao.host={K-API-HOST}
  app.api.kakao.apikey={K-API-KEY}

  app.api.naver.host={N-API-HOST}
  app.api.naver.clientId={N-API-CLIENT-ID}
  app.api.naver.clientSecret={N-API-CLIENT-SECRET}
  ###########################################################
  ```
  - 해당 값은 마스킹 처리하여 `github`에 업로드합니다.
  - 테스트 혹은 실행 시, 해당 값을 채워서 실행하시면 됩니다.

- 어플리케이션 실행 시, 원활한 테스트 및 실행을 위하여 페이지에 접속 가능한 계정이 입력되어 있습니다. - 어플리케이션 부트스트랩 시 해당 계정에 조회 할 수 있는 더미 성격의 데이터를 입력합니다.

## FRONT-END
- vue.js를 사용하여 간단한 도서 검색 및 키워드 검색 어플리케이션을 구현
- 인증의 경우 토큰 기반으로 진행, LocalStorage를 사용하여 기 저장되어 있는 토큰이 존재 할 경우 해당 토큰을 사용하여 인증 진행
- 프론트엔드 코드의 경우 `/resources/static`에 배치

### 프레임워크 & 라이브러리
- axios.js
  - 브라우저 XHR 구현체
  - 대부분의 반환값이 `Promise`로 비동기 처리를 원활하게 할 수 있음.
- vue.min.js
  - SPA Framework
- semantic-ui-vue
  - Vue.js UI Component로서 빠른 페이지 개발을 위하여 사용
- moment.js
  - 자바스크립트 Date 객체 핸들링을 원활하게 하기 위해 사용
### 패키지 구조
![스크린샷 2019-07-24 오후 3.42.46](https://i.imgur.com/StnywpA.png)
- `app.js`을 기준으로 어플리케이션 부트스트랩
- `component` 디렉토리에 사용하는 컴포넌트 코드 배치
- `environment.js`를 참조하여 환경변수(API경로) 관리



## BACK-END
### 프레임워크 & 라이브러리
- spring-boot의 경우 최신 버전인 `2.1.6 RELEASE`를 기준을 기준으로 셋팅
- **필수** 항목의 경우, 요구사항에서 언급한 관련 구현체를 포함, 설명 생략
- **추가**에 해당하는 구현체의 경우, 요구사항 구현 중 필요에 의하여 추가

#### 필수
- spring-boot-starter-data-jpa
- spring-boot-starter-web
- com.h2database:h2
- org.junit.jupiter:junit-jupiter-api:5.2.0
#### 추가
- spring-cloud-starter-netflix-hystrix, spring-cloud-starter-openfeign
  - 책 검색 관련 외부 HTTP API 호출에 사용.
  - 외부 API 조회 시, API 클라이언트 구현에 대하여 Spring MVC와 같이 직렬화/역직렬화, HTTP 관련 서포트 등 HTTP 킅라이언트로서의 구현에 충실하여 사용. 
  - Spring이 런타임에 구현체를 제공해주고 있으며 API 호출에서 빈번하게 발생하는 실패에 대한 fallback 및 클라이언트별로 달라질 수 있는 Configuration을 편리하게 설정할 수 있음.

- com.querydsl:querydsl-jpa
  - JPA-HIBERNATE를 사용할 때, JPQL을 사용하여 영속성 컨텍스트에 질의 시 API 레벨에서 다이나믹하게 구현하고자 사용
- com.auth0:java-jwt
  - 인증 구현 시, JWT 토큰 사용
- spring-boot-starter-cache, com.github.ben-manes.caffeine:caffeine
  - 검색 도메인에서 사용하고 있으며, 전체 키워드에 대한 통계 조회 응용 메소드에 적용. 불필요한 SQL 집계가 발생하지 않도록 하기 위하여 추가

- org.projectlombok:lombok
  - 프로젝트 전체에 걸쳐 사용.
  - 반복적으로 발생하는 Getter/Setter/ToString/hashCode/equals 그리고 빌더에 대한 구현을 어노테이션 프로세서를 활용하여 편리하게 표현하고자 사용.
----
### 아키텍쳐 & 패키지 구조
#### 개요 & 흐름
- 프로젝트 구조는 패키지 단위로 도메인을 표현하고자 하였으며, 모든 도메인에서 공유하는 코드/구현 내용은 `global` 패키지에 코드 배치
- presentation 레이어가 포함되어 있는 `user`, `book`, `search`의 경우 아래 같은 흐름으로 코드를 참조함.
![개요흐름_1](https://i.imgur.com/Yr1I8U2.png)
- 도메인간 발생하는 데이터 처리의 경우 `Event`를 사용하여 처리한다. 
@TransactionEventListener 혹은 @EventListener를 사용했으며 요청 스레드가 Block 되지 않도록 @Async 어노테이션을 활용하여 별도의 스레드풀에서 이벤트 Job을 처리하도록 했음. 
아래는 `도서 도메인`에서 검색 시 발생하는 조회 키워드를 `검색 도메인`에 전달하는 흐름이다.
![개요흐름_2](https://i.imgur.com/1c6kGdf.png)

#### 패키지 구조
```
└── src
    ├── main
    │   ├── java
    │   │   └── me
    │   │       └── daniel
    │   │           └── taskapi
    │   │               ├── TaskApiApplication.java
    │   │               ├── global(모든 도메인에서 공유)
    │   │               │   ├── auth(공용 인증 패키지)
    │   │               │   ├── error(도메인에서 공유 에러 처리 패키지)
    │   │               │   ├── event(도메인에서 발생하는 이벤트 객체)
    │   │               │   ├── model(도메인에서 공유하는 모델 객체)
    │   │               │   ├── AppConfiguration.java
    │   │               ├── book
    │   │               │   ├── api(책 도메인 표현 계층, API 컨트롤러)
    │   │               │   ├── application(책 도메인 응용 계층)
    │   │               │   ├── config(책 도메인 인프라 관련 설정)
    │   │               │   ├── dao(책 도메인 레파지토리)
    │   │               │   ├── domain(책 도메인 객체)
    │   │               │   ├── dto(유저 도메인 DTO 객체)
    │   │               │   ├── event(이벤트 리스너 & 이벤트 객체)
    │   │               ├── user
    │   │               │   ├── api(유저 도메인 표현 계층, API 컨트롤러)
    │   │               │   ├── application(유저 도메인 응용 계층)
    │   │               │   ├── dao(유저 도메인 레파지토리)
    │   │               │   ├── domain(유저 도메인 객체)
    │   │               │   ├── dto(유저 DTO 객체)
    │   │               │   ├── event(유저 관련 이벤트 리스너 & 이벤트 객체)
    │   │               │   ├── exception(유저 도메인 예외 패키지)
    │   │               │   ├── util(유틸리티 보관 패키지)
    │   └── resources
    │       ├── application.properties
    │       ├── application-prod.yml
    │       ├── import.sql
```
----
### 주요 도메인 흐름
#### [회원] - 로그인
![[회원]로그인](https://i.imgur.com/SjVPalK.png)
#### [회원] - 회원가입
![[회원]회원가입](https://i.imgur.com/ActkAz2.png)


#### [검색] - 인기 키워드 조회
![[검색]인기키워드](https://i.imgur.com/8ZdqZ5L.png)
#### [검색] - 회원 검색 키워드 조회
![[검색]회원키워드조회](https://i.imgur.com/Q4JKe7A.png)
#### [도서] - 도서 검색
![[도서]도서목록조회](https://i.imgur.com/7xDLMgL.png)


----
### API 문서
- [회원 API](./doc/USER_API_DOCUMENT.md)
- [도서 API](./doc/BOOK_API_DOCUMENT.md)
- [검색 API](./doc/SEARCH_API_DOCUMENT.md)
----
### 테스트
- 테스트는 `통합 테스트`를 기준으로 진행
- 테스트 명세는 하단을 참고

#### 테스트 명세
##### [도서서비스] - 도서 목록 조회 통합테스트
- 검색결과에 키워드 내용을 포함해야 한다.
- 검색 키워드가 없으면 요청에 실패해야 한다.
- 페이지 사이즈가 100 이상이면 요청에 실패해야 한다.
- 페이징(page=0, size=10)을 처리할 수 있어야 한다.
- 페이지 번호가 `0` 이하이면 요청에 실패해야 한다

##### [검색서비스] - 인기 키워드 목록 조회 통합테스트
- 키워드 목록은 10개가 반환되어야 한다.	

##### [검색서비스] - 회원 검색 키워드 목록 조회 통합테스트
- 카테고리로 필터 할 수 있어야 한다.
- 데이터가 존재하지 않으면 빈 배열을 반환해야 한다.
- 조회 결과에 키워드를 포함해야 한다.
- 페이징(page=1, size=10)을 할 수 있어야 한다.

##### [유저서비스] - 회원가입 통합테스트
- 이메일(`email`)이 없으면 요청에 실패해야 한다.
- 패스워드(`password`)가 없으면 요청에 실패해야 한다.
- 이메일(`email`) 형식이 아니면 요청에 실패해야 한다.
- 패스워드(`password`)가 정해진 길이에 맞지 않으면 요청에 실패해야 한다.
- 이메일(`email`)과 패스워드(`password`)가 모두 유효한 값이면 회원가입에 성공해야 한다.	


##### [유저서비스] - 로그인 통합테스트
- 이메일(`email`)이 없으면 요청에 실패해야 한다.
- 패스워드(`password`)가 없으면 요청에 실패해야 한다.
- 회원이 존재하지 않으면 요청에 실패해야 한다.
- 이메일(`email`) 형식이 아니면 요청에 실패해야 한다.
- 패스워드(`password`)가 정해진 길이에 맞지 않으면 요청에 실패해야 한다.
- 이메일(`email`)과 패스워드(`password`)가 모두 유효한 값이면 로그인에 성공해야 한다.









