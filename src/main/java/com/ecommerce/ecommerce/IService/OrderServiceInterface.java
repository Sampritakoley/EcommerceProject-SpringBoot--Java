package com.ecommerce.ecommerce.IService;

import com.ecommerce.ecommerce.Dto.*;
import com.ecommerce.ecommerce.Entities.Order;
import com.ecommerce.ecommerce.Entities.User;
import org.springframework.stereotype.Component;

import java.util.List;
@Component

public interface OrderServiceInterface {

    public List<OrderBookModel> getOrders(int userId);

    public MessageModel placeOrder(int addressId, Integer cartId, User user);

    public List<OrderModel> getOrder(List<Order> orders);

    public PrivateMessage processOrder(int orderId, UpdateOrderRequest updateOrderRequest);
}
