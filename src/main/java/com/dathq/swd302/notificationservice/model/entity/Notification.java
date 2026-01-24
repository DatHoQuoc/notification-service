package com.dathq.swd302.notificationservice.model.entity;


import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

/**
 * @author matve
 */

@Entity
@Table(name = "notification")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Notification {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "notification_id")
    private Long notificationId;

    @Column(name = "user_id", nullable = false)
    private Long userId; // Chỉ lưu ID để map với User Service

    @Column(name = "notification_type")
    private String notificationType;

    private String title;

    @Column(columnDefinition = "TEXT")
    private String content;

    @JdbcTypeCode(SqlTypes.JSON) // Hướng dẫn Hibernate ép kiểu sang JSON cho Postgres
    @Column(name = "metadata", columnDefinition = "jsonb")
    private String metadata;

    @Column(name = "deep_link")
    private String deepLink;

    private String priority;

    @Builder.Default
    @Column(name = "is_read")
    private boolean isRead = false;

    @Column(name = "read_at")
    private LocalDateTime readAt;

    @Column(name = "created_at")
    private LocalDateTime createdAt = LocalDateTime.now();
}
