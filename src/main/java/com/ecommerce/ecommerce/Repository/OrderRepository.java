package com.ecommerce.ecommerce.Repository;

import com.ecommerce.ecommerce.Entities.Order;
import com.ecommerce.ecommerce.Entities.Products;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.UUID;

public interface OrderRepository extends JpaRepository<Order,Integer> {

    @Transactional
    @Query(value = "select DISTINCT o.* from customer_order o where user_id=:user_id ORDER BY o.id DESC",nativeQuery = true)
    List<Order> getOrdersByUserId(@Param("user_id")int user_id);

    @Transactional
    @Query(value = "select DISTINCT o.* from customer_order o where o.order_tracking_id=:order_tracking_id",nativeQuery = true)
    Order getOrdersByTrackingId(@Param("order_tracking_id") UUID order_tracking_id);

    @Transactional
    @Query(value = "select DISTINCT o.* from customer_order o where o.status=:status",nativeQuery = true)
    List<Order> findByStatus(@Param("status")String status);
}
