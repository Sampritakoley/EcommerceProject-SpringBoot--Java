package com.ecommerce.ecommerce.Controller;

import com.ecommerce.ecommerce.Dto.*;
import com.ecommerce.ecommerce.Entities.Address;
import com.ecommerce.ecommerce.Entities.Order;
import com.ecommerce.ecommerce.Entities.User;
import com.ecommerce.ecommerce.Entities.Warehouse;
import com.ecommerce.ecommerce.Repository.AddressRepository;
import com.ecommerce.ecommerce.Repository.UserRepository;
import com.ecommerce.ecommerce.Repository.WarehouseRepository;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.*;

@Controller
public class AddressController {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private AddressRepository addressRepository;



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

    @GetMapping("/address")
    public String getOrders(Model model, Principal principal){
        addModelAttribute(model);
        String userEmail = principal.getName();
        User user = userRepository.getUserByEmail(userEmail);
        Set<Address> addressList=user.getAddresses();
        model.addAttribute("userId",user.getId());
        model.addAttribute("AddressList",addressList);
        return "admin/address";
    }


    @PostMapping("user/{userId}/address")
    public String addAddress(@PathVariable Integer userId,
                             @RequestBody  AddressModel addressModel){

        Optional<User> userOp=userRepository.findById(userId);
        User user=userOp.get();
        ModelMapper modelMapper = new ModelMapper();
        Address address=modelMapper.map(addressModel, Address.class);
        user.getAddresses().add(address);
        userRepository.save(user);
        return "redirect:/address";
    }



}
