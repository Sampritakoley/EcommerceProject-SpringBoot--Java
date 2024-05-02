package com.ecommerce.ecommerce.Config;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class CustomAuthenticationSuccessHandler extends SimpleUrlAuthenticationSuccessHandler implements AuthenticationSuccessHandler {
    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        String userRole;
        if (authentication.getAuthorities().stream().anyMatch(a -> a.getAuthority().equals("ROLE_ADMIN"))) {
            // Redirect to admin dashboard if user has admin role
            response.sendRedirect("/admin/dashboard");
            userRole="ROLE_ADMIN";
        } else {
            // Redirect to user dashboard for other roles
            response.sendRedirect("/user/dashboard");
            userRole="ROLE_USER";
        }
        request.getSession().setAttribute("userRole", userRole);
    }
}
