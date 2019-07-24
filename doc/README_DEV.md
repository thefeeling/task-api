
## 개요
## FRONT-END
### 프레임워크 & 라이브러리
- vue.js를 사용하여 간단한 도서 검색 및 키워드 검색 어플리케이션을 구현
- 인증의 경우 토큰 기반으로 진행, LocalStorage를 사용하여 기 저장되어 있는 토큰이 존재 할 경우 해당 토큰을 사용하여 인증 진행
- 

## BACK-END
### 프레임워크 & 라이브러리
- spring-boot의 경우 가장 최신 버전인 `2.1.6 RELEASE`를 기준을 기준으로 셋팅
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
  - 인증 구현 시, JWT 토큰을 사용
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
```puml
participant API as A
participant APPLICATION as B
participant DAO as C
participant EVENT as D

A -> B: HTTP 요청에 맞는 응용 서비스 메서드 호출
activate B
B -> C: DAO.`REPOSITORY`를 사용하여\n도메인 객체를 조회하며 전반적인 흐름을 제어
B -> A: 비즈니스 로직 수행\nDTO 변환/리턴
deactivate B


alt 응용 계층에서 이벤트를 발행할 경우
  B -> D: 이밴트 전달\n1.트랜잭션을 분리하는 목적\n2.다른 도메인에 이벤트를 전달하는 목적
  activate B
  deactivate B
  activate D
	D -> D: 이벤트 리스너가 이벤트를 받은 후 로직 수행
  
end
```

- 도메인간 발생하는 데이터 처리의 경우 `Event`를 사용하여 처리한다. 
@TransactionEventListener 혹은 @EventListener를 사용했으며 요청 스레드가 Block 되지 않도록 @Async 어노테이션을 활용하여 별도의 스레드풀에서 이벤트 Job을 처리하도록 했음. 
아래는 `도서 도메인`에서 검색 시 발생하는 조회 키워드를 `검색 도메인`에 전달하는 흐름이다.
```puml
box 책 도메인
  participant API as A
  participant APPLICATION as B
  participant DAO as C
  A -> B: 키워드에 해당하는 책 목록 요청
  activate B
  B -> C: 외부 HTTP API 호출하여\n도서 목록을 조회
  B -> EVENT: 검색이벤트(SearchedEvent) 전달\n- 유저ID, 키워드 등
  activate EVENT
  B -> A: 데이터 반환
  deactivate B
end box

box 검색 도메인
  participant EVENT
  EVENT -> EVENT: 도메인 객체에 맞도록 Event 객체 변환
  EVENT -> DAO: 도메인 객체를 레파지토리에\n전달하여 검색 이력 영속화
end box
```

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
```puml
actor 회원 as U
participant UserController as A
participant UserLoginService as B
database UserRepository as C
participant TokenPublishService as D
participant UserLoginedEventListener as F
database UserLoginEventRepository as G
autonumber

U -> A: 로그인 요청
  group 요청 스레드
  A -> A: 요청 본문 유효성 검사
  A -> B: 로그인 응용 서비스 호출
  B -> C: findByEmail(email)\n- 이메일 주소로 도메인 객체 조회
  C -> B : 도메인 객체 반환


  alt 유저 도메인 객체가 존재하는 경우  
    B -> F: 로그인 성공 이벤트 전달(UserJoinedEvent)
    B -> D: JWT 토큰 발행 요청
    D -> B: TokenPayload 반환
    B -> B: DTO 변환
    B -> A: DTO 반환
    A -> U: HTTP/1.1 200 OK\n- TokenPayload 데이터 반환
  else 조회 결과가 없는 경우
    autonumber 6
    
    B -> A: [throw NotExistsUserException]\n- 존재하지 않는 유저입니다.
  end
end

group 이벤트 스레드
  F -> F: 이벤트 객체 수신 후 UserLoginEvent 도메인 객체로 변환
  F -> G: UserLoginEventRepository에 도메인 객체 영속화
end
```

#### [회원] - 회원가입
```puml
actor 회원 as U
participant UserController as A
participant UserJoinService as B
database UserRepository as C
autonumber

U -> A: 회원가입 요청
  group 요청 스레드
  A -> A: 요청 본문 유효성 검사
  A -> B: 회원가입 응용 서비스 호출
  B -> C: findByEmail(email)\n- 이메일 주소로 도메인 객체 조회
  C -> B : 도메인 객체 반환


  alt 유저 도메인 객체가 존재하는 경우  
    autonumber 6
    B -> A: [throw ExistsEmailException]\n- 이미 존재하는 이메일입니다.
  else 조회 결과가 없는 경우
    B -> B: 도메인 객체 생성\n- 패스워드 암호화 진행
    B -> C: UserRepository.save(user)\n- 유저 도메인 객체 영속화    
    B -> A: return
    A -> U: HTTP/1.1 204 NO_CONTENT
  end
end
```


#### [검색] - 인기 키워드 조회
```puml
actor 회원 as U
participant SearchController as A
participant SearchService as B
database SearchHistoryRepository as C
autonumber

U -> A: 인기 키워드 조회 API 호출
  group 요청 스레드
  A -> A: 요청 쿼리스트링 유효성 검사
  A -> B: 인기 키워드 조회 응용 서비스 메소드 호출
  alt 캐시가 존재하는 경우(30초 단위)
    autonumber 4
    B -> A: 캐시 되어 있는 DTO 반환
  else 캐시가 없는 경우
    autonumber 4
    B -> C: searchHistoryRepository.findTopNKeyword(limit)\n- 키워드 단위로 집계 데이터 조회
    C -> B: 집계 결과 반환
    B -> A: DTO 반환
    A -> U: HTTP/1.1 200 OK
  end  
end
```
#### [검색] - 회원 검색 키워드 조회
```puml
actor 회원 as U
participant SearchController as A
participant SearchService as B
participant SearchHistoryPredicateBuilder as C
database UserSearchHistoryRepository as D
autonumber

U -> A: 회원 검색 키워드 조회 API 호출
  group 요청 스레드
  A -> A: 요청 쿼리스트링 유효성 검사
  A -> B: 회원 검색 키워드 조회 조회 응용 서비스 메소드 호출
  B -> C: 조회에 필요한 Predicate 생성\n- 요청 DTO에 있는 데이터를 기준으로\n 회원ID, 키워드, 카테고리에 해당하는 Predicate 생성
  C -> B: Predicate 반환
  B -> D: UserSearchHistoryRepository.fetchPage(predicate, pageable)\n- 키워드 데이터 조회
  D -> B: 페이징이 되어 있는 데이터 반환
  B -> B: DTO 변환
  B -> A: DTO 반환
  A -> U: HTTP/1.1 200 OK
end
```
#### [도서] - 도서 검색
```puml
skinparam ParticipantPadding 10
skinparam BoxPadding 30
box 도서
  actor 회원 as U
  participant BookController as A
  participant BookSearchAdapterService as B
  participant KSearchDao as C
  participant NSearchDao as D
  autonumber

    U -> A: 도서 검색 API 호출
    A -> A: 요청 쿼리스트링 유효성 검사
    A -> B: 도서 검색 응용 서비스 메소드 호출
    B -> B: 요청 DTO 객체를 KSearchDao.search 호출에 필요한 객체로 변환
    B -> C: 도서 데이터 조회 요청(HTTP)
    C -> B: 도서 데이터 반환
    alt 도서 데이터 조회에 성공한 경우
      B -> B: KSearchDao.search의 응답 데이터를 공용 도서 조회 DTO로 변환
      B -> A: 도서 조회 응답 DTO 반환
      A -> U: HTTP/1.1 200 OK
    else 데이터 조회에 실패한 경우
      autonumber 7
      B -> B: 요청 DTO 객체를 NSearchDao.search 호출에 필요한 객체로 변환
      B -> D: 도서 데이터 조회 요청(HTTP)
      D -> B: 도서 데이터 반환
      alt 도서 데이터 호출에 성공한 경우
        autonumber 10
        B -> B: NSearchDao.search의 응답 데이터를 공용 도서 조회 DTO로 변환
        B -> A: 도서 조회 응답 DTO 반환
        A -> U: HTTP/1.1 200 OK      
      else 도서 데이터 호출에 실패한 경우
        autonumber 10
        B -> A: Throw ServiceUnavailableException()\n- 도서 조회 서비스 호출에 실패했습니다.
        A -> U: HTTP/1.1 500 ServiceUnavailable
      end  
    end
    
    group 도메인 이벤트 처리
      alt 도서 데이터 조회에 성공한 경우
        B -> SearchedEventListener: 검색 이벤트 전달\n- 회원ID, 키워드 등
        SearchedEventListener -> SearchedEventListener: UserSearchHistory 엔티티 생성
        SearchedEventListener -> UserSearchHistoryRepository: 엔티티 영속화
      end
    end

end box

box 검색
  participant SearchedEventListener as SearchedEventListener
  database UserSearchHistoryRepository as UserSearchHistoryRepository
end box

```


----
### API 문서
- [회원 API](./doc/USER_API_DOCUMENT.md)
- [도서 API](./doc/BOOK_API_DOCUMENT.md)
- [검색 API](./doc/SEARCH_API_DOCUMENT.md)
----
