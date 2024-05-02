package com.ecommerce.ecommerce.Controller;

import com.ecommerce.ecommerce.Dto.WishListModel;
import com.ecommerce.ecommerce.Entities.Items;
import com.ecommerce.ecommerce.Entities.User;
import com.ecommerce.ecommerce.Entities.WishList;
import com.ecommerce.ecommerce.IService.WishListServiceInterface;
import com.ecommerce.ecommerce.Repository.ItemRepository;
import com.ecommerce.ecommerce.Repository.UserRepository;
import com.ecommerce.ecommerce.Repository.WishListRepository;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.security.Principal;
import java.util.Date;
import java.util.List;
import java.util.Objects;

@Controller
public class WishListController {

    @Autowired
    private WishListRepository wishListRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ItemRepository itemRepository;

    @Autowired
    private WishListServiceInterface wishListServiceInterface;



    public void addModelAttribute(Model model)
    {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication != null && authentication.getPrincipal() instanceof UserDetails) {
            UserDetails userDetails = (UserDetails) authentication.getPrincipal();
            String username = userDetails.getUsername();

            User user = userRepository.getUserByEmail(username);

            model.addAttribute("role", user.getRole());
            model.addAttribute("user", user);
            model.addAttribute("title", "Dashboard - Smart Contact Manager");
        } else {
            throw new IllegalStateException("Invalid authentication");
        }
    }
    @GetMapping("/add/wishlist/item/{itemId}")
    private void addToWishlist(@PathVariable Integer itemId, HttpSession session,
                               Model model, Principal principal)
    {
        addModelAttribute(model);
        String userEmail=principal.getName();
        User user=userRepository.getUserByEmail(userEmail);
        WishList wishListItem=wishListRepository.getWishListByUserIdAndItemId(itemId,user.getId());
        wishListServiceInterface.addToWishlist(wishListItem,user,itemId);
    }

    @GetMapping("/remove/wishlist/{wishListId}")
    private void removeToWishlist(@PathVariable Integer wishListId,HttpSession session,
                                  Model model, Principal principal)
    {
        addModelAttribute(model);
        WishList wishList=wishListRepository.getWishListById(wishListId);
        wishList.setStatus("removed");
        wishList.setUpdated_time(new java.sql.Timestamp(new Date().getTime()));
        wishListRepository.save(wishList);
    }

    @GetMapping("/wishlist")
    private String getWishlist(HttpSession session,
                               Model model, Principal principal)
    {
        addModelAttribute(model);
        String userEmail=principal.getName();
        User user=userRepository.getUserByEmail(userEmail);
        List<WishListModel> wishListList=wishListRepository.getWishListByUserId(user.getId());
        model.addAttribute("WishListItems",wishListList);
        return "admin/admin_wishlist";
    }



}
