package com.ecommerce.ecommerce.Controller;

import com.ecommerce.ecommerce.Dto.ItemRequest;
import com.ecommerce.ecommerce.Entities.Brands;
import com.ecommerce.ecommerce.Entities.Items;
import com.ecommerce.ecommerce.Entities.Products;
import com.ecommerce.ecommerce.Entities.User;
import com.ecommerce.ecommerce.Helper.Message;
import com.ecommerce.ecommerce.IService.ItemServiceInterface;
import com.ecommerce.ecommerce.Repository.BrandRepository;
import com.ecommerce.ecommerce.Repository.UserRepository;
import com.ecommerce.ecommerce.Service.ItemService;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.security.core.Authentication;
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
import java.util.Date;
import java.util.List;
import java.util.Objects;

@Controller
public class ItemController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private BrandRepository brandRepository;

    @Autowired
    private ItemServiceInterface itemServiceInterface;


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

    @GetMapping("/category/{cid}/product/{pid}/item")
    public String itemForm(@PathVariable Integer pid, @PathVariable Integer cid, Model model, Principal principal){
        addModelAttribute(model);
        model.addAttribute("ItemRequest",new ItemRequest());
        model.addAttribute("cid",cid);
        List<Brands> brandList=brandRepository.getAllBrandByCategoryId(cid);
        if(!brandList.isEmpty()){
            model.addAttribute("brands",brandList);
        }
        return "admin/admin_item_form";
    }

    @PostMapping("/category/{cid}/product/{pid}/item")
    public String itemFormPost(@PathVariable Integer cid, @PathVariable Integer pid,
                               @Valid @ModelAttribute("ItemRequest") ItemRequest itemRequest,
                               @RequestParam("profileImage") MultipartFile file,
                               BindingResult bindingResult, HttpSession session,
                               Model model, Principal principal){
        try{
            if(bindingResult.hasErrors())
            {
                throw new Exception();
            }
            String brand_name = itemRequest.getBrand_name().substring(0, itemRequest.getBrand_name().length() - 1);
            itemRequest.setBrand_name(brand_name);
            if(!bindingResult.hasErrors())
            {
                addModelAttribute(model);
                String userEmail=principal.getName();
                User user=userRepository.getUserByEmail(userEmail);
                itemServiceInterface.addItem(file,itemRequest,user,pid);
                session.setAttribute("message", new Message("New category is added successfully","alert-success"));
            }
        }
        catch (Exception e){
            addModelAttribute(model);
            session.setAttribute("message", new Message("Something went wrong","alert-danger"));
        }
        return "redirect:/category/"+cid+"/products/"+pid;
    }
}
