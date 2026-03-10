package com.dathq.swd302.notificationservice.controller;

import com.dathq.swd302.notificationservice.model.dto.request.EmailSendRequest;
import com.dathq.swd302.notificationservice.model.dto.request.NotificationRequest;
import com.dathq.swd302.notificationservice.model.entity.Notification;
import com.dathq.swd302.notificationservice.service.EmailService;
import com.dathq.swd302.notificationservice.service.NotificationService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author matve
 */

@RestController
@RequestMapping("/api/v1/notifications")
@RequiredArgsConstructor
public class NotificationController {
    private final NotificationService notificationService;
    private final EmailService emailService;

    @Value("${notification.secret}")
    private String expectedSecret;

    @PostMapping("/send-manual")
    public String sendManualNotification(@RequestBody NotificationRequest request) {
        notificationService.createNotification(request);
        return "Thông báo đã được gửi đi";
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<Notification>> getNotifications(@PathVariable Long userId) {
        return ResponseEntity.ok(notificationService.getNotificationsForUser(userId));
    }

    @GetMapping("/user/{userId}/unread-count")
    public ResponseEntity<Long> getUnreadCount(@PathVariable Long userId) {
        return ResponseEntity.ok(notificationService.getUnreadCount(userId));
    }

    @PatchMapping("/{id}/read")
    public ResponseEntity<Void> markAsRead(@PathVariable Long id) {
        notificationService.markAsRead(id);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteNotification(@PathVariable Long id) {
        notificationService.deleteNotification(id);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/send-mail")
    public ResponseEntity<String> sendEmail(
            @RequestHeader("X-Notification-Secret") String secret,
            @Valid @RequestBody EmailSendRequest request) {

        if (expectedSecret == null || !expectedSecret.equals(secret)) {
            return ResponseEntity.status(401).body("Truy cập bị từ chối: Sai Secret!");
        }

        emailService.sendEmail(request);
        return ResponseEntity.ok("Đã gửi thông báo thành công!");
    }

}
