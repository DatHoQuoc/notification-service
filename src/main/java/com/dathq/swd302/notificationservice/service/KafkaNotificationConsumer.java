package com.dathq.swd302.notificationservice.service;

import com.dathq.swd302.notificationservice.model.dto.request.NotificationRequest;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

/**
 * @author matve
 */
@Service
public class KafkaNotificationConsumer {
    private final SimpMessagingTemplate messagingTemplate;
    private final NotificationService notificationService;
    private final ObjectMapper objectMapper; // Không dùng @Autowired ở đây

    @Autowired
    public KafkaNotificationConsumer(SimpMessagingTemplate messagingTemplate, NotificationService notificationService) {
        this.messagingTemplate = messagingTemplate;
        this.notificationService = notificationService;

        // Khởi tạo trực tiếp để tránh lỗi thiếu Bean
        this.objectMapper = new ObjectMapper();
        this.objectMapper.registerModule(new JavaTimeModule());
    }

    @KafkaListener(topics = "ai-notification-topic", groupId = "notification-group")
    public void listen(String message) {
        try {
            System.out.println("Nhận JSON từ Kafka: " + message);

            // Chuyển chuỗi JSON nhận được từ Kafka thành Object NotificationRequest
            NotificationRequest request = objectMapper.readValue(message, NotificationRequest.class);

            // Gọi Service để xử lý logic lưu DB và gửi Email
            notificationService.createNotification(request);

        } catch (Exception e) {
            System.err.println("Lỗi parse JSON hoặc xử lý tin nhắn: " + e.getMessage());
            e.printStackTrace();
        }
    }
}