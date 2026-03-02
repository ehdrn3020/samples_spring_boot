## 개요
```declarative
- Spring Data JPA + ListCrudRepository CRUD + H2 In-Memory
- Spring Boot 3.2.x 에서 'ListCrudRepository'를 사용해 CRUD를 단순하게 실행하는 예제
- DB는 별도 설치 없이 H2 In-Memory DB를 사용
- 실행 환경
    - Java 17+ (Spring Boot 3.x 권장)
    - Gradle
```

## 동작 흐름
```declarative
// 애플리케이션이 실행되면 CommandLineRunner가 자동으로 동작하며 콘솔에 아래 흐름이 출력
  → repo.save()      (INSERT)
  → repo.findAll()   (SELECT)
  → repo.findById()  (SELECT)
  → repo.save()      (UPDATE)
  → repo.deleteById()(DELETE)
```

## DB 확인
```declarative
http://localhost:8080/h2-console/ 접속
```