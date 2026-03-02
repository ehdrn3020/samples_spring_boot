package org.main;

import org.springframework.data.repository.ListCrudRepository;

public interface PersonRepository extends ListCrudRepository<Person, Long> {
    // 기본 CRUD는 상속으로 자동 제공
    // 필요하면 여기서 findByName 같은 메서드 추가 가능
}