package com.dathq.swd302.notificationservice.controller;

import com.dathq.swd302.notificationservice.model.dto.request.NotificationRequest;
import com.dathq.swd302.notificationservice.service.NotificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author matve
 */

@RestController
@RequestMapping("/api/v1/notifications")
public class NotificationController {
    @Autowired
    private NotificationService notificationService;

    @PostMapping("/test-ai-result")
    public String testAiResult(@RequestBody NotificationRequest request) {
        notificationService.createNotification(request);
        return "Đã gửi yêu cầu tạo thông báo thành công!";
    }
}
