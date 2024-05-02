package com.ecommerce.ecommerce.Controller;

import com.ecommerce.ecommerce.Dto.MessageModel;
import com.ecommerce.ecommerce.Dto.PrivateMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MessageController {

    @Autowired
    SimpMessagingTemplate simpMessagingTemplate;

    @MessageMapping("/message")
    @SendTo("/topic/return-to")
    public MessageModel getContent(@RequestBody MessageModel message) {

        try {
            Thread.sleep(2000);
        }catch (InterruptedException e) {
            e.printStackTrace();
        }
        return message;
    }
    @MessageMapping("/private")
    public void sendToSpecificUser(@Payload PrivateMessage message) {
        simpMessagingTemplate.convertAndSendToUser(message.getTo(), "/specific", message);
    }
}
