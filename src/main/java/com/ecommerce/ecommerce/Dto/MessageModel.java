package com.ecommerce.ecommerce.Dto;

public class MessageModel {

    private String userId;
    private String orderId;

    private String type;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getOrderId() {
        return orderId;
    }

    public MessageModel(String userId, String orderId,String type) {
        this.userId = userId;
        this.orderId = orderId;
        this.type=type;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }
}
