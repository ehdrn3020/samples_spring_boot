## 개요
```declarative
- Spring Data JPA + ListCrudRepository CRUD + H2 In-Memory
- Spring Boot 3.2.x 에서 'ListCrudRepository'를 사용해 CRUD를 단순하게 실행하는 예제
- DB는 별도 설치 없이 H2 In-Memory DB를 사용
- 실행 환경
    - Java 17+ (Spring Boot 3.x 권장)
    - Gradle
- crud-01 참고하여 rest api로 구현
```

## API
```declarative
// GET /persons
curl http://localhost:8080/persons

// POST /persons ( window powershell에서 )
Invoke-RestMethod -Method POST `
-Uri "http://localhost:8080/persons" `
-ContentType "application/json" `
-Body '{"name":"John"}'

// POST /persons/with-list
curl -X POST http://localhost:8080/persons/with-list \
-H "Content-Type: application/json" \
-d "{\"name\":\"John\"}"
```

## DB 확인
```declarative
http://localhost:8080/h2-console/ 접속
```

## 프로세스
```declarative
[사용자 / 브라우저 / API 호출]
            |
            v
   HTTP Request (/persons ...)
            |
            v
+------------------------+
|   PersonController     |
| - 요청 받음            |
| - DTO 사용             |
| - Repository 호출      |
+------------------------+
            |
            v
+------------------------+
|   PersonRepository     |
| - DB 접근 담당         |
| - Person 엔티티 저장   |
| - Person 조회          |
+------------------------+
            |
            v
+------------------------+
|       Person           |
| - Entity               |
| - DB 테이블과 매핑     |
+------------------------+
            |
            v
        [Database]

애플리케이션 시작점
+------------------------+
|      Application       |
| - Spring Boot 시작     |
| - 컴포넌트 스캔        |
| - Controller/Repo 등록 |
+------------------------+
```