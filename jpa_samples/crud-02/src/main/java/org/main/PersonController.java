package org.main;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/persons")
public class PersonController {
    private final PersonRepository repo;

    public PersonController(PersonRepository repo) {
        this.repo = repo;
    }

    // 저장된 전체 목록 조회
    @GetMapping
    public List<Person> getAllPersons() {
        return repo.findAll();
    }

    // 1건 저장
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Person createPerson(@RequestBody PersonDTO request) {
        Person person = new Person(request.getName());
        return repo.save(person);
    }

    // 저장 후 전체 목록 반환
    @PostMapping("/with-list")
    @ResponseStatus(HttpStatus.CREATED)
    public List<Person> createPersonAndReturnList(@RequestBody PersonDTO request) {
        Person person = new Person(request.getName());
        repo.save(person);
        return repo.findAll();
    }
}
