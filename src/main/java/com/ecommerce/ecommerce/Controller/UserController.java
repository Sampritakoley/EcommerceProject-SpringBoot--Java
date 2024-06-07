package com.ecommerce.ecommerce.Controller;

import com.ecommerce.ecommerce.Entities.User;
import com.ecommerce.ecommerce.Repository.UserRepository;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.security.Principal;
import java.util.Optional;

@Controller
public class UserController {
    @Autowired
    private UserRepository userRepository;

    public void addModelAttribute(Model model)
    {
       /* String username=principal.getName();
        User user=userRepository.getUserByEmail(username);
        model.addAttribute("user",user);
        model.addAttribute("title","Dashboard -Smart Contact Manger");*/
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication != null && authentication.getPrincipal() instanceof UserDetails) {
            UserDetails userDetails = (UserDetails) authentication.getPrincipal();
            String username = userDetails.getUsername();

            // Fetch user data based on username
            User user = userRepository.getUserByEmail(username);

            model.addAttribute("role", user.getRole());
            model.addAttribute("user", user);
            model.addAttribute("title", "Dashboard - ShopMart");
        } else {
            throw new IllegalStateException("Invalid authentication");
        }
    }

    @GetMapping("/user/dashboard")
    public String dashboard(Model model){
        addModelAttribute(model);
        return "member/user_dashboard";
    }

    @GetMapping("/admin/dashboard")
    public String admindashboard(Model model){
        addModelAttribute(model);
        return "admin/admin_dashboard";
    }









}
