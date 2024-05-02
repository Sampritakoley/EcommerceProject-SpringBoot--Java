package com.ecommerce.ecommerce.IService;

import com.ecommerce.ecommerce.Dto.NotificationRequest;
import com.ecommerce.ecommerce.Entities.Notification;
import org.springframework.stereotype.Component;

@Component
public interface NotificationServiceInterface {
    public Notification sendNotification(NotificationRequest notificationRequest);
}
