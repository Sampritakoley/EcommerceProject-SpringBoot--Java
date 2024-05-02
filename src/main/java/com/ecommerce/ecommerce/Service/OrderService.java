package com.ecommerce.ecommerce.Service;

import com.ecommerce.ecommerce.Dto.*;
import com.ecommerce.ecommerce.Entities.*;
import com.ecommerce.ecommerce.Enums.NotificationTypes;
import com.ecommerce.ecommerce.IService.CartItemServiceInterface;
import com.ecommerce.ecommerce.IService.OrderServiceInterface;
import com.ecommerce.ecommerce.Repository.*;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class OrderService implements OrderServiceInterface {

    @Autowired
    private CartRepository cartRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private ItemRepository itemRepository;

    @Autowired
    private AddressRepository addressRepository;
    @Autowired
    private OrderRepository orderRepository;
    @Autowired
    private OrderItemRepository orderItemRepository;

    @Autowired
    private CartItemRepository cartItemRepository;

    @Autowired
    private CartItemServiceInterface cartItemServiceInterface;


    @Autowired
    private WarehouseRepository warehouseRepository;


    public List<OrderBookModel> getOrders(int userId)
    {
        List<Order> orderList=orderRepository.getOrdersByUserId(userId);
        List<OrderBookModel> orderBookModels=new ArrayList<>();
        for(Order order:orderList){
            ModelMapper modelMapper = new ModelMapper();
            OrderBookModel orderModel=modelMapper.map(order, OrderBookModel.class);
            List<OrderItemModel> orderItemList=orderItemRepository.getOrderItemModel(order.getId());
            orderModel.getOrderItemsModel().addAll(orderItemList);
            orderBookModels.add(orderModel);
        }
        return orderBookModels;
    }

    public MessageModel placeOrder(int addressId,Integer cartId,User user)
    {
        Cart cart=cartRepository.getCartByCartId(cartId);
        cart.setStatus("expired");
        cart.setLastUpdatedTime(new java.sql.Timestamp(new Date().getTime()));

        Optional<Address> addressOp=addressRepository.findById(addressId);
        Address address=addressOp.get();

        //create order
        Order order=new Order();
        order.setUser(user);
        order.setTotalPrice(cart.getTotalAmount());
        order.setStatus("Pending");
        order.setDate(new java.sql.Timestamp(new Date().getTime()));
        UUID uuid = UUID.randomUUID();
        order.setOrderTrackingId(uuid);
        order.setBillingAddress(address);
        order = orderRepository.save(order);



        //add orderItem
        List<PriceDetailModel> allitemDetailList=cartItemServiceInterface.getPriceDetail(cart.getId());

        List<PriceDetailModel> itemDetailList=allitemDetailList.subList(0,allitemDetailList.size()-1);

        for(PriceDetailModel priceDetailModel:itemDetailList)
        {
            OrderItem orderItem=new OrderItem();
            orderItem.setPriceToPay(priceDetailModel.getDiscount_price());
            orderItem.setTotalQty(priceDetailModel.getQuantity());
            Items item=itemRepository.getItemById(priceDetailModel.getItem_id());
            item.setQuantity(item.getQuantity()-priceDetailModel.getQuantity());
            orderItem.setItem(item);
            orderItem.setOrder(order);
            orderItemRepository.save(orderItem);
            itemRepository.save(item);
            order.getOrderItems().add(orderItem);
        }

        PriceDetailModel priceDetailModel=allitemDetailList.get(allitemDetailList.size()-1);
        order.setTotalPrice(priceDetailModel.getDiscount_price());


        //create new cart
        Cart newcart=new Cart();
        newcart.setStatus("active");
        newcart.setTotalAmount(0.0);
        newcart.setUser(user);
        newcart.setLastUpdatedTime(new java.sql.Timestamp(new Date().getTime()));
        user.getCarts().add(newcart);

        //change cart item status
        List<CartItem> cartItems=cartItemRepository.getCartItemByCartId(cart.getId());
        for(CartItem cartItem:cartItems){
            cartItem.setStatus("ordered");
            cartItemRepository.save(cartItem);
        }
        userRepository.save(user);
        cartRepository.save(cart);
        Order newOrder=orderRepository.save(order);
        MessageModel messageModel=new MessageModel(String.valueOf(user.getId()) ,String.valueOf(newOrder.getId()), NotificationTypes.New_Order.getValue());
        return messageModel;
    }

    public List<OrderModel> getOrder(List<Order> orders)
    {
        List<OrderModel> orderlist=new ArrayList<>();
        ModelMapper modelMapper = new ModelMapper();
        for(Order order:orders){
            OrderModel ordermodel=modelMapper.map(order, OrderModel.class);
            Optional<User> userOp=userRepository.findById(order.getUser().getId());
            User user=userOp.get();
            ordermodel.setUserEmail(user.getEmail());
            ordermodel.setUserFirstName(user.getFirst_name());
            ordermodel.setUserLastName(user.getLast_name());
            orderlist.add(ordermodel);
        }
        return orderlist;
    }

    public PrivateMessage processOrder(int orderId,UpdateOrderRequest updateOrderRequest)
    {
        Optional<Order> order_op=orderRepository.findById(orderId);
        Order order=order_op.get();
        List<Shippment> listShippment=order.getShippments();
        PrivateMessage privateMessage=null;
        if((listShippment.isEmpty() || !listShippment.get(listShippment.size()-1).getStatus().equals(updateOrderRequest.getStatus())) && updateOrderRequest.getStatus()!=null)
        {
            order.setStatus(updateOrderRequest.getStatus());
            Shippment shippment=new Shippment();
            shippment.setDate((new java.sql.Timestamp(new Date().getTime())).toString());
            shippment.setStatus(updateOrderRequest.getStatus());
            Optional<Warehouse> warehouse_op=warehouseRepository.findById(updateOrderRequest.getWarehouseId());
            Warehouse warehouse=warehouse_op.get();
            shippment.setWarehouse(warehouse);
            shippment.setOrder(order);
            order.getShippments().add(shippment);
            orderRepository.save(order);
            User toUser=order.getUser();
            String odrId=String.valueOf(order.getId());
            privateMessage=new PrivateMessage(odrId,toUser.getEmail(),shippment.getStatus());
        }
        if(updateOrderRequest.getDeliveryDate()!=null)
        {
            order.setDeliveryDate(updateOrderRequest.getDeliveryDate());
            orderRepository.save(order);
            User toUser=order.getUser();
            String odrId=String.valueOf(order.getId());
            privateMessage=new PrivateMessage(odrId,toUser.getEmail(),NotificationTypes.Delivery_Date.getValue());
        }
        return privateMessage;
    }
}
