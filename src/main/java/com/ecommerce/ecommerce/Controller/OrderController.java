package com.ecommerce.ecommerce.Controller;

import com.ecommerce.ecommerce.Dto.*;
import com.ecommerce.ecommerce.Entities.*;
import com.ecommerce.ecommerce.Enums.NotificationTypes;
import com.ecommerce.ecommerce.IService.OrderServiceInterface;
import com.ecommerce.ecommerce.Repository.*;
import com.ecommerce.ecommerce.Service.OrderService;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import org.hibernate.metamodel.mapping.ordering.ast.OrderingExpression;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.security.Principal;
import java.util.*;

@Controller
public class OrderController {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private OrderServiceInterface orderServiceInterface;

    @Autowired
    private OrderItemRepository orderItemRepository;
    @Autowired
    private WarehouseRepository warehouseRepository;

    @Autowired
    private OrderRepository orderRepository;
    public void addModelAttribute(Model model)
    {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication != null && authentication.getPrincipal() instanceof UserDetails) {
            UserDetails userDetails = (UserDetails) authentication.getPrincipal();
            String username = userDetails.getUsername();

            // Fetch user data based on username
            User user = userRepository.getUserByEmail(username);
            model.addAttribute("role", user.getRole());
            model.addAttribute("user", user);
            model.addAttribute("title", "Dashboard - Smart Contact Manager");
        } else {
            throw new IllegalStateException("Invalid authentication");
        }
    }

    @GetMapping("/orders")
    public String getOrders(Model model, Principal principal){
        addModelAttribute(model);
        String userEmail = principal.getName();
        User user = userRepository.getUserByEmail(userEmail);
        List<OrderBookModel> orderBookModels=orderServiceInterface.getOrders(user.getId());
        model.addAttribute("OrderList",orderBookModels);
        return "admin/admin_order";
    }

    @PostMapping("/order/{cartId}")
    public ResponseEntity<MessageModel> postOrderAndSendNotification(@PathVariable Integer cartId, HttpSession session,
                                                                     Model model, Principal principal,@RequestBody int addressId){
        addModelAttribute(model);
        String userEmail=principal.getName();
        User user=userRepository.getUserByEmail(userEmail);
        MessageModel messageModel=orderServiceInterface.placeOrder(addressId,cartId,user);
        return ResponseEntity.ok(messageModel);
    }

    @GetMapping("/order-confirmation")
    public String orderConfirmation(Model model, Principal principal){
        addModelAttribute(model);
        String userEmail = principal.getName();
        User user = userRepository.getUserByEmail(userEmail);
        Set<Address> addressList=user.getAddresses();
        model.addAttribute("userId",user.getId());
        model.addAttribute("AddressList",addressList);
        return "admin/address_payment";
    }

    @GetMapping("/order-manage")
    public String getAllOrder(HttpSession session,
                              Model model, Principal principal)
    {
        addModelAttribute(model);
        String userEmail = principal.getName();
        List<Order> orders=orderRepository.findAll();
        List<OrderModel> orderlist=orderServiceInterface.getOrder(orders);
        model.addAttribute("OrderList",orderlist);
        return "admin/orders";
    }

    @GetMapping("/order-manage/{order_tracking_id}")
    public String manageOrder(@PathVariable String order_tracking_id, HttpSession session,
                              Model model, Principal principal){
        addModelAttribute(model);
        String userEmail = principal.getName();
        UUID orderTid = UUID.fromString(order_tracking_id);
        Order order=orderRepository.getOrdersByTrackingId(orderTid);
        model.addAttribute("order",order);
        List<OrderItemModel> orderItemList=orderItemRepository.getOrderItemModel(order.getId());
        model.addAttribute("orderItemList",orderItemList);
        Address billing_address=order.getBillingAddress();
        model.addAttribute("billingAddress",billing_address);
        List<Warehouse> warehouses=warehouseRepository.findAll();
        List<Shippment> shippments=order.getShippments();
        if(!shippments.isEmpty())
        {
            Shippment lastShippment=shippments.get(shippments.size()-1);
            model.addAttribute("LastShippment",lastShippment);
        }else{
            model.addAttribute("LastShippment",null);
        }
        model.addAttribute("Warehouses",warehouses);
        model.addAttribute("ShippmentList",shippments);
        return "admin/order_manage";
    }


    @PostMapping("/process-order/{orderId}")
    public ResponseEntity<PrivateMessage> processOrder(@PathVariable int orderId,
                                                       Model model,@RequestBody(required = false)
                                                       UpdateOrderRequest updateOrderRequest){
        addModelAttribute(model);
        PrivateMessage privateMessage=orderServiceInterface.processOrder(orderId,updateOrderRequest);
        return ResponseEntity.ok(privateMessage);
    }
}
