package com.dathq.swd302.notificationservice.repository;

import com.dathq.swd302.notificationservice.model.entity.UserNotificationPreference;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

/**
 * @author matve
 */
@Repository
public interface UserNotificationPreferenceRepository extends JpaRepository<UserNotificationPreference,String>{
    // Tìm cấu hình nhận thông báo của User theo loại thông báo
    Optional<UserNotificationPreference> findByUserIdAndNotificationType(Long userId, String notificationType);
}
