package com.ecommerce.ecommerce.Controller;

import com.ecommerce.ecommerce.Dto.NotificationModel;
import com.ecommerce.ecommerce.Dto.NotificationRequest;
import com.ecommerce.ecommerce.Entities.Notification;
import com.ecommerce.ecommerce.Entities.Order;
import com.ecommerce.ecommerce.Entities.User;
import com.ecommerce.ecommerce.IService.NotificationServiceInterface;
import com.ecommerce.ecommerce.Repository.UserRepository;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.security.Principal;
import java.util.Date;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;

@Controller
public class NotificationController {
    @Autowired
    private NotificationServiceInterface notificationServiceInterface;

    @Autowired
    private UserRepository userRepository;


    public void addModelAttribute(Model model)
    {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication != null && authentication.getPrincipal() instanceof UserDetails) {
            UserDetails userDetails = (UserDetails) authentication.getPrincipal();
            String username = userDetails.getUsername();

            // Fetch user data based on username
            User user = userRepository.getUserByEmail(username);

            model.addAttribute("role", user.getRole());
            model.addAttribute("user", user);
            model.addAttribute("title", "Dashboard - ShopMart");
            if(Objects.equals(user.getRole(), "ROLE_USER")){
                model.addAttribute("baseUrl","member/base");
            }else if(Objects.equals(user.getRole(), "ROLE_ADMIN")){
                model.addAttribute("baseUrl","admin/base");
            }
        } else {
            throw new IllegalStateException("Invalid authentication");
        }
    }
    //consumer
    @PostMapping("/notification")
    public ResponseEntity<Notification> sendNotification(@RequestBody NotificationRequest notificationRequest){
        Notification notification=notificationServiceInterface.sendNotification(notificationRequest);
        return ResponseEntity.ok(notification);
    }


    @GetMapping("/notification")
    public String getNotification(HttpSession session,
                                  Model model, Principal principal)
    {
        addModelAttribute(model);
        String userEmail = principal.getName();
        User user = userRepository.getUserByEmail(userEmail);
        Set<Notification> notificationList=user.getNotifications();
        model.addAttribute("notificationList",notificationList);
        model.addAttribute("userId",user.getId());
        return "admin/notification_box";
    }
}
