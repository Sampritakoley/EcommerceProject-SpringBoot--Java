package com.ecommerce.ecommerce.Dto;

import com.ecommerce.ecommerce.Entities.Address;
import com.ecommerce.ecommerce.Entities.OrderItem;
import com.ecommerce.ecommerce.Entities.User;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

public class OrderBookModel {
    public int getOrder_id() {
        return order_id;
    }

    public void setOrder_id(int order_id) {
        this.order_id = order_id;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public UUID getOrderTrackingId() {
        return orderTrackingId;
    }

    public void setOrderTrackingId(UUID orderTrackingId) {
        this.orderTrackingId = orderTrackingId;
    }

    public Address getBillingAddress() {
        return billingAddress;
    }

    public void setBillingAddress(Address billingAddress) {
        this.billingAddress = billingAddress;
    }

    public double getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(double totalPrice) {
        this.totalPrice = totalPrice;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public List<OrderItemModel> getOrderItemsModel() {
        return orderItemsModel;
    }

    public void setOrderItemsModel(List<OrderItemModel> orderItemsModel) {
        this.orderItemsModel = orderItemsModel;
    }

    private int order_id;

    private String status;

    private UUID orderTrackingId;

    private Address billingAddress;
    private double totalPrice;

    private Date date;

    private User user;

    private List<OrderItemModel> orderItemsModel=new ArrayList<>();
}
