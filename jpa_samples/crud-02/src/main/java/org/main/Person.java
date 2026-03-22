package org.main;

import jakarta.persistence.*;

@Entity
@Table(name = "person")
public class Person {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // H2에서도 동작
    private Long id;

    @Column(nullable = false)
    private String name;

    protected Person() { } // JPA 기본 생성자

    public Person(String name) {
        this.name = name;
    }

    public Long getId() { return id; }
    public String getName() { return name; }

    public void setName(String name) { this.name = name; }

    @Override
    public String toString() {
        return "Person{id=" + id + ", name='" + name + "'}";
    }
}