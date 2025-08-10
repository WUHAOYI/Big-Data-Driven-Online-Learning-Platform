package com.goodstudy.notification.repository;

import com.goodstudy.notification.model.NotificationEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface NotificationEntityRepository extends JpaRepository<NotificationEntity, Long> {
}
