package com.ecommerce.ecommerce.Dto;

public class PrivateMessage {

    private String orderId;

    private String to;

    public PrivateMessage(String orderId, String to, String type) {
        this.orderId = orderId;
        this.to = to;
        this.type = type;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getTo() {
        return to;
    }

    public void setTo(String to) {
        this.to = to;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    private String type;
}
