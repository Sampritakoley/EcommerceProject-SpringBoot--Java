package com.ecommerce.ecommerce.Controller;

import com.ecommerce.ecommerce.Dto.CategoryRequest;
import com.ecommerce.ecommerce.Entities.Category;
import com.ecommerce.ecommerce.Entities.User;
import com.ecommerce.ecommerce.Helper.Message;
import com.ecommerce.ecommerce.Repository.CategoryRepository;
import com.ecommerce.ecommerce.Repository.UserRepository;
import com.ecommerce.ecommerce.Service.CategoryService;
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
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Controller
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private CategoryRepository categoryRepository;
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
            model.addAttribute("title", "Dashboard - Smart Contact Manager");
        } else {
            throw new IllegalStateException("Invalid authentication");
        }
    }

    @GetMapping("/item")
    public String items( Model model, Principal principal){
        addModelAttribute(model);
        return "admin/admin_item";
    }


    @GetMapping("/admin/category/{page}")
    public String showContactList(@PathVariable Integer page, Model model, Principal principal){
        addModelAttribute(model);
        // Ensure page number is non-negative
        // Integer page = Integer.parseInt(pge);
        page = Math.max(0, page);
        // Set the number of items per page
        int pageSize = 4;
        // Create PageRequest for pagination
        Pageable pageable = PageRequest.of(page, pageSize);
        // Fetch contacts for the given user and page
        Page<Category> categories=categoryService.getCategory(pageable);
        model.addAttribute("Categories", categories.getContent());
        model.addAttribute("totalPages", categories.getTotalPages());
        model.addAttribute("currentPage", page);
        return "admin/admin_category";
    }

    @GetMapping("/category")
    public String contactForm(Model model,Principal principal){
        addModelAttribute(model);
        model.addAttribute("Category",new CategoryRequest());
        return "admin/admin_add_category";
    }


    @PostMapping("/category")
    public String addContactForm(@Valid @ModelAttribute("Category") CategoryRequest category,
                                 @RequestParam("profileImage") MultipartFile file,
                                 BindingResult bindingResult, HttpSession session,
                                 Model model, Principal principal)
    {
        try{
            if(bindingResult.hasErrors())
            {
                throw new Exception();
            }
            if(!bindingResult.hasErrors())
            {
                addModelAttribute(model);
                String userEmail=principal.getName();
                User user=userRepository.getUserByEmail(userEmail);
                ModelMapper modelMapper = new ModelMapper();
                Category newCategory=modelMapper.map(category, Category.class);
                if(file.isEmpty()){
                    System.out.println("File is empty");
                }else{
                    newCategory.setImage(file.getOriginalFilename());
                    File saveFile=new ClassPathResource("/static/image").getFile();
                    Path path= Paths.get(saveFile.getAbsolutePath()+File.separator+file.getOriginalFilename());
                    Files.copy(file.getInputStream(),path, StandardCopyOption.REPLACE_EXISTING);
                    System.out.println("File is uploaded successfully");
                }
                newCategory.setCreated_by(user.getFirst_name()+" "+user.getLast_name());
                newCategory.setCreated_at(new java.sql.Timestamp(new Date().getTime()));
                categoryRepository.save(newCategory);
                session.setAttribute("message", new Message("New category is added successfully","alert-success"));
            }
        }
        catch (Exception e){
            addModelAttribute(model);
            session.setAttribute("message", new Message("Something went wrong","alert-danger"));
        }
        return "redirect:/admin/category/0";
    }


}
