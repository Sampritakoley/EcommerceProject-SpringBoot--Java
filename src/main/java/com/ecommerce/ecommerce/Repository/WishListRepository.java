package com.ecommerce.ecommerce.Repository;

import com.ecommerce.ecommerce.Dto.WishListModel;
import com.ecommerce.ecommerce.Entities.Items;
import com.ecommerce.ecommerce.Entities.WishList;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface WishListRepository extends JpaRepository<WishList,Integer> {

    @Transactional
    @Query(value = "select DISTINCT w.* from wishlist w where id=:wishListId",nativeQuery = true)
    WishList getWishListById(@Param("wishListId")int wishListId);



    @Transactional
    @Query(value = "select DISTINCT w.* from wishlist w where user_id=:userId and item_id=:itemId",nativeQuery = true)
    WishList getWishListByUserIdAndItemId(@Param("itemId")int itemId,@Param("userId")int userId);



    @Query(value = "select new com.ecommerce.ecommerce.Dto.WishListModel(w.id as wishlist_id, w.status as status,w.updated_time as updated_time, " +
            "w.item.id as item_id, i.available as available,i.description as description, " +
            "i.name as name,i.offer as offer,i.price as price,i.quantity as quantity,i.products.id as products_id, " +
            "i.image as image,i.brand.id as brand_id) " +
            "FROM WishList w inner join Items i " +
            "on w.item.id=i.id and w.status='added' where w.user.id=:userId")
    List<WishListModel> getWishListByUserId(@Param("userId")int userId);
}
