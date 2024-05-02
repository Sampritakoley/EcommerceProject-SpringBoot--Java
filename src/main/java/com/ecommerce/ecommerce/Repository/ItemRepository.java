package com.ecommerce.ecommerce.Repository;

import com.ecommerce.ecommerce.Dto.CartItemModel;
import com.ecommerce.ecommerce.Dto.ItemModel;
import com.ecommerce.ecommerce.Entities.Cart;
import com.ecommerce.ecommerce.Entities.Category;
import com.ecommerce.ecommerce.Entities.Items;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.query.Procedure;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ItemRepository extends JpaRepository<Items,Integer> {

    @Query(value=" from Items i where i.products.id=:pid")
    List<Items> getItemsByProductId(@Param("pid")int pid);

    @Transactional
    @Query(value = "select DISTINCT i.* from items i where id=:itemId",nativeQuery = true)
    Items getItemById(@Param("itemId")int itemId);

    @Query(value="select new com.ecommerce.ecommerce.Dto.CartItemModel(i.id as item_id,i.available,i.description,i.name,i.offer,i.price,i.quantity,i.image,ci.id as cid,ci.quantity as cartQuantity,ci.totalPrice as cartPrice) " +
            "  from Cart c inner join " +
            "  CartItem ci on c.id=ci.cart.id inner join " +
            "  Items i on i.id=ci.item.id where c.id=:cartId and ci.status='added' ")
    List<CartItemModel> getCartItemModel(@Param("cartId")int cartId);




}
