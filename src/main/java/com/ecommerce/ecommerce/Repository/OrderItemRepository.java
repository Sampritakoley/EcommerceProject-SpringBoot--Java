package com.ecommerce.ecommerce.Repository;

import com.ecommerce.ecommerce.Dto.CartItemModel;
import com.ecommerce.ecommerce.Dto.OrderItemModel;
import com.ecommerce.ecommerce.Entities.OrderItem;
import jakarta.persistence.criteria.CriteriaBuilder;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface OrderItemRepository extends JpaRepository<OrderItem, Integer> {

    @Query(value="select new com.ecommerce.ecommerce.Dto.OrderItemModel(oi.id as orderItemId,oi.priceToPay as priceToPay,oi.totalQty as totalQuantity,oi.item.id as itemId,oi.order.id as orderId, " +
            "  i.description as description,i.name as name,i.offer as offer,i.price as price,i.image as image,i.brand.id as brandId) " +
            "  FROM OrderItem oi " +
            "  inner join Items i on oi.item.id=i.id where oi.order.id=:orderId")
    List<OrderItemModel> getOrderItemModel(@Param("orderId")int orderId);
}
