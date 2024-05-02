package com.ecommerce.ecommerce.Helper;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.messaging.simp.stomp.StompHeaders;
import org.springframework.messaging.simp.stomp.StompSession;
import org.springframework.messaging.simp.stomp.StompSessionHandlerAdapter;
import org.springframework.stereotype.Component;

@Component
public class WebSocketHandler extends StompSessionHandlerAdapter {
    @Override
    public void afterConnected(StompSession session, StompHeaders connectedHeaders) {
        super.afterConnected(session, connectedHeaders);
        String sessionId = session.getSessionId(); // STOMP session ID
        String userAgent = connectedHeaders.getFirst("User-Agent"); // Example of a custom header

        System.out.println("Client connected with Session ID: " + sessionId);
        if (userAgent != null) {
            System.out.println("User-Agent: " + userAgent);
        }
    }


}
