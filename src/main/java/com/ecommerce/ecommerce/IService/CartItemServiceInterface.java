package com.ecommerce.ecommerce.IService;

import com.ecommerce.ecommerce.Dto.PriceDetailModel;
import com.ecommerce.ecommerce.Entities.Cart;
import com.ecommerce.ecommerce.Entities.CartItem;
import com.ecommerce.ecommerce.Entities.User;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public interface CartItemServiceInterface {
    public Cart addToCart(User user, int itemId);

    public List<PriceDetailModel> getPriceDetail(int cartId);

    public void addToCart(CartItem cartItem);

    public void removeCartItem(CartItem cartItem);
}
