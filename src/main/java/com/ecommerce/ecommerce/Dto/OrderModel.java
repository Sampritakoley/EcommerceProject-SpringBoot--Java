package com.ecommerce.ecommerce.Dto;

import java.util.Date;
import java.util.UUID;

public class OrderModel {
    private int id;

    private String status;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }

    public String getUserFirstName() {
        return userFirstName;
    }

    public void setUserFirstName(String userFirstName) {
        this.userFirstName = userFirstName;
    }

    public String getUserLastName() {
        return userLastName;
    }

    public void setUserLastName(String userLastName) {
        this.userLastName = userLastName;
    }

    private UUID orderTrackingId;
    private Date date;

    private String userEmail;

    private String userFirstName;

    public double getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(double totalPrice) {
        this.totalPrice = totalPrice;
    }

    private String userLastName;
    private double totalPrice;
}
