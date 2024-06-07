package com.ecommerce.ecommerce.Service;

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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
public class CartItemService implements CartItemServiceInterface {
    @Autowired
    private CartRepository cartRepository;

    @Autowired
    private CartItemRepository cartItemRepository;

    @Autowired
    private ItemRepository itemRepository;
    public Cart addToCart(User user, int itemId)
    {
        Cart cart=cartRepository.getActiveCartByUserId(user.getId());
        Items item=itemRepository.getItemById(itemId);
        List<CartItem> availableItems = cart.getCartItems().stream()
                .filter(cartItem-> cartItem.getItem().getId()==itemId && Objects.equals(cartItem.getStatus(), "added"))
                .collect(Collectors.toList());
        if(availableItems.size()==0){
            double totalPrice=cart.getTotalAmount()+item.getPrice();
            cart.setTotalAmount(totalPrice);
            cart.setLastUpdatedTime(new java.sql.Timestamp(new Date().getTime()));
            cartRepository.save(cart);
            CartItem cartItem=new CartItem();
            cartItem.setItem(item);
            cartItem.setQuantity(1);
            cartItem.setTotalPrice(item.getPrice());
            cartItem.setStatus("added");
            cartItem.setUpdatedTime(new java.sql.Timestamp(new Date().getTime()));
            cartItem.setCart(cart);
            cartItem.setItemPrice(item.getPrice());
            cartItemRepository.save(cartItem);
        }
        return cart;
    }

    public List<PriceDetailModel> getPriceDetail(int cartId)
    {
        List<CartItemModel> allCatItems=itemRepository.getCartItemModel(cartId);
        List<PriceDetailModel> priceDetailList=new ArrayList<>();
        double totalActualPrice=0;
        double totalOffer=0;
        double totalPrice=0;
        for(CartItemModel item:allCatItems){
            PriceDetailModel priceDetailModel=new PriceDetailModel();
            priceDetailModel.setItem_id(item.getItem_id());
            priceDetailModel.setItem_name(item.getName());
            priceDetailModel.setActual_price(item.getPrice()*item.getCartQuantity());
            priceDetailModel.setQuantity(item.getCartQuantity());
            double offerPrice=((item.getPrice()*item.getOffer())/100)*item.getCartQuantity();
            priceDetailModel.setOffer(offerPrice);
            double discount=priceDetailModel.getActual_price()-offerPrice;
            priceDetailModel.setDiscount_price(discount);

            totalActualPrice+=priceDetailModel.getActual_price();
            totalOffer+=offerPrice;
            totalPrice+=discount;
            priceDetailList.add(priceDetailModel);
        }
        PriceDetailModel subTotal=new PriceDetailModel();
        subTotal.setDiscount_price(totalPrice);
        subTotal.setItem_name("subTotal");
        subTotal.setActual_price(totalActualPrice);
        subTotal.setOffer(totalOffer);
        priceDetailList.add(subTotal);
        return  priceDetailList;
    }

    public void addToCart(CartItem cartItem)
    {
        Cart cart=cartItem.getCart();
        cart.setTotalAmount(cart.getTotalAmount()+cartItem.getItemPrice());
        cart.setLastUpdatedTime(new java.sql.Timestamp(new Date().getTime()));
        cartItem.setQuantity(cartItem.getQuantity()+1);
        cartItem.setTotalPrice(cartItem.getTotalPrice()+cartItem.getItemPrice());
        cartItem.setUpdatedTime(new java.sql.Timestamp(new Date().getTime()));
        cartItemRepository.save(cartItem);
        cartRepository.save(cart);
    }

    public void removeCartItem(CartItem cartItem)
    {
        Cart cart=cartItem.getCart();
        cart.setTotalAmount(cart.getTotalAmount()-cartItem.getItemPrice());
        cart.setLastUpdatedTime(new java.sql.Timestamp(new Date().getTime()));
        cartItem.setQuantity(cartItem.getQuantity()-1);
        cartItem.setTotalPrice(cartItem.getTotalPrice()-cartItem.getItemPrice());
        cartItem.setUpdatedTime(new java.sql.Timestamp(new Date().getTime()));
        if(cartItem.getQuantity()==0){
            cartItem.setStatus("removed");
        }
        cartItemRepository.save(cartItem);
        cartRepository.save(cart);
    }
}
