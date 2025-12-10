package org.example.listener;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
@RequiredArgsConstructor
@Slf4j
public class ApplicationEventListener {

    @RabbitListener(queues = "application.queue")
    public void handleApplicationSubmitted(Map<String, Object> payload) {
        log.info("ASYNC EVENT RECEIVED → Student {} applied to Job {}",
                payload.get("studentId"), payload.get("jobId"));

        // Simulate heavy work (email, notification, analytics...)
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }

        log.info("Heavy processing finished for application {}", payload.get("applicationId"));
    }
}