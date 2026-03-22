package com.debish.health.notification.domain.repository;

import com.debish.health.notification.domain.model.NotificationRecord;
import org.springframework.data.jpa.repository.JpaRepository;

public interface NotificationRecordRepository extends JpaRepository<NotificationRecord, Long> {
}
