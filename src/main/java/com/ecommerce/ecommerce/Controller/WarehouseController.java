package com.ecommerce.ecommerce.Controller;

import com.ecommerce.ecommerce.Dto.WarehouseModel;
import com.ecommerce.ecommerce.Entities.User;
import com.ecommerce.ecommerce.Entities.Warehouse;
import com.ecommerce.ecommerce.Repository.UserRepository;
import com.ecommerce.ecommerce.Repository.WarehouseRepository;
import org.modelmapper.ModelMapper;
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
import java.util.List;

@Controller
public class WarehouseController {

    @Autowired
    private WarehouseRepository warehouseRepository;

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
        } else {
            throw new IllegalStateException("Invalid authentication");
        }
    }


    @GetMapping("/warehouse")
    public String getWarehouse(Model model, Principal principal){
        addModelAttribute(model);
        String userEmail = principal.getName();
        User user = userRepository.getUserByEmail(userEmail);
        List<Warehouse> warehousesList=warehouseRepository.findAll();
        model.addAttribute("userId",user.getId());
        model.addAttribute("WarehouseList",warehousesList);
        return "admin/warehouse";
    }

    @PostMapping("/warehouse")
    public ResponseEntity<Integer> addAddress(
            @RequestBody WarehouseModel warehouseModel){
        ModelMapper modelMapper = new ModelMapper();
        Warehouse warehouse=modelMapper.map(warehouseModel, Warehouse.class);
        warehouseRepository.save(warehouse);
        return ResponseEntity.ok(1);
    }
}
