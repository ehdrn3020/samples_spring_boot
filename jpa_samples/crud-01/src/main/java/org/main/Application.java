package org.main;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class Application {

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

    @Bean
    CommandLineRunner demo(PersonRepository repo) {
        return args -> {
            System.out.println("\n=== CREATE ===");
            Person p1 = repo.save(new Person("John"));
            Person p2 = repo.save(new Person("Alice"));
            System.out.println("Saved: " + p1);
            System.out.println("Saved: " + p2);

            System.out.println("\n=== READ ALL (ListCrudRepository는 List 반환) ===");
            repo.findAll().forEach(System.out::println);

            System.out.println("\n=== READ BY ID ===");
            Person found = repo.findById(p1.getId()).orElseThrow();
            System.out.println("Found: " + found);

            System.out.println("\n=== UPDATE ===");
            found.setName("John Updated");
            // 같은 엔티티를 다시 save 해도 되고,
            // (트랜잭션/영속성 컨텍스트 상황에 따라) 변경 감지로 UPDATE 되기도 함.
            repo.save(found);

            repo.findAll().forEach(System.out::println);

            System.out.println("\n=== DELETE ===");
            repo.deleteById(p2.getId());

            repo.findAll().forEach(System.out::println);

            System.out.println("\n=== COUNT ===");
            System.out.println("count=" + repo.count());
        };
    }
}