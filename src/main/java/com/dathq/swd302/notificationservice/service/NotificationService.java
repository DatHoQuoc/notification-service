package com.dathq.swd302.notificationservice.service;

import com.dathq.swd302.notificationservice.model.dto.request.NotificationRequest;
import com.dathq.swd302.notificationservice.model.entity.Notification;
import com.dathq.swd302.notificationservice.model.entity.UserNotificationPreference;
import com.dathq.swd302.notificationservice.repository.NotificationRepository;
import com.dathq.swd302.notificationservice.repository.UserNotificationPreferenceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;

/**
 * @author matve
 */

@Service
public class NotificationService {
    @Autowired
    private NotificationRepository notificationRepository;

    @Autowired
    private UserNotificationPreferenceRepository preferenceRepository;

    public void createNotification(NotificationRequest request) {
        // 1. Kiểm tra Preference của User
        boolean shouldSendEmail = preferenceRepository.findByUserIdAndNotificationType(request.getUserId(), request.getNotificationType())
                .map(UserNotificationPreference::isEmailEnabled)
                .orElse(true); // Mặc định là có gửi nếu chưa cài đặt

        // 2. Lưu vào Database
        Notification notification = Notification.builder()
                .userId(request.getUserId())
                .notificationType(request.getNotificationType())
                .title(request.getTitle())
                .content(request.getContent())
                .metadata(request.getMetadata())
                .deepLink(request.getDeepLink())
                .priority(request.getPriority())
                .createdAt(LocalDateTime.now())
                .isRead(false)
                .build();

        notificationRepository.save(notification);

        // 3. Logic gửi Email (Nếu shouldSendEmail == true)
        if (shouldSendEmail) {
            System.out.println("Gọi sang Email Service để gửi mail cho user: " + request.getUserId());
        }

        // 4. (Sau này sẽ gọi WebSocket gửi Runtime ở đây)
    }
}
