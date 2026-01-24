package com.dathq.swd302.notificationservice.model.entity;

import jakarta.persistence.*;
import lombok.*;

/**
 * @author matve
 */

@Entity
@Table(name = "user_notification_preference")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserNotificationPreference {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID) // Thường dùng UUID cho PreferenceID
    @Column(name = "preference_id")
    private String preferenceId;

    @Column(name = "user_id", nullable = false)
    private Long userId;

    @Column(name = "notification_type")
    private String notificationType;

    @Column(name = "email_enabled")
    private boolean emailEnabled;

    @Column(name = "push_enabled")
    private boolean pushEnabled;

    @Column(name = "sms_enabled")
    private boolean smsEnabled;
}
