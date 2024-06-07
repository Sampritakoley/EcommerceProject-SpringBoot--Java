package com.ecommerce.ecommerce.Controller;

import com.ecommerce.ecommerce.Dto.RegisterDataDto;
import com.ecommerce.ecommerce.Entities.User;
import com.ecommerce.ecommerce.Helper.Message;
import com.ecommerce.ecommerce.Service.UserService;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
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
import java.util.HashMap;
import java.util.Map;

@Controller
public class HomeController {
    @Autowired
    private UserService userService;
    @GetMapping("/home")
    public String home(Model model)
    {
        model.addAttribute("title","Home- ShopMart");
        return "home";
    }



    @GetMapping("/signup")
    public String signUp(Model model)
    {
        model.addAttribute("title","Sign Up- ShopMart");
        model.addAttribute("RegisterData",new RegisterDataDto());
        return "signup";
    }

    @PostMapping("/signup")
    public String addRegister(@Valid @ModelAttribute("RegisterData") RegisterDataDto registerDataDto, @RequestParam("profileImage") MultipartFile file, BindingResult bindingResult, HttpSession session)
    {
        try{
            if(bindingResult.hasErrors())
            {
                throw new Exception();
            }
            if(!bindingResult.hasErrors())
            {
                User user=userService.addUser(registerDataDto,file);
                session.setAttribute("message", new Message("You have been registered successfully","alert-success"));
                return "signup";
            }
        }catch (Exception e){
            session.setAttribute("message", new Message("Something went wrong","alert-danger"));
            return "signup";
        }
        return "signup";
    }

    @GetMapping("/signin")
    public String login(Model model)
    {
        model.addAttribute("title","LoginPage");
        return "login";
    }

    @GetMapping("/user/role")
    @ResponseBody
    public Map<String, String> getUserRole(HttpSession session) {
        String role = (String) session.getAttribute("userRole");
        if (role == null) {
            role = "USER"; // Default to USER if role is not set
        }
        Map<String, String> response = new HashMap<>();
        response.put("role", role);
        return response;
    }

}
