package com.dathq.swd302.notificationservice.model.dto.request;

import lombok.Data;

/**
 * @author matve
 */
@Data
public class NotificationRequest {
    private Long userId;
    private String notificationType;
    private String title;
    private String content;
    private String metadata;
    private String deepLink;
    private String priority;
}
