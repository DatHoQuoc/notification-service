package com.dathq.swd302.notificationservice.repository;

import com.dathq.swd302.notificationservice.model.entity.Notification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;
/**
 * @author matve
 */

@Repository
public interface NotificationRepository extends JpaRepository<Notification,Long> {
    // Tìm tất cả thông báo của một User và sắp xếp mới nhất lên đầu
    List<Notification> findByUserIdOrderByCreatedAtDesc(Long userId);
}
