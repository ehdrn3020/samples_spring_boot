# Design Patterns

GoF 디자인 패턴 23개 중 Refactoring.Guru 카탈로그에 정리된 22개를 세 분류로 나누어 정리한 문서.

출처: [Refactoring.Guru - Design Patterns in Java](https://refactoring.guru/design-patterns/java)
패턴의 정의는 위 문서를 참고해 재작성했으며, 실제 사례와 구분 기준은 자체 정리한 내용이다.

## 분류 기준

| 분류 | 관심사 | 한 줄 요약 |
| --- | --- | --- |
| Creational | 객체 생성 | 무엇을, 어떻게 만들 것인가 |
| Structural | 객체 조립 | 어떻게 엮고 감쌀 것인가 |
| Behavioral | 객체 간 협업 | 누가 무엇을 언제 할 것인가 |

## 1. Creational Patterns (생성 패턴)

객체 생성 로직을 감춰서, 사용하는 쪽이 구체 클래스에 의존하지 않게 만드는 것이 공통 목적이다.

| 패턴 | 핵심 | 언제 쓰나 | 실제 사례 |
| --- | --- | --- | --- |
| Factory Method | 상위 클래스가 생성 인터페이스를 제공하고, 어떤 타입을 만들지는 서브클래스가 결정 | 만들 타입이 1종류인데 결정을 미루고 싶을 때 | `Calendar.getInstance()`, Spring `FactoryBean` |
| Abstract Factory | 서로 관련된 객체 군(family)을 구체 클래스 노출 없이 생성 | 만들 타입이 여러 종류인데 세트로 묶여야 할 때 | `DocumentBuilderFactory`, JDBC 드라이버별 `Connection`/`Statement` 세트 |
| Builder | 복잡한 객체를 단계별로 조립 | 생성자 파라미터가 많고 선택 항목이 섞일 때 | `StringBuilder`, Lombok `@Builder`, `WebClient.builder()` |
| Prototype | 기존 객체를 복제해 클래스 의존 없이 새 객체 획득 | 생성 비용이 비싸거나 런타임에만 원본을 알 때 | `Object.clone()` |
| Singleton | 인스턴스 1개 보장 + 전역 접근점 | 상태 없는 공용 자원 | `Runtime.getRuntime()`, Spring 기본 빈 스코프 |

### 구분 포인트

- **Factory Method vs Abstract Factory** — 메서드 하나로 제품 하나 → Factory Method. 팩토리 객체 하나가 제품 여러 개를 일관된 세트로 → Abstract Factory. Abstract Factory 내부는 보통 Factory Method들로 구현된다.
- **Builder vs Factory** — 팩토리는 "무엇을" 만들지 결정, 빌더는 "어떻게 단계별로" 만들지 결정. 빌더는 결과물의 표현이 달라질 때 쓴다.
- **Prototype vs Factory** — 팩토리는 `new`, 프로토타입은 `copy`.

> 주의: Spring의 `@Scope("prototype")`은 이름만 같고 GoF Prototype 패턴과는 다른 개념이다.

## 2. Structural Patterns (구조 패턴)

객체를 조합해 더 큰 구조를 만들되 유연성을 유지하는 것이 목적이다. 7개 중 Adapter, Decorator, Facade, Proxy는 모두 "감싸기(wrapping)" 형태라 헷갈리기 쉽다. 차이는 구조가 아니라 **의도**에 있다.

| 패턴 | 핵심 | 의도 | 실제 사례 |
| --- | --- | --- | --- |
| Adapter | 호환되지 않는 인터페이스끼리 협업 가능하게 | 인터페이스 변환 | `InputStreamReader`, `Arrays.asList()` |
| Bridge | 추상화와 구현을 별도 계층으로 분리해 독립적으로 확장 | 두 축의 조합 폭발 방지 | JDBC `Driver`, SLF4J ↔ Logback |
| Composite | 객체를 트리로 구성하고 개별 객체처럼 다룸 | 부분-전체 동일 취급 | Swing `Container`, DOM 트리 |
| Decorator | 래퍼 객체로 감싸 새 행동을 덧붙임 | 기능 추가(중첩 가능) | `BufferedInputStream`, `Collections.unmodifiableList()` |
| Facade | 복잡한 클래스 집합에 단순한 진입점 제공 | 복잡도 은폐 | `JdbcTemplate`, `KafkaTemplate` |
| Flyweight | 공통 상태를 공유해 메모리에 더 많은 객체 수용 | 메모리 절약 | `Integer.valueOf()` 캐시, String pool |
| Proxy | 대역 객체가 원본으로의 접근을 통제 | 접근 제어(지연/권한/원격) | Spring AOP, JPA 지연 로딩, RMI |

### 감싸기 4형제 구분법

```text
Adapter    : 인터페이스가 다름   → 같게 맞춘다      (계약 변경 O, 기능 동일)
Decorator  : 인터페이스가 같음   → 기능을 더한다    (계약 변경 X, 기능 추가)
Proxy      : 인터페이스가 같음   → 접근을 통제한다  (계약 변경 X, 기능 동일)
Facade     : 인터페이스가 새로움 → 여럿을 하나로    (대상이 1개가 아닌 N개)
```

- **Decorator vs Proxy** — 둘 다 같은 인터페이스를 구현하지만, Decorator는 런타임에 사용자가 자유롭게 중첩하고, Proxy는 대상의 생명주기를 자기가 관리한다. `@Transactional`은 Proxy, `BufferedWriter(new FileWriter(...))`는 Decorator.
- **Bridge vs Adapter** — Adapter는 이미 만들어진 것을 사후에 끼워 맞추는 것, Bridge는 설계 시점에 미리 두 축을 분리하는 것.

## 3. Behavioral Patterns (행위 패턴)

객체 간 책임 분배와 통신 방식을 다룬다.

| 패턴 | 핵심 | 구분되는 지점 | 실제 사례 |
| --- | --- | --- | --- |
| Chain of Responsibility | 요청을 핸들러 체인에 흘려보내고, 각 핸들러가 처리할지 다음으로 넘길지 결정 | 처리자가 누구일지 모름 | Servlet `Filter`, Spring Security `FilterChain` |
| Command | 요청을 독립 객체로 만들어 인자 전달 · 큐잉 · 실행 취소 지원 | 요청 자체를 객체화 | `Runnable` |
| Iterator | 내부 표현을 노출하지 않고 컬렉션 순회 | 순회 방법 분리 | `Iterator`, `Stream` |
| Mediator | 객체 간 직접 통신을 막고 중재자를 통해서만 협업 | N:N을 N:1로 | `ExecutorService`, MVC의 `DispatcherServlet` |
| Memento | 구현 세부를 드러내지 않고 이전 상태를 저장 · 복원 | 상태 스냅샷 | 직렬화 기반 undo, DB savepoint |
| Observer | 관찰 대상에 일어난 이벤트를 여러 객체에 통지하는 구독 메커니즘 | 1:N 이벤트 통지 | `ApplicationEventPublisher`, Reactor, Kafka 컨슈머 |
| State | 내부 상태가 바뀌면 행동이 바뀌어 마치 클래스가 바뀐 듯 동작 | 상태끼리 서로를 앎 | 주문 상태머신, `Thread.State` |
| Strategy | 알고리즘군을 각각 클래스로 분리해 교체 가능하게 | 전략끼리 서로 모름 | `Comparator`, Spring `PasswordEncoder` |
| Template Method | 상위 클래스가 알고리즘 뼈대를 정하고 서브클래스가 일부 단계만 재정의 | 상속 기반 | `AbstractList`, `JdbcTemplate` 내부 |
| Visitor | 알고리즘을 대상 객체에서 분리 | 구조는 고정, 연산이 늘어남 | AST 처리, `FileVisitor` |

### 구분 포인트

- **State vs Strategy** — 구조는 거의 같다. Strategy는 클라이언트가 무엇을 쓸지 정하고 전략들끼리 독립적이다. State는 상태 객체가 스스로 다음 상태로 전이시킨다.
- **Strategy vs Template Method** — 전자는 위임(조합), 후자는 상속. 전자는 런타임 교체 가능, 후자는 컴파일 타임 고정.
- **Chain of Responsibility vs Observer** — 체인은 보통 하나가 처리하고 멈춘다(순차). 옵저버는 모두에게 통지한다(브로드캐스트).
- **Mediator vs Observer** — 둘 다 결합도를 낮추지만, Mediator는 중앙 허브가 흐름을 지휘하고, Observer는 발행자가 구독자를 모른 채 뿌린다.
- **Command vs Strategy** — 둘 다 동작을 객체로 감싸지만, Command는 "무엇을 할지"(실행 / 취소 / 큐잉이 관심사), Strategy는 "어떻게 할지"(알고리즘 교체가 관심사).

## 이 저장소의 kafka_samples에 적용된 패턴

| 코드 | 패턴 |
| --- | --- |
| `KafkaTemplate` | Facade |
| `@KafkaListener` 이벤트 수신 | Observer |
| `CommonErrorHandler` / `DeadLetterPublishingRecoverer` 주입 | Strategy |
| `DefaultErrorHandler`의 재시도 뼈대 | Template Method |

## 참고 링크

- [Design Patterns in Java (Refactoring.Guru)](https://refactoring.guru/design-patterns/java)
- [Creational Patterns](https://refactoring.guru/design-patterns/creational-patterns)
- [Structural Patterns](https://refactoring.guru/design-patterns/structural-patterns)
- [Behavioral Patterns](https://refactoring.guru/design-patterns/behavioral-patterns)
