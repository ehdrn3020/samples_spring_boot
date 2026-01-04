package org.example;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.core.task.TaskExecutor;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaOperations;
import org.springframework.kafka.listener.CommonErrorHandler;
import org.springframework.kafka.listener.DeadLetterPublishingRecoverer;
import org.springframework.kafka.listener.DefaultErrorHandler;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.util.backoff.FixedBackOff;

/**
 * 운영 환경 기준 Kafka Consumer 애플리케이션.
 *
 * - 외부 Kafka broker 연동
 * - retry + Dead Letter Topic 처리
 * - 무한 실행 서비스 형태
 */
@SpringBootApplication
public class Application {

    private static final Logger logger = LoggerFactory.getLogger(Application.class);

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

    /**
     * 운영 환경용 ErrorHandler.
     *
     * - 1초 간격 재시도
     * - 2회 실패 후 DLT 전송
     */
    @Bean
    public CommonErrorHandler errorHandler(KafkaOperations<Object, Object> template) {
        return new DefaultErrorHandler(
                new DeadLetterPublishingRecoverer(template),
                new FixedBackOff(1_000L, 2)
        );
    }

    /**
     * 메인 Kafka Consumer.
     *
     * - topic1 소비
     * - payload는 byte[] 그대로 처리
     * - JSON 파싱 실패로 Consumer 전체가 죽는 것 방지
     */
    @KafkaListener(
            id = "fooGroup",
            topics = "topic1",
            groupId = "foo-consumer-group"
    )
    public void listen(byte[] payload) {
        String message = new String(payload);
        logger.info("Received message: {}", message);

        if (message.contains("fail")) {
            throw new RuntimeException("forced failure");
        }

        // 실제 비즈니스 처리
    }

    /**
     * Dead Letter Topic Consumer.
     *
     * - 운영에서는 재처리 / 저장 / 알림 용도
     */
    @KafkaListener(
            id = "dltGroup",
            topics = "topic1-dlt",
            groupId = "foo-dlt-consumer-group"
    )
    public void dltListen(byte[] payload) {
        logger.error("Received message from DLT: {}", new String(payload));
    }

    /**
     * 운영 환경용 비동기 Executor.
     *
     * - Consumer thread 보호
     * - Thread 폭증 방지
     */
    @Bean
    public TaskExecutor taskExecutor() {
        ThreadPoolTaskExecutor exec = new ThreadPoolTaskExecutor();
        exec.setCorePoolSize(4);
        exec.setMaxPoolSize(8);
        exec.setQueueCapacity(1000);
        exec.setThreadNamePrefix("kafka-worker-");
        exec.initialize();
        return exec;
    }
}
