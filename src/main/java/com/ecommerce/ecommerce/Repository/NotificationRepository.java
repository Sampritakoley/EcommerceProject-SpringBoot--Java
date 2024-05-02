package com.ecommerce.ecommerce.Repository;

import com.ecommerce.ecommerce.Entities.Notification;
import org.springframework.data.jpa.repository.JpaRepository;

public interface NotificationRepository extends JpaRepository<Notification,Integer> {
}
