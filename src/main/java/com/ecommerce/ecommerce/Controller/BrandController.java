package com.ecommerce.ecommerce.Controller;

import com.ecommerce.ecommerce.Dto.BrandRequest;
import com.ecommerce.ecommerce.Dto.PrivateMessage;
import com.ecommerce.ecommerce.Entities.Brands;
import com.ecommerce.ecommerce.Entities.Category;
import com.ecommerce.ecommerce.Entities.User;
import com.ecommerce.ecommerce.Helper.Message;
import com.ecommerce.ecommerce.IService.BrandServiceInterface;
import com.ecommerce.ecommerce.Repository.BrandRepository;
import com.ecommerce.ecommerce.Repository.CategoryRepository;
import com.ecommerce.ecommerce.Repository.UserRepository;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.security.Principal;
import java.util.Date;
import java.util.Objects;

@Controller
public class BrandController {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private BrandServiceInterface brandServiceInterface;

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

    @GetMapping("/category/{cid}/brand")
    public String brandForm(@PathVariable Integer cid, Model model, Principal principal, HttpServletRequest request){
        addModelAttribute(model);
        model.addAttribute("BrandRequest",new BrandRequest());
        model.addAttribute("cid",cid);
        return "admin/admin_brand_form";
    }


    @PostMapping("/category/{cid}/brand")
    public ResponseEntity<String> brandFormPost(@PathVariable Integer cid,
                                                        @RequestParam("brand_name")String brand_name,
                                                        @RequestParam("descriptions")String descriptions,
                                                        @RequestParam("brand_Logo") MultipartFile file,
                                                        HttpSession session,
                                                        Model model, Principal principal){
        try{
            addModelAttribute(model);
            String userEmail=principal.getName();
            User user=userRepository.getUserByEmail(userEmail);
            brandServiceInterface.addBrand(file,descriptions,brand_name,user,cid);
            session.setAttribute("message", new Message("New category is added successfully","alert-success"));
        }
        catch (Exception e){
            addModelAttribute(model);
            session.setAttribute("message", new Message("Something went wrong","alert-danger"));
        }
        return ResponseEntity.ok(brand_name);
    }
}
