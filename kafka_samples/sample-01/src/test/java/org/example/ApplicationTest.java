package org.example;

import org.junit.jupiter.api.Test;

import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;

@SpringBootTest
@TestPropertySource(properties = "spring.profiles.active=test")
public class ApplicationTest {

    @Test
    public void contextLoads() {
    }

}



//package org.example;
//
//import java.nio.charset.StandardCharsets;
//
//import org.junit.jupiter.api.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.kafka.core.KafkaTemplate;
//import org.springframework.kafka.test.context.EmbeddedKafka;
//
///**
// * Embedded Kafka 기반 디버깅용 테스트
// *
// * - 실제 Kafka 서버 없이
// * - @KafkaListener / retry / DLT 흐름을
// *   디버거로 확인하기 위한 클래스
// */
//@EmbeddedKafka(
//        topics = {"topic1", "topic1-dlt"},
//        partitions = 1,
//        bootstrapServersProperty = "spring.kafka.bootstrap-servers"
//)
//@SpringBootTest
//class ApplicationTest {
//
//    @Autowired
//    KafkaTemplate<Object, Object> kafkaTemplate;
//
//    @Test
//    void debug_kafka_listener() throws Exception {
//        // 🔴 브레이크포인트 ① (메시지 전송 직전)
//        kafkaTemplate.send(
//                "topic1",
//                "{\"foo\":\"fail-test\"}".getBytes(StandardCharsets.UTF_8)
//        );
//
//        // Listener / retry / DLT 디버깅을 위한 대기
//        Thread.sleep(10_000);
//    }
//}
