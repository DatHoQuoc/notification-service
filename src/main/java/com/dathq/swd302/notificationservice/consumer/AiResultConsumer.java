package com.dathq.swd302.notificationservice.consumer;

import com.dathq.swd302.notificationservice.model.dto.request.NotificationRequest;
import com.dathq.swd302.notificationservice.service.NotificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

/**
 * @author matve
 */

@Component
public class AiResultConsumer {
    @Autowired
    private NotificationService notificationService;

    @KafkaListener(topics = "ai-check-results", groupId = "notification-group")
    public void consumeAiResult(NotificationRequest request) {
        // Khi nhận được tin từ AI Service qua Kafka, đẩy thẳng vào nghiệp vụ xử lý
        notificationService.createNotification(request);
    }
}
