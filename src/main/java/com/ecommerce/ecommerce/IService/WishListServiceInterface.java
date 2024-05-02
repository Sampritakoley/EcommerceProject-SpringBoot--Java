package com.ecommerce.ecommerce.IService;

import com.ecommerce.ecommerce.Entities.User;
import com.ecommerce.ecommerce.Entities.WishList;
import org.springframework.stereotype.Component;

@Component
public interface WishListServiceInterface {
    public void addToWishlist(WishList wishListItem, User user, int itemId);
}
