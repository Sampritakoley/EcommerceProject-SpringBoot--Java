package com.ecommerce.ecommerce.Service;

import com.ecommerce.ecommerce.Dto.NotificationModel;
import com.ecommerce.ecommerce.Dto.NotificationRequest;
import com.ecommerce.ecommerce.Entities.Notification;
import com.ecommerce.ecommerce.Entities.Order;
import com.ecommerce.ecommerce.Entities.User;
import com.ecommerce.ecommerce.IService.NotificationServiceInterface;
import com.ecommerce.ecommerce.Repository.NotificationModelRepository;
import com.ecommerce.ecommerce.Repository.OrderRepository;
import com.ecommerce.ecommerce.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Optional;
@Service
public class NotificationService implements NotificationServiceInterface {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private NotificationModelRepository notificationModelRepository;


    public Notification sendNotification(NotificationRequest notificationRequest)
    {
        Notification notification=new Notification();
        notification.setType(notificationRequest.getType());
        Integer ordId=Integer.parseInt(notificationRequest.getOrderId());
        Optional<Order> orderOptional=orderRepository.findById(ordId);
        Order order=orderOptional.get();

        NotificationModel notificationModel=this.notificationModelRepository.findByType(notificationRequest.getType());
        notification.setTitle(notificationModel.getTitle());
        notification.setMessage("Order #"+order.getOrderTrackingId()+notificationModel.getMessage()+(new java.sql.Timestamp(new Date().getTime())).toString());
        notification.setStatus("delivered");
        notification.setSendAt((new java.sql.Timestamp(new Date().getTime())).toString());

        User toUser=userRepository.getUserByEmail(notificationRequest.getUserName());
        toUser.getNotifications().add(notification);
        userRepository.save(toUser);
        return notification;
    }
}
