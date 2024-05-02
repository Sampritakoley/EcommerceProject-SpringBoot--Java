package com.ecommerce.ecommerce.Service;

import com.ecommerce.ecommerce.Entities.Items;
import com.ecommerce.ecommerce.Entities.User;
import com.ecommerce.ecommerce.Entities.WishList;
import com.ecommerce.ecommerce.IService.WishListServiceInterface;
import com.ecommerce.ecommerce.Repository.ItemRepository;
import com.ecommerce.ecommerce.Repository.WishListRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Objects;
@Service
public class WishlistService implements WishListServiceInterface {

    @Autowired
    private WishListRepository wishListRepository;
    @Autowired
    private ItemRepository itemRepository;

    public void addToWishlist(WishList wishListItem, User user, int itemId)
    {
        if(wishListItem==null){
            WishList wishList=new WishList();
            wishList.setStatus("added");
            wishList.setUpdated_time(new java.sql.Timestamp(new Date().getTime()));
            Items item=itemRepository.getItemById(itemId);
            wishList.setUser(user);
            wishList.setItem(item);
            wishListRepository.save(wishList);
        }if(wishListItem!=null && Objects.equals(wishListItem.getStatus(), "removed")) {
        wishListItem.setStatus("added");
        wishListItem.setUpdated_time(new java.sql.Timestamp(new Date().getTime()));
        wishListRepository.save(wishListItem);
        }
    }
}
