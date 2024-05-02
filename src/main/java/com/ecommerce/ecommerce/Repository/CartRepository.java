package com.ecommerce.ecommerce.Repository;

import com.ecommerce.ecommerce.Entities.Brands;
import com.ecommerce.ecommerce.Entities.Cart;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface CartRepository extends JpaRepository<Cart,Integer> {

    @Transactional
    @Query(value = "select DISTINCT c.* from cart c where c.user_id=:userId and c.status='active'",nativeQuery = true)
    Cart getActiveCartByUserId(@Param("userId")int userId);

    @Transactional
    @Query(value = "select DISTINCT c.* from cart c where id=:cartId",nativeQuery = true)
    Cart getCartByCartId(@Param("cartId")int cartId);
}
