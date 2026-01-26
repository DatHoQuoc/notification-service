package com.dathq.swd302.notificationservice.service;

import com.dathq.swd302.notificationservice.model.dto.request.NotificationRequest;
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

    @Autowired
    private NotificationService notificationService; // Thêm dòng này để gọi logic xử lý

    public KafkaNotificationConsumer(SimpMessagingTemplate messagingTemplate) {
        this.messagingTemplate = messagingTemplate;
    }

    @KafkaListener(topics = "ai-notification-topic", groupId = "notification-group")
    public void listen(String message) {
        System.out.println("Nhận tin nhắn mới từ Kafka: " + message);

        // Giả sử tin nhắn từ AI là một chuỗi Text, ta tạo một Request giả lập để lưu DB
        // Sau này khi AI gửi JSON, bạn sẽ dùng ObjectMapper để chuyển message thành Object
        NotificationRequest mockRequest = new NotificationRequest();
        mockRequest.setUserId(1L); // Hoặc lấy từ message nếu có
        mockRequest.setTitle("Thông báo từ AI");
        mockRequest.setContent(message);

        // Gọi Service để vừa lưu DB vừa đẩy WebSocket
        notificationService.createNotification(mockRequest);
    }
}
