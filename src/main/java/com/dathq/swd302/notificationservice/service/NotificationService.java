package com.dathq.swd302.notificationservice.service;

import com.dathq.swd302.notificationservice.model.dto.request.NotificationRequest;
import com.dathq.swd302.notificationservice.model.entity.Notification;
import com.dathq.swd302.notificationservice.model.entity.UserNotificationPreference;
import com.dathq.swd302.notificationservice.repository.NotificationRepository;
import com.dathq.swd302.notificationservice.repository.UserNotificationPreferenceRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.List;

/**
 * @author matve
 */

@Service
public class NotificationService {
    @Autowired
    private NotificationRepository notificationRepository;

    @Autowired
    private UserNotificationPreferenceRepository preferenceRepository;

    @Autowired
    private SimpMessagingTemplate messagingTemplate;

    @Autowired
    private EmailService emailService;

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

        Notification savedNotification = notificationRepository.save(notification);

        // 3. Logic gửi Email (Nếu shouldSendEmail == true)
        if (shouldSendEmail) {
            // Lấy email từ request gửi lên
            String userEmail = request.getRecipientEmail();

            if (userEmail != null && !userEmail.isEmpty()) {
                String htmlContent = "<h3>" + request.getTitle() + "</h3>" +
                        "<p>" + request.getContent() + "</p>" +
                        "<hr><small>Đây là thông báo tự động từ hệ thống.</small>";

                emailService.sendHtmlEmail(userEmail, request.getTitle(), htmlContent);
            } else {
                System.out.println("Không thể gửi email vì recipientEmail trống (userId: " + request.getUserId() + ")");
            }
        }

        // 4. Thông báo runtime
        // Đẩy tin nhắn vào topic riêng của từng User: /topic/user/{userId}/notifications
        String destination = "/topic/user/" + request.getUserId() + "/notifications";
        messagingTemplate.convertAndSend(destination, savedNotification);

        System.out.println("Đã đẩy thông báo runtime tới: " + destination);
    }


    public List<Notification> getNotificationsForUser(Long userId) {
        return notificationRepository.findByUserIdOrderByCreatedAtDesc(userId);
    }

    public long getUnreadCount(Long userId) {
        return notificationRepository.countByUserIdAndIsReadFalse(userId);
    }


    public void markAsRead(Long id) {
        Notification notification = notificationRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy thông báo"));
        notification.setRead(true);
        notificationRepository.save(notification);
    }

    @Transactional
    public void deleteNotification(Long id) {
        Notification notification = notificationRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy thông báo để xóa"));
        notification.setDeleted(true); // Đánh dấu đã xóa
        notificationRepository.save(notification); // Lưu lại vào DB
    }


}
