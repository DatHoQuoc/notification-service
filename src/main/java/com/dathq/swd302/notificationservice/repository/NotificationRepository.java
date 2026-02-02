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

    // Đếm số thông báo chưa đọc
    long countByUserIdAndIsReadFalse(Long userId);

    // Thêm "AndIsDeletedFalse" vào tên hàm để Spring Data JPA tự hiểu
    List<Notification> findByUserIdAndIsDeletedFalseOrderByCreatedAtDesc(Long userId);

    // Đếm số lượng chưa đọc cũng phải loại bỏ những cái đã xóa
    long countByUserIdAndIsReadFalseAndIsDeletedFalse(Long userId);


}
