package com.ecommerce.ecommerce.Controller;

import com.ecommerce.ecommerce.Dto.CartItemModel;
import com.ecommerce.ecommerce.Dto.PriceDetailModel;
import com.ecommerce.ecommerce.Entities.Cart;
import com.ecommerce.ecommerce.Entities.CartItem;
import com.ecommerce.ecommerce.Entities.Items;
import com.ecommerce.ecommerce.Entities.User;
import com.ecommerce.ecommerce.IService.CartItemServiceInterface;
import com.ecommerce.ecommerce.Repository.CartItemRepository;
import com.ecommerce.ecommerce.Repository.CartRepository;
import com.ecommerce.ecommerce.Repository.ItemRepository;
import com.ecommerce.ecommerce.Repository.UserRepository;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.security.Principal;
import java.util.Date;
import java.util.List;

@Controller
public class CartItemController {
   @Autowired
   private CartItemServiceInterface cartItemServiceInterface;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private CartRepository cartRepository;

    @Autowired
    private CartItemRepository cartItemRepository;

    @Autowired
    private ItemRepository itemRepository;
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

    @GetMapping("/cart/items/{itemId}")
    private String addToCart(@PathVariable Integer itemId, HttpSession session,
                             Model model, Principal principal)
    {
        addModelAttribute(model);
        String userEmail=principal.getName();
        User user=userRepository.getUserByEmail(userEmail);
        Cart cart=cartItemServiceInterface.addToCart(user,itemId);
        return "redirect:/cart/"+cart.getId()+"/item";
    }

    @GetMapping("/cart/item/")
    private String cartDetails(HttpSession session,
                               Model model, Principal principal)
    {
        addModelAttribute(model);
        String userEmail=principal.getName();
        User user=userRepository.getUserByEmail(userEmail);
        Cart cart=cartRepository.getActiveCartByUserId(user.getId());
        List<CartItemModel> allCatItems=itemRepository.getCartItemModel(cart.getId());
        model.addAttribute("Items",allCatItems);
        model.addAttribute("totalCartItem",allCatItems.size());
        model.addAttribute("cartId",cart.getId());
        List<PriceDetailModel> priceDetailList=cartItemServiceInterface.getPriceDetail(cart.getId());
        model.addAttribute("PriceDetailList",priceDetailList);
        return "admin/admin_cart";
    }

    //update add qty in cart item
    @PostMapping("/add/cart-item/{cartItemId}/item")
    private String incrementCartItem(@PathVariable Integer cartItemId)
    {
        CartItem cartItem=cartItemRepository.getCartItemById(cartItemId);
        cartItemServiceInterface.addToCart(cartItem);
        return "redirect:/cart/item/";
    }

    //update remove qty in cart item
    @PostMapping("/remove/cart-item/{cartItemId}/item")
    private String decrementCartItem(@PathVariable Integer cartItemId)
    {
        CartItem cartItem=cartItemRepository.getCartItemById(cartItemId);
        cartItemServiceInterface.removeCartItem(cartItem);
        return "redirect:/cart/item/";
    }
}
