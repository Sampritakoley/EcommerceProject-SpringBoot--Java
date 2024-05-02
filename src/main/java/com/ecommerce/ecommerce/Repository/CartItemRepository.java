package com.ecommerce.ecommerce.Repository;

import com.ecommerce.ecommerce.Entities.Cart;
import com.ecommerce.ecommerce.Entities.CartItem;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface CartItemRepository extends JpaRepository<CartItem,Integer> {

    @Transactional
    @Query(value = "select DISTINCT c.* from cart_item c where c.id=:cid",nativeQuery = true)
    CartItem getCartItemById(@Param("cid")int cid);

    @Transactional
    @Query(value = "select DISTINCT c.* from cart_item c where c.cart_id=:cartId and status='added'",nativeQuery = true)
    List<CartItem> getCartItemByCartId(@Param("cartId")int cartId);


}
